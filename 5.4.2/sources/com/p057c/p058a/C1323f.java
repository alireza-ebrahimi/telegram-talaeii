package com.p057c.p058a;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.c.a.f */
public class C1323f implements C1254e {
    /* renamed from: a */
    FileChannel f4000a;
    /* renamed from: b */
    String f4001b;

    public C1323f(File file) {
        this.f4000a = new FileInputStream(file).getChannel();
        this.f4001b = file.getName();
    }

    /* renamed from: a */
    public int mo1102a(ByteBuffer byteBuffer) {
        return this.f4000a.read(byteBuffer);
    }

    /* renamed from: a */
    public long mo1103a() {
        return this.f4000a.size();
    }

    /* renamed from: a */
    public long mo1104a(long j, long j2, WritableByteChannel writableByteChannel) {
        return this.f4000a.transferTo(j, j2, writableByteChannel);
    }

    /* renamed from: a */
    public ByteBuffer mo1105a(long j, long j2) {
        return this.f4000a.map(MapMode.READ_ONLY, j, j2);
    }

    /* renamed from: a */
    public void mo1106a(long j) {
        this.f4000a.position(j);
    }

    /* renamed from: b */
    public long mo1107b() {
        return this.f4000a.position();
    }

    public void close() {
        this.f4000a.close();
    }

    public String toString() {
        return this.f4001b;
    }
}
