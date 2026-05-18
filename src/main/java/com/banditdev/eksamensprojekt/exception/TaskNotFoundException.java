package com.banditdev.eksamensprojekt.exception;

public class TaskNotFoundException extends RuntimeException     {
    public TaskNotFoundException(int taskId) {
        super("Task not found with id:" + taskId);
    }
}
