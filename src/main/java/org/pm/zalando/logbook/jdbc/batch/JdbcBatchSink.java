package org.pm.zalando.logbook.jdbc.batch;

import org.pm.zalando.logbook.jdbc.JdbcSink;
import org.pm.zalando.logbook.jdbc.formatters.BatchStatementFormatter;
import org.pm.zalando.logbook.jdbc.formatters.HttpLogDescriptorFormatter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.zalando.logbook.*;

import javax.sql.DataSource;
import java.io.IOException;

import static org.pm.zalando.logbook.jdbc.formatters.HttpLogDescriptorFormatter.ANSI_REQUEST_INSERT_STATEMENT_TEMPLATE;
import static org.pm.zalando.logbook.jdbc.formatters.HttpLogDescriptorFormatter.ANSI_RESPONSE_INSERT_STATEMENT_TEMPLATE;

/**
 * @author silviu ilie
 * @since 0.0.1-SNAPSHOT on logbook
 **/
public class JdbcBatchSink implements JdbcSink /*  extends _final!_ DefaultSink*/ {
    private BatchSqlUpdate requestWriter;
    private BatchSqlUpdate responseWriter;
    private final DataSource dataSource;
    private final HttpLogDescriptorFormatter objectFormatter;

    private final int batchSize;

    public JdbcBatchSink(DataSource dataSource, int batchSize, HttpLogDescriptorFormatter objectFormatter) {
        this.dataSource = dataSource;
        this.batchSize = batchSize;

        if (!BatchStatementFormatter.class.isAssignableFrom(objectFormatter.getClass())) {
            throw new IllegalArgumentException(BatchStatementFormatter.class.getName() + " is required for " +
                    this.getClass().getName());
        }

        this.objectFormatter = objectFormatter;
    }


    @Override
    public void write(final Correlation correlation, final HttpRequest request, final HttpResponse response)
            throws IOException {

        if (null == responseWriter) {
            responseWriter = new BatchSqlUpdate(
                    dataSource,
                    sql(response),
                    objectFormatter.asSQLTypes(correlation, response),
                    batchSize
            );
        }

        responseWriter.update(
                objectFormatter.describe(correlation, response)
        );
    }

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request) throws IOException {

        if (null == requestWriter) {
            requestWriter = new BatchSqlUpdate(
                    dataSource,
                    sql(request),
                    objectFormatter.asSQLTypes(precorrelation, request),
                    batchSize
            );
        }

        requestWriter.update(
                objectFormatter.describe(precorrelation, request)
        );
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public String sql(HttpRequest request) {
        return ANSI_REQUEST_INSERT_STATEMENT_TEMPLATE;
    }

    public String sql(HttpResponse response) {
        return ANSI_RESPONSE_INSERT_STATEMENT_TEMPLATE;
    }


}
