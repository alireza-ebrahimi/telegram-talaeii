package org.telegram.messenger.exoplayer2;

import org.telegram.messenger.exoplayer2.ExoPlayer.ExoPlayerComponent;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.util.MediaClock;

public interface Renderer extends ExoPlayerComponent {
    public static final int STATE_DISABLED = 0;
    public static final int STATE_ENABLED = 1;
    public static final int STATE_STARTED = 2;

    void disable();

    void enable(RendererConfiguration rendererConfiguration, Format[] formatArr, SampleStream sampleStream, long j, boolean z, long j2);

    RendererCapabilities getCapabilities();

    MediaClock getMediaClock();

    int getState();

    SampleStream getStream();

    int getTrackType();

    boolean hasReadStreamToEnd();

    boolean isCurrentStreamFinal();

    boolean isEnded();

    boolean isReady();

    void maybeThrowStreamError();

    void render(long j, long j2);

    void replaceStream(Format[] formatArr, SampleStream sampleStream, long j);

    void resetPosition(long j);

    void setCurrentStreamFinal();

    void setIndex(int i);

    void start();

    void stop();
}
