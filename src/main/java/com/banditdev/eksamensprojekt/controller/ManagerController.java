package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Manager;
import com.banditdev.eksamensprojekt.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("profile")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("view")
    public String viewProfile(HttpSession session, Model model) {
        Manager currentLoggedInManager = (Manager) session.getAttribute("manager");
        model.addAttribute("manager", currentLoggedInManager);
        return "profileOverview"; // << TODO HTML PAGE
    }

    @GetMapping("edit")
    public String editProfile(HttpSession session, Model model) {
        Manager currentLoggedInManager = (Manager) session.getAttribute("manager");
        model.addAttribute("manager", currentLoggedInManager);
        return "profileEdit"; // << TODO HTML PAGE
    }

    @PostMapping("save")
    public String saveProfile(@ModelAttribute Manager manager, HttpSession session) {
        managerService.updateManager(manager);
        session.setAttribute("manager", manager);
        return "redirect:/profile/view";
    }


}
