package com.alex.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 19.10.2016.
 */
@RestController
public class WebController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> list = jdbcTemplate.query("select * from users", new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
            }
        });
        return list;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public User addNewUser(@RequestParam("name") String name, HttpServletResponse response) throws IOException {
        if (name != null) {
            List<User> list = jdbcTemplate.query("select * from users where name=?", new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
                }
            }, name);
            if (list.size() == 0) {
                jdbcTemplate.update("INSERT INTO users(name, score) VALUES(?,?)", name, 0);
                list = jdbcTemplate.query("select * from users where name=?", new RowMapper<User>() {
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
                    }
                }, name);
                response.setStatus(201);
                return list.get(0);
            } else {
                response.sendError(409);
                return null;
            }
        } else {
            response.sendError(400);
            return null;
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void delUser(@RequestParam("name") String name, HttpServletResponse response) {
        if (name != null) {
            List<User> list = jdbcTemplate.query("select * from users where name=?", new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
                }
            }, name);
            if (list.size() != 0) {
                jdbcTemplate.update("DELETE FROM users WHERE name=?", name);
            } else {
                try {
                    response.sendError(404);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                response.sendError(400);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/user/name", method = RequestMethod.PATCH)
    public void rename(HttpServletResponse response, @RequestParam("name") String name, @RequestParam("newName") String newName) {
        if (name != null && newName != null) {
            List<User> list = jdbcTemplate.query("select * from users where name=?", new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
                }
            }, name);
            if (list.size() != 0) {
                jdbcTemplate.update("UPDATE users SET name=? WHERE name=?", newName, name);
            } else {
                try {
                    response.sendError(404);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                response.sendError(400);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/user/score", method = RequestMethod.PATCH)
    public void reScore(HttpServletResponse response, @RequestParam("name") String name, @RequestParam("score") String score) {
        if (name != null && score != null) {
            List<User> list = jdbcTemplate.query("select * from users where name=?", new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
                }
            }, name);
            if (list.size() != 0) {
                jdbcTemplate.update("UPDATE users SET score=? WHERE name=?", Integer.parseInt(score), name);
            } else {
                try {
                    response.sendError(404);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                response.sendError(400);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
