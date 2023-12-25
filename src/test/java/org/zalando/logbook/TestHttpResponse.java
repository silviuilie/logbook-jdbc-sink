package org.zalando.logbook;

import java.io.IOException;
import java.util.Random;

/**
 * @author silviu ilie
 * @since unknown on logbook-jdbc-sink
 **/
public class TestHttpResponse implements HttpResponse {

    @Override
    public int getStatus() {
        int value;
        Random random = new Random();
        switch ((random.nextInt() % 4)) {
            case 0 : value = 403; break;
            case 1 : value = 404; break;
            case 2 : value = 500; break;
            case 3 : value = 300; break;
            default:
                value = 200;
        }
        return value;
    }

    @Override
    public HttpResponse withBody() throws IOException {
        return null;
    }

    @Override
    public HttpResponse withoutBody() {
        return null;
    }

    @Override
    public Origin getOrigin() {
        return null;
    }

    @Override
    public HttpHeaders getHeaders() {
        return null;
    }

    @Override
    public byte[] getBody() throws IOException {
        return new byte[0];
    }
}
