package com.smartpasswordmanager.controllerAdvice;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
public class ErrorInfo {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime timeStamp;

    public ErrorInfo(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp= LocalDateTime.now();
    }

}
