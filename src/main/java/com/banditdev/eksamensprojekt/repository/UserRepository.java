package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.model.UserExperience;
import com.banditdev.eksamensprojekt.model.UserRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("user_id"),
            rs.getString("user_name"),
            rs.getString("user_username"),
            rs.getString("user_password"),
            UserExperience.valueOf(rs.getString("user_experience")),
            UserRole.valueOf(rs.getString("user_role"))
    );

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void editOwnUser(User user) {
        String sql = """
                        UPDATE user
                        SET user_name = ?, user_username = ?, user_password = ?
                        WHERE user_id = ?
                """;

        jdbcTemplate.update(
                sql,
                user.getUserName(),
                user.getUserUsername(),
                user.getUserPassword(),
                user.getUserId()
        );
    }

    public User findUserByUserUsername(String username) {
        String sql = """
                SELECT
                    user_id,
                    user_name,
                    user_username,
                    user_password,
                    user_experience,
                    user_role
                FROM user
                WHERE LOWER(user_username) = LOWER(?)
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql, userRowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> findAllUsers() {
        String sql = """
                SELECT
                    user_id,
                    user_name,
                    user_username,
                    user_password,
                    user_experience,
                    user_role
                FROM user
                """;

            return jdbcTemplate.query(sql, userRowMapper);
    }

    public User findUserAssignedToTaskByTaskId(int taskId) {
        String sql = """
                SELECT
                    u.user_id,
                    u.user_name,
                    u.user_username,
                    u.user_password,
                    u.user_experience,
                    u.user_role
                FROM task t
                JOIN user u ON t.user_id = u.user_id
                WHERE t.task_id = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, taskId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findUserByUserId(int userId) {
        String sql = """
                SELECT
                    user_id,
                    user_name,
                    user_username,
                    user_password,
                    user_experience,
                    user_role
                FROM user
                WHERE user_id = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, userId);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> findUsersAssignedToProjectByProjectId(int projectId) {
        String sql = """
        SELECT
            u.user_id,
            u.user_name,
            u.user_username,
            u.user_password,
            u.user_experience,
            u.user_role
        FROM user u
        JOIN project_assigned_user pau
            ON u.user_id = pau.project_assigned_user_id
        WHERE pau.project_id = ?
        """;

        return jdbcTemplate.query(sql, userRowMapper, projectId);
    }

    public List<Integer> findUserIdsAssignedToProjectByProjectId(int projectId) {
        String sql = """
        SELECT pau.project_assigned_user_id
        FROM project_assigned_user pau
        WHERE pau.project_id = ?
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("project_assigned_user_id"),
                projectId
        );
    }

    public User returnOwnerOfProjectByProjectId(int projectId) {
        String sql = """
                SELECT
                    u.user_id,
                    u.user_name,
                    u.user_username,
                    u.user_password,
                    u.user_experience,
                    u.user_role
                FROM project p
                JOIN user u ON p.owner_user_id = u.user_id
                WHERE p.project_id = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getString("user_username"),
                    rs.getString("user_password"),
                    UserExperience.valueOf(rs.getString("user_experience")),
                    UserRole.valueOf(rs.getString("user_role"))
            ), projectId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}