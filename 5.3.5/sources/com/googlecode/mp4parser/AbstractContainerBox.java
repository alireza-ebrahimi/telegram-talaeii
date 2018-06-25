package com.googlecode.mp4parser;

import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class AbstractContainerBox extends BasicContainer implements Box {
    protected boolean largeBox;
    private long offset;
    Container parent;
    protected String type;

    public AbstractContainerBox(String type) {
        this.type = type;
    }

    public Container getParent() {
        return this.parent;
    }

    public long getOffset() {
        return this.offset;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public long getSize() {
        long s = getContainerSize();
        int i = (this.largeBox || 8 + s >= 4294967296L) ? 16 : 8;
        return ((long) i) + s;
    }

    public String getType() {
        return this.type;
    }

    protected ByteBuffer getHeader() {
        ByteBuffer header;
        byte[] bArr;
        if (this.largeBox || getSize() >= 4294967296L) {
            bArr = new byte[16];
            bArr[3] = (byte) 1;
            bArr[4] = this.type.getBytes()[0];
            bArr[5] = this.type.getBytes()[1];
            bArr[6] = this.type.getBytes()[2];
            bArr[7] = this.type.getBytes()[3];
            header = ByteBuffer.wrap(bArr);
            header.position(8);
            IsoTypeWriter.writeUInt64(header, getSize());
        } else {
            bArr = new byte[8];
            bArr[4] = this.type.getBytes()[0];
            bArr[5] = this.type.getBytes()[1];
            bArr[6] = this.type.getBytes()[2];
            bArr[7] = this.type.getBytes()[3];
            header = ByteBuffer.wrap(bArr);
            IsoTypeWriter.writeUInt32(header, getSize());
        }
        header.rewind();
        return header;
    }

    public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        this.offset = dataSource.position() - ((long) header.remaining());
        this.largeBox = header.remaining() == 16;
        initContainer(dataSource, contentSize, boxParser);
    }

    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        writableByteChannel.write(getHeader());
        writeContainer(writableByteChannel);
    }

    public void initContainer(DataSource dataSource, long containerSize, BoxParser boxParser) throws IOException {
        this.dataSource = dataSource;
        this.parsePosition = dataSource.position();
        long j = this.parsePosition;
        int i = (this.largeBox || 8 + containerSize >= 4294967296L) ? 16 : 8;
        this.startPosition = j - ((long) i);
        dataSource.position(dataSource.position() + containerSize);
        this.endPosition = dataSource.position();
        this.boxParser = boxParser;
    }
}
