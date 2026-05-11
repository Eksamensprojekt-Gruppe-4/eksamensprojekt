package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class ProjectService {

        private final ProjectRepository projectRepository;

        public ProjectService(ProjectRepository projectRepository) {
            this.projectRepository = projectRepository;
        }

        public List<Project> findProjectsByUserId(int userId) {
            return projectRepository.findProjectsByUserId(userId);
        }

        public Project addProject(Project project, int userId) {
            return projectRepository.addProject(project, userId);
        }
    }