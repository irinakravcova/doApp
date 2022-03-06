package com.doapp.doApp.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
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
