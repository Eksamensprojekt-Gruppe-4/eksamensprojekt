package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) { this.projectRepository = projectRepository; }

    public Project addProject(Project project, int managerId){
return projectRepository.addProject(project, managerId);
    }
}
