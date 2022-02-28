package com.doapp.doApp.models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLogin {
    private String username;
    private String password;
}