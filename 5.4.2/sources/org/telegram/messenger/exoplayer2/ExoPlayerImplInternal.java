package org.telegram.messenger.exoplayer2;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import org.telegram.messenger.exoplayer2.ExoPlayer.ExoPlayerMessage;
import org.telegram.messenger.exoplayer2.MediaPeriodInfoSequence.MediaPeriodInfo;
import org.telegram.messenger.exoplayer2.Timeline.Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.ClippingMediaPeriod;
import org.telegram.messenger.exoplayer2.source.MediaPeriod;
import org.telegram.messenger.exoplayer2.source.MediaSource;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelectionArray;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelector;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelector.InvalidationListener;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelectorResult;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MediaClock;
import org.telegram.messenger.exoplayer2.util.StandaloneMediaClock;
import org.telegram.messenger.exoplayer2.util.TraceUtil;

final class ExoPlayerImplInternal implements Callback, MediaPeriod.Callback, Listener, InvalidationListener {
    private static final int IDLE_INTERVAL_MS = 1000;
    private static final int MAXIMUM_BUFFER_AHEAD_PERIODS = 100;
    private static final int MSG_CUSTOM = 11;
    private static final int MSG_DO_SOME_WORK = 2;
    public static final int MSG_ERROR = 8;
    public static final int MSG_LOADING_CHANGED = 2;
    private static final int MSG_PERIOD_PREPARED = 8;
    public static final int MSG_PLAYBACK_PARAMETERS_CHANGED = 7;
    public static final int MSG_POSITION_DISCONTINUITY = 5;
    private static final int MSG_PREPARE = 0;
    public static final int MSG_PREPARE_ACK = 0;
    private static final int MSG_REFRESH_SOURCE_INFO = 7;
    private static final int MSG_RELEASE = 6;
    public static final int MSG_SEEK_ACK = 4;
    private static final int MSG_SEEK_TO = 3;
    private static final int MSG_SET_PLAYBACK_PARAMETERS = 4;
    private static final int MSG_SET_PLAY_WHEN_READY = 1;
    private static final int MSG_SET_REPEAT_MODE = 12;
    private static final int MSG_SOURCE_CONTINUE_LOADING_REQUESTED = 9;
    public static final int MSG_SOURCE_INFO_REFRESHED = 6;
    public static final int MSG_STATE_CHANGED = 1;
    private static final int MSG_STOP = 5;
    public static final int MSG_TRACKS_CHANGED = 3;
    private static final int MSG_TRACK_SELECTION_INVALIDATED = 10;
    private static final int PREPARING_SOURCE_INTERVAL_MS = 10;
    private static final int RENDERER_TIMESTAMP_OFFSET_US = 60000000;
    private static final int RENDERING_INTERVAL_MS = 10;
    private static final String TAG = "ExoPlayerImplInternal";
    private int customMessagesProcessed;
    private int customMessagesSent;
    private long elapsedRealtimeUs;
    private Renderer[] enabledRenderers;
    private final Handler eventHandler;
    private final Handler handler;
    private final HandlerThread internalPlaybackThread;
    private boolean isLoading;
    private final LoadControl loadControl;
    private MediaPeriodHolder loadingPeriodHolder;
    private final MediaPeriodInfoSequence mediaPeriodInfoSequence;
    private MediaSource mediaSource;
    private int pendingInitialSeekCount;
    private SeekPosition pendingSeekPosition;
    private final Period period;
    private boolean playWhenReady;
    private PlaybackInfo playbackInfo;
    private PlaybackParameters playbackParameters;
    private final ExoPlayer player;
    private MediaPeriodHolder playingPeriodHolder;
    private MediaPeriodHolder readingPeriodHolder;
    private boolean rebuffering;
    private boolean released;
    private final RendererCapabilities[] rendererCapabilities;
    private MediaClock rendererMediaClock;
    private Renderer rendererMediaClockSource;
    private long rendererPositionUs;
    private final Renderer[] renderers;
    private int repeatMode;
    private final StandaloneMediaClock standaloneMediaClock;
    private int state = 1;
    private Timeline timeline;
    private final TrackSelector trackSelector;
    private final Window window;

    private static final class MediaPeriodHolder {
        public boolean hasEnabledTracks;
        public final int index;
        public MediaPeriodInfo info;
        private final LoadControl loadControl;
        public final boolean[] mayRetainStreamFlags;
        public final MediaPeriod mediaPeriod;
        private final MediaSource mediaSource;
        public MediaPeriodHolder next;
        private TrackSelectorResult periodTrackSelectorResult;
        public boolean prepared;
        private final RendererCapabilities[] rendererCapabilities;
        public final long rendererPositionOffsetUs;
        private final Renderer[] renderers;
        public final SampleStream[] sampleStreams;
        private final TrackSelector trackSelector;
        public TrackSelectorResult trackSelectorResult;
        public final Object uid;

        public MediaPeriodHolder(Renderer[] rendererArr, RendererCapabilities[] rendererCapabilitiesArr, long j, TrackSelector trackSelector, LoadControl loadControl, MediaSource mediaSource, Object obj, int i, MediaPeriodInfo mediaPeriodInfo) {
            MediaPeriod clippingMediaPeriod;
            this.renderers = rendererArr;
            this.rendererCapabilities = rendererCapabilitiesArr;
            this.rendererPositionOffsetUs = j;
            this.trackSelector = trackSelector;
            this.loadControl = loadControl;
            this.mediaSource = mediaSource;
            this.uid = Assertions.checkNotNull(obj);
            this.index = i;
            this.info = mediaPeriodInfo;
            this.sampleStreams = new SampleStream[rendererArr.length];
            this.mayRetainStreamFlags = new boolean[rendererArr.length];
            MediaPeriod createPeriod = mediaSource.createPeriod(mediaPeriodInfo.id, loadControl.getAllocator());
            if (mediaPeriodInfo.endPositionUs != Long.MIN_VALUE) {
                clippingMediaPeriod = new ClippingMediaPeriod(createPeriod, true);
                clippingMediaPeriod.setClipping(0, mediaPeriodInfo.endPositionUs);
            } else {
                clippingMediaPeriod = createPeriod;
            }
            this.mediaPeriod = clippingMediaPeriod;
        }

        public void continueLoading(long j) {
            this.mediaPeriod.continueLoading(toPeriodTime(j));
        }

        public long getRendererOffset() {
            return this.index == 0 ? this.rendererPositionOffsetUs : this.rendererPositionOffsetUs - this.info.startPositionUs;
        }

        public void handlePrepared() {
            this.prepared = true;
            selectTracks();
            this.info = this.info.copyWithStartPositionUs(updatePeriodTrackSelection(this.info.startPositionUs, false));
        }

        public boolean haveSufficientBuffer(boolean z, long j) {
            long bufferedPositionUs = !this.prepared ? this.info.startPositionUs : this.mediaPeriod.getBufferedPositionUs();
            if (bufferedPositionUs == Long.MIN_VALUE) {
                if (this.info.isFinal) {
                    return true;
                }
                bufferedPositionUs = this.info.durationUs;
            }
            return this.loadControl.shouldStartPlayback(bufferedPositionUs - toPeriodTime(j), z);
        }

        public boolean isFullyBuffered() {
            return this.prepared && (!this.hasEnabledTracks || this.mediaPeriod.getBufferedPositionUs() == Long.MIN_VALUE);
        }

