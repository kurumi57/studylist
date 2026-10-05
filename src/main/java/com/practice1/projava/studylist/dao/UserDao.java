package com.practice1.projava.studylist.dao;

import com.practice1.projava.studylist.model.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ユーザー情報の登録・取得など、
 * usersテーブルへのデータベース操作を担当するクラス
 */
@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * ユーザー情報をuserテーブルに登録する
     *
     * @param user　登録するユーザー情報
     */
    public void add(AppUser user) {
        String sql = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.id(), user.username(), user.password());
    }

    /**
     * 指定されたユーザー名に一致するユーザー情報をusersテーブルから取得する
     *
     * @param username　取得するユーザーのユーザー名
     * @return　取得したユーザー情報
     */
    public AppUser findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new AppUser(rs.getString("id"), rs.getString("username"), rs.getString("password")),
                username);
    }
}