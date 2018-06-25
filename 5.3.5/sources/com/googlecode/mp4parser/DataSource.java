package com.googlecode.mp4parser;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public interface DataSource extends Closeable {
    void close() throws IOException;

    ByteBuffer map(long j, long j2) throws IOException;

    long position() throws IOException;

    void position(long j) throws IOException;

    int read(ByteBuffer byteBuffer) throws IOException;

    long size() throws IOException;

    long transferTo(long j, long j2, WritableByteChannel writableByteChannel) throws IOException;
}
