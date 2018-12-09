package org.telegram.messenger.exoplayer2.util;

import org.telegram.messenger.exoplayer2.PlaybackParameters;

public interface MediaClock {
    PlaybackParameters getPlaybackParameters();

    long getPositionUs();

    PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters);
}
