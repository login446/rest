package com.alex.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource(value = { "classpath:jdbc.properties" })
public class DatabaseConfiguration {
    @Autowired
    private Environment environment;
//    @Bean
//    public DataSource jdbcTemplate(){
//        BasicDataSource dataSource = new BasicDataSource() ;
//
//      dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
//        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
//        return dataSource;
//    }
}