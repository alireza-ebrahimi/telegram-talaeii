package org.telegram.messenger.exoplayer2;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import org.telegram.messenger.exoplayer2.ExoPlayer.ExoPlayerMessage;
import org.telegram.messenger.exoplayer2.ExoPlayerImplInternal.PlaybackInfo;
import org.telegram.messenger.exoplayer2.ExoPlayerImplInternal.SourceInfo;
import org.telegram.messenger.exoplayer2.Player.EventListener;
import org.telegram.messenger.exoplayer2.Timeline.Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaSource;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelectionArray;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelector;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelectorResult;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

final class ExoPlayerImpl implements ExoPlayer {
    private static final String TAG = "ExoPlayerImpl";
    private final TrackSelectionArray emptyTrackSelections;
    private final Handler eventHandler;
    private final ExoPlayerImplInternal internalPlayer;
    private boolean isLoading;
    private final CopyOnWriteArraySet<EventListener> listeners;
    private Object manifest;
    private int maskingPeriodIndex;
    private int maskingWindowIndex;
    private long maskingWindowPositionMs;
    private int pendingPrepareAcks;
    private int pendingSeekAcks;
    private final Period period;
    private boolean playWhenReady;
    private PlaybackInfo playbackInfo;
    private PlaybackParameters playbackParameters;
    private int playbackState;
    private final Renderer[] renderers;
    private int repeatMode;
    private Timeline timeline;
    private TrackGroupArray trackGroups;
    private TrackSelectionArray trackSelections;
    private final TrackSelector trackSelector;
    private boolean tracksSelected;
    private final Window window;

