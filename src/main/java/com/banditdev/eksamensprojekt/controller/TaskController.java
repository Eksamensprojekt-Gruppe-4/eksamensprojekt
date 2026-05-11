package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/projects/{projectId}/subprojects/{subProjectId}/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/add")
    public String showAddNewTaskForm(@PathVariable int projectId, @PathVariable int subProjectId, HttpSession session, Model model) {

        //TODO lav HttpSession logik!

        model.addAttribute("subProject", taskService.findSubProjectBySubProjectId(subProjectId));
        return "addTask";
    }

    @PostMapping("/add")
    public String saveNewTask(@ModelAttribute Task task, @PathVariable int projectId, @PathVariable int subProjectId, HttpSession session) {

        taskService.addTask(task, task.getUserId(), subProjectId);
        return "redirect:/projects/" + projectId + "/subprojects/" + subProjectId + "/";
    }

    //TODO  @GetMapping("/{taskId}/viewTask")
}
