package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public void deleteTaskByTaskId(int taskIdToDelete) {
        taskRepository.deleteTaskByTaskId(taskIdToDelete);
    }

    public List<Task> findTasksBySubProjectId(int subProjectId) {
        return taskRepository.findTasksBySubProjectId(subProjectId);
    }

    public Map<Integer, List<Task>> tasksBySubProject(List<SubProject> subProjects) {
        Map<Integer, List<Task>> tasksBySubProjectMap = new HashMap<>();

        for (SubProject sp : subProjects) {
            tasksBySubProjectMap.put(sp.getSubProjectId(), findTasksBySubProjectId(sp.getSubProjectId()));
        }

        return tasksBySubProjectMap;
    }
}
