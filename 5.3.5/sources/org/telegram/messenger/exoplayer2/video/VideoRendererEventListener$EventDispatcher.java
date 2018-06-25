package org.telegram.messenger.exoplayer2.video;

import android.os.Handler;
import android.view.Surface;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.decoder.DecoderCounters;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class VideoRendererEventListener$EventDispatcher {
    private final Handler handler;
    private final VideoRendererEventListener listener;

    public VideoRendererEventListener$EventDispatcher(Handler handler, VideoRendererEventListener listener) {
        this.handler = listener != null ? (Handler) Assertions.checkNotNull(handler) : null;
        this.listener = listener;
    }

    public void enabled(final DecoderCounters decoderCounters) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    VideoRendererEventListener$EventDispatcher.this.listener.onVideoEnabled(decoderCounters);
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
                    VideoRendererEventListener$EventDispatcher.this.listener.onVideoDecoderInitialized(str, j, j2);
                }
            });
        }
    }

    public void inputFormatChanged(final Format format) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    VideoRendererEventListener$EventDispatcher.this.listener.onVideoInputFormatChanged(format);
                }
            });
        }
    }

    public void droppedFrames(final int droppedFrameCount, final long elapsedMs) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    VideoRendererEventListener$EventDispatcher.this.listener.onDroppedFrames(droppedFrameCount, elapsedMs);
                }
            });
        }
    }

    public void videoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        if (this.listener != null) {
            final int i = width;
            final int i2 = height;
            final int i3 = unappliedRotationDegrees;
            final float f = pixelWidthHeightRatio;
            this.handler.post(new Runnable() {
                public void run() {
                    VideoRendererEventListener$EventDispatcher.this.listener.onVideoSizeChanged(i, i2, i3, f);
                }
            });
        }
    }

    public void renderedFirstFrame(final Surface surface) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    VideoRendererEventListener$EventDispatcher.this.listener.onRenderedFirstFrame(surface);
                }
            });
        }
    }

    public void disabled(final DecoderCounters counters) {
        if (this.listener != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    counters.ensureUpdated();
                    VideoRendererEventListener$EventDispatcher.this.listener.onVideoDisabled(counters);
                }
            });
        }
    }
}
