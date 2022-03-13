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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public String list(Model model,
                       @RequestParam("token") String token) {
        User user = ul.userLogin(token);
        if (user == null) {
            return "login";
        }
        List<TaskList> lists = tlr.getLists(user.getUserId());
        model.addAttribute("token", user.getToken());
        model.addAttribute("lists", lists);
        return "lists";
    }

    @GetMapping("/list/{listId}")
    public String list(Model model,
                       @RequestParam("token") String token,
                       @PathVariable("listId") Integer listId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return "login";
        }
        List<Task> tasks = tr.listTasks(listId, user.getUserId());
        model.addAttribute("listId", listId);
        model.addAttribute("token", user.getToken());
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/add_list")
    public String addListForm(Model model,
                              @RequestParam("token") String token) {
        User user = ul.userLogin(token);
        if (user == null) {
            return "login";
        }
        model.addAttribute("token", user.getToken());
        return "add_list";
    }

    @PostMapping("/list/create")
    public ModelAndView create(ModelMap model,
                               @RequestParam("token") String token,
                               @RequestParam("newListName") String name) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("redirect:/login", model);
        }
        TaskList taskList = new TaskList(null, user, null, name);
        TaskList saved = tlr.save(taskList);
        model.addAttribute("token", user.getToken());
        return new ModelAndView("redirect:/lists", model);
    }

    @GetMapping("/edit_list")
    public String editListForm(Model model) {
        User user = ul.userLogin((String) model.getAttribute("token"));
        if (user == null) {
            return "login";
        }
        return "edit_list";
    }

    @PostMapping("/list/edit")
    public String edit(Model model,
                       @RequestParam("token") String token,
                       @RequestParam("listId") Integer listId,
                       @RequestParam("newName") String newName) {
        User user = ul.userLogin(token);
        if (user == null) {
            return "login";
        }
        TaskList taskList = tlr.findById(listId).orElseThrow();
        taskList.setName(newName);
        tlr.save(taskList);
        model.addAttribute("token", user.getToken());
        return "lists";
    }

    @DeleteMapping("/list/delete/{listId}")
    public String delete(Model model,
                         @RequestParam("token") String token,
                         @PathVariable("listId") Integer listId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return "login";
        }
        //todo: not implemented; must check if user is list owner; also need to get confirmation if list is not empty
        model.addAttribute("token", user.getToken());
        model.addAttribute("errorCode", 1); // just non-zero value to display message in template
        model.addAttribute("errorMessage", "Delete not implemented");
        return "lists";
    }

}
