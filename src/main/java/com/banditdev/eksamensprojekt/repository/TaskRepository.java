package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Task addNewTask(Task task, int userId, int subProjectId) {

        String sql = """
                INSERT INTO project_db.task (task_name, task_description, task_estimated_hours, task_actual_hours, user_id, sub_project_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        KeyHolder kh = new GeneratedKeyHolder();


        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskDescription());
            ps.setDouble(3, task.getTaskEstimatedHours());
            ps.setDouble(4, task.getTaskActualHours());
            ps.setInt(5, userId);
            ps.setInt(6, subProjectId);
            return ps;
        }, kh);


        Number key = kh.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to get KeyHolder id.");
        }


        return new Task(key.intValue(), task.getTaskName(), task.getTaskDescription(), task.getTaskEstimatedHours(), task.getTaskActualHours(), userId, subProjectId);
    }


}
