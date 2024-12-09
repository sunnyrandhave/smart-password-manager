package com.smartpasswordmanager.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordUtil {
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
//        PasswordUtil obj = new PasswordUtil();
//        String password = "sam@randhave1";
//        String hashedPassword = obj.hashPassword(password);
//
//        System.out.println("Hashed Password: " + hashedPassword);
//
//        // Check if password matches
//        boolean isMatch = obj.verifyPassword("sam@randhave1", hashedPassword);
//        System.out.println("Password Match: " + isMatch);
//    }
}
