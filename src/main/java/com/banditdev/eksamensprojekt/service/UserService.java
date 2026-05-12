package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}