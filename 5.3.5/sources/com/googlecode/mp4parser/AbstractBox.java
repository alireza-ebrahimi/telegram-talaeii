package com.googlecode.mp4parser;

import com.coremedia.iso.BoxParser;
import com.coremedia.iso.Hex;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.UserBox;
import com.googlecode.mp4parser.annotations.DoNotParseDetail;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Logger;
import com.googlecode.mp4parser.util.Path;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public abstract class AbstractBox implements Box {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractBox.class.desiredAssertionStatus());
    private static Logger LOG = Logger.getLogger(AbstractBox.class);
    private ByteBuffer content;
    long contentStartPosition;
    DataSource dataSource;
    private ByteBuffer deadBytes = null;
    boolean isParsed;
    boolean isRead;
    long memMapSize = -1;
    long offset;
    private Container parent;
    protected String type;
    private byte[] userType;

    protected abstract void _parseDetails(ByteBuffer byteBuffer);

    protected abstract void getContent(ByteBuffer byteBuffer);

    protected abstract long getContentSize();

    private synchronized void readContent() {
        if (!this.isRead) {
            try {
                LOG.logDebug("mem mapping " + getType());
                this.content = this.dataSource.map(this.contentStartPosition, this.memMapSize);
                this.isRead = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public long getOffset() {
        return this.offset;
    }

    protected AbstractBox(String type) {
        this.type = type;
        this.isRead = true;
        this.isParsed = true;
    }

    protected AbstractBox(String type, byte[] userType) {
        this.type = type;
        this.userType = userType;
        this.isRead = true;
        this.isParsed = true;
    }

    @DoNotParseDetail
    public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        this.contentStartPosition = dataSource.position();
        this.offset = this.contentStartPosition - ((long) header.remaining());
        this.memMapSize = contentSize;
        this.dataSource = dataSource;
        dataSource.position(dataSource.position() + contentSize);
        this.isRead = false;
        this.isParsed = false;
    }

    public void getBox(WritableByteChannel os) throws IOException {
        int i = 8;
        int i2 = 16;
        ByteBuffer header;
        if (!this.isRead) {
            if (!isSmallBox()) {
                i = 16;
            }
            if (!UserBox.TYPE.equals(getType())) {
                i2 = 0;
            }
            header = ByteBuffer.allocate(i + i2);
            getHeader(header);
            os.write((ByteBuffer) header.rewind());
            this.dataSource.transferTo(this.contentStartPosition, this.memMapSize, os);
        } else if (this.isParsed) {
            ByteBuffer bb = ByteBuffer.allocate(CastUtils.l2i(getSize()));
            getHeader(bb);
            getContent(bb);
            if (this.deadBytes != null) {
                this.deadBytes.rewind();
                while (this.deadBytes.remaining() > 0) {
                    bb.put(this.deadBytes);
                }
            }
            os.write((ByteBuffer) bb.rewind());
        } else {
            if (!isSmallBox()) {
                i = 16;
            }
            if (!UserBox.TYPE.equals(getType())) {
                i2 = 0;
            }
            header = ByteBuffer.allocate(i + i2);
            getHeader(header);
            os.write((ByteBuffer) header.rewind());
            os.write((ByteBuffer) this.content.position(0));
        }
    }

    public final synchronized void parseDetails() {
        readContent();
        LOG.logDebug("parsing details of " + getType());
        if (this.content != null) {
            ByteBuffer content = this.content;
            this.isParsed = true;
            content.rewind();
            _parseDetails(content);
            if (content.remaining() > 0) {
                this.deadBytes = content.slice();
            }
            this.content = null;
            if (!($assertionsDisabled || verify(content))) {
                throw new AssertionError();
            }
        }
    }

    protected void setDeadBytes(ByteBuffer newDeadBytes) {
        this.deadBytes = newDeadBytes;
    }

    public long getSize() {
        long size;
        int i;
        int i2 = 0;
        if (!this.isRead) {
            size = this.memMapSize;
        } else if (this.isParsed) {
            size = getContentSize();
        } else {
            size = (long) (this.content != null ? this.content.limit() : 0);
        }
        if (size >= 4294967288L) {
            i = 8;
        } else {
            i = 0;
        }
        int i3 = i + 8;
        if (UserBox.TYPE.equals(getType())) {
            i = 16;
        } else {
            i = 0;
        }
        size += (long) (i + i3);
        if (this.deadBytes != null) {
            i2 = this.deadBytes.limit();
        }
        return size + ((long) i2);
    }

    @DoNotParseDetail
    public String getType() {
        return this.type;
    }

    @DoNotParseDetail
    public byte[] getUserType() {
        return this.userType;
    }

    @DoNotParseDetail
    public Container getParent() {
        return this.parent;
    }

    @DoNotParseDetail
    public void setParent(Container parent) {
        this.parent = parent;
    }

    public boolean isParsed() {
        return this.isParsed;
    }

    private boolean verify(ByteBuffer content) {
        ByteBuffer bb = ByteBuffer.allocate(CastUtils.l2i(((long) (this.deadBytes != null ? this.deadBytes.limit() : 0)) + getContentSize()));
        getContent(bb);
        if (this.deadBytes != null) {
            this.deadBytes.rewind();
            while (this.deadBytes.remaining() > 0) {
                bb.put(this.deadBytes);
            }
        }
        content.rewind();
        bb.rewind();
        if (content.remaining() != bb.remaining()) {
            System.err.print(getType() + ": remaining differs " + content.remaining() + " vs. " + bb.remaining());
            LOG.logError(getType() + ": remaining differs " + content.remaining() + " vs. " + bb.remaining());
            return false;
        }
        int p = content.position();
        int i = content.limit() - 1;
        int j = bb.limit() - 1;
        while (i >= p) {
            if (content.get(i) != bb.get(j)) {
                LOG.logError(String.format("%s: buffers differ at %d: %2X/%2X", new Object[]{getType(), Integer.valueOf(i), Byte.valueOf(content.get(i)), Byte.valueOf(bb.get(j))}));
                byte[] b1 = new byte[content.remaining()];
                byte[] b2 = new byte[bb.remaining()];
                content.get(b1);
                bb.get(b2);
                System.err.println("original      : " + Hex.encodeHex(b1, 4));
                System.err.println("reconstructed : " + Hex.encodeHex(b2, 4));
                return false;
            }
            i--;
            j--;
        }
        return true;
    }

    private boolean isSmallBox() {
        int baseSize = 8;
        if (UserBox.TYPE.equals(getType())) {
            baseSize = 8 + 16;
        }
        if (this.isRead) {
            if (this.isParsed) {
                if ((getContentSize() + ((long) (this.deadBytes != null ? this.deadBytes.limit() : 0))) + ((long) baseSize) < 4294967296L) {
                    return true;
                }
                return false;
            } else if (((long) (this.content.limit() + baseSize)) >= 4294967296L) {
                return false;
            } else {
                return true;
            }
        } else if (this.memMapSize + ((long) baseSize) >= 4294967296L) {
            return false;
        } else {
            return true;
        }
    }

    private void getHeader(ByteBuffer byteBuffer) {
        if (isSmallBox()) {
            IsoTypeWriter.writeUInt32(byteBuffer, getSize());
            byteBuffer.put(IsoFile.fourCCtoBytes(getType()));
        } else {
            IsoTypeWriter.writeUInt32(byteBuffer, 1);
            byteBuffer.put(IsoFile.fourCCtoBytes(getType()));
            IsoTypeWriter.writeUInt64(byteBuffer, getSize());
        }
        if (UserBox.TYPE.equals(getType())) {
            byteBuffer.put(getUserType());
        }
    }

    @DoNotParseDetail
    public String getPath() {
        return Path.createPath(this);
    }
}
