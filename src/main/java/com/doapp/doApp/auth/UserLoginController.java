package com.doapp.doApp.auth;

import com.doapp.doApp.controller.ListController;
import com.doapp.doApp.dto.TaskListsDTO;
import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.UserRepository;
import com.doapp.doApp.service.PageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/api/login")
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password
    ) {
        System.err.println("Login: entered");
        UserCredentials uc = ul.login(username, password);
        if (uc == null) {
            model.addAttribute("errorCode", 1);
            model.addAttribute("errorMessage", "Login failed");
            System.err.println("Login: FAILED!!!");
            return "login";
        }

        System.err.println("Login: Passed, user creds: " + uc);

        User user = ur.findByTokenEquals(uc.getToken());
        TaskListsDTO lists = lc.list(user.getUserId());

        model.addAttribute("tasksLists", lists);
        model.addAttribute("token", uc.getToken());
        return "lists";
    }

}
