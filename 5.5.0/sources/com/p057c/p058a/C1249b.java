package com.p057c.p058a;

import com.p054b.p055a.C1286b;
import com.p054b.p055a.C1291f;
import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.c.a.b */
public class C1249b extends C1247d implements C1248b {
    /* renamed from: a */
    private long f3612a;
    /* renamed from: c */
    C1246e f3613c;
    /* renamed from: d */
    protected String f3614d;
    /* renamed from: e */
    protected boolean f3615e;

    public C1249b(String str) {
        this.f3614d = str;
    }

    /* renamed from: a */
    public void mo1094a(C1254e c1254e, long j, C1286b c1286b) {
        this.g = c1254e;
        this.i = c1254e.mo1107b();
        long j2 = this.i;
        int i = (this.f3615e || 8 + j >= 4294967296L) ? 16 : 8;
        this.j = j2 - ((long) i);
        c1254e.mo1106a(c1254e.mo1107b() + j);
        this.k = c1254e.mo1107b();
        this.f = c1286b;
    }

    public void getBox(WritableByteChannel writableByteChannel) {
        writableByteChannel.write(m6488i());
        m6484a(writableByteChannel);
    }

    public C1246e getParent() {
        return this.f3613c;
    }

    public long getSize() {
        long j = m6485j();
        int i = (this.f3615e || 8 + j >= 4294967296L) ? 16 : 8;
        return ((long) i) + j;
    }

    public String getType() {
        return this.f3614d;
    }

    /* renamed from: i */
    protected ByteBuffer m6488i() {
        ByteBuffer wrap;
        byte[] bArr;
        if (this.f3615e || getSize() >= 4294967296L) {
            bArr = new byte[16];
            bArr[3] = (byte) 1;
            bArr[4] = this.f3614d.getBytes()[0];
            bArr[5] = this.f3614d.getBytes()[1];
            bArr[6] = this.f3614d.getBytes()[2];
            bArr[7] = this.f3614d.getBytes()[3];
            wrap = ByteBuffer.wrap(bArr);
            wrap.position(8);
            C1291f.m6680a(wrap, getSize());
        } else {
            bArr = new byte[8];
            bArr[4] = this.f3614d.getBytes()[0];
            bArr[5] = this.f3614d.getBytes()[1];
            bArr[6] = this.f3614d.getBytes()[2];
            bArr[7] = this.f3614d.getBytes()[3];
            wrap = ByteBuffer.wrap(bArr);
            C1291f.m6684b(wrap, getSize());
        }
        wrap.rewind();
        return wrap;
    }

    public void parse(C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b) {
        this.f3612a = c1254e.mo1107b() - ((long) byteBuffer.remaining());
        this.f3615e = byteBuffer.remaining() == 16;
        mo1094a(c1254e, j, c1286b);
    }

    public void setParent(C1246e c1246e) {
        this.f3613c = c1246e;
    }
}
