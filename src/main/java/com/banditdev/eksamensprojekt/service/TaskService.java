package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task, int userId, int subProjectId) {
        taskRepository.addNewTask(task, userId, subProjectId);
    }

    public Task findTaskById(int taskId) {
        return taskRepository.findTaskById(taskId);
    }
}
