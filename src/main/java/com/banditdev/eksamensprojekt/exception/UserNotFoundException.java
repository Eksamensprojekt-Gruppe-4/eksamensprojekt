package com.banditdev.eksamensprojekt.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int userId) {
        super("User with the id: " + userId + ". Could not be found");
    }
    public UserNotFoundException(String username) {
        super("User with the name: " + username + ". could not be found");
    }
}
