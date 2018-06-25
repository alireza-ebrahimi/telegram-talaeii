package org.telegram.messenger.exoplayer2.source.dash.manifest;

import org.telegram.messenger.exoplayer2.source.dash.DashSegmentIndex;

final class SingleSegmentIndex implements DashSegmentIndex {
    private final RangedUri uri;

    public SingleSegmentIndex(RangedUri uri) {
        this.uri = uri;
    }

    public int getSegmentNum(long timeUs, long periodDurationUs) {
        return 0;
    }

    public long getTimeUs(int segmentNum) {
        return 0;
    }

    public long getDurationUs(int segmentNum, long periodDurationUs) {
        return periodDurationUs;
    }

    public RangedUri getSegmentUrl(int segmentNum) {
        return this.uri;
    }

    public int getFirstSegmentNum() {
        return 0;
    }

    public int getSegmentCount(long periodDurationUs) {
        return 1;
    }

    public boolean isExplicit() {
        return true;
    }
}
