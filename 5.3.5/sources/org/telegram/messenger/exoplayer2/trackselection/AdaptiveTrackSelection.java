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

        public Factory(BandwidthMeter bandwidthMeter, int maxInitialBitrate, int minDurationForQualityIncreaseMs, int maxDurationForQualityDecreaseMs, int minDurationToRetainAfterDiscardMs, float bandwidthFraction) {
            this.bandwidthMeter = bandwidthMeter;
            this.maxInitialBitrate = maxInitialBitrate;
            this.minDurationForQualityIncreaseMs = minDurationForQualityIncreaseMs;
            this.maxDurationForQualityDecreaseMs = maxDurationForQualityDecreaseMs;
            this.minDurationToRetainAfterDiscardMs = minDurationToRetainAfterDiscardMs;
            this.bandwidthFraction = bandwidthFraction;
        }

        public AdaptiveTrackSelection createTrackSelection(TrackGroup group, int... tracks) {
            return new AdaptiveTrackSelection(group, tracks, this.bandwidthMeter, this.maxInitialBitrate, (long) this.minDurationForQualityIncreaseMs, (long) this.maxDurationForQualityDecreaseMs, (long) this.minDurationToRetainAfterDiscardMs, this.bandwidthFraction);
        }
    }

    public AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter) {
        this(group, tracks, bandwidthMeter, DEFAULT_MAX_INITIAL_BITRATE, 10000, 25000, 25000, 0.75f);
    }

    public AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter, int maxInitialBitrate, long minDurationForQualityIncreaseMs, long maxDurationForQualityDecreaseMs, long minDurationToRetainAfterDiscardMs, float bandwidthFraction) {
        super(group, tracks);
        this.bandwidthMeter = bandwidthMeter;
        this.maxInitialBitrate = maxInitialBitrate;
        this.minDurationForQualityIncreaseUs = minDurationForQualityIncreaseMs * 1000;
        this.maxDurationForQualityDecreaseUs = maxDurationForQualityDecreaseMs * 1000;
        this.minDurationToRetainAfterDiscardUs = minDurationToRetainAfterDiscardMs * 1000;
        this.bandwidthFraction = bandwidthFraction;
        this.selectedIndex = determineIdealSelectedIndex(Long.MIN_VALUE);
        this.reason = 1;
    }

    public void updateSelectedTrack(long bufferedDurationUs) {
        long nowMs = SystemClock.elapsedRealtime();
        int currentSelectedIndex = this.selectedIndex;
        this.selectedIndex = determineIdealSelectedIndex(nowMs);
        if (this.selectedIndex != currentSelectedIndex) {
            if (!isBlacklisted(currentSelectedIndex, nowMs)) {
                Format currentFormat = getFormat(currentSelectedIndex);
                Format selectedFormat = getFormat(this.selectedIndex);
                if (selectedFormat.bitrate > currentFormat.bitrate && bufferedDurationUs < this.minDurationForQualityIncreaseUs) {
                    this.selectedIndex = currentSelectedIndex;
                } else if (selectedFormat.bitrate < currentFormat.bitrate && bufferedDurationUs >= this.maxDurationForQualityDecreaseUs) {
                    this.selectedIndex = currentSelectedIndex;
                }
            }
            if (this.selectedIndex != currentSelectedIndex) {
                this.reason = 3;
            }
        }
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public int getSelectionReason() {
        return this.reason;
    }

    public Object getSelectionData() {
        return null;
    }

    public int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        if (queue.isEmpty()) {
            return 0;
        }
        int queueSize = queue.size();
        if (((MediaChunk) queue.get(queueSize - 1)).endTimeUs - playbackPositionUs < this.minDurationToRetainAfterDiscardUs) {
            return queueSize;
        }
        Format idealFormat = getFormat(determineIdealSelectedIndex(SystemClock.elapsedRealtime()));
        for (int i = 0; i < queueSize; i++) {
            MediaChunk chunk = (MediaChunk) queue.get(i);
            Format format = chunk.trackFormat;
            if (chunk.startTimeUs - playbackPositionUs >= this.minDurationToRetainAfterDiscardUs && format.bitrate < idealFormat.bitrate && format.height != -1 && format.height < 720 && format.width != -1 && format.width < 1280 && format.height < idealFormat.height) {
                return i;
            }
        }
        return queueSize;
    }

    private int determineIdealSelectedIndex(long nowMs) {
        long bitrateEstimate = this.bandwidthMeter.getBitrateEstimate();
        long effectiveBitrate = bitrateEstimate == -1 ? (long) this.maxInitialBitrate : (long) (((float) bitrateEstimate) * this.bandwidthFraction);
        int lowestBitrateNonBlacklistedIndex = 0;
        int i = 0;
        while (i < this.length) {
            if (nowMs == Long.MIN_VALUE || !isBlacklisted(i, nowMs)) {
                if (((long) getFormat(i).bitrate) <= effectiveBitrate) {
                    return i;
                }
                lowestBitrateNonBlacklistedIndex = i;
            }
            i++;
        }
        return lowestBitrateNonBlacklistedIndex;
    }
}
