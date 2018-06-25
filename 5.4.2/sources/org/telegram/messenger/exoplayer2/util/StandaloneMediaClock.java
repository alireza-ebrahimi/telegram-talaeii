package org.telegram.messenger.exoplayer2.util;

import android.os.SystemClock;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.PlaybackParameters;

public final class StandaloneMediaClock implements MediaClock {
    private long baseElapsedMs;
    private long baseUs;
    private PlaybackParameters playbackParameters = PlaybackParameters.DEFAULT;
    private boolean started;

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public long getPositionUs() {
        long j = this.baseUs;
        if (!this.started) {
            return j;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.baseElapsedMs;
        return this.playbackParameters.speed == 1.0f ? j + C3446C.msToUs(elapsedRealtime) : j + this.playbackParameters.getSpeedAdjustedDurationUs(elapsedRealtime);
    }

    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (this.started) {
            setPositionUs(getPositionUs());
        }
        this.playbackParameters = playbackParameters;
        return playbackParameters;
    }

    public void setPositionUs(long j) {
        this.baseUs = j;
        if (this.started) {
            this.baseElapsedMs = SystemClock.elapsedRealtime();
        }
    }

    public void start() {
        if (!this.started) {
            this.baseElapsedMs = SystemClock.elapsedRealtime();
            this.started = true;
        }
    }

    public void stop() {
        if (this.started) {
            setPositionUs(getPositionUs());
            this.started = false;
        }
    }

    public void synchronize(MediaClock mediaClock) {
        setPositionUs(mediaClock.getPositionUs());
        this.playbackParameters = mediaClock.getPlaybackParameters();
    }
}
