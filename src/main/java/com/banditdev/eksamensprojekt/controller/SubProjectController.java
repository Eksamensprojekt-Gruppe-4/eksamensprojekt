package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.service.ProjectService;
import com.banditdev.eksamensprojekt.service.SubProjectService;
import com.banditdev.eksamensprojekt.service.TaskService;
import com.banditdev.eksamensprojekt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/projects/{projectId}/subprojects")
public class SubProjectController {
    private final SubProjectService service;
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public SubProjectController(SubProjectService service, TaskService taskService, ProjectService projectService, UserService userService) {
        this.service = service;
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String showSubProjectForm(@PathVariable int projectId, Model model, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser) || !userService.canEditProject(currentLoggedInUser, projectId)) {
            return "redirect:/profile/login";
        }

        model.addAttribute("projectId", projectId);
        model.addAttribute("subProject", new SubProject());

        return "subProjectCreate";
    }

    @PostMapping
    public String createSubProject(@PathVariable int projectId,
                           @ModelAttribute SubProject subProject, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser) || !userService.canEditProject(currentLoggedInUser, projectId)) {
            return "redirect:/profile/login";
        }

        subProject.setProjectId(projectId);
        service.createSubProject(subProject);

        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{subProjectId}/delete")
    public String deleteSubProject(@PathVariable int projectId,
                                   @PathVariable int subProjectId, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser) || !userService.canEditProject(currentLoggedInUser, projectId)) {
            return "redirect:/profile/login";
        }

        service.deleteSubProjectById(subProjectId);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{subProjectId}/edit")
    public String showEditProject(@PathVariable int projectId,
                                  @PathVariable int subProjectId,
                                  Model model, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser) || !userService.canEditProject(currentLoggedInUser, projectId)) {
            return "redirect:/profile/login";
        }

        model.addAttribute("subProject", service.findSubProjectBySubProjectId(subProjectId));
        model.addAttribute("projectId", projectId);
        return "editSubProject";
    }

    @PostMapping("/{subProjectId}/edit")
    public String editSubProject(@PathVariable int projectId,
                                 @PathVariable int subProjectId,
                                 @ModelAttribute SubProject subProject, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser) || !userService.canEditProject(currentLoggedInUser, projectId)) {
            return "redirect:/profile/login";
        }

        subProject.setSubProjectId(subProjectId);
        subProject.setProjectId(projectId);
        service.updateSubProject(subProject);
        return "redirect:/projects/" + projectId + "/subprojects/" + subProjectId;
    }

    @GetMapping("/{subProjectId}")
    public String showSubProject(@PathVariable int projectId, HttpSession session, @PathVariable int subProjectId, Model model) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        Project project = projectService.findProjectById(projectId);
        SubProject subProject = service.findSubProjectBySubProjectId(subProjectId);
        List<Task> tasks = taskService.findTasksBySubProjectId(subProjectId);

        model.addAttribute("project", project);
        model.addAttribute("subProjects", subProject);
        model.addAttribute("tasksBySubProject", tasks);
        model.addAttribute("usersById", userService.getUsersMappedById());


        if (userService.canEditProject(currentLoggedInUser,projectId)) {
            return "subProjectView";
        } else {
            return "subProjectViewNoEdit";
        }
    }

}
