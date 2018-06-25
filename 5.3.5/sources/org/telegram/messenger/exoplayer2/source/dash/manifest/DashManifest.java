package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.net.Uri;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;

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

    public DashManifest(long availabilityStartTime, long duration, long minBufferTime, boolean dynamic, long minUpdatePeriod, long timeShiftBufferDepth, long suggestedPresentationDelay, UtcTimingElement utcTiming, Uri location, List<Period> periods) {
        this.availabilityStartTime = availabilityStartTime;
        this.duration = duration;
        this.minBufferTime = minBufferTime;
        this.dynamic = dynamic;
        this.minUpdatePeriod = minUpdatePeriod;
        this.timeShiftBufferDepth = timeShiftBufferDepth;
        this.suggestedPresentationDelay = suggestedPresentationDelay;
        this.utcTiming = utcTiming;
        this.location = location;
        if (periods == null) {
            periods = Collections.emptyList();
        }
        this.periods = periods;
    }

    public final int getPeriodCount() {
        return this.periods.size();
    }

    public final Period getPeriod(int index) {
        return (Period) this.periods.get(index);
    }

    public final long getPeriodDurationMs(int index) {
        if (index != this.periods.size() - 1) {
            return ((Period) this.periods.get(index + 1)).startMs - ((Period) this.periods.get(index)).startMs;
        }
        if (this.duration == C0907C.TIME_UNSET) {
            return C0907C.TIME_UNSET;
        }
        return this.duration - ((Period) this.periods.get(index)).startMs;
    }

    public final long getPeriodDurationUs(int index) {
        return C0907C.msToUs(getPeriodDurationMs(index));
    }

    public final DashManifest copy(List<RepresentationKey> representationKeys) {
        LinkedList<RepresentationKey> linkedList = new LinkedList(representationKeys);
        Collections.sort(linkedList);
        linkedList.add(new RepresentationKey(-1, -1, -1));
        ArrayList<Period> copyPeriods = new ArrayList();
        long shiftMs = 0;
        for (int periodIndex = 0; periodIndex < getPeriodCount(); periodIndex++) {
            if (((RepresentationKey) linkedList.peek()).periodIndex != periodIndex) {
                long periodDurationMs = getPeriodDurationMs(periodIndex);
                if (periodDurationMs != C0907C.TIME_UNSET) {
                    shiftMs += periodDurationMs;
                }
            } else {
                Period period = getPeriod(periodIndex);
                copyPeriods.add(new Period(period.id, period.startMs - shiftMs, copyAdaptationSets(period.adaptationSets, linkedList)));
            }
        }
        return new DashManifest(this.availabilityStartTime, this.duration != C0907C.TIME_UNSET ? this.duration - shiftMs : C0907C.TIME_UNSET, this.minBufferTime, this.dynamic, this.minUpdatePeriod, this.timeShiftBufferDepth, this.suggestedPresentationDelay, this.utcTiming, this.location, copyPeriods);
    }

    private static ArrayList<AdaptationSet> copyAdaptationSets(List<AdaptationSet> adaptationSets, LinkedList<RepresentationKey> keys) {
        RepresentationKey key = (RepresentationKey) keys.poll();
        int periodIndex = key.periodIndex;
        ArrayList<AdaptationSet> copyAdaptationSets = new ArrayList();
        do {
            int adaptationSetIndex = key.adaptationSetIndex;
            AdaptationSet adaptationSet = (AdaptationSet) adaptationSets.get(adaptationSetIndex);
            List<Representation> representations = adaptationSet.representations;
            ArrayList<Representation> copyRepresentations = new ArrayList();
            do {
                copyRepresentations.add((Representation) representations.get(key.representationIndex));
                key = (RepresentationKey) keys.poll();
                if (key.periodIndex != periodIndex) {
                    break;
                }
            } while (key.adaptationSetIndex == adaptationSetIndex);
            copyAdaptationSets.add(new AdaptationSet(adaptationSet.id, adaptationSet.type, copyRepresentations, adaptationSet.accessibilityDescriptors, adaptationSet.supplementalProperties));
        } while (key.periodIndex == periodIndex);
        keys.addFirst(key);
        return copyAdaptationSets;
    }
}
