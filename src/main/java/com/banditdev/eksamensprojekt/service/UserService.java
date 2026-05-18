package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.*;
import com.banditdev.eksamensprojekt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUser(User user) {
        userRepository.editOwnUser(user);
    }

    public User findUserByUserUsername(String userUsername) {
        return userRepository.findUserByUserUsername(userUsername);
    }

    public boolean validateUser(String userUsername, String userPassword) {
        User user = userRepository.findUserByUserUsername(userUsername);
        return user != null && user.getUserPassword().equals(userPassword);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User findUserAssignedToTaskByTaskId(int taskId) {
        return userRepository.findUserAssignedToTaskByTaskId(taskId);
    }

    public User findUserByUserId(int userIdToFind) {
        return userRepository.findUserByUserId(userIdToFind);
    }

    public List<User> findUsersAssignedToProjectByProjectId(int projectId) {
        return userRepository.findUsersAssignedToProjectByProjectId(projectId);
    }

    public List<Integer> findUserIdsAssignedToProjectByProjectId(int projectId) {
        return userRepository.findUserIdsAssignedToProjectByProjectId(projectId);
    }

    public Map<Integer, User> getUsersMappedById() {
        Map<Integer, User> usersByUserIdMap = new HashMap<>();

        for (User user : findAllUsers()) {
            usersByUserIdMap.put(user.getUserId(), user);
        }

        return usersByUserIdMap;
    }

    public Map<Integer, User> getOwnersByProjectIdMap(List<Project> allProjects) {

        Map<Integer, User> ownersByProject = new HashMap<>();
        for (Project project : allProjects) {
            ownersByProject.put(project.getProjectId(), findUserByUserId(project.getOwnerUserId()));
        }

        return ownersByProject;
    }

    public User updateUserProfile(int userId, User formUser) {

        User currentUserFromDatabase = userRepository.findUserByUserId(userId);

        currentUserFromDatabase.setUserUsername(formUser.getUserUsername());

        if (formUser.getUserPassword() != null &&
                !formUser.getUserPassword().trim().isEmpty()) {
            currentUserFromDatabase.setUserPassword(formUser.getUserPassword());
        }

        userRepository.editOwnUser(currentUserFromDatabase);

        return currentUserFromDatabase;
    }

    public boolean validateUserIsManager(User user) {
        return user.getUserRole() == UserRole.MANAGER;
    }

    public boolean validateUserIsAdmin(User user) {
        return user.getUserRole() == UserRole.ADMIN;
    }

    public boolean validateUserIsProjectOwner(int userId, int projectId) {
        User owner = userRepository.returnOwnerOfProjectByProjectId(projectId);
        return owner != null && (owner.getUserId() == userId);
    }

    public boolean isUserLoggedIn(User user) {
        return user != null;
    }
}