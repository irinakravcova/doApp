package com.doapp.doApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

@Controller
public class ListPermissionController {

    @GetMapping("/permission/{listId}")
    public ModelAndView listGrantees(ModelMap model,
                                     @RequestParam("token") String token,
                                     @PathParam("listId") Integer listId) {
        //TODO: check if user allowed to view/change permissions of list (list owner only)
        return new ModelAndView("permission_list", model);
    }

    @GetMapping("/permission/grant/{listId}/{grantee}")
    public ModelAndView grantPermission(ModelMap model,
                                        @RequestParam("token") String token,
                                        @PathParam("listId") Integer listId,
                                        @PathParam("grantee") Integer granteeUserId) {
        //TODO: check if user allowed to view/change permissions of list (list owner only)
        return new ModelAndView("permission_list", model);
    }

    @GetMapping("/permission/revoke/{listId}/{grantee}")
    public ModelAndView revokePermission(ModelMap model,
                                         @RequestParam("token") String token,
                                         @PathParam("listId") Integer listId,
                                         @PathParam("grantee") Integer granteeUserId) {
        //TODO: check if user allowed to view/change permissions of list (list owner only)
        return new ModelAndView("permission_list", model);
    }

}
