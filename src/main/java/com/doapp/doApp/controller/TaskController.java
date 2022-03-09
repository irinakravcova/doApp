package com.doapp.doApp.controller;

import com.doapp.doApp.models.Task;
import com.doapp.doApp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService ts;

    @GetMapping("/{id}")
    public Task get(@RequestParam("token") String token, @PathVariable("id") Integer taskId) {
        return null;
    }

    @PostMapping("/create")
    public Task create(@RequestParam("token") String token, @RequestParam("listId") Integer listId,
                       @RequestParam("name") String name, @RequestParam("dueDate") String dueDate) {
        return null;
    }

    @PutMapping("/update")
    public Task update(@RequestParam("token") String token, @RequestParam("taskId") Integer taskId,
                       @RequestParam("name") String name, @RequestParam("dueDate") String dueDate) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Task delete(@RequestParam("token") String token, @PathVariable("id") Integer taskId) {
        return null;
    }

}
