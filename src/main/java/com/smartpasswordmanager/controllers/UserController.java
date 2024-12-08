package com.smartpasswordmanager.controllers;
import com.smartpasswordmanager.entity.User;
import com.smartpasswordmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception{
        Map map = new HashMap();
        userService.RegisterUser(user);
        map.put("message","User Registered Sucessfully");
        map.put(user.getUserId(),user);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user1) throws Exception  {
        User user = userService.loginUser(user1.getUserName(), user1.getUserPassword());
        Map map = new HashMap();
        if (user != null) {
            map.put("message","login Successfull!");
            map.put("user",user);
            return ResponseEntity.ok(map); // Send user details if login is successful
        }
        map.put("message","Invalid credentials.!");
        return ResponseEntity.status(401).body(map); // Unauthorized if login fails
    }


}
