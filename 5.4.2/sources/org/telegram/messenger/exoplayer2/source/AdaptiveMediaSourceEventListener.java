package org.telegram.messenger.exoplayer2.source;

import android.os.Handler;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.Assertions;

public interface AdaptiveMediaSourceEventListener {

    public static final class EventDispatcher {
        private final Handler handler;
        private final AdaptiveMediaSourceEventListener listener;
        private final long mediaTimeOffsetMs;

        public EventDispatcher(Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
            this(handler, adaptiveMediaSourceEventListener, 0);
        }

        public EventDispatcher(Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener, long j) {
            this.handler = adaptiveMediaSourceEventListener != null ? (Handler) Assertions.checkNotNull(handler) : null;
            this.listener = adaptiveMediaSourceEventListener;
            this.mediaTimeOffsetMs = j;
        }

        private long adjustMediaTime(long j) {
            long usToMs = C3446C.usToMs(j);
            return usToMs == C3446C.TIME_UNSET ? C3446C.TIME_UNSET : this.mediaTimeOffsetMs + usToMs;
        }

        public EventDispatcher copyWithMediaTimeOffsetMs(long j) {
            return new EventDispatcher(this.handler, this.listener, j);
        }

        public void downstreamFormatChanged(int i, Format format, int i2, Object obj, long j) {
            if (this.listener != null) {
                final int i3 = i;
                final Format format2 = format;
                final int i4 = i2;
                final Object obj2 = obj;
                final long j2 = j;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onDownstreamFormatChanged(i3, format2, i4, obj2, EventDispatcher.this.adjustMediaTime(j2));
                    }
                });
            }
        }

        public void loadCanceled(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5) {
            if (this.listener != null) {
                final DataSpec dataSpec2 = dataSpec;
                final int i4 = i;
                final int i5 = i2;
                final Format format2 = format;
                final int i6 = i3;
                final Object obj2 = obj;
                final long j6 = j;
                final long j7 = j2;
                final long j8 = j3;
                final long j9 = j4;
                final long j10 = j5;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onLoadCanceled(dataSpec2, i4, i5, format2, i6, obj2, EventDispatcher.this.adjustMediaTime(j6), EventDispatcher.this.adjustMediaTime(j7), j8, j9, j10);
                    }
                });
            }
        }

        public void loadCanceled(DataSpec dataSpec, int i, long j, long j2, long j3) {
            loadCanceled(dataSpec, i, -1, null, 0, null, C3446C.TIME_UNSET, C3446C.TIME_UNSET, j, j2, j3);
        }

        public void loadCompleted(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5) {
            if (this.listener != null) {
                final DataSpec dataSpec2 = dataSpec;
                final int i4 = i;
                final int i5 = i2;
                final Format format2 = format;
                final int i6 = i3;
                final Object obj2 = obj;
                final long j6 = j;
                final long j7 = j2;
                final long j8 = j3;
                final long j9 = j4;
                final long j10 = j5;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onLoadCompleted(dataSpec2, i4, i5, format2, i6, obj2, EventDispatcher.this.adjustMediaTime(j6), EventDispatcher.this.adjustMediaTime(j7), j8, j9, j10);
                    }
                });
            }
        }

        public void loadCompleted(DataSpec dataSpec, int i, long j, long j2, long j3) {
            loadCompleted(dataSpec, i, -1, null, 0, null, C3446C.TIME_UNSET, C3446C.TIME_UNSET, j, j2, j3);
        }

        public void loadError(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5, IOException iOException, boolean z) {
            if (this.listener != null) {
                final DataSpec dataSpec2 = dataSpec;
                final int i4 = i;
                final int i5 = i2;
                final Format format2 = format;
                final int i6 = i3;
                final Object obj2 = obj;
                final long j6 = j;
                final long j7 = j2;
                final long j8 = j3;
                final long j9 = j4;
                final long j10 = j5;
                final IOException iOException2 = iOException;
                final boolean z2 = z;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onLoadError(dataSpec2, i4, i5, format2, i6, obj2, EventDispatcher.this.adjustMediaTime(j6), EventDispatcher.this.adjustMediaTime(j7), j8, j9, j10, iOException2, z2);
                    }
                });
            }
        }

        public void loadError(DataSpec dataSpec, int i, long j, long j2, long j3, IOException iOException, boolean z) {
            loadError(dataSpec, i, -1, null, 0, null, C3446C.TIME_UNSET, C3446C.TIME_UNSET, j, j2, j3, iOException, z);
        }

        public void loadStarted(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3) {
            if (this.listener != null) {
                final DataSpec dataSpec2 = dataSpec;
                final int i4 = i;
                final int i5 = i2;
                final Format format2 = format;
                final int i6 = i3;
                final Object obj2 = obj;
                final long j4 = j;
                final long j5 = j2;
                final long j6 = j3;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onLoadStarted(dataSpec2, i4, i5, format2, i6, obj2, EventDispatcher.this.adjustMediaTime(j4), EventDispatcher.this.adjustMediaTime(j5), j6);
                    }
                });
            }
        }

        public void loadStarted(DataSpec dataSpec, int i, long j) {
            loadStarted(dataSpec, i, -1, null, 0, null, C3446C.TIME_UNSET, C3446C.TIME_UNSET, j);
        }

        public void upstreamDiscarded(int i, long j, long j2) {
            if (this.listener != null) {
                final int i2 = i;
                final long j3 = j;
                final long j4 = j2;
                this.handler.post(new Runnable() {
                    public void run() {
                        EventDispatcher.this.listener.onUpstreamDiscarded(i2, EventDispatcher.this.adjustMediaTime(j3), EventDispatcher.this.adjustMediaTime(j4));
                    }
                });
            }
        }
    }

    void onDownstreamFormatChanged(int i, Format format, int i2, Object obj, long j);

    void onLoadCanceled(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5);

    void onLoadCompleted(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5);

    void onLoadError(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5, IOException iOException, boolean z);

    void onLoadStarted(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3);

    void onUpstreamDiscarded(int i, long j, long j2);
}
