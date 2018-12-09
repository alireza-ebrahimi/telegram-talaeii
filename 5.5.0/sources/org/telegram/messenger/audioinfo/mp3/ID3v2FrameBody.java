package org.telegram.messenger.audioinfo.mp3;

import java.io.InputStream;
import org.telegram.messenger.audioinfo.util.RangeInputStream;

public class ID3v2FrameBody {
    static final ThreadLocal<Buffer> textBuffer = new C34211();
    private final ID3v2DataInput data = new ID3v2DataInput(this.input);
    private final ID3v2FrameHeader frameHeader;
    private final RangeInputStream input;
    private final ID3v2TagHeader tagHeader;

    /* renamed from: org.telegram.messenger.audioinfo.mp3.ID3v2FrameBody$1 */
    static class C34211 extends ThreadLocal<Buffer> {
        C34211() {
        }

        protected Buffer initialValue() {
            return new Buffer(4096);
        }
    }

    static final class Buffer {
        byte[] bytes;

        Buffer(int i) {
            this.bytes = new byte[i];
        }

        byte[] bytes(int i) {
            if (i > this.bytes.length) {
                int length = this.bytes.length * 2;
                while (i > length) {
                    length *= 2;
                }
                this.bytes = new byte[length];
            }
            return this.bytes;
        }
    }

    ID3v2FrameBody(InputStream inputStream, long j, int i, ID3v2TagHeader iD3v2TagHeader, ID3v2FrameHeader iD3v2FrameHeader) {
        this.input = new RangeInputStream(inputStream, j, (long) i);
        this.tagHeader = iD3v2TagHeader;
        this.frameHeader = iD3v2FrameHeader;
    }

    private String extractString(byte[] bArr, int i, int i2, ID3v2Encoding iD3v2Encoding, boolean z) {
        if (z) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < i2) {
                if (bArr[i + i3] != (byte) 0 || (iD3v2Encoding == ID3v2Encoding.UTF_16 && i4 == 0 && (i + i3) % 2 != 0)) {
                    i4 = 0;
                } else {
                    i4++;
                    if (i4 == iD3v2Encoding.getZeroBytes()) {
                        i2 = (i3 + 1) - iD3v2Encoding.getZeroBytes();
                        break;
                    }
                }
                i3++;
            }
        }
        try {
            String str = new String(bArr, i, i2, iD3v2Encoding.getCharset().name());
            return (str.length() <= 0 || str.charAt(0) != '﻿') ? str : str.substring(1);
        } catch (Exception e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    public ID3v2DataInput getData() {
        return this.data;
    }

    public ID3v2FrameHeader getFrameHeader() {
        return this.frameHeader;
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

    public ID3v2Encoding readEncoding() {
        byte readByte = this.data.readByte();
        switch (readByte) {
            case (byte) 0:
                return ID3v2Encoding.ISO_8859_1;
            case (byte) 1:
                return ID3v2Encoding.UTF_16;
            case (byte) 2:
                return ID3v2Encoding.UTF_16BE;
            case (byte) 3:
                return ID3v2Encoding.UTF_8;
            default:
                throw new ID3v2Exception("Invalid encoding: " + readByte);
        }
    }

    public String readFixedLengthString(int i, ID3v2Encoding iD3v2Encoding) {
        if (((long) i) > getRemainingLength()) {
            throw new ID3v2Exception("Could not read fixed-length string of length: " + i);
        }
        byte[] bytes = ((Buffer) textBuffer.get()).bytes(i);
        this.data.readFully(bytes, 0, i);
        return extractString(bytes, 0, i, iD3v2Encoding, true);
    }

    public String readZeroTerminatedString(int i, ID3v2Encoding iD3v2Encoding) {
        int min = Math.min(i, (int) getRemainingLength());
        byte[] bytes = ((Buffer) textBuffer.get()).bytes(min);
        int i2 = 0;
        int i3 = 0;
        while (i2 < min) {
            byte readByte = this.data.readByte();
            bytes[i2] = readByte;
            if (readByte != (byte) 0 || (iD3v2Encoding == ID3v2Encoding.UTF_16 && i3 == 0 && i2 % 2 != 0)) {
                i3 = 0;
            } else {
                i3++;
                if (i3 == iD3v2Encoding.getZeroBytes()) {
                    return extractString(bytes, 0, (i2 + 1) - iD3v2Encoding.getZeroBytes(), iD3v2Encoding, false);
                }
            }
            i2++;
        }
        throw new ID3v2Exception("Could not read zero-termiated string");
    }

    public String toString() {
        return "id3v2frame[pos=" + getPosition() + ", " + getRemainingLength() + " left]";
    }
}
