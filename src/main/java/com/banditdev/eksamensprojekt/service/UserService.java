package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.model.User;
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
}