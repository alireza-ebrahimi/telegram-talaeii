package org.telegram.messenger.exoplayer2.extractor.mp4;

import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class AtomParsers$ChunkIterator {
    private final ParsableByteArray chunkOffsets;
    private final boolean chunkOffsetsAreLongs;
    public int index;
    public final int length;
    private int nextSamplesPerChunkChangeIndex;
    public int numSamples;
    public long offset;
    private int remainingSamplesPerChunkChanges;
    private final ParsableByteArray stsc;

    public AtomParsers$ChunkIterator(ParsableByteArray stsc, ParsableByteArray chunkOffsets, boolean chunkOffsetsAreLongs) {
        boolean z = true;
        this.stsc = stsc;
        this.chunkOffsets = chunkOffsets;
        this.chunkOffsetsAreLongs = chunkOffsetsAreLongs;
        chunkOffsets.setPosition(12);
        this.length = chunkOffsets.readUnsignedIntToInt();
        stsc.setPosition(12);
        this.remainingSamplesPerChunkChanges = stsc.readUnsignedIntToInt();
        if (stsc.readInt() != 1) {
            z = false;
        }
        Assertions.checkState(z, "first_chunk must be 1");
        this.index = -1;
    }

    public boolean moveNext() {
        int i = this.index + 1;
        this.index = i;
        if (i == this.length) {
            return false;
        }
        long readUnsignedLongToLong;
        if (this.chunkOffsetsAreLongs) {
            readUnsignedLongToLong = this.chunkOffsets.readUnsignedLongToLong();
        } else {
            readUnsignedLongToLong = this.chunkOffsets.readUnsignedInt();
        }
        this.offset = readUnsignedLongToLong;
        if (this.index == this.nextSamplesPerChunkChangeIndex) {
            this.numSamples = this.stsc.readUnsignedIntToInt();
            this.stsc.skipBytes(4);
            i = this.remainingSamplesPerChunkChanges - 1;
            this.remainingSamplesPerChunkChanges = i;
            this.nextSamplesPerChunkChangeIndex = i > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
        }
        return true;
    }
}
