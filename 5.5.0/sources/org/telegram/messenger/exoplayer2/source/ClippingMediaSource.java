package org.telegram.messenger.exoplayer2.source;

import java.util.ArrayList;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline.Period;
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

        public ClippingTimeline(Timeline timeline, long j, long j2) {
            boolean z = true;
            Assertions.checkArgument(timeline.getWindowCount() == 1);
            Assertions.checkArgument(timeline.getPeriodCount() == 1);
            Window window = timeline.getWindow(0, new Window(), false);
            Assertions.checkArgument(!window.isDynamic);
            if (j2 == Long.MIN_VALUE) {
                j2 = window.durationUs;
            }
            if (window.durationUs != C3446C.TIME_UNSET) {
                if (j2 > window.durationUs) {
                    j2 = window.durationUs;
                }
                boolean z2 = j == 0 || window.isSeekable;
                Assertions.checkArgument(z2);
                Assertions.checkArgument(j <= j2);
            }
            if (timeline.getPeriod(0, new Period()).getPositionInWindowUs() != 0) {
                z = false;
            }
            Assertions.checkArgument(z);
            this.timeline = timeline;
            this.startUs = j;
            this.endUs = j2;
        }

        public int getIndexOfPeriod(Object obj) {
            return this.timeline.getIndexOfPeriod(obj);
        }

        public int getNextWindowIndex(int i, int i2) {
            return this.timeline.getNextWindowIndex(i, i2);
        }

        public Period getPeriod(int i, Period period, boolean z) {
            long j = C3446C.TIME_UNSET;
            Period period2 = this.timeline.getPeriod(0, period, z);
            if (this.endUs != C3446C.TIME_UNSET) {
                j = this.endUs - this.startUs;
            }
            period2.durationUs = j;
            return period2;
        }

        public int getPeriodCount() {
            return 1;
        }

        public int getPreviousWindowIndex(int i, int i2) {
            return this.timeline.getPreviousWindowIndex(i, i2);
        }

        public Window getWindow(int i, Window window, boolean z, long j) {
            Window window2 = this.timeline.getWindow(0, window, z, j);
            window2.durationUs = this.endUs != C3446C.TIME_UNSET ? this.endUs - this.startUs : C3446C.TIME_UNSET;
            if (window2.defaultPositionUs != C3446C.TIME_UNSET) {
                window2.defaultPositionUs = Math.max(window2.defaultPositionUs, this.startUs);
                window2.defaultPositionUs = this.endUs == C3446C.TIME_UNSET ? window2.defaultPositionUs : Math.min(window2.defaultPositionUs, this.endUs);
                window2.defaultPositionUs -= this.startUs;
            }
            long usToMs = C3446C.usToMs(this.startUs);
            if (window2.presentationStartTimeMs != C3446C.TIME_UNSET) {
                window2.presentationStartTimeMs += usToMs;
            }
            if (window2.windowStartTimeMs != C3446C.TIME_UNSET) {
                window2.windowStartTimeMs = usToMs + window2.windowStartTimeMs;
            }
            return window2;
        }

        public int getWindowCount() {
            return 1;
        }
    }

    public ClippingMediaSource(MediaSource mediaSource, long j, long j2) {
        this(mediaSource, j, j2, true);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j, long j2, boolean z) {
        Assertions.checkArgument(j >= 0);
        this.mediaSource = (MediaSource) Assertions.checkNotNull(mediaSource);
        this.startUs = j;
        this.endUs = j2;
        this.enableInitialDiscontinuity = z;
        this.mediaPeriods = new ArrayList();
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        MediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(this.mediaSource.createPeriod(mediaPeriodId, allocator), this.enableInitialDiscontinuity);
        this.mediaPeriods.add(clippingMediaPeriod);
        clippingMediaPeriod.setClipping(this.clippingTimeline.startUs, this.clippingTimeline.endUs);
        return clippingMediaPeriod;
    }

    public void maybeThrowSourceInfoRefreshError() {
        this.mediaSource.maybeThrowSourceInfoRefreshError();
    }

    public void onSourceInfoRefreshed(Timeline timeline, Object obj) {
        this.clippingTimeline = new ClippingTimeline(timeline, this.startUs, this.endUs);
        this.sourceListener.onSourceInfoRefreshed(this.clippingTimeline, obj);
        long access$000 = this.clippingTimeline.startUs;
        long access$100 = this.clippingTimeline.endUs == C3446C.TIME_UNSET ? Long.MIN_VALUE : this.clippingTimeline.endUs;
        int size = this.mediaPeriods.size();
        for (int i = 0; i < size; i++) {
            ((ClippingMediaPeriod) this.mediaPeriods.get(i)).setClipping(access$000, access$100);
        }
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener) {
        this.sourceListener = listener;
        this.mediaSource.prepareSource(exoPlayer, false, this);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        Assertions.checkState(this.mediaPeriods.remove(mediaPeriod));
        this.mediaSource.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
    }

    public void releaseSource() {
        this.mediaSource.releaseSource();
    }
}
