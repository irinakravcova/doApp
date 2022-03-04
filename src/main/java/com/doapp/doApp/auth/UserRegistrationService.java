package com.doapp.doApp.auth;

import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class UserRegistrationService {

    @Autowired
    UserRepository ur;

    String addUser(String nameSurname, String userName, String password, String email) {
        User userExists = ur.findByUserName(userName);
        if (userExists != null) {
            return "User name already exists";
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5"); // replace to bcrypt for safety later!!!
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Failed to obtain MD5 generator: " + e);
            return "Failed to create user";
        }
        byte[] tokenSourceData = (userName + password).getBytes(StandardCharsets.UTF_8);
        byte[] md5bytes = md.digest(tokenSourceData);
        String md5string = Arrays.toString(md5bytes);
        User newUser = new User(null, nameSurname, userName, md5string, email, null, null);
        User savedUser = ur.save(newUser);
        System.out.println("User created with name: '" + savedUser.getUserName() + "', id: " + savedUser.getUserId());
        return "OK";
    }
}
