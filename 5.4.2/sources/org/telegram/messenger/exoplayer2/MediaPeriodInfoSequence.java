package org.telegram.messenger.exoplayer2;

import android.util.Pair;
import org.telegram.messenger.exoplayer2.ExoPlayerImplInternal.PlaybackInfo;
import org.telegram.messenger.exoplayer2.Timeline.Period;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;

final class MediaPeriodInfoSequence {
    private final Period period = new Period();
    private int repeatMode;
    private Timeline timeline;
    private final Window window = new Window();

    public static final class MediaPeriodInfo {
        public final long contentPositionUs;
        public final long durationUs;
        public final long endPositionUs;
        public final MediaPeriodId id;
        public final boolean isFinal;
        public final boolean isLastInTimelinePeriod;
        public final long startPositionUs;

        private MediaPeriodInfo(MediaPeriodId mediaPeriodId, long j, long j2, long j3, long j4, boolean z, boolean z2) {
            this.id = mediaPeriodId;
            this.startPositionUs = j;
            this.endPositionUs = j2;
            this.contentPositionUs = j3;
            this.durationUs = j4;
            this.isLastInTimelinePeriod = z;
            this.isFinal = z2;
        }

        public MediaPeriodInfo copyWithPeriodIndex(int i) {
            return new MediaPeriodInfo(this.id.copyWithPeriodIndex(i), this.startPositionUs, this.endPositionUs, this.contentPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
        }

        public MediaPeriodInfo copyWithStartPositionUs(long j) {
            return new MediaPeriodInfo(this.id, j, this.endPositionUs, this.contentPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
        }
    }

    private MediaPeriodInfo getMediaPeriodInfo(MediaPeriodId mediaPeriodId, long j, long j2) {
        this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period);
        if (mediaPeriodId.isAd()) {
            return !this.period.isAdAvailable(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup) ? null : getMediaPeriodInfoForAd(mediaPeriodId.periodIndex, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, j);
        } else {
            int adGroupIndexAfterPositionUs = this.period.getAdGroupIndexAfterPositionUs(j2);
            return getMediaPeriodInfoForContent(mediaPeriodId.periodIndex, j2, adGroupIndexAfterPositionUs == -1 ? Long.MIN_VALUE : this.period.getAdGroupTimeUs(adGroupIndexAfterPositionUs));
        }
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(int i, int i2, int i3, long j) {
        MediaPeriodId mediaPeriodId = new MediaPeriodId(i, i2, i3);
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodId, Long.MIN_VALUE);
        boolean isLastInTimeline = isLastInTimeline(mediaPeriodId, isLastInPeriod);
        return new MediaPeriodInfo(mediaPeriodId, i3 == this.period.getPlayedAdCount(i2) ? this.period.getAdResumePositionUs() : 0, Long.MIN_VALUE, j, this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period).getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup), isLastInPeriod, isLastInTimeline);
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(int i, long j, long j2) {
        MediaPeriodId mediaPeriodId = new MediaPeriodId(i);
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodId, j2);
        boolean isLastInTimeline = isLastInTimeline(mediaPeriodId, isLastInPeriod);
        this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period);
        return new MediaPeriodInfo(mediaPeriodId, j, j2, C3446C.TIME_UNSET, j2 == Long.MIN_VALUE ? this.period.getDurationUs() : j2, isLastInPeriod, isLastInTimeline);
    }

    private MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo, MediaPeriodId mediaPeriodId) {
        long j = mediaPeriodInfo.startPositionUs;
        long j2 = mediaPeriodInfo.endPositionUs;
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodId, j2);
        boolean isLastInTimeline = isLastInTimeline(mediaPeriodId, isLastInPeriod);
        this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period);
        long adDurationUs = mediaPeriodId.isAd() ? this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup) : j2 == Long.MIN_VALUE ? this.period.getDurationUs() : j2;
        return new MediaPeriodInfo(mediaPeriodId, j, j2, mediaPeriodInfo.contentPositionUs, adDurationUs, isLastInPeriod, isLastInTimeline);
    }

    private boolean isLastInPeriod(MediaPeriodId mediaPeriodId, long j) {
        boolean z = false;
        int adGroupCount = this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period).getAdGroupCount();
        if (adGroupCount == 0) {
            return true;
        }
        int i = adGroupCount - 1;
        boolean isAd = mediaPeriodId.isAd();
        if (this.period.getAdGroupTimeUs(i) != Long.MIN_VALUE) {
            return !isAd && j == Long.MIN_VALUE;
        } else {
            int adCountInAdGroup = this.period.getAdCountInAdGroup(i);
            if (adCountInAdGroup == -1) {
                return false;
            }
            boolean z2 = isAd && mediaPeriodId.adGroupIndex == i && mediaPeriodId.adIndexInAdGroup == adCountInAdGroup - 1;
            if (z2 || (!isAd && this.period.getPlayedAdCount(i) == adCountInAdGroup)) {
                z = true;
            }
            return z;
        }
    }

    private boolean isLastInTimeline(MediaPeriodId mediaPeriodId, boolean z) {
        return !this.timeline.getWindow(this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period).windowIndex, this.window).isDynamic && this.timeline.isLastPeriod(mediaPeriodId.periodIndex, this.period, this.window, this.repeatMode) && z;
    }

    public MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.periodId, playbackInfo.contentPositionUs, playbackInfo.startPositionUs);
    }

    public MediaPeriodInfo getNextMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo, long j, long j2) {
        int i;
        if (mediaPeriodInfo.isLastInTimelinePeriod) {
            int nextPeriodIndex = this.timeline.getNextPeriodIndex(mediaPeriodInfo.id.periodIndex, this.period, this.window, this.repeatMode);
            if (nextPeriodIndex == -1) {
                return null;
            }
            long longValue;
            i = this.timeline.getPeriod(nextPeriodIndex, this.period).windowIndex;
            if (this.timeline.getWindow(i, this.window).firstPeriodIndex == nextPeriodIndex) {
                Pair periodPosition = this.timeline.getPeriodPosition(this.window, this.period, i, C3446C.TIME_UNSET, Math.max(0, (mediaPeriodInfo.durationUs + j) - j2));
                if (periodPosition == null) {
                    return null;
                }
                int intValue = ((Integer) periodPosition.first).intValue();
                longValue = ((Long) periodPosition.second).longValue();
                nextPeriodIndex = intValue;
            } else {
                longValue = 0;
            }
            return getMediaPeriodInfo(resolvePeriodPositionForAds(nextPeriodIndex, longValue), longValue, longValue);
        }
        MediaPeriodId mediaPeriodId = mediaPeriodInfo.id;
        int i2;
        if (mediaPeriodId.isAd()) {
            i2 = mediaPeriodId.adGroupIndex;
            this.timeline.getPeriod(mediaPeriodId.periodIndex, this.period);
            intValue = this.period.getAdCountInAdGroup(i2);
            if (intValue == -1) {
                return null;
            }
            i = mediaPeriodId.adIndexInAdGroup + 1;
            if (i < intValue) {
                return !this.period.isAdAvailable(i2, i) ? null : getMediaPeriodInfoForAd(mediaPeriodId.periodIndex, i2, i, mediaPeriodInfo.contentPositionUs);
            } else {
                intValue = this.period.getAdGroupIndexAfterPositionUs(mediaPeriodInfo.contentPositionUs);
                return getMediaPeriodInfoForContent(mediaPeriodId.periodIndex, mediaPeriodInfo.contentPositionUs, intValue == -1 ? Long.MIN_VALUE : this.period.getAdGroupTimeUs(intValue));
            }
        } else if (mediaPeriodInfo.endPositionUs != Long.MIN_VALUE) {
            i2 = this.period.getAdGroupIndexForPositionUs(mediaPeriodInfo.endPositionUs);
            return !this.period.isAdAvailable(i2, 0) ? null : getMediaPeriodInfoForAd(mediaPeriodId.periodIndex, i2, 0, mediaPeriodInfo.endPositionUs);
        } else {
            i2 = this.period.getAdGroupCount();
            if (i2 == 0 || this.period.getAdGroupTimeUs(i2 - 1) != Long.MIN_VALUE || this.period.hasPlayedAdGroup(i2 - 1) || !this.period.isAdAvailable(i2 - 1, 0)) {
                return null;
            }
            return getMediaPeriodInfoForAd(mediaPeriodId.periodIndex, i2 - 1, 0, this.period.getDurationUs());
        }
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo) {
        return getUpdatedMediaPeriodInfo(mediaPeriodInfo, mediaPeriodInfo.id);
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo, int i) {
        return getUpdatedMediaPeriodInfo(mediaPeriodInfo, mediaPeriodInfo.id.copyWithPeriodIndex(i));
    }

    public MediaPeriodId resolvePeriodPositionForAds(int i, long j) {
        this.timeline.getPeriod(i, this.period);
        int adGroupIndexForPositionUs = this.period.getAdGroupIndexForPositionUs(j);
        return adGroupIndexForPositionUs == -1 ? new MediaPeriodId(i) : new MediaPeriodId(i, adGroupIndexForPositionUs, this.period.getPlayedAdCount(adGroupIndexForPositionUs));
    }

    public void setRepeatMode(int i) {
        this.repeatMode = i;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
