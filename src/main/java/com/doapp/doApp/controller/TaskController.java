package com.doapp.doApp.controller;

import com.doapp.doApp.auth.UserLoginService;
import com.doapp.doApp.models.Status;
import com.doapp.doApp.models.Task;
import com.doapp.doApp.models.TaskList;
import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.TaskListRepository;
import com.doapp.doApp.repository.TaskRepository;
import com.doapp.doApp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.doapp.doApp.controller.DoAppErrorCode.ILLEGAL_STATUS_CHANGE;
import static com.doapp.doApp.controller.DoAppErrorCode.NOT_IMPLEMENTED;

@Controller
public class TaskController {
    @Autowired
    TaskService ts;
    @Autowired
    UserLoginService ul;
    @Autowired
    TaskListRepository tlr;
    @Autowired
    TaskRepository tr;

    @GetMapping("/add_task/{listId}")
    public ModelAndView addTask(ModelMap model,
                                @RequestParam("token") String token,
                                @PathVariable("listId") Integer listId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());
        model.addAttribute("listId", listId);
        return new ModelAndView("add_task", model);
    }

    @GetMapping("/edit_task/{taskId}")
    public ModelAndView get(ModelMap model,
                            @RequestParam("token") String token,
                            @PathVariable("taskId") Integer taskId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        Task task = tr.findById(taskId).orElseThrow();
        model.addAttribute("token", user.getToken());
        model.addAttribute("taskId", task.getTaskId());
        model.addAttribute("listId", task.getList().getListId());
        model.addAttribute("name", task.getName());
        model.addAttribute("dueDate", task.getDueDate());
        return new ModelAndView("edit_task", model);
    }

    @PostMapping("/task/create/{listId}")
    public ModelAndView create(ModelMap model,
                               @RequestParam("token") String token, @PathVariable("listId") Integer listId,
                               @RequestParam("name") String name, @RequestParam("dueDate") String dueDate) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        TaskList taskList = tlr.findById(listId).orElseThrow();
        Task task = new Task(null, user, taskList, name, null, Status.INCOMPLETE.toString());
        Task saved = tr.save(task);

        System.out.println("Just created new task with id=" + saved.getTaskId());

        model.addAttribute("token", user.getToken());
        model.addAttribute("listId", listId);
        return new ModelAndView("redirect:/list/" + listId, model);
    }

    @PostMapping("/task/edit/{taskId}/{listId}")
    public ModelAndView edit(ModelMap model,
                             @RequestParam("token") String token,
                             @PathVariable("taskId") Integer taskId,
                             @PathVariable("listId") Integer listId,
                             @RequestParam("name") String name,
                             @RequestParam("dueDate") String dueDate) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());
        model.addAttribute("listId", listId);
        Task task = tr.findTaskByIdIfAccessibleToUser(taskId, user.getUserId());
        if (task == null) {
            System.err.println("No task or user has no rights: " + taskId + "/" + user.getUserId());

            return new ModelAndView("redirect:/list/" + listId, model);
        }
        task.setName(name);
        task.setDueDate(null); //TODO: parse dueDate and update dueDate ^_^
        tr.save(task);
        return new ModelAndView("redirect:/list/" + listId, model);
    }

    @GetMapping("/task/complete/{taskId}/{listId}")
    public ModelAndView complete(ModelMap model,
                                 @RequestParam("token") String token,
                                 @PathVariable("taskId") Integer taskId,
                                 @PathVariable("listId") Integer listId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());
        model.addAttribute("listId", listId);

        List<Task> tasks = tr.listTasks(listId, user.getUserId());
        model.addAttribute("tasks", tasks);

        //TODO: check if user can delete specified task from list this task belongs to
        Task task = tr.findById(taskId).orElseThrow();
        if (task.getStatus().equals(Status.COMPLETED.toString())) {
            model.addAttribute("errorCode", ILLEGAL_STATUS_CHANGE);
            model.addAttribute("errorMessage", "Task already completed");
            return new ModelAndView("list", model);
        }

        task.setStatus(Status.COMPLETED.toString());
        tr.save(task);

        return new ModelAndView("list", model);
    }

    @GetMapping("/task/delete/{taskId}/{listId}")
    public ModelAndView delete(ModelMap model,
                               @RequestParam("token") String token,
                               @PathVariable("taskId") Integer taskId,
                               @PathVariable("listId") Integer listId) {
        User user = ul.userLogin(token);
        if (user == null) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("token", user.getToken());
        model.addAttribute("listId", listId);

        //TODO: check if user can delete specified task from list this task belongs to

        List<Task> tasks = tr.listTasks(listId, user.getUserId());
        model.addAttribute("tasks", tasks);
        model.addAttribute("errorCode", NOT_IMPLEMENTED);
        model.addAttribute("errorMessage", "Deletion not implemented");

        return new ModelAndView("list", model);
    }

}
