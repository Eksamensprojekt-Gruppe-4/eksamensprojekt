package com.banditdev.eksamensprojekt.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer status = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        model.addAttribute("status", status != null ? status : 500);
        model.addAttribute("error", status != null && status == 404
                ? "Page not found"
                : "Something went wrong");
        model.addAttribute("message", status != null && status == 404
                ? "The page you are looking for does not exist."
                : "An unexpected error occurred. Please try again.");

        return "errorPage";
    }
}