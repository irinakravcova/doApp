package com.doapp.doApp.auth;

import com.doapp.doApp.service.PageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserRegistrationController {

    @Autowired
    PageDataService pageDataService;

    @Autowired
    UserRegistrationService ur;

    @PostMapping("/api/register")
    public String register(Model model,
                           @RequestParam("nameSurname") String nameSurname,
                           @RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email
    ) {
        String status = ur.addUser(nameSurname, userName, password, email);
        if ("OK".equals(status)) {
            return "login";
        }
        model.addAttribute("errorCode", 1); // just non-zero value to display message in template
        model.addAttribute("errorMessage", status);
        return "register";
    }

}
