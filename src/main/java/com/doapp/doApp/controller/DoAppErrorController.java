package com.doapp.doApp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DoAppErrorController implements ErrorController {

    /*
     * Error handler view
     */
    @RequestMapping("/error")
    public ModelAndView handleError(ModelMap model, HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token != null) {
            model.addAttribute("token", token);
        }
        return new ModelAndView("error", model);
    }
}