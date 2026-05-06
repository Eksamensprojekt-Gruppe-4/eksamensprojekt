package com.banditdev.eksamensprojekt.model;

public class Project {
    private int projectId;
    private String projectName;
    private String projectDescription;
    private int projectEstimatedTimeMinutes;
    private int projectActualTimeMinutes;

    public Project(int projectId, String projectName, String projectDescription, int projectEstimatedTimeMinutes, int projectActualTimeMinutes) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectEstimatedTimeMinutes = projectEstimatedTimeMinutes;
        this.projectActualTimeMinutes = projectActualTimeMinutes;
    }


    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public int getProjectEstimatedTimeMinutes() {
        return projectEstimatedTimeMinutes;
    }

    public void setProjectEstimatedTimeMinutes(int projectEstimatedTimeMinutes) {
        this.projectEstimatedTimeMinutes = projectEstimatedTimeMinutes;
    }

    public int getProjectActualTimeMinutes() {
        return projectActualTimeMinutes;
    }

    public void setProjectActualTimeMinutes(int projectActualTimeMinutes) {
        this.projectActualTimeMinutes = projectActualTimeMinutes;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", projectEstimatedTimeMinutes=" + projectEstimatedTimeMinutes +
                ", projectActualTimeMinutes=" + projectActualTimeMinutes +
                '}';
    }
}
