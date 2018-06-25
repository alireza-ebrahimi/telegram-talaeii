package org.telegram.messenger.exoplayer2.extractor;

import org.telegram.messenger.exoplayer2.util.Util;

public final class ChunkIndex implements SeekMap {
    private final long durationUs;
    public final long[] durationsUs;
    public final int length;
    public final long[] offsets;
    public final int[] sizes;
    public final long[] timesUs;

    public ChunkIndex(int[] iArr, long[] jArr, long[] jArr2, long[] jArr3) {
        this.sizes = iArr;
        this.offsets = jArr;
        this.durationsUs = jArr2;
        this.timesUs = jArr3;
        this.length = iArr.length;
        if (this.length > 0) {
            this.durationUs = jArr2[this.length - 1] + jArr3[this.length - 1];
        } else {
            this.durationUs = 0;
        }
    }

    public int getChunkIndex(long j) {
        return Util.binarySearchFloor(this.timesUs, j, true, true);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getPosition(long j) {
        return this.offsets[getChunkIndex(j)];
    }

    public boolean isSeekable() {
        return true;
    }
}
