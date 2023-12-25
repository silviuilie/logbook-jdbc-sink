package org.pm.zalando.logbook.jdbc;

import org.junit.jupiter.api.Test;
import org.pm.zalando.logbook.jdbc.formatters.DefaultHttpLogDescriptorFormatter;
import org.pm.zalando.logbook.jdbc.writers.DefaultHttpJdbcWriter;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.logbook.*;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * TODO : comment !
 *
 * @author silviu ilie
 * @since 0.0.1-SNAPSHOT on logbook-jdbc-sink
 **/
public class SimpleJdbcSinkIntegrationTest extends IntegrationTestContext {

    private final SimpleJdbcSink simpleJdbcSink = new SimpleJdbcSink(
            new DefaultHttpLogDescriptorFormatter(),
            new DefaultHttpJdbcWriter(IntegrationTestContext.createDataSource())
    );

    @Test
    @Sql(statements = IntegrationTestContext.CREATE_HTTP_REQUESTS_TABLE)
    public void writeRequest() throws IOException {
        simpleJdbcSink.write(new TestPrecorrelation(UUID.randomUUID().toString(), Clock.systemUTC()),
                new TestHttpRequest().withHeaders(
                        TestHttpHeaders.getDelegate()
                )
        );
    }

    @Test
    @Sql(statements = IntegrationTestContext.CREATE_HTTP_RESPONSES_TABLE)
    public void writeResponse() throws IOException {
        simpleJdbcSink.write(new TestCorrelation(UUID.randomUUID().toString(),
                Instant.now(),
                Instant.now().plus(1, ChronoUnit.MINUTES)), new TestHttpRequest(), new TestHttpResponse());
    }

}
