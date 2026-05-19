package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.exception.TaskNotFoundException;
import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;
    @InjectMocks
    TaskService taskService;

    @Test
    void findTaskById_throwsWhenNotFound() {
        when(taskRepository.findTaskById(99)).thenReturn(null);
        assertThrows(TaskNotFoundException.class,
                () -> taskService.findTaskById(99));
    }

    @Test
    void tasksBySubProject_returnsMapKeyedBySubProjectId() {
        SubProject sp1 = new SubProject(1, "UI", "", 10, 5, 1);
        SubProject sp2 = new SubProject(2, "API", "", 20, 10, 1);
        Task t1 = new Task(1, "Task A", "", 5, 0, 1, 1);
        when(taskRepository.findTasksBySubProjectId(1)).thenReturn(List.of(t1));
        when(taskRepository.findTasksBySubProjectId(2)).thenReturn(List.of());

        Map<Integer, List<Task>> result = taskService.tasksBySubProject(List.of(sp1, sp2));

        assertEquals(1, result.get(1).size());
        assertTrue(result.get(2).isEmpty());
    }

    @Test
    void calculateEstimatedHoursForSubProject_sumsAllTaskHours() {
        Task t1 = new Task(1, "Task1", "", 8.0, 0, 1, 1);
        Task t2 = new Task(2, "Task2", "", 6.0, 0, 1, 1);
        Task t3 = new Task(3, "Task3", "", 4.0, 0, 1, 1);

        when(taskRepository.findTasksBySubProjectId(1)).thenReturn(List.of(t1, t2, t3));

        double result = taskService.calculateEstimatedHoursForSubProject(1);

        assertEquals(18.0, result);
    }
}
