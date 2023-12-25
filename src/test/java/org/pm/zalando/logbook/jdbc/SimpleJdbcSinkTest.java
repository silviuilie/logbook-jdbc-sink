package org.pm.zalando.logbook.jdbc;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pm.zalando.logbook.jdbc.formatters.DefaultHttpLogDescriptorFormatter;
import org.pm.zalando.logbook.jdbc.writers.DefaultHttpJdbcWriter;
import org.zalando.logbook.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author silviu ilie
 * @since 1.0.0-SNAPSHOT on logbook-jdbc-sink
 **/
public class SimpleJdbcSinkTest {


    private final DefaultHttpLogDescriptorFormatter formatter = Mockito.mock(DefaultHttpLogDescriptorFormatter.class);
    private final HttpLogWriter writer = Mockito.mock(DefaultHttpJdbcWriter.class);
    private final SimpleJdbcSink simpleJdbcSink = Mockito.mock(SimpleJdbcSink.class);
    private final JdbcSink bodyJdbcSink = Mockito.mock(JdbcSink.class);
    private final SimpleJdbcSink headerJdbcSink = Mockito.mock(SimpleJdbcSink.class);
    private final Precorrelation precorrelation = Mockito.mock(Precorrelation.class);
    private final Correlation correlation = Mockito.mock(Correlation.class);
    private final HttpRequest request = Mockito.mock(HttpRequest.class);
    private final HttpResponse response = Mockito.mock(HttpResponse.class);

    @Test
    public void write() throws IOException {

        Mockito.doNothing().when(simpleJdbcSink).write(precorrelation, request);
        Mockito.doNothing().when(simpleJdbcSink).write(correlation, request, response);

        simpleJdbcSink.write(precorrelation, request);
        simpleJdbcSink.write(correlation, request, response);
        simpleJdbcSink.writeBoth(correlation, request, response);

        Mockito.verify(simpleJdbcSink).write(precorrelation, request);
    }

    @Test
    public void active() {
        //mock has no writer
        assertTrue(!simpleJdbcSink.isActive());
    }

}
