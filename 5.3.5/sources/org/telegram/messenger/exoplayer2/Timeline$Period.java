package org.telegram.messenger.exoplayer2;

public final class Timeline$Period {
    private int[] adCounts;
    private long[][] adDurationsUs;
    private long[] adGroupTimesUs;
    private long adResumePositionUs;
    private int[] adsLoadedCounts;
    private int[] adsPlayedCounts;
    public long durationUs;
    public Object id;
    private long positionInWindowUs;
    public Object uid;
    public int windowIndex;

    public Timeline$Period set(Object id, Object uid, int windowIndex, long durationUs, long positionInWindowUs) {
        return set(id, uid, windowIndex, durationUs, positionInWindowUs, null, null, null, null, (long[][]) null, C0907C.TIME_UNSET);
    }

    public Timeline$Period set(Object id, Object uid, int windowIndex, long durationUs, long positionInWindowUs, long[] adGroupTimesUs, int[] adCounts, int[] adsLoadedCounts, int[] adsPlayedCounts, long[][] adDurationsUs, long adResumePositionUs) {
        this.id = id;
        this.uid = uid;
        this.windowIndex = windowIndex;
        this.durationUs = durationUs;
        this.positionInWindowUs = positionInWindowUs;
        this.adGroupTimesUs = adGroupTimesUs;
        this.adCounts = adCounts;
        this.adsLoadedCounts = adsLoadedCounts;
        this.adsPlayedCounts = adsPlayedCounts;
        this.adDurationsUs = adDurationsUs;
        this.adResumePositionUs = adResumePositionUs;
        return this;
    }

    public long getDurationMs() {
        return C0907C.usToMs(this.durationUs);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getPositionInWindowMs() {
        return C0907C.usToMs(this.positionInWindowUs);
    }

    public long getPositionInWindowUs() {
        return this.positionInWindowUs;
    }

    public int getAdGroupCount() {
        return this.adGroupTimesUs == null ? 0 : this.adGroupTimesUs.length;
    }

    public long getAdGroupTimeUs(int adGroupIndex) {
        return this.adGroupTimesUs[adGroupIndex];
    }

    public int getPlayedAdCount(int adGroupIndex) {
        return this.adsPlayedCounts[adGroupIndex];
    }

    public boolean hasPlayedAdGroup(int adGroupIndex) {
        return this.adCounts[adGroupIndex] != -1 && this.adsPlayedCounts[adGroupIndex] == this.adCounts[adGroupIndex];
    }

    public int getAdGroupIndexForPositionUs(long positionUs) {
        if (this.adGroupTimesUs == null) {
            return -1;
        }
        int index = this.adGroupTimesUs.length - 1;
        while (index >= 0 && (this.adGroupTimesUs[index] == Long.MIN_VALUE || this.adGroupTimesUs[index] > positionUs)) {
            index--;
        }
        if (index < 0 || hasPlayedAdGroup(index)) {
            index = -1;
        }
        return index;
    }

    public int getAdGroupIndexAfterPositionUs(long positionUs) {
        if (this.adGroupTimesUs == null) {
            return -1;
        }
        int index = 0;
        while (index < this.adGroupTimesUs.length && this.adGroupTimesUs[index] != Long.MIN_VALUE && (positionUs >= this.adGroupTimesUs[index] || hasPlayedAdGroup(index))) {
            index++;
        }
        if (index >= this.adGroupTimesUs.length) {
            index = -1;
        }
        return index;
    }

    public int getAdCountInAdGroup(int adGroupIndex) {
        return this.adCounts[adGroupIndex];
    }

    public boolean isAdAvailable(int adGroupIndex, int adIndexInAdGroup) {
        return adIndexInAdGroup < this.adsLoadedCounts[adGroupIndex];
    }

    public long getAdDurationUs(int adGroupIndex, int adIndexInAdGroup) {
        if (adIndexInAdGroup >= this.adDurationsUs[adGroupIndex].length) {
            return C0907C.TIME_UNSET;
        }
        return this.adDurationsUs[adGroupIndex][adIndexInAdGroup];
    }

    public long getAdResumePositionUs() {
        return this.adResumePositionUs;
    }
}
