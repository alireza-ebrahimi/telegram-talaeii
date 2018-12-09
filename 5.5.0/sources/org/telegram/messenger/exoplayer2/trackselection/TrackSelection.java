package org.telegram.messenger.exoplayer2.trackselection;

import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.chunk.MediaChunk;

public interface TrackSelection {

    public interface Factory {
        TrackSelection createTrackSelection(TrackGroup trackGroup, int... iArr);
    }

    boolean blacklist(int i, long j);

    int evaluateQueueSize(long j, List<? extends MediaChunk> list);

    Format getFormat(int i);

    int getIndexInTrackGroup(int i);

    Format getSelectedFormat();

    int getSelectedIndex();

    int getSelectedIndexInTrackGroup();

    Object getSelectionData();

    int getSelectionReason();

    TrackGroup getTrackGroup();

    int indexOf(int i);

    int indexOf(Format format);

    int length();

    void updateSelectedTrack(long j);
}
