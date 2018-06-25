package org.telegram.messenger.exoplayer2.source.hls;

import android.net.Uri;
import android.os.Handler;
import java.io.IOException;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.ExoPlayerLibraryInfo;
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

    public HlsMediaSource(Uri manifestUri, Factory dataSourceFactory, Handler eventHandler, AdaptiveMediaSourceEventListener eventListener) {
        this(manifestUri, dataSourceFactory, 3, eventHandler, eventListener);
    }

    public HlsMediaSource(Uri manifestUri, Factory dataSourceFactory, int minLoadableRetryCount, Handler eventHandler, AdaptiveMediaSourceEventListener eventListener) {
        this(manifestUri, new DefaultHlsDataSourceFactory(dataSourceFactory), minLoadableRetryCount, eventHandler, eventListener);
    }

    public HlsMediaSource(Uri manifestUri, HlsDataSourceFactory dataSourceFactory, int minLoadableRetryCount, Handler eventHandler, AdaptiveMediaSourceEventListener eventListener) {
        this(manifestUri, dataSourceFactory, minLoadableRetryCount, eventHandler, eventListener, new HlsPlaylistParser());
    }

    public HlsMediaSource(Uri manifestUri, HlsDataSourceFactory dataSourceFactory, int minLoadableRetryCount, Handler eventHandler, AdaptiveMediaSourceEventListener eventListener, Parser<HlsPlaylist> playlistParser) {
        this.manifestUri = manifestUri;
        this.dataSourceFactory = dataSourceFactory;
        this.minLoadableRetryCount = minLoadableRetryCount;
        this.playlistParser = playlistParser;
        this.eventDispatcher = new EventDispatcher(eventHandler, eventListener);
    }

    public void prepareSource(ExoPlayer player, boolean isTopLevelSource, Listener listener) {
        Assertions.checkState(this.playlistTracker == null);
        this.playlistTracker = new HlsPlaylistTracker(this.manifestUri, this.dataSourceFactory, this.eventDispatcher, this.minLoadableRetryCount, this, this.playlistParser);
        this.sourceListener = listener;
        this.playlistTracker.start();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.playlistTracker.maybeThrowPrimaryPlaylistRefreshError();
    }

    public MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator) {
        Assertions.checkArgument(id.periodIndex == 0);
        return new HlsMediaPeriod(this.playlistTracker, this.dataSourceFactory, this.minLoadableRetryCount, this.eventDispatcher, allocator);
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

    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist playlist) {
        SinglePeriodTimeline timeline;
        long presentationStartTimeMs = playlist.hasProgramDateTime ? 0 : C0907C.TIME_UNSET;
        long windowStartTimeMs = playlist.hasProgramDateTime ? C0907C.usToMs(playlist.startTimeUs) : C0907C.TIME_UNSET;
        long windowDefaultStartPositionUs = playlist.startOffsetUs;
        if (this.playlistTracker.isLive()) {
            boolean z;
            long periodDurationUs = playlist.hasEndTag ? playlist.startTimeUs + playlist.durationUs : C0907C.TIME_UNSET;
            List<Segment> segments = playlist.segments;
            if (windowDefaultStartPositionUs == C0907C.TIME_UNSET) {
                if (segments.isEmpty()) {
                    windowDefaultStartPositionUs = 0;
                } else {
                    windowDefaultStartPositionUs = ((Segment) segments.get(Math.max(0, segments.size() - 3))).relativeStartTimeUs;
                }
            }
            long j = playlist.durationUs;
            long j2 = playlist.startTimeUs;
            if (playlist.hasEndTag) {
                z = false;
            } else {
                z = true;
            }
            timeline = new SinglePeriodTimeline(presentationStartTimeMs, windowStartTimeMs, periodDurationUs, j, j2, windowDefaultStartPositionUs, true, z);
        } else {
            if (windowDefaultStartPositionUs == C0907C.TIME_UNSET) {
                windowDefaultStartPositionUs = 0;
            }
            SinglePeriodTimeline singlePeriodTimeline = new SinglePeriodTimeline(presentationStartTimeMs, windowStartTimeMs, playlist.startTimeUs + playlist.durationUs, playlist.durationUs, playlist.startTimeUs, windowDefaultStartPositionUs, true, false);
        }
        this.sourceListener.onSourceInfoRefreshed(timeline, new HlsManifest(this.playlistTracker.getMasterPlaylist(), playlist));
    }
}
