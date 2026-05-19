package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.exception.ProjectNotFoundException;
import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project, int userId) {
        validateStartDate(project.getProjectStartDate(), null);
        return projectRepository.addProject(project, userId);
    }

    public void deleteProjectById(int projectId) {
        projectRepository.deleteProjectById(projectId);
    }

    public List<Project> findProjectsByUserId(int userId) {
        return projectRepository.findProjectsByUserId(userId);
    }

    public void updateProject(int projectId, String name, String description, LocalDate startDate) {
        Project existing = projectRepository.findProjectById(projectId);
        validateStartDate(startDate, existing.getProjectStartDate());
        projectRepository.updateProject(projectId, name, description, startDate);
    }

    public Project findProjectById(int projectId) {
        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(projectId);
        }
        return project;
    }

    public void addUserToProject(int projectId, int userId) {
        projectRepository.addUserToProject(projectId, userId);
    }

    public void removeUserFromProject(int projectId, int userId) {
        projectRepository.removeUserFromProject(projectId, userId);
    }

    public void addAssignedUserIdsToDatabase(int projectId, List<Integer> userIdsToAdd) {

        if (userIdsToAdd != null) {
            for (Integer userId : userIdsToAdd) {
                projectRepository.addUserToProject(projectId, userId);
            }
        }
    }

    public void removeAllUsersFromProject(int projectId) {
        projectRepository.removeAllUsersFromProject(projectId);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAllProjects();
    }

    private void validateStartDate(LocalDate newDate, LocalDate storedDate) {
        boolean changed = !newDate.equals(storedDate);
        if (changed && newDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
    }
}