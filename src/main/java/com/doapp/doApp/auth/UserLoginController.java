package com.doapp.doApp.auth;

import com.doapp.doApp.controller.ListController;
import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.UserRepository;
import com.doapp.doApp.service.PageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserLoginController {

    PageDataService pageDataService;
    UserLoginService ul;
    ListController lc;
    UserRepository ur;

    @Autowired
    public UserLoginController(PageDataService pageDataService, UserLoginService ul, ListController lc, UserRepository ur) {
        this.pageDataService = pageDataService;
        this.ul = ul;
        this.lc = lc;
        this.ur = ur;
    }

    @GetMapping("/")
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("login", model);
    }

    @PostMapping("/api/login")
    public ModelAndView login(ModelMap model,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password
    ) {
        System.err.println("Login: entered");
        User uc = ul.login(username, password);
        if (uc == null) {
            model.addAttribute("errorCode", 1);
            model.addAttribute("errorMessage", "Login failed");
            System.err.println("Login: FAILED!!!");
            return new ModelAndView("login", model);
        }

        System.err.println("Login: Passed, user creds: " + uc);

        model.addAttribute("token", uc.getToken());

        return new ModelAndView("redirect:/lists", model);
    }

}
