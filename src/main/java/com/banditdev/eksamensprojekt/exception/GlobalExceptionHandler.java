package com.banditdev.eksamensprojekt.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "User not found");
        model.addAttribute("message", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public String handleProjectNotFound(ProjectNotFoundException ex, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Project not found");
        model.addAttribute("message", ex.getMessage());
        return "errorPage";
    }


    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound(TaskNotFoundException ex, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Task not found");
        model.addAttribute("message", ex.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("error", "Something went wrong");
        model.addAttribute("message", "An unexpected error occurred. Please try again.");
        return "errorPage";
    }
}