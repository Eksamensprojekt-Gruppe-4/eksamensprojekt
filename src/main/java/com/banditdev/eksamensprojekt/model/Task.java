package com.banditdev.eksamensprojekt.model;

public class Task {

    private int taskId;
    private String taskName;
    private String taskDescription;
    private double taskEstimatedHours;
    private double taskActualHours;
    private int userId;
    private int subProjectId;

    public Task(int taskId, String taskName, String taskDescription, double taskEstimatedHours, double taskActualHours, int userId, int subProjectId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskEstimatedHours = taskEstimatedHours;
        this.taskActualHours = taskActualHours;
        this.userId = userId;
        this.subProjectId = subProjectId;
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

    public double getTaskEstimatedHours() {
        return taskEstimatedHours;
    }

    public void setTaskEstimatedHours(double taskEstimatedHours) {
        this.taskEstimatedHours = taskEstimatedHours;
    }

    public double getTaskActualHours() {
        return taskActualHours;
    }

    public void setTaskActualHours(double taskActualHours) {
        this.taskActualHours = taskActualHours;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskEstimatedHours=" + taskEstimatedHours +
                ", taskActualHours=" + taskActualHours +
                ", userId=" + userId +
                ", subProjectId=" + subProjectId +
                '}';
    }
}
