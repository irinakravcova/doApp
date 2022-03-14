package com.doapp.doApp.auth;

import com.doapp.doApp.service.PageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    PageDataService pageDataService;

    @Autowired
    public UserController(PageDataService pageDataService) {
        this.pageDataService = pageDataService;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage(ModelMap model) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("register"));
        return new ModelAndView("register", model);
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage(ModelMap model) {
        model.addAttribute("appTitle", pageDataService.getAppTitle());
        model.addAttribute("pageInfo", pageDataService.getPage("login"));
        return new ModelAndView("login", model);
    }

}
