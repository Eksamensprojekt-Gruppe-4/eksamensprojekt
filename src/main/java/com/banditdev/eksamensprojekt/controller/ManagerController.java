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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("profile")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("manager") == null) {
            return "loginPage";
        }
        return "redirect:/profile/view"; //TODO ændrer til project board
    }

    @PostMapping("/authenticateLogin")
    public String login(@RequestParam String managerUsername, @RequestParam String managerPassword, HttpSession session) {

        if (managerService.validateManager(managerUsername, managerPassword)) {
            session.setAttribute("manager", managerService.findManagerByUsername(managerUsername));
            return "redirect:/profile/view"; //TODO ændrer til project board
        } else {
            return "loginPage";
        }
    }

    @GetMapping("view")
    public String viewProfile(HttpSession session, Model model) {
        Manager currentLoggedInManager = (Manager) session.getAttribute("manager");

        if (currentLoggedInManager == null) { //TODO logik skal tjekkes igennem og gøres universel
            return "redirect:/profile/login";
        }

        model.addAttribute("manager", currentLoggedInManager);
        return "profileOverview";
    }

    @GetMapping("edit")
    public String editProfile(HttpSession session, Model model) {
        Manager currentLoggedInManager = (Manager) session.getAttribute("manager");

        if (currentLoggedInManager == null) { //TODO logik skal tjekkes igennem og gøres universel
            return "redirect:/profile/login";
        }

        model.addAttribute("manager", currentLoggedInManager);
        return "profileEdit";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "loginPage";
    }

    @PostMapping("update")
    public String updateProfile(@ModelAttribute Manager manager, HttpSession session) {
        managerService.updateManager(manager);
        session.setAttribute("manager", manager);
        return "redirect:/profile/view";
    }
}
