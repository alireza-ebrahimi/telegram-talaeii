package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.net.Uri;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;

public class DashManifest {
    public final long availabilityStartTime;
    public final long duration;
    public final boolean dynamic;
    public final Uri location;
    public final long minBufferTime;
    public final long minUpdatePeriod;
    private final List<Period> periods;
    public final long suggestedPresentationDelay;
    public final long timeShiftBufferDepth;
    public final UtcTimingElement utcTiming;

    public DashManifest(long j, long j2, long j3, boolean z, long j4, long j5, long j6, UtcTimingElement utcTimingElement, Uri uri, List<Period> list) {
        this.availabilityStartTime = j;
        this.duration = j2;
        this.minBufferTime = j3;
        this.dynamic = z;
        this.minUpdatePeriod = j4;
        this.timeShiftBufferDepth = j5;
        this.suggestedPresentationDelay = j6;
        this.utcTiming = utcTimingElement;
        this.location = uri;
        if (list == null) {
            list = Collections.emptyList();
        }
        this.periods = list;
    }

    private static ArrayList<AdaptationSet> copyAdaptationSets(List<AdaptationSet> list, LinkedList<RepresentationKey> linkedList) {
        RepresentationKey representationKey = (RepresentationKey) linkedList.poll();
        int i = representationKey.periodIndex;
        ArrayList<AdaptationSet> arrayList = new ArrayList();
        RepresentationKey representationKey2 = representationKey;
        do {
            int i2 = representationKey2.adaptationSetIndex;
            AdaptationSet adaptationSet = (AdaptationSet) list.get(i2);
            List list2 = adaptationSet.representations;
            List arrayList2 = new ArrayList();
            do {
                arrayList2.add((Representation) list2.get(representationKey2.representationIndex));
                representationKey2 = (RepresentationKey) linkedList.poll();
                if (representationKey2.periodIndex != i) {
                    break;
                }
            } while (representationKey2.adaptationSetIndex == i2);
            arrayList.add(new AdaptationSet(adaptationSet.id, adaptationSet.type, arrayList2, adaptationSet.accessibilityDescriptors, adaptationSet.supplementalProperties));
        } while (representationKey2.periodIndex == i);
        linkedList.addFirst(representationKey2);
        return arrayList;
    }

    public final DashManifest copy(List<RepresentationKey> list) {
        LinkedList linkedList = new LinkedList(list);
        Collections.sort(linkedList);
        linkedList.add(new RepresentationKey(-1, -1, -1));
        List arrayList = new ArrayList();
        long j = 0;
        for (int i = 0; i < getPeriodCount(); i++) {
            if (((RepresentationKey) linkedList.peek()).periodIndex != i) {
                long periodDurationMs = getPeriodDurationMs(i);
                if (periodDurationMs != C3446C.TIME_UNSET) {
                    j += periodDurationMs;
                }
            } else {
                Period period = getPeriod(i);
                arrayList.add(new Period(period.id, period.startMs - j, copyAdaptationSets(period.adaptationSets, linkedList)));
            }
        }
        return new DashManifest(this.availabilityStartTime, this.duration != C3446C.TIME_UNSET ? this.duration - j : C3446C.TIME_UNSET, this.minBufferTime, this.dynamic, this.minUpdatePeriod, this.timeShiftBufferDepth, this.suggestedPresentationDelay, this.utcTiming, this.location, arrayList);
    }

    public final Period getPeriod(int i) {
        return (Period) this.periods.get(i);
    }

    public final int getPeriodCount() {
        return this.periods.size();
    }

    public final long getPeriodDurationMs(int i) {
        return i == this.periods.size() + -1 ? this.duration == C3446C.TIME_UNSET ? C3446C.TIME_UNSET : this.duration - ((Period) this.periods.get(i)).startMs : ((Period) this.periods.get(i + 1)).startMs - ((Period) this.periods.get(i)).startMs;
    }

    public final long getPeriodDurationUs(int i) {
        return C3446C.msToUs(getPeriodDurationMs(i));
    }
}
