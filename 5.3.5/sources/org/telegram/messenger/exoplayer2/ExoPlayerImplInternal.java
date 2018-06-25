package org.telegram.messenger.exoplayer2;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.MediaPeriodInfoSequence.MediaPeriodInfo;
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

final class ExoPlayerImplInternal implements Callback, MediaPeriod.Callback, InvalidationListener, Listener {
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
    private final Timeline$Period period;
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

        public MediaPeriodHolder(Renderer[] renderers, RendererCapabilities[] rendererCapabilities, long rendererPositionOffsetUs, TrackSelector trackSelector, LoadControl loadControl, MediaSource mediaSource, Object periodUid, int index, MediaPeriodInfo info) {
            this.renderers = renderers;
            this.rendererCapabilities = rendererCapabilities;
            this.rendererPositionOffsetUs = rendererPositionOffsetUs;
            this.trackSelector = trackSelector;
            this.loadControl = loadControl;
            this.mediaSource = mediaSource;
            this.uid = Assertions.checkNotNull(periodUid);
            this.index = index;
            this.info = info;
            this.sampleStreams = new SampleStream[renderers.length];
            this.mayRetainStreamFlags = new boolean[renderers.length];
            MediaPeriod mediaPeriod = mediaSource.createPeriod(info.id, loadControl.getAllocator());
            if (info.endPositionUs != Long.MIN_VALUE) {
                ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(mediaPeriod, true);
                clippingMediaPeriod.setClipping(0, info.endPositionUs);
                mediaPeriod = clippingMediaPeriod;
            }
            this.mediaPeriod = mediaPeriod;
        }

        public long toRendererTime(long periodTimeUs) {
            return getRendererOffset() + periodTimeUs;
        }

        public long toPeriodTime(long rendererTimeUs) {
            return rendererTimeUs - getRendererOffset();
        }

        public long getRendererOffset() {
            return this.index == 0 ? this.rendererPositionOffsetUs : this.rendererPositionOffsetUs - this.info.startPositionUs;
        }

        public boolean isFullyBuffered() {
            return this.prepared && (!this.hasEnabledTracks || this.mediaPeriod.getBufferedPositionUs() == Long.MIN_VALUE);
        }

        public boolean haveSufficientBuffer(boolean rebuffering, long rendererPositionUs) {
            long bufferedPositionUs;
            if (this.prepared) {
                bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
            } else {
                bufferedPositionUs = this.info.startPositionUs;
            }
            if (bufferedPositionUs == Long.MIN_VALUE) {
                if (this.info.isFinal) {
                    return true;
                }
                bufferedPositionUs = this.info.durationUs;
            }
            return this.loadControl.shouldStartPlayback(bufferedPositionUs - toPeriodTime(rendererPositionUs), rebuffering);
        }

        public void handlePrepared() throws ExoPlaybackException {
            this.prepared = true;
            selectTracks();
            this.info = this.info.copyWithStartPositionUs(updatePeriodTrackSelection(this.info.startPositionUs, false));
        }

        public boolean shouldContinueLoading(long rendererPositionUs) {
            long nextLoadPositionUs = !this.prepared ? 0 : this.mediaPeriod.getNextLoadPositionUs();
            if (nextLoadPositionUs == Long.MIN_VALUE) {
                return false;
            }
            return this.loadControl.shouldContinueLoading(nextLoadPositionUs - toPeriodTime(rendererPositionUs));
        }

        public void continueLoading(long rendererPositionUs) {
            this.mediaPeriod.continueLoading(toPeriodTime(rendererPositionUs));
        }

        public boolean selectTracks() throws ExoPlaybackException {
            TrackSelectorResult selectorResult = this.trackSelector.selectTracks(this.rendererCapabilities, this.mediaPeriod.getTrackGroups());
            if (selectorResult.isEquivalent(this.periodTrackSelectorResult)) {
                return false;
            }
            this.trackSelectorResult = selectorResult;
            return true;
        }

        public long updatePeriodTrackSelection(long positionUs, boolean forceRecreateStreams) {
            return updatePeriodTrackSelection(positionUs, forceRecreateStreams, new boolean[this.renderers.length]);
        }

        public long updatePeriodTrackSelection(long positionUs, boolean forceRecreateStreams, boolean[] streamResetFlags) {
            boolean z;
            TrackSelectionArray trackSelections = this.trackSelectorResult.selections;
            int i = 0;
            while (i < trackSelections.length) {
                boolean[] zArr = this.mayRetainStreamFlags;
                if (forceRecreateStreams || !this.trackSelectorResult.isEquivalent(this.periodTrackSelectorResult, i)) {
                    z = false;
                } else {
                    z = true;
                }
                zArr[i] = z;
                i++;
            }
            positionUs = this.mediaPeriod.selectTracks(trackSelections.getAll(), this.mayRetainStreamFlags, this.sampleStreams, streamResetFlags, positionUs);
            this.periodTrackSelectorResult = this.trackSelectorResult;
            this.hasEnabledTracks = false;
            for (i = 0; i < this.sampleStreams.length; i++) {
                if (this.sampleStreams[i] != null) {
                    if (trackSelections.get(i) != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Assertions.checkState(z);
                    this.hasEnabledTracks = true;
                } else {
                    Assertions.checkState(trackSelections.get(i) == null);
                }
            }
            this.loadControl.onTracksSelected(this.renderers, this.trackSelectorResult.groups, trackSelections);
            return positionUs;
        }

        public void release() {
            try {
                if (this.info.endPositionUs != Long.MIN_VALUE) {
                    this.mediaSource.releasePeriod(((ClippingMediaPeriod) this.mediaPeriod).mediaPeriod);
                } else {
                    this.mediaSource.releasePeriod(this.mediaPeriod);
                }
            } catch (RuntimeException e) {
                Log.e(ExoPlayerImplInternal.TAG, "Period release failed.", e);
            }
        }
    }

    public static final class PlaybackInfo {
        public volatile long bufferedPositionUs;
        public final long contentPositionUs;
        public final MediaPeriodId periodId;
        public volatile long positionUs;
        public final long startPositionUs;

        public PlaybackInfo(int periodIndex, long startPositionUs) {
            this(new MediaPeriodId(periodIndex), startPositionUs);
        }

        public PlaybackInfo(MediaPeriodId periodId, long startPositionUs) {
            this(periodId, startPositionUs, C0907C.TIME_UNSET);
        }

