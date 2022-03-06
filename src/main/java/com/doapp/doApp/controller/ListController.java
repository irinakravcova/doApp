package com.doapp.doApp.controller;

import com.doapp.doApp.dto.OperationStatus;
import com.doapp.doApp.dto.TaskListDTO;
import com.doapp.doApp.dto.TaskListsDTO;
import com.doapp.doApp.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * TODO: authorization should be changed to really secure way instead of passing direct userId
 */
@RestController
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    ListService ls;

    @GetMapping("/my")
    public TaskListsDTO list(@RequestParam("userId") Integer userId) {
        return new TaskListsDTO(List.of(new TaskListDTO(1, "test list 1"), new TaskListDTO(2, "test list 2")));
    }

    @GetMapping("/list/{listId}")
    public TaskListsDTO list(@RequestParam("userId") Integer userId, @PathVariable("listId") Integer listId) {
        return new TaskListsDTO(List.of(new TaskListDTO(listId, "test list 1")));
    }

    @PostMapping("/create")
    public TaskListDTO create(@RequestParam("userId") Integer userId, @RequestParam("name") String name) {
        return new TaskListDTO(1, name);
    }

    @PutMapping("/rename")
    public TaskListDTO rename(@RequestParam("userId") Integer userId, @RequestParam("listId") Integer listId, @RequestParam("newName") String newName) {
        return new TaskListDTO(1, newName);
    }

    @DeleteMapping("/delete/{listId}")
    public OperationStatus delete(@RequestParam("userId") Integer userId, @PathVariable("listId") Integer listId) {
        return new OperationStatus("OK", "Delete succeed");
    }

}
