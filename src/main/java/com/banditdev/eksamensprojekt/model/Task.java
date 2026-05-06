package com.banditdev.eksamensprojekt.model;

public class Task {

    private int taskId;
    private String taskName;
    private String taskDescription;
    private int taskEstimatedTimeMinutes;
    private int taskActualTimeMinutes;

    public Task(int taskId, String taskName, String taskDescription, int taskEstimatedTimeMinutes, int taskActualTimeMinutes) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskEstimatedTimeMinutes = taskEstimatedTimeMinutes;
        this.taskActualTimeMinutes = taskActualTimeMinutes;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskEstimatedTimeMinutes() {
        return taskEstimatedTimeMinutes;
    }

    public void setTaskEstimatedTimeMinutes(int taskEstimatedTimeMinutes) {
        this.taskEstimatedTimeMinutes = taskEstimatedTimeMinutes;
    }

    public int getTaskActualTimeMinutes() {
        return taskActualTimeMinutes;
    }

    public void setTaskActualTimeMinutes(int taskActualTimeMinutes) {
        this.taskActualTimeMinutes = taskActualTimeMinutes;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskEstimatedTimeMinutes=" + taskEstimatedTimeMinutes +
                ", taskActualTimeMinutes=" + taskActualTimeMinutes +
                '}';
    }

}