        public PlaybackInfo(MediaPeriodId periodId, long startPositionUs, long contentPositionUs) {
            this.periodId = periodId;
            this.startPositionUs = startPositionUs;
            this.contentPositionUs = contentPositionUs;
            this.positionUs = startPositionUs;
            this.bufferedPositionUs = startPositionUs;
        }

        public PlaybackInfo copyWithPeriodIndex(int periodIndex) {
            PlaybackInfo playbackInfo = new PlaybackInfo(this.periodId.copyWithPeriodIndex(periodIndex), this.startPositionUs, this.contentPositionUs);
            playbackInfo.positionUs = this.positionUs;
            playbackInfo.bufferedPositionUs = this.bufferedPositionUs;
            return playbackInfo;
        }
    }

    private static final class SeekPosition {
        public final Timeline timeline;
        public final int windowIndex;
        public final long windowPositionUs;

        public SeekPosition(Timeline timeline, int windowIndex, long windowPositionUs) {
            this.timeline = timeline;
            this.windowIndex = windowIndex;
            this.windowPositionUs = windowPositionUs;
        }
    }

    public static final class SourceInfo {
        public final Object manifest;
        public final PlaybackInfo playbackInfo;
        public final int seekAcks;
        public final Timeline timeline;

        public SourceInfo(Timeline timeline, Object manifest, PlaybackInfo playbackInfo, int seekAcks) {
            this.timeline = timeline;
            this.manifest = manifest;
            this.playbackInfo = playbackInfo;
            this.seekAcks = seekAcks;
        }
    }

    public ExoPlayerImplInternal(Renderer[] renderers, TrackSelector trackSelector, LoadControl loadControl, boolean playWhenReady, int repeatMode, Handler eventHandler, PlaybackInfo playbackInfo, ExoPlayer player) {
        this.renderers = renderers;
        this.trackSelector = trackSelector;
        this.loadControl = loadControl;
        this.playWhenReady = playWhenReady;
        this.repeatMode = repeatMode;
        this.eventHandler = eventHandler;
        this.playbackInfo = playbackInfo;
        this.player = player;
        this.rendererCapabilities = new RendererCapabilities[renderers.length];
        for (int i = 0; i < renderers.length; i++) {
            renderers[i].setIndex(i);
            this.rendererCapabilities[i] = renderers[i].getCapabilities();
        }
        this.standaloneMediaClock = new StandaloneMediaClock();
        this.enabledRenderers = new Renderer[0];
        this.window = new Window();
        this.period = new Timeline$Period();
        this.mediaPeriodInfoSequence = new MediaPeriodInfoSequence();
        trackSelector.init(this);
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.internalPlaybackThread = new HandlerThread("ExoPlayerImplInternal:Handler", -16);
        this.internalPlaybackThread.start();
        this.handler = new Handler(this.internalPlaybackThread.getLooper(), this);
    }

    public void prepare(MediaSource mediaSource, boolean resetPosition) {
        int i;
        Handler handler = this.handler;
        if (resetPosition) {
            i = 1;
        } else {
            i = 0;
        }
        handler.obtainMessage(0, i, 0, mediaSource).sendToTarget();
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        int i;
        Handler handler = this.handler;
        if (playWhenReady) {
            i = 1;
        } else {
            i = 0;
        }
        handler.obtainMessage(1, i, 0).sendToTarget();
    }

    public void setRepeatMode(int repeatMode) {
        this.handler.obtainMessage(12, repeatMode, 0).sendToTarget();
    }

