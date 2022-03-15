package com.doapp.doApp.controller;

import com.doapp.doApp.auth.UserLoginService;
import com.doapp.doApp.models.Task;
import com.doapp.doApp.models.TaskList;
import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.TaskListRepository;
import com.doapp.doApp.repository.TaskRepository;
import com.doapp.doApp.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.doapp.doApp.controller.DoAppErrorCode.NOT_OWNER_OF_LIST;

/*
 * TODO: authorization should be changed to really secure way instead of passing direct userId
 */
@Controller
public class ListController {

    @Autowired
    ListService ls;
    @Autowired
    TaskRepository tr;
    @Autowired
    TaskListRepository tlr;
    @Autowired
    UserLoginService ul;


    @GetMapping("/lists")
    public ModelAndView listsForm(ModelMap model,
                                  @RequestParam("token") String token) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());

        List<TaskList> lists = tlr.getLists(user.getUserId());
        model.addAttribute("lists", lists);

        return new ModelAndView("lists", model);
    }

    @GetMapping("/list/{listId}")
    public ModelAndView listForm(ModelMap model,
                                 @RequestParam("token") String token,
                                 @PathVariable("listId") Integer listId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());

        List<Task> tasks = tr.listTasks(listId, user.getUserId());
        model.addAttribute("listId", listId);
        model.addAttribute("tasks", tasks);

        return new ModelAndView("list", model);
    }

    @GetMapping("/add_list")
    public ModelAndView addListForm(ModelMap model,
                                    @RequestParam("token") String token) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());

        return new ModelAndView("add_list", model);
    }

    @PostMapping("/list/create")
    public ModelAndView listCreateAction(ModelMap model,
                                         @RequestParam("token") String token,
                                         @RequestParam("newListName") String name) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("redirect:/login", model);
        }
        model.addAttribute("token", user.getToken());

        TaskList taskList = new TaskList(null, user, null, name);
        TaskList saved = tlr.save(taskList);
        System.out.println("List created: " + saved.getListId());
        return new ModelAndView("redirect:/lists", model);
    }

    @GetMapping("/edit_list/{listId}")
    public ModelAndView editListForm(ModelMap model,
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
            List<TaskList> lists = tlr.getLists(user.getUserId());
            model.addAttribute("lists", lists);

            return new ModelAndView("lists", model);
        }

        model.addAttribute("listId", list.getListId());
        model.addAttribute("name", list.getName());

        return new ModelAndView("edit_list", model);
    }

    @PostMapping("/list/edit")
    public ModelAndView listEditAction(ModelMap model,
                                       @RequestParam("token") String token,
                                       @RequestParam("listId") Integer listId,
                                       @RequestParam("newName") String newName) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());

        TaskList list = tlr.findById(listId).orElseThrow();
        if (!user.getUserId().equals(list.getOwner().getUserId())) {
            model.addAttribute("errorCode", NOT_OWNER_OF_LIST);
            model.addAttribute("errorMessage", "You are not owner of list");

            List<TaskList> lists = tlr.getLists(user.getUserId());
            model.addAttribute("lists", lists);

            return new ModelAndView("redirect:/lists", model);
        }

        list.setName(newName);
        tlr.save(list);

        return new ModelAndView("redirect:/lists", model);
    }

    @GetMapping("/list/delete/{listId}")
    public ModelAndView listDeleteAction(ModelMap model,
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
            List<TaskList> lists = tlr.getLists(user.getUserId());
            model.addAttribute("lists", lists);
            return new ModelAndView("lists", model);
        }

        model.addAttribute("errorCode", 1); // just non-zero value to display message in template
        model.addAttribute("errorMessage", "Delete not implemented");

        List<TaskList> lists = tlr.getLists(user.getUserId());
        model.addAttribute("lists", lists);

        return new ModelAndView("lists", model);
    }

}
