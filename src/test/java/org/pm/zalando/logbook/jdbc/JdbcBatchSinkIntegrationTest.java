package org.pm.zalando.logbook.jdbc;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pm.zalando.logbook.jdbc.batch.JdbcBatchSink;
import org.pm.zalando.logbook.jdbc.formatters.BatchStatementFormatter;
import org.pm.zalando.logbook.jdbc.formatters.DefaultHttpLogDescriptorFormatter;
import org.pm.zalando.logbook.jdbc.formatters.HttpLogDescriptorFormatter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.logbook.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author silviu ilie
 * @since 0.0.1-SNAPSHOT on logbook-jdbc-sink
 **/
public class JdbcBatchSinkIntegrationTest extends IntegrationTestContext {

    private DataSource dataSource;
    private JdbcBatchSink jdbcBatchSink;

    private List<HttpRequest> testRequests = new ArrayList<>();
    private List<HttpResponse> testResponse = new ArrayList<>();
    private List<Precorrelation> preCorrelations = new ArrayList<>();

    private List<Correlation> correlations = new ArrayList<>();

    class NOPHttpLogDescriptorFormatter implements BatchStatementFormatter, HttpLogDescriptorFormatter {
        //
    }

    @BeforeEach
    public void init() {
        dataSource = IntegrationTestContext.createDataSource();
        jdbcBatchSink = new JdbcBatchSink(dataSource,
                BatchSqlUpdate.DEFAULT_BATCH_SIZE,
                new NOPHttpLogDescriptorFormatter()
                );

        // test data with more requests than batch size.
        for (int i = 0; i < BatchSqlUpdate.DEFAULT_BATCH_SIZE + 1; i++) {
            testRequests.add(new TestHttpRequest());
        }
        for (int i = 0; i < BatchSqlUpdate.DEFAULT_BATCH_SIZE + 1; i++) {
            testResponse.add(new TestHttpResponse());
        }
        for (int i = 0; i < BatchSqlUpdate.DEFAULT_BATCH_SIZE + 1; i++) {
            preCorrelations.add(new TestPrecorrelation(UUID.randomUUID().toString(), Clock.systemUTC()));
        }
        for (int i = 0; i < BatchSqlUpdate.DEFAULT_BATCH_SIZE + 1; i++) {
            correlations.add(
                    new TestCorrelation(UUID.randomUUID().toString(),
                            Instant.now(),
                            Instant.now().plus(1, ChronoUnit.MINUTES)));
        }
    }

    @Test
    @Sql(statements = {IntegrationTestContext.CREATE_HTTP_REQUESTS_TABLE, IntegrationTestContext.DELETE_ALL_HTTP_REQUESTS})
    public void writeRequest() throws IOException, SQLException {
        for (int i = 0; i < BatchSqlUpdate.DEFAULT_BATCH_SIZE + 1; i++) {
            jdbcBatchSink.write(preCorrelations.get(i), testRequests.get(i));
        }

        Statement checkStatement = dataSource
                .getConnection()
                .createStatement();

        if (checkStatement.execute("SELECT COUNT(*) FROM http_requests")) {
            ResultSet result = checkStatement.getResultSet();
            result.next();
            int rowsAffected = result.getInt(1);
            Assertions.assertEquals(BatchSqlUpdate.DEFAULT_BATCH_SIZE, rowsAffected);
        } else {
            Assertions.fail();
        }
    }

    @Test
    @Sql(statements = {IntegrationTestContext.CREATE_HTTP_RESPONSES_TABLE, IntegrationTestContext.DELETE_ALL_HTTP_RESPONSES})
    public void writeResponse() throws IOException, SQLException {
        for (int i = 0; i < BatchSqlUpdate.DEFAULT_BATCH_SIZE + 1; i++) {
            jdbcBatchSink.write(correlations.get(i), testRequests.get(i), testResponse.get(i));
        }

        Statement checkStatement = dataSource
                .getConnection()
                .createStatement();

        if (checkStatement.execute("SELECT COUNT(*) FROM http_responses")) {
            ResultSet result = checkStatement.getResultSet();
            result.next();
            int rowsAffected = result.getInt(1);
            Assertions.assertEquals(BatchSqlUpdate.DEFAULT_BATCH_SIZE, rowsAffected);
        } else {
            Assertions.fail();
        }
    }

    @Test()
    public void wrongConfiguration() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jdbcBatchSink = new JdbcBatchSink(
                    dataSource, BatchSqlUpdate.DEFAULT_BATCH_SIZE,
                    /* !!! **/ new DefaultHttpLogDescriptorFormatter()
            );
        });
    }

    @Test
    public void active() {
        assertTrue(jdbcBatchSink.isActive());
    }
}