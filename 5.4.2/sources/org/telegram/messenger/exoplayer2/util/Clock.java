package org.telegram.messenger.exoplayer2.util;

public interface Clock {
    public static final Clock DEFAULT = new SystemClock();

    long elapsedRealtime();

    void sleep(long j);
}
