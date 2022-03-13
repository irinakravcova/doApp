package com.doapp.doApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

@Controller
public class ListPermissionController {

    @GetMapping("/permission/{listId}")
    public String listGrantees(Model model,
                               @RequestParam("token") String token,
                               @PathParam("listId") Integer listId) {
        //TODO: check if user allowed to view/change permissions of list (list owner only)
        return "permission_list";
    }

    @GetMapping("/permission/grant/{listId}/{grantee}")
    public String grantPermission(Model model,
                                  @RequestParam("token") String token,
                                  @PathParam("listId") Integer listId,
                                  @PathParam("grantee") Integer granteeUserId) {
        //TODO: check if user allowed to view/change permissions of list (list owner only)
        return "permission_list";
    }

    @GetMapping("/permission/revoke/{listId}/{grantee}")
    public String revokePermission(Model model,
                                   @RequestParam("token") String token,
                                   @PathParam("listId") Integer listId,
                                   @PathParam("grantee") Integer granteeUserId) {
        //TODO: check if user allowed to view/change permissions of list (list owner only)
        return "permission_list";
    }

}
