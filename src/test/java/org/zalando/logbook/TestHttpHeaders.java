package org.zalando.logbook;

/**
 * @author silviu ilie
 * @since 0.0.1-SNAPSHOT on logbook-jdbc-sink
 **/
public class TestHttpHeaders /*extends DefaultHttpHeaders*/ {


    public static final HttpHeaders getDelegate() {
        HttpHeaders headers = DefaultHttpHeaders.EMPTY;

        return headers.update("test","value");

    }
}
