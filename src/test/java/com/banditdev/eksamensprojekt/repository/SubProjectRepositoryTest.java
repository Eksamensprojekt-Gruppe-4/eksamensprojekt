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
class SubProjectRepositoryTest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Test
    void saveSubProject() {
    }

    @Test
    void findSubProjectBySubProjectId() {
    }

    @Test
    void findSubProjectsByProjectId() {
    }

    @Test
    void deleteSubProjectById() {
    }

    @Test
    void updateSubProject() {
    }
}