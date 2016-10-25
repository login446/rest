package com.alex.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by admin on 25.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class WebControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        String sql = "CREATE TABLE users " +
                "(id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(45) NOT NULL," +
                "score INT NOT NULL," +
                " PRIMARY KEY (id))";
        jdbcTemplate.execute(sql);

        String sqlInsert = "INSERT INTO users(name, score) VALUES(?,?)";
        jdbcTemplate.update(sqlInsert, "ConflictName", 0);
        jdbcTemplate.update(sqlInsert, "DeleteName", 0);
        jdbcTemplate.update(sqlInsert, "Rename", 0);
        jdbcTemplate.update(sqlInsert, "RenameOk", 0);
        jdbcTemplate.update(sqlInsert, "ReScore", 0);
    }

    @After
    public void drop() throws Exception {
        String sql = "DROP TABLE users";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void addNewUserIsEmptyName() throws Exception {
        mockMvc.perform(put("/user")
                .param("name", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addNewUserConflict() throws Exception {
        mockMvc.perform(put("/user")
                .param("name", "ConflictName"))
                .andExpect(status().isConflict());
    }

    @Test
    public void addNewUser() throws Exception {
        mockMvc.perform(put("/user")
                .param("name", "Joni"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("6"))
                .andExpect(jsonPath("$.name").value("Joni"))
                .andExpect(jsonPath("$.score").value("0"));
    }

    @Test
    public void delUserIsEmptyName() throws Exception {
        mockMvc.perform(delete("/user")
                .param("name", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delUserNotFound() throws Exception {
        mockMvc.perform(delete("/user")
                .param("name", "DeleteNameNotFound"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delUser() throws Exception {
        mockMvc.perform(delete("/user")
                .param("name", "DeleteName"))
                .andExpect(status().isOk());
    }

    @Test
    public void renameIsEmptyName() throws Exception {
        mockMvc.perform(patch("/user/name")
                .param("name", "")
                .param("newName", "freeName"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void renameIsEmptyNewName() throws Exception {
        mockMvc.perform(patch("/user/name")
                .param("name", "Rename")
                .param("newName", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void renameNotFound() throws Exception {
        mockMvc.perform(patch("/user/name")
                .param("name", "renameNotFound")
                .param("newName", "freeName"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void renameConflict() throws Exception {
        mockMvc.perform(patch("/user/name")
                .param("name", "Rename")
                .param("newName", "ConflictName"))
                .andExpect(status().isConflict());
    }

    @Test
    public void rename() throws Exception {
        mockMvc.perform(patch("/user/name")
                .param("name", "RenameOk")
                .param("newName", "freeNameOk"))
                .andExpect(status().isOk());
    }

    @Test
    public void reScoreIsEmpty() throws Exception {
        mockMvc.perform(patch("/user/score")
                .param("name", "")
                .param("score", "55"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void reScoreNumberFormat() throws Exception {
        mockMvc.perform(patch("/user/score")
                .param("name", "reScore")
                .param("score", "55t"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void reScoreNotFound() throws Exception {
        mockMvc.perform(patch("/user/score")
                .param("name", "reScoreNotFound")
                .param("score", "55"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void reScore() throws Exception {
        mockMvc.perform(patch("/user/score")
                .param("name", "reScore")
                .param("score", "55"))
                .andExpect(status().isOk());
    }
}