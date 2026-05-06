package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.Manager;
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

    private int managerId;
    private String managerName;
    private String managerUsername;
    private String managerPassword;
}
