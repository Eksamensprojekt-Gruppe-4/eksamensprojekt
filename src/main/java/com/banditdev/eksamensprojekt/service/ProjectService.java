package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.exception.ProjectNotFoundException;
import com.banditdev.eksamensprojekt.model.Project;
import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.model.Task;
import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.repository.ProjectRepository;
import com.banditdev.eksamensprojekt.repository.SubProjectRepository;
import com.banditdev.eksamensprojekt.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SubProjectRepository subProjectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, SubProjectRepository subProjectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.subProjectRepository = subProjectRepository;
        this.taskRepository = taskRepository;
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

    public double calculateEstimatedHoursForProject(int projectId) {
        double total = 0;
        for (SubProject sp : subProjectRepository.findSubProjectsByProjectId(projectId)) {
            for (Task task : taskRepository.findTasksBySubProjectId(sp.getSubProjectId())) {
                total += task.getTaskEstimatedHours();
            }
        }
        return total;
    }

    private void validateStartDate(LocalDate newDate, LocalDate storedDate) {
        boolean changed = !newDate.equals(storedDate);
        if (changed && newDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
    }

    public LocalDate calculateEstimatedEndDate(int projectId) {
        LocalDate startDate = findProjectById(projectId).getProjectStartDate();
        double estimatedHours = calculateEstimatedHoursForProject(projectId);
        int workingDays = (int) Math.ceil(estimatedHours / 8.0); //Divides hours with 8 and rounds up.

        LocalDate endDate = startDate;
        int days = 0;

        while (days < workingDays) {
            endDate = endDate.plusDays(1);
            if (endDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
            endDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                days++;
            }
        }
        return endDate;
    }

    public Map<Integer, LocalDate> getEstimatedEndDatesByProjectId(List<Project> projects) {
        Map<Integer, LocalDate> endDates = new HashMap<>();
        for (Project project : projects) {
            endDates.put(project.getProjectId(), calculateEstimatedEndDate(project.getProjectId()));
        }
        return endDates;
    }
}