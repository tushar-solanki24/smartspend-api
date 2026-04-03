package com.smartspend.smartspend_api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${SPRING_DATASOURCE_URL}")
    private String dbUrl;

    @Value("${SPRING_DATASOURCE_USERNAME}")
    private String dbUsername;

    @Value("${SPRING_DATASOURCE_PASSWORD}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        String jdbcUrl = dbUrl.startsWith("jdbc:") ? dbUrl : "jdbc:" + dbUrl;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
        return new HikariDataSource(config);
    }
}