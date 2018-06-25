package org.telegram.messenger.exoplayer2.source.hls;

import java.io.IOException;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.source.SampleStream;

final class HlsSampleStream implements SampleStream {
    public final int group;
    private final HlsSampleStreamWrapper sampleStreamWrapper;

    public HlsSampleStream(HlsSampleStreamWrapper sampleStreamWrapper, int group) {
        this.sampleStreamWrapper = sampleStreamWrapper;
        this.group = group;
    }

    public boolean isReady() {
        return this.sampleStreamWrapper.isReady(this.group);
    }

    public void maybeThrowError() throws IOException {
        this.sampleStreamWrapper.maybeThrowError();
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean requireFormat) {
        return this.sampleStreamWrapper.readData(this.group, formatHolder, buffer, requireFormat);
    }

    public void skipData(long positionUs) {
        this.sampleStreamWrapper.skipData(this.group, positionUs);
    }
}
