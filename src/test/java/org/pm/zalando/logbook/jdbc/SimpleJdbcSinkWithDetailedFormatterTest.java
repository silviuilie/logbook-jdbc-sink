package org.pm.zalando.logbook.jdbc;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pm.zalando.logbook.jdbc.formatters.DefaultHttpLogDescriptorFormatter;
import org.pm.zalando.logbook.jdbc.writers.DefaultHttpJdbcWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;

import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleJdbcSinkWithDetailedFormatterTest {


    private final Precorrelation precorrelation = Mockito.mock(Precorrelation.class);
    private final Correlation correlation = Mockito.mock(Correlation.class);
    private final HttpRequest request = Mockito.mock(HttpRequest.class);
    private final HttpResponse response = Mockito.mock(HttpResponse.class);
    private final DefaultHttpLogDescriptorFormatter formatter = Mockito.mock(DefaultHttpLogDescriptorFormatter.class);
    private final DefaultHttpJdbcWriter writer = Mockito.mock(DefaultHttpJdbcWriter.class);

    private final DataSource dataSource = Mockito.mock(DataSource.class);

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);

    private final SimpleJdbcSink simpleJdbcSink = new SimpleJdbcSink(formatter, writer);


    @Test
    public void isInactiveIfWriterIsInactive() {
        Mockito.when(writer.isActive()).thenReturn(false);

        assertFalse(simpleJdbcSink.isActive());
    }


    @Test
    public void shouldBeActiveByDefault() {

        Mockito.when(writer.isActive()).thenReturn(true);

        assertTrue(simpleJdbcSink.isActive());

        Mockito.verify(writer).isActive();
    }

    @Test
    public void writeRequest() throws IOException {
        Mockito.when(formatter.format(precorrelation, request)).thenReturn("request");
        simpleJdbcSink.write(precorrelation, request);
        Mockito.verify(writer).write(precorrelation, "request");
    }


    @Test
    public void write() throws IOException {

        String requestFormatted = " INSERT INTO REQUESTS (..";
        Mockito.when(formatter.format(precorrelation, request)).thenReturn(requestFormatted);
        Mockito.doNothing().when(writer).setTemplate(jdbcTemplate);
        Mockito.doNothing().when(writer).write(precorrelation, requestFormatted);

        writer.setTemplate(jdbcTemplate);
        simpleJdbcSink.write(precorrelation, request);

        Mockito.verify(formatter).format(precorrelation, request);
        Mockito.verify(writer).setTemplate(jdbcTemplate);
        Mockito.verify(writer).write(precorrelation, requestFormatted);

    }

    @Test
    public void writeBoth() throws IOException {
        Mockito.when(formatter.format(correlation, request)).thenReturn("request");
        Mockito.when(formatter.format(correlation, response)).thenReturn("response");
        simpleJdbcSink.writeBoth(correlation, request, response);
        Mockito.verify(writer).write((Precorrelation) correlation, "request");
        Mockito.verify(writer).write(correlation, "response");
    }

}