package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Project> findProjectsByUserId(int userId) {
        String sql = """
                SELECT
                    project_id,
                    project_name,
                    project_description,
                    project_start_date,
                    project_estimated_deadline,
                    project_estimated_hours,
                    project_actual_hours,
                    owner_user_id
                FROM project
                WHERE owner_user_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Project(
                rs.getInt("project_id"),
                rs.getString("project_name"),
                rs.getString("project_description"),
                rs.getDate("project_start_date").toLocalDate(),
                rs.getDate("project_estimated_deadline") != null
                        ? rs.getDate("project_estimated_deadline").toLocalDate()
                        : null,
                rs.getDouble("project_estimated_hours"),
                rs.getDouble("project_actual_hours"),
                rs.getInt("owner_user_id")
        ), userId);
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

    public void deleteProjectById(int projectId) {
        String sql = """
                DELETE FROM project
                WHERE project_id = ?
                """;
        jdbcTemplate.update(sql, projectId);
    }

    public void updateProject(int projectId, String name, String description, LocalDate startDate) {
        String sql = """
                UPDATE project
                SET project_name = ?, project_description = ?, project_start_date = ?
                WHERE project_id = ?
                """;
        jdbcTemplate.update(sql, name, description, Date.valueOf(startDate), projectId);
    }

    public Project findProjectById(int projectId) {
        String sql = """
                SELECT project_id, project_name, project_description, 
                project_start_date, project_estimated_deadline, 
                project_estimated_hours, project_actual_hours, owner_user_id
                FROM project
                WHERE project_id = ?""";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Project(
                rs.getInt("project_id"),
                rs.getString("project_name"),
                rs.getString("project_description"),
                rs.getDate("project_start_date").toLocalDate(),
                rs.getDate("project_estimated_deadline") != null
                ? rs.getDate("project_estimated_deadline").toLocalDate() : null,
                rs.getDouble("project_estimated_hours"),
                rs.getDouble("project_actual_hours"),
                rs.getInt("owner_user_id")
                ), projectId);
    }

    public void addUserToProject(int projectId, int userId) {
        String sql = """
        INSERT INTO project_assigned_user(project_id, project_assigned_user_id)
        VALUES (?, ?)
        """;

        jdbcTemplate.update(sql, projectId, userId);
    }

    public void removeUserFromProject(int projectId, int userId) {
        String sql = """
        DELETE FROM project_assigned_user
        WHERE project_id = ?
        AND project_assigned_user_id = ?
        """;

        jdbcTemplate.update(sql, projectId, userId);
    }

    public void removeAllUsersFromProject(int projectId) {
        String sql = """
        
                DELETE FROM project_assigned_user
        WHERE project_id = ?
        """;

        jdbcTemplate.update(sql, projectId);
        }

    public List<Project> findAllProjects() {
        String sql = """
                SELECT
                  project_id,
                  project_name,
                  project_description,
                  project_start_date,
                  project_estimated_deadline,
                  project_estimated_hours,
                  project_actual_hours,
                  owner_user_id
                FROM project
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Project(
                rs.getInt("project_id"),
                rs.getString("project_name"),
                rs.getString("project_description"),
                rs.getDate("project_start_date").toLocalDate(),
                rs.getDate("project_estimated_deadline") != null
                        ? rs.getDate("project_estimated_deadline").toLocalDate()
                        : null,
                rs.getDouble("project_estimated_hours"),
                rs.getDouble("project_actual_hours"),
                rs.getInt("owner_user_id")
        ));
    }
}