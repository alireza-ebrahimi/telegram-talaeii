package org.telegram.messenger.exoplayer2.video;

import android.os.Handler;
import android.view.Surface;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.decoder.DecoderCounters;
import org.telegram.messenger.exoplayer2.util.Assertions;

public interface VideoRendererEventListener {

    public static final class EventDispatcher {
        private final Handler handler;
        private final VideoRendererEventListener listener;

        public EventDispatcher(Handler handler, VideoRendererEventListener videoRendererEventListener) {
            this.handler = videoRendererEventListener != null ? (Handler) Assertions.checkNotNull(handler) : null;
            this.listener = videoRendererEventListener;
        }

        public void decoderInitialized(String str, long j, long j2) {
            if (this.listener != null) {
                final String str2 = str;
                final long j3 = j;
                final long j4 = j2;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onVideoDecoderInitialized(str2, j3, j4);
                    }
                });
            }
        }

        public void disabled(final DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new Runnable() {
                    public void run() {
                        decoderCounters.ensureUpdated();
                        EventDispatcher.this.listener.onVideoDisabled(decoderCounters);
                    }
                });
            }
        }

        public void droppedFrames(final int i, final long j) {
            if (this.listener != null) {
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onDroppedFrames(i, j);
                    }
                });
            }
        }

        public void enabled(final DecoderCounters decoderCounters) {
            if (this.listener != null) {
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onVideoEnabled(decoderCounters);
                    }
                });
            }
        }

        public void inputFormatChanged(final Format format) {
            if (this.listener != null) {
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onVideoInputFormatChanged(format);
                    }
                });
            }
        }

        public void renderedFirstFrame(final Surface surface) {
            if (this.listener != null) {
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onRenderedFirstFrame(surface);
                    }
                });
            }
        }

        public void videoSizeChanged(int i, int i2, int i3, float f) {
            if (this.listener != null) {
                final int i4 = i;
                final int i5 = i2;
                final int i6 = i3;
                final float f2 = f;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onVideoSizeChanged(i4, i5, i6, f2);
                    }
                });
            }
        }
    }

    void onDroppedFrames(int i, long j);

    void onRenderedFirstFrame(Surface surface);

    void onVideoDecoderInitialized(String str, long j, long j2);

    void onVideoDisabled(DecoderCounters decoderCounters);

    void onVideoEnabled(DecoderCounters decoderCounters);

    void onVideoInputFormatChanged(Format format);

    void onVideoSizeChanged(int i, int i2, int i3, float f);
}
