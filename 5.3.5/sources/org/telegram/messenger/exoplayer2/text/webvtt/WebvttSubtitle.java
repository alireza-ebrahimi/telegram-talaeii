package org.telegram.messenger.exoplayer2.text.webvtt;

import android.text.SpannableStringBuilder;
import com.persianswitch.sdk.base.log.LogCollector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

final class WebvttSubtitle implements Subtitle {
    private final long[] cueTimesUs = new long[(this.numCues * 2)];
    private final List<WebvttCue> cues;
    private final int numCues;
    private final long[] sortedCueTimesUs;

    public WebvttSubtitle(List<WebvttCue> cues) {
        this.cues = cues;
        this.numCues = cues.size();
        for (int cueIndex = 0; cueIndex < this.numCues; cueIndex++) {
            WebvttCue cue = (WebvttCue) cues.get(cueIndex);
            int arrayIndex = cueIndex * 2;
            this.cueTimesUs[arrayIndex] = cue.startTime;
            this.cueTimesUs[arrayIndex + 1] = cue.endTime;
        }
        this.sortedCueTimesUs = Arrays.copyOf(this.cueTimesUs, this.cueTimesUs.length);
        Arrays.sort(this.sortedCueTimesUs);
    }

    public int getNextEventTimeIndex(long timeUs) {
        int index = Util.binarySearchCeil(this.sortedCueTimesUs, timeUs, false, false);
        return index < this.sortedCueTimesUs.length ? index : -1;
    }

    public int getEventTimeCount() {
        return this.sortedCueTimesUs.length;
    }

    public long getEventTime(int index) {
        boolean z;
        boolean z2 = true;
        if (index >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        if (index >= this.sortedCueTimesUs.length) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        return this.sortedCueTimesUs[index];
    }

    public List<Cue> getCues(long timeUs) {
        ArrayList<Cue> list = null;
        WebvttCue firstNormalCue = null;
        SpannableStringBuilder normalCueTextBuilder = null;
        int i = 0;
        while (i < this.numCues) {
            if (this.cueTimesUs[i * 2] <= timeUs && timeUs < this.cueTimesUs[(i * 2) + 1]) {
                if (list == null) {
                    list = new ArrayList();
                }
                WebvttCue cue = (WebvttCue) this.cues.get(i);
                if (!cue.isNormalCue()) {
                    list.add(cue);
                } else if (firstNormalCue == null) {
                    firstNormalCue = cue;
                } else if (normalCueTextBuilder == null) {
                    normalCueTextBuilder = new SpannableStringBuilder();
                    normalCueTextBuilder.append(firstNormalCue.text).append(LogCollector.LINE_SEPARATOR).append(cue.text);
                } else {
                    normalCueTextBuilder.append(LogCollector.LINE_SEPARATOR).append(cue.text);
                }
            }
            i++;
        }
        if (normalCueTextBuilder != null) {
            list.add(new WebvttCue(normalCueTextBuilder));
        } else if (firstNormalCue != null) {
            list.add(firstNormalCue);
        }
        return list != null ? list : Collections.emptyList();
    }
}
