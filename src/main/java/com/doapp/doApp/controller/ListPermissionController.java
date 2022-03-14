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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

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

    @GetMapping("/ps/{listId}")
    public ModelAndView listGrantees(ModelMap model,
                                     @RequestParam("token") String token,
                                     @PathParam("listId") Integer listId) {
//        listId = 5;
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        TaskList list = tlr.findById(listId).orElseThrow();
        if (!user.getUserId().equals(list.getOwner().getUserId())) {
            model.addAttribute("token", user.getToken());
            model.addAttribute("errorCode", 1);
            model.addAttribute("errorMessage", "You are not owner of list");
            return new ModelAndView("lists", model);
        }

        List<ListPermission> permissions = lpr.findByListPermissionId_TaskList_ListId(listId);

        List<User> otherUsers = ur.findByUserIdNot(user.getUserId());

        model.addAttribute("token", user.getToken());
        model.addAttribute("listId", listId);
        model.addAttribute("permissions", permissions);
        model.addAttribute("otherUsers", otherUsers);

        return new ModelAndView("permission_list", model);
    }

    @PostMapping("/p/grant")
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
            model.addAttribute("errorCode", 1); //TODO: add constant?..
            model.addAttribute("errorMessage", "You are not owner of list");
            return new ModelAndView("redirect:/lists", model);
        }
        // check if grantee has not assigned yet to listId
        ListPermission lp = lpr.findByListPermissionId_TaskList_ListIdAndListPermissionId_Grantee_UserId(listId, granteeUserId);
        if (lp != null) {
            model.addAttribute("errorCode", 2); //TODO: add constant?..
            model.addAttribute("errorMessage", "User is already in list of grantees");
            return new ModelAndView("redirect:/ps/" + listId, model);
        }
        ListPermission newLp = new ListPermission();
        newLp.setListPermissionId(new ListPermissionId());
        newLp.getListPermissionId().setTaskList(tlr.findById(listId).orElseThrow());
        newLp.getListPermissionId().setGrantee(ur.findById(granteeUserId).orElseThrow());
        lpr.save(newLp);

        return new ModelAndView("redirect:/permission_list/" + listId, model);
    }

    @GetMapping("/p/revoke/{listId}/{grantee}")
    public ModelAndView revokePermission(ModelMap model,
                                         @RequestParam("token") String token,
                                         @PathParam("listId") Integer listId,
                                         @PathParam("grantee") Integer granteeUserId) {
        //TODO: check if user allowed to view/change permissions of list (list owner only)
        return new ModelAndView("redirect:/permissions/" + listId, model);
    }

}
