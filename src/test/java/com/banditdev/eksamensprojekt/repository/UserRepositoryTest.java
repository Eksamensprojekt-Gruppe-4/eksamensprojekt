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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void editOwnUser() {
    }

    @Test
    void findUserByUserUsername() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void findUserAssignedToTaskByTaskId() {
    }

    @Test
    void findUserByUserId() {
    }

    @Test
    void findUsersAssignedToProjectByProjectId() {
    }

    @Test
    void findUserIdsAssignedToProjectByProjectId() {
    }
}