package org.telegram.messenger.exoplayer2.decoder;

public interface Decoder<I, O, E extends Exception> {
    I dequeueInputBuffer();

    O dequeueOutputBuffer();

    void flush();

    String getName();

    void queueInputBuffer(I i);

    void release();
}
