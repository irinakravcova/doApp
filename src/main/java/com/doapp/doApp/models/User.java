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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
