package org.telegram.messenger.exoplayer2.util;

import android.os.SystemClock;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.PlaybackParameters;

public final class StandaloneMediaClock implements MediaClock {
    private long baseElapsedMs;
    private long baseUs;
    private PlaybackParameters playbackParameters = PlaybackParameters.DEFAULT;
    private boolean started;

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

    public void setPositionUs(long positionUs) {
        this.baseUs = positionUs;
        if (this.started) {
            this.baseElapsedMs = SystemClock.elapsedRealtime();
        }
    }

    public void synchronize(MediaClock clock) {
        setPositionUs(clock.getPositionUs());
        this.playbackParameters = clock.getPlaybackParameters();
    }

    public long getPositionUs() {
        long positionUs = this.baseUs;
        if (!this.started) {
            return positionUs;
        }
        long elapsedSinceBaseMs = SystemClock.elapsedRealtime() - this.baseElapsedMs;
        if (this.playbackParameters.speed == 1.0f) {
            return positionUs + C0907C.msToUs(elapsedSinceBaseMs);
        }
        return positionUs + this.playbackParameters.getSpeedAdjustedDurationUs(elapsedSinceBaseMs);
    }

    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (this.started) {
            setPositionUs(getPositionUs());
        }
        this.playbackParameters = playbackParameters;
        return playbackParameters;
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }
}
