package com.doapp.doApp.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Integer userId;
    private String nameSurname;
    private String userName;
    private String password;
    private String email;
}
