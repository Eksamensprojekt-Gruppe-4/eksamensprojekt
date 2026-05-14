package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void addProject(Project project, int userId) {
        projectRepository.addProject(project, userId);
    }

    public void deleteProjectById(int projectId) {
        projectRepository.deleteProjectById(projectId);
    }

    public List<Project> findProjectsByUserId(int userId) {
        return projectRepository.findProjectsByUserId(userId);
    }

    public void updateProject(int projectId, String name, String description, LocalDate startDate){
        projectRepository.updateProject(projectId, name, description, startDate);
    }

    public Project findProjectById(int projectId) {
        return projectRepository.findProjectById(projectId);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAllProjects();
    }
}