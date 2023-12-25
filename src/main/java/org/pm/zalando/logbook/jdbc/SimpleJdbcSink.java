package org.pm.zalando.logbook.jdbc;

import org.pm.zalando.logbook.jdbc.formatters.HttpLogDescriptorFormatter;
import org.zalando.logbook.*;

import java.io.IOException;

/**
 * @author silviu ilie
 * @since 0.0.1-SNAPSHOT on logbook-jdbc
 **/
public class SimpleJdbcSink implements JdbcSink /*  extends DefaultSink (final?) */ {

    private final HttpLogFormatter formatter;
    private final HttpLogWriter writer;

    public SimpleJdbcSink(HttpLogDescriptorFormatter formatter, HttpLogWriter writer) {
        this.formatter = formatter;
        this.writer = writer;
    }

    @Override
    public boolean isActive() {
        return writer != null && writer.isActive();
    }

    @Override
    public void write(final Precorrelation precorrelation,
                      final HttpRequest request) throws IOException {
        writer.write(precorrelation, formatter.format(precorrelation, request));
    }

    @Override
    public void write(final Correlation correlation,
                      final HttpRequest request,
                      final HttpResponse response) throws IOException {
        writer.write(correlation, formatter.format(correlation, response));
    }

}
