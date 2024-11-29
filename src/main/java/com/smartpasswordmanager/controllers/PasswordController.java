package com.smartpasswordmanager.controllers;

import com.smartpasswordmanager.entity.Password;
import com.smartpasswordmanager.services.PasswordService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("user/password")
public class PasswordController {
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Password> addNewPassword(@PathVariable int userId,@RequestBody Password password) throws Exception{
        System.out.println(password.toString());
        passwordService.addNewPassword(userId,password);
      return new ResponseEntity<>(password, HttpStatus.CREATED);
    };

    @GetMapping("/getByUserId/{user_Id}")
    public ResponseEntity<TreeMap<Integer,Password>> getAllPasswordsByUserId(@PathVariable("user_Id") int user_Id) throws Exception{
        return new ResponseEntity<>(passwordService.getAllPasswordsByUserID(user_Id),HttpStatus.OK);
    }

    @PutMapping("/updatePassword/{userId}")
    public ResponseEntity<Password> updatePassword(@PathVariable("userId") int userId,@RequestBody Password password) throws Exception{
        System.out.println(password);
        return new ResponseEntity<>(passwordService.updatePassword(userId,password),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{password_Id}")
    public ResponseEntity<String> deletePassword(@PathVariable("password_Id")int password_Id) throws Exception{
        return new ResponseEntity<>(passwordService.deletePassword(password_Id),HttpStatus.OK);
    }


}
