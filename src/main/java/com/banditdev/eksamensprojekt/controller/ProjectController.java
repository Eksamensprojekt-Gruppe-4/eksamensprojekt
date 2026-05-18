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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;
    private final SubProjectService subProjectService;
    private final TaskService taskService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, SubProjectService subProjectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.subProjectService = subProjectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("myProjects")
    public String showOwnProjects(HttpSession session, Model model) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        List<Project> projects = projectService.findProjectsByUserId(currentLoggedInUser.getUserId());

        model.addAttribute("projects", projects);

        return "projectsOwnedOverview";
    }

    @GetMapping("/{projectId}")
    public String showProject(@PathVariable int projectId, HttpSession session, Model model) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        Project project = projectService.findProjectById(projectId);

        if (project == null) {
            return "redirect:/projects/myProjects";
        }

        List<SubProject> subProjects = subProjectService.findSubProjectsByProjectId(projectId);
        List<User> assignedUsers = userService.findUsersAssignedToProjectByProjectId(projectId);

        model.addAttribute("project", project);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("tasksBySubProject", taskService.tasksBySubProject(subProjects));
        model.addAttribute("assignedUsers", assignedUsers);
        model.addAttribute("usersById", userService.getUsersMappedById());

        return "projectView";
    }

    @GetMapping("/add")
    public String showAddProjectForm(Model model, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        model.addAttribute("project", new Project());
        model.addAttribute("allUsers",userService.findAllUsers());
        model.addAttribute("today", LocalDate.now());

        return "projectCreate";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute Project project,
                             @RequestParam(required = false) List<Integer> listOfUserIdsFromAssignedUsers,
                             HttpSession session,
                             Model model) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        Project createdProject;
        try {
            createdProject = projectService.addProject(project, currentLoggedInUser.getUserId());
            projectService.addAssignedUserIdsToDatabase(createdProject.getProjectId(), listOfUserIdsFromAssignedUsers);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("allUsers", userService.findAllUsers());
            model.addAttribute("today", LocalDate.now());
            return "projectCreate";
        }
        createdProject = projectService.addProject(project, currentLoggedInUser.getUserId());
        projectService.addAssignedUserIdsToDatabase(createdProject.getProjectId(), listOfUserIdsFromAssignedUsers);

        return "redirect:/projects/" + createdProject.getProjectId();
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        projectService.removeAllUsersFromProject(projectId);
        projectService.deleteProjectById(projectId);
        return "redirect:/projects/myProjects";
    }

    @GetMapping("/edit/{projectId}")
    public String showEditProject(@PathVariable int projectId, Model model, HttpSession session) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        model.addAttribute("project", projectService.findProjectById(projectId));
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("assignedUsersIds", userService.findUserIdsAssignedToProjectByProjectId(projectId));
        model.addAttribute("today", LocalDate.now());

        return "projectEdit";
    }

    @PostMapping("/edit/{projectId}")
    public String editProject(@PathVariable int projectId,
                              @RequestParam String projectName,
                              @RequestParam String projectDescription,
                              @RequestParam LocalDate projectStartDate, @RequestParam(required = false) List<Integer> listOfUserIdsFromAssignedUsers, HttpSession session, Model model) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        try {
            projectService.updateProject(projectId, projectName, projectDescription, projectStartDate);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("project", projectService.findProjectById(projectId));
            model.addAttribute("allUsers", userService.findAllUsers());
            model.addAttribute("assignedUsersIds", userService.findUserIdsAssignedToProjectByProjectId(projectId));
            model.addAttribute("today", LocalDate.now());
            return "projectEdit";
        }

        projectService.removeAllUsersFromProject(projectId);
        projectService.addAssignedUserIdsToDatabase(projectId, listOfUserIdsFromAssignedUsers);

        return "redirect:/projects/" + projectId;
    }

    @GetMapping("allProjects")
    public String showAllProjects(HttpSession session, Model model) {

        User currentLoggedInUser = (User) session.getAttribute("user");
        if (!userService.isUserLoggedIn(currentLoggedInUser)) {
            return "redirect:/profile/login";
        }

        List<Project> projects = projectService.findAllProjects();

        model.addAttribute("projects", projects);
        model.addAttribute("ownersByProject", userService.getOwnersByProjectIdMap(projects));

        return "projectsAllOverview";
    }
}
