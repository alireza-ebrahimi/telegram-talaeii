package org.telegram.messenger.exoplayer2.source.chunk;

import android.util.Log;
import org.telegram.messenger.exoplayer2.extractor.DummyTrackOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.source.SampleQueue;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkExtractorWrapper.TrackOutputProvider;

final class BaseMediaChunkOutput implements TrackOutputProvider {
    private static final String TAG = "BaseMediaChunkOutput";
    private final SampleQueue[] sampleQueues;
    private final int[] trackTypes;

    public BaseMediaChunkOutput(int[] trackTypes, SampleQueue[] sampleQueues) {
        this.trackTypes = trackTypes;
        this.sampleQueues = sampleQueues;
    }

    public TrackOutput track(int id, int type) {
        for (int i = 0; i < this.trackTypes.length; i++) {
            if (type == this.trackTypes[i]) {
                return this.sampleQueues[i];
            }
        }
        Log.e(TAG, "Unmatched track of type: " + type);
        return new DummyTrackOutput();
    }

    public int[] getWriteIndices() {
        int[] writeIndices = new int[this.sampleQueues.length];
        for (int i = 0; i < this.sampleQueues.length; i++) {
            if (this.sampleQueues[i] != null) {
                writeIndices[i] = this.sampleQueues[i].getWriteIndex();
            }
        }
        return writeIndices;
    }

    public void setSampleOffsetUs(long sampleOffsetUs) {
        for (SampleQueue sampleQueue : this.sampleQueues) {
            if (sampleQueue != null) {
                sampleQueue.setSampleOffsetUs(sampleOffsetUs);
            }
        }
    }
}
