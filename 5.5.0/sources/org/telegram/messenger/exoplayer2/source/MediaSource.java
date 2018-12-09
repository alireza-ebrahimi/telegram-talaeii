package org.telegram.messenger.exoplayer2.source;

import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.upstream.Allocator;

public interface MediaSource {

    public interface Listener {
        void onSourceInfoRefreshed(Timeline timeline, Object obj);
    }

    public static final class MediaPeriodId {
        public static final MediaPeriodId UNSET = new MediaPeriodId(-1, -1, -1);
        public final int adGroupIndex;
        public final int adIndexInAdGroup;
        public final int periodIndex;

        public MediaPeriodId(int i) {
            this(i, -1, -1);
        }

        public MediaPeriodId(int i, int i2, int i3) {
            this.periodIndex = i;
            this.adGroupIndex = i2;
            this.adIndexInAdGroup = i3;
        }

        public MediaPeriodId copyWithPeriodIndex(int i) {
            return this.periodIndex == i ? this : new MediaPeriodId(i, this.adGroupIndex, this.adIndexInAdGroup);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            MediaPeriodId mediaPeriodId = (MediaPeriodId) obj;
            return this.periodIndex == mediaPeriodId.periodIndex && this.adGroupIndex == mediaPeriodId.adGroupIndex && this.adIndexInAdGroup == mediaPeriodId.adIndexInAdGroup;
        }

        public int hashCode() {
            return ((((this.periodIndex + 527) * 31) + this.adGroupIndex) * 31) + this.adIndexInAdGroup;
        }

        public boolean isAd() {
            return this.adGroupIndex != -1;
        }
    }

    MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator);

    void maybeThrowSourceInfoRefreshError();

    void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener);

    void releasePeriod(MediaPeriod mediaPeriod);

    void releaseSource();
}
