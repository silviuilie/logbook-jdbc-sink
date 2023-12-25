package org.pm.zalando.logbook.jdbc.formatters;

import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;

import java.sql.Types;
import java.time.Instant;

/**
 * TODO : comment !
 *
 * @author silviu ilie
 * @since 0.0.1-SNAPSHOT on logbook-jdbc-sink
 **/
interface HttpLogDescriptor {

    public static final String ANSI_REQUEST_INSERT_STRING_TEMPLATE =
            "INSERT INTO HTTP_REQUESTS (ID, METHOD, URI, SCHEME, HOST, PORT, PATH, QUERY, CREATED_AT) VALUES ('%s', '%s', '%s', '%s', '%s', %d, '%s', '%s', current_timestamp)";
    public static final String ANSI_RESPONSE_INSERT_STRING_TEMPLATE =
            "INSERT INTO HTTP_RESPONSES (ID,STATUS) VALUES ('%s', '%d')";
    public static final String ANSI_REQUEST_INSERT_STATEMENT_TEMPLATE =
            "INSERT INTO HTTP_REQUESTS (ID, METHOD, URI, SCHEME, HOST, PORT, PATH, QUERY) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ANSI_RESPONSE_INSERT_STATEMENT_TEMPLATE =
            "INSERT INTO HTTP_RESPONSES (ID,STATUS) VALUES (?, ?)";


    default Object[] describe(Precorrelation precorrelation, HttpRequest request) {
        return new Object[]{
                precorrelation.getId(),

                request.getMethod(),
                request.getRequestUri(),
                request.getScheme(),
                request.getHost(),
                request.getPort().orElse(0),
                request.getPath(),
                request.getQuery()
        };
    }

    default Object[] describe(Precorrelation correlation, HttpResponse response) {
        return new Object[]{
                correlation.getId(),

                response.getStatus()
        };
    }

    default int[] asSQLTypes(Precorrelation precorrelation, HttpRequest request) {
        return describeSQLTypes(describe(precorrelation, request));
    }

    default int[] asSQLTypes(Precorrelation precorrelation, HttpResponse response) {
        return describeSQLTypes(describe(precorrelation, response));

    }

    default int[] describeSQLTypes(Object[] flattenRecord) {
        int index = 0;
        int[] sqlTypes = new int[flattenRecord.length];
        for (Object value : flattenRecord) {
            if (null == value || value.getClass() == String.class) {
                sqlTypes[index] = Types.VARCHAR;
            } else if (Number.class.isAssignableFrom(value.getClass())) {
                sqlTypes[index] = Types.NUMERIC;
            } else if (Instant.class.isAssignableFrom(value.getClass())) {
                sqlTypes[index] = Types.TIMESTAMP;
            }
            index++;
        }
        return sqlTypes;
    }

}
