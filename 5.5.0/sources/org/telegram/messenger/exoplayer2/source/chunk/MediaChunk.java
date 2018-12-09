package org.telegram.messenger.exoplayer2.source.chunk;

import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.Assertions;

public abstract class MediaChunk extends Chunk {
    public final int chunkIndex;

    public MediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, Object obj, long j, long j2, int i2) {
        super(dataSource, dataSpec, 1, format, i, obj, j, j2);
        Assertions.checkNotNull(format);
        this.chunkIndex = i2;
    }

    public int getNextChunkIndex() {
        return this.chunkIndex + 1;
    }

    public abstract boolean isLoadCompleted();
}
