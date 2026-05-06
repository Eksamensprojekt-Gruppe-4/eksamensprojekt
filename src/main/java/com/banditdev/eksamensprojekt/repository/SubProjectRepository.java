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

    public void saveSubProject(SubProject sp) {
        String sql = """
                INSERT INTO Sub_Project (
                sub_project_id,
                sub_project_name,
                sub_project_description,
                sub_project_estimated_time_minutes,
                sub_project_actual_time_minutes)
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                sp.getSubProjectName(),
                sp.getSubProjectDescription(),
                sp.getSubProjectEstimatedTimeMinutes(),
                sp.getSubProjectActualTimeMinutes(),
                sp.getProjectId()
                );
    }
}