package com.doapp.doApp.auth;

import com.doapp.doApp.service.PageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRegistrationController {

    @Autowired
    PageDataService pageDataService;

    @Autowired
    UserRegistrationService ur;

    @PostMapping("/api/register")
    public ModelAndView register(ModelMap model,
                                 @RequestParam("nameSurname") String nameSurname,
                                 @RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email
    ) {
        String status = ur.addUser(nameSurname, userName, password, email);
        if ("OK".equals(status)) {
            return new ModelAndView("login", model);
        }
        model.addAttribute("errorCode", 1); // just non-zero value to display message in template
        model.addAttribute("errorMessage", status);
        return new ModelAndView("register", model);
    }

}
