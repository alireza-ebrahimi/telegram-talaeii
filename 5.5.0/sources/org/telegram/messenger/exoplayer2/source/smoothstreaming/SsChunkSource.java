package org.telegram.messenger.exoplayer2.source.smoothstreaming;

import org.telegram.messenger.exoplayer2.extractor.mp4.TrackEncryptionBox;
import org.telegram.messenger.exoplayer2.source.chunk.ChunkSource;
import org.telegram.messenger.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower;

public interface SsChunkSource extends ChunkSource {

    public interface Factory {
        SsChunkSource createChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i, TrackSelection trackSelection, TrackEncryptionBox[] trackEncryptionBoxArr);
    }

    void updateManifest(SsManifest ssManifest);
}
