package org.telegram.messenger.exoplayer2.text;

import android.support.annotation.NonNull;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;

public final class SubtitleInputBuffer extends DecoderInputBuffer implements Comparable<SubtitleInputBuffer> {
    public long subsampleOffsetUs;

    public SubtitleInputBuffer() {
        super(1);
    }

    public int compareTo(@NonNull SubtitleInputBuffer other) {
        if (isEndOfStream() == other.isEndOfStream()) {
            long delta = this.timeUs - other.timeUs;
            if (delta == 0) {
                return 0;
            }
            if (delta <= 0) {
                return -1;
            }
            return 1;
        } else if (isEndOfStream()) {
            return 1;
        } else {
            return -1;
        }
    }
}
