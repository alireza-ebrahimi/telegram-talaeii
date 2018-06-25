package com.crashlytics.android.p066c;

import android.os.Process;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;

/* renamed from: com.crashlytics.android.c.c */
class C1396c {
    /* renamed from: a */
    private static final AtomicLong f4237a = new AtomicLong(0);
    /* renamed from: b */
    private static String f4238b;

    public C1396c(C1122p c1122p) {
        r0 = new byte[10];
        m7044a(r0);
        m7046b(r0);
        m7048c(r0);
        String a = C1110i.m6002a(c1122p.m6060b());
        String a2 = C1110i.m6004a(r0);
        f4238b = String.format(Locale.US, "%s-%s-%s-%s", new Object[]{a2.substring(0, 12), a2.substring(12, 16), a2.subSequence(16, 20), a.substring(0, 12)}).toUpperCase(Locale.US);
    }

    /* renamed from: a */
    private void m7044a(byte[] bArr) {
        long time = new Date().getTime();
        long j = time / 1000;
        time %= 1000;
        byte[] a = C1396c.m7045a(j);
        bArr[0] = a[0];
        bArr[1] = a[1];
        bArr[2] = a[2];
        bArr[3] = a[3];
        byte[] b = C1396c.m7047b(time);
        bArr[4] = b[0];
        bArr[5] = b[1];
    }

    /* renamed from: a */
    private static byte[] m7045a(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt((int) j);
        allocate.order(ByteOrder.BIG_ENDIAN);
        allocate.position(0);
        return allocate.array();
    }

    /* renamed from: b */
    private void m7046b(byte[] bArr) {
        byte[] b = C1396c.m7047b(f4237a.incrementAndGet());
        bArr[6] = b[0];
        bArr[7] = b[1];
    }

    /* renamed from: b */
    private static byte[] m7047b(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.putShort((short) ((int) j));
        allocate.order(ByteOrder.BIG_ENDIAN);
        allocate.position(0);
        return allocate.array();
    }

    /* renamed from: c */
    private void m7048c(byte[] bArr) {
        byte[] b = C1396c.m7047b((long) Integer.valueOf(Process.myPid()).shortValue());
        bArr[8] = b[0];
        bArr[9] = b[1];
    }

    public String toString() {
        return f4238b;
    }
}
