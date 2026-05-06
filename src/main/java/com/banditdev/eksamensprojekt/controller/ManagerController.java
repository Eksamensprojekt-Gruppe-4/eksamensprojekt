package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("profile")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "loginPage";
        }
        return "redirect:/profileOverview";
    }

    @PostMapping("/authenticateLogin")
    public String login(@RequestParam String managerUsername, @RequestParam String managerPassword, HttpSession session) {

        if (managerService.validateUser(managerUsername, managerPassword)) {
            session.setAttribute("user", managerService.findManagerByUsername(managerUsername));
            return "redirect:/profile/loginPage";
        } else {
            return "redirect:/profile/profileOverview";
        }
    }
}
