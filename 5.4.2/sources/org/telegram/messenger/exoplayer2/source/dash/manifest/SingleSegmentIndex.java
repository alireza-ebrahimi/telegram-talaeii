package org.telegram.messenger.exoplayer2.source.dash.manifest;

import org.telegram.messenger.exoplayer2.source.dash.DashSegmentIndex;

final class SingleSegmentIndex implements DashSegmentIndex {
    private final RangedUri uri;

    public SingleSegmentIndex(RangedUri rangedUri) {
        this.uri = rangedUri;
    }

    public long getDurationUs(int i, long j) {
        return j;
    }

    public int getFirstSegmentNum() {
        return 0;
    }

    public int getSegmentCount(long j) {
        return 1;
    }

    public int getSegmentNum(long j, long j2) {
        return 0;
    }

    public RangedUri getSegmentUrl(int i) {
        return this.uri;
    }

    public long getTimeUs(int i) {
        return 0;
    }

    public boolean isExplicit() {
        return true;
    }
}
