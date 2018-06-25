package org.telegram.messenger.exoplayer2.extractor.mp4;

import org.telegram.messenger.exoplayer2.Format;

final class AtomParsers$StsdData {
    public static final int STSD_HEADER_SIZE = 8;
    public Format format;
    public int nalUnitLengthFieldLength;
    public int requiredSampleTransformation = 0;
    public final TrackEncryptionBox[] trackEncryptionBoxes;

    public AtomParsers$StsdData(int numberOfEntries) {
        this.trackEncryptionBoxes = new TrackEncryptionBox[numberOfEntries];
    }
}
