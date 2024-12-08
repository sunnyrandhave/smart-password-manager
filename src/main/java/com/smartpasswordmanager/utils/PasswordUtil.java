package com.smartpasswordmanager.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Hashing the password
    public String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    // Verifying the password
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }

//    public static void main(String[] args) {
//        String password = "mySecret123";
//        String hashedPassword = hashPassword(password);
//
//        System.out.println("Hashed Password: " + hashedPassword);
//
//        // Check if password matches
//        boolean isMatch = verifyPassword("mySecret123", hashedPassword);
//        System.out.println("Password Match: " + isMatch);
//    }
}
