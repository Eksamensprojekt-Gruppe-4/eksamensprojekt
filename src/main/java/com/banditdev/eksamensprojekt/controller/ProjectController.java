package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Manager;
import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
@Controller
@RequestMapping("project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public String addProject(@RequestParam String projectName, HttpSession session) {
        Manager currentManager = (Manager) session.getAttribute("manager");

        Project project = new Project();
        project.setProjectName(projectName);

        projectService.addProject(project, currentManager.getManagerId());

        return"redirect:/project";
    }
}
