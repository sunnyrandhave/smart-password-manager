package com.smartpasswordmanager.services;


import com.smartpasswordmanager.entity.Password;
import com.smartpasswordmanager.entity.User;
import com.smartpasswordmanager.exception.PasswordNotFoundException;
import com.smartpasswordmanager.exception.UserNotFoundException;
import com.smartpasswordmanager.repository.PasswordRepository;
import com.smartpasswordmanager.repository.UserRepository;
import com.smartpasswordmanager.utils.UserPasswordUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@Service
@Data
public class PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private UserRepository userRepository;
    private UserPasswordUtil userPasswordUtil;

    public Password addNewPassword(int userId, Password password) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User Not Found!"));
        password.setCreatedAt(LocalDateTime.now());
        password.setUpdatedAt(LocalDateTime.now());
        password.setUser(user);
        passwordRepository.save(password);
        return password;
    }

    public TreeMap<Integer,Password> getAllPasswordsByUserID(int user_id) throws Exception{
        Optional<User> user =   userRepository.findById(user_id);
        TreeMap<Integer,Password> passwordTreeMap = new TreeMap<>(Comparator.comparing(id->id));
        if (user.isPresent()){
             List<Password> passwordList = passwordRepository.findPasswordByUserId(user_id);
             for(Password i:passwordList){
                 passwordTreeMap.put(i.getPasswordId(),i);
             }
             return passwordTreeMap;
        }else{
            throw new UserNotFoundException("User Not Found!");
        }
    }

    public Password updatePassword(int userId,Password pwd) throws Exception{
        Password toBeUpdated = passwordRepository.findPasswordById(userId);
        toBeUpdated.setWebsitePassword(pwd.getWebsitePassword());
        toBeUpdated.setWebsiteUsername(pwd.getWebsiteUsername());
        toBeUpdated.setWebsite(pwd.getWebsite());
        toBeUpdated.setUpdatedAt(LocalDateTime.now());
        passwordRepository.save(toBeUpdated);
        return toBeUpdated;
    }

    public String deletePassword(int passwordId) throws Exception{
        Optional<Password> passwordOptional = passwordRepository.findById(passwordId);
        if (passwordOptional.isPresent()){
            String pwdName = passwordOptional.get().getWebsite();
            passwordRepository.delete(passwordOptional.get());
           return "Password for "+pwdName+" Deleted!";
        }else{
            throw new PasswordNotFoundException("Password with "+passwordId+" Doesn't exits!!");
        }
    }






}
