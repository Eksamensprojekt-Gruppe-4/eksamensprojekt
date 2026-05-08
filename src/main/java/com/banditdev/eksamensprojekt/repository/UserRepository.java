package com.banditdev.eksamensprojekt.repository;

import com.banditdev.eksamensprojekt.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateUser(User user) {
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
                    user_password
                FROM user
                WHERE LOWER(user_username) = LOWER(?)
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) ->
                            new User(
                                    rs.getInt("user_id"),
                                    rs.getString("user_name"),
                                    rs.getString("user_username"),
                                    rs.getString("user_password")
                            ),
                    username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}