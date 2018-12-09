package org.telegram.messenger.exoplayer2.text.webvtt;

import android.text.SpannableStringBuilder;
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

    public WebvttSubtitle(List<WebvttCue> list) {
        this.cues = list;
        this.numCues = list.size();
        for (int i = 0; i < this.numCues; i++) {
            WebvttCue webvttCue = (WebvttCue) list.get(i);
            int i2 = i * 2;
            this.cueTimesUs[i2] = webvttCue.startTime;
            this.cueTimesUs[i2 + 1] = webvttCue.endTime;
        }
        this.sortedCueTimesUs = Arrays.copyOf(this.cueTimesUs, this.cueTimesUs.length);
        Arrays.sort(this.sortedCueTimesUs);
    }

    public List<Cue> getCues(long j) {
        CharSequence charSequence = null;
        int i = 0;
        WebvttCue webvttCue = null;
        ArrayList arrayList = null;
        while (i < this.numCues) {
            CharSequence charSequence2;
            WebvttCue webvttCue2;
            ArrayList arrayList2;
            CharSequence charSequence3;
            if (this.cueTimesUs[i * 2] > j || j >= this.cueTimesUs[(i * 2) + 1]) {
                charSequence2 = charSequence;
                webvttCue2 = webvttCue;
                arrayList2 = arrayList;
                charSequence3 = charSequence2;
            } else {
                ArrayList arrayList3 = arrayList == null ? new ArrayList() : arrayList;
                WebvttCue webvttCue3 = (WebvttCue) this.cues.get(i);
                if (!webvttCue3.isNormalCue()) {
                    arrayList3.add(webvttCue3);
                    charSequence3 = charSequence;
                    webvttCue2 = webvttCue;
                    arrayList2 = arrayList3;
                } else if (webvttCue == null) {
                    arrayList2 = arrayList3;
                    WebvttCue webvttCue4 = webvttCue3;
                    charSequence3 = charSequence;
                    webvttCue2 = webvttCue4;
                } else if (charSequence == null) {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append(webvttCue.text).append("\n").append(webvttCue3.text);
                    Object obj = spannableStringBuilder;
                    webvttCue2 = webvttCue;
                    arrayList2 = arrayList3;
                } else {
                    charSequence.append("\n").append(webvttCue3.text);
                    charSequence3 = charSequence;
                    webvttCue2 = webvttCue;
                    arrayList2 = arrayList3;
                }
            }
            i++;
            charSequence2 = charSequence3;
            arrayList = arrayList2;
            webvttCue = webvttCue2;
            charSequence = charSequence2;
        }
        if (charSequence != null) {
            arrayList.add(new WebvttCue(charSequence));
        } else if (webvttCue != null) {
            arrayList.add(webvttCue);
        }
        return arrayList != null ? arrayList : Collections.emptyList();
    }

    public long getEventTime(int i) {
        boolean z = true;
        Assertions.checkArgument(i >= 0);
        if (i >= this.sortedCueTimesUs.length) {
            z = false;
        }
        Assertions.checkArgument(z);
        return this.sortedCueTimesUs[i];
    }

    public int getEventTimeCount() {
        return this.sortedCueTimesUs.length;
    }

    public int getNextEventTimeIndex(long j) {
        int binarySearchCeil = Util.binarySearchCeil(this.sortedCueTimesUs, j, false, false);
        return binarySearchCeil < this.sortedCueTimesUs.length ? binarySearchCeil : -1;
    }
}
