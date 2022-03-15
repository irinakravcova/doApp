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

    public static final Integer TOKEN_EXPIRATION_SECONDS = 60 * 60; // 60 minutes

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
        byte[] hashedPasswordSourceData = (userName + password).getBytes(StandardCharsets.UTF_8); // create base for hashing
        byte[] md5bytes = md.digest(hashedPasswordSourceData); // generate hash out of base
        String hashedPassword = bytesToHex(md5bytes); // convert resulting hash to string representation
        User userFound = ur.findByUserNameAndPassword(userName, hashedPassword);

        if (userFound == null) {
            return new UserCredentials("ERROR", "Invalid password or user not found");
        }
        md.reset();
        byte[] hashedTokenSourceData = (userFound.getUserName() + userFound.getEmail() + new Random().nextInt(Integer.MAX_VALUE))
                .getBytes(StandardCharsets.UTF_8);
        byte[] md5tokenData = md.digest(hashedTokenSourceData);
        String token = bytesToHex(md5tokenData);
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
        User user = userLogin(token);
        if (user != null) {
            return new UserCredentials("OK", "", user.getToken(), user.getNameSurname());
        }
        return new UserCredentials("NOT_LOGGED_IN", "Not logged in");
    }

    /**
     * Find user by token, if token is still valid; also update token expiration if token still valid.
     *
     * @param token
     * @return user or null
     */
    public User userLogin(String token) {
        User user = ur.findByTokenEquals(token);
        if (user != null) {
            Calendar now = Calendar.getInstance();
            if (user.getTokenExpiration().before(now)) {
                return null;
            }
            now.add(Calendar.SECOND, TOKEN_EXPIRATION_SECONDS);
            user.setTokenExpiration(now);
            ur.save(user); // update expiration token
            return user;
        }
        return null;
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

}
