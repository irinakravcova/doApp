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
        availablePages.add(new Page("index", "Home", "Home page", "/"));
        availablePages.add(new Page("register", "Register", "Create an account", "/register"));
        availablePages.add(new Page("login", "Login", "Login to start chatting", "/login"));
        availablePages.add(new Page("tasks", "Tasks", "Tasks belongs to user", "/tasks"));
        availablePages.add(new Page("lists", "Task Lists", "Lists of tasks available for user", "/lists"));
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
