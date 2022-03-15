package com.doapp.doApp.service;

import com.doapp.doApp.models.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class PageDataService {
    @Value("${chatapp.title}")
    String appTitle;
    List<Page> availablePages = new ArrayList<>();

    PageDataService() {
        availablePages.add(new Page("login", "Login", "Login into DoApp", "/"));
        availablePages.add(new Page("register", "Register", "Create an account", "/register"));
        availablePages.add(new Page("list", "Tasks", "Tasks within list", "/list"));
        availablePages.add(new Page("lists", "Task Lists", "Lists of tasks available for user", "/lists"));
        availablePages.add(new Page("add_list", "Add New List", "Add new list of tasks with given name", "/add_list"));
        availablePages.add(new Page("delete_list", "Delete List", "Delete selected list", "/delete_list"));
        availablePages.add(new Page("edit_list", "Edit List", "Edit list name", "/edit_list"));
        availablePages.add(new Page("add_task", "Add New Task", "Add new task with name and due date", "/add_task"));
        availablePages.add(new Page("complete_task", "Complete Task", "Mark selected task as completed", "/complete_task"));
        availablePages.add(new Page("delete_task", "Delete Task", "Delete selected task", "/delete_task"));
        availablePages.add(new Page("edit_task", "Edit Task", "Edit task name and due date", "/edit_task"));
        availablePages.add(new Page("permissions", "Permissions granted to list", "Edit task name and due date", "/permissions"));
        availablePages.add(new Page("permission_grant", "Grant permission to user", "Grant permission to user", "/perimssion/grant"));
        availablePages.add(new Page("permission_revoke", "Revoke permission to user", "Grant permission to user", "/perimssion/revoke"));
        availablePages.add(new Page("useraccount", "User settings", "Edit user settings", "/useraccount"));
    }

    public Page getPage(String pageName) {
        for (Page page : this.availablePages) {
            if (page.getName().equalsIgnoreCase(pageName)) return page;
        }
        return null;
    }

    public List<Page> getPages() {
        return availablePages;
    }

    public String getAppTitle() {
        return appTitle;
    }

}
