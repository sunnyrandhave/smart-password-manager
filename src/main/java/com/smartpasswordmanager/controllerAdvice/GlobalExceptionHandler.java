package com.smartpasswordmanager.controllerAdvice;

import com.smartpasswordmanager.exception.MailAlreadyRegisteredException;
import com.smartpasswordmanager.exception.PasswordNotFoundException;
import com.smartpasswordmanager.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        ErrorInfo errorInfo = new ErrorInfo(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MailAlreadyRegisteredException.class)
    public ResponseEntity<ErrorInfo> handlerMailAlreadyRegisteredException(MailAlreadyRegisteredException mailAlreadyRegisteredException){
        ErrorInfo errorInfo = new ErrorInfo(mailAlreadyRegisteredException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotFoundException.class)
    public ResponseEntity<ErrorInfo> handlePasswordNotFoundException(PasswordNotFoundException passwordNotFoundException){
        ErrorInfo errorInfo = new ErrorInfo(passwordNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorInfo,HttpStatus.NOT_FOUND);
    }


}
