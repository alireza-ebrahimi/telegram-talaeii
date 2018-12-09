package org.telegram.messenger.audioinfo.m4a;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import org.telegram.messenger.audioinfo.util.RangeInputStream;

public class MP4Atom extends MP4Box<RangeInputStream> {
    public MP4Atom(RangeInputStream rangeInputStream, MP4Box<?> mP4Box, String str) {
        super(rangeInputStream, mP4Box, str);
    }

    private StringBuffer appendPath(StringBuffer stringBuffer, MP4Box<?> mP4Box) {
        if (mP4Box.getParent() != null) {
            appendPath(stringBuffer, mP4Box.getParent());
            stringBuffer.append("/");
        }
        return stringBuffer.append(mP4Box.getType());
    }

    public long getLength() {
        return ((RangeInputStream) getInput()).getRemainingLength() + ((RangeInputStream) getInput()).getPosition();
    }

    public long getOffset() {
        return getParent().getPosition() - getPosition();
    }

    public String getPath() {
        return appendPath(new StringBuffer(), this).toString();
    }

    public long getRemaining() {
        return ((RangeInputStream) getInput()).getRemainingLength();
    }

    public boolean hasMoreChildren() {
        return (getChild() != null ? getChild().getRemaining() : 0) < getRemaining();
    }

    public MP4Atom nextChildUpTo(String str) {
        while (getRemaining() > 0) {
            MP4Atom nextChild = nextChild();
            if (nextChild.getType().matches(str)) {
                return nextChild;
            }
        }
        throw new IOException("atom type mismatch, not found: " + str);
    }

    public boolean readBoolean() {
        return this.data.readBoolean();
    }

    public byte readByte() {
        return this.data.readByte();
    }

    public byte[] readBytes() {
        return readBytes((int) getRemaining());
    }

    public byte[] readBytes(int i) {
        byte[] bArr = new byte[i];
        this.data.readFully(bArr);
        return bArr;
    }

    public int readInt() {
        return this.data.readInt();
    }

    public BigDecimal readIntegerFixedPoint() {
        return new BigDecimal(String.valueOf(this.data.readShort()) + TtmlNode.ANONYMOUS_REGION_ID + String.valueOf(this.data.readUnsignedShort()));
    }

    public long readLong() {
        return this.data.readLong();
    }

    public short readShort() {
        return this.data.readShort();
    }

    public BigDecimal readShortFixedPoint() {
        return new BigDecimal(String.valueOf(this.data.readByte()) + TtmlNode.ANONYMOUS_REGION_ID + String.valueOf(this.data.readUnsignedByte()));
    }

    public String readString(int i, String str) {
        String str2 = new String(readBytes(i), str);
        int indexOf = str2.indexOf(0);
        return indexOf < 0 ? str2 : str2.substring(0, indexOf);
    }

    public String readString(String str) {
        return readString((int) getRemaining(), str);
    }

    public void skip() {
        while (getRemaining() > 0) {
            if (((RangeInputStream) getInput()).skip(getRemaining()) == 0) {
                throw new EOFException("Cannot skip atom");
            }
        }
    }

    public void skip(int i) {
        int i2 = 0;
        while (i2 < i) {
            int skipBytes = this.data.skipBytes(i - i2);
            if (skipBytes > 0) {
                i2 += skipBytes;
            } else {
                throw new EOFException();
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        appendPath(stringBuffer, this);
        stringBuffer.append("[off=");
        stringBuffer.append(getOffset());
        stringBuffer.append(",pos=");
        stringBuffer.append(getPosition());
        stringBuffer.append(",len=");
        stringBuffer.append(getLength());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
