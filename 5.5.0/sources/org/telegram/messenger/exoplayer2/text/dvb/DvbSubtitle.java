package org.telegram.messenger.exoplayer2.text.dvb;

import java.util.List;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;

final class DvbSubtitle implements Subtitle {
    private final List<Cue> cues;

    public DvbSubtitle(List<Cue> list) {
        this.cues = list;
    }

    public List<Cue> getCues(long j) {
        return this.cues;
    }

    public long getEventTime(int i) {
        return 0;
    }

    public int getEventTimeCount() {
        return 1;
    }

    public int getNextEventTimeIndex(long j) {
        return -1;
    }
}
