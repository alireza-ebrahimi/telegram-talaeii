package org.telegram.messenger.audioinfo.mp3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import org.telegram.messenger.audioinfo.util.RangeInputStream;

public class ID3v2TagBody {
    private final ID3v2DataInput data = new ID3v2DataInput(this.input);
    private final RangeInputStream input;
    private final ID3v2TagHeader tagHeader;

    ID3v2TagBody(InputStream delegate, long position, int length, ID3v2TagHeader tagHeader) throws IOException {
        this.input = new RangeInputStream(delegate, position, (long) length);
        this.tagHeader = tagHeader;
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

    public ID3v2FrameBody frameBody(ID3v2FrameHeader frameHeader) throws IOException, ID3v2Exception {
        int dataLength = frameHeader.getBodySize();
        InputStream inputStream = this.input;
        if (frameHeader.isUnsynchronization()) {
            byte[] bytes = this.data.readFully(frameHeader.getBodySize());
            boolean ff = false;
            int length = bytes.length;
            int i = 0;
            int len = 0;
            while (i < length) {
                int len2;
                byte b = bytes[i];
                if (ff && b == (byte) 0) {
                    len2 = len;
                } else {
                    len2 = len + 1;
                    bytes[len] = b;
                }
                if (b == (byte) -1) {
                    ff = true;
                } else {
                    ff = false;
                }
                i++;
                len = len2;
            }
            dataLength = len;
            inputStream = new ByteArrayInputStream(bytes, 0, len);
        }
        if (frameHeader.isEncryption()) {
            throw new ID3v2Exception("Frame encryption is not supported");
        }
        if (frameHeader.isCompression()) {
            dataLength = frameHeader.getDataLengthIndicator();
            inputStream = new InflaterInputStream(inputStream);
        }
        return new ID3v2FrameBody(inputStream, (long) frameHeader.getHeaderSize(), dataLength, this.tagHeader, frameHeader);
    }

    public String toString() {
        return "id3v2tag[pos=" + getPosition() + ", " + getRemainingLength() + " left]";
    }
}
