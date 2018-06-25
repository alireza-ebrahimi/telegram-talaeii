package com.googlecode.mp4parser;

import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class MemoryDataSourceImpl implements DataSource {
    ByteBuffer data;

    public MemoryDataSourceImpl(byte[] data) {
        this.data = ByteBuffer.wrap(data);
    }

    public MemoryDataSourceImpl(ByteBuffer buffer) {
        this.data = buffer;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        if (this.data.remaining() == 0 && byteBuffer.remaining() != 0) {
            return -1;
        }
        int size = Math.min(byteBuffer.remaining(), this.data.remaining());
        if (byteBuffer.hasArray()) {
            byteBuffer.put(this.data.array(), this.data.position(), size);
            this.data.position(this.data.position() + size);
            return size;
        }
        byte[] buf = new byte[size];
        this.data.get(buf);
        byteBuffer.put(buf);
        return size;
    }

    public long size() throws IOException {
        return (long) this.data.capacity();
    }

    public long position() throws IOException {
        return (long) this.data.position();
    }

    public void position(long nuPos) throws IOException {
        this.data.position(CastUtils.l2i(nuPos));
    }

    public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
        return (long) target.write((ByteBuffer) ((ByteBuffer) this.data.position(CastUtils.l2i(position))).slice().limit(CastUtils.l2i(count)));
    }

    public ByteBuffer map(long startPosition, long size) throws IOException {
        int oldPosition = this.data.position();
        this.data.position(CastUtils.l2i(startPosition));
        ByteBuffer result = this.data.slice();
        result.limit(CastUtils.l2i(size));
        this.data.position(oldPosition);
        return result;
    }

    public void close() throws IOException {
    }
}
