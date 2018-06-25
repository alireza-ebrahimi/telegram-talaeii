package org.telegram.messenger.exoplayer2.source;

import java.io.IOException;
import java.util.ArrayList;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline$Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class ClippingMediaSource implements MediaSource, Listener {
    private ClippingTimeline clippingTimeline;
    private final boolean enableInitialDiscontinuity;
    private final long endUs;
    private final ArrayList<ClippingMediaPeriod> mediaPeriods;
    private final MediaSource mediaSource;
    private Listener sourceListener;
    private final long startUs;

    private static final class ClippingTimeline extends Timeline {
        private final long endUs;
        private final long startUs;
        private final Timeline timeline;

        public ClippingTimeline(Timeline timeline, long startUs, long endUs) {
            long resolvedEndUs;
            Assertions.checkArgument(timeline.getWindowCount() == 1);
            Assertions.checkArgument(timeline.getPeriodCount() == 1);
            Window window = timeline.getWindow(0, new Window(), false);
            Assertions.checkArgument(!window.isDynamic);
            if (endUs == Long.MIN_VALUE) {
                resolvedEndUs = window.durationUs;
            } else {
                resolvedEndUs = endUs;
            }
            if (window.durationUs != C0907C.TIME_UNSET) {
                if (resolvedEndUs > window.durationUs) {
                    resolvedEndUs = window.durationUs;
                }
                boolean z = startUs == 0 || window.isSeekable;
                Assertions.checkArgument(z);
                Assertions.checkArgument(startUs <= resolvedEndUs);
            }
            Assertions.checkArgument(timeline.getPeriod(0, new Timeline$Period()).getPositionInWindowUs() == 0);
            this.timeline = timeline;
            this.startUs = startUs;
            this.endUs = resolvedEndUs;
        }

        public int getWindowCount() {
            return 1;
        }

        public int getNextWindowIndex(int windowIndex, int repeatMode) {
            return this.timeline.getNextWindowIndex(windowIndex, repeatMode);
        }

        public int getPreviousWindowIndex(int windowIndex, int repeatMode) {
            return this.timeline.getPreviousWindowIndex(windowIndex, repeatMode);
        }

        public Window getWindow(int windowIndex, Window window, boolean setIds, long defaultPositionProjectionUs) {
            window = this.timeline.getWindow(0, window, setIds, defaultPositionProjectionUs);
            window.durationUs = this.endUs != C0907C.TIME_UNSET ? this.endUs - this.startUs : C0907C.TIME_UNSET;
            if (window.defaultPositionUs != C0907C.TIME_UNSET) {
                long j;
                window.defaultPositionUs = Math.max(window.defaultPositionUs, this.startUs);
                if (this.endUs == C0907C.TIME_UNSET) {
                    j = window.defaultPositionUs;
                } else {
                    j = Math.min(window.defaultPositionUs, this.endUs);
                }
                window.defaultPositionUs = j;
                window.defaultPositionUs -= this.startUs;
            }
            long startMs = C0907C.usToMs(this.startUs);
            if (window.presentationStartTimeMs != C0907C.TIME_UNSET) {
                window.presentationStartTimeMs += startMs;
            }
            if (window.windowStartTimeMs != C0907C.TIME_UNSET) {
                window.windowStartTimeMs += startMs;
            }
            return window;
        }

        public int getPeriodCount() {
            return 1;
        }

        public Timeline$Period getPeriod(int periodIndex, Timeline$Period period, boolean setIds) {
            long j = C0907C.TIME_UNSET;
            period = this.timeline.getPeriod(0, period, setIds);
            if (this.endUs != C0907C.TIME_UNSET) {
                j = this.endUs - this.startUs;
            }
            period.durationUs = j;
            return period;
        }

        public int getIndexOfPeriod(Object uid) {
            return this.timeline.getIndexOfPeriod(uid);
        }
    }

    public ClippingMediaSource(MediaSource mediaSource, long startPositionUs, long endPositionUs) {
        this(mediaSource, startPositionUs, endPositionUs, true);
    }

    public ClippingMediaSource(MediaSource mediaSource, long startPositionUs, long endPositionUs, boolean enableInitialDiscontinuity) {
        Assertions.checkArgument(startPositionUs >= 0);
        this.mediaSource = (MediaSource) Assertions.checkNotNull(mediaSource);
        this.startUs = startPositionUs;
        this.endUs = endPositionUs;
        this.enableInitialDiscontinuity = enableInitialDiscontinuity;
        this.mediaPeriods = new ArrayList();
    }

    public void prepareSource(ExoPlayer player, boolean isTopLevelSource, Listener listener) {
        this.sourceListener = listener;
        this.mediaSource.prepareSource(player, false, this);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.mediaSource.maybeThrowSourceInfoRefreshError();
    }

    public MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator) {
        ClippingMediaPeriod mediaPeriod = new ClippingMediaPeriod(this.mediaSource.createPeriod(id, allocator), this.enableInitialDiscontinuity);
        this.mediaPeriods.add(mediaPeriod);
        mediaPeriod.setClipping(this.clippingTimeline.startUs, this.clippingTimeline.endUs);
        return mediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        Assertions.checkState(this.mediaPeriods.remove(mediaPeriod));
        this.mediaSource.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
    }

    public void releaseSource() {
        this.mediaSource.releaseSource();
    }

    public void onSourceInfoRefreshed(Timeline timeline, Object manifest) {
        long endUs;
        this.clippingTimeline = new ClippingTimeline(timeline, this.startUs, this.endUs);
        this.sourceListener.onSourceInfoRefreshed(this.clippingTimeline, manifest);
        long startUs = this.clippingTimeline.startUs;
        if (this.clippingTimeline.endUs == C0907C.TIME_UNSET) {
            endUs = Long.MIN_VALUE;
        } else {
            endUs = this.clippingTimeline.endUs;
        }
        int count = this.mediaPeriods.size();
        for (int i = 0; i < count; i++) {
            ((ClippingMediaPeriod) this.mediaPeriods.get(i)).setClipping(startUs, endUs);
        }
    }
}
