package org.telegram.messenger.audioinfo.m4a;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import org.telegram.messenger.audioinfo.util.RangeInputStream;

public class MP4Atom extends MP4Box<RangeInputStream> {
    public MP4Atom(RangeInputStream input, MP4Box<?> parent, String type) {
        super(input, parent, type);
    }

    public long getLength() {
        return ((RangeInputStream) getInput()).getRemainingLength() + ((RangeInputStream) getInput()).getPosition();
    }

    public long getOffset() {
        return getParent().getPosition() - getPosition();
    }

    public long getRemaining() {
        return ((RangeInputStream) getInput()).getRemainingLength();
    }

    public boolean hasMoreChildren() {
        return (getChild() != null ? getChild().getRemaining() : 0) < getRemaining();
    }

    public MP4Atom nextChildUpTo(String expectedTypeExpression) throws IOException {
        while (getRemaining() > 0) {
            MP4Atom atom = nextChild();
            if (atom.getType().matches(expectedTypeExpression)) {
                return atom;
            }
        }
        throw new IOException("atom type mismatch, not found: " + expectedTypeExpression);
    }

    public boolean readBoolean() throws IOException {
        return this.data.readBoolean();
    }

    public byte readByte() throws IOException {
        return this.data.readByte();
    }

    public short readShort() throws IOException {
        return this.data.readShort();
    }

    public int readInt() throws IOException {
        return this.data.readInt();
    }

    public long readLong() throws IOException {
        return this.data.readLong();
    }

    public byte[] readBytes(int len) throws IOException {
        byte[] bytes = new byte[len];
        this.data.readFully(bytes);
        return bytes;
    }

    public byte[] readBytes() throws IOException {
        return readBytes((int) getRemaining());
    }

    public BigDecimal readShortFixedPoint() throws IOException {
        return new BigDecimal(String.valueOf(this.data.readByte()) + "" + String.valueOf(this.data.readUnsignedByte()));
    }

    public BigDecimal readIntegerFixedPoint() throws IOException {
        return new BigDecimal(String.valueOf(this.data.readShort()) + "" + String.valueOf(this.data.readUnsignedShort()));
    }

    public String readString(int len, String enc) throws IOException {
        String s = new String(readBytes(len), enc);
        int end = s.indexOf(0);
        return end < 0 ? s : s.substring(0, end);
    }

    public String readString(String enc) throws IOException {
        return readString((int) getRemaining(), enc);
    }

    public void skip(int len) throws IOException {
        int total = 0;
        while (total < len) {
            int current = this.data.skipBytes(len - total);
            if (current > 0) {
                total += current;
            } else {
                throw new EOFException();
            }
        }
    }

    public void skip() throws IOException {
        while (getRemaining() > 0) {
            if (((RangeInputStream) getInput()).skip(getRemaining()) == 0) {
                throw new EOFException("Cannot skip atom");
            }
        }
    }

    private StringBuffer appendPath(StringBuffer s, MP4Box<?> box) {
        if (box.getParent() != null) {
            appendPath(s, box.getParent());
            s.append("/");
        }
        return s.append(box.getType());
    }

    public String getPath() {
        return appendPath(new StringBuffer(), this).toString();
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        appendPath(s, this);
        s.append("[off=");
        s.append(getOffset());
        s.append(",pos=");
        s.append(getPosition());
        s.append(",len=");
        s.append(getLength());
        s.append("]");
        return s.toString();
    }
}
