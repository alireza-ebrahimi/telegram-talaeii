package org.telegram.customization.compression.util;

import android.support.v4.internal.view.SupportMenu;
import java.nio.ByteOrder;

public enum SafeUtils {
    ;

    public static void checkRange(byte[] buf, int off) {
        if (off < 0 || off >= buf.length) {
            throw new ArrayIndexOutOfBoundsException(off);
        }
    }

    public static void checkRange(byte[] buf, int off, int len) {
        checkLength(len);
        if (len > 0) {
            checkRange(buf, off);
            checkRange(buf, (off + len) - 1);
        }
    }

    public static void checkLength(int len) {
        if (len < 0) {
            throw new IllegalArgumentException("lengths must be >= 0");
        }
    }

    public static byte readByte(byte[] buf, int i) {
        return buf[i];
    }

    public static int readIntBE(byte[] buf, int i) {
        return ((((buf[i] & 255) << 24) | ((buf[i + 1] & 255) << 16)) | ((buf[i + 2] & 255) << 8)) | (buf[i + 3] & 255);
    }

    public static int readIntLE(byte[] buf, int i) {
        return (((buf[i] & 255) | ((buf[i + 1] & 255) << 8)) | ((buf[i + 2] & 255) << 16)) | ((buf[i + 3] & 255) << 24);
    }

    public static int readInt(byte[] buf, int i) {
        if (Utils.NATIVE_BYTE_ORDER == ByteOrder.BIG_ENDIAN) {
            return readIntBE(buf, i);
        }
        return readIntLE(buf, i);
    }

    public static long readLongLE(byte[] buf, int i) {
        return (((((((((long) buf[i]) & 255) | ((((long) buf[i + 1]) & 255) << 8)) | ((((long) buf[i + 2]) & 255) << 16)) | ((((long) buf[i + 3]) & 255) << 24)) | ((((long) buf[i + 4]) & 255) << 32)) | ((((long) buf[i + 5]) & 255) << 40)) | ((((long) buf[i + 6]) & 255) << 48)) | ((((long) buf[i + 7]) & 255) << 56);
    }

    public static void writeShortLE(byte[] buf, int off, int v) {
        int i = off + 1;
        buf[off] = (byte) v;
        off = i + 1;
        buf[i] = (byte) (v >>> 8);
    }

    public static void writeInt(int[] buf, int off, int v) {
        buf[off] = v;
    }

    public static int readInt(int[] buf, int off) {
        return buf[off];
    }

    public static void writeByte(byte[] dest, int off, int i) {
        dest[off] = (byte) i;
    }

    public static void writeShort(short[] buf, int off, int v) {
        buf[off] = (short) v;
    }

    public static int readShortLE(byte[] buf, int i) {
        return (buf[i] & 255) | ((buf[i + 1] & 255) << 8);
    }

    public static int readShort(short[] buf, int off) {
        return buf[off] & SupportMenu.USER_MASK;
    }
}
