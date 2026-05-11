package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.repository.TaskRepository;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }



    public Task addTask(Task task, int userId, int subProjectId) {
        return taskRepository.addNewTask(task, userId, subProjectId);
    }


}
