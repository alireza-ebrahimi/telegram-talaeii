package org.telegram.messenger.exoplayer2.text.ttml;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.util.Util;

final class TtmlSubtitle implements Subtitle {
    private final long[] eventTimesUs;
    private final Map<String, TtmlStyle> globalStyles;
    private final Map<String, TtmlRegion> regionMap;
    private final TtmlNode root;

    public TtmlSubtitle(TtmlNode ttmlNode, Map<String, TtmlStyle> map, Map<String, TtmlRegion> map2) {
        this.root = ttmlNode;
        this.regionMap = map2;
        this.globalStyles = map != null ? Collections.unmodifiableMap(map) : Collections.emptyMap();
        this.eventTimesUs = ttmlNode.getEventTimesUs();
    }

    public List<Cue> getCues(long j) {
        return this.root.getCues(j, this.globalStyles, this.regionMap);
    }

    public long getEventTime(int i) {
        return this.eventTimesUs[i];
    }

    public int getEventTimeCount() {
        return this.eventTimesUs.length;
    }

    Map<String, TtmlStyle> getGlobalStyles() {
        return this.globalStyles;
    }

    public int getNextEventTimeIndex(long j) {
        int binarySearchCeil = Util.binarySearchCeil(this.eventTimesUs, j, false, false);
        return binarySearchCeil < this.eventTimesUs.length ? binarySearchCeil : -1;
    }

    TtmlNode getRoot() {
        return this.root;
    }
}
