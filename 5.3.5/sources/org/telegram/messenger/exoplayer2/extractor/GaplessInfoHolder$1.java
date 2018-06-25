package org.telegram.messenger.exoplayer2.extractor;

import org.telegram.messenger.exoplayer2.metadata.id3.Id3Decoder.FramePredicate;

class GaplessInfoHolder$1 implements FramePredicate {
    GaplessInfoHolder$1() {
    }

    public boolean evaluate(int majorVersion, int id0, int id1, int id2, int id3) {
        return id0 == 67 && id1 == 79 && id2 == 77 && (id3 == 77 || majorVersion == 2);
    }
}
