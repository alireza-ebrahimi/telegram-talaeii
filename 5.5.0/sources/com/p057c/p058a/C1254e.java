package com.p057c.p058a;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.c.a.e */
public interface C1254e extends Closeable {
    /* renamed from: a */
    int mo1102a(ByteBuffer byteBuffer);

    /* renamed from: a */
    long mo1103a();

    /* renamed from: a */
    long mo1104a(long j, long j2, WritableByteChannel writableByteChannel);

    /* renamed from: a */
    ByteBuffer mo1105a(long j, long j2);

    /* renamed from: a */
    void mo1106a(long j);

    /* renamed from: b */
    long mo1107b();

    void close();
}
