package org.telegram.messenger.exoplayer2.audio;

import android.os.Handler;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.decoder.DecoderCounters;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class AudioRendererEventListener$EventDispatcher {
    private final Handler handler;
    private final AudioRendererEventListener listener;

    public AudioRendererEventListener$EventDispatcher(Handler handler, AudioRendererEventListener listener) {
        this.handler = listener != null ? (Handler) Assertions.checkNotNull(handler) : null;
        this.listener = listener;
    }

    public void enabled(final DecoderCounters decoderCounters) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    AudioRendererEventListener$EventDispatcher.this.listener.onAudioEnabled(decoderCounters);
                }
            });
        }
    }

    public void decoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        if (this.listener != null) {
            final String str = decoderName;
            final long j = initializedTimestampMs;
            final long j2 = initializationDurationMs;
            this.handler.post(new Runnable() {
                public void run() {
                    AudioRendererEventListener$EventDispatcher.this.listener.onAudioDecoderInitialized(str, j, j2);
                }
            });
        }
    }

    public void inputFormatChanged(final Format format) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    AudioRendererEventListener$EventDispatcher.this.listener.onAudioInputFormatChanged(format);
                }
            });
        }
    }

    public void audioTrackUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        if (this.listener != null) {
            final int i = bufferSize;
            final long j = bufferSizeMs;
            final long j2 = elapsedSinceLastFeedMs;
            this.handler.post(new Runnable() {
                public void run() {
                    AudioRendererEventListener$EventDispatcher.this.listener.onAudioTrackUnderrun(i, j, j2);
                }
            });
        }
    }

    public void disabled(final DecoderCounters counters) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    counters.ensureUpdated();
                    AudioRendererEventListener$EventDispatcher.this.listener.onAudioDisabled(counters);
                }
            });
        }
    }

    public void audioSessionId(final int audioSessionId) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    AudioRendererEventListener$EventDispatcher.this.listener.onAudioSessionId(audioSessionId);
                }
            });
        }
    }
}
