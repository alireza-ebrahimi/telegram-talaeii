package org.telegram.messenger.exoplayer2;

import android.util.Pair;
import org.telegram.messenger.exoplayer2.ExoPlayerImplInternal.PlaybackInfo;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;

final class MediaPeriodInfoSequence {
    private final Timeline$Period period = new Timeline$Period();
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

        private MediaPeriodInfo(MediaPeriodId id, long startPositionUs, long endPositionUs, long contentPositionUs, long durationUs, boolean isLastInTimelinePeriod, boolean isFinal) {
            this.id = id;
            this.startPositionUs = startPositionUs;
            this.endPositionUs = endPositionUs;
            this.contentPositionUs = contentPositionUs;
            this.durationUs = durationUs;
            this.isLastInTimelinePeriod = isLastInTimelinePeriod;
            this.isFinal = isFinal;
        }

        public MediaPeriodInfo copyWithPeriodIndex(int periodIndex) {
            return new MediaPeriodInfo(this.id.copyWithPeriodIndex(periodIndex), this.startPositionUs, this.endPositionUs, this.contentPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
        }

        public MediaPeriodInfo copyWithStartPositionUs(long startPositionUs) {
            return new MediaPeriodInfo(this.id, startPositionUs, this.endPositionUs, this.contentPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
        }
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    public MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.periodId, playbackInfo.contentPositionUs, playbackInfo.startPositionUs);
    }

    public MediaPeriodInfo getNextMediaPeriodInfo(MediaPeriodInfo currentMediaPeriodInfo, long rendererOffsetUs, long rendererPositionUs) {
        if (currentMediaPeriodInfo.isLastInTimelinePeriod) {
            int nextPeriodIndex = this.timeline.getNextPeriodIndex(currentMediaPeriodInfo.id.periodIndex, this.period, this.window, this.repeatMode);
            if (nextPeriodIndex == -1) {
                return null;
            }
            long startPositionUs;
            int nextWindowIndex = this.timeline.getPeriod(nextPeriodIndex, this.period).windowIndex;
            if (this.timeline.getWindow(nextWindowIndex, this.window).firstPeriodIndex == nextPeriodIndex) {
                Pair<Integer, Long> defaultPosition = this.timeline.getPeriodPosition(this.window, this.period, nextWindowIndex, C0907C.TIME_UNSET, Math.max(0, (currentMediaPeriodInfo.durationUs + rendererOffsetUs) - rendererPositionUs));
                if (defaultPosition == null) {
                    return null;
                }
                nextPeriodIndex = ((Integer) defaultPosition.first).intValue();
                startPositionUs = ((Long) defaultPosition.second).longValue();
            } else {
                startPositionUs = 0;
            }
            return getMediaPeriodInfo(resolvePeriodPositionForAds(nextPeriodIndex, startPositionUs), startPositionUs, startPositionUs);
        }
        MediaPeriodId currentPeriodId = currentMediaPeriodInfo.id;
        int nextAdGroupIndex;
        if (currentPeriodId.isAd()) {
            int currentAdGroupIndex = currentPeriodId.adGroupIndex;
            this.timeline.getPeriod(currentPeriodId.periodIndex, this.period);
            int adCountInCurrentAdGroup = this.period.getAdCountInAdGroup(currentAdGroupIndex);
            if (adCountInCurrentAdGroup == -1) {
                return null;
            }
            int nextAdIndexInAdGroup = currentPeriodId.adIndexInAdGroup + 1;
            if (nextAdIndexInAdGroup >= adCountInCurrentAdGroup) {
                long endUs;
                nextAdGroupIndex = this.period.getAdGroupIndexAfterPositionUs(currentMediaPeriodInfo.contentPositionUs);
                if (nextAdGroupIndex == -1) {
                    endUs = Long.MIN_VALUE;
                } else {
                    endUs = this.period.getAdGroupTimeUs(nextAdGroupIndex);
                }
                return getMediaPeriodInfoForContent(currentPeriodId.periodIndex, currentMediaPeriodInfo.contentPositionUs, endUs);
            } else if (this.period.isAdAvailable(currentAdGroupIndex, nextAdIndexInAdGroup)) {
                return getMediaPeriodInfoForAd(currentPeriodId.periodIndex, currentAdGroupIndex, nextAdIndexInAdGroup, currentMediaPeriodInfo.contentPositionUs);
            } else {
                return null;
            }
        } else if (currentMediaPeriodInfo.endPositionUs != Long.MIN_VALUE) {
            nextAdGroupIndex = this.period.getAdGroupIndexForPositionUs(currentMediaPeriodInfo.endPositionUs);
            if (this.period.isAdAvailable(nextAdGroupIndex, 0)) {
                return getMediaPeriodInfoForAd(currentPeriodId.periodIndex, nextAdGroupIndex, 0, currentMediaPeriodInfo.endPositionUs);
            }
            return null;
        } else {
            int adGroupCount = this.period.getAdGroupCount();
            if (adGroupCount == 0 || this.period.getAdGroupTimeUs(adGroupCount - 1) != Long.MIN_VALUE || this.period.hasPlayedAdGroup(adGroupCount - 1) || !this.period.isAdAvailable(adGroupCount - 1, 0)) {
                return null;
            }
            return getMediaPeriodInfoForAd(currentPeriodId.periodIndex, adGroupCount - 1, 0, this.period.getDurationUs());
        }
    }

