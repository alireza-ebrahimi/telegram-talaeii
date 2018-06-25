package org.telegram.messenger.exoplayer2.text;

import java.util.List;
import org.telegram.messenger.exoplayer2.decoder.OutputBuffer;

public abstract class SubtitleOutputBuffer extends OutputBuffer implements Subtitle {
    private long subsampleOffsetUs;
    private Subtitle subtitle;

    public abstract void release();

    public void setContent(long timeUs, Subtitle subtitle, long subsampleOffsetUs) {
        this.timeUs = timeUs;
        this.subtitle = subtitle;
        if (subsampleOffsetUs == Long.MAX_VALUE) {
            subsampleOffsetUs = this.timeUs;
        }
        this.subsampleOffsetUs = subsampleOffsetUs;
    }

    public int getEventTimeCount() {
        return this.subtitle.getEventTimeCount();
    }

    public long getEventTime(int index) {
        return this.subtitle.getEventTime(index) + this.subsampleOffsetUs;
    }

    public int getNextEventTimeIndex(long timeUs) {
        return this.subtitle.getNextEventTimeIndex(timeUs - this.subsampleOffsetUs);
    }

    public List<Cue> getCues(long timeUs) {
        return this.subtitle.getCues(timeUs - this.subsampleOffsetUs);
    }

    public void clear() {
        super.clear();
        this.subtitle = null;
    }
}
