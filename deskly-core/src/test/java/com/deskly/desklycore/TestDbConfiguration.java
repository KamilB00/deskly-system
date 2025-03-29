package com.deskly.desklycore;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration(proxyBeanMethods = false)
public class TestDbConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15-alpine");
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer postgresSQLContainer) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username(postgresSQLContainer.getUsername());
        dataSourceBuilder.password(postgresSQLContainer.getPassword());
        dataSourceBuilder.url(postgresSQLContainer.getJdbcUrl());
        dataSourceBuilder.driverClassName(postgresSQLContainer.getDriverClassName());
        return dataSourceBuilder.build();
    }
}
