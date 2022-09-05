package com.decagon.taskactivity.controller;

import com.decagon.taskactivity.exception.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class UserException {
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(UserNotFoundException usex, Model model){
        model.addAttribute("error", "Invalid login details. kindly confirm your Login details");
        System.out.println("gsbjhsb");
        return "login";
    }

}
