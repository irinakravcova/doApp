package com.doapp.doApp.auth;

import com.doapp.doApp.service.PageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    PageDataService pageDataService;

    @Autowired
    public UserController(PageDataService pageDataService) {
        this.pageDataService = pageDataService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("register"));
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("login"));
        return "login";
    }

}
