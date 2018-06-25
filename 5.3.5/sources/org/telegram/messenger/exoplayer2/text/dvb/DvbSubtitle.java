package org.telegram.messenger.exoplayer2.text.dvb;

import java.util.List;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;

final class DvbSubtitle implements Subtitle {
    private final List<Cue> cues;

    public DvbSubtitle(List<Cue> cues) {
        this.cues = cues;
    }

    public int getNextEventTimeIndex(long timeUs) {
        return -1;
    }

    public int getEventTimeCount() {
        return 1;
    }

    public long getEventTime(int index) {
        return 0;
    }

    public List<Cue> getCues(long timeUs) {
        return this.cues;
    }
}
