package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.SubProject;
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
class SubProjectRepositoryTest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findSubProjectsByProjectId_shouldReturnTwoSubProjects() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();

        List<SubProject> result = subProjectRepository.findSubProjectsByProjectId(projectId);

        assertEquals(2, result.size());
    }

    @Test
    void findSubProjectBySubProjectId_shouldReturnSubProject() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        int subProjectId = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst().getSubProjectId();

        SubProject result = subProjectRepository.findSubProjectBySubProjectId(subProjectId);

        assertNotNull(result);
        assertEquals(subProjectId, result.getSubProjectId());
    }

    @Test
    void findSubProjectBySubProjectId_shouldReturnNull() {
        SubProject result = subProjectRepository.findSubProjectBySubProjectId(9999);

        assertNull(result);
    }

    @Test
    void saveSubProject_shouldExistSubProject() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();

        SubProject subProject = new SubProject();
        subProject.setSubProjectName("Test SubProject");
        subProject.setSubProjectDescription("Test Description");
        subProject.setSubProjectEstimatedHours(50.0);
        subProject.setSubProjectActualHours(0.0);
        subProject.setProjectId(projectId);

        subProjectRepository.saveSubProject(subProject);

        List<SubProject> result = subProjectRepository.findSubProjectsByProjectId(projectId);

        assertEquals(3, result.size());
    }

    @Test
    void deleteSubProjectById_shouldRemoveSubProject() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        int subProjectId = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst().getSubProjectId();

        subProjectRepository.deleteSubProjectById(subProjectId);

        assertNull(subProjectRepository.findSubProjectBySubProjectId(subProjectId));
    }

    @Test
    void updateSubProject_shouldUpdateNameAndDescription() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();
        SubProject existing = subProjectRepository.findSubProjectsByProjectId(projectId).getFirst();

        existing.setSubProjectName("Updated Name");
        existing.setSubProjectDescription("Updated Description");
        subProjectRepository.updateSubProject(existing);

        SubProject updated = subProjectRepository.findSubProjectBySubProjectId(existing.getSubProjectId());
        assertNotNull(updated);
        assertEquals("Updated Name", updated.getSubProjectName());
        assertEquals("Updated Description", updated.getSubProjectDescription());
    }
}