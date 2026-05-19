package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Task;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> new Task(
            rs.getInt("task_id"),
            rs.getString("task_name"),
            rs.getString("task_description"),
            rs.getDouble("task_estimated_hours"),
            rs.getDouble("task_actual_hours"),
            rs.getInt("user_id"),
            rs.getInt("sub_project_id")
    );

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void addNewTask(Task task, int userId, int subProjectId) {

        String sql = """
                INSERT INTO task (task_name, task_description, task_estimated_hours, task_actual_hours, user_id, sub_project_id)
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


        key.intValue();
    }

    public Task findTaskById(int taskIdToFind) {
            String sql = """
                SELECT
                    task_id,
                    task_name,
                    task_description,
                    task_estimated_hours,
                    task_actual_hours,
                    user_id,
                    sub_project_id
                FROM task
                WHERE task_id = ?
                """;

            try {
                return jdbcTemplate.queryForObject(sql, taskRowMapper, taskIdToFind);

            }

            catch (EmptyResultDataAccessException e) {
                return null;
            }
    }

    public void updateTask(Task task) {
        String sql = """
                UPDATE task
                SET task_name             = ?,
                    task_description      = ?,
                    task_estimated_hours  = ?,
                    task_actual_hours     = ?,
                    user_id               = ?
                WHERE task_id = ?
                """;

        jdbcTemplate.update(sql,
                task.getTaskName(),
                task.getTaskDescription(),
                task.getTaskEstimatedHours(),
                task.getTaskActualHours(),
                task.getUserId(),
                task.getTaskId()
        );
    }

    public List<Task> findTasksBySubProjectId(int subProjectId) {
        String sql = """
            SELECT task_id, task_name, task_description,
                   task_estimated_hours, task_actual_hours, user_id, sub_project_id
            FROM task
            WHERE sub_project_id = ?
            """;
        return jdbcTemplate.query(sql, taskRowMapper, subProjectId);
    }

    public void deleteTaskByTaskId(int taskIdToDelete) {
        String sql = """
                DELETE FROM task
                WHERE task_Id = ?
                """;

        jdbcTemplate.update(sql, taskIdToDelete);
    }


}
