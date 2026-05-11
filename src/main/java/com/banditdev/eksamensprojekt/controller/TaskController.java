package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.service.SubProjectService;
import com.banditdev.eksamensprojekt.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/projects/{projectId}/subprojects/{subProjectId}/task")
public class TaskController {
    private final TaskService taskService;
    private final SubProjectService subProjectService;

    public TaskController(TaskService taskService, SubProjectService subProjectService) {
        this.taskService = taskService;
        this.subProjectService = subProjectService;
    }

    @GetMapping("/add")
    public String showAddNewTaskForm(@PathVariable int projectId, @PathVariable int subProjectId, HttpSession session, Model model) {

        //TODO lav HttpSession logik!

        model.addAttribute("subProject", subProjectService.findSubProjectBySubProjectId(subProjectId));
        //TODO model.addAttribute("users", taskService.findAllUsers());
        model.addAttribute("projectId", projectId);
        return "addTask";
    }

    @PostMapping("/add")
    public String saveNewTask(@ModelAttribute Task task, @PathVariable int projectId, @PathVariable int subProjectId, HttpSession session) {

        taskService.addTask(task, task.getUserId(), subProjectId);
        //måske lav et return til "viewTask" i stedet for ? Måske er det dårligt userflow.
        return "redirect:/projects/" + projectId + "/subprojects/" + subProjectId + "/";
    }

    //TODO  @GetMapping("/{taskId}/viewTask") - giver det mening at lave denne metode???
}
