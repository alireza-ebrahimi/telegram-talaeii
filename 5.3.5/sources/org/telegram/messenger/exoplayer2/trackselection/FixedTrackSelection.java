package org.telegram.messenger.exoplayer2.trackselection;

import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class FixedTrackSelection extends BaseTrackSelection {
    private final Object data;
    private final int reason;

    public static final class Factory implements org.telegram.messenger.exoplayer2.trackselection.TrackSelection.Factory {
        private final Object data;
        private final int reason;

        public Factory() {
            this.reason = 0;
            this.data = null;
        }

        public Factory(int reason, Object data) {
            this.reason = reason;
            this.data = data;
        }

        public FixedTrackSelection createTrackSelection(TrackGroup group, int... tracks) {
            boolean z = true;
            if (tracks.length != 1) {
                z = false;
            }
            Assertions.checkArgument(z);
            return new FixedTrackSelection(group, tracks[0], this.reason, this.data);
        }
    }

    public FixedTrackSelection(TrackGroup group, int track) {
        this(group, track, 0, null);
    }

    public FixedTrackSelection(TrackGroup group, int track, int reason, Object data) {
        super(group, track);
        this.reason = reason;
        this.data = data;
    }

    public void updateSelectedTrack(long bufferedDurationUs) {
    }

    public int getSelectedIndex() {
        return 0;
    }

    public int getSelectionReason() {
        return this.reason;
    }

    public Object getSelectionData() {
        return this.data;
    }
}
