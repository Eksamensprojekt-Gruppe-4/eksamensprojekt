package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllProjects_shouldReturnAllThreeProjects() {
        List<Project> result = projectRepository.findAllProjects();

        assertEquals(3, result.size());
    }

    @Test
    void findProjectsByUserId_shouldReturnTwoProjects() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();

        List<Project> result = projectRepository.findProjectsByUserId(andersId);

        assertEquals(2, result.size());
    }

    @Test
    void findProjectsByUserId_shouldReturnOneProject() {
        int sofieId = userRepository.findUserByUserUsername("sofie953").getUserId();

        List<Project> result = projectRepository.findProjectsByUserId(sofieId);

        assertEquals(1, result.size());
        assertEquals("Intern HR System", result.getFirst().getProjectName());
    }

    @Test
    void findProjectById_shouldReturnProject() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        List<Project> projects = projectRepository.findProjectsByUserId(andersId);
        int projectId = projects.getFirst().getProjectId();

        Project result = projectRepository.findProjectById(projectId);

        assertNotNull(result);
        assertEquals(projectId, result.getProjectId());
    }

    @Test
    void findProjectById_shouldReturnNull() {
        Project result = projectRepository.findProjectById(99999);

        assertNull(result);
    }

    @Test
    void addProject_shouldReturnWithGeneratedIdAndProject() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();

        Project project = new Project();
        project.setProjectName("Test Project");
        project.setProjectDescription("Test Description");
        project.setProjectStartDate(LocalDate.now());
        project.setProjectEstimatedHours(100.0);
        project.setProjectActualHours(0.0);

        Project result = projectRepository.addProject(project, andersId);

        assertNotNull(result);
        assertTrue(result.getProjectId() > 0);
        assertEquals("Test Project", result.getProjectName());

        Project fromDb = projectRepository.findProjectById(result.getProjectId());
        assertNotNull(fromDb);
        assertEquals("Test Project", fromDb.getProjectName());
    }

    @Test
    void deleteProjectById_shouldRemoveProject() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();

        Project project = new Project();
        project.setProjectName("To Be Deleted");
        project.setProjectDescription("desc");
        project.setProjectStartDate(LocalDate.now());
        project.setProjectEstimatedHours(10.0);
        project.setProjectActualHours(0.0);
        Project created = projectRepository.addProject(project, andersId);

        projectRepository.deleteProjectById(created.getProjectId());

        assertNull(projectRepository.findProjectById(created.getProjectId()));
    }

    @Test
    void updateProject_shouldUpdateDetails() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        List<Project> projects = projectRepository.findProjectsByUserId(andersId);
        Project existing = projects.getFirst();

        LocalDate newDate = LocalDate.now().plusDays(1);
        projectRepository.updateProject(existing.getProjectId(), "Updated Name", "Updated Desc", newDate);

        Project updated = projectRepository.findProjectById(existing.getProjectId());
        assertNotNull(updated);
        assertEquals("Updated Name", updated.getProjectName());
        assertEquals("Updated Desc", updated.getProjectDescription());
        assertEquals(newDate, updated.getProjectStartDate());
    }

    @Test
    void removeAllUsersFromProject_shouldClearAssignedUsers() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        List<Project> projects = projectRepository.findProjectsByUserId(andersId);
        int projectId = projects.getFirst().getProjectId();

        projectRepository.removeAllUsersFromProject(projectId);

        List<User> remaining = userRepository.findUsersAssignedToProjectByProjectId(projectId);
        assertEquals(0, remaining.size());
    }
}