    public void seekTo(Timeline timeline, int windowIndex, long positionUs) {
        this.handler.obtainMessage(3, new SeekPosition(timeline, windowIndex, positionUs)).sendToTarget();
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void stop() {
        this.handler.sendEmptyMessage(5);
    }

    public void sendMessages(ExoPlayer$ExoPlayerMessage... messages) {
        if (this.released) {
            Log.w(TAG, "Ignoring messages sent after release.");
            return;
        }
        this.customMessagesSent++;
        this.handler.obtainMessage(11, messages).sendToTarget();
    }

    public synchronized void blockingSendMessages(ExoPlayer$ExoPlayerMessage... messages) {
        if (this.released) {
            Log.w(TAG, "Ignoring messages sent after release.");
        } else {
            int messageNumber = this.customMessagesSent;
            this.customMessagesSent = messageNumber + 1;
            this.handler.obtainMessage(11, messages).sendToTarget();
            boolean wasInterrupted = false;
            while (this.customMessagesProcessed <= messageNumber) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                }
            }
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void release() {
        if (!this.released) {
            this.handler.sendEmptyMessage(6);
            boolean wasInterrupted = false;
            while (!this.released) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                }
            }
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
            this.internalPlaybackThread.quit();
        }
    }

    public Looper getPlaybackLooper() {
        return this.internalPlaybackThread.getLooper();
    }

    public void onSourceInfoRefreshed(Timeline timeline, Object manifest) {
        this.handler.obtainMessage(7, Pair.create(timeline, manifest)).sendToTarget();
    }

    public void onPrepared(MediaPeriod source) {
        this.handler.obtainMessage(8, source).sendToTarget();
    }

    public void onContinueLoadingRequested(MediaPeriod source) {
        this.handler.obtainMessage(9, source).sendToTarget();
    }

    public void onTrackSelectionsInvalidated() {
        this.handler.sendEmptyMessage(10);
    }

    public boolean handleMessage(Message msg) {
        boolean z = false;
        try {
            switch (msg.what) {
                case 0:
                    MediaSource mediaSource = (MediaSource) msg.obj;
                    if (msg.arg1 != 0) {
                        z = true;
                    }
                    prepareInternal(mediaSource, z);
                    return true;
                case 1:
                    if (msg.arg1 != 0) {
                        z = true;
                    }
                    setPlayWhenReadyInternal(z);
                    return true;
                case 2:
                    doSomeWork();
                    return true;
                case 3:
                    seekToInternal((SeekPosition) msg.obj);
                    return true;
                case 4:
                    setPlaybackParametersInternal((PlaybackParameters) msg.obj);
                    return true;
                case 5:
                    stopInternal();
                    return true;
                case 6:
                    releaseInternal();
                    return true;
                case 7:
                    handleSourceInfoRefreshed((Pair) msg.obj);
                    return true;
                case 8:
                    handlePeriodPrepared((MediaPeriod) msg.obj);
                    return true;
                case 9:
                    handleContinueLoadingRequested((MediaPeriod) msg.obj);
                    return true;
                case 10:
                    reselectTracksInternal();
                    return true;
                case 11:
                    sendMessagesInternal((ExoPlayer$ExoPlayerMessage[]) msg.obj);
                    return true;
                case 12:
                    setRepeatModeInternal(msg.arg1);
                    return true;
                default:
                    return false;
            }
        } catch (ExoPlaybackException e) {
            Log.e(TAG, "Renderer error.", e);
            this.eventHandler.obtainMessage(8, e).sendToTarget();
            stopInternal();
            return true;
        } catch (IOException e2) {
            Log.e(TAG, "Source error.", e2);
            this.eventHandler.obtainMessage(8, ExoPlaybackException.createForSource(e2)).sendToTarget();
            stopInternal();
            return true;
        } catch (RuntimeException e3) {
            Log.e(TAG, "Internal runtime error.", e3);
            this.eventHandler.obtainMessage(8, ExoPlaybackException.createForUnexpected(e3)).sendToTarget();
            stopInternal();
            return true;
        }
    }

    private void setState(int state) {
        if (this.state != state) {
            this.state = state;
            this.eventHandler.obtainMessage(1, state, 0).sendToTarget();
        }
    }

    private void setIsLoading(boolean isLoading) {
        if (this.isLoading != isLoading) {
            int i;
            this.isLoading = isLoading;
            Handler handler = this.eventHandler;
            if (isLoading) {
                i = 1;
            } else {
                i = 0;
            }
            handler.obtainMessage(2, i, 0).sendToTarget();
        }
    }

    private void prepareInternal(MediaSource mediaSource, boolean resetPosition) {
        this.eventHandler.sendEmptyMessage(0);
        resetInternal(true);
        this.loadControl.onPrepared();
        if (resetPosition) {
            this.playbackInfo = new PlaybackInfo(0, (long) C0907C.TIME_UNSET);
        } else {
            this.playbackInfo = new PlaybackInfo(this.playbackInfo.periodId, this.playbackInfo.positionUs, this.playbackInfo.contentPositionUs);
        }
        this.mediaSource = mediaSource;
        mediaSource.prepareSource(this.player, true, this);
        setState(2);
        this.handler.sendEmptyMessage(2);
    }

    private void setPlayWhenReadyInternal(boolean playWhenReady) throws ExoPlaybackException {
        this.rebuffering = false;
        this.playWhenReady = playWhenReady;
        if (!playWhenReady) {
            stopRenderers();
            updatePlaybackPositions();
        } else if (this.state == 3) {
            startRenderers();
            this.handler.sendEmptyMessage(2);
        } else if (this.state == 2) {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setRepeatModeInternal(int repeatMode) throws ExoPlaybackException {
        this.repeatMode = repeatMode;
        this.mediaPeriodInfoSequence.setRepeatMode(repeatMode);
        MediaPeriodHolder lastValidPeriodHolder = this.playingPeriodHolder != null ? this.playingPeriodHolder : this.loadingPeriodHolder;
        if (lastValidPeriodHolder != null) {
            int loadingPeriodHolderIndex;
            while (true) {
                int nextPeriodIndex = this.timeline.getNextPeriodIndex(lastValidPeriodHolder.info.id.periodIndex, this.period, this.window, repeatMode);
                while (lastValidPeriodHolder.next != null && !lastValidPeriodHolder.info.isLastInTimelinePeriod) {
                    lastValidPeriodHolder = lastValidPeriodHolder.next;
                }
                if (nextPeriodIndex == -1 || lastValidPeriodHolder.next == null || lastValidPeriodHolder.next.info.id.periodIndex != nextPeriodIndex) {
                    loadingPeriodHolderIndex = this.loadingPeriodHolder.index;
                } else {
                    lastValidPeriodHolder = lastValidPeriodHolder.next;
                }
            }
            loadingPeriodHolderIndex = this.loadingPeriodHolder.index;
            int readingPeriodHolderIndex = this.readingPeriodHolder != null ? this.readingPeriodHolder.index : -1;
            if (lastValidPeriodHolder.next != null) {
                releasePeriodHoldersFrom(lastValidPeriodHolder.next);
                lastValidPeriodHolder.next = null;
            }
            lastValidPeriodHolder.info = this.mediaPeriodInfoSequence.getUpdatedMediaPeriodInfo(lastValidPeriodHolder.info);
            if (!(loadingPeriodHolderIndex <= lastValidPeriodHolder.index)) {
                this.loadingPeriodHolder = lastValidPeriodHolder;
            }
            boolean seenReadingPeriodHolder = readingPeriodHolderIndex != -1 && readingPeriodHolderIndex <= lastValidPeriodHolder.index;
            if (!seenReadingPeriodHolder && this.playingPeriodHolder != null) {
                MediaPeriodId periodId = this.playingPeriodHolder.info.id;
                this.playbackInfo = new PlaybackInfo(periodId, seekToPeriodPosition(periodId, this.playbackInfo.positionUs), this.playbackInfo.contentPositionUs);
            }
        }
    }

    private void startRenderers() throws ExoPlaybackException {
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

    private void stopRenderers() throws ExoPlaybackException {
        this.standaloneMediaClock.stop();
        for (Renderer renderer : this.enabledRenderers) {
            ensureStopped(renderer);
        }
    }

    private void updatePlaybackPositions() throws ExoPlaybackException {
        if (this.playingPeriodHolder != null) {
            long bufferedPositionUs;
            long periodPositionUs = this.playingPeriodHolder.mediaPeriod.readDiscontinuity();
            if (periodPositionUs != C0907C.TIME_UNSET) {
                resetRendererPosition(periodPositionUs);
            } else {
                if (this.rendererMediaClockSource == null || this.rendererMediaClockSource.isEnded()) {
                    this.rendererPositionUs = this.standaloneMediaClock.getPositionUs();
                } else {
                    this.rendererPositionUs = this.rendererMediaClock.getPositionUs();
                    this.standaloneMediaClock.setPositionUs(this.rendererPositionUs);
                }
                periodPositionUs = this.playingPeriodHolder.toPeriodTime(this.rendererPositionUs);
            }
            this.playbackInfo.positionUs = periodPositionUs;
            this.elapsedRealtimeUs = SystemClock.elapsedRealtime() * 1000;
            if (this.enabledRenderers.length == 0) {
                bufferedPositionUs = Long.MIN_VALUE;
            } else {
                bufferedPositionUs = this.playingPeriodHolder.mediaPeriod.getBufferedPositionUs();
            }
            PlaybackInfo playbackInfo = this.playbackInfo;
            if (bufferedPositionUs == Long.MIN_VALUE) {
                bufferedPositionUs = this.playingPeriodHolder.info.durationUs;
            }
            playbackInfo.bufferedPositionUs = bufferedPositionUs;
        }
    }

    private void doSomeWork() throws ExoPlaybackException, IOException {
        long operationStartTimeMs = SystemClock.elapsedRealtime();
        updatePeriods();
        if (this.playingPeriodHolder == null) {
            maybeThrowPeriodPrepareError();
            scheduleNextWork(operationStartTimeMs, 10);
            return;
        }
        TraceUtil.beginSection("doSomeWork");
        updatePlaybackPositions();
        this.playingPeriodHolder.mediaPeriod.discardBuffer(this.playbackInfo.positionUs);
        boolean allRenderersEnded = true;
        boolean allRenderersReadyOrEnded = true;
        for (Renderer renderer : this.enabledRenderers) {
            renderer.render(this.rendererPositionUs, this.elapsedRealtimeUs);
            allRenderersEnded = allRenderersEnded && renderer.isEnded();
            boolean rendererReadyOrEnded = renderer.isReady() || renderer.isEnded();
            if (!rendererReadyOrEnded) {
                renderer.maybeThrowStreamError();
            }
            if (allRenderersReadyOrEnded && rendererReadyOrEnded) {
                allRenderersReadyOrEnded = true;
            } else {
                allRenderersReadyOrEnded = false;
            }
        }
        if (!allRenderersReadyOrEnded) {
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
        long playingPeriodDurationUs = this.playingPeriodHolder.info.durationUs;
        if (allRenderersEnded && ((playingPeriodDurationUs == C0907C.TIME_UNSET || playingPeriodDurationUs <= this.playbackInfo.positionUs) && this.playingPeriodHolder.info.isFinal)) {
            setState(4);
            stopRenderers();
        } else if (this.state == 2) {
            boolean isNewlyReady;
            if (this.enabledRenderers.length > 0) {
                if (allRenderersReadyOrEnded) {
                    if (this.loadingPeriodHolder.haveSufficientBuffer(this.rebuffering, this.rendererPositionUs)) {
                        isNewlyReady = true;
                    }
                }
                isNewlyReady = false;
            } else {
                isNewlyReady = isTimelineReady(playingPeriodDurationUs);
            }
            if (isNewlyReady) {
                setState(3);
                if (this.playWhenReady) {
                    startRenderers();
                }
            }
        } else if (this.state == 3) {
            boolean isStillReady;
            if (this.enabledRenderers.length > 0) {
                isStillReady = allRenderersReadyOrEnded;
            } else {
                isStillReady = isTimelineReady(playingPeriodDurationUs);
            }
            if (!isStillReady) {
                this.rebuffering = this.playWhenReady;
                setState(2);
                stopRenderers();
            }
        }
        if (this.state == 2) {
            for (Renderer renderer2 : this.enabledRenderers) {
                renderer2.maybeThrowStreamError();
            }
        }
        if ((this.playWhenReady && this.state == 3) || this.state == 2) {
            scheduleNextWork(operationStartTimeMs, 10);
        } else if (this.enabledRenderers.length == 0 || this.state == 4) {
            this.handler.removeMessages(2);
        } else {
            scheduleNextWork(operationStartTimeMs, 1000);
        }
        TraceUtil.endSection();
    }

    private void scheduleNextWork(long thisOperationStartTimeMs, long intervalMs) {
        this.handler.removeMessages(2);
        long nextOperationDelayMs = (thisOperationStartTimeMs + intervalMs) - SystemClock.elapsedRealtime();
        if (nextOperationDelayMs <= 0) {
            this.handler.sendEmptyMessage(2);
        } else {
            this.handler.sendEmptyMessageDelayed(2, nextOperationDelayMs);
        }
    }

    private void seekToInternal(SeekPosition seekPosition) throws ExoPlaybackException {
        if (this.timeline == null) {
            this.pendingInitialSeekCount++;
            this.pendingSeekPosition = seekPosition;
            return;
        }
        Pair<Integer, Long> periodPosition = resolveSeekPosition(seekPosition);
        if (periodPosition == null) {
            this.playbackInfo = new PlaybackInfo(0, 0);
            this.eventHandler.obtainMessage(4, 1, 0, this.playbackInfo).sendToTarget();
            this.playbackInfo = new PlaybackInfo(0, (long) C0907C.TIME_UNSET);
            setState(4);
            resetInternal(false);
            return;
        }
        boolean seekPositionAdjusted = seekPosition.windowPositionUs == C0907C.TIME_UNSET;
        int periodIndex = ((Integer) periodPosition.first).intValue();
        long periodPositionUs = ((Long) periodPosition.second).longValue();
        long contentPositionUs = periodPositionUs;
        MediaPeriodId periodId = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(periodIndex, periodPositionUs);
        if (periodId.isAd()) {
            seekPositionAdjusted = true;
            periodPositionUs = 0;
        }
        try {
            if (periodId.equals(this.playbackInfo.periodId) && periodPositionUs / 1000 == this.playbackInfo.positionUs / 1000) {
                this.playbackInfo = new PlaybackInfo(periodId, periodPositionUs, contentPositionUs);
                this.eventHandler.obtainMessage(4, seekPositionAdjusted ? 1 : 0, 0, this.playbackInfo).sendToTarget();
                return;
            }
            long newPeriodPositionUs = seekToPeriodPosition(periodId, periodPositionUs);
            seekPositionAdjusted |= periodPositionUs != newPeriodPositionUs ? 1 : 0;
            this.playbackInfo = new PlaybackInfo(periodId, newPeriodPositionUs, contentPositionUs);
            this.eventHandler.obtainMessage(4, seekPositionAdjusted ? 1 : 0, 0, this.playbackInfo).sendToTarget();
        } catch (Throwable th) {
            Throwable th2 = th;
            this.playbackInfo = new PlaybackInfo(periodId, periodPositionUs, contentPositionUs);
            this.eventHandler.obtainMessage(4, seekPositionAdjusted ? 1 : 0, 0, this.playbackInfo).sendToTarget();
        }
    }

    private long seekToPeriodPosition(MediaPeriodId periodId, long periodPositionUs) throws ExoPlaybackException {
        stopRenderers();
        this.rebuffering = false;
        setState(2);
        MediaPeriodHolder newPlayingPeriodHolder = null;
        if (this.playingPeriodHolder != null) {
            MediaPeriodHolder periodHolder = this.playingPeriodHolder;
            while (periodHolder != null) {
                if (newPlayingPeriodHolder == null && shouldKeepPeriodHolder(periodId, periodPositionUs, periodHolder)) {
                    newPlayingPeriodHolder = periodHolder;
                } else {
                    periodHolder.release();
                }
                periodHolder = periodHolder.next;
            }
        } else if (this.loadingPeriodHolder != null) {
            this.loadingPeriodHolder.release();
        }
        if (!(this.playingPeriodHolder == newPlayingPeriodHolder && this.playingPeriodHolder == this.readingPeriodHolder)) {
            for (Renderer renderer : this.enabledRenderers) {
                renderer.disable();
            }
            this.enabledRenderers = new Renderer[0];
            this.rendererMediaClock = null;
            this.rendererMediaClockSource = null;
            this.playingPeriodHolder = null;
        }
        if (newPlayingPeriodHolder != null) {
            newPlayingPeriodHolder.next = null;
            this.loadingPeriodHolder = newPlayingPeriodHolder;
            this.readingPeriodHolder = newPlayingPeriodHolder;
            setPlayingPeriodHolder(newPlayingPeriodHolder);
            if (this.playingPeriodHolder.hasEnabledTracks) {
                periodPositionUs = this.playingPeriodHolder.mediaPeriod.seekToUs(periodPositionUs);
            }
            resetRendererPosition(periodPositionUs);
            maybeContinueLoading();
        } else {
            this.loadingPeriodHolder = null;
            this.readingPeriodHolder = null;
            this.playingPeriodHolder = null;
            resetRendererPosition(periodPositionUs);
        }
        this.handler.sendEmptyMessage(2);
        return periodPositionUs;
    }

    private boolean shouldKeepPeriodHolder(MediaPeriodId seekPeriodId, long positionUs, MediaPeriodHolder holder) {
        if (seekPeriodId.equals(holder.info.id) && holder.prepared) {
            this.timeline.getPeriod(holder.info.id.periodIndex, this.period);
            int nextAdGroupIndex = this.period.getAdGroupIndexAfterPositionUs(positionUs);
            if (nextAdGroupIndex == -1 || this.period.getAdGroupTimeUs(nextAdGroupIndex) == holder.info.endPositionUs) {
                return true;
            }
        }
        return false;
    }

    private void resetRendererPosition(long periodPositionUs) throws ExoPlaybackException {
        long j;
        if (this.playingPeriodHolder == null) {
            j = 60000000 + periodPositionUs;
        } else {
            j = this.playingPeriodHolder.toRendererTime(periodPositionUs);
        }
        this.rendererPositionUs = j;
        this.standaloneMediaClock.setPositionUs(this.rendererPositionUs);
        for (Renderer renderer : this.enabledRenderers) {
            renderer.resetPosition(this.rendererPositionUs);
        }
    }

    private void setPlaybackParametersInternal(PlaybackParameters playbackParameters) {
        if (this.rendererMediaClock != null) {
            playbackParameters = this.rendererMediaClock.setPlaybackParameters(playbackParameters);
        } else {
            playbackParameters = this.standaloneMediaClock.setPlaybackParameters(playbackParameters);
        }
        this.playbackParameters = playbackParameters;
        this.eventHandler.obtainMessage(7, playbackParameters).sendToTarget();
    }

    private void stopInternal() {
        resetInternal(true);
        this.loadControl.onStopped();
        setState(1);
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

    private void resetInternal(boolean releaseMediaSource) {
        Exception e;
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
        if (releaseMediaSource) {
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

    private void sendMessagesInternal(ExoPlayer$ExoPlayerMessage[] messages) throws ExoPlaybackException {
        try {
            for (ExoPlayer$ExoPlayerMessage message : messages) {
                message.target.handleMessage(message.messageType, message.message);
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

    private void ensureStopped(Renderer renderer) throws ExoPlaybackException {
        if (renderer.getState() == 2) {
            renderer.stop();
        }
    }

    private void reselectTracksInternal() throws ExoPlaybackException {
        if (this.playingPeriodHolder != null) {
            MediaPeriodHolder periodHolder = this.playingPeriodHolder;
            boolean selectionsChangedForReadPeriod = true;
            while (periodHolder != null && periodHolder.prepared) {
                if (periodHolder.selectTracks()) {
                    if (selectionsChangedForReadPeriod) {
                        boolean recreateStreams = this.readingPeriodHolder != this.playingPeriodHolder;
                        releasePeriodHoldersFrom(this.playingPeriodHolder.next);
                        this.playingPeriodHolder.next = null;
                        this.loadingPeriodHolder = this.playingPeriodHolder;
                        this.readingPeriodHolder = this.playingPeriodHolder;
                        boolean[] streamResetFlags = new boolean[this.renderers.length];
                        long periodPositionUs = this.playingPeriodHolder.updatePeriodTrackSelection(this.playbackInfo.positionUs, recreateStreams, streamResetFlags);
                        if (periodPositionUs != this.playbackInfo.positionUs) {
                            this.playbackInfo.positionUs = periodPositionUs;
                            resetRendererPosition(periodPositionUs);
                        }
                        int enabledRendererCount = 0;
                        boolean[] rendererWasEnabledFlags = new boolean[this.renderers.length];
                        for (int i = 0; i < this.renderers.length; i++) {
                            Renderer renderer = this.renderers[i];
                            rendererWasEnabledFlags[i] = renderer.getState() != 0;
                            SampleStream sampleStream = this.playingPeriodHolder.sampleStreams[i];
                            if (sampleStream != null) {
                                enabledRendererCount++;
                            }
                            if (rendererWasEnabledFlags[i]) {
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
                                } else if (streamResetFlags[i]) {
                                    renderer.resetPosition(this.rendererPositionUs);
                                }
                            }
                        }
                        this.eventHandler.obtainMessage(3, periodHolder.trackSelectorResult).sendToTarget();
                        enableRenderers(rendererWasEnabledFlags, enabledRendererCount);
                    } else {
                        this.loadingPeriodHolder = periodHolder;
                        for (periodHolder = this.loadingPeriodHolder.next; periodHolder != null; periodHolder = periodHolder.next) {
                            periodHolder.release();
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
                if (periodHolder == this.readingPeriodHolder) {
                    selectionsChangedForReadPeriod = false;
                }
                periodHolder = periodHolder.next;
            }
        }
    }

    private boolean isTimelineReady(long playingPeriodDurationUs) {
        return playingPeriodDurationUs == C0907C.TIME_UNSET || this.playbackInfo.positionUs < playingPeriodDurationUs || (this.playingPeriodHolder.next != null && (this.playingPeriodHolder.next.prepared || this.playingPeriodHolder.next.info.id.isAd()));
    }

    private void maybeThrowPeriodPrepareError() throws IOException {
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

    private void handleSourceInfoRefreshed(Pair<Timeline, Object> timelineAndManifest) throws ExoPlaybackException {
        Timeline oldTimeline = this.timeline;
        this.timeline = (Timeline) timelineAndManifest.first;
        this.mediaPeriodInfoSequence.setTimeline(this.timeline);
        Object manifest = timelineAndManifest.second;
        int periodIndex;
        Pair<Integer, Long> defaultPosition;
        MediaPeriodId periodId;
        if (oldTimeline != null) {
            int playingPeriodIndex = this.playbackInfo.periodId.periodIndex;
            MediaPeriodHolder periodHolder = this.playingPeriodHolder != null ? this.playingPeriodHolder : this.loadingPeriodHolder;
            if (periodHolder != null || playingPeriodIndex < oldTimeline.getPeriodCount()) {
                Object playingPeriodUid;
                if (periodHolder == null) {
                    playingPeriodUid = oldTimeline.getPeriod(playingPeriodIndex, this.period, true).uid;
                } else {
                    playingPeriodUid = periodHolder.uid;
                }
                periodIndex = this.timeline.getIndexOfPeriod(playingPeriodUid);
                if (periodIndex == -1) {
                    int newPeriodIndex = resolveSubsequentPeriod(playingPeriodIndex, oldTimeline, this.timeline);
                    if (newPeriodIndex == -1) {
                        handleSourceInfoRefreshEndedPlayback(manifest);
                        return;
                    }
                    defaultPosition = getPeriodPosition(this.timeline.getPeriod(newPeriodIndex, this.period).windowIndex, C0907C.TIME_UNSET);
                    newPeriodIndex = ((Integer) defaultPosition.first).intValue();
                    long newPositionUs = ((Long) defaultPosition.second).longValue();
                    this.timeline.getPeriod(newPeriodIndex, this.period, true);
                    if (periodHolder != null) {
                        Object newPeriodUid = this.period.uid;
                        periodHolder.info = periodHolder.info.copyWithPeriodIndex(-1);
                        while (periodHolder.next != null) {
                            periodHolder = periodHolder.next;
                            if (periodHolder.uid.equals(newPeriodUid)) {
                                periodHolder.info = this.mediaPeriodInfoSequence.getUpdatedMediaPeriodInfo(periodHolder.info, newPeriodIndex);
                            } else {
                                periodHolder.info = periodHolder.info.copyWithPeriodIndex(-1);
                            }
                        }
                    }
                    periodId = new MediaPeriodId(newPeriodIndex);
                    this.playbackInfo = new PlaybackInfo(periodId, seekToPeriodPosition(periodId, newPositionUs));
                    notifySourceInfoRefresh(manifest);
                    return;
                }
                if (periodIndex != playingPeriodIndex) {
                    this.playbackInfo = this.playbackInfo.copyWithPeriodIndex(periodIndex);
                }
                if (this.playbackInfo.periodId.isAd()) {
                    periodId = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(periodIndex, this.playbackInfo.contentPositionUs);
                    if (!(periodId.isAd() && periodId.adIndexInAdGroup == this.playbackInfo.periodId.adIndexInAdGroup)) {
                        this.playbackInfo = new PlaybackInfo(periodId, seekToPeriodPosition(periodId, this.playbackInfo.contentPositionUs), periodId.isAd() ? this.playbackInfo.contentPositionUs : C0907C.TIME_UNSET);
                        notifySourceInfoRefresh(manifest);
                        return;
                    }
                }
                if (periodHolder == null) {
                    notifySourceInfoRefresh(manifest);
                    return;
                }
                periodHolder = updatePeriodInfo(periodHolder, periodIndex);
                while (periodHolder.next != null) {
                    MediaPeriodHolder previousPeriodHolder = periodHolder;
                    periodHolder = periodHolder.next;
                    periodIndex = this.timeline.getNextPeriodIndex(periodIndex, this.period, this.window, this.repeatMode);
                    if (periodIndex != -1) {
                        if (periodHolder.uid.equals(this.timeline.getPeriod(periodIndex, this.period, true).uid)) {
                            periodHolder = updatePeriodInfo(periodHolder, periodIndex);
                        }
                    }
                    boolean seenReadingPeriodHolder = this.readingPeriodHolder != null && this.readingPeriodHolder.index < periodHolder.index;
                    if (seenReadingPeriodHolder) {
                        this.loadingPeriodHolder = previousPeriodHolder;
                        this.loadingPeriodHolder.next = null;
                        releasePeriodHoldersFrom(periodHolder);
                    } else {
                        this.playbackInfo = new PlaybackInfo(this.playingPeriodHolder.info.id, seekToPeriodPosition(this.playingPeriodHolder.info.id, this.playbackInfo.positionUs), this.playbackInfo.contentPositionUs);
                    }
                    notifySourceInfoRefresh(manifest);
                    return;
                }
                notifySourceInfoRefresh(manifest);
                return;
            }
            notifySourceInfoRefresh(manifest);
        } else if (this.pendingInitialSeekCount > 0) {
            Pair<Integer, Long> periodPosition = resolveSeekPosition(this.pendingSeekPosition);
            int processedInitialSeekCount = this.pendingInitialSeekCount;
            this.pendingInitialSeekCount = 0;
            this.pendingSeekPosition = null;
            if (periodPosition == null) {
                handleSourceInfoRefreshEndedPlayback(manifest, processedInitialSeekCount);
                return;
            }
            long j;
            periodIndex = ((Integer) periodPosition.first).intValue();
            long positionUs = ((Long) periodPosition.second).longValue();
            periodId = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(periodIndex, positionUs);
            if (periodId.isAd()) {
                j = 0;
            } else {
                j = positionUs;
            }
            this.playbackInfo = new PlaybackInfo(periodId, j, positionUs);
            notifySourceInfoRefresh(manifest, processedInitialSeekCount);
        } else if (this.playbackInfo.startPositionUs != C0907C.TIME_UNSET) {
            notifySourceInfoRefresh(manifest);
        } else if (this.timeline.isEmpty()) {
            handleSourceInfoRefreshEndedPlayback(manifest);
        } else {
            long j2;
            defaultPosition = getPeriodPosition(0, C0907C.TIME_UNSET);
            periodIndex = ((Integer) defaultPosition.first).intValue();
            long startPositionUs = ((Long) defaultPosition.second).longValue();
            periodId = this.mediaPeriodInfoSequence.resolvePeriodPositionForAds(periodIndex, startPositionUs);
            if (periodId.isAd()) {
                j2 = 0;
            } else {
                j2 = startPositionUs;
            }
            this.playbackInfo = new PlaybackInfo(periodId, j2, startPositionUs);
            notifySourceInfoRefresh(manifest);
        }
    }

    private MediaPeriodHolder updatePeriodInfo(MediaPeriodHolder periodHolder, int periodIndex) {
        while (true) {
            periodHolder.info = this.mediaPeriodInfoSequence.getUpdatedMediaPeriodInfo(periodHolder.info, periodIndex);
            if (periodHolder.info.isLastInTimelinePeriod || periodHolder.next == null) {
                return periodHolder;
            }
            periodHolder = periodHolder.next;
        }
        return periodHolder;
    }

    private void handleSourceInfoRefreshEndedPlayback(Object manifest) {
        handleSourceInfoRefreshEndedPlayback(manifest, 0);
    }

    private void handleSourceInfoRefreshEndedPlayback(Object manifest, int processedInitialSeekCount) {
        this.playbackInfo = new PlaybackInfo(0, 0);
        notifySourceInfoRefresh(manifest, processedInitialSeekCount);
        this.playbackInfo = new PlaybackInfo(0, (long) C0907C.TIME_UNSET);
        setState(4);
        resetInternal(false);
    }

    private void notifySourceInfoRefresh(Object manifest) {
        notifySourceInfoRefresh(manifest, 0);
    }

    private void notifySourceInfoRefresh(Object manifest, int processedInitialSeekCount) {
        this.eventHandler.obtainMessage(6, new SourceInfo(this.timeline, manifest, this.playbackInfo, processedInitialSeekCount)).sendToTarget();
    }

    private int resolveSubsequentPeriod(int oldPeriodIndex, Timeline oldTimeline, Timeline newTimeline) {
        int newPeriodIndex = -1;
        int maxIterations = oldTimeline.getPeriodCount();
        for (int i = 0; i < maxIterations && newPeriodIndex == -1; i++) {
            oldPeriodIndex = oldTimeline.getNextPeriodIndex(oldPeriodIndex, this.period, this.window, this.repeatMode);
            if (oldPeriodIndex == -1) {
                break;
            }
            newPeriodIndex = newTimeline.getIndexOfPeriod(oldTimeline.getPeriod(oldPeriodIndex, this.period, true).uid);
        }
        return newPeriodIndex;
    }

    private Pair<Integer, Long> resolveSeekPosition(SeekPosition seekPosition) {
        Timeline seekTimeline = seekPosition.timeline;
        if (seekTimeline.isEmpty()) {
            seekTimeline = this.timeline;
        }
        try {
            Pair<Integer, Long> periodPosition = seekTimeline.getPeriodPosition(this.window, this.period, seekPosition.windowIndex, seekPosition.windowPositionUs);
            if (this.timeline == seekTimeline) {
                return periodPosition;
            }
            int periodIndex = this.timeline.getIndexOfPeriod(seekTimeline.getPeriod(((Integer) periodPosition.first).intValue(), this.period, true).uid);
            if (periodIndex != -1) {
                return Pair.create(Integer.valueOf(periodIndex), periodPosition.second);
            }
            periodIndex = resolveSubsequentPeriod(((Integer) periodPosition.first).intValue(), seekTimeline, this.timeline);
            if (periodIndex != -1) {
                return getPeriodPosition(this.timeline.getPeriod(periodIndex, this.period).windowIndex, C0907C.TIME_UNSET);
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalSeekPositionException(this.timeline, seekPosition.windowIndex, seekPosition.windowPositionUs);
        }
    }

    private Pair<Integer, Long> getPeriodPosition(int windowIndex, long windowPositionUs) {
        return this.timeline.getPeriodPosition(this.window, this.period, windowIndex, windowPositionUs);
    }

    private void updatePeriods() throws ExoPlaybackException, IOException {
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
                TrackSelectorResult oldTrackSelectorResult = this.readingPeriodHolder.trackSelectorResult;
                this.readingPeriodHolder = this.readingPeriodHolder.next;
                TrackSelectorResult newTrackSelectorResult = this.readingPeriodHolder.trackSelectorResult;
                boolean initialDiscontinuity = this.readingPeriodHolder.mediaPeriod.readDiscontinuity() != C0907C.TIME_UNSET;
                for (i = 0; i < this.renderers.length; i++) {
                    renderer = this.renderers[i];
                    if (oldTrackSelectorResult.selections.get(i) != null) {
                        if (initialDiscontinuity) {
                            renderer.setCurrentStreamFinal();
                        } else if (!renderer.isCurrentStreamFinal()) {
                            TrackSelection newSelection = newTrackSelectorResult.selections.get(i);
                            RendererConfiguration oldConfig = oldTrackSelectorResult.rendererConfigurations[i];
                            RendererConfiguration newConfig = newTrackSelectorResult.rendererConfigurations[i];
                            if (newSelection == null || !newConfig.equals(oldConfig)) {
                                renderer.setCurrentStreamFinal();
                            } else {
                                Format[] formats = new Format[newSelection.length()];
                                for (int j = 0; j < formats.length; j++) {
                                    formats[j] = newSelection.getFormat(j);
                                }
                                renderer.replaceStream(formats, this.readingPeriodHolder.sampleStreams[i], this.readingPeriodHolder.getRendererOffset());
                            }
                        }
                    }
                }
            }
        }
    }

    private void maybeUpdateLoadingPeriod() throws IOException {
        MediaPeriodInfo info;
        if (this.loadingPeriodHolder == null) {
            info = this.mediaPeriodInfoSequence.getFirstMediaPeriodInfo(this.playbackInfo);
        } else if (!this.loadingPeriodHolder.info.isFinal && this.loadingPeriodHolder.isFullyBuffered() && this.loadingPeriodHolder.info.durationUs != C0907C.TIME_UNSET) {
            if (this.playingPeriodHolder == null || this.loadingPeriodHolder.index - this.playingPeriodHolder.index != 100) {
                info = this.mediaPeriodInfoSequence.getNextMediaPeriodInfo(this.loadingPeriodHolder.info, this.loadingPeriodHolder.getRendererOffset(), this.rendererPositionUs);
            } else {
                return;
            }
        } else {
            return;
        }
        if (info == null) {
            this.mediaSource.maybeThrowSourceInfoRefreshError();
            return;
        }
        long rendererPositionOffsetUs;
        if (this.loadingPeriodHolder == null) {
            rendererPositionOffsetUs = 60000000;
        } else {
            rendererPositionOffsetUs = this.loadingPeriodHolder.getRendererOffset() + this.loadingPeriodHolder.info.durationUs;
        }
        MediaPeriodHolder newPeriodHolder = new MediaPeriodHolder(this.renderers, this.rendererCapabilities, rendererPositionOffsetUs, this.trackSelector, this.loadControl, this.mediaSource, this.timeline.getPeriod(info.id.periodIndex, this.period, true).uid, this.loadingPeriodHolder == null ? 0 : this.loadingPeriodHolder.index + 1, info);
        if (this.loadingPeriodHolder != null) {
            this.loadingPeriodHolder.next = newPeriodHolder;
        }
        this.loadingPeriodHolder = newPeriodHolder;
        this.loadingPeriodHolder.mediaPeriod.prepare(this, info.startPositionUs);
        setIsLoading(true);
    }

    private void handlePeriodPrepared(MediaPeriod period) throws ExoPlaybackException {
        if (this.loadingPeriodHolder != null && this.loadingPeriodHolder.mediaPeriod == period) {
            this.loadingPeriodHolder.handlePrepared();
            if (this.playingPeriodHolder == null) {
                this.readingPeriodHolder = this.loadingPeriodHolder;
                resetRendererPosition(this.readingPeriodHolder.info.startPositionUs);
                setPlayingPeriodHolder(this.readingPeriodHolder);
            }
            maybeContinueLoading();
        }
    }

    private void handleContinueLoadingRequested(MediaPeriod period) {
        if (this.loadingPeriodHolder != null && this.loadingPeriodHolder.mediaPeriod == period) {
            maybeContinueLoading();
        }
    }

    private void maybeContinueLoading() {
        boolean continueLoading = this.loadingPeriodHolder.shouldContinueLoading(this.rendererPositionUs);
        setIsLoading(continueLoading);
        if (continueLoading) {
            this.loadingPeriodHolder.continueLoading(this.rendererPositionUs);
        }
    }

    private void releasePeriodHoldersFrom(MediaPeriodHolder periodHolder) {
        while (periodHolder != null) {
            periodHolder.release();
            periodHolder = periodHolder.next;
        }
    }

    private void setPlayingPeriodHolder(MediaPeriodHolder periodHolder) throws ExoPlaybackException {
        if (this.playingPeriodHolder != periodHolder) {
            int enabledRendererCount = 0;
            boolean[] rendererWasEnabledFlags = new boolean[this.renderers.length];
            int i = 0;
            while (i < this.renderers.length) {
                Renderer renderer = this.renderers[i];
                rendererWasEnabledFlags[i] = renderer.getState() != 0;
                TrackSelection newSelection = periodHolder.trackSelectorResult.selections.get(i);
                if (newSelection != null) {
                    enabledRendererCount++;
                }
                if (rendererWasEnabledFlags[i] && (newSelection == null || (renderer.isCurrentStreamFinal() && renderer.getStream() == this.playingPeriodHolder.sampleStreams[i]))) {
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
            this.playingPeriodHolder = periodHolder;
            this.eventHandler.obtainMessage(3, periodHolder.trackSelectorResult).sendToTarget();
            enableRenderers(rendererWasEnabledFlags, enabledRendererCount);
        }
    }

    private void enableRenderers(boolean[] rendererWasEnabledFlags, int enabledRendererCount) throws ExoPlaybackException {
        this.enabledRenderers = new Renderer[enabledRendererCount];
        enabledRendererCount = 0;
        for (int i = 0; i < this.renderers.length; i++) {
            Renderer renderer = this.renderers[i];
            TrackSelection newSelection = this.playingPeriodHolder.trackSelectorResult.selections.get(i);
            if (newSelection != null) {
                int enabledRendererCount2 = enabledRendererCount + 1;
                this.enabledRenderers[enabledRendererCount] = renderer;
                if (renderer.getState() == 0) {
                    RendererConfiguration rendererConfiguration = this.playingPeriodHolder.trackSelectorResult.rendererConfigurations[i];
                    boolean playing = this.playWhenReady && this.state == 3;
                    boolean joining = !rendererWasEnabledFlags[i] && playing;
                    Format[] formats = new Format[newSelection.length()];
                    for (int j = 0; j < formats.length; j++) {
                        formats[j] = newSelection.getFormat(j);
                    }
                    renderer.enable(rendererConfiguration, formats, this.playingPeriodHolder.sampleStreams[i], this.rendererPositionUs, joining, this.playingPeriodHolder.getRendererOffset());
                    MediaClock mediaClock = renderer.getMediaClock();
                    if (mediaClock != null) {
                        if (this.rendererMediaClock != null) {
                            throw ExoPlaybackException.createForUnexpected(new IllegalStateException("Multiple renderer media clocks enabled."));
                        }
                        this.rendererMediaClock = mediaClock;
                        this.rendererMediaClockSource = renderer;
                        this.rendererMediaClock.setPlaybackParameters(this.playbackParameters);
                    }
                    if (playing) {
                        renderer.start();
                    }
                }
                enabledRendererCount = enabledRendererCount2;
            }
        }
    }
}
