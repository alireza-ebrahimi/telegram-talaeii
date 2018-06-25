package org.telegram.messenger.exoplayer2.source.dash.manifest;

import java.util.Collections;
import java.util.List;

public class Period {
    public final List<AdaptationSet> adaptationSets;
    public final String id;
    public final long startMs;

    public Period(String id, long startMs, List<AdaptationSet> adaptationSets) {
        this.id = id;
        this.startMs = startMs;
        this.adaptationSets = Collections.unmodifiableList(adaptationSets);
    }

    public int getAdaptationSetIndex(int type) {
        int adaptationCount = this.adaptationSets.size();
        for (int i = 0; i < adaptationCount; i++) {
            if (((AdaptationSet) this.adaptationSets.get(i)).type == type) {
                return i;
            }
        }
        return -1;
    }
}
