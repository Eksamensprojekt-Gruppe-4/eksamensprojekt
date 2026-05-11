package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project, int userId) {
        return projectRepository.addProject(project, userId);
    }

    public void deleteProjectById(int projectId) {
        projectRepository.deleteProjectById(projectId);
    }

    public List<Project> findProjectsByUserId(int userId) {
        return projectRepository.findProjectsByUserId(userId);
    }

    public boolean validateProjectOwner(User user, int projectId) {
        return projectRepository.findProjectsByUserId(user.getUserId()).stream()
                .anyMatch(projectList -> projectList.getProjectId() == projectId);
    }
}