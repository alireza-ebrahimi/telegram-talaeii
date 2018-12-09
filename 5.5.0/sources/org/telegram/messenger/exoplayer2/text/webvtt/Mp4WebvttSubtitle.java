package org.telegram.messenger.exoplayer2.text.webvtt;

import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class Mp4WebvttSubtitle implements Subtitle {
    private final List<Cue> cues;

    public Mp4WebvttSubtitle(List<Cue> list) {
        this.cues = Collections.unmodifiableList(list);
    }

    public List<Cue> getCues(long j) {
        return j >= 0 ? this.cues : Collections.emptyList();
    }

    public long getEventTime(int i) {
        Assertions.checkArgument(i == 0);
        return 0;
    }

    public int getEventTimeCount() {
        return 1;
    }

    public int getNextEventTimeIndex(long j) {
        return j < 0 ? 0 : -1;
    }
}
