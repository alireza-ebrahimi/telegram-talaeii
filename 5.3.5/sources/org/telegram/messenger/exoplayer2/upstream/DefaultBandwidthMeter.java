package org.telegram.messenger.exoplayer2.upstream;

import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.exoplayer2.upstream.BandwidthMeter.EventListener;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Clock;
import org.telegram.messenger.exoplayer2.util.SlidingPercentile;

public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener<Object> {
    private static final int BYTES_TRANSFERRED_FOR_ESTIMATE = 524288;
    public static final int DEFAULT_MAX_WEIGHT = 2000;
    private static final int ELAPSED_MILLIS_FOR_ESTIMATE = 2000;
    private long bitrateEstimate;
    private final Clock clock;
    private final Handler eventHandler;
    private final EventListener eventListener;
    private long sampleBytesTransferred;
    private long sampleStartTimeMs;
    private final SlidingPercentile slidingPercentile;
    private int streamCount;
    private long totalBytesTransferred;
    private long totalElapsedTimeMs;

    public DefaultBandwidthMeter() {
        this(null, null);
    }

    public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener) {
        this(eventHandler, eventListener, 2000);
    }

    public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, int maxWeight) {
        this(eventHandler, eventListener, maxWeight, Clock.DEFAULT);
    }

    public DefaultBandwidthMeter(Handler eventHandler, EventListener eventListener, int maxWeight, Clock clock) {
        this.eventHandler = eventHandler;
        this.eventListener = eventListener;
        this.slidingPercentile = new SlidingPercentile(maxWeight);
        this.clock = clock;
        this.bitrateEstimate = -1;
    }

    public synchronized long getBitrateEstimate() {
        return this.bitrateEstimate;
    }

    public synchronized void onTransferStart(Object source, DataSpec dataSpec) {
        if (this.streamCount == 0) {
            this.sampleStartTimeMs = this.clock.elapsedRealtime();
        }
        this.streamCount++;
    }

    public synchronized void onBytesTransferred(Object source, int bytes) {
        this.sampleBytesTransferred += (long) bytes;
    }

    public synchronized void onTransferEnd(Object source) {
        Assertions.checkState(this.streamCount > 0);
        long nowMs = this.clock.elapsedRealtime();
        int sampleElapsedTimeMs = (int) (nowMs - this.sampleStartTimeMs);
        this.totalElapsedTimeMs += (long) sampleElapsedTimeMs;
        this.totalBytesTransferred += this.sampleBytesTransferred;
        if (sampleElapsedTimeMs > 0) {
            this.slidingPercentile.addSample((int) Math.sqrt((double) this.sampleBytesTransferred), (float) ((this.sampleBytesTransferred * 8000) / ((long) sampleElapsedTimeMs)));
            if (this.totalElapsedTimeMs >= FetchConst.DEFAULT_ON_UPDATE_INTERVAL || this.totalBytesTransferred >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                float bitrateEstimateFloat = this.slidingPercentile.getPercentile(0.5f);
                this.bitrateEstimate = Float.isNaN(bitrateEstimateFloat) ? -1 : (long) bitrateEstimateFloat;
            }
        }
        notifyBandwidthSample(sampleElapsedTimeMs, this.sampleBytesTransferred, this.bitrateEstimate);
        int i = this.streamCount - 1;
        this.streamCount = i;
        if (i > 0) {
            this.sampleStartTimeMs = nowMs;
        }
        this.sampleBytesTransferred = 0;
    }

    private void notifyBandwidthSample(int elapsedMs, long bytes, long bitrate) {
        if (this.eventHandler != null && this.eventListener != null) {
            final int i = elapsedMs;
            final long j = bytes;
            final long j2 = bitrate;
            this.eventHandler.post(new Runnable() {
                public void run() {
                    DefaultBandwidthMeter.this.eventListener.onBandwidthSample(i, j, j2);
                }
            });
        }
    }
}
