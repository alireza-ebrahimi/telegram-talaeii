package org.telegram.messenger.exoplayer2.source;

import android.support.annotation.Nullable;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.upstream.Allocator;

public interface MediaSource {

    public interface Listener {
        void onSourceInfoRefreshed(Timeline timeline, @Nullable Object obj);
    }

    public static final class MediaPeriodId {
        public static final MediaPeriodId UNSET = new MediaPeriodId(-1, -1, -1);
        public final int adGroupIndex;
        public final int adIndexInAdGroup;
        public final int periodIndex;

        public MediaPeriodId(int periodIndex) {
            this(periodIndex, -1, -1);
        }

        public MediaPeriodId(int periodIndex, int adGroupIndex, int adIndexInAdGroup) {
            this.periodIndex = periodIndex;
            this.adGroupIndex = adGroupIndex;
            this.adIndexInAdGroup = adIndexInAdGroup;
        }

        public MediaPeriodId copyWithPeriodIndex(int newPeriodIndex) {
            return this.periodIndex == newPeriodIndex ? this : new MediaPeriodId(newPeriodIndex, this.adGroupIndex, this.adIndexInAdGroup);
        }

        public boolean isAd() {
            return this.adGroupIndex != -1;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            MediaPeriodId periodId = (MediaPeriodId) obj;
            if (this.periodIndex == periodId.periodIndex && this.adGroupIndex == periodId.adGroupIndex && this.adIndexInAdGroup == periodId.adIndexInAdGroup) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((((this.periodIndex + 527) * 31) + this.adGroupIndex) * 31) + this.adIndexInAdGroup;
        }
    }

    MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator);

    void maybeThrowSourceInfoRefreshError() throws IOException;

    void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener);

    void releasePeriod(MediaPeriod mediaPeriod);

    void releaseSource();
}
