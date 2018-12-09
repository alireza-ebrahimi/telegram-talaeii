package com.p054b.p055a.p056a.p059a;

import com.p054b.p055a.C1286b;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p054b.p055a.C1293h;
import com.p054b.p055a.p056a.C1246e;
import com.p057c.p058a.C1254e;
import com.p057c.p058a.p063b.C1315b;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.b.a.a.a.d */
public final class C1256d extends C1251a implements C1246e {
    /* renamed from: b */
    static final /* synthetic */ boolean f3637b = (!C1256d.class.desiredAssertionStatus());
    /* renamed from: l */
    private int f3638l;
    /* renamed from: m */
    private int f3639m;
    /* renamed from: n */
    private double f3640n;
    /* renamed from: o */
    private double f3641o;
    /* renamed from: p */
    private int f3642p;
    /* renamed from: q */
    private String f3643q;
    /* renamed from: r */
    private int f3644r;
    /* renamed from: s */
    private long[] f3645s;

    public C1256d() {
        super("avc1");
        this.f3640n = 72.0d;
        this.f3641o = 72.0d;
        this.f3642p = 1;
        this.f3643q = TtmlNode.ANONYMOUS_REGION_ID;
        this.f3644r = 24;
        this.f3645s = new long[3];
    }

    public C1256d(String str) {
        super(str);
        this.f3640n = 72.0d;
        this.f3641o = 72.0d;
        this.f3642p = 1;
        this.f3643q = TtmlNode.ANONYMOUS_REGION_ID;
        this.f3644r = 24;
        this.f3645s = new long[3];
    }

    /* renamed from: a */
    public void m6507a(double d) {
        this.f3640n = d;
    }

    /* renamed from: b */
    public int m6508b() {
        return this.f3638l;
    }

    /* renamed from: b */
    public void m6509b(double d) {
        this.f3641o = d;
    }

    /* renamed from: b */
    public void m6510b(int i) {
        this.f3638l = i;
    }

    /* renamed from: c */
    public int m6511c() {
        return this.f3639m;
    }

    /* renamed from: c */
    public void m6512c(int i) {
        this.f3639m = i;
    }

    /* renamed from: d */
    public double m6513d() {
        return this.f3640n;
    }

    /* renamed from: d */
    public void m6514d(int i) {
        this.f3642p = i;
    }

    /* renamed from: e */
    public double m6515e() {
        return this.f3641o;
    }

    /* renamed from: e */
    public void m6516e(int i) {
        this.f3644r = i;
    }

    /* renamed from: f */
    public int m6517f() {
        return this.f3642p;
    }

    /* renamed from: g */
    public String m6518g() {
        return this.f3643q;
    }

    public void getBox(WritableByteChannel writableByteChannel) {
        writableByteChannel.write(m6488i());
        ByteBuffer allocate = ByteBuffer.allocate(78);
        allocate.position(6);
        C1291f.m6683b(allocate, this.a);
        C1291f.m6683b(allocate, 0);
        C1291f.m6683b(allocate, 0);
        C1291f.m6684b(allocate, this.f3645s[0]);
        C1291f.m6684b(allocate, this.f3645s[1]);
        C1291f.m6684b(allocate, this.f3645s[2]);
        C1291f.m6683b(allocate, m6508b());
        C1291f.m6683b(allocate, m6511c());
        C1291f.m6678a(allocate, m6513d());
        C1291f.m6678a(allocate, m6515e());
        C1291f.m6684b(allocate, 0);
        C1291f.m6683b(allocate, m6517f());
        C1291f.m6687c(allocate, C1293h.m6692b(m6518g()));
        allocate.put(C1293h.m6691a(m6518g()));
        int b = C1293h.m6692b(m6518g());
        while (b < 31) {
            b++;
            allocate.put((byte) 0);
        }
        C1291f.m6683b(allocate, m6519h());
        C1291f.m6683b(allocate, 65535);
        writableByteChannel.write((ByteBuffer) allocate.rewind());
        m6484a(writableByteChannel);
    }

    public long getSize() {
        long j = m6485j();
        long j2 = j + 78;
        int i = (this.e || (j + 78) + 8 >= 4294967296L) ? 16 : 8;
        return ((long) i) + j2;
    }

    /* renamed from: h */
    public int m6519h() {
        return this.f3644r;
    }

    public void parse(final C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b) {
        final long b = c1254e.mo1107b() + j;
        ByteBuffer allocate = ByteBuffer.allocate(78);
        c1254e.mo1102a(allocate);
        allocate.position(6);
        this.a = C1290e.m6670c(allocate);
        long c = (long) C1290e.m6670c(allocate);
        if (f3637b || 0 == c) {
            c = (long) C1290e.m6670c(allocate);
            if (f3637b || 0 == c) {
                this.f3645s[0] = C1290e.m6667a(allocate);
                this.f3645s[1] = C1290e.m6667a(allocate);
                this.f3645s[2] = C1290e.m6667a(allocate);
                this.f3638l = C1290e.m6670c(allocate);
                this.f3639m = C1290e.m6670c(allocate);
                this.f3640n = C1290e.m6673f(allocate);
                this.f3641o = C1290e.m6673f(allocate);
                c = C1290e.m6667a(allocate);
                if (f3637b || 0 == c) {
                    this.f3642p = C1290e.m6670c(allocate);
                    int d = C1290e.m6671d(allocate);
                    if (d > 31) {
                        d = 31;
                    }
                    byte[] bArr = new byte[d];
                    allocate.get(bArr);
                    this.f3643q = C1293h.m6690a(bArr);
                    if (d < 31) {
                        allocate.get(new byte[(31 - d)]);
                    }
                    this.f3644r = C1290e.m6670c(allocate);
                    long c2 = (long) C1290e.m6670c(allocate);
                    if (f3637b || 65535 == c2) {
                        mo1094a(new C1254e(this) {
                            /* renamed from: a */
                            final /* synthetic */ C1256d f3634a;

                            /* renamed from: a */
                            public int mo1102a(ByteBuffer byteBuffer) {
                                if (b == c1254e.mo1107b()) {
                                    return -1;
                                }
                                if (((long) byteBuffer.remaining()) <= b - c1254e.mo1107b()) {
                                    return c1254e.mo1102a(byteBuffer);
                                }
                                ByteBuffer allocate = ByteBuffer.allocate(C1315b.m6756a(b - c1254e.mo1107b()));
                                c1254e.mo1102a(allocate);
                                byteBuffer.put((ByteBuffer) allocate.rewind());
                                return allocate.capacity();
                            }

                            /* renamed from: a */
                            public long mo1103a() {
                                return b;
                            }

                            /* renamed from: a */
                            public long mo1104a(long j, long j2, WritableByteChannel writableByteChannel) {
                                return c1254e.mo1104a(j, j2, writableByteChannel);
                            }

                            /* renamed from: a */
                            public ByteBuffer mo1105a(long j, long j2) {
                                return c1254e.mo1105a(j, j2);
                            }

                            /* renamed from: a */
                            public void mo1106a(long j) {
                                c1254e.mo1106a(j);
                            }

                            /* renamed from: b */
                            public long mo1107b() {
                                return c1254e.mo1107b();
                            }

                            public void close() {
                                c1254e.close();
                            }
                        }, j - 78, c1286b);
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError("reserved byte not 0");
            }
            throw new AssertionError("reserved byte not 0");
        }
        throw new AssertionError("reserved byte not 0");
    }
}
