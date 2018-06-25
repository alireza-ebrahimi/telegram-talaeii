package org.telegram.customization.compression.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public enum ByteBufferUtils {
    ;

    static {
        $assertionsDisabled = !ByteBufferUtils.class.desiredAssertionStatus();
        $VALUES = new ByteBufferUtils[0];
    }

    public static void checkRange(ByteBuffer buf, int off, int len) {
        SafeUtils.checkLength(len);
        if (len > 0) {
            checkRange(buf, off);
            checkRange(buf, (off + len) - 1);
        }
    }

    public static void checkRange(ByteBuffer buf, int off) {
        if (off < 0 || off >= buf.capacity()) {
            throw new ArrayIndexOutOfBoundsException(off);
        }
    }

    public static ByteBuffer inLittleEndianOrder(ByteBuffer buf) {
        return buf.order().equals(ByteOrder.LITTLE_ENDIAN) ? buf : buf.duplicate().order(ByteOrder.LITTLE_ENDIAN);
    }

    public static ByteBuffer inNativeByteOrder(ByteBuffer buf) {
        return buf.order().equals(Utils.NATIVE_BYTE_ORDER) ? buf : buf.duplicate().order(Utils.NATIVE_BYTE_ORDER);
    }

    public static byte readByte(ByteBuffer buf, int i) {
        return buf.get(i);
    }

    public static void writeInt(ByteBuffer buf, int i, int v) {
        if ($assertionsDisabled || buf.order() == Utils.NATIVE_BYTE_ORDER) {
            buf.putInt(i, v);
            return;
        }
        throw new AssertionError();
    }

    public static int readInt(ByteBuffer buf, int i) {
        if ($assertionsDisabled || buf.order() == Utils.NATIVE_BYTE_ORDER) {
            return buf.getInt(i);
        }
        throw new AssertionError();
    }

    public static int readIntLE(ByteBuffer buf, int i) {
        if ($assertionsDisabled || buf.order() == ByteOrder.LITTLE_ENDIAN) {
            return buf.getInt(i);
        }
        throw new AssertionError();
    }

    public static void writeLong(ByteBuffer buf, int i, long v) {
        if ($assertionsDisabled || buf.order() == Utils.NATIVE_BYTE_ORDER) {
            buf.putLong(i, v);
            return;
        }
        throw new AssertionError();
    }

    public static long readLong(ByteBuffer buf, int i) {
        if ($assertionsDisabled || buf.order() == Utils.NATIVE_BYTE_ORDER) {
            return buf.getLong(i);
        }
        throw new AssertionError();
    }

    public static long readLongLE(ByteBuffer buf, int i) {
        if ($assertionsDisabled || buf.order() == ByteOrder.LITTLE_ENDIAN) {
            return buf.getLong(i);
        }
        throw new AssertionError();
    }

    public static void writeByte(ByteBuffer dest, int off, int i) {
        dest.put(off, (byte) i);
    }

    public static void writeShortLE(ByteBuffer dest, int off, int i) {
        dest.put(off, (byte) i);
        dest.put(off + 1, (byte) (i >>> 8));
    }

    public static void checkNotReadOnly(ByteBuffer buffer) {
        if (buffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
    }

    public static int readShortLE(ByteBuffer buf, int i) {
        return (buf.get(i) & 255) | ((buf.get(i + 1) & 255) << 8);
    }
}
