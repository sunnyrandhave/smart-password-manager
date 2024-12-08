package com.smartpasswordmanager.services;

import com.smartpasswordmanager.entity.User;
import com.smartpasswordmanager.exception.MailAlreadyRegisteredException;
import com.smartpasswordmanager.exception.UserNotFoundException;
import com.smartpasswordmanager.repository.UserRepository;
import com.smartpasswordmanager.utils.PasswordUtil;
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
    @Autowired
    private PasswordUtil passwordUtil;

    public void RegisterUser(@RequestBody User user) throws Exception{
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByuserMail(user.getUserMail()));
        if (optionalUser.isPresent()){
            throw new MailAlreadyRegisteredException("Mail Id provided is already registered with another account!");
        }else {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            String subject = String.format("Welcome %s!",user.getUserName());
            String mailbody = String.format("Hello! "+user.getUserName()+" thank you for registering on Smart Password Manager. Enjoy our hassle-free Smart Password Manager to manage all secured passwords and download passwords anytime.\nUser-Name:"+user.getUserName()+"\nUser-password:"+user.getUserPassword());
            user.setCreatedAt(LocalDateTime.now());
            user.setUserPassword(passwordUtil.hashPassword(user.getUserPassword()));
            userRepository.save(user);
            simpleMailMessage.setTo(user.getUserMail());
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(mailbody);
            javaMailSender.send(simpleMailMessage);
        }
    }

    public User loginUser(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByuserName(username);
        if (user != null && passwordUtil.verifyPassword(password, user.getUserPassword())) {
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
            return user;
        } else {
            throw new UserNotFoundException("User Not Found or Invalid Password!");
        }
    }








}
