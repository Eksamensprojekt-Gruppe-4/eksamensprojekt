package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.service.SubProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/subprojects")
public class SubProjectController {
    private final SubProjectService service;

    public SubProjectController(SubProjectService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String showSubProjectForm(@PathVariable int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("subProject", new SubProject());

        return "subProjectCreate";
    }

    @PostMapping
    public String createSubProject(@PathVariable int projectId,
                           @ModelAttribute SubProject subProject) {
        subProject.setProjectId(projectId);
        service.createSubProject(subProject);

        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/delete/{subProjectId}")
    public String deleteSubProject(@PathVariable int projectId,
                                   @PathVariable int subProjectId,
                                   HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/profile/login";

        service.deleteSubProjectById(subProjectId);
        return "redirect:/projects/" + projectId;
    }
}
