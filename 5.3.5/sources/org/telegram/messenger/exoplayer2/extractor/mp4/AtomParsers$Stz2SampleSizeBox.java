package org.telegram.messenger.exoplayer2.extractor.mp4;

import org.telegram.messenger.exoplayer2.extractor.mp4.Atom.LeafAtom;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class AtomParsers$Stz2SampleSizeBox implements AtomParsers$SampleSizeBox {
    private int currentByte;
    private final ParsableByteArray data;
    private final int fieldSize = (this.data.readUnsignedIntToInt() & 255);
    private final int sampleCount = this.data.readUnsignedIntToInt();
    private int sampleIndex;

    public AtomParsers$Stz2SampleSizeBox(LeafAtom stz2Atom) {
        this.data = stz2Atom.data;
        this.data.setPosition(12);
    }

    public int getSampleCount() {
        return this.sampleCount;
    }

    public int readNextSampleSize() {
        if (this.fieldSize == 8) {
            return this.data.readUnsignedByte();
        }
        if (this.fieldSize == 16) {
            return this.data.readUnsignedShort();
        }
        int i = this.sampleIndex;
        this.sampleIndex = i + 1;
        if (i % 2 != 0) {
            return this.currentByte & 15;
        }
        this.currentByte = this.data.readUnsignedByte();
        return (this.currentByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
    }

    public boolean isFixedSampleSize() {
        return false;
    }
}
