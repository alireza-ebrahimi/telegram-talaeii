package org.telegram.messenger.exoplayer2.source;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.upstream.Allocator;

public final class MergingMediaSource implements MediaSource {
    private static final int PERIOD_COUNT_UNSET = -1;
    private Listener listener;
    private final MediaSource[] mediaSources;
    private IllegalMergeException mergeError;
    private final ArrayList<MediaSource> pendingTimelineSources;
    private int periodCount = -1;
    private Object primaryManifest;
    private Timeline primaryTimeline;
    private final Window window = new Window();

    public static final class IllegalMergeException extends IOException {
        public static final int REASON_PERIOD_COUNT_MISMATCH = 1;
        public static final int REASON_WINDOWS_ARE_DYNAMIC = 0;
        public final int reason;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        public IllegalMergeException(int i) {
            this.reason = i;
        }
    }

    public MergingMediaSource(MediaSource... mediaSourceArr) {
        this.mediaSources = mediaSourceArr;
        this.pendingTimelineSources = new ArrayList(Arrays.asList(mediaSourceArr));
    }

    private IllegalMergeException checkTimelineMerges(Timeline timeline) {
        int windowCount = timeline.getWindowCount();
        for (int i = 0; i < windowCount; i++) {
            if (timeline.getWindow(i, this.window, false).isDynamic) {
                return new IllegalMergeException(0);
            }
        }
        if (this.periodCount == -1) {
            this.periodCount = timeline.getPeriodCount();
        } else if (timeline.getPeriodCount() != this.periodCount) {
            return new IllegalMergeException(1);
        }
        return null;
    }

    private void handleSourceInfoRefreshed(int i, Timeline timeline, Object obj) {
        if (this.mergeError == null) {
            this.mergeError = checkTimelineMerges(timeline);
        }
        if (this.mergeError == null) {
            this.pendingTimelineSources.remove(this.mediaSources[i]);
            if (i == 0) {
                this.primaryTimeline = timeline;
                this.primaryManifest = obj;
            }
            if (this.pendingTimelineSources.isEmpty()) {
                this.listener.onSourceInfoRefreshed(this.primaryTimeline, this.primaryManifest);
            }
        }
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        MediaPeriod[] mediaPeriodArr = new MediaPeriod[this.mediaSources.length];
        for (int i = 0; i < mediaPeriodArr.length; i++) {
            mediaPeriodArr[i] = this.mediaSources[i].createPeriod(mediaPeriodId, allocator);
        }
        return new MergingMediaPeriod(mediaPeriodArr);
    }

    public void maybeThrowSourceInfoRefreshError() {
        if (this.mergeError != null) {
            throw this.mergeError;
        }
        for (MediaSource maybeThrowSourceInfoRefreshError : this.mediaSources) {
            maybeThrowSourceInfoRefreshError.maybeThrowSourceInfoRefreshError();
        }
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener) {
        this.listener = listener;
        for (int i = 0; i < this.mediaSources.length; i++) {
            this.mediaSources[i].prepareSource(exoPlayer, false, new Listener() {
                public void onSourceInfoRefreshed(Timeline timeline, Object obj) {
                    MergingMediaSource.this.handleSourceInfoRefreshed(i, timeline, obj);
                }
            });
        }
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        MergingMediaPeriod mergingMediaPeriod = (MergingMediaPeriod) mediaPeriod;
        for (int i = 0; i < this.mediaSources.length; i++) {
            this.mediaSources[i].releasePeriod(mergingMediaPeriod.periods[i]);
        }
    }

    public void releaseSource() {
        for (MediaSource releaseSource : this.mediaSources) {
            releaseSource.releaseSource();
        }
    }
}
