package com.banditdev.eksamensprojekt.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(int projectIp) {
        super("Project not found with id: " + projectIp);
    }
}
