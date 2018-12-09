package org.telegram.messenger.exoplayer2.source;

import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline.Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class SinglePeriodTimeline extends Timeline {
    private static final Object ID = new Object();
    private final boolean isDynamic;
    private final boolean isSeekable;
    private final long periodDurationUs;
    private final long presentationStartTimeMs;
    private final long windowDefaultStartPositionUs;
    private final long windowDurationUs;
    private final long windowPositionInPeriodUs;
    private final long windowStartTimeMs;

    public SinglePeriodTimeline(long j, long j2, long j3, long j4, long j5, long j6, boolean z, boolean z2) {
        this.presentationStartTimeMs = j;
        this.windowStartTimeMs = j2;
        this.periodDurationUs = j3;
        this.windowDurationUs = j4;
        this.windowPositionInPeriodUs = j5;
        this.windowDefaultStartPositionUs = j6;
        this.isSeekable = z;
        this.isDynamic = z2;
    }

    public SinglePeriodTimeline(long j, long j2, long j3, long j4, boolean z, boolean z2) {
        this(C3446C.TIME_UNSET, C3446C.TIME_UNSET, j, j2, j3, j4, z, z2);
    }

    public SinglePeriodTimeline(long j, boolean z) {
        this(j, j, 0, 0, z, false);
    }

    public int getIndexOfPeriod(Object obj) {
        return ID.equals(obj) ? 0 : -1;
    }

    public Period getPeriod(int i, Period period, boolean z) {
        Assertions.checkIndex(i, 0, 1);
        Object obj = z ? ID : null;
        return period.set(obj, obj, 0, this.periodDurationUs, -this.windowPositionInPeriodUs);
    }

    public int getPeriodCount() {
        return 1;
    }

    public Window getWindow(int i, Window window, boolean z, long j) {
        Assertions.checkIndex(i, 0, 1);
        Object obj = z ? ID : null;
        long j2 = this.windowDefaultStartPositionUs;
        if (this.isDynamic) {
            j2 += j;
            if (j2 > this.windowDurationUs) {
                j2 = C3446C.TIME_UNSET;
            }
        }
        return window.set(obj, this.presentationStartTimeMs, this.windowStartTimeMs, this.isSeekable, this.isDynamic, j2, this.windowDurationUs, 0, 0, this.windowPositionInPeriodUs);
    }

    public int getWindowCount() {
        return 1;
    }
}
