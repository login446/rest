package com.alex.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 19.10.2016.
 */
@RestController
public class WebController {

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public User method(@Autowired JdbcTemplate jdbcTemplate) {
        List<User> list = jdbcTemplate.query("select * from users", new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
            }
        });
        return list.get(0);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void method2() {
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void method3() {
    }

    @RequestMapping(value = "/user/name", method = RequestMethod.PATCH)
    public void method4() {
    }

    @RequestMapping(value = "/user/score", method = RequestMethod.PATCH)
    public void method5() {
    }
}
