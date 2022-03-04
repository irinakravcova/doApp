package com.doapp.doApp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class UserLoginController {

    @Autowired
    UserLoginService ul;

    @PostMapping("/")
    public UserCredentials login(@RequestParam("nameSurname") String nameSurname,
                                 @RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email
    ) {
        return ul.login(userName, password);
    }

}
