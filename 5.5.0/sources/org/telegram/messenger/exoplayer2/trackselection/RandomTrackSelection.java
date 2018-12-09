package org.telegram.messenger.exoplayer2.trackselection;

import android.os.SystemClock;
import java.util.Random;
import org.telegram.messenger.exoplayer2.source.TrackGroup;

public final class RandomTrackSelection extends BaseTrackSelection {
    private final Random random;
    private int selectedIndex;

    public static final class Factory implements org.telegram.messenger.exoplayer2.trackselection.TrackSelection.Factory {
        private final Random random;

        public Factory() {
            this.random = new Random();
        }

        public Factory(int i) {
            this.random = new Random((long) i);
        }

        public RandomTrackSelection createTrackSelection(TrackGroup trackGroup, int... iArr) {
            return new RandomTrackSelection(trackGroup, iArr, this.random);
        }
    }

    public RandomTrackSelection(TrackGroup trackGroup, int... iArr) {
        super(trackGroup, iArr);
        this.random = new Random();
        this.selectedIndex = this.random.nextInt(this.length);
    }

    public RandomTrackSelection(TrackGroup trackGroup, int[] iArr, long j) {
        this(trackGroup, iArr, new Random(j));
    }

    public RandomTrackSelection(TrackGroup trackGroup, int[] iArr, Random random) {
        super(trackGroup, iArr);
        this.random = random;
        this.selectedIndex = random.nextInt(this.length);
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public Object getSelectionData() {
        return null;
    }

    public int getSelectionReason() {
        return 3;
    }

    public void updateSelectedTrack(long j) {
        int i;
        int i2 = 0;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i3 = 0;
        for (i = 0; i < this.length; i++) {
            if (!isBlacklisted(i, elapsedRealtime)) {
                i3++;
            }
        }
        this.selectedIndex = this.random.nextInt(i3);
        if (i3 != this.length) {
            i = 0;
            while (i2 < this.length) {
                if (!isBlacklisted(i2, elapsedRealtime)) {
                    i3 = i + 1;
                    if (this.selectedIndex == i) {
                        this.selectedIndex = i2;
                        return;
                    }
                    i = i3;
                }
                i2++;
            }
        }
    }
}
