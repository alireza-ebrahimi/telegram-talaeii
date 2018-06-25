package com.p054b.p055a.p056a.p059a;

import com.p054b.p055a.C1286b;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import com.p057c.p058a.C1254e;
import com.p057c.p058a.p063b.C1315b;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.b.a.a.a.b */
public final class C1253b extends C1251a {
    /* renamed from: b */
    static final /* synthetic */ boolean f3620b = (!C1253b.class.desiredAssertionStatus());
    /* renamed from: l */
    private int f3621l;
    /* renamed from: m */
    private int f3622m;
    /* renamed from: n */
    private long f3623n;
    /* renamed from: o */
    private int f3624o;
    /* renamed from: p */
    private int f3625p;
    /* renamed from: q */
    private int f3626q;
    /* renamed from: r */
    private long f3627r;
    /* renamed from: s */
    private long f3628s;
    /* renamed from: t */
    private long f3629t;
    /* renamed from: u */
    private long f3630u;
    /* renamed from: v */
    private int f3631v;
    /* renamed from: w */
    private long f3632w;
    /* renamed from: x */
    private byte[] f3633x;

    public C1253b(String str) {
        super(str);
    }

    /* renamed from: a */
    public void m6490a(long j) {
        this.f3623n = j;
    }

    /* renamed from: b */
    public int m6491b() {
        return this.f3621l;
    }

    /* renamed from: b */
    public void m6492b(int i) {
        this.f3621l = i;
    }

    /* renamed from: c */
    public long m6493c() {
        return this.f3623n;
    }

    /* renamed from: c */
    public void m6494c(int i) {
        this.f3622m = i;
    }

    public void getBox(WritableByteChannel writableByteChannel) {
        int i = 0;
        writableByteChannel.write(m6488i());
        int i2 = (this.f3624o == 1 ? 16 : 0) + 28;
        if (this.f3624o == 2) {
            i = 36;
        }
        ByteBuffer allocate = ByteBuffer.allocate(i2 + i);
        allocate.position(6);
        C1291f.m6683b(allocate, this.a);
        C1291f.m6683b(allocate, this.f3624o);
        C1291f.m6683b(allocate, this.f3631v);
        C1291f.m6684b(allocate, this.f3632w);
        C1291f.m6683b(allocate, this.f3621l);
        C1291f.m6683b(allocate, this.f3622m);
        C1291f.m6683b(allocate, this.f3625p);
        C1291f.m6683b(allocate, this.f3626q);
        if (this.d.equals("mlpa")) {
            C1291f.m6684b(allocate, m6493c());
        } else {
            C1291f.m6684b(allocate, m6493c() << 16);
        }
        if (this.f3624o == 1) {
            C1291f.m6684b(allocate, this.f3627r);
            C1291f.m6684b(allocate, this.f3628s);
            C1291f.m6684b(allocate, this.f3629t);
            C1291f.m6684b(allocate, this.f3630u);
        }
        if (this.f3624o == 2) {
            C1291f.m6684b(allocate, this.f3627r);
            C1291f.m6684b(allocate, this.f3628s);
            C1291f.m6684b(allocate, this.f3629t);
            C1291f.m6684b(allocate, this.f3630u);
            allocate.put(this.f3633x);
        }
        writableByteChannel.write((ByteBuffer) allocate.rewind());
        m6484a(writableByteChannel);
    }

    public long getSize() {
        int i = 16;
        int i2 = 0;
        int i3 = (this.f3624o == 1 ? 16 : 0) + 28;
        if (this.f3624o == 2) {
            i2 = 36;
        }
        long j = ((long) (i3 + i2)) + m6485j();
        if (!this.e && 8 + j < 4294967296L) {
            i = 8;
        }
        return ((long) i) + j;
    }

    public void parse(C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b) {
        int i = 36;
        int i2 = 16;
        int i3 = 0;
        ByteBuffer allocate = ByteBuffer.allocate(28);
        c1254e.mo1102a(allocate);
        allocate.position(6);
        this.a = C1290e.m6670c(allocate);
        this.f3624o = C1290e.m6670c(allocate);
        this.f3631v = C1290e.m6670c(allocate);
        this.f3632w = C1290e.m6667a(allocate);
        this.f3621l = C1290e.m6670c(allocate);
        this.f3622m = C1290e.m6670c(allocate);
        this.f3625p = C1290e.m6670c(allocate);
        this.f3626q = C1290e.m6670c(allocate);
        this.f3623n = C1290e.m6667a(allocate);
        if (!this.d.equals("mlpa")) {
            this.f3623n >>>= 16;
        }
        if (this.f3624o == 1) {
            allocate = ByteBuffer.allocate(16);
            c1254e.mo1102a(allocate);
            allocate.rewind();
            this.f3627r = C1290e.m6667a(allocate);
            this.f3628s = C1290e.m6667a(allocate);
            this.f3629t = C1290e.m6667a(allocate);
            this.f3630u = C1290e.m6667a(allocate);
        }
        if (this.f3624o == 2) {
            allocate = ByteBuffer.allocate(36);
            c1254e.mo1102a(allocate);
            allocate.rewind();
            this.f3627r = C1290e.m6667a(allocate);
            this.f3628s = C1290e.m6667a(allocate);
            this.f3629t = C1290e.m6667a(allocate);
            this.f3630u = C1290e.m6667a(allocate);
            this.f3633x = new byte[20];
            allocate.get(this.f3633x);
        }
        if ("owma".equals(this.d)) {
            System.err.println("owma");
            long j2 = j - 28;
            if (this.f3624o != 1) {
                i2 = 0;
            }
            j2 -= (long) i2;
            if (this.f3624o == 2) {
                i3 = 36;
            }
            final long j3 = j2 - ((long) i3);
            final ByteBuffer allocate2 = ByteBuffer.allocate(C1315b.m6756a(j3));
            c1254e.mo1102a(allocate2);
            m6482a(new C1248b(this) {
                /* renamed from: a */
                final /* synthetic */ C1253b f3617a;

                public void getBox(WritableByteChannel writableByteChannel) {
                    allocate2.rewind();
                    writableByteChannel.write(allocate2);
                }

                public C1246e getParent() {
                    return this.f3617a;
                }

                public long getSize() {
                    return j3;
                }

                public String getType() {
                    return "----";
                }

                public void parse(C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b) {
                    throw new RuntimeException("NotImplemented");
                }

                public void setParent(C1246e c1246e) {
                    if (!C1253b.f3620b && c1246e != this.f3617a) {
                        throw new AssertionError("you cannot diswown this special box");
                    }
                }
            });
            return;
        }
        j2 = j - 28;
        if (this.f3624o != 1) {
            i2 = 0;
        }
        j2 -= (long) i2;
        if (this.f3624o != 2) {
            i = 0;
        }
        mo1094a(c1254e, j2 - ((long) i), c1286b);
    }

    public String toString() {
        return "AudioSampleEntry{bytesPerSample=" + this.f3630u + ", bytesPerFrame=" + this.f3629t + ", bytesPerPacket=" + this.f3628s + ", samplesPerPacket=" + this.f3627r + ", packetSize=" + this.f3626q + ", compressionId=" + this.f3625p + ", soundVersion=" + this.f3624o + ", sampleRate=" + this.f3623n + ", sampleSize=" + this.f3622m + ", channelCount=" + this.f3621l + ", boxes=" + mo1093a() + '}';
    }
}
