package org.telegram.messenger.exoplayer2.source;

import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline.Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class LoopingMediaSource implements MediaSource {
    private int childPeriodCount;
    private final MediaSource childSource;
    private final int loopCount;

    private static final class InfinitelyLoopingTimeline extends Timeline {
        private final Timeline childTimeline;

        public InfinitelyLoopingTimeline(Timeline timeline) {
            this.childTimeline = timeline;
        }

        public int getIndexOfPeriod(Object obj) {
            return this.childTimeline.getIndexOfPeriod(obj);
        }

        public int getNextWindowIndex(int i, int i2) {
            int nextWindowIndex = this.childTimeline.getNextWindowIndex(i, i2);
            return nextWindowIndex == -1 ? 0 : nextWindowIndex;
        }

        public Period getPeriod(int i, Period period, boolean z) {
            return this.childTimeline.getPeriod(i, period, z);
        }

        public int getPeriodCount() {
            return this.childTimeline.getPeriodCount();
        }

        public int getPreviousWindowIndex(int i, int i2) {
            int previousWindowIndex = this.childTimeline.getPreviousWindowIndex(i, i2);
            return previousWindowIndex == -1 ? getWindowCount() - 1 : previousWindowIndex;
        }

        public Window getWindow(int i, Window window, boolean z, long j) {
            return this.childTimeline.getWindow(i, window, z, j);
        }

        public int getWindowCount() {
            return this.childTimeline.getWindowCount();
        }
    }

    private static final class LoopingTimeline extends AbstractConcatenatedTimeline {
        private final int childPeriodCount;
        private final Timeline childTimeline;
        private final int childWindowCount;
        private final int loopCount;

        public LoopingTimeline(Timeline timeline, int i) {
            super(i);
            this.childTimeline = timeline;
            this.childPeriodCount = timeline.getPeriodCount();
            this.childWindowCount = timeline.getWindowCount();
            this.loopCount = i;
            Assertions.checkState(i <= Integer.MAX_VALUE / this.childPeriodCount, "LoopingMediaSource contains too many periods");
        }

        protected int getChildIndexByChildUid(Object obj) {
            return !(obj instanceof Integer) ? -1 : ((Integer) obj).intValue();
        }

        protected int getChildIndexByPeriodIndex(int i) {
            return i / this.childPeriodCount;
        }

        protected int getChildIndexByWindowIndex(int i) {
            return i / this.childWindowCount;
        }

        protected Object getChildUidByChildIndex(int i) {
            return Integer.valueOf(i);
        }

        protected int getFirstPeriodIndexByChildIndex(int i) {
            return this.childPeriodCount * i;
        }

        protected int getFirstWindowIndexByChildIndex(int i) {
            return this.childWindowCount * i;
        }

        public int getPeriodCount() {
            return this.childPeriodCount * this.loopCount;
        }

        protected Timeline getTimelineByChildIndex(int i) {
            return this.childTimeline;
        }

        public int getWindowCount() {
            return this.childWindowCount * this.loopCount;
        }
    }

    public LoopingMediaSource(MediaSource mediaSource) {
        this(mediaSource, Integer.MAX_VALUE);
    }

    public LoopingMediaSource(MediaSource mediaSource, int i) {
        Assertions.checkArgument(i > 0);
        this.childSource = mediaSource;
        this.loopCount = i;
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        return this.loopCount != Integer.MAX_VALUE ? this.childSource.createPeriod(new MediaPeriodId(mediaPeriodId.periodIndex % this.childPeriodCount), allocator) : this.childSource.createPeriod(mediaPeriodId, allocator);
    }

    public void maybeThrowSourceInfoRefreshError() {
        this.childSource.maybeThrowSourceInfoRefreshError();
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, final Listener listener) {
        this.childSource.prepareSource(exoPlayer, false, new Listener() {
            public void onSourceInfoRefreshed(Timeline timeline, Object obj) {
                LoopingMediaSource.this.childPeriodCount = timeline.getPeriodCount();
                listener.onSourceInfoRefreshed(LoopingMediaSource.this.loopCount != Integer.MAX_VALUE ? new LoopingTimeline(timeline, LoopingMediaSource.this.loopCount) : new InfinitelyLoopingTimeline(timeline), obj);
            }
        });
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.childSource.releasePeriod(mediaPeriod);
    }

    public void releaseSource() {
        this.childSource.releaseSource();
    }
}
