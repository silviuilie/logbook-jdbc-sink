package org.pm.zalando.logbook.jdbc.writers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *
 * @author silviu ilie
 * @since 0.0.1-SNAPSHOT on logbook-jdbc-sink
 **/
public class DefaultHttpJdbcWriter  implements HttpLogWriter {
    private JdbcTemplate template;

    public DefaultHttpJdbcWriter(DataSource dataSource) {
        setTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public boolean isActive() {
        return HttpLogWriter.super.isActive();
    }

    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {
        template.execute(request);
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {
        template.execute(response);
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
