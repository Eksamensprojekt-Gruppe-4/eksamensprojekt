package com.banditdev.eksamensprojekt.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("error", "Something went wrong");
        model.addAttribute("message", "An unexpected error occurred. Please try again.");
        return "errorPage";
    }
}