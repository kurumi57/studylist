package com.practice1.projava.studylist;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(AppUser user) {
        String sql = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                sql,
                user.id(),
                user.username(),
                user.password()
        );
    }

    public AppUser findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        return jdbcTemplate.queryForObject(
                sql,
                        (rs, rowNum) -> new AppUser(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password")
                ),
                username
        );
    }
}
