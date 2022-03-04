package com.doapp.doApp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class UserRegistrationController {

    @Autowired
    UserRegistrationService ur;

    @PostMapping("/")
    public String login(@RequestParam("nameSurname") String nameSurname,
                        @RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("email") String email
    ) {
        return ur.addUser(nameSurname, userName, password, email);
    }

}
