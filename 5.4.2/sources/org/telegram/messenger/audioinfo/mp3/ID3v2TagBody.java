package org.telegram.messenger.audioinfo.mp3;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import org.telegram.messenger.audioinfo.util.RangeInputStream;

public class ID3v2TagBody {
    private final ID3v2DataInput data = new ID3v2DataInput(this.input);
    private final RangeInputStream input;
    private final ID3v2TagHeader tagHeader;

    ID3v2TagBody(InputStream inputStream, long j, int i, ID3v2TagHeader iD3v2TagHeader) {
        this.input = new RangeInputStream(inputStream, j, (long) i);
        this.tagHeader = iD3v2TagHeader;
    }

    public ID3v2FrameBody frameBody(ID3v2FrameHeader iD3v2FrameHeader) {
        int i;
        int bodySize = iD3v2FrameHeader.getBodySize();
        InputStream inputStream = this.input;
        if (iD3v2FrameHeader.isUnsynchronization()) {
            byte[] readFully = this.data.readFully(iD3v2FrameHeader.getBodySize());
            bodySize = 0;
            int i2 = 0;
            for (byte b : readFully) {
                if (i2 == 0 || b != (byte) 0) {
                    i2 = bodySize + 1;
                    readFully[bodySize] = b;
                    bodySize = i2;
                }
                i2 = b == (byte) -1 ? 1 : 0;
            }
            inputStream = new ByteArrayInputStream(readFully, 0, bodySize);
            i = bodySize;
        } else {
            i = bodySize;
        }
        if (iD3v2FrameHeader.isEncryption()) {
            throw new ID3v2Exception("Frame encryption is not supported");
        }
        int dataLengthIndicator;
        InputStream inflaterInputStream;
        if (iD3v2FrameHeader.isCompression()) {
            dataLengthIndicator = iD3v2FrameHeader.getDataLengthIndicator();
            inflaterInputStream = new InflaterInputStream(inputStream);
        } else {
            dataLengthIndicator = i;
            inflaterInputStream = inputStream;
        }
        return new ID3v2FrameBody(inflaterInputStream, (long) iD3v2FrameHeader.getHeaderSize(), dataLengthIndicator, this.tagHeader, iD3v2FrameHeader);
    }

    public ID3v2DataInput getData() {
        return this.data;
    }

    public long getPosition() {
        return this.input.getPosition();
    }

    public long getRemainingLength() {
        return this.input.getRemainingLength();
    }

    public ID3v2TagHeader getTagHeader() {
        return this.tagHeader;
    }

    public String toString() {
        return "id3v2tag[pos=" + getPosition() + ", " + getRemainingLength() + " left]";
    }
}
