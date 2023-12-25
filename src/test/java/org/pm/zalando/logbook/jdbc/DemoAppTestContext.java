package org.pm.zalando.logbook.jdbc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.util.function.Consumer;

/**
 * @author silviu ilie
 * @since unknown on logbook-jdbc-sink
 **/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DemoAppTestContext.class)
public class DemoAppTestContext {

    private static final GenericContainer DEMO_APP_CONTAINER;

    static {
        DEMO_APP_CONTAINER = new GenericContainer(DockerImageName.parse(
                "logbook-jdbc-sink-testapp")
        );
        DEMO_APP_CONTAINER.start();
    }

    public static String openPorts() {

        DEMO_APP_CONTAINER.getPortBindings().forEach(action -> System.out.println(" ports  " + action));

        return null;
    }
}
