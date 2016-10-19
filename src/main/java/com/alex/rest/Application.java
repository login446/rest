package com.alex.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.alex.rest")
@EnableAutoConfiguration
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(Application.class, args);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/asserts/**").addResourceLocations("classpath:/asserts/").setCachePeriod(0);
    }

    @Bean(name="dataSource")
    public DataSource getDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/restDB");
        dataSource.setUsername("root");
        dataSource.setPassword("1111");
        return dataSource;
    }
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Autowired DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
