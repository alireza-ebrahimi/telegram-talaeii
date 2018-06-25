package org.telegram.messenger.exoplayer2.source.dash;

import org.telegram.messenger.exoplayer2.source.chunk.ChunkSource;
import org.telegram.messenger.exoplayer2.source.dash.manifest.DashManifest;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower;

public interface DashChunkSource extends ChunkSource {

    public interface Factory {
        DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, int i, int[] iArr, TrackSelection trackSelection, int i2, long j, boolean z, boolean z2);
    }

    void updateManifest(DashManifest dashManifest, int i);
}
