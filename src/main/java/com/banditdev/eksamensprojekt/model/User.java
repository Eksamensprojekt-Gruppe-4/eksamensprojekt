package com.banditdev.eksamensprojekt.model;

public class User {

    private int userId;
    private String userName;
    private String userUsername;
    private String userPassword;
    private UserExperience userExperience;
    private UserRole userRole;

    public User(int userId, String userName, String userUsername, String userPassword, UserExperience userExperience, UserRole userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userUsername = userUsername;
        this.userPassword = userPassword;
        this.userExperience = userExperience;
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserExperience getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(UserExperience userExperience) {
        this.userExperience = userExperience;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userFullName='" + userName + '\'' +
                ", userUsername='" + userUsername + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
