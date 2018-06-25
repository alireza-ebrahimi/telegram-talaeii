package org.telegram.messenger.exoplayer2;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import org.telegram.messenger.exoplayer2.ExoPlayerImplInternal.PlaybackInfo;
import org.telegram.messenger.exoplayer2.ExoPlayerImplInternal.SourceInfo;
import org.telegram.messenger.exoplayer2.Player.EventListener;
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
    private final Timeline$Period period;
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
    public ExoPlayerImpl(Renderer[] renderers, TrackSelector trackSelector, LoadControl loadControl) {
        boolean z;
        Log.i(TAG, "Init " + Integer.toHexString(System.identityHashCode(this)) + " [" + ExoPlayerLibraryInfo.VERSION_SLASHY + "] [" + Util.DEVICE_DEBUG_INFO + "]");
        if (renderers.length > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.renderers = (Renderer[]) Assertions.checkNotNull(renderers);
        this.trackSelector = (TrackSelector) Assertions.checkNotNull(trackSelector);
        this.playWhenReady = false;
        this.repeatMode = 0;
        this.playbackState = 1;
        this.listeners = new CopyOnWriteArraySet();
        this.emptyTrackSelections = new TrackSelectionArray(new TrackSelection[renderers.length]);
        this.timeline = Timeline.EMPTY;
        this.window = new Window();
        this.period = new Timeline$Period();
        this.trackGroups = TrackGroupArray.EMPTY;
        this.trackSelections = this.emptyTrackSelections;
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.eventHandler = new Handler(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                ExoPlayerImpl.this.handleEvent(msg);
            }
        };
        this.playbackInfo = new PlaybackInfo(0, 0);
        this.internalPlayer = new ExoPlayerImplInternal(renderers, trackSelector, loadControl, this.playWhenReady, this.repeatMode, this.eventHandler, this.playbackInfo, this);
    }

    public Looper getPlaybackLooper() {
        return this.internalPlayer.getPlaybackLooper();
    }

    public void addListener(EventListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(EventListener listener) {
        this.listeners.remove(listener);
    }

    public int getPlaybackState() {
        return this.playbackState;
    }

    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    public void prepare(MediaSource mediaSource, boolean resetPosition, boolean resetState) {
        if (resetState) {
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
        this.internalPlayer.prepare(mediaSource, resetPosition);
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        if (this.playWhenReady != playWhenReady) {
            this.playWhenReady = playWhenReady;
            this.internalPlayer.setPlayWhenReady(playWhenReady);
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((EventListener) it.next()).onPlayerStateChanged(playWhenReady, this.playbackState);
            }
        }
    }

    public boolean getPlayWhenReady() {
        return this.playWhenReady;
    }

    public void setRepeatMode(int repeatMode) {
        if (this.repeatMode != repeatMode) {
            this.repeatMode = repeatMode;
            this.internalPlayer.setRepeatMode(repeatMode);
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((EventListener) it.next()).onRepeatModeChanged(repeatMode);
            }
        }
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    public void seekToDefaultPosition(int windowIndex) {
        seekTo(windowIndex, C0907C.TIME_UNSET);
    }

    public void seekTo(long positionMs) {
        seekTo(getCurrentWindowIndex(), positionMs);
    }

    public void seekTo(int windowIndex, long positionMs) {
        if (windowIndex < 0 || (!this.timeline.isEmpty() && windowIndex >= this.timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(this.timeline, windowIndex, positionMs);
        }
        this.pendingSeekAcks++;
        this.maskingWindowIndex = windowIndex;
        if (this.timeline.isEmpty()) {
            this.maskingPeriodIndex = 0;
        } else {
            this.timeline.getWindow(windowIndex, this.window);
            long resolvedPositionUs = positionMs == C0907C.TIME_UNSET ? this.window.getDefaultPositionUs() : C0907C.msToUs(positionMs);
            int periodIndex = this.window.firstPeriodIndex;
            long periodPositionUs = this.window.getPositionInFirstPeriodUs() + resolvedPositionUs;
            long periodDurationUs = this.timeline.getPeriod(periodIndex, this.period).getDurationUs();
            while (periodDurationUs != C0907C.TIME_UNSET && periodPositionUs >= periodDurationUs && periodIndex < this.window.lastPeriodIndex) {
                periodPositionUs -= periodDurationUs;
                periodIndex++;
                periodDurationUs = this.timeline.getPeriod(periodIndex, this.period).getDurationUs();
            }
            this.maskingPeriodIndex = periodIndex;
        }
        if (positionMs == C0907C.TIME_UNSET) {
            this.maskingWindowPositionMs = 0;
            this.internalPlayer.seekTo(this.timeline, windowIndex, C0907C.TIME_UNSET);
            return;
        }
        this.maskingWindowPositionMs = positionMs;
        this.internalPlayer.seekTo(this.timeline, windowIndex, C0907C.msToUs(positionMs));
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((EventListener) it.next()).onPositionDiscontinuity();
        }
    }

    public void setPlaybackParameters(@Nullable PlaybackParameters playbackParameters) {
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        this.internalPlayer.setPlaybackParameters(playbackParameters);
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public void stop() {
        this.internalPlayer.stop();
    }

    public void release() {
        Log.i(TAG, "Release " + Integer.toHexString(System.identityHashCode(this)) + " [" + ExoPlayerLibraryInfo.VERSION_SLASHY + "] [" + Util.DEVICE_DEBUG_INFO + "] [" + ExoPlayerLibraryInfo.registeredModules() + "]");
        this.internalPlayer.release();
        this.eventHandler.removeCallbacksAndMessages(null);
    }

    public void sendMessages(ExoPlayer$ExoPlayerMessage... messages) {
        this.internalPlayer.sendMessages(messages);
    }

    public void blockingSendMessages(ExoPlayer$ExoPlayerMessage... messages) {
        this.internalPlayer.blockingSendMessages(messages);
    }

    public int getCurrentPeriodIndex() {
        if (this.timeline.isEmpty() || this.pendingSeekAcks > 0) {
            return this.maskingPeriodIndex;
        }
        return this.playbackInfo.periodId.periodIndex;
    }

    public int getCurrentWindowIndex() {
        if (this.timeline.isEmpty() || this.pendingSeekAcks > 0) {
            return this.maskingWindowIndex;
        }
        return this.timeline.getPeriod(this.playbackInfo.periodId.periodIndex, this.period).windowIndex;
    }

    public long getDuration() {
        if (this.timeline.isEmpty()) {
            return C0907C.TIME_UNSET;
        }
        if (!isPlayingAd()) {
            return this.timeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
        }
        MediaPeriodId periodId = this.playbackInfo.periodId;
        this.timeline.getPeriod(periodId.periodIndex, this.period);
        return C0907C.usToMs(this.period.getAdDurationUs(periodId.adGroupIndex, periodId.adIndexInAdGroup));
    }

    public long getCurrentPosition() {
        if (this.timeline.isEmpty() || this.pendingSeekAcks > 0) {
            return this.maskingWindowPositionMs;
        }
        return playbackInfoPositionUsToWindowPositionMs(this.playbackInfo.positionUs);
    }

    public long getBufferedPosition() {
        if (this.timeline.isEmpty() || this.pendingSeekAcks > 0) {
            return this.maskingWindowPositionMs;
        }
        return playbackInfoPositionUsToWindowPositionMs(this.playbackInfo.bufferedPositionUs);
    }

    public int getBufferedPercentage() {
        int i = 100;
        if (this.timeline.isEmpty()) {
            return 0;
        }
        long position = getBufferedPosition();
        long duration = getDuration();
        if (position == C0907C.TIME_UNSET || duration == C0907C.TIME_UNSET) {
            i = 0;
        } else if (duration != 0) {
            i = Util.constrainValue((int) ((100 * position) / duration), 0, 100);
        }
        return i;
    }

    public boolean isCurrentWindowDynamic() {
        return !this.timeline.isEmpty() && this.timeline.getWindow(getCurrentWindowIndex(), this.window).isDynamic;
    }

    public boolean isCurrentWindowSeekable() {
        return !this.timeline.isEmpty() && this.timeline.getWindow(getCurrentWindowIndex(), this.window).isSeekable;
    }

    public boolean isPlayingAd() {
        return !this.timeline.isEmpty() && this.pendingSeekAcks == 0 && this.playbackInfo.periodId.isAd();
    }

    public int getCurrentAdGroupIndex() {
        return isPlayingAd() ? this.playbackInfo.periodId.adGroupIndex : -1;
    }

    public int getCurrentAdIndexInAdGroup() {
        return isPlayingAd() ? this.playbackInfo.periodId.adIndexInAdGroup : -1;
    }

    public long getContentPosition() {
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        this.timeline.getPeriod(this.playbackInfo.periodId.periodIndex, this.period);
        return this.period.getPositionInWindowMs() + C0907C.usToMs(this.playbackInfo.contentPositionUs);
    }

    public int getRendererCount() {
        return this.renderers.length;
    }

    public int getRendererType(int index) {
        return this.renderers[index].getTrackType();
    }

    public TrackGroupArray getCurrentTrackGroups() {
        return this.trackGroups;
    }

    public TrackSelectionArray getCurrentTrackSelections() {
        return this.trackSelections;
    }

    public Timeline getCurrentTimeline() {
        return this.timeline;
    }

    public Object getCurrentManifest() {
        return this.manifest;
    }

    void handleEvent(Message msg) {
        boolean z = true;
        Iterator it;
        switch (msg.what) {
            case 0:
                this.pendingPrepareAcks--;
                return;
            case 1:
                this.playbackState = msg.arg1;
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onPlayerStateChanged(this.playWhenReady, this.playbackState);
                }
                return;
            case 2:
                if (msg.arg1 == 0) {
                    z = false;
                }
                this.isLoading = z;
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onLoadingChanged(this.isLoading);
                }
                return;
            case 3:
                if (this.pendingPrepareAcks == 0) {
                    TrackSelectorResult trackSelectorResult = msg.obj;
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
                    this.playbackInfo = (PlaybackInfo) msg.obj;
                    if (this.timeline.isEmpty()) {
                        this.maskingPeriodIndex = 0;
                        this.maskingWindowIndex = 0;
                        this.maskingWindowPositionMs = 0;
                    }
                    if (msg.arg1 != 0) {
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
                    this.playbackInfo = (PlaybackInfo) msg.obj;
                    it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((EventListener) it.next()).onPositionDiscontinuity();
                    }
                    return;
                }
                return;
            case 6:
                SourceInfo sourceInfo = msg.obj;
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
                PlaybackParameters playbackParameters = msg.obj;
                if (!this.playbackParameters.equals(playbackParameters)) {
                    this.playbackParameters = playbackParameters;
                    it = this.listeners.iterator();
                    while (it.hasNext()) {
                        ((EventListener) it.next()).onPlaybackParametersChanged(playbackParameters);
                    }
                    return;
                }
                return;
            case 8:
                ExoPlaybackException exception = msg.obj;
                it = this.listeners.iterator();
                while (it.hasNext()) {
                    ((EventListener) it.next()).onPlayerError(exception);
                }
                return;
            default:
                throw new IllegalStateException();
        }
    }

    private long playbackInfoPositionUsToWindowPositionMs(long positionUs) {
        long positionMs = C0907C.usToMs(positionUs);
        if (this.playbackInfo.periodId.isAd()) {
            return positionMs;
        }
        this.timeline.getPeriod(this.playbackInfo.periodId.periodIndex, this.period);
        return positionMs + this.period.getPositionInWindowMs();
    }
}
