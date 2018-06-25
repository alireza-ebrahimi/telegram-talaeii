package com.coremedia.iso.boxes.mdat;

import com.coremedia.iso.BoxParser;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.DataSource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Logger;

public final class MediaDataBox implements Box {
    private static Logger LOG = Logger.getLogger(MediaDataBox.class.getName());
    public static final String TYPE = "mdat";
    private DataSource dataSource;
    boolean largeBox = false;
    private long offset;
    Container parent;
    private long size;

    public Container getParent() {
        return this.parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public String getType() {
        return TYPE;
    }

    private static void transfer(DataSource from, long position, long count, WritableByteChannel to) throws IOException {
        long offset = 0;
        while (offset < count) {
            offset += from.transferTo(position + offset, Math.min(67076096, count - offset), to);
        }
    }

    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        transfer(this.dataSource, this.offset, this.size, writableByteChannel);
    }

    public long getSize() {
        return this.size;
    }

    public long getOffset() {
        return this.offset;
    }

    public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        this.offset = dataSource.position() - ((long) header.remaining());
        this.dataSource = dataSource;
        this.size = ((long) header.remaining()) + contentSize;
        dataSource.position(dataSource.position() + contentSize);
    }

    public String toString() {
        return "MediaDataBox{size=" + this.size + '}';
    }
}
