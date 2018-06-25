package org.telegram.messenger.exoplayer2.text.cea;

import org.telegram.messenger.exoplayer2.text.SubtitleOutputBuffer;

public final class CeaOutputBuffer extends SubtitleOutputBuffer {
    private final CeaDecoder owner;

    public CeaOutputBuffer(CeaDecoder ceaDecoder) {
        this.owner = ceaDecoder;
    }

    public final void release() {
        this.owner.releaseOutputBuffer(this);
    }
}
