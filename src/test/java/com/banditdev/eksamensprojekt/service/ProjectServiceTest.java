package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
import com.banditdev.eksamensprojekt.repository.SubProjectRepository;
import com.banditdev.eksamensprojekt.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock ProjectRepository projectRepository;
    @Mock
    SubProjectRepository subProjectRepository;
    @Mock
    TaskRepository taskRepository;
    @InjectMocks ProjectService projectService;

    @Test
    void addProject_throwsWhenStartDateIsInPast() {
        Project p = new Project();
        p.setProjectStartDate(LocalDate.now().minusDays(1));
        assertThrows(IllegalArgumentException.class,
                () -> projectService.addProject(p, 1));
    }

    @Test
    void addProject_succeedsWhenStartDateIsToday() {
        Project p = new Project();
        p.setProjectStartDate(LocalDate.now());
        when(projectRepository.addProject(p, 1)).thenReturn(p);
        assertDoesNotThrow(() -> projectService.addProject(p, 1));
    }

    @Test
    void findProjectById_returnsProjectWhenFound() {
        Project p = new Project(1, "Test", "", LocalDate.now(),
                null, 0, 0, 1);
        when(projectRepository.findProjectById(1)).thenReturn(p);
        assertEquals(p, projectService.findProjectById(1));
    }

    @Test
    void updateProject_throwsWhenNewDateIsInPast() {
        LocalDate originalDate = LocalDate.now();
        LocalDate newPastDate = LocalDate.now().minusDays(3);
        Project existing = new Project(1, "Old", "", originalDate,
                null, 0, 0, 1);
        when(projectRepository.findProjectById(1)).thenReturn(existing);
        assertThrows(IllegalArgumentException.class, () ->
                projectService.updateProject(1, "New", "Desc", newPastDate));
    }

    @Test
    void addAssignedUserIdsToDatabase_addsEachUserId() {
        projectService.addAssignedUserIdsToDatabase(1, List.of(2, 3, 4));
        verify(projectRepository).addUserToProject(1, 2);
        verify(projectRepository).addUserToProject(1, 3);
        verify(projectRepository).addUserToProject(1, 4);
    }

    @Test
    void calculateEstimatedEndDate_skipsWeekends() {
        Project project = new Project(1, "Test", "", LocalDate.of(2026, 5, 22), null, 0, 0, 1);
        SubProject sp = new SubProject(1, "SP", "", 0, 0, 1);
        Task t1 = new Task(1, "Task", "", 8.0, 0, 1, 1);
        Task t2 = new Task(2, "Task2", "", 8.0, 0, 1, 1);

        when(projectRepository.findProjectById(1)).thenReturn(project);
        when(subProjectRepository.findSubProjectsByProjectId(1)).thenReturn(List.of(sp));
        when(taskRepository.findTasksBySubProjectId(1)).thenReturn(List.of(t1, t2));

        LocalDate result = projectService.calculateEstimatedEndDate(1);

        assertEquals(LocalDate.of(2026, 5, 26), result);
    }


}