        public void release() {
            try {
                if (this.info.endPositionUs != Long.MIN_VALUE) {
                    this.mediaSource.releasePeriod(((ClippingMediaPeriod) this.mediaPeriod).mediaPeriod);
                } else {
                    this.mediaSource.releasePeriod(this.mediaPeriod);
                }
            } catch (Throwable e) {
                Log.e(ExoPlayerImplInternal.TAG, "Period release failed.", e);
            }
        }

        public boolean selectTracks() {
            TrackSelectorResult selectTracks = this.trackSelector.selectTracks(this.rendererCapabilities, this.mediaPeriod.getTrackGroups());
            if (selectTracks.isEquivalent(this.periodTrackSelectorResult)) {
                return false;
            }
            this.trackSelectorResult = selectTracks;
            return true;
        }

        public boolean shouldContinueLoading(long j) {
            long nextLoadPositionUs = !this.prepared ? 0 : this.mediaPeriod.getNextLoadPositionUs();
            if (nextLoadPositionUs == Long.MIN_VALUE) {
                return false;
            }
            return this.loadControl.shouldContinueLoading(nextLoadPositionUs - toPeriodTime(j));
        }

        public long toPeriodTime(long j) {
            return j - getRendererOffset();
        }

        public long toRendererTime(long j) {
            return getRendererOffset() + j;
        }

        public long updatePeriodTrackSelection(long j, boolean z) {
            return updatePeriodTrackSelection(j, z, new boolean[this.renderers.length]);
        }

        public long updatePeriodTrackSelection(long j, boolean z, boolean[] zArr) {
            int i;
            TrackSelectionArray trackSelectionArray = this.trackSelectorResult.selections;
            for (i = 0; i < trackSelectionArray.length; i++) {
                boolean[] zArr2 = this.mayRetainStreamFlags;
                boolean z2 = !z && this.trackSelectorResult.isEquivalent(this.periodTrackSelectorResult, i);
                zArr2[i] = z2;
            }
            long selectTracks = this.mediaPeriod.selectTracks(trackSelectionArray.getAll(), this.mayRetainStreamFlags, this.sampleStreams, zArr, j);
            this.periodTrackSelectorResult = this.trackSelectorResult;
            this.hasEnabledTracks = false;
            for (i = 0; i < this.sampleStreams.length; i++) {
                if (this.sampleStreams[i] != null) {
                    Assertions.checkState(trackSelectionArray.get(i) != null);
                    this.hasEnabledTracks = true;
                } else {
                    Assertions.checkState(trackSelectionArray.get(i) == null);
                }
            }
            this.loadControl.onTracksSelected(this.renderers, this.trackSelectorResult.groups, trackSelectionArray);
            return selectTracks;
        }
    }

    public static final class PlaybackInfo {
        public volatile long bufferedPositionUs;
        public final long contentPositionUs;
        public final MediaPeriodId periodId;
        public volatile long positionUs;
        public final long startPositionUs;

        public PlaybackInfo(int i, long j) {
            this(new MediaPeriodId(i), j);
        }

        public PlaybackInfo(MediaPeriodId mediaPeriodId, long j) {
            this(mediaPeriodId, j, C3446C.TIME_UNSET);
        }

        public PlaybackInfo(MediaPeriodId mediaPeriodId, long j, long j2) {
            this.periodId = mediaPeriodId;
            this.startPositionUs = j;
            this.contentPositionUs = j2;
            this.positionUs = j;
            this.bufferedPositionUs = j;
        }

        public PlaybackInfo copyWithPeriodIndex(int i) {
            PlaybackInfo playbackInfo = new PlaybackInfo(this.periodId.copyWithPeriodIndex(i), this.startPositionUs, this.contentPositionUs);
            playbackInfo.positionUs = this.positionUs;
            playbackInfo.bufferedPositionUs = this.bufferedPositionUs;
            return playbackInfo;
        }
    }

    private static final class SeekPosition {
        public final Timeline timeline;
        public final int windowIndex;
        public final long windowPositionUs;

        public SeekPosition(Timeline timeline, int i, long j) {
            this.timeline = timeline;
            this.windowIndex = i;
            this.windowPositionUs = j;
        }
    }

    public static final class SourceInfo {
        public final Object manifest;
        public final PlaybackInfo playbackInfo;
        public final int seekAcks;
        public final Timeline timeline;

        public SourceInfo(Timeline timeline, Object obj, PlaybackInfo playbackInfo, int i) {
            this.timeline = timeline;
            this.manifest = obj;
            this.playbackInfo = playbackInfo;
            this.seekAcks = i;
        }
    }

    public ExoPlayerImplInternal(Renderer[] rendererArr, TrackSelector trackSelector, LoadControl loadControl, boolean z, int i, Handler handler, PlaybackInfo playbackInfo, ExoPlayer exoPlayer) {
        this.renderers = rendererArr;
        this.trackSelector = trackSelector;
        this.loadControl = loadControl;
        this.playWhenReady = z;
        this.repeatMode = i;
        this.eventHandler = handler;
        this.playbackInfo = playbackInfo;
        this.player = exoPlayer;
        this.rendererCapabilities = new RendererCapabilities[rendererArr.length];
        for (int i2 = 0; i2 < rendererArr.length; i2++) {
            rendererArr[i2].setIndex(i2);
            this.rendererCapabilities[i2] = rendererArr[i2].getCapabilities();
        }
        this.standaloneMediaClock = new StandaloneMediaClock();
        this.enabledRenderers = new Renderer[0];
        this.window = new Window();
        this.period = new Period();
        this.mediaPeriodInfoSequence = new MediaPeriodInfoSequence();
        trackSelector.init(this);
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.internalPlaybackThread = new HandlerThread("ExoPlayerImplInternal:Handler", -16);
        this.internalPlaybackThread.start();
        this.handler = new Handler(this.internalPlaybackThread.getLooper(), this);
    }

