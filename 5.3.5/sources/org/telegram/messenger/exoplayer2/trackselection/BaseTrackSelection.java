package org.telegram.messenger.exoplayer2.trackselection;

import android.os.SystemClock;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.chunk.MediaChunk;
import org.telegram.messenger.exoplayer2.util.Assertions;

public abstract class BaseTrackSelection implements TrackSelection {
    private final long[] blacklistUntilTimes;
    private final Format[] formats;
    protected final TrackGroup group;
    private int hashCode;
    protected final int length;
    protected final int[] tracks;

    private static final class DecreasingBandwidthComparator implements Comparator<Format> {
        private DecreasingBandwidthComparator() {
        }

        public int compare(Format a, Format b) {
            return b.bitrate - a.bitrate;
        }
    }

    public BaseTrackSelection(TrackGroup group, int... tracks) {
        int i;
        Assertions.checkState(tracks.length > 0);
        this.group = (TrackGroup) Assertions.checkNotNull(group);
        this.length = tracks.length;
        this.formats = new Format[this.length];
        for (i = 0; i < tracks.length; i++) {
            this.formats[i] = group.getFormat(tracks[i]);
        }
        Arrays.sort(this.formats, new DecreasingBandwidthComparator());
        this.tracks = new int[this.length];
        for (i = 0; i < this.length; i++) {
            this.tracks[i] = group.indexOf(this.formats[i]);
        }
        this.blacklistUntilTimes = new long[this.length];
    }

    public final TrackGroup getTrackGroup() {
        return this.group;
    }

    public final int length() {
        return this.tracks.length;
    }

    public final Format getFormat(int index) {
        return this.formats[index];
    }

    public final int getIndexInTrackGroup(int index) {
        return this.tracks[index];
    }

    public final int indexOf(Format format) {
        for (int i = 0; i < this.length; i++) {
            if (this.formats[i] == format) {
                return i;
            }
        }
        return -1;
    }

    public final int indexOf(int indexInTrackGroup) {
        for (int i = 0; i < this.length; i++) {
            if (this.tracks[i] == indexInTrackGroup) {
                return i;
            }
        }
        return -1;
    }

    public final Format getSelectedFormat() {
        return this.formats[getSelectedIndex()];
    }

    public final int getSelectedIndexInTrackGroup() {
        return this.tracks[getSelectedIndex()];
    }

    public int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        return queue.size();
    }

    public final boolean blacklist(int index, long blacklistDurationMs) {
        long nowMs = SystemClock.elapsedRealtime();
        boolean canBlacklist = isBlacklisted(index, nowMs);
        int i = 0;
        while (i < this.length && !canBlacklist) {
            if (i == index || isBlacklisted(i, nowMs)) {
                canBlacklist = false;
            } else {
                canBlacklist = true;
            }
            i++;
        }
        if (!canBlacklist) {
            return false;
        }
        this.blacklistUntilTimes[index] = Math.max(this.blacklistUntilTimes[index], nowMs + blacklistDurationMs);
        return true;
    }

    protected final boolean isBlacklisted(int index, long nowMs) {
        return this.blacklistUntilTimes[index] > nowMs;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (System.identityHashCode(this.group) * 31) + Arrays.hashCode(this.tracks);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseTrackSelection other = (BaseTrackSelection) obj;
        if (this.group == other.group && Arrays.equals(this.tracks, other.tracks)) {
            return true;
        }
        return false;
    }
}