    @SuppressLint({"HandlerLeak"})
    public ExoPlayerImpl(Renderer[] rendererArr, TrackSelector trackSelector, LoadControl loadControl) {
        Log.i(TAG, "Init " + Integer.toHexString(System.identityHashCode(this)) + " [" + ExoPlayerLibraryInfo.VERSION_SLASHY + "] [" + Util.DEVICE_DEBUG_INFO + "]");
        Assertions.checkState(rendererArr.length > 0);
        this.renderers = (Renderer[]) Assertions.checkNotNull(rendererArr);
        this.trackSelector = (TrackSelector) Assertions.checkNotNull(trackSelector);
        this.playWhenReady = false;
        this.repeatMode = 0;
        this.playbackState = 1;
        this.listeners = new CopyOnWriteArraySet();
        this.emptyTrackSelections = new TrackSelectionArray(new TrackSelection[rendererArr.length]);
        this.timeline = Timeline.EMPTY;
        this.window = new Window();
        this.period = new Period();
        this.trackGroups = TrackGroupArray.EMPTY;
        this.trackSelections = this.emptyTrackSelections;
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.eventHandler = new Handler(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                ExoPlayerImpl.this.handleEvent(message);
            }
        };
        this.playbackInfo = new PlaybackInfo(0, 0);
        this.internalPlayer = new ExoPlayerImplInternal(rendererArr, trackSelector, loadControl, this.playWhenReady, this.repeatMode, this.eventHandler, this.playbackInfo, this);
    }

    private long playbackInfoPositionUsToWindowPositionMs(long j) {
        long usToMs = C3446C.usToMs(j);
        if (this.playbackInfo.periodId.isAd()) {
            return usToMs;
        }
        this.timeline.getPeriod(this.playbackInfo.periodId.periodIndex, this.period);
        return usToMs + this.period.getPositionInWindowMs();
    }

    public void addListener(EventListener eventListener) {
        this.listeners.add(eventListener);
    }

    public void blockingSendMessages(ExoPlayerMessage... exoPlayerMessageArr) {
        this.internalPlayer.blockingSendMessages(exoPlayerMessageArr);
    }

    public int getBufferedPercentage() {
        int i = 100;
        if (this.timeline.isEmpty()) {
            return 0;
        }
        long bufferedPosition = getBufferedPosition();
        long duration = getDuration();
        if (bufferedPosition == C3446C.TIME_UNSET || duration == C3446C.TIME_UNSET) {
            i = 0;
        } else if (duration != 0) {
            i = Util.constrainValue((int) ((bufferedPosition * 100) / duration), 0, 100);
        }
        return i;
    }

    public long getBufferedPosition() {
        return (this.timeline.isEmpty() || this.pendingSeekAcks > 0) ? this.maskingWindowPositionMs : playbackInfoPositionUsToWindowPositionMs(this.playbackInfo.bufferedPositionUs);
    }

    public long getContentPosition() {
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        this.timeline.getPeriod(this.playbackInfo.periodId.periodIndex, this.period);
        return this.period.getPositionInWindowMs() + C3446C.usToMs(this.playbackInfo.contentPositionUs);
    }

    public int getCurrentAdGroupIndex() {
        return isPlayingAd() ? this.playbackInfo.periodId.adGroupIndex : -1;
    }

    public int getCurrentAdIndexInAdGroup() {
        return isPlayingAd() ? this.playbackInfo.periodId.adIndexInAdGroup : -1;
    }

    public Object getCurrentManifest() {
        return this.manifest;
    }

    public int getCurrentPeriodIndex() {
        return (this.timeline.isEmpty() || this.pendingSeekAcks > 0) ? this.maskingPeriodIndex : this.playbackInfo.periodId.periodIndex;
    }

    public long getCurrentPosition() {
        return (this.timeline.isEmpty() || this.pendingSeekAcks > 0) ? this.maskingWindowPositionMs : playbackInfoPositionUsToWindowPositionMs(this.playbackInfo.positionUs);
    }

    public Timeline getCurrentTimeline() {
        return this.timeline;
    }

    public TrackGroupArray getCurrentTrackGroups() {
        return this.trackGroups;
    }

    public TrackSelectionArray getCurrentTrackSelections() {
        return this.trackSelections;
    }

    public int getCurrentWindowIndex() {
        return (this.timeline.isEmpty() || this.pendingSeekAcks > 0) ? this.maskingWindowIndex : this.timeline.getPeriod(this.playbackInfo.periodId.periodIndex, this.period).windowIndex;
    }

    public long getDuration() {
        if (this.timeline.isEmpty()) {
            return C3446C.TIME_UNSET;
        }
        if (!isPlayingAd()) {
            return this.timeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
        }
        MediaPeriodId mediaPeriodId = this.playbackInfo.periodId;
        this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period);
        return C3446C.usToMs(this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup));
    }

    public boolean getPlayWhenReady() {
        return this.playWhenReady;
    }

    public Looper getPlaybackLooper() {
        return this.internalPlayer.getPlaybackLooper();
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public int getPlaybackState() {
        return this.playbackState;
    }

    public int getRendererCount() {
        return this.renderers.length;
    }

    public int getRendererType(int i) {
        return this.renderers[i].getTrackType();
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    void handleEvent(Message message) {
        Iterator it;
        Iterator it2;
        switch (message.what) {
            case 0:
                this.pendingPrepareAcks--;
                return;
            case 1:
                this.playbackState = message.arg1;
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onPlayerStateChanged(this.playWhenReady, this.playbackState);
                }
                return;
            case 2:
                this.isLoading = message.arg1 != 0;
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onLoadingChanged(this.isLoading);
                }
                return;
            case 3:
                if (this.pendingPrepareAcks == 0) {
                    TrackSelectorResult trackSelectorResult = (TrackSelectorResult) message.obj;
                    this.tracksSelected = true;
                    this.trackGroups = trackSelectorResult.groups;
                    this.trackSelections = trackSelectorResult.selections;
                    this.trackSelector.onSelectionActivated(trackSelectorResult.info);
                    it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((EventListener) it.next()).onTracksChanged(this.trackGroups, this.trackSelections);
                    }
                    return;
                }
                return;
            case 4:
                int i = this.pendingSeekAcks - 1;
                this.pendingSeekAcks = i;
                if (i == 0) {
                    this.playbackInfo = (PlaybackInfo) message.obj;
                    if (this.timeline.isEmpty()) {
                        this.maskingPeriodIndex = 0;
                        this.maskingWindowIndex = 0;
                        this.maskingWindowPositionMs = 0;
                    }
                    if (message.arg1 != 0) {
                        it = this.listeners.iterator();
                        while (it.hasNext()) {
                            ((EventListener) it.next()).onPositionDiscontinuity();
                        }
                        return;
                    }
                    return;
                }
                return;
            case 5:
                if (this.pendingSeekAcks == 0) {
                    this.playbackInfo = (PlaybackInfo) message.obj;
                    it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((EventListener) it.next()).onPositionDiscontinuity();
                    }
                    return;
                }
                return;
            case 6:
                SourceInfo sourceInfo = (SourceInfo) message.obj;
                this.pendingSeekAcks -= sourceInfo.seekAcks;
                if (this.pendingPrepareAcks == 0) {
                    this.timeline = sourceInfo.timeline;
                    this.manifest = sourceInfo.manifest;
                    this.playbackInfo = sourceInfo.playbackInfo;
                    if (this.pendingSeekAcks == 0 && this.timeline.isEmpty()) {
                        this.maskingPeriodIndex = 0;
                        this.maskingWindowIndex = 0;
                        this.maskingWindowPositionMs = 0;
                    }
                    it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((EventListener) it.next()).onTimelineChanged(this.timeline, this.manifest);
                    }
                    return;
                }
                return;
            case 7:
                PlaybackParameters playbackParameters = (PlaybackParameters) message.obj;
                if (!this.playbackParameters.equals(playbackParameters)) {
                    this.playbackParameters = playbackParameters;
                    it2 = this.listeners.iterator();
                    while (it2.hasNext()) {
                        ((EventListener) it2.next()).onPlaybackParametersChanged(playbackParameters);
                    }
                    return;
                }
                return;
            case 8:
                ExoPlaybackException exoPlaybackException = (ExoPlaybackException) message.obj;
                it2 = this.listeners.iterator();
                while (it2.hasNext()) {
                    ((EventListener) it2.next()).onPlayerError(exoPlaybackException);
                }
                return;
            default:
                throw new IllegalStateException();
        }
    }

    public boolean isCurrentWindowDynamic() {
        return !this.timeline.isEmpty() && this.timeline.getWindow(getCurrentWindowIndex(), this.window).isDynamic;
    }

    public boolean isCurrentWindowSeekable() {
        return !this.timeline.isEmpty() && this.timeline.getWindow(getCurrentWindowIndex(), this.window).isSeekable;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public boolean isPlayingAd() {
        return !this.timeline.isEmpty() && this.pendingSeekAcks == 0 && this.playbackInfo.periodId.isAd();
    }

    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    public void prepare(MediaSource mediaSource, boolean z, boolean z2) {
        if (z2) {
            Iterator it;
            if (!(this.timeline.isEmpty() && this.manifest == null)) {
                this.timeline = Timeline.EMPTY;
                this.manifest = null;
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onTimelineChanged(this.timeline, this.manifest);
                }
            }
            if (this.tracksSelected) {
                this.tracksSelected = false;
                this.trackGroups = TrackGroupArray.EMPTY;
                this.trackSelections = this.emptyTrackSelections;
                this.trackSelector.onSelectionActivated(null);
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onTracksChanged(this.trackGroups, this.trackSelections);
                }
            }
        }
        this.pendingPrepareAcks++;
        this.internalPlayer.prepare(mediaSource, z);
    }

    public void release() {
        Log.i(TAG, "Release " + Integer.toHexString(System.identityHashCode(this)) + " [" + ExoPlayerLibraryInfo.VERSION_SLASHY + "] [" + Util.DEVICE_DEBUG_INFO + "] [" + ExoPlayerLibraryInfo.registeredModules() + "]");
        this.internalPlayer.release();
        this.eventHandler.removeCallbacksAndMessages(null);
    }

    public void removeListener(EventListener eventListener) {
        this.listeners.remove(eventListener);
    }

    public void seekTo(int i, long j) {
        if (i < 0 || (!this.timeline.isEmpty() && i >= this.timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(this.timeline, i, j);
        }
        this.pendingSeekAcks++;
        this.maskingWindowIndex = i;
        if (this.timeline.isEmpty()) {
            this.maskingPeriodIndex = 0;
        } else {
            this.timeline.getWindow(i, this.window);
            long defaultPositionUs = j == C3446C.TIME_UNSET ? this.window.getDefaultPositionUs() : C3446C.msToUs(j);
            int i2 = this.window.firstPeriodIndex;
            long positionInFirstPeriodUs = this.window.getPositionInFirstPeriodUs() + defaultPositionUs;
            defaultPositionUs = this.timeline.getPeriod(i2, this.period).getDurationUs();
            while (defaultPositionUs != C3446C.TIME_UNSET && positionInFirstPeriodUs >= defaultPositionUs && i2 < this.window.lastPeriodIndex) {
                positionInFirstPeriodUs -= defaultPositionUs;
                i2++;
                defaultPositionUs = this.timeline.getPeriod(i2, this.period).getDurationUs();
            }
            this.maskingPeriodIndex = i2;
        }
        if (j == C3446C.TIME_UNSET) {
            this.maskingWindowPositionMs = 0;
            this.internalPlayer.seekTo(this.timeline, i, C3446C.TIME_UNSET);
            return;
        }
        this.maskingWindowPositionMs = j;
        this.internalPlayer.seekTo(this.timeline, i, C3446C.msToUs(j));
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((EventListener) it.next()).onPositionDiscontinuity();
        }
    }

    public void seekTo(long j) {
        seekTo(getCurrentWindowIndex(), j);
    }

    public void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    public void seekToDefaultPosition(int i) {
        seekTo(i, C3446C.TIME_UNSET);
    }

    public void sendMessages(ExoPlayerMessage... exoPlayerMessageArr) {
        this.internalPlayer.sendMessages(exoPlayerMessageArr);
    }

    public void setPlayWhenReady(boolean z) {
        if (this.playWhenReady != z) {
            this.playWhenReady = z;
            this.internalPlayer.setPlayWhenReady(z);
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((EventListener) it.next()).onPlayerStateChanged(z, this.playbackState);
            }
        }
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        this.internalPlayer.setPlaybackParameters(playbackParameters);
    }

    public void setRepeatMode(int i) {
        if (this.repeatMode != i) {
            this.repeatMode = i;
            this.internalPlayer.setRepeatMode(i);
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((EventListener) it.next()).onRepeatModeChanged(i);
            }
        }
    }

    public void stop() {
        this.internalPlayer.stop();
    }
}
