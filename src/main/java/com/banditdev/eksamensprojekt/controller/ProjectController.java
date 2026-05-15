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

        if (currentLoggedInUser == null) {
            return "redirect:/profile/login";
        }

        List<Project> projects = projectService.findProjectsByUserId(currentLoggedInUser.getUserId());

        model.addAttribute("projects", projects);

        return "projectsOwnedOverview";
    }

    @GetMapping("/{projectId}")
    public String showProject(@PathVariable int projectId, HttpSession session, Model model) {

        User currentLoggedInUser = (User) session.getAttribute("user");

        Project project = projectService.findProjectById(projectId);
        List<SubProject> subProjects = subProjectService.findSubProjectsByProjectId(projectId);
        List<User> assignedUsers = userService.findUsersAssignedToProjectByProjectId(projectId);


        Map<Integer, List<Task>> tasksBySubProject = new HashMap<>();
        for (SubProject sp : subProjects) {
            tasksBySubProject.put(sp.getSubProjectId(), taskService.findTasksBySubProjectId(sp.getSubProjectId()));
        }

        if (project == null) {
            return "redirect:/projects/myProjects";
        }

        model.addAttribute("project", project);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("tasksBySubProject", tasksBySubProject);
        model.addAttribute("assignedUsers", assignedUsers);

        return "projectView";
    }


    @GetMapping("/add")
    public String showAddProjectForm(Model model) {

        model.addAttribute("project", new Project());
        model.addAttribute("allUsers",userService.findAllUsers());

        return "projectCreate";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute Project project, @RequestParam(required = false) List<Integer> listOfUserIdsFromAssignedUsers, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");

        Project createdProject = projectService.addProject(project, currentUser.getUserId());
        projectService.addAssignedUserIdsToDatabase(createdProject.getProjectId(), listOfUserIdsFromAssignedUsers);

        return "redirect:/projects/" + createdProject.getProjectId();
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:user/login";
        }

        projectService.removeAllUsersFromProject(projectId);
        projectService.deleteProjectById(projectId);
        return "redirect:/projects/myProjects";
    }

    @GetMapping("/edit/{projectId}")
    public String showEditProject(@PathVariable int projectId, Model model) {
        model.addAttribute("project", projectService.findProjectById(projectId));
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("assignedUsersIds", userService.findUserIdsAssignedToProjectByProjectId(projectId));

        return "projectEdit";
    }

    @PostMapping("/edit/{projectId}")
    public String editProject(@PathVariable int projectId,
                              @RequestParam String projectName,
                              @RequestParam String projectDescription,
                              @RequestParam LocalDate projectStartDate, @RequestParam(required = false) List<Integer> listOfUserIdsFromAssignedUsers) {
        projectService.updateProject(projectId, projectName, projectDescription, projectStartDate);

        projectService.removeAllUsersFromProject(projectId);
        projectService.addAssignedUserIdsToDatabase(projectId, listOfUserIdsFromAssignedUsers);

        return "redirect:/projects/" + projectId;
    }

    @GetMapping("allProjects")
    public String showAllProjects(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/profile/login";
        }

        List<Project> projects = projectService.findAllProjects();

        Map<Integer, User> ownersByProject = new HashMap<>();
        for(Project project: projects){
            ownersByProject.put(project.getProjectId(), userService.findUserByUserId(project.getOwnerUserId()));
        }
        model.addAttribute("projects", projects);
        model.addAttribute("ownersByProject", ownersByProject);

        return "projectsAllOverview";
    }
}
