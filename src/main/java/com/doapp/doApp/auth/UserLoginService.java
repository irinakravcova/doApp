package com.doapp.doApp.auth;

import com.doapp.doApp.models.User;
import com.doapp.doApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

@Service
public class UserLoginService {

    public static final Integer TOKEN_EXPIRATION_SECONDS = 60 * 5; // 5 minutes

    @Autowired
    UserRepository ur;

    public UserCredentials login(String userName, String password) {
        MessageDigest md; // we need some hashing function; let's use MD5 for easier implementation
        try {
            md = MessageDigest.getInstance("MD5"); // replace to bcrypt for safety later!!!
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Failed to obtain MD5 generator: " + e);
            return null;
        }
        byte[] tokenSourceData = (userName + password).getBytes(StandardCharsets.UTF_8); // create base for hashing
        byte[] md5bytes = md.digest(tokenSourceData); // generate hash out of base
        String md5string = Arrays.toString(md5bytes); // convert resulting hash to string representation
        User userFound = ur.findByUserNameAndPassword(userName, md5string);

        if (userFound == null) {
            return new UserCredentials("ERROR", "Invalid password or user not found");
        }
        md.reset();
        String token = md.digest((userFound.getUserName() + userFound.getEmail() + new Random().nextInt(Integer.MAX_VALUE)).toString().getBytes(StandardCharsets.UTF_8)).toString();
        userFound.setToken(token);

        Calendar tokenExpiration = Calendar.getInstance();
        tokenExpiration.add(Calendar.SECOND, TOKEN_EXPIRATION_SECONDS);
        userFound.setTokenExpiration(tokenExpiration);

        ur.save(userFound);

        return new UserCredentials("OK", "", userFound.getToken(), userFound.getNameSurname());
    }

    /**
     * Check token for validity and update expiration time if token still valid.
     *
     * @param token
     * @return
     */
    public UserCredentials login(String token) {
        User user = ur.findByTokenEquals(token);
        Calendar now = Calendar.getInstance();
        if (user != null) {
            user.getTokenExpiration().after(now);
            now.add(Calendar.SECOND, TOKEN_EXPIRATION_SECONDS);
            user.setTokenExpiration(now);
            ur.save(user); // update expiration token
            return new UserCredentials("OK", "", user.getToken(), user.getNameSurname());
        }
        return new UserCredentials("NOT_LOGGED_IN", "Not logged in");
    }
}
