package com.smartpasswordmanager.services;

import com.smartpasswordmanager.entity.User;
import com.smartpasswordmanager.exception.MailAlreadyRegisteredException;
import com.smartpasswordmanager.exception.UserNotFoundException;
import com.smartpasswordmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public void RegisterUser(@RequestBody User user) throws Exception{
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByuserMail(user.getUserMail()));
        if (optionalUser.isPresent()){
            throw new MailAlreadyRegisteredException("Mail Id provided is already registered with another account!");
        }else {
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(user.getUserMail());
            String subject = String.format("Welcome %s!",user.getUserName());
            String mailbody = String.format("Hello! "+user.getUserName()+" thank you for registering on Smart Password Manager. Enjoy our hassle-free Smart Password Manager to manage all secured passwords and download passwords anytime.\nUser-Name:"+user.getUserName()+"\nUser-password:"+user.getUserPassword());
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(mailbody);
            javaMailSender.send(simpleMailMessage);
        }
    }

    public User loginUser(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByuserName(username);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        if (user != null && user.getUserPassword().equals(password)) {
            return user;
        }
        throw new UserNotFoundException("User Not Found!");
    }







}
