package org.telegram.messenger.exoplayer2.source;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public final class ConcatenatingMediaSource implements MediaSource {
    private final boolean[] duplicateFlags;
    private final boolean isRepeatOneAtomic;
    private Listener listener;
    private final Object[] manifests;
    private final MediaSource[] mediaSources;
    private final Map<MediaPeriod, Integer> sourceIndexByMediaPeriod;
    private ConcatenatedTimeline timeline;
    private final Timeline[] timelines;

    private static final class ConcatenatedTimeline extends AbstractConcatenatedTimeline {
        private final boolean isRepeatOneAtomic;
        private final int[] sourcePeriodOffsets;
        private final int[] sourceWindowOffsets;
        private final Timeline[] timelines;

        public ConcatenatedTimeline(Timeline[] timelineArr, boolean z) {
            super(timelineArr.length);
            int[] iArr = new int[timelineArr.length];
            int[] iArr2 = new int[timelineArr.length];
            long j = 0;
            int i = 0;
            for (int i2 = 0; i2 < timelineArr.length; i2++) {
                Timeline timeline = timelineArr[i2];
                j += (long) timeline.getPeriodCount();
                Assertions.checkState(j <= 2147483647L, "ConcatenatingMediaSource children contain too many periods");
                iArr[i2] = (int) j;
                i += timeline.getWindowCount();
                iArr2[i2] = i;
            }
            this.timelines = timelineArr;
            this.sourcePeriodOffsets = iArr;
            this.sourceWindowOffsets = iArr2;
            this.isRepeatOneAtomic = z;
        }

        protected int getChildIndexByChildUid(Object obj) {
            return !(obj instanceof Integer) ? -1 : ((Integer) obj).intValue();
        }

        protected int getChildIndexByPeriodIndex(int i) {
            return Util.binarySearchFloor(this.sourcePeriodOffsets, i, true, false) + 1;
        }

        protected int getChildIndexByWindowIndex(int i) {
            return Util.binarySearchFloor(this.sourceWindowOffsets, i, true, false) + 1;
        }

        protected Object getChildUidByChildIndex(int i) {
            return Integer.valueOf(i);
        }

        protected int getFirstPeriodIndexByChildIndex(int i) {
            return i == 0 ? 0 : this.sourcePeriodOffsets[i - 1];
        }

        protected int getFirstWindowIndexByChildIndex(int i) {
            return i == 0 ? 0 : this.sourceWindowOffsets[i - 1];
        }

        public int getNextWindowIndex(int i, int i2) {
            if (this.isRepeatOneAtomic && i2 == 1) {
                i2 = 2;
            }
            return super.getNextWindowIndex(i, i2);
        }

        public int getPeriodCount() {
            return this.sourcePeriodOffsets[this.sourcePeriodOffsets.length - 1];
        }

        public int getPreviousWindowIndex(int i, int i2) {
            if (this.isRepeatOneAtomic && i2 == 1) {
                i2 = 2;
            }
            return super.getPreviousWindowIndex(i, i2);
        }

        protected Timeline getTimelineByChildIndex(int i) {
            return this.timelines[i];
        }

        public int getWindowCount() {
            return this.sourceWindowOffsets[this.sourceWindowOffsets.length - 1];
        }
    }

    public ConcatenatingMediaSource(boolean z, MediaSource... mediaSourceArr) {
        for (Object checkNotNull : mediaSourceArr) {
            Assertions.checkNotNull(checkNotNull);
        }
        this.mediaSources = mediaSourceArr;
        this.isRepeatOneAtomic = z;
        this.timelines = new Timeline[mediaSourceArr.length];
        this.manifests = new Object[mediaSourceArr.length];
        this.sourceIndexByMediaPeriod = new HashMap();
        this.duplicateFlags = buildDuplicateFlags(mediaSourceArr);
    }

    public ConcatenatingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    private static boolean[] buildDuplicateFlags(MediaSource[] mediaSourceArr) {
        boolean[] zArr = new boolean[mediaSourceArr.length];
        IdentityHashMap identityHashMap = new IdentityHashMap(mediaSourceArr.length);
        for (int i = 0; i < mediaSourceArr.length; i++) {
            Object obj = mediaSourceArr[i];
            if (identityHashMap.containsKey(obj)) {
                zArr[i] = true;
            } else {
                identityHashMap.put(obj, null);
            }
        }
        return zArr;
    }

    private void handleSourceInfoRefreshed(int i, Timeline timeline, Object obj) {
        int i2;
        this.timelines[i] = timeline;
        this.manifests[i] = obj;
        for (i2 = i + 1; i2 < this.mediaSources.length; i2++) {
            if (this.mediaSources[i2] == this.mediaSources[i]) {
                this.timelines[i2] = timeline;
                this.manifests[i2] = obj;
            }
        }
        Timeline[] timelineArr = this.timelines;
        int length = timelineArr.length;
        i2 = 0;
        while (i2 < length) {
            if (timelineArr[i2] != null) {
                i2++;
            } else {
                return;
            }
        }
        this.timeline = new ConcatenatedTimeline((Timeline[]) this.timelines.clone(), this.isRepeatOneAtomic);
        this.listener.onSourceInfoRefreshed(this.timeline, this.manifests.clone());
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        int childIndexByPeriodIndex = this.timeline.getChildIndexByPeriodIndex(mediaPeriodId.periodIndex);
        MediaPeriod createPeriod = this.mediaSources[childIndexByPeriodIndex].createPeriod(new MediaPeriodId(mediaPeriodId.periodIndex - this.timeline.getFirstPeriodIndexByChildIndex(childIndexByPeriodIndex)), allocator);
        this.sourceIndexByMediaPeriod.put(createPeriod, Integer.valueOf(childIndexByPeriodIndex));
        return createPeriod;
    }

    public void maybeThrowSourceInfoRefreshError() {
        for (int i = 0; i < this.mediaSources.length; i++) {
            if (!this.duplicateFlags[i]) {
                this.mediaSources[i].maybeThrowSourceInfoRefreshError();
            }
        }
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener) {
        this.listener = listener;
        for (int i = 0; i < this.mediaSources.length; i++) {
            if (!this.duplicateFlags[i]) {
                this.mediaSources[i].prepareSource(exoPlayer, false, new Listener() {
                    public void onSourceInfoRefreshed(Timeline timeline, Object obj) {
                        ConcatenatingMediaSource.this.handleSourceInfoRefreshed(i, timeline, obj);
                    }
                });
            }
        }
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        int intValue = ((Integer) this.sourceIndexByMediaPeriod.get(mediaPeriod)).intValue();
        this.sourceIndexByMediaPeriod.remove(mediaPeriod);
        this.mediaSources[intValue].releasePeriod(mediaPeriod);
    }

    public void releaseSource() {
        for (int i = 0; i < this.mediaSources.length; i++) {
            if (!this.duplicateFlags[i]) {
                this.mediaSources[i].releaseSource();
            }
        }
    }
}
