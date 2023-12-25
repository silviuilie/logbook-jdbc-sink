package org.zalando.logbook;

import lombok.Generated;

import java.time.Clock;
import java.time.Instant;

/**
 * @author silviu ilie
 * @since unknown on logbook-jdbc-sink
 **/
public class TestPrecorrelation implements Precorrelation {
    private final String id;
    private final Clock clock;
    private final Instant start;

    public TestPrecorrelation(final String id, final Clock clock) {
        this.id = id;
        this.clock = clock;
        this.start = Instant.now(clock);
    }

    public Correlation correlate() {
        return new TestCorrelation(this.id, this.start, Instant.now(this.clock));
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public Instant getStart() {
        return this.start;
    }
}
