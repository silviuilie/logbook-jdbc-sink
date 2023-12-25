package org.pm.zalando.logbook.jdbc.formatters;

import org.zalando.logbook.*;

import java.io.IOException;

/**
 * @author silviu ilie
 * @since 1 on logbook-jdbc-sink
 **/
public interface HttpLogDescriptorFormatter extends HttpLogDescriptor, HttpLogFormatter {

    @Override
    default String format(Precorrelation precorrelation, HttpRequest request) throws IOException {
        return ANSI_REQUEST_INSERT_STRING_TEMPLATE.formatted(
                describe(precorrelation, request)
        );
    }


    @Override
    default String format(Correlation correlation, HttpResponse response) throws IOException {
        return ANSI_RESPONSE_INSERT_STRING_TEMPLATE.formatted(
                correlation.getId(),

                response.getStatus()
        );
    }
}
