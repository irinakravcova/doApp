package com.doapp.doApp.controller;

import com.doapp.doApp.dto.GranteeUsers;
import com.doapp.doApp.dto.OperationStatus;
import com.doapp.doApp.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class ListPermissionController {

    @GetMapping("/{listId}")
    public GranteeUsers listGrantees(@RequestParam Integer userId, @PathParam("listId") Integer listId) {
        return new GranteeUsers(List.of(new UserDTO(1, "name one"), new UserDTO(2, "name two")));
    }

    public OperationStatus grantPermission(Integer userId, Integer listId, Integer granteeUserId) {
        return new OperationStatus("OK", "Permission granted");
    }

    public OperationStatus revokePermission(Integer userId, Integer listId, Integer granteeUserId) {
        return new OperationStatus("OK", "Permission has been revoked");
    }

}
