package org.telegram.messenger.exoplayer2.source.hls;

import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.source.SampleStream;

final class HlsSampleStream implements SampleStream {
    public final int group;
    private final HlsSampleStreamWrapper sampleStreamWrapper;

    public HlsSampleStream(HlsSampleStreamWrapper hlsSampleStreamWrapper, int i) {
        this.sampleStreamWrapper = hlsSampleStreamWrapper;
        this.group = i;
    }

    public boolean isReady() {
        return this.sampleStreamWrapper.isReady(this.group);
    }

    public void maybeThrowError() {
        this.sampleStreamWrapper.maybeThrowError();
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        return this.sampleStreamWrapper.readData(this.group, formatHolder, decoderInputBuffer, z);
    }

    public void skipData(long j) {
        this.sampleStreamWrapper.skipData(this.group, j);
    }
}
