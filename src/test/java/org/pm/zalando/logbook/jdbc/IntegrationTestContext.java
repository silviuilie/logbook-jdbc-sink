package org.pm.zalando.logbook.jdbc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

/**
 * @author silviu ilie
 * @since unknown on logbook-jdbc-sink
 **/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestContext.class)
public class IntegrationTestContext {


    public static final String CREATE_HTTP_REQUESTS_TABLE =
            "CREATE TABLE IF NOT EXISTS http_requests (" +
                    "ID character varying(36), " +
                    "METHOD character varying(8)," +
                    "URI character varying (256)," +
                    "SCHEME character varying (256)," +
                    "HOST character varying (256)," +
                    "PORT integer ," +
                    "PATH character varying (256)," +
                    "QUERY character varying (1024)," +
                    "HEADERS character varying (4000)," +
                    "BODY character varying (4000)," +
                    "CREATED_AT date" +
                    ")";

    public static final String CREATE_HTTP_HEADERS_TABLE =
            "CREATE TABLE IF NOT EXISTS HTTP_HEADERS (" +
                    "  ID character varying(36) " +
                    ", TYPE  character varying(8) " +
                    ", NAME  character varying(40) " +
                    ", VALUE text " +
                    ") ";

    public static final String CREATE_HTTP_RESPONSES_TABLE =
            "CREATE TABLE IF NOT EXISTS http_responses (ID character varying(36), STATUS numeric(3,0))";

    public static final String DELETE_ALL_HTTP_REQUESTS =
            "DELETE from http_requests ";

    public static final String DELETE_ALL_HTTP_RESPONSES =
            "DELETE from http_responses ";

    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER;

    static {
        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.2-alpine"));
        POSTGRES_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
    }


    /**
     * Creates a new {@link DataSource}.
     */
    @Bean
    public static DataSource createDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUsername(POSTGRES_SQL_CONTAINER.getUsername());
        dataSource.setPassword(POSTGRES_SQL_CONTAINER.getPassword());
        dataSource.setUrl(POSTGRES_SQL_CONTAINER.getJdbcUrl());
        dataSource.setDriverClassName(POSTGRES_SQL_CONTAINER.getDriverClassName());

        return dataSource;
    }
}