    private void doSomeWork() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        updatePeriods();
        if (this.playingPeriodHolder == null) {
            maybeThrowPeriodPrepareError();
            scheduleNextWork(elapsedRealtime, 10);
            return;
        }
        TraceUtil.beginSection("doSomeWork");
        updatePlaybackPositions();
        this.playingPeriodHolder.mediaPeriod.discardBuffer(this.playbackInfo.positionUs);
        boolean z = true;
        Object obj = 1;
        for (Renderer renderer : this.enabledRenderers) {
            renderer.render(this.rendererPositionUs, this.elapsedRealtimeUs);
            obj = (obj == null || !renderer.isEnded()) ? null : 1;
            Object obj2 = (renderer.isReady() || renderer.isEnded()) ? 1 : null;
            if (obj2 == null) {
                renderer.maybeThrowStreamError();
            }
            z = z && obj2 != null;
        }
        if (!z) {
            maybeThrowPeriodPrepareError();
        }
        if (this.rendererMediaClock != null) {
            PlaybackParameters playbackParameters = this.rendererMediaClock.getPlaybackParameters();
            if (!playbackParameters.equals(this.playbackParameters)) {
                this.playbackParameters = playbackParameters;
                this.standaloneMediaClock.synchronize(this.rendererMediaClock);
                this.eventHandler.obtainMessage(7, playbackParameters).sendToTarget();
            }
        }
        long j = this.playingPeriodHolder.info.durationUs;
        if (obj != null && ((j == C3446C.TIME_UNSET || j <= this.playbackInfo.positionUs) && this.playingPeriodHolder.info.isFinal)) {
            setState(4);
            stopRenderers();
        } else if (this.state == 2) {
            boolean isTimelineReady = this.enabledRenderers.length > 0 ? z && this.loadingPeriodHolder.haveSufficientBuffer(this.rebuffering, this.rendererPositionUs) : isTimelineReady(j);
            if (isTimelineReady) {
                setState(3);
                if (this.playWhenReady) {
                    startRenderers();
                }
            }
        } else if (this.state == 3) {
            if (!(this.enabledRenderers.length > 0 ? z : isTimelineReady(j))) {
                this.rebuffering = this.playWhenReady;
                setState(2);
                stopRenderers();
            }
        }
        if (this.state == 2) {
            for (Renderer maybeThrowStreamError : this.enabledRenderers) {
                maybeThrowStreamError.maybeThrowStreamError();
            }
        }
        if ((this.playWhenReady && this.state == 3) || this.state == 2) {
            scheduleNextWork(elapsedRealtime, 10);
        } else if (this.enabledRenderers.length == 0 || this.state == 4) {
            this.handler.removeMessages(2);
        } else {
            scheduleNextWork(elapsedRealtime, 1000);
        }
        TraceUtil.endSection();
    }

    private void enableRenderers(boolean[] zArr, int i) {
        this.enabledRenderers = new Renderer[i];
        int i2 = 0;
        for (int i3 = 0; i3 < this.renderers.length; i3++) {
            Renderer renderer = this.renderers[i3];
            TrackSelection trackSelection = this.playingPeriodHolder.trackSelectorResult.selections.get(i3);
            if (trackSelection != null) {
                int i4 = i2 + 1;
                this.enabledRenderers[i2] = renderer;
                if (renderer.getState() == 0) {
                    RendererConfiguration rendererConfiguration = this.playingPeriodHolder.trackSelectorResult.rendererConfigurations[i3];
                    Object obj = (this.playWhenReady && this.state == 3) ? 1 : null;
                    boolean z = (zArr[i3] || obj == null) ? false : true;
                    Format[] formatArr = new Format[trackSelection.length()];
                    for (int i5 = 0; i5 < formatArr.length; i5++) {
                        formatArr[i5] = trackSelection.getFormat(i5);
                    }
                    renderer.enable(rendererConfiguration, formatArr, this.playingPeriodHolder.sampleStreams[i3], this.rendererPositionUs, z, this.playingPeriodHolder.getRendererOffset());
                    MediaClock mediaClock = renderer.getMediaClock();
                    if (mediaClock != null) {
                        if (this.rendererMediaClock != null) {
                            throw ExoPlaybackException.createForUnexpected(new IllegalStateException("Multiple renderer media clocks enabled."));
                        }
                        this.rendererMediaClock = mediaClock;
                        this.rendererMediaClockSource = renderer;
                        this.rendererMediaClock.setPlaybackParameters(this.playbackParameters);
                    }
                    if (obj != null) {
                        renderer.start();
                    }
                }
                i2 = i4;
            }
        }
    }

    private void ensureStopped(Renderer renderer) {
        if (renderer.getState() == 2) {
            renderer.stop();
        }
    }

    private Pair<Integer, Long> getPeriodPosition(int i, long j) {
        return this.timeline.getPeriodPosition(this.window, this.period, i, j);
    }

    private void handleContinueLoadingRequested(MediaPeriod mediaPeriod) {
        if (this.loadingPeriodHolder != null && this.loadingPeriodHolder.mediaPeriod == mediaPeriod) {
            maybeContinueLoading();
        }
    }

    private void handlePeriodPrepared(MediaPeriod mediaPeriod) {
        if (this.loadingPeriodHolder != null && this.loadingPeriodHolder.mediaPeriod == mediaPeriod) {
            this.loadingPeriodHolder.handlePrepared();
            if (this.playingPeriodHolder == null) {
                this.readingPeriodHolder = this.loadingPeriodHolder;
                resetRendererPosition(this.readingPeriodHolder.info.startPositionUs);
                setPlayingPeriodHolder(this.readingPeriodHolder);
            }
            maybeContinueLoading();
        }
    }

    private void handleSourceInfoRefreshEndedPlayback(Object obj) {
        handleSourceInfoRefreshEndedPlayback(obj, 0);
    }

    private void handleSourceInfoRefreshEndedPlayback(Object obj, int i) {
        this.playbackInfo = new PlaybackInfo(0, 0);
        notifySourceInfoRefresh(obj, i);
        this.playbackInfo = new PlaybackInfo(0, (long) C3446C.TIME_UNSET);
        setState(4);
        resetInternal(false);
    }

    private void handleSourceInfoRefreshed(Pair<Timeline, Object> pair) {
        long j = C3446C.TIME_UNSET;
        Timeline timeline = this.timeline;
        this.timeline = (Timeline) pair.first;
        this.mediaPeriodInfoSequence.setTimeline(this.timeline);
        Object obj = pair.second;
        int i;
        Pair periodPosition;
        MediaPeriodId resolvePeriodPositionForAds;
        if (timeline != null) {
            i = this.playbackInfo.periodId.periodIndex;
            MediaPeriodHolder mediaPeriodHolder = this.playingPeriodHolder != null ? this.playingPeriodHolder : this.loadingPeriodHolder;
            if (mediaPeriodHolder != null || i < timeline.getPeriodCount()) {
                int indexOfPeriod = this.timeline.getIndexOfPeriod(mediaPeriodHolder == null ? timeline.getPeriod(i, this.period, true).uid : mediaPeriodHolder.uid);
                if (indexOfPeriod == -1) {
                    indexOfPeriod = resolveSubsequentPeriod(i, timeline, this.timeline);
                    if (indexOfPeriod == -1) {
                        handleSourceInfoRefreshEndedPlayback(obj);
                        return;
                    }
                    periodPosition = getPeriodPosition(this.timeline.getPeriod(indexOfPeriod, this.period).windowIndex, C3446C.TIME_UNSET);
                    int intValue = ((Integer) periodPosition.first).intValue();
                    long longValue = ((Long) periodPosition.second).longValue();
                    this.timeline.getPeriod(intValue, this.period, true);
                    if (mediaPeriodHolder != null) {
                        Object obj2 = this.period.uid;
                        mediaPeriodHolder.info = mediaPeriodHolder.info.copyWithPeriodIndex(-1);
                        MediaPeriodHolder mediaPeriodHolder2 = mediaPeriodHolder;
                        while (mediaPeriodHolder2.next != null) {
                            mediaPeriodHolder2 = mediaPeriodHolder2.next;
                            if (mediaPeriodHolder2.uid.equals(obj2)) {
                                mediaPeriodHolder2.info = this.mediaPeriodInfoSequence.getUpdatedMediaPeriodInfo(mediaPeriodHolder2.info, intValue);
                            } else {
                                mediaPeriodHolder2.info = mediaPeriodHolder2.info.copyWithPeriodIndex(-1);
                            }
                        }
                    }
                    MediaPeriodId mediaPeriodId = new MediaPeriodId(intValue);
                    this.playbackInfo = new PlaybackInfo(mediaPeriodId, seekToPeriodPosition(mediaPeriodId, longValue));
                    notifySourceInfoRefresh(obj);
                    return;
                }
                if (indexOfPeriod != i) {
                    this.playbackInfo = this.playbackInfo.copyWithPeriodIndex(indexOfPeriod);
                }
                if (this.playbackInfo.periodId.isAd()) {
                    resolvePeriodPositionForAds = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(indexOfPeriod, this.playbackInfo.contentPositionUs);
                    if (!(resolvePeriodPositionForAds.isAd() && resolvePeriodPositionForAds.adIndexInAdGroup == this.playbackInfo.periodId.adIndexInAdGroup)) {
                        long seekToPeriodPosition = seekToPeriodPosition(resolvePeriodPositionForAds, this.playbackInfo.contentPositionUs);
                        if (resolvePeriodPositionForAds.isAd()) {
                            j = this.playbackInfo.contentPositionUs;
                        }
                        this.playbackInfo = new PlaybackInfo(resolvePeriodPositionForAds, seekToPeriodPosition, j);
                        notifySourceInfoRefresh(obj);
                        return;
                    }
                }
                if (mediaPeriodHolder == null) {
                    notifySourceInfoRefresh(obj);
                    return;
                }
                MediaPeriodHolder updatePeriodInfo = updatePeriodInfo(mediaPeriodHolder, indexOfPeriod);
                while (updatePeriodInfo.next != null) {
                    mediaPeriodHolder = updatePeriodInfo.next;
                    indexOfPeriod = this.timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode);
                    if (indexOfPeriod == -1 || !mediaPeriodHolder.uid.equals(this.timeline.getPeriod(indexOfPeriod, this.period, true).uid)) {
                        boolean z = this.readingPeriodHolder != null && this.readingPeriodHolder.index < mediaPeriodHolder.index;
                        if (z) {
                            this.loadingPeriodHolder = updatePeriodInfo;
                            this.loadingPeriodHolder.next = null;
                            releasePeriodHoldersFrom(mediaPeriodHolder);
                        } else {
                            this.playbackInfo = new PlaybackInfo(this.playingPeriodHolder.info.id, seekToPeriodPosition(this.playingPeriodHolder.info.id, this.playbackInfo.positionUs), this.playbackInfo.contentPositionUs);
                        }
                        notifySourceInfoRefresh(obj);
                        return;
                    }
                    updatePeriodInfo = updatePeriodInfo(mediaPeriodHolder, indexOfPeriod);
                }
                notifySourceInfoRefresh(obj);
                return;
            }
            notifySourceInfoRefresh(obj);
        } else if (this.pendingInitialSeekCount > 0) {
            periodPosition = resolveSeekPosition(this.pendingSeekPosition);
            i = this.pendingInitialSeekCount;
            this.pendingInitialSeekCount = 0;
            this.pendingSeekPosition = null;
            if (periodPosition == null) {
                handleSourceInfoRefreshEndedPlayback(obj, i);
                return;
            }
            r2 = ((Integer) periodPosition.first).intValue();
            j = ((Long) periodPosition.second).longValue();
            resolvePeriodPositionForAds = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(r2, j);
            this.playbackInfo = new PlaybackInfo(resolvePeriodPositionForAds, resolvePeriodPositionForAds.isAd() ? 0 : j, j);
            notifySourceInfoRefresh(obj, i);
        } else if (this.playbackInfo.startPositionUs != C3446C.TIME_UNSET) {
            notifySourceInfoRefresh(obj);
        } else if (this.timeline.isEmpty()) {
            handleSourceInfoRefreshEndedPlayback(obj);
        } else {
            periodPosition = getPeriodPosition(0, C3446C.TIME_UNSET);
            r2 = ((Integer) periodPosition.first).intValue();
            j = ((Long) periodPosition.second).longValue();
            resolvePeriodPositionForAds = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(r2, j);
            this.playbackInfo = new PlaybackInfo(resolvePeriodPositionForAds, resolvePeriodPositionForAds.isAd() ? 0 : j, j);
            notifySourceInfoRefresh(obj);
        }
    }

    private boolean isTimelineReady(long j) {
        return j == C3446C.TIME_UNSET || this.playbackInfo.positionUs < j || (this.playingPeriodHolder.next != null && (this.playingPeriodHolder.next.prepared || this.playingPeriodHolder.next.info.id.isAd()));
    }

    private void maybeContinueLoading() {
        boolean shouldContinueLoading = this.loadingPeriodHolder.shouldContinueLoading(this.rendererPositionUs);
        setIsLoading(shouldContinueLoading);
        if (shouldContinueLoading) {
            this.loadingPeriodHolder.continueLoading(this.rendererPositionUs);
        }
    }

    private void maybeThrowPeriodPrepareError() {
        if (this.loadingPeriodHolder != null && !this.loadingPeriodHolder.prepared) {
            if (this.readingPeriodHolder == null || this.readingPeriodHolder.next == this.loadingPeriodHolder) {
                Renderer[] rendererArr = this.enabledRenderers;
                int length = rendererArr.length;
                int i = 0;
                while (i < length) {
                    if (rendererArr[i].hasReadStreamToEnd()) {
                        i++;
                    } else {
                        return;
                    }
                }
                this.loadingPeriodHolder.mediaPeriod.maybeThrowPrepareError();
            }
        }
    }

    private void maybeUpdateLoadingPeriod() {
        MediaPeriodInfo firstMediaPeriodInfo;
        if (this.loadingPeriodHolder == null) {
            firstMediaPeriodInfo = this.mediaPeriodInfoSequence.getFirstMediaPeriodInfo(this.playbackInfo);
        } else if (!this.loadingPeriodHolder.info.isFinal && this.loadingPeriodHolder.isFullyBuffered() && this.loadingPeriodHolder.info.durationUs != C3446C.TIME_UNSET) {
            if (this.playingPeriodHolder == null || this.loadingPeriodHolder.index - this.playingPeriodHolder.index != 100) {
                firstMediaPeriodInfo = this.mediaPeriodInfoSequence.getNextMediaPeriodInfo(this.loadingPeriodHolder.info, this.loadingPeriodHolder.getRendererOffset(), this.rendererPositionUs);
            } else {
                return;
            }
        } else {
            return;
        }
        if (firstMediaPeriodInfo == null) {
            this.mediaSource.maybeThrowSourceInfoRefreshError();
            return;
        }
        MediaPeriodHolder mediaPeriodHolder = new MediaPeriodHolder(this.renderers, this.rendererCapabilities, this.loadingPeriodHolder == null ? 60000000 : this.loadingPeriodHolder.getRendererOffset() + this.loadingPeriodHolder.info.durationUs, this.trackSelector, this.loadControl, this.mediaSource, this.timeline.getPeriod(firstMediaPeriodInfo.id.periodIndex, this.period, true).uid, this.loadingPeriodHolder == null ? 0 : this.loadingPeriodHolder.index + 1, firstMediaPeriodInfo);
        if (this.loadingPeriodHolder != null) {
            this.loadingPeriodHolder.next = mediaPeriodHolder;
        }
        this.loadingPeriodHolder = mediaPeriodHolder;
        this.loadingPeriodHolder.mediaPeriod.prepare(this, firstMediaPeriodInfo.startPositionUs);
        setIsLoading(true);
    }

    private void notifySourceInfoRefresh(Object obj) {
        notifySourceInfoRefresh(obj, 0);
    }

    private void notifySourceInfoRefresh(Object obj, int i) {
        this.eventHandler.obtainMessage(6, new SourceInfo(this.timeline, obj, this.playbackInfo, i)).sendToTarget();
    }

    private void prepareInternal(MediaSource mediaSource, boolean z) {
        this.eventHandler.sendEmptyMessage(0);
        resetInternal(true);
        this.loadControl.onPrepared();
        if (z) {
            this.playbackInfo = new PlaybackInfo(0, (long) C3446C.TIME_UNSET);
        } else {
            this.playbackInfo = new PlaybackInfo(this.playbackInfo.periodId, this.playbackInfo.positionUs, this.playbackInfo.contentPositionUs);
        }
        this.mediaSource = mediaSource;
        mediaSource.prepareSource(this.player, true, this);
        setState(2);
        this.handler.sendEmptyMessage(2);
    }

    private void releaseInternal() {
        resetInternal(true);
        this.loadControl.onReleased();
        setState(1);
        synchronized (this) {
            this.released = true;
            notifyAll();
        }
    }

    private void releasePeriodHoldersFrom(MediaPeriodHolder mediaPeriodHolder) {
        while (mediaPeriodHolder != null) {
            mediaPeriodHolder.release();
            mediaPeriodHolder = mediaPeriodHolder.next;
        }
    }

    private void reselectTracksInternal() {
        if (this.playingPeriodHolder != null) {
            MediaPeriodHolder mediaPeriodHolder = this.playingPeriodHolder;
            boolean z = true;
            while (mediaPeriodHolder != null && mediaPeriodHolder.prepared) {
                if (mediaPeriodHolder.selectTracks()) {
                    if (z) {
                        z = this.readingPeriodHolder != this.playingPeriodHolder;
                        releasePeriodHoldersFrom(this.playingPeriodHolder.next);
                        this.playingPeriodHolder.next = null;
                        this.loadingPeriodHolder = this.playingPeriodHolder;
                        this.readingPeriodHolder = this.playingPeriodHolder;
                        boolean[] zArr = new boolean[this.renderers.length];
                        long updatePeriodTrackSelection = this.playingPeriodHolder.updatePeriodTrackSelection(this.playbackInfo.positionUs, z, zArr);
                        if (updatePeriodTrackSelection != this.playbackInfo.positionUs) {
                            this.playbackInfo.positionUs = updatePeriodTrackSelection;
                            resetRendererPosition(updatePeriodTrackSelection);
                        }
                        boolean[] zArr2 = new boolean[this.renderers.length];
                        int i = 0;
                        for (int i2 = 0; i2 < this.renderers.length; i2++) {
                            Renderer renderer = this.renderers[i2];
                            zArr2[i2] = renderer.getState() != 0;
                            SampleStream sampleStream = this.playingPeriodHolder.sampleStreams[i2];
                            if (sampleStream != null) {
                                i++;
                            }
                            if (zArr2[i2]) {
                                if (sampleStream != renderer.getStream()) {
                                    if (renderer == this.rendererMediaClockSource) {
                                        if (sampleStream == null) {
                                            this.standaloneMediaClock.synchronize(this.rendererMediaClock);
                                        }
                                        this.rendererMediaClock = null;
                                        this.rendererMediaClockSource = null;
                                    }
                                    ensureStopped(renderer);
                                    renderer.disable();
                                } else if (zArr[i2]) {
                                    renderer.resetPosition(this.rendererPositionUs);
                                }
                            }
                        }
                        this.eventHandler.obtainMessage(3, mediaPeriodHolder.trackSelectorResult).sendToTarget();
                        enableRenderers(zArr2, i);
                    } else {
                        this.loadingPeriodHolder = mediaPeriodHolder;
                        for (MediaPeriodHolder mediaPeriodHolder2 = this.loadingPeriodHolder.next; mediaPeriodHolder2 != null; mediaPeriodHolder2 = mediaPeriodHolder2.next) {
                            mediaPeriodHolder2.release();
                        }
                        this.loadingPeriodHolder.next = null;
                        if (this.loadingPeriodHolder.prepared) {
                            this.loadingPeriodHolder.updatePeriodTrackSelection(Math.max(this.loadingPeriodHolder.info.startPositionUs, this.loadingPeriodHolder.toPeriodTime(this.rendererPositionUs)), false);
                        }
                    }
                    maybeContinueLoading();
                    updatePlaybackPositions();
                    this.handler.sendEmptyMessage(2);
                    return;
                }
                if (mediaPeriodHolder == this.readingPeriodHolder) {
                    z = false;
                }
                mediaPeriodHolder = mediaPeriodHolder.next;
            }
        }
    }

    private void resetInternal(boolean z) {
        Throwable e;
        this.handler.removeMessages(2);
        this.rebuffering = false;
        this.standaloneMediaClock.stop();
        this.rendererMediaClock = null;
        this.rendererMediaClockSource = null;
        this.rendererPositionUs = 60000000;
        for (Renderer renderer : this.enabledRenderers) {
            try {
                ensureStopped(renderer);
                renderer.disable();
            } catch (ExoPlaybackException e2) {
                e = e2;
            } catch (RuntimeException e3) {
                e = e3;
            }
        }
        this.enabledRenderers = new Renderer[0];
        releasePeriodHoldersFrom(this.playingPeriodHolder != null ? this.playingPeriodHolder : this.loadingPeriodHolder);
        this.loadingPeriodHolder = null;
        this.readingPeriodHolder = null;
        this.playingPeriodHolder = null;
        setIsLoading(false);
        if (z) {
            if (this.mediaSource != null) {
                this.mediaSource.releaseSource();
                this.mediaSource = null;
            }
            this.mediaPeriodInfoSequence.setTimeline(null);
            this.timeline = null;
            return;
        }
        return;
        Log.e(TAG, "Stop failed.", e);
    }

    private void resetRendererPosition(long j) {
        this.rendererPositionUs = this.playingPeriodHolder == null ? 60000000 + j : this.playingPeriodHolder.toRendererTime(j);
        this.standaloneMediaClock.setPositionUs(this.rendererPositionUs);
        for (Renderer resetPosition : this.enabledRenderers) {
            resetPosition.resetPosition(this.rendererPositionUs);
        }
    }

    private Pair<Integer, Long> resolveSeekPosition(SeekPosition seekPosition) {
        Timeline timeline = seekPosition.timeline;
        if (timeline.isEmpty()) {
            timeline = this.timeline;
        }
        try {
            Pair<Integer, Long> periodPosition = timeline.getPeriodPosition(this.window, this.period, seekPosition.windowIndex, seekPosition.windowPositionUs);
            if (this.timeline == timeline) {
                return periodPosition;
            }
            int indexOfPeriod = this.timeline.getIndexOfPeriod(timeline.getPeriod(((Integer) periodPosition.first).intValue(), this.period, true).uid);
            if (indexOfPeriod != -1) {
                return Pair.create(Integer.valueOf(indexOfPeriod), periodPosition.second);
            }
            int resolveSubsequentPeriod = resolveSubsequentPeriod(((Integer) periodPosition.first).intValue(), timeline, this.timeline);
            return resolveSubsequentPeriod != -1 ? getPeriodPosition(this.timeline.getPeriod(resolveSubsequentPeriod, this.period).windowIndex, C3446C.TIME_UNSET) : null;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalSeekPositionException(this.timeline, seekPosition.windowIndex, seekPosition.windowPositionUs);
        }
    }

    private int resolveSubsequentPeriod(int i, Timeline timeline, Timeline timeline2) {
        int periodCount = timeline.getPeriodCount();
        int i2 = -1;
        for (int i3 = 0; i3 < periodCount && i2 == -1; i3++) {
            i = timeline.getNextPeriodIndex(i, this.period, this.window, this.repeatMode);
            if (i == -1) {
                break;
            }
            i2 = timeline2.getIndexOfPeriod(timeline.getPeriod(i, this.period, true).uid);
        }
        return i2;
    }

    private void scheduleNextWork(long j, long j2) {
        this.handler.removeMessages(2);
        long elapsedRealtime = (j + j2) - SystemClock.elapsedRealtime();
        if (elapsedRealtime <= 0) {
            this.handler.sendEmptyMessage(2);
        } else {
            this.handler.sendEmptyMessageDelayed(2, elapsedRealtime);
        }
    }

    private void seekToInternal(SeekPosition seekPosition) {
        long j;
        int i;
        if (this.timeline == null) {
            this.pendingInitialSeekCount++;
            this.pendingSeekPosition = seekPosition;
            return;
        }
        Pair resolveSeekPosition = resolveSeekPosition(seekPosition);
        if (resolveSeekPosition == null) {
            this.playbackInfo = new PlaybackInfo(0, 0);
            this.eventHandler.obtainMessage(4, 1, 0, this.playbackInfo).sendToTarget();
            this.playbackInfo = new PlaybackInfo(0, (long) C3446C.TIME_UNSET);
            setState(4);
            resetInternal(false);
            return;
        }
        int i2 = seekPosition.windowPositionUs == C3446C.TIME_UNSET ? 1 : 0;
        int intValue = ((Integer) resolveSeekPosition.first).intValue();
        long longValue = ((Long) resolveSeekPosition.second).longValue();
        MediaPeriodId resolvePeriodPositionForAds = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(intValue, longValue);
        if (resolvePeriodPositionForAds.isAd()) {
            j = 0;
            i = 1;
        } else {
            i = i2;
            j = longValue;
        }
        try {
            if (resolvePeriodPositionForAds.equals(this.playbackInfo.periodId) && j / 1000 == this.playbackInfo.positionUs / 1000) {
                this.playbackInfo = new PlaybackInfo(resolvePeriodPositionForAds, j, longValue);
                this.eventHandler.obtainMessage(4, i != 0 ? 1 : 0, 0, this.playbackInfo).sendToTarget();
                return;
            }
            long seekToPeriodPosition = seekToPeriodPosition(resolvePeriodPositionForAds, j);
            int i3 = i | (j != seekToPeriodPosition ? 1 : 0);
            this.playbackInfo = new PlaybackInfo(resolvePeriodPositionForAds, seekToPeriodPosition, longValue);
            this.eventHandler.obtainMessage(4, i3 != 0 ? 1 : 0, 0, this.playbackInfo).sendToTarget();
        } catch (Throwable th) {
            Throwable th2 = th;
            this.playbackInfo = new PlaybackInfo(resolvePeriodPositionForAds, j, longValue);
            this.eventHandler.obtainMessage(4, i != 0 ? 1 : 0, 0, this.playbackInfo).sendToTarget();
        }
    }

    private long seekToPeriodPosition(MediaPeriodId mediaPeriodId, long j) {
        MediaPeriodHolder mediaPeriodHolder;
        stopRenderers();
        this.rebuffering = false;
        setState(2);
        if (this.playingPeriodHolder != null) {
            MediaPeriodHolder mediaPeriodHolder2 = this.playingPeriodHolder;
            mediaPeriodHolder = null;
            while (mediaPeriodHolder2 != null) {
                if (mediaPeriodHolder == null && shouldKeepPeriodHolder(mediaPeriodId, j, mediaPeriodHolder2)) {
                    mediaPeriodHolder = mediaPeriodHolder2;
                } else {
                    mediaPeriodHolder2.release();
                }
                mediaPeriodHolder2 = mediaPeriodHolder2.next;
            }
        } else if (this.loadingPeriodHolder != null) {
            this.loadingPeriodHolder.release();
            mediaPeriodHolder = null;
        } else {
            mediaPeriodHolder = null;
        }
        if (!(this.playingPeriodHolder == mediaPeriodHolder && this.playingPeriodHolder == this.readingPeriodHolder)) {
            for (Renderer disable : this.enabledRenderers) {
                disable.disable();
            }
            this.enabledRenderers = new Renderer[0];
            this.rendererMediaClock = null;
            this.rendererMediaClockSource = null;
            this.playingPeriodHolder = null;
        }
        if (mediaPeriodHolder != null) {
            mediaPeriodHolder.next = null;
            this.loadingPeriodHolder = mediaPeriodHolder;
            this.readingPeriodHolder = mediaPeriodHolder;
            setPlayingPeriodHolder(mediaPeriodHolder);
            if (this.playingPeriodHolder.hasEnabledTracks) {
                j = this.playingPeriodHolder.mediaPeriod.seekToUs(j);
            }
            resetRendererPosition(j);
            maybeContinueLoading();
        } else {
            this.loadingPeriodHolder = null;
            this.readingPeriodHolder = null;
            this.playingPeriodHolder = null;
            resetRendererPosition(j);
        }
        this.handler.sendEmptyMessage(2);
        return j;
    }

    private void sendMessagesInternal(ExoPlayerMessage[] exoPlayerMessageArr) {
        try {
            for (ExoPlayerMessage exoPlayerMessage : exoPlayerMessageArr) {
                exoPlayerMessage.target.handleMessage(exoPlayerMessage.messageType, exoPlayerMessage.message);
            }
            if (this.state == 3 || this.state == 2) {
                this.handler.sendEmptyMessage(2);
            }
            synchronized (this) {
                this.customMessagesProcessed++;
                notifyAll();
            }
        } catch (Throwable th) {
            synchronized (this) {
                this.customMessagesProcessed++;
                notifyAll();
            }
        }
    }

    private void setIsLoading(boolean z) {
        if (this.isLoading != z) {
            this.isLoading = z;
            this.eventHandler.obtainMessage(2, z ? 1 : 0, 0).sendToTarget();
        }
    }

    private void setPlayWhenReadyInternal(boolean z) {
        this.rebuffering = false;
        this.playWhenReady = z;
        if (!z) {
            stopRenderers();
            updatePlaybackPositions();
        } else if (this.state == 3) {
            startRenderers();
            this.handler.sendEmptyMessage(2);
        } else if (this.state == 2) {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setPlaybackParametersInternal(PlaybackParameters playbackParameters) {
        PlaybackParameters playbackParameters2 = this.rendererMediaClock != null ? this.rendererMediaClock.setPlaybackParameters(playbackParameters) : this.standaloneMediaClock.setPlaybackParameters(playbackParameters);
        this.playbackParameters = playbackParameters2;
        this.eventHandler.obtainMessage(7, playbackParameters2).sendToTarget();
    }

    private void setPlayingPeriodHolder(MediaPeriodHolder mediaPeriodHolder) {
        if (this.playingPeriodHolder != mediaPeriodHolder) {
            boolean[] zArr = new boolean[this.renderers.length];
            int i = 0;
            int i2 = 0;
            while (i < this.renderers.length) {
                Renderer renderer = this.renderers[i];
                zArr[i] = renderer.getState() != 0;
                TrackSelection trackSelection = mediaPeriodHolder.trackSelectorResult.selections.get(i);
                if (trackSelection != null) {
                    i2++;
                }
                if (zArr[i] && (trackSelection == null || (renderer.isCurrentStreamFinal() && renderer.getStream() == this.playingPeriodHolder.sampleStreams[i]))) {
                    if (renderer == this.rendererMediaClockSource) {
                        this.standaloneMediaClock.synchronize(this.rendererMediaClock);
                        this.rendererMediaClock = null;
                        this.rendererMediaClockSource = null;
                    }
                    ensureStopped(renderer);
                    renderer.disable();
                }
                i++;
            }
            this.playingPeriodHolder = mediaPeriodHolder;
            this.eventHandler.obtainMessage(3, mediaPeriodHolder.trackSelectorResult).sendToTarget();
            enableRenderers(zArr, i2);
        }
    }

    private void setRepeatModeInternal(int i) {
        this.repeatMode = i;
        this.mediaPeriodInfoSequence.setRepeatMode(i);
        MediaPeriodHolder mediaPeriodHolder = this.playingPeriodHolder != null ? this.playingPeriodHolder : this.loadingPeriodHolder;
        if (mediaPeriodHolder != null) {
            int nextPeriodIndex;
            int i2;
            while (true) {
                nextPeriodIndex = this.timeline.getNextPeriodIndex(mediaPeriodHolder.info.id.periodIndex, this.period, this.window, i);
                while (mediaPeriodHolder.next != null && !mediaPeriodHolder.info.isLastInTimelinePeriod) {
                    mediaPeriodHolder = mediaPeriodHolder.next;
                }
                if (nextPeriodIndex == -1 || mediaPeriodHolder.next == null || mediaPeriodHolder.next.info.id.periodIndex != nextPeriodIndex) {
                    i2 = this.loadingPeriodHolder.index;
                } else {
                    mediaPeriodHolder = mediaPeriodHolder.next;
                }
            }
            i2 = this.loadingPeriodHolder.index;
            nextPeriodIndex = this.readingPeriodHolder != null ? this.readingPeriodHolder.index : -1;
            if (mediaPeriodHolder.next != null) {
                releasePeriodHoldersFrom(mediaPeriodHolder.next);
                mediaPeriodHolder.next = null;
            }
            mediaPeriodHolder.info = this.mediaPeriodInfoSequence.getUpdatedMediaPeriodInfo(mediaPeriodHolder.info);
            if ((i2 <= mediaPeriodHolder.index ? 1 : null) == null) {
                this.loadingPeriodHolder = mediaPeriodHolder;
            }
            Object obj = (nextPeriodIndex == -1 || nextPeriodIndex > mediaPeriodHolder.index) ? null : 1;
            if (obj == null && this.playingPeriodHolder != null) {
                MediaPeriodId mediaPeriodId = this.playingPeriodHolder.info.id;
                this.playbackInfo = new PlaybackInfo(mediaPeriodId, seekToPeriodPosition(mediaPeriodId, this.playbackInfo.positionUs), this.playbackInfo.contentPositionUs);
            }
        }
    }

    private void setState(int i) {
        if (this.state != i) {
            this.state = i;
            this.eventHandler.obtainMessage(1, i, 0).sendToTarget();
        }
    }

    private boolean shouldKeepPeriodHolder(MediaPeriodId mediaPeriodId, long j, MediaPeriodHolder mediaPeriodHolder) {
        if (mediaPeriodId.equals(mediaPeriodHolder.info.id) && mediaPeriodHolder.prepared) {
            this.timeline.getPeriod(mediaPeriodHolder.info.id.periodIndex, this.period);
            int adGroupIndexAfterPositionUs = this.period.getAdGroupIndexAfterPositionUs(j);
            if (adGroupIndexAfterPositionUs == -1 || this.period.getAdGroupTimeUs(adGroupIndexAfterPositionUs) == mediaPeriodHolder.info.endPositionUs) {
                return true;
            }
        }
        return false;
    }

    private void startRenderers() {
        int i = 0;
        this.rebuffering = false;
        this.standaloneMediaClock.start();
        Renderer[] rendererArr = this.enabledRenderers;
        int length = rendererArr.length;
        while (i < length) {
            rendererArr[i].start();
            i++;
        }
    }

    private void stopInternal() {
        resetInternal(true);
        this.loadControl.onStopped();
        setState(1);
    }

    private void stopRenderers() {
        this.standaloneMediaClock.stop();
        for (Renderer ensureStopped : this.enabledRenderers) {
            ensureStopped(ensureStopped);
        }
    }

    private MediaPeriodHolder updatePeriodInfo(MediaPeriodHolder mediaPeriodHolder, int i) {
        while (true) {
            mediaPeriodHolder.info = this.mediaPeriodInfoSequence.getUpdatedMediaPeriodInfo(mediaPeriodHolder.info, i);
            if (mediaPeriodHolder.info.isLastInTimelinePeriod || mediaPeriodHolder.next == null) {
                return mediaPeriodHolder;
            }
            mediaPeriodHolder = mediaPeriodHolder.next;
        }
        return mediaPeriodHolder;
    }

    private void updatePeriods() {
        if (this.timeline == null) {
            this.mediaSource.maybeThrowSourceInfoRefreshError();
            return;
        }
        maybeUpdateLoadingPeriod();
        if (this.loadingPeriodHolder == null || this.loadingPeriodHolder.isFullyBuffered()) {
            setIsLoading(false);
        } else if (!(this.loadingPeriodHolder == null || this.isLoading)) {
            maybeContinueLoading();
        }
        if (this.playingPeriodHolder != null) {
            while (this.playingPeriodHolder != this.readingPeriodHolder && this.rendererPositionUs >= this.playingPeriodHolder.next.rendererPositionOffsetUs) {
                this.playingPeriodHolder.release();
                setPlayingPeriodHolder(this.playingPeriodHolder.next);
                this.playbackInfo = new PlaybackInfo(this.playingPeriodHolder.info.id, this.playingPeriodHolder.info.startPositionUs, this.playingPeriodHolder.info.contentPositionUs);
                updatePlaybackPositions();
                this.eventHandler.obtainMessage(5, this.playbackInfo).sendToTarget();
            }
            int i;
            Renderer renderer;
            SampleStream sampleStream;
            if (this.readingPeriodHolder.info.isFinal) {
                for (i = 0; i < this.renderers.length; i++) {
                    renderer = this.renderers[i];
                    sampleStream = this.readingPeriodHolder.sampleStreams[i];
                    if (sampleStream != null && renderer.getStream() == sampleStream && renderer.hasReadStreamToEnd()) {
                        renderer.setCurrentStreamFinal();
                    }
                }
                return;
            }
            i = 0;
            while (i < this.renderers.length) {
                renderer = this.renderers[i];
                sampleStream = this.readingPeriodHolder.sampleStreams[i];
                if (renderer.getStream() != sampleStream) {
                    return;
                }
                if (sampleStream == null || renderer.hasReadStreamToEnd()) {
                    i++;
                } else {
                    return;
                }
            }
            if (this.readingPeriodHolder.next != null && this.readingPeriodHolder.next.prepared) {
                TrackSelectorResult trackSelectorResult = this.readingPeriodHolder.trackSelectorResult;
                this.readingPeriodHolder = this.readingPeriodHolder.next;
                TrackSelectorResult trackSelectorResult2 = this.readingPeriodHolder.trackSelectorResult;
                boolean z = this.readingPeriodHolder.mediaPeriod.readDiscontinuity() != C3446C.TIME_UNSET;
                for (int i2 = 0; i2 < this.renderers.length; i2++) {
                    Renderer renderer2 = this.renderers[i2];
                    if (trackSelectorResult.selections.get(i2) != null) {
                        if (z) {
                            renderer2.setCurrentStreamFinal();
                        } else if (!renderer2.isCurrentStreamFinal()) {
                            TrackSelection trackSelection = trackSelectorResult2.selections.get(i2);
                            Object obj = trackSelectorResult.rendererConfigurations[i2];
                            RendererConfiguration rendererConfiguration = trackSelectorResult2.rendererConfigurations[i2];
                            if (trackSelection == null || !rendererConfiguration.equals(obj)) {
                                renderer2.setCurrentStreamFinal();
                            } else {
                                Format[] formatArr = new Format[trackSelection.length()];
                                for (int i3 = 0; i3 < formatArr.length; i3++) {
                                    formatArr[i3] = trackSelection.getFormat(i3);
                                }
                                renderer2.replaceStream(formatArr, this.readingPeriodHolder.sampleStreams[i2], this.readingPeriodHolder.getRendererOffset());
                            }
                        }
                    }
                }
            }
        }
    }

    private void updatePlaybackPositions() {
        if (this.playingPeriodHolder != null) {
            long readDiscontinuity = this.playingPeriodHolder.mediaPeriod.readDiscontinuity();
            if (readDiscontinuity != C3446C.TIME_UNSET) {
                resetRendererPosition(readDiscontinuity);
            } else {
                if (this.rendererMediaClockSource == null || this.rendererMediaClockSource.isEnded()) {
                    this.rendererPositionUs = this.standaloneMediaClock.getPositionUs();
                } else {
                    this.rendererPositionUs = this.rendererMediaClock.getPositionUs();
                    this.standaloneMediaClock.setPositionUs(this.rendererPositionUs);
                }
                readDiscontinuity = this.playingPeriodHolder.toPeriodTime(this.rendererPositionUs);
            }
            this.playbackInfo.positionUs = readDiscontinuity;
            this.elapsedRealtimeUs = SystemClock.elapsedRealtime() * 1000;
            readDiscontinuity = this.enabledRenderers.length == 0 ? Long.MIN_VALUE : this.playingPeriodHolder.mediaPeriod.getBufferedPositionUs();
            PlaybackInfo playbackInfo = this.playbackInfo;
            if (readDiscontinuity == Long.MIN_VALUE) {
                readDiscontinuity = this.playingPeriodHolder.info.durationUs;
            }
            playbackInfo.bufferedPositionUs = readDiscontinuity;
        }
    }

    public synchronized void blockingSendMessages(ExoPlayerMessage... exoPlayerMessageArr) {
        if (this.released) {
            Log.w(TAG, "Ignoring messages sent after release.");
        } else {
            int i = this.customMessagesSent;
            this.customMessagesSent = i + 1;
            this.handler.obtainMessage(11, exoPlayerMessageArr).sendToTarget();
            Object obj = null;
            while (this.customMessagesProcessed <= i) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    obj = 1;
                }
            }
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Looper getPlaybackLooper() {
        return this.internalPlaybackThread.getLooper();
    }

    public boolean handleMessage(Message message) {
        boolean z = false;
        try {
            switch (message.what) {
                case 0:
                    MediaSource mediaSource = (MediaSource) message.obj;
                    if (message.arg1 != 0) {
                        z = true;
                    }
                    prepareInternal(mediaSource, z);
                    return true;
                case 1:
                    if (message.arg1 != 0) {
                        z = true;
                    }
                    setPlayWhenReadyInternal(z);
                    return true;
                case 2:
                    doSomeWork();
                    return true;
                case 3:
                    seekToInternal((SeekPosition) message.obj);
                    return true;
                case 4:
                    setPlaybackParametersInternal((PlaybackParameters) message.obj);
                    return true;
                case 5:
                    stopInternal();
                    return true;
                case 6:
                    releaseInternal();
                    return true;
                case 7:
                    handleSourceInfoRefreshed((Pair) message.obj);
                    return true;
                case 8:
                    handlePeriodPrepared((MediaPeriod) message.obj);
                    return true;
                case 9:
                    handleContinueLoadingRequested((MediaPeriod) message.obj);
                    return true;
                case 10:
                    reselectTracksInternal();
                    return true;
                case 11:
                    sendMessagesInternal((ExoPlayerMessage[]) message.obj);
                    return true;
                case 12:
                    setRepeatModeInternal(message.arg1);
                    return true;
                default:
                    return false;
            }
        } catch (Throwable e) {
            Log.e(TAG, "Renderer error.", e);
            this.eventHandler.obtainMessage(8, e).sendToTarget();
            stopInternal();
            return true;
        } catch (Throwable e2) {
            Log.e(TAG, "Source error.", e2);
            this.eventHandler.obtainMessage(8, ExoPlaybackException.createForSource(e2)).sendToTarget();
            stopInternal();
            return true;
        } catch (Throwable e22) {
            Log.e(TAG, "Internal runtime error.", e22);
            this.eventHandler.obtainMessage(8, ExoPlaybackException.createForUnexpected(e22)).sendToTarget();
            stopInternal();
            return true;
        }
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.handler.obtainMessage(9, mediaPeriod).sendToTarget();
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        this.handler.obtainMessage(8, mediaPeriod).sendToTarget();
    }

    public void onSourceInfoRefreshed(Timeline timeline, Object obj) {
        this.handler.obtainMessage(7, Pair.create(timeline, obj)).sendToTarget();
    }

    public void onTrackSelectionsInvalidated() {
        this.handler.sendEmptyMessage(10);
    }

    public void prepare(MediaSource mediaSource, boolean z) {
        this.handler.obtainMessage(0, z ? 1 : 0, 0, mediaSource).sendToTarget();
    }

    public synchronized void release() {
        if (!this.released) {
            this.handler.sendEmptyMessage(6);
            Object obj = null;
            while (!this.released) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    obj = 1;
                }
            }
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
            this.internalPlaybackThread.quit();
        }
    }

    public void seekTo(Timeline timeline, int i, long j) {
        this.handler.obtainMessage(3, new SeekPosition(timeline, i, j)).sendToTarget();
    }

    public void sendMessages(ExoPlayerMessage... exoPlayerMessageArr) {
        if (this.released) {
            Log.w(TAG, "Ignoring messages sent after release.");
            return;
        }
        this.customMessagesSent++;
        this.handler.obtainMessage(11, exoPlayerMessageArr).sendToTarget();
    }

    public void setPlayWhenReady(boolean z) {
        this.handler.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void setRepeatMode(int i) {
        this.handler.obtainMessage(12, i, 0).sendToTarget();
    }

    public void stop() {
        this.handler.sendEmptyMessage(5);
    }
}
