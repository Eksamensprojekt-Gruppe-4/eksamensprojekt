package com.banditdev.eksamensprojekt.model;

public class SubProject {

    private int subProjectId;
    private String subProjectName;
    private String subProjectDescription;
    private int subProjectEstimatedTimeMinutes;
    private int subProjectActualTimeMinutes;
    private int projectId;

    public SubProject() {}

    public SubProject(int subProjectId, String subProjectName,
                      String subProjectDescription,
                      int subProjectEstimatedTimeMinutes,
                      int subProjectActualTimeMinutes,
                      int projectId) {
        this.subProjectId = subProjectId;
        this.subProjectName = subProjectName;
        this.subProjectDescription = subProjectDescription;
        this.subProjectEstimatedTimeMinutes = subProjectEstimatedTimeMinutes;
        this.subProjectActualTimeMinutes = subProjectActualTimeMinutes;
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

    public int getSubProjectEstimatedTimeMinutes() {
        return subProjectEstimatedTimeMinutes;
    }

    public void setSubProjectEstimatedTimeMinutes(int subProjectEstimatedTimeMinutes) {
        this.subProjectEstimatedTimeMinutes = subProjectEstimatedTimeMinutes;
    }

    public int getSubProjectActualTimeMinutes() {
        return subProjectActualTimeMinutes;
    }

    public void setSubProjectActualTimeMinutes(int subProjectActualTimeMinutes) {
        this.subProjectActualTimeMinutes = subProjectActualTimeMinutes;
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
                ", subProjectEstimatedTimeMinutes=" + subProjectEstimatedTimeMinutes +
                ", subProjectActualTimeMinutes=" + subProjectActualTimeMinutes +
                '}';
    }
}
