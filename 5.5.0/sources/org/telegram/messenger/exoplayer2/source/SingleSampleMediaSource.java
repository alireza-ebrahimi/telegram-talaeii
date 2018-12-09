package org.telegram.messenger.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class SingleSampleMediaSource implements MediaSource {
    public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT = 3;
    private final Factory dataSourceFactory;
    private final Handler eventHandler;
    private final EventListener eventListener;
    private final int eventSourceId;
    private final Format format;
    private final int minLoadableRetryCount;
    private final Timeline timeline;
    private final Uri uri;

    public interface EventListener {
        void onLoadError(int i, IOException iOException);
    }

    public SingleSampleMediaSource(Uri uri, Factory factory, Format format, long j) {
        this(uri, factory, format, j, 3);
    }

    public SingleSampleMediaSource(Uri uri, Factory factory, Format format, long j, int i) {
        this(uri, factory, format, j, i, null, null, 0);
    }

    public SingleSampleMediaSource(Uri uri, Factory factory, Format format, long j, int i, Handler handler, EventListener eventListener, int i2) {
        this.uri = uri;
        this.dataSourceFactory = factory;
        this.format = format;
        this.minLoadableRetryCount = i;
        this.eventHandler = handler;
        this.eventListener = eventListener;
        this.eventSourceId = i2;
        this.timeline = new SinglePeriodTimeline(j, true);
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        Assertions.checkArgument(mediaPeriodId.periodIndex == 0);
        return new SingleSampleMediaPeriod(this.uri, this.dataSourceFactory, this.format, this.minLoadableRetryCount, this.eventHandler, this.eventListener, this.eventSourceId);
    }

    public void maybeThrowSourceInfoRefreshError() {
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener) {
        listener.onSourceInfoRefreshed(this.timeline, null);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((SingleSampleMediaPeriod) mediaPeriod).release();
    }

    public void releaseSource() {
    }
}
