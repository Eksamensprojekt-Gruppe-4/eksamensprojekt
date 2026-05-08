package com.banditdev.eksamensprojekt.model;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String projectName;
    private String projectDescription;
    private LocalDate projectStartDate;
    private LocalDate projectEstimatedDeadline;
    private double projectEstimatedHours;
    private double projectActualHours;
    private int ownerUserId;

    public Project(){}

    public Project(int projectId, String projectName, String projectDescription, LocalDate projectStartDate, LocalDate projectEstimatedDeadline, double projectEstimatedHours, double projectActualHours, int ownerUserId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStartDate = projectStartDate;
        this.projectEstimatedDeadline = projectEstimatedDeadline;
        this.projectEstimatedHours = projectEstimatedHours;
        this.projectActualHours = projectActualHours;
        this.ownerUserId = ownerUserId;
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

    public LocalDate getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(LocalDate projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public LocalDate getProjectEstimatedDeadline() {
        return projectEstimatedDeadline;
    }

    public void setProjectEstimatedDeadline(LocalDate projectEstimatedDeadline) {
        this.projectEstimatedDeadline = projectEstimatedDeadline;
    }

    public double getProjectEstimatedHours() {
        return projectEstimatedHours;
    }

    public void setProjectEstimatedHours(double projectEstimatedHours) {
        this.projectEstimatedHours = projectEstimatedHours;
    }

    public double getProjectActualHours() {
        return projectActualHours;
    }

    public void setProjectActualHours(double projectActualHours) {
        this.projectActualHours = projectActualHours;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", projectStartDate=" + projectStartDate +
                ", projectEstimatedDeadline=" + projectEstimatedDeadline +
                ", projectEstimatedHours=" + projectEstimatedHours +
                ", projectActualHours=" + projectActualHours +
                ", ownerUserId=" + ownerUserId +
                '}';
    }
}
