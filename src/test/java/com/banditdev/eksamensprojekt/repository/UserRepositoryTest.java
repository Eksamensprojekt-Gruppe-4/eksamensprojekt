package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.model.UserExperience;
import com.banditdev.eksamensprojekt.model.UserRole;
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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findAllUsers_shouldReturnFiveUsers() {
        List<User> result = userRepository.findAllUsers();

        assertEquals(5, result.size());
    }

    @Test
    void findUserByUserUsername_shouldReturnCorrectUser() {
        User result = userRepository.findUserByUserUsername("anders123");

        assertNotNull(result);
        assertEquals("Anders Nielsen", result.getUserName());
    }

    @Test
    void findUserByUserUsername_shouldReturnNull_whenNotFound() {
        User result = userRepository.findUserByUserUsername("something");

        assertNull(result);
    }

    @Test
    void findUserByUserUsername_shouldBeCaseInsensitive() {
        User result = userRepository.findUserByUserUsername("ANDERS123");

        assertNotNull(result);
        assertEquals("Anders Nielsen", result.getUserName());
    }

    @Test
    void findUserByUserId_shouldReturnCorrectUser() {
        int userId = userRepository.findUserByUserUsername("anders123").getUserId();

        User result = userRepository.findUserByUserId(userId);

        assertNotNull(result);
        assertEquals("anders123", result.getUserUsername());
    }

    @Test
    void findUserByUserId_shouldReturnNull() {
        User result = userRepository.findUserByUserId(9999);

        assertNull(result);
    }

    @Test
    void editOwnUser_shouldUpdateUsernameAndPassword() {
        User user = userRepository.findUserByUserUsername("anders123");
        user.setUserUsername("anders_updated");
        user.setUserPassword("1230");

        userRepository.editOwnUser(user);

        User updated = userRepository.findUserByUserId(user.getUserId());
        assertEquals("anders_updated", updated.getUserUsername());
        assertEquals("1230", updated.getUserPassword());
    }

    @Test
    void findUsersAssignedToProjectByProjectId_shouldReturnThreeUsers() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();

        List<User> result = userRepository.findUsersAssignedToProjectByProjectId(projectId);

        assertEquals(3, result.size());
    }

    @Test
    void findUserIdsAssignedToProjectByProjectId_shouldReturnCorrectIds() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();

        List<Integer> result = userRepository.findUserIdsAssignedToProjectByProjectId(projectId);

        assertEquals(3, result.size());
        assertTrue(result.contains(andersId));
    }

    @Test
    void findUserAssignedToTaskByTaskId_shouldReturnCorrectUser() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();

        List<User> assignedUsers = userRepository.findUsersAssignedToProjectByProjectId(projectId);
        assertFalse(assignedUsers.isEmpty());
    }

    @Test
    void returnOwnerOfProjectByProjectId_shouldReturnAnders() {
        int andersId = userRepository.findUserByUserUsername("anders123").getUserId();
        int projectId = projectRepository.findProjectsByUserId(andersId).getFirst().getProjectId();

        User result = userRepository.returnOwnerOfProjectByProjectId(projectId);

        assertNotNull(result);
        assertEquals("Anders Nielsen", result.getUserName());
    }

    @Test
    void returnOwnerOfProjectByProjectId_shouldReturnNull() {
        User result = userRepository.returnOwnerOfProjectByProjectId(9999);

        assertNull(result);
    }

    @Test
    void createUser_shouldIncreaseUserCountByOne() {
        User user = new User();
        user.setUserName("Test User");
        user.setUserUsername("testuser");
        user.setUserPassword("testpass");
        user.setUserRole(UserRole.EMPLOYEE);
        user.setUserExperience(UserExperience.JUNIOR);

        userRepository.createUser(user);

        List<User> result = userRepository.findAllUsers();
        assertEquals(6, result.size());
    }

    @Test
    void deleteUserById_shouldDecreaseUserCountByOne() {
        int userId = userRepository.findUserByUserUsername("lars271").getUserId();

        userRepository.deleteUserByUserId(userId);

        assertNull(userRepository.findUserByUserId(userId));
    }

    @Test
    void updateUserAsAdmin_shouldUpdateAllFields() {
        User user = userRepository.findUserByUserUsername("anders123");
        user.setUserName("Updated Name");
        user.setUserUsername("updated_username");
        user.setUserPassword("newpassword");
        user.setUserRole(UserRole.EMPLOYEE);
        user.setUserExperience(UserExperience.INTERN);

        userRepository.updateUserAsAdmin(user);

        User updated = userRepository.findUserByUserId(user.getUserId());
        assertEquals("Updated Name", updated.getUserName());
        assertEquals("updated_username", updated.getUserUsername());
        assertEquals("newpassword", updated.getUserPassword());
        assertEquals(UserRole.EMPLOYEE, updated.getUserRole());
        assertEquals(UserExperience.INTERN, updated.getUserExperience());
    }
}