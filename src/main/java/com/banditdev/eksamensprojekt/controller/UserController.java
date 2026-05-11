package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("profile")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "loginPage";
        }
        return "redirect:/profile/view"; // TODO: change to project board
    }

    @PostMapping("/authenticateLogin")
    public String login(@RequestParam String userUsername, @RequestParam String userPassword, HttpSession session) {

        if (userService.validateUser(userUsername, userPassword)) {
            session.setAttribute("user", userService.findUserByUserUsername(userUsername));
            return "redirect:/profile/view"; // TODO: change to project board
        } else {
            return "loginPage";
        }
    }

    @GetMapping("view")
    public String viewProfile(HttpSession session, Model model) {
        User currentLoggedInUser = (User) session.getAttribute("user");

        if (currentLoggedInUser == null) {
            return "redirect:/profile/login";
        }

        model.addAttribute("user", currentLoggedInUser);
        return "profileOverview";
    }

    @GetMapping("edit")
    public String editProfile(HttpSession session, Model model) {
        User currentLoggedInUser = (User) session.getAttribute("user");

        if (currentLoggedInUser == null) {
            return "redirect:/profile/login";
        }

        model.addAttribute("user", currentLoggedInUser);
        return "profileEdit";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "loginPage";
    }

    @PostMapping("update")
    public String updateProfile(@ModelAttribute User user, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");

        // Preserves an existing password if a new one is unset
        if (user.getUserPassword() == null || user.getUserPassword().trim().isEmpty()) {
            user.setUserPassword(currentLoggedInUser.getUserPassword());
        }

        userService.updateUser(user);
        session.setAttribute("user", user);

        return "redirect:/profile/view";
    }
}