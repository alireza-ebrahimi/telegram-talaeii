package org.telegram.messenger.exoplayer2.extractor.mp4;

import org.telegram.messenger.exoplayer2.extractor.mp4.Atom.LeafAtom;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class AtomParsers$StszSampleSizeBox implements AtomParsers$SampleSizeBox {
    private final ParsableByteArray data;
    private final int fixedSampleSize = this.data.readUnsignedIntToInt();
    private final int sampleCount = this.data.readUnsignedIntToInt();

    public AtomParsers$StszSampleSizeBox(LeafAtom stszAtom) {
        this.data = stszAtom.data;
        this.data.setPosition(12);
    }

    public int getSampleCount() {
        return this.sampleCount;
    }

    public int readNextSampleSize() {
        return this.fixedSampleSize == 0 ? this.data.readUnsignedIntToInt() : this.fixedSampleSize;
    }

    public boolean isFixedSampleSize() {
        return this.fixedSampleSize != 0;
    }
}
