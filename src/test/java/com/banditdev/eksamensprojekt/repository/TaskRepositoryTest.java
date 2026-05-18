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
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void addNewTask() {
    }

    @Test
    void findTaskById() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void findTasksBySubProjectId() {
    }

    @Test
    void deleteTaskByTaskId() {
    }
}