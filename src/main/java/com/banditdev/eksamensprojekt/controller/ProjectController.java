package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("myProjects")
    public String showOwnProjects(HttpSession session, Model model) {
        User currentLoggedInUser = (User) session.getAttribute("user");

        if (currentLoggedInUser == null) {
            return "redirect:/profile/login";
        }

        List<Project> projects = projectService.findProjectsByUserId(currentLoggedInUser.getUserId());

        model.addAttribute("projects", projects);

        return "projectsOwnedOverview";
    }

    @GetMapping("/add")
    public String showAddProjectForm() {
        return "projectCreate";
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
        project.setProjectEstimatedDeadline(null);
        project.setProjectEstimatedHours(0);
        project.setProjectActualHours(0);

        projectService.addProject(project, currentUser.getUserId());

        return "redirect:/projects/myProjects";
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {

        User user =(User) session.getAttribute("user");
        if (user == null)return "redirect:user/login";

        projectService.deleteProjectById(projectId);
        return "redirect:/projects/myProjects";
    }
}
