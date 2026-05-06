package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Manager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepository {
    private final JdbcTemplate jdbcTemplate;

    public ManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateManager(Manager manager) {
        String sql = """
                        UPDATE manager
                        SET manager_name = ?, manager_username = ?, manager_password = ?
                        WHERE manager_id = ?
                """;

        jdbcTemplate.update(
                sql,
                manager.getManagerName(),
                manager.getManagerUsername(),
                manager.getManagerPassword()
        );
    }

    public Manager findManagerByUsername(String username) {
        String sql = """
                SELECT
                    manager_id,
                    manager_name,
                    manager_username,
                    manager_password
                FROM manager
                WHERE LOWER(user_email) = LOWER(?)
                """;

        try {
            return
                    jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                                    new Manager(
                                            rs.getInt("manager_id"),
                                            rs.getString("manager_name"),
                                            rs.getString("manager_username"),
                                            rs.getString("manager_password")
                                    ),
                            username
                    );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
