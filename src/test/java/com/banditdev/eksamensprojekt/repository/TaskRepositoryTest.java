package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findTasksBySubProjectId_shouldReturnTwoTasks() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        int subProjectId = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst().getSubProjectId();

        List<Task> result = taskRepository.findTasksBySubProjectId(subProjectId);

        assertEquals(2, result.size());
    }

    @Test
    void findTaskById_shouldReturnCorrectTask() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        int subProjectId = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst().getSubProjectId();
        int taskId = taskRepository.findTasksBySubProjectId(subProjectId).getFirst().getTaskId();

        Task result = taskRepository.findTaskById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getTaskId());
    }

    @Test
    void findTaskById_shouldReturnNull() {
        Task result = taskRepository.findTaskById(9999);

        assertNull(result);
    }

    @Test
    void addNewTask_shouldExistTask() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        int subProjectId = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst().getSubProjectId();

        Task task = new Task();
        task.setTaskName("New Test Task");
        task.setTaskDescription("Test description");
        task.setTaskEstimatedHours(5.0);
        task.setTaskActualHours(0.0);
        task.setUserId(andersId);

        taskRepository.addNewTask(task, andersId, subProjectId);

        List<Task> result = taskRepository.findTasksBySubProjectId(subProjectId);

        assertEquals(3, result.size());
    }

    @Test
    void deleteTaskByTaskId_shouldRemoveTask() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        int subProjectId = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst().getSubProjectId();
        int taskId = taskRepository.findTasksBySubProjectId(subProjectId).getFirst().getTaskId();

        taskRepository.deleteTaskByTaskId(taskId);

        assertNull(taskRepository.findTaskById(taskId));
    }

    @Test
    void updateTask_shouldUpdateNameAndHours() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        int subProjectId = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst().getSubProjectId();
        Task existing = taskRepository.findTasksBySubProjectId(subProjectId).getFirst();

        existing.setTaskName("Updated Task Name");
        existing.setTaskActualHours(12.0);
        taskRepository.updateTask(existing);

        Task updated = taskRepository.findTaskById(existing.getTaskId());
        assertNotNull(updated);
        assertEquals("Updated Task Name", updated.getTaskName());
        assertEquals(12.0, updated.getTaskActualHours());
    }
}