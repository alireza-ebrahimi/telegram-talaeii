package org.telegram.messenger.exoplayer2.util;

import org.telegram.messenger.exoplayer2.C3446C;

public final class TimestampAdjuster {
    public static final long DO_NOT_OFFSET = Long.MAX_VALUE;
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    private long firstSampleTimestampUs;
    private volatile long lastSampleTimestamp = C3446C.TIME_UNSET;
    private long timestampOffsetUs;

    public TimestampAdjuster(long j) {
        setFirstSampleTimestampUs(j);
    }

    public static long ptsToUs(long j) {
        return (C3446C.MICROS_PER_SECOND * j) / 90000;
    }

    public static long usToPts(long j) {
        return (90000 * j) / C3446C.MICROS_PER_SECOND;
    }

    public long adjustSampleTimestamp(long j) {
        if (j == C3446C.TIME_UNSET) {
            return C3446C.TIME_UNSET;
        }
        if (this.lastSampleTimestamp != C3446C.TIME_UNSET) {
            this.lastSampleTimestamp = j;
        } else {
            if (this.firstSampleTimestampUs != Long.MAX_VALUE) {
                this.timestampOffsetUs = this.firstSampleTimestampUs - j;
            }
            synchronized (this) {
                this.lastSampleTimestamp = j;
                notifyAll();
            }
        }
        return this.timestampOffsetUs + j;
    }

    public long adjustTsTimestamp(long j) {
        if (j == C3446C.TIME_UNSET) {
            return C3446C.TIME_UNSET;
        }
        long j2;
        if (this.lastSampleTimestamp != C3446C.TIME_UNSET) {
            long usToPts = usToPts(this.lastSampleTimestamp);
            long j3 = (4294967296L + usToPts) / MAX_PTS_PLUS_ONE;
            j2 = ((j3 - 1) * MAX_PTS_PLUS_ONE) + j;
            j3 = (j3 * MAX_PTS_PLUS_ONE) + j;
            if (Math.abs(j2 - usToPts) >= Math.abs(j3 - usToPts)) {
                j2 = j3;
            }
        } else {
            j2 = j;
        }
        return adjustSampleTimestamp(ptsToUs(j2));
    }

    public long getFirstSampleTimestampUs() {
        return this.firstSampleTimestampUs;
    }

    public long getLastAdjustedTimestampUs() {
        return this.lastSampleTimestamp != C3446C.TIME_UNSET ? this.lastSampleTimestamp : this.firstSampleTimestampUs != Long.MAX_VALUE ? this.firstSampleTimestampUs : C3446C.TIME_UNSET;
    }

    public long getTimestampOffsetUs() {
        return this.firstSampleTimestampUs == Long.MAX_VALUE ? 0 : this.lastSampleTimestamp != C3446C.TIME_UNSET ? this.timestampOffsetUs : C3446C.TIME_UNSET;
    }

    public void reset() {
        this.lastSampleTimestamp = C3446C.TIME_UNSET;
    }

    public synchronized void setFirstSampleTimestampUs(long j) {
        Assertions.checkState(this.lastSampleTimestamp == C3446C.TIME_UNSET);
        this.firstSampleTimestampUs = j;
    }

    public synchronized void waitUntilInitialized() {
        while (this.lastSampleTimestamp == C3446C.TIME_UNSET) {
            wait();
        }
    }
}
