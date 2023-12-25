package org.zalando.logbook;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

/**
 * @author silviu ilie
 * @since unknown on logbook-jdbc-sink
 **/
public class TestHttpRequest implements HttpRequest {

    private HttpHeaders headers;

    public static final String randomString() {
        return RandomStringUtils.randomAlphabetic(36);
    }

    @Override
    public String getRequestUri() {
        return TestHttpRequest.randomString();
    }

    @Override
    public String getMethod() {
        String value = "";
        Random random = new Random();
          switch ((random.nextInt() % 4)) {
            case 0 : value = "POST"; break;
            case 1 : value = "GET"; break;
            case 2 : value = "PUT"; break;
            case 3 : value = "DELETE"; break;
            default:
                value = "PATCH";
        }
        return value;
    }

    @Override
    public String getRemote() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public Optional<Integer> getPort() {
        return Optional.empty();
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getQuery() {
        return null;
    }

    @Override
    public HttpRequest withBody() throws IOException {
        return null;
    }

    public HttpRequest withHeaders(HttpHeaders headers) throws IOException {
        this.headers = headers;
        return this;
    }

    @Override
    public HttpRequest withoutBody() {
        return null;
    }

    @Override
    public Origin getOrigin() {
        return null;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public byte[] getBody() throws IOException {
        return new byte[0];
    }
}
