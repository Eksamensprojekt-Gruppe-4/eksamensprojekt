package com.banditdev.eksamensprojekt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findProjectsByUserId() {
    }

    @Test
    void addProject() {
    }

    @Test
    void deleteProjectById() {
    }

    @Test
    void updateProject() {
    }

    @Test
    void findProjectById() {
    }

    @Test
    void addUserToProject() {
    }

    @Test
    void removeUserFromProject() {
    }

    @Test
    void removeAllUsersFromProject() {
    }

    @Test
    void findAllProjects() {
    }
}