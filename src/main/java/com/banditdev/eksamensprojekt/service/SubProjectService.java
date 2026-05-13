package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.repository.SubProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubProjectService {
    private final SubProjectRepository subProjectRepository;

    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }

    public void createSubProject(SubProject subProject) {
        subProjectRepository.saveSubProject(subProject);
    }

    public SubProject findSubProjectBySubProjectId(int subProjectId) {
        return subProjectRepository.findSubProjectBySubProjectId(subProjectId);
    }

    public List<SubProject> findSubProjectsByProjectId(int projectId) {
        return subProjectRepository.findSubProjectsByProjectId(projectId);
    }

    public void deleteSubProjectById(int subProjectId) {
        subProjectRepository.deleteSubProjectById(subProjectId);
    }
}
