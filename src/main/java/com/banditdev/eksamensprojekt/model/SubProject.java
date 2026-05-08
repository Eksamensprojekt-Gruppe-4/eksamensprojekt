package com.banditdev.eksamensprojekt.model;

public class SubProject {

    private int subProjectId;
    private String subProjectName;
    private String subProjectDescription;
    private double subProjectEstimatedHours;
    private double subProjectActualHours;
    private int projectId;

    public SubProject() {}

    public SubProject(int subProjectId, String subProjectName, String subProjectDescription, double subProjectEstimatedHours, double subProjectActualHours, int projectId) {
        this.subProjectId = subProjectId;
        this.subProjectName = subProjectName;
        this.subProjectDescription = subProjectDescription;
        this.subProjectEstimatedHours = subProjectEstimatedHours;
        this.subProjectActualHours = subProjectActualHours;
        this.projectId = projectId;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getSubProjectDescription() {
        return subProjectDescription;
    }

    public void setSubProjectDescription(String subProjectDescription) {
        this.subProjectDescription = subProjectDescription;
    }

    public double getSubProjectEstimatedHours() {
        return subProjectEstimatedHours;
    }

    public void setSubProjectEstimatedHours(double subProjectEstimatedHours) {
        this.subProjectEstimatedHours = subProjectEstimatedHours;
    }

    public double getSubProjectActualHours() {
        return subProjectActualHours;
    }

    public void setSubProjectActualHours(double subProjectActualHours) {
        this.subProjectActualHours = subProjectActualHours;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "SubProject{" +
                "subProjectId=" + subProjectId +
                ", subProjectName='" + subProjectName + '\'' +
                ", subProjectDescription='" + subProjectDescription + '\'' +
                ", subProjectEstimatedHours=" + subProjectEstimatedHours +
                ", subProjectActualHours=" + subProjectActualHours +
                ", projectId=" + projectId +
                '}';
    }
}
