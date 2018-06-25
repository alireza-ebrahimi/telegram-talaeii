package org.telegram.messenger.exoplayer2.text.cea;

import org.telegram.messenger.exoplayer2.text.SubtitleOutputBuffer;

public final class CeaOutputBuffer extends SubtitleOutputBuffer {
    private final CeaDecoder owner;

    public CeaOutputBuffer(CeaDecoder owner) {
        this.owner = owner;
    }

    public final void release() {
        this.owner.releaseOutputBuffer(this);
    }
}
