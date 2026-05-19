package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.model.UserExperience;
import com.banditdev.eksamensprojekt.model.UserRole;
import com.banditdev.eksamensprojekt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("adminPanel")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showAdminPanel(Model model, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        } else if (currentLoggedInUser.getUserRole() != UserRole.ADMIN) {
            return "redirect:/projects/myProjects";
        } else {
            model.addAttribute("users", userService.findAllUsers());
            return "adminPanelView";
        }
    }

    @PostMapping("delete/{userId}")
    public String deleteUserAsAdmin(@PathVariable int userId, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        } else if (currentLoggedInUser.getUserRole() != UserRole.ADMIN) {
            return "redirect:/projects/myProjects";
        }

        if (currentLoggedInUser.getUserId() == userId) {
            return "redirect:/adminPanel";
        }
        userService.deleteByUserId(userId);
        return "redirect:/adminPanel";
    }

    @GetMapping("create")
    public String showCreateUserForm(Model model, HttpSession session) {
        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        } else if (currentLoggedInUser.getUserRole() != UserRole.ADMIN) {
            return "redirect:/projects/myProjects";
        }

        model.addAttribute("user", new User());
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("experiences", UserExperience.values());
        return "adminCreateUser";
    }

    @PostMapping("create")
    public String createUser(@ModelAttribute User user, HttpSession session) {
        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        } else if (currentLoggedInUser.getUserRole() != UserRole.ADMIN) {
            return "redirect:/projects/myProjects";
        }

        userService.createUser(user);
        return "redirect:/adminPanel";
    }

    @GetMapping("edit/{userId}")
    public String showEditUserForm(@PathVariable int userId, Model model, HttpSession session) {
        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        } else if (currentLoggedInUser.getUserRole() != UserRole.ADMIN) {
            return "redirect:/projects/myProjects";
        }

        model.addAttribute("user", userService.findUserByUserId(userId));
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("experiences", UserExperience.values());
        return "adminEditUser";
    }

    @PostMapping("edit/{userId}")
    public String editUserAsAdmin(@PathVariable int userId, @ModelAttribute User user, HttpSession session) {
        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        } else if (currentLoggedInUser.getUserRole() != UserRole.ADMIN) {
            return "redirect:/projects/myProjects";
        }

        user.setUserId(userId);
        userService.updateUserAsAdmin(user);
        return "redirect:/adminPanel";
    }
}
