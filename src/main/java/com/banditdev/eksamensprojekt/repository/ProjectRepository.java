package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Project addProject(Project project, int managerId) {
        String sql = """
                INSERT INTO project (project_name, manager_id)
                VALUES (?, ?)
                """;
        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setInt(2, managerId);
            return preparedStatement;
        }, kh);

        Number key = kh.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to get KeyHolder id.");
        }
        return new Project(
                key.intValue(),
                project.getProjectName(),
                project.getProjectDescription(),
                project.getProjectEstimatedTimeMinutes(),
                project.getProjectActualTimeMinutes()
        );
    }
}
