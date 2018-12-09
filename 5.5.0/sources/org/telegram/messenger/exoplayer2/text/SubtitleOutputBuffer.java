package org.telegram.messenger.exoplayer2.text;

import java.util.List;
import org.telegram.messenger.exoplayer2.decoder.OutputBuffer;

public abstract class SubtitleOutputBuffer extends OutputBuffer implements Subtitle {
    private long subsampleOffsetUs;
    private Subtitle subtitle;

    public void clear() {
        super.clear();
        this.subtitle = null;
    }

    public List<Cue> getCues(long j) {
        return this.subtitle.getCues(j - this.subsampleOffsetUs);
    }

    public long getEventTime(int i) {
        return this.subtitle.getEventTime(i) + this.subsampleOffsetUs;
    }

    public int getEventTimeCount() {
        return this.subtitle.getEventTimeCount();
    }

    public int getNextEventTimeIndex(long j) {
        return this.subtitle.getNextEventTimeIndex(j - this.subsampleOffsetUs);
    }

    public abstract void release();

    public void setContent(long j, Subtitle subtitle, long j2) {
        this.timeUs = j;
        this.subtitle = subtitle;
        if (j2 == Long.MAX_VALUE) {
            j2 = this.timeUs;
        }
        this.subsampleOffsetUs = j2;
    }
}
