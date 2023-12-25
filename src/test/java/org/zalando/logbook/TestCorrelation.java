package org.zalando.logbook;

import lombok.Generated;

import java.time.Duration;
import java.time.Instant;

/**
 * test {@code Correlation}.
 *
 * @author silviu ilie
 * @since unknown on logbook-jdbc-sink
 **/
public class TestCorrelation implements Correlation {
    private final String id;
    private final Instant start;
    private final Instant end;
    private final Duration duration;

    public TestCorrelation(final String id, final Instant start, final Instant end) {
        this(id, start, end, Duration.between(start, end));
    }

    @Generated
    private TestCorrelation(final String id, final Instant start, final Instant end, final Duration duration) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public Instant getStart() {
        return this.start;
    }

    @Generated
    public Instant getEnd() {
        return this.end;
    }

    @Generated
    public Duration getDuration() {
        return this.duration;
    }
}
