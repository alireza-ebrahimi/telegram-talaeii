package org.telegram.messenger;

import java.nio.ByteBuffer;

class MediaController$AudioBuffer {
    ByteBuffer buffer;
    byte[] bufferBytes;
    int finished;
    long pcmOffset;
    int size;
    final /* synthetic */ MediaController this$0;

    public MediaController$AudioBuffer(MediaController mediaController, int capacity) {
        this.this$0 = mediaController;
        this.buffer = ByteBuffer.allocateDirect(capacity);
        this.bufferBytes = new byte[capacity];
    }
}
