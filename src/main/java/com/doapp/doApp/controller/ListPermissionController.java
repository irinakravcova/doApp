package com.doapp.doApp.controller;

import com.doapp.doApp.auth.UserLoginService;
import com.doapp.doApp.models.ListPermission;
import com.doapp.doApp.models.ListPermissionId;
import com.doapp.doApp.models.TaskList;
import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.ListPermissionRepository;
import com.doapp.doApp.repository.TaskListRepository;
import com.doapp.doApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.doapp.doApp.controller.DoAppErrorCode.*;

@Controller
public class ListPermissionController {

    @Autowired
    UserLoginService ul;
    @Autowired
    UserRepository ur;
    @Autowired
    TaskListRepository tlr;

    @Autowired
    ListPermissionRepository lpr;

    private ModelAndView redirectToPermissionList(ModelMap model, Integer listId) {
        model.addAttribute("listId", listId);
        return new ModelAndView("permission_list", model);
    }

    private void loadPermissionsAndUsers(ModelMap model, Integer listId, Integer userId) {
        List<ListPermission> permissions = lpr.findByListPermissionId_TaskList_ListId(listId);

        List<User> otherUsers = ur.findByUserIdNot(userId);

        model.addAttribute("permissions", permissions);
        model.addAttribute("otherUsers", otherUsers);
    }

    @GetMapping("/permissions/{listId}")
    public ModelAndView listGrantees(ModelMap model,
                                     @RequestParam("token") String token,
                                     @PathVariable("listId") Integer listId) {

        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }

        model.addAttribute("token", user.getToken());

        TaskList list = tlr.findById(listId).orElseThrow();
        if (!user.getUserId().equals(list.getOwner().getUserId())) {
            model.addAttribute("errorCode", NOT_OWNER_OF_LIST);
            model.addAttribute("errorMessage", "You are not owner of list");
            return new ModelAndView("lists", model);
        }

        loadPermissionsAndUsers(model, listId, user.getUserId());

        return redirectToPermissionList(model, listId);
    }

    @PostMapping("/permission/grant")
    public ModelAndView grantPermission(ModelMap model,
                                        @RequestParam("token") String token,
                                        @RequestParam("listId") Integer listId,
                                        @RequestParam("grantee") Integer granteeUserId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }

        model.addAttribute("token", user.getToken());

        // check if current user is owner of listId
        TaskList list = tlr.findById(listId).orElseThrow();
        if (!user.getUserId().equals(list.getOwner().getUserId())) {
            model.addAttribute("errorCode", NOT_OWNER_OF_LIST);
            model.addAttribute("errorMessage", "You are not owner of list");
            return new ModelAndView("redirect:/lists", model);
        }
        // check if grantee has not assigned yet to listId
        ListPermission lp = lpr.findByListPermissionId_TaskList_ListIdAndListPermissionId_Grantee_UserId(listId, granteeUserId);
        if (lp != null) {
            model.addAttribute("errorCode", PERMISSION_ALREADY_GRANTED);
            model.addAttribute("errorMessage", "User is already in list of grantees");

            loadPermissionsAndUsers(model, listId, user.getUserId());

            return redirectToPermissionList(model, listId);
        }
        ListPermission newLp = new ListPermission();
        newLp.setListPermissionId(new ListPermissionId());
        newLp.getListPermissionId().setTaskList(tlr.findById(listId).orElseThrow());
        newLp.getListPermissionId().setGrantee(ur.findById(granteeUserId).orElseThrow());
        lpr.save(newLp);
        System.out.println("Permission granted: list=" + listId + ", userId=" + granteeUserId);

        loadPermissionsAndUsers(model, listId, user.getUserId());

        return redirectToPermissionList(model, listId);
    }

    @GetMapping("/permission/revoke/{listId}/{grantee}")
    public ModelAndView revokePermission(ModelMap model,
                                         @RequestParam("token") String token,
                                         @PathVariable("listId") Integer listId,
                                         @PathVariable("grantee") Integer granteeUserId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }

        model.addAttribute("token", user.getToken());

        // check if current user is owner of listId
        TaskList list = tlr.findById(listId).orElseThrow();
        if (!user.getUserId().equals(list.getOwner().getUserId())) {
            model.addAttribute("errorCode", NOT_OWNER_OF_LIST);
            model.addAttribute("errorMessage", "You are not owner of list");
            return new ModelAndView("redirect:/lists", model);
        }
        // find permission...
        ListPermission lp = lpr.findByListPermissionId_TaskList_ListIdAndListPermissionId_Grantee_UserId(listId, granteeUserId);
        if (lp == null) {
            model.addAttribute("errorCode", NO_SUCH_PERMISSION_FOUND);
            model.addAttribute("errorMessage", "No such permission found.");

            return redirectToPermissionList(model, listId);
        }
        lpr.delete(lp);
        System.out.println("Permission revoked: list=" + listId + ", userId=" + granteeUserId);

        loadPermissionsAndUsers(model, listId, user.getUserId());

        return redirectToPermissionList(model, listId);
    }

}
