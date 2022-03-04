package com.doapp.doApp.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCredentials {
    String status;
    String message;
    String token;
    String nameSurname;

    public UserCredentials(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
