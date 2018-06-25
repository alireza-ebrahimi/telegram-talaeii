package org.telegram.messenger.exoplayer2.extractor.mp3;

import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.util.Util;

final class ConstantBitrateSeeker implements Seeker {
    private static final int BITS_PER_BYTE = 8;
    private final int bitrate;
    private final long durationUs;
    private final long firstFramePosition;

    public ConstantBitrateSeeker(long j, int i, long j2) {
        this.firstFramePosition = j;
        this.bitrate = i;
        this.durationUs = j2 == -1 ? C3446C.TIME_UNSET : getTimeUs(j2);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getPosition(long j) {
        if (this.durationUs == C3446C.TIME_UNSET) {
            return 0;
        }
        return this.firstFramePosition + ((Util.constrainValue(j, 0, this.durationUs) * ((long) this.bitrate)) / 8000000);
    }

    public long getTimeUs(long j) {
        return ((Math.max(0, j - this.firstFramePosition) * C3446C.MICROS_PER_SECOND) * 8) / ((long) this.bitrate);
    }

    public boolean isSeekable() {
        return this.durationUs != C3446C.TIME_UNSET;
    }
}
