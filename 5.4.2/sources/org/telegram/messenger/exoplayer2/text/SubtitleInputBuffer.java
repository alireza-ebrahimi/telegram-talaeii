package org.telegram.messenger.exoplayer2.text;

import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;

public final class SubtitleInputBuffer extends DecoderInputBuffer implements Comparable<SubtitleInputBuffer> {
    public long subsampleOffsetUs;

    public SubtitleInputBuffer() {
        super(1);
    }

    public int compareTo(SubtitleInputBuffer subtitleInputBuffer) {
        if (isEndOfStream() != subtitleInputBuffer.isEndOfStream()) {
            return isEndOfStream() ? 1 : -1;
        } else {
            long j = this.timeUs - subtitleInputBuffer.timeUs;
            return j == 0 ? 0 : j <= 0 ? -1 : 1;
        }
    }
}
