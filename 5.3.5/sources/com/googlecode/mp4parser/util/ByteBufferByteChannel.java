package com.googlecode.mp4parser.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class ByteBufferByteChannel implements ByteChannel {
    ByteBuffer byteBuffer;

    public ByteBufferByteChannel(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public int read(ByteBuffer dst) throws IOException {
        int rem = dst.remaining();
        if (this.byteBuffer.remaining() <= 0) {
            return -1;
        }
        dst.put((ByteBuffer) this.byteBuffer.duplicate().limit(this.byteBuffer.position() + dst.remaining()));
        this.byteBuffer.position(this.byteBuffer.position() + rem);
        return rem;
    }

    public boolean isOpen() {
        return true;
    }

    public void close() throws IOException {
    }

    public int write(ByteBuffer src) throws IOException {
        int r = src.remaining();
        this.byteBuffer.put(src);
        return r;
    }
}
