package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.model.UserRole;
import com.banditdev.eksamensprojekt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("adminPanel")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showAdminPanel(HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        } else if (currentLoggedInUser.getUserRole() != UserRole.ADMIN) {
            return "redirect:/projects/myProjects";
        } else {
            return "adminPanelView";
        }
    }
}
