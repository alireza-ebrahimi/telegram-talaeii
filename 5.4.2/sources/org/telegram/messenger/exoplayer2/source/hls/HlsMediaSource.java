package org.telegram.messenger.exoplayer2.source.hls;

import android.net.Uri;
import android.os.Handler;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.ExoPlayerLibraryInfo;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.source.MediaPeriod;
import org.telegram.messenger.exoplayer2.source.MediaSource;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.source.SinglePeriodTimeline;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylist;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PrimaryPlaylistListener;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;
import org.telegram.messenger.exoplayer2.upstream.ParsingLoadable.Parser;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class HlsMediaSource implements MediaSource, PrimaryPlaylistListener {
    public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT = 3;
    private final HlsDataSourceFactory dataSourceFactory;
    private final EventDispatcher eventDispatcher;
    private final Uri manifestUri;
    private final int minLoadableRetryCount;
    private final Parser<HlsPlaylist> playlistParser;
    private HlsPlaylistTracker playlistTracker;
    private Listener sourceListener;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.hls");
    }

    public HlsMediaSource(Uri uri, HlsDataSourceFactory hlsDataSourceFactory, int i, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(uri, hlsDataSourceFactory, i, handler, adaptiveMediaSourceEventListener, new HlsPlaylistParser());
    }

    public HlsMediaSource(Uri uri, HlsDataSourceFactory hlsDataSourceFactory, int i, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener, Parser<HlsPlaylist> parser) {
        this.manifestUri = uri;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.minLoadableRetryCount = i;
        this.playlistParser = parser;
        this.eventDispatcher = new EventDispatcher(handler, adaptiveMediaSourceEventListener);
    }

    public HlsMediaSource(Uri uri, Factory factory, int i, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(uri, new DefaultHlsDataSourceFactory(factory), i, handler, adaptiveMediaSourceEventListener);
    }

    public HlsMediaSource(Uri uri, Factory factory, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(uri, factory, 3, handler, adaptiveMediaSourceEventListener);
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        Assertions.checkArgument(mediaPeriodId.periodIndex == 0);
        return new HlsMediaPeriod(this.playlistTracker, this.dataSourceFactory, this.minLoadableRetryCount, this.eventDispatcher, allocator);
    }

    public void maybeThrowSourceInfoRefreshError() {
        this.playlistTracker.maybeThrowPrimaryPlaylistRefreshError();
    }

    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist hlsMediaPlaylist) {
        Timeline singlePeriodTimeline;
        long j = hlsMediaPlaylist.hasProgramDateTime ? 0 : C3446C.TIME_UNSET;
        long usToMs = hlsMediaPlaylist.hasProgramDateTime ? C3446C.usToMs(hlsMediaPlaylist.startTimeUs) : C3446C.TIME_UNSET;
        long j2 = hlsMediaPlaylist.startOffsetUs;
        if (this.playlistTracker.isLive()) {
            long j3 = hlsMediaPlaylist.hasEndTag ? hlsMediaPlaylist.durationUs + hlsMediaPlaylist.startTimeUs : C3446C.TIME_UNSET;
            List list = hlsMediaPlaylist.segments;
            if (j2 == C3446C.TIME_UNSET) {
                j2 = list.isEmpty() ? 0 : ((Segment) list.get(Math.max(0, list.size() - 3))).relativeStartTimeUs;
            }
            singlePeriodTimeline = new SinglePeriodTimeline(j, usToMs, j3, hlsMediaPlaylist.durationUs, hlsMediaPlaylist.startTimeUs, j2, true, !hlsMediaPlaylist.hasEndTag);
        } else {
            if (j2 == C3446C.TIME_UNSET) {
                j2 = 0;
            }
            singlePeriodTimeline = new SinglePeriodTimeline(j, usToMs, hlsMediaPlaylist.startTimeUs + hlsMediaPlaylist.durationUs, hlsMediaPlaylist.durationUs, hlsMediaPlaylist.startTimeUs, j2, true, false);
        }
        this.sourceListener.onSourceInfoRefreshed(singlePeriodTimeline, new HlsManifest(this.playlistTracker.getMasterPlaylist(), hlsMediaPlaylist));
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener) {
        Assertions.checkState(this.playlistTracker == null);
        this.playlistTracker = new HlsPlaylistTracker(this.manifestUri, this.dataSourceFactory, this.eventDispatcher, this.minLoadableRetryCount, this, this.playlistParser);
        this.sourceListener = listener;
        this.playlistTracker.start();
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((HlsMediaPeriod) mediaPeriod).release();
    }

    public void releaseSource() {
        if (this.playlistTracker != null) {
            this.playlistTracker.release();
            this.playlistTracker = null;
        }
        this.sourceListener = null;
    }
}
