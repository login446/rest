package com.alex.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by alex on 21.10.2016.
 */
@Component
public class AuthDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAllUsers() {
        String sql = "select * from users";
        List<User> list = jdbcTemplate.query(sql, (ResultSet resultSet, int i) -> {
                    return new User(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("score"));
                }
        );
        return list;
    }

    public User getUser(String name) {
        String sql = "select * from users where name=?";
        List<User> list = jdbcTemplate.query(sql, (rs, i) -> {
            return new User(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("score"));
        }, name);
        if (list.size() == 0)
            return null;
        return list.get(0);
    }

    public User addNewUser(String name) throws IOException {
        String sql = "INSERT INTO users(name, score) VALUES(?,?)";
        jdbcTemplate.update(sql, name, 0);
        return getUser(name);
    }

    public void delUser(String name) {
        String sql = "DELETE FROM users WHERE name=?";
        jdbcTemplate.update(sql, name);
    }

    public void rename(String name, String newName) {
        String sql = "UPDATE users SET name=? WHERE name=?";
        jdbcTemplate.update(sql, newName, name);
    }

    public void reScore(String name, int score) {
        String sql = "UPDATE users SET score=? WHERE name=?";
        jdbcTemplate.update(sql, score, name);
    }
}
