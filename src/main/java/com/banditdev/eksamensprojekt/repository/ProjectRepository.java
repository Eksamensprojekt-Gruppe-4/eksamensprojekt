package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Project addProject(Project project, int userId) {
        String sql = """
                INSERT INTO project (project_name,
                                     project_description,
                                     project_start_date,
                                     project_estimated_deadline,
                                     project_estimated_hours,
                                     project_actual_hours,
                                     owner_user_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setString(2, project.getProjectDescription());
            preparedStatement.setDate(3, Date.valueOf(project.getProjectStartDate()));
            preparedStatement.setDate(4, project.getProjectEstimatedDeadline() != null
                    ? Date.valueOf(project.getProjectEstimatedDeadline())
                    : null);
            preparedStatement.setDouble(5, project.getProjectEstimatedHours());
            preparedStatement.setDouble(6, project.getProjectActualHours());
            preparedStatement.setInt(7, userId);

            return preparedStatement;
        }, kh);

        Number key = kh.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to get generated project id.");
        }

        return new Project(
                key.intValue(),
                project.getProjectName(),
                project.getProjectDescription(),
                project.getProjectStartDate(),
                project.getProjectEstimatedDeadline(),
                project.getProjectEstimatedHours(),
                project.getProjectActualHours(),
                project.getOwnerUserId()
        );
    }
}