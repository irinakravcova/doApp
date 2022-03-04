package com.doapp.doApp.models;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

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
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar tokenExpiration;
}
