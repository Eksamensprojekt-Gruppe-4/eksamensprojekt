package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.service.ProjectService;
import com.banditdev.eksamensprojekt.service.SubProjectService;
import com.banditdev.eksamensprojekt.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
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

    public ProjectController(ProjectService projectService, SubProjectService subProjectService, TaskService taskService) {
        this.projectService = projectService;
        this.subProjectService = subProjectService;
        this.taskService = taskService;
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

        /* User authentication check possible?
        if (currentLoggedInUser == null) {
            return "redirect:/profile/login";
        } */

        Project project = projectService.findProjectById(projectId);
        List<SubProject> subProjects = subProjectService.findSubProjectsByProjectId(projectId);

        Map<Integer, List<Task>> tasksBySubProject = new HashMap<>();
        for (SubProject sp : subProjects) {
            tasksBySubProject.put(sp.getSubProjectId(), taskService.findTasksBySubProjectId(sp.getSubProjectId()));
        }


        /* Possible user security check? Check if currentLoggedInUser is either asigned or owner of the project
        he is trying to access.

        if (!projectService.validateProjectOwnerOrAsignees(currentLoggedInUser, projectId)) {
           return "redirect:/projects/myProjects";
        } */

        if (project == null) {
            return "redirect:/projects/myProjects";
        }

        model.addAttribute("project", project);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("tasksBySubProject", tasksBySubProject);


        /* How to show task for specific subproject via. HashMap in the Thymeleaf.

        <div th:each="sp : ${subProjects}">
                <h3 th:text="${sp.subProjectName}"></h3>
                <div th:each="task : ${tasksBySubProject[sp.subProjectId]}">
                <p th:text="${task.taskName}"></p>
                </div>
                </div>
         */


        return "projectView";
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

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:user/login";
        }

        projectService.deleteProjectById(projectId);
        return "redirect:/projects/myProjects";
    }

    @GetMapping("/edit/{projectId}")
    public String showEditProject(@PathVariable int projectId, Model model) {
        model.addAttribute("project", projectService.findProjectById(projectId));
        return "projectEdit";
    }

    @PostMapping("/edit/{projectId}")
    public String editProject(@PathVariable int projectId,
                              @RequestParam String projectName,
                              @RequestParam String projectDescription,
                              @RequestParam LocalDate projectStartDate) {
        projectService.updateProject(projectId, projectName, projectDescription, projectStartDate);
        return "redirect:/projects/myProjects";
    }
}
