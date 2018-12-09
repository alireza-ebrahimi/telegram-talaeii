package org.telegram.messenger.exoplayer2.decoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SimpleOutputBuffer extends OutputBuffer {
    public ByteBuffer data;
    private final SimpleDecoder<?, SimpleOutputBuffer, ?> owner;

    public SimpleOutputBuffer(SimpleDecoder<?, SimpleOutputBuffer, ?> simpleDecoder) {
        this.owner = simpleDecoder;
    }

    public void clear() {
        super.clear();
        if (this.data != null) {
            this.data.clear();
        }
    }

    public ByteBuffer init(long j, int i) {
        this.timeUs = j;
        if (this.data == null || this.data.capacity() < i) {
            this.data = ByteBuffer.allocateDirect(i).order(ByteOrder.nativeOrder());
        }
        this.data.position(0);
        this.data.limit(i);
        return this.data;
    }

    public void release() {
        this.owner.releaseOutputBuffer(this);
    }
}
