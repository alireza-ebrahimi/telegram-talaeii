package org.telegram.customization.compression.p160a;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

/* renamed from: org.telegram.customization.compression.a.a */
public enum C2672a {
    ;

    static {
        f8909a = !C2672a.class.desiredAssertionStatus();
        f8910b = new C2672a[0];
    }

    /* renamed from: a */
    public static ByteBuffer m12560a(ByteBuffer byteBuffer) {
        return byteBuffer.order().equals(C2676d.f8921a) ? byteBuffer : byteBuffer.duplicate().order(C2676d.f8921a);
    }

    /* renamed from: a */
    public static void m12561a(ByteBuffer byteBuffer, int i) {
        if (i < 0 || i >= byteBuffer.capacity()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
    }

    /* renamed from: a */
    public static void m12562a(ByteBuffer byteBuffer, int i, int i2) {
        C2675c.m12579a(i2);
        if (i2 > 0) {
            C2672a.m12561a(byteBuffer, i);
            C2672a.m12561a(byteBuffer, (i + i2) - 1);
        }
    }

    /* renamed from: a */
    public static void m12563a(ByteBuffer byteBuffer, int i, long j) {
        if (f8909a || byteBuffer.order() == C2676d.f8921a) {
            byteBuffer.putLong(i, j);
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: b */
    public static byte m12564b(ByteBuffer byteBuffer, int i) {
        return byteBuffer.get(i);
    }

    /* renamed from: b */
    public static void m12565b(ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
    }

    /* renamed from: b */
    public static void m12566b(ByteBuffer byteBuffer, int i, int i2) {
        if (f8909a || byteBuffer.order() == C2676d.f8921a) {
            byteBuffer.putInt(i, i2);
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: c */
    public static int m12567c(ByteBuffer byteBuffer, int i) {
        if (f8909a || byteBuffer.order() == C2676d.f8921a) {
            return byteBuffer.getInt(i);
        }
        throw new AssertionError();
    }

    /* renamed from: c */
    public static void m12568c(ByteBuffer byteBuffer, int i, int i2) {
        byteBuffer.put(i, (byte) i2);
    }

    /* renamed from: d */
    public static long m12569d(ByteBuffer byteBuffer, int i) {
        if (f8909a || byteBuffer.order() == C2676d.f8921a) {
            return byteBuffer.getLong(i);
        }
        throw new AssertionError();
    }

    /* renamed from: d */
    public static void m12570d(ByteBuffer byteBuffer, int i, int i2) {
        byteBuffer.put(i, (byte) i2);
        byteBuffer.put(i + 1, (byte) (i2 >>> 8));
    }

    /* renamed from: e */
    public static int m12571e(ByteBuffer byteBuffer, int i) {
        return (byteBuffer.get(i) & 255) | ((byteBuffer.get(i + 1) & 255) << 8);
    }
}
