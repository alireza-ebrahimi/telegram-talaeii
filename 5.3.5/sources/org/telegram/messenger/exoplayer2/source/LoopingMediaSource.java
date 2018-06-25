package org.telegram.messenger.exoplayer2.source;

import java.io.IOException;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline$Period;
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

        public InfinitelyLoopingTimeline(Timeline childTimeline) {
            this.childTimeline = childTimeline;
        }

        public int getWindowCount() {
            return this.childTimeline.getWindowCount();
        }

        public int getNextWindowIndex(int windowIndex, int repeatMode) {
            int childNextWindowIndex = this.childTimeline.getNextWindowIndex(windowIndex, repeatMode);
            return childNextWindowIndex == -1 ? 0 : childNextWindowIndex;
        }

        public int getPreviousWindowIndex(int windowIndex, int repeatMode) {
            int childPreviousWindowIndex = this.childTimeline.getPreviousWindowIndex(windowIndex, repeatMode);
            return childPreviousWindowIndex == -1 ? getWindowCount() - 1 : childPreviousWindowIndex;
        }

        public Window getWindow(int windowIndex, Window window, boolean setIds, long defaultPositionProjectionUs) {
            return this.childTimeline.getWindow(windowIndex, window, setIds, defaultPositionProjectionUs);
        }

        public int getPeriodCount() {
            return this.childTimeline.getPeriodCount();
        }

        public Timeline$Period getPeriod(int periodIndex, Timeline$Period period, boolean setIds) {
            return this.childTimeline.getPeriod(periodIndex, period, setIds);
        }

        public int getIndexOfPeriod(Object uid) {
            return this.childTimeline.getIndexOfPeriod(uid);
        }
    }

    private static final class LoopingTimeline extends AbstractConcatenatedTimeline {
        private final int childPeriodCount;
        private final Timeline childTimeline;
        private final int childWindowCount;
        private final int loopCount;

        public LoopingTimeline(Timeline childTimeline, int loopCount) {
            super(loopCount);
            this.childTimeline = childTimeline;
            this.childPeriodCount = childTimeline.getPeriodCount();
            this.childWindowCount = childTimeline.getWindowCount();
            this.loopCount = loopCount;
            Assertions.checkState(loopCount <= Integer.MAX_VALUE / this.childPeriodCount, "LoopingMediaSource contains too many periods");
        }

        public int getWindowCount() {
            return this.childWindowCount * this.loopCount;
        }

        public int getPeriodCount() {
            return this.childPeriodCount * this.loopCount;
        }

        protected int getChildIndexByPeriodIndex(int periodIndex) {
            return periodIndex / this.childPeriodCount;
        }

        protected int getChildIndexByWindowIndex(int windowIndex) {
            return windowIndex / this.childWindowCount;
        }

        protected int getChildIndexByChildUid(Object childUid) {
            if (childUid instanceof Integer) {
                return ((Integer) childUid).intValue();
            }
            return -1;
        }

        protected Timeline getTimelineByChildIndex(int childIndex) {
            return this.childTimeline;
        }

        protected int getFirstPeriodIndexByChildIndex(int childIndex) {
            return this.childPeriodCount * childIndex;
        }

        protected int getFirstWindowIndexByChildIndex(int childIndex) {
            return this.childWindowCount * childIndex;
        }

        protected Object getChildUidByChildIndex(int childIndex) {
            return Integer.valueOf(childIndex);
        }
    }

    public LoopingMediaSource(MediaSource childSource) {
        this(childSource, Integer.MAX_VALUE);
    }

    public LoopingMediaSource(MediaSource childSource, int loopCount) {
        Assertions.checkArgument(loopCount > 0);
        this.childSource = childSource;
        this.loopCount = loopCount;
    }

    public void prepareSource(ExoPlayer player, boolean isTopLevelSource, final Listener listener) {
        this.childSource.prepareSource(player, false, new Listener() {
            public void onSourceInfoRefreshed(Timeline timeline, Object manifest) {
                LoopingMediaSource.this.childPeriodCount = timeline.getPeriodCount();
                listener.onSourceInfoRefreshed(LoopingMediaSource.this.loopCount != Integer.MAX_VALUE ? new LoopingTimeline(timeline, LoopingMediaSource.this.loopCount) : new InfinitelyLoopingTimeline(timeline), manifest);
            }
        });
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.childSource.maybeThrowSourceInfoRefreshError();
    }

    public MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator) {
        if (this.loopCount != Integer.MAX_VALUE) {
            return this.childSource.createPeriod(new MediaPeriodId(id.periodIndex % this.childPeriodCount), allocator);
        }
        return this.childSource.createPeriod(id, allocator);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.childSource.releasePeriod(mediaPeriod);
    }

    public void releaseSource() {
        this.childSource.releaseSource();
    }
}
