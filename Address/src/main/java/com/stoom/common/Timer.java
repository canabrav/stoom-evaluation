package com.stoom.common;

import java.time.Duration;
import java.time.Instant;

/**
 * Helper class used to measure execution time.
 * 
 * @author Rodrigo
 *
 */
public class Timer {

    private Instant start;
    private Instant end;
    
    public void start() {
        start = Instant.now();
    }

    public void stop () {
        end = Instant.now();
    }

    public Duration getInterval() {
        return Duration.between(start, end);
    }

    public Instant getStart() {
        return start;
    }
}
