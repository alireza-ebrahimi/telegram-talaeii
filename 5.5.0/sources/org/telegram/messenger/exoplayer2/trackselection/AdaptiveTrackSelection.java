package org.telegram.messenger.exoplayer2.trackselection;

import android.os.SystemClock;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.chunk.MediaChunk;
import org.telegram.messenger.exoplayer2.upstream.BandwidthMeter;

public class AdaptiveTrackSelection extends BaseTrackSelection {
    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.75f;
    public static final int DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS = 25000;
    public static final int DEFAULT_MAX_INITIAL_BITRATE = 800000;
    public static final int DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS = 10000;
    public static final int DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS = 25000;
    private final float bandwidthFraction;
    private final BandwidthMeter bandwidthMeter;
    private final long maxDurationForQualityDecreaseUs;
    private final int maxInitialBitrate;
    private final long minDurationForQualityIncreaseUs;
    private final long minDurationToRetainAfterDiscardUs;
    private int reason;
    private int selectedIndex;

    public static final class Factory implements org.telegram.messenger.exoplayer2.trackselection.TrackSelection.Factory {
        private final float bandwidthFraction;
        private final BandwidthMeter bandwidthMeter;
        private final int maxDurationForQualityDecreaseMs;
        private final int maxInitialBitrate;
        private final int minDurationForQualityIncreaseMs;
        private final int minDurationToRetainAfterDiscardMs;

        public Factory(BandwidthMeter bandwidthMeter) {
            this(bandwidthMeter, AdaptiveTrackSelection.DEFAULT_MAX_INITIAL_BITRATE, 10000, 25000, 25000, 0.75f);
        }

        public Factory(BandwidthMeter bandwidthMeter, int i, int i2, int i3, int i4, float f) {
            this.bandwidthMeter = bandwidthMeter;
            this.maxInitialBitrate = i;
            this.minDurationForQualityIncreaseMs = i2;
            this.maxDurationForQualityDecreaseMs = i3;
            this.minDurationToRetainAfterDiscardMs = i4;
            this.bandwidthFraction = f;
        }

        public AdaptiveTrackSelection createTrackSelection(TrackGroup trackGroup, int... iArr) {
            return new AdaptiveTrackSelection(trackGroup, iArr, this.bandwidthMeter, this.maxInitialBitrate, (long) this.minDurationForQualityIncreaseMs, (long) this.maxDurationForQualityDecreaseMs, (long) this.minDurationToRetainAfterDiscardMs, this.bandwidthFraction);
        }
    }

    public AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, BandwidthMeter bandwidthMeter) {
        this(trackGroup, iArr, bandwidthMeter, DEFAULT_MAX_INITIAL_BITRATE, 10000, 25000, 25000, 0.75f);
    }

    public AdaptiveTrackSelection(TrackGroup trackGroup, int[] iArr, BandwidthMeter bandwidthMeter, int i, long j, long j2, long j3, float f) {
        super(trackGroup, iArr);
        this.bandwidthMeter = bandwidthMeter;
        this.maxInitialBitrate = i;
        this.minDurationForQualityIncreaseUs = j * 1000;
        this.maxDurationForQualityDecreaseUs = j2 * 1000;
        this.minDurationToRetainAfterDiscardUs = j3 * 1000;
        this.bandwidthFraction = f;
        this.selectedIndex = determineIdealSelectedIndex(Long.MIN_VALUE);
        this.reason = 1;
    }

    private int determineIdealSelectedIndex(long j) {
        int i = 0;
        long bitrateEstimate = this.bandwidthMeter.getBitrateEstimate();
        bitrateEstimate = bitrateEstimate == -1 ? (long) this.maxInitialBitrate : (long) (((float) bitrateEstimate) * this.bandwidthFraction);
        int i2 = 0;
        while (i < this.length) {
            if (j == Long.MIN_VALUE || !isBlacklisted(i, j)) {
                if (((long) getFormat(i).bitrate) <= bitrateEstimate) {
                    return i;
                }
                i2 = i;
            }
            i++;
        }
        return i2;
    }

    public int evaluateQueueSize(long j, List<? extends MediaChunk> list) {
        int i = 0;
        if (list.isEmpty()) {
            return 0;
        }
        int size = list.size();
        if (((MediaChunk) list.get(size - 1)).endTimeUs - j < this.minDurationToRetainAfterDiscardUs) {
            return size;
        }
        Format format = getFormat(determineIdealSelectedIndex(SystemClock.elapsedRealtime()));
        while (i < size) {
            MediaChunk mediaChunk = (MediaChunk) list.get(i);
            Format format2 = mediaChunk.trackFormat;
            if (mediaChunk.startTimeUs - j >= this.minDurationToRetainAfterDiscardUs && format2.bitrate < format.bitrate && format2.height != -1 && format2.height < 720 && format2.width != -1 && format2.width < 1280 && format2.height < format.height) {
                return i;
            }
            i++;
        }
        return size;
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public Object getSelectionData() {
        return null;
    }

    public int getSelectionReason() {
        return this.reason;
    }

    public void updateSelectedTrack(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i = this.selectedIndex;
        this.selectedIndex = determineIdealSelectedIndex(elapsedRealtime);
        if (this.selectedIndex != i) {
            if (!isBlacklisted(i, elapsedRealtime)) {
                Format format = getFormat(i);
                Format format2 = getFormat(this.selectedIndex);
                if (format2.bitrate > format.bitrate && j < this.minDurationForQualityIncreaseUs) {
                    this.selectedIndex = i;
                } else if (format2.bitrate < format.bitrate && j >= this.maxDurationForQualityDecreaseUs) {
                    this.selectedIndex = i;
                }
            }
            if (this.selectedIndex != i) {
                this.reason = 3;
            }
        }
    }
}
