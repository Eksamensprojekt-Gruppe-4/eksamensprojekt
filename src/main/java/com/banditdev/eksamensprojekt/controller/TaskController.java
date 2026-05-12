package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.service.SubProjectService;
import com.banditdev.eksamensprojekt.service.TaskService;
import com.banditdev.eksamensprojekt.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/subprojects/{subProjectId}/task")
public class TaskController {
    private final TaskService taskService;
    private final SubProjectService subProjectService;
    private final UserService userService;

    public TaskController(TaskService taskService, SubProjectService subProjectService, UserService userService) {
        this.taskService = taskService;
        this.subProjectService = subProjectService;
        this.userService = userService;
    }


    @GetMapping("/add")
    public String showAddNewTaskForm(@PathVariable int projectId, @PathVariable int subProjectId, HttpSession session, Model model) {

        //TODO lav HttpSession logik!

        model.addAttribute("task", new Task());
        model.addAttribute("subProject", subProjectService.findSubProjectBySubProjectId(subProjectId));
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("projectId", projectId);
        return "addTask";
    }

    @PostMapping("/add")
    public String saveNewTask(@ModelAttribute Task task, @PathVariable int projectId, @PathVariable int subProjectId, HttpSession session) {

        taskService.addTask(task, task.getUserId(), subProjectId);
        //måske lav et return til "viewTask" i stedet for ? Måske er det dårligt userflow.
        return "redirect:/projects/" + projectId + "/subprojects/" + subProjectId + "/";
    }

    @GetMapping("/{taskId}/view")
    public String viewTask(@PathVariable int taskId, @PathVariable int projectId, @PathVariable int subProjectId, HttpSession session, Model model) {

            model.addAttribute("task", taskService.findTaskById(taskId));
            model.addAttribute("subProject", subProjectService.findSubProjectBySubProjectId(subProjectId));
            model.addAttribute("assignedUser", userService.findUserAssignedToTaskByTaskId(taskId));
            model.addAttribute("projectId", projectId);

            return "viewTask";
    }

    @GetMapping("/{taskId}/edit")
    public String editTask(@PathVariable int projectId, @PathVariable int subProjectId, @PathVariable int taskId, HttpSession session, Model model) {

        model.addAttribute("task", taskService.findTaskById(taskId));
        model.addAttribute("subProject", subProjectService.findSubProjectBySubProjectId(subProjectId));
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("projectId", projectId);

        return "editTask";
    }

    @PostMapping("/{taskId}/edit")
        public String updateTask(@ModelAttribute Task task, @PathVariable int projectId, @PathVariable int subProjectId, @PathVariable int taskId, HttpSession session) {

            task.setTaskId(taskId);
            task.setSubProjectId(subProjectId);
            taskService.updateTask(task);
            return "redirect:/projects/" + projectId + "/subprojects/" + subProjectId + "/task/" + taskId + "/view";
    }

    @PostMapping("{taskId}/delete")
    public String deleteTaskByTaskId(@PathVariable int taskId, )
}