    public MediaPeriodId resolvePeriodPositionForAds(int periodIndex, long positionUs) {
        this.timeline.getPeriod(periodIndex, this.period);
        int adGroupIndex = this.period.getAdGroupIndexForPositionUs(positionUs);
        if (adGroupIndex == -1) {
            return new MediaPeriodId(periodIndex);
        }
        return new MediaPeriodId(periodIndex, adGroupIndex, this.period.getPlayedAdCount(adGroupIndex));
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo) {
        return getUpdatedMediaPeriodInfo(mediaPeriodInfo, mediaPeriodInfo.id);
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo, int newPeriodIndex) {
        return getUpdatedMediaPeriodInfo(mediaPeriodInfo, mediaPeriodInfo.id.copyWithPeriodIndex(newPeriodIndex));
    }

    private MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo info, MediaPeriodId newId) {
        long startPositionUs = info.startPositionUs;
        long endPositionUs = info.endPositionUs;
        boolean isLastInPeriod = isLastInPeriod(newId, endPositionUs);
        boolean isLastInTimeline = isLastInTimeline(newId, isLastInPeriod);
        this.timeline.getPeriod(newId.periodIndex, this.period);
        long durationUs = newId.isAd() ? this.period.getAdDurationUs(newId.adGroupIndex, newId.adIndexInAdGroup) : endPositionUs == Long.MIN_VALUE ? this.period.getDurationUs() : endPositionUs;
        return new MediaPeriodInfo(newId, startPositionUs, endPositionUs, info.contentPositionUs, durationUs, isLastInPeriod, isLastInTimeline);
    }

    private MediaPeriodInfo getMediaPeriodInfo(MediaPeriodId id, long contentPositionUs, long startPositionUs) {
        this.timeline.getPeriod(id.periodIndex, this.period);
        if (!id.isAd()) {
            long endUs;
            int nextAdGroupIndex = this.period.getAdGroupIndexAfterPositionUs(startPositionUs);
            if (nextAdGroupIndex == -1) {
                endUs = Long.MIN_VALUE;
            } else {
                endUs = this.period.getAdGroupTimeUs(nextAdGroupIndex);
            }
            return getMediaPeriodInfoForContent(id.periodIndex, startPositionUs, endUs);
        } else if (this.period.isAdAvailable(id.adGroupIndex, id.adIndexInAdGroup)) {
            return getMediaPeriodInfoForAd(id.periodIndex, id.adGroupIndex, id.adIndexInAdGroup, contentPositionUs);
        } else {
            return null;
        }
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(int periodIndex, int adGroupIndex, int adIndexInAdGroup, long contentPositionUs) {
        MediaPeriodId id = new MediaPeriodId(periodIndex, adGroupIndex, adIndexInAdGroup);
        boolean isLastInPeriod = isLastInPeriod(id, Long.MIN_VALUE);
        boolean isLastInTimeline = isLastInTimeline(id, isLastInPeriod);
        return new MediaPeriodInfo(id, adIndexInAdGroup == this.period.getPlayedAdCount(adGroupIndex) ? this.period.getAdResumePositionUs() : 0, Long.MIN_VALUE, contentPositionUs, this.timeline.getPeriod(id.periodIndex, this.period).getAdDurationUs(id.adGroupIndex, id.adIndexInAdGroup), isLastInPeriod, isLastInTimeline);
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(int periodIndex, long startPositionUs, long endUs) {
        long durationUs;
        MediaPeriodId id = new MediaPeriodId(periodIndex);
        boolean isLastInPeriod = isLastInPeriod(id, endUs);
        boolean isLastInTimeline = isLastInTimeline(id, isLastInPeriod);
        this.timeline.getPeriod(id.periodIndex, this.period);
        if (endUs == Long.MIN_VALUE) {
            durationUs = this.period.getDurationUs();
        } else {
            durationUs = endUs;
        }
        return new MediaPeriodInfo(id, startPositionUs, endUs, C0907C.TIME_UNSET, durationUs, isLastInPeriod, isLastInTimeline);
    }

    private boolean isLastInPeriod(MediaPeriodId id, long endPositionUs) {
        boolean z = false;
        int adGroupCount = this.timeline.getPeriod(id.periodIndex, this.period).getAdGroupCount();
        if (adGroupCount == 0) {
            return true;
        }
        int lastAdGroupIndex = adGroupCount - 1;
        boolean isAd = id.isAd();
        if (this.period.getAdGroupTimeUs(lastAdGroupIndex) == Long.MIN_VALUE) {
            int postrollAdCount = this.period.getAdCountInAdGroup(lastAdGroupIndex);
            if (postrollAdCount == -1) {
                return false;
            }
            boolean isLastAd;
            if (isAd && id.adGroupIndex == lastAdGroupIndex && id.adIndexInAdGroup == postrollAdCount - 1) {
                isLastAd = true;
            } else {
                isLastAd = false;
            }
            if (isLastAd || (!isAd && this.period.getPlayedAdCount(lastAdGroupIndex) == postrollAdCount)) {
                z = true;
            }
            return z;
        } else if (isAd || endPositionUs != Long.MIN_VALUE) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isLastInTimeline(MediaPeriodId id, boolean isLastMediaPeriodInPeriod) {
        return !this.timeline.getWindow(this.timeline.getPeriod(id.periodIndex, this.period).windowIndex, this.window).isDynamic && this.timeline.isLastPeriod(id.periodIndex, this.period, this.window, this.repeatMode) && isLastMediaPeriodInPeriod;
    }
}
