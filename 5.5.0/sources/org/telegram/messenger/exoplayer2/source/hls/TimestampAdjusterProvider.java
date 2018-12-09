package org.telegram.messenger.exoplayer2.source.hls;

import android.util.SparseArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;

public final class TimestampAdjusterProvider {
    private final SparseArray<TimestampAdjuster> timestampAdjusters = new SparseArray();

    public TimestampAdjuster getAdjuster(int i) {
        TimestampAdjuster timestampAdjuster = (TimestampAdjuster) this.timestampAdjusters.get(i);
        if (timestampAdjuster != null) {
            return timestampAdjuster;
        }
        timestampAdjuster = new TimestampAdjuster(Long.MAX_VALUE);
        this.timestampAdjusters.put(i, timestampAdjuster);
        return timestampAdjuster;
    }

    public void reset() {
        this.timestampAdjusters.clear();
    }
}
