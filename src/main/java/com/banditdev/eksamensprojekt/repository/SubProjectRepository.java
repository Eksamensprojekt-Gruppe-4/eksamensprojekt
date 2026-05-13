package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.SubProject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveSubProject(SubProject subProject) {
        String sql = """
                INSERT INTO Sub_Project (
                sub_project_name,
                sub_project_description,
                sub_project_estimated_hours,
                sub_project_actual_hours,
                project_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                subProject.getSubProjectName(),
                subProject.getSubProjectDescription(),
                subProject.getSubProjectEstimatedHours(),
                subProject.getSubProjectActualHours(),
                subProject.getProjectId()
                );
    }

    public SubProject findSubProjectBySubProjectId(int subProjectId) {

        String sql = """
            SELECT
                sub_project_id,
                sub_project_name,
                sub_project_description,
                sub_project_estimated_hours,
                sub_project_actual_hours,
                project_id
            FROM Sub_Project
            WHERE sub_project_id = ?;
            """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new SubProject(
                                rs.getInt("sub_project_id"),
                                rs.getString("sub_project_name"),
                                rs.getString("sub_project_description"),
                                rs.getDouble("sub_project_estimated_hours"),
                                rs.getDouble("sub_project_actual_hours"),
                                rs.getInt("project_id")
                        ),
                subProjectId
        );
    }
    public void deleteSubProjectById(int subProjectId) {
        String sql = "DELETE FROM Sub_Project WHERE sub_project_id = ?";
        jdbcTemplate.update(sql, subProjectId);
    }
}