package com.persianswitch.p126b;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

/* renamed from: com.persianswitch.b.j */
public final class C2248j implements C2096s {
    /* renamed from: a */
    private int f6946a = 0;
    /* renamed from: b */
    private final C2243e f6947b;
    /* renamed from: c */
    private final Inflater f6948c;
    /* renamed from: d */
    private final C2249k f6949d;
    /* renamed from: e */
    private final CRC32 f6950e = new CRC32();

    public C2248j(C2096s c2096s) {
        if (c2096s == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.f6948c = new Inflater(true);
        this.f6947b = C2253l.m10358a(c2096s);
        this.f6949d = new C2249k(this.f6947b, this.f6948c);
    }

    /* renamed from: a */
    private void m10342a(C2244c c2244c, long j, long j2) {
        C2257o c2257o = c2244c.f6935a;
        while (j >= ((long) (c2257o.f6970c - c2257o.f6969b))) {
            j -= (long) (c2257o.f6970c - c2257o.f6969b);
            c2257o = c2257o.f6973f;
        }
        while (j2 > 0) {
            int i = (int) (((long) c2257o.f6969b) + j);
            int min = (int) Math.min((long) (c2257o.f6970c - i), j2);
            this.f6950e.update(c2257o.f6968a, i, min);
            j2 -= (long) min;
            c2257o = c2257o.f6973f;
            j = 0;
        }
    }

    /* renamed from: a */
    private void m10343a(String str, int i, int i2) {
        if (i2 != i) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{str, Integer.valueOf(i2), Integer.valueOf(i)}));
        }
    }

    /* renamed from: b */
    private void m10344b() {
        long a;
        this.f6947b.mo3174a(10);
        byte b = this.f6947b.mo3177c().m10273b(3);
        Object obj = ((b >> 1) & 1) == 1 ? 1 : null;
        if (obj != null) {
            m10342a(this.f6947b.mo3177c(), 0, 10);
        }
        m10343a("ID1ID2", 8075, this.f6947b.mo3189i());
        this.f6947b.mo3185g(8);
        if (((b >> 2) & 1) == 1) {
            this.f6947b.mo3174a(2);
            if (obj != null) {
                m10342a(this.f6947b.mo3177c(), 0, 2);
            }
            short k = this.f6947b.mo3177c().mo3193k();
            this.f6947b.mo3174a((long) k);
            if (obj != null) {
                m10342a(this.f6947b.mo3177c(), 0, (long) k);
            }
            this.f6947b.mo3185g((long) k);
        }
        if (((b >> 3) & 1) == 1) {
            a = this.f6947b.mo3172a((byte) 0);
            if (a == -1) {
                throw new EOFException();
            }
            if (obj != null) {
                m10342a(this.f6947b.mo3177c(), 0, 1 + a);
            }
            this.f6947b.mo3185g(1 + a);
        }
        if (((b >> 4) & 1) == 1) {
            a = this.f6947b.mo3172a((byte) 0);
            if (a == -1) {
                throw new EOFException();
            }
            if (obj != null) {
                m10342a(this.f6947b.mo3177c(), 0, 1 + a);
            }
            this.f6947b.mo3185g(1 + a);
        }
        if (obj != null) {
            m10343a("FHCRC", this.f6947b.mo3193k(), (short) ((int) this.f6950e.getValue()));
            this.f6950e.reset();
        }
    }

    /* renamed from: c */
    private void m10345c() {
        m10343a("CRC", this.f6947b.mo3194l(), (int) this.f6950e.getValue());
        m10343a("ISIZE", this.f6947b.mo3194l(), this.f6948c.getTotalOut());
    }

    /* renamed from: a */
    public long mo3105a(C2244c c2244c, long j) {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (j == 0) {
            return 0;
        } else {
            if (this.f6946a == 0) {
                m10344b();
                this.f6946a = 1;
            }
            if (this.f6946a == 1) {
                long j2 = c2244c.f6936b;
                long a = this.f6949d.mo3105a(c2244c, j);
                if (a != -1) {
                    m10342a(c2244c, j2, a);
                    return a;
                }
                this.f6946a = 2;
            }
            if (this.f6946a == 2) {
                m10345c();
                this.f6946a = 3;
                if (!this.f6947b.mo3181e()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    /* renamed from: a */
    public C2098t mo3106a() {
        return this.f6947b.mo3106a();
    }

    public void close() {
        this.f6949d.close();
    }
}
