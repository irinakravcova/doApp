package com.doapp.doApp.models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class User {
    private long id;
    private String userName;
    private String password;
}
