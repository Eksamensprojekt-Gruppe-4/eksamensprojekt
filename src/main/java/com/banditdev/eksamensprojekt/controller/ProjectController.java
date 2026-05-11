package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public String addProject(@RequestParam String projectName,
                             @RequestParam String projectDescription,
                             @RequestParam LocalDate projectStartDate,
                             HttpSession session) {

        User currentUser = (User) session.getAttribute("user");

        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectDescription(projectDescription);
        project.setProjectStartDate(projectStartDate);

        projectService.addProject(project, currentUser.getUserId());

        return"redirect:/project";
    }
}
