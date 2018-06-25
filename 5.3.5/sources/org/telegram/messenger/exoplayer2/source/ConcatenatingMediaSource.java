package org.telegram.messenger.exoplayer2.source;

import java.io.IOException;
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

        public ConcatenatedTimeline(Timeline[] timelines, boolean isRepeatOneAtomic) {
            super(timelines.length);
            int[] sourcePeriodOffsets = new int[timelines.length];
            int[] sourceWindowOffsets = new int[timelines.length];
            long periodCount = 0;
            int windowCount = 0;
            for (int i = 0; i < timelines.length; i++) {
                Timeline timeline = timelines[i];
                periodCount += (long) timeline.getPeriodCount();
                Assertions.checkState(periodCount <= 2147483647L, "ConcatenatingMediaSource children contain too many periods");
                sourcePeriodOffsets[i] = (int) periodCount;
                windowCount += timeline.getWindowCount();
                sourceWindowOffsets[i] = windowCount;
            }
            this.timelines = timelines;
            this.sourcePeriodOffsets = sourcePeriodOffsets;
            this.sourceWindowOffsets = sourceWindowOffsets;
            this.isRepeatOneAtomic = isRepeatOneAtomic;
        }

        public int getWindowCount() {
            return this.sourceWindowOffsets[this.sourceWindowOffsets.length - 1];
        }

        public int getPeriodCount() {
            return this.sourcePeriodOffsets[this.sourcePeriodOffsets.length - 1];
        }

        public int getNextWindowIndex(int windowIndex, int repeatMode) {
            if (this.isRepeatOneAtomic && repeatMode == 1) {
                repeatMode = 2;
            }
            return super.getNextWindowIndex(windowIndex, repeatMode);
        }

        public int getPreviousWindowIndex(int windowIndex, int repeatMode) {
            if (this.isRepeatOneAtomic && repeatMode == 1) {
                repeatMode = 2;
            }
            return super.getPreviousWindowIndex(windowIndex, repeatMode);
        }

        protected int getChildIndexByPeriodIndex(int periodIndex) {
            return Util.binarySearchFloor(this.sourcePeriodOffsets, periodIndex, true, false) + 1;
        }

        protected int getChildIndexByWindowIndex(int windowIndex) {
            return Util.binarySearchFloor(this.sourceWindowOffsets, windowIndex, true, false) + 1;
        }

        protected int getChildIndexByChildUid(Object childUid) {
            if (childUid instanceof Integer) {
                return ((Integer) childUid).intValue();
            }
            return -1;
        }

        protected Timeline getTimelineByChildIndex(int childIndex) {
            return this.timelines[childIndex];
        }

        protected int getFirstPeriodIndexByChildIndex(int childIndex) {
            return childIndex == 0 ? 0 : this.sourcePeriodOffsets[childIndex - 1];
        }

        protected int getFirstWindowIndexByChildIndex(int childIndex) {
            return childIndex == 0 ? 0 : this.sourceWindowOffsets[childIndex - 1];
        }

        protected Object getChildUidByChildIndex(int childIndex) {
            return Integer.valueOf(childIndex);
        }
    }

    public ConcatenatingMediaSource(MediaSource... mediaSources) {
        this(false, mediaSources);
    }

    public ConcatenatingMediaSource(boolean isRepeatOneAtomic, MediaSource... mediaSources) {
        for (MediaSource mediaSource : mediaSources) {
            Assertions.checkNotNull(mediaSource);
        }
        this.mediaSources = mediaSources;
        this.isRepeatOneAtomic = isRepeatOneAtomic;
        this.timelines = new Timeline[mediaSources.length];
        this.manifests = new Object[mediaSources.length];
        this.sourceIndexByMediaPeriod = new HashMap();
        this.duplicateFlags = buildDuplicateFlags(mediaSources);
    }

    public void prepareSource(ExoPlayer player, boolean isTopLevelSource, Listener listener) {
        this.listener = listener;
        for (int i = 0; i < this.mediaSources.length; i++) {
            if (!this.duplicateFlags[i]) {
                final int index = i;
                this.mediaSources[i].prepareSource(player, false, new Listener() {
                    public void onSourceInfoRefreshed(Timeline timeline, Object manifest) {
                        ConcatenatingMediaSource.this.handleSourceInfoRefreshed(index, timeline, manifest);
                    }
                });
            }
        }
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        for (int i = 0; i < this.mediaSources.length; i++) {
            if (!this.duplicateFlags[i]) {
                this.mediaSources[i].maybeThrowSourceInfoRefreshError();
            }
        }
    }

    public MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator) {
        int sourceIndex = this.timeline.getChildIndexByPeriodIndex(id.periodIndex);
        MediaPeriod mediaPeriod = this.mediaSources[sourceIndex].createPeriod(new MediaPeriodId(id.periodIndex - this.timeline.getFirstPeriodIndexByChildIndex(sourceIndex)), allocator);
        this.sourceIndexByMediaPeriod.put(mediaPeriod, Integer.valueOf(sourceIndex));
        return mediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        int sourceIndex = ((Integer) this.sourceIndexByMediaPeriod.get(mediaPeriod)).intValue();
        this.sourceIndexByMediaPeriod.remove(mediaPeriod);
        this.mediaSources[sourceIndex].releasePeriod(mediaPeriod);
    }

    public void releaseSource() {
        for (int i = 0; i < this.mediaSources.length; i++) {
            if (!this.duplicateFlags[i]) {
                this.mediaSources[i].releaseSource();
            }
        }
    }

    private void handleSourceInfoRefreshed(int sourceFirstIndex, Timeline sourceTimeline, Object sourceManifest) {
        this.timelines[sourceFirstIndex] = sourceTimeline;
        this.manifests[sourceFirstIndex] = sourceManifest;
        for (int i = sourceFirstIndex + 1; i < this.mediaSources.length; i++) {
            if (this.mediaSources[i] == this.mediaSources[sourceFirstIndex]) {
                this.timelines[i] = sourceTimeline;
                this.manifests[i] = sourceManifest;
            }
        }
        Timeline[] timelineArr = this.timelines;
        int length = timelineArr.length;
        int i2 = 0;
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

    private static boolean[] buildDuplicateFlags(MediaSource[] mediaSources) {
        boolean[] duplicateFlags = new boolean[mediaSources.length];
        IdentityHashMap<MediaSource, Void> sources = new IdentityHashMap(mediaSources.length);
        for (int i = 0; i < mediaSources.length; i++) {
            MediaSource source = mediaSources[i];
            if (sources.containsKey(source)) {
                duplicateFlags[i] = true;
            } else {
                sources.put(source, null);
            }
        }
        return duplicateFlags;
    }
}
