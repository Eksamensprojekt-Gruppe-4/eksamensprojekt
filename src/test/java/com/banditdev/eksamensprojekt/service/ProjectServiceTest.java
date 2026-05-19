package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
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

    @Mock
    ProjectRepository projectRepository;
    @InjectMocks
    ProjectService projectService;

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
}