package com.persianswitch.p126b;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.persianswitch.b.n */
final class C2256n implements C2243e {
    /* renamed from: a */
    public final C2244c f6965a = new C2244c();
    /* renamed from: b */
    public final C2096s f6966b;
    /* renamed from: c */
    boolean f6967c;

    /* renamed from: com.persianswitch.b.n$1 */
    class C22551 extends InputStream {
        /* renamed from: a */
        final /* synthetic */ C2256n f6964a;

        C22551(C2256n c2256n) {
            this.f6964a = c2256n;
        }

        public int available() {
            if (!this.f6964a.f6967c) {
                return (int) Math.min(this.f6964a.f6965a.f6936b, 2147483647L);
            }
            throw new IOException("closed");
        }

        public void close() {
            this.f6964a.close();
        }

        public int read() {
            if (!this.f6964a.f6967c) {
                return (this.f6964a.f6965a.f6936b == 0 && this.f6964a.f6966b.mo3105a(this.f6964a.f6965a, 8192) == -1) ? -1 : this.f6964a.f6965a.mo3186h() & 255;
            } else {
                throw new IOException("closed");
            }
        }

        public int read(byte[] bArr, int i, int i2) {
            if (this.f6964a.f6967c) {
                throw new IOException("closed");
            }
            C2261u.m10423a((long) bArr.length, (long) i, (long) i2);
            return (this.f6964a.f6965a.f6936b == 0 && this.f6964a.f6966b.mo3105a(this.f6964a.f6965a, 8192) == -1) ? -1 : this.f6964a.f6965a.m10259a(bArr, i, i2);
        }

        public String toString() {
            return this.f6964a + ".inputStream()";
        }
    }

    C2256n(C2096s c2096s) {
        if (c2096s == null) {
            throw new NullPointerException("source == null");
        }
        this.f6966b = c2096s;
    }

    /* renamed from: a */
    public long mo3172a(byte b) {
        return m10379a(b, 0);
    }

    /* renamed from: a */
    public long m10379a(byte b, long j) {
        if (this.f6967c) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long a = this.f6965a.m10261a(b, j);
            if (a != -1) {
                return a;
            }
            a = this.f6965a.f6936b;
            if (this.f6966b.mo3105a(this.f6965a, 8192) == -1) {
                return -1;
            }
            j = Math.max(j, a);
        }
    }

    /* renamed from: a */
    public long mo3105a(C2244c c2244c, long j) {
        if (c2244c == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.f6967c) {
            throw new IllegalStateException("closed");
        } else if (this.f6965a.f6936b == 0 && this.f6966b.mo3105a(this.f6965a, 8192) == -1) {
            return -1;
        } else {
            return this.f6965a.mo3105a(c2244c, Math.min(j, this.f6965a.f6936b));
        }
    }

    /* renamed from: a */
    public C2098t mo3106a() {
        return this.f6966b.mo3106a();
    }

    /* renamed from: a */
    public void mo3174a(long j) {
        if (!m10383b(j)) {
            throw new EOFException();
        }
    }

    /* renamed from: b */
    public boolean m10383b(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.f6967c) {
            throw new IllegalStateException("closed");
        } else {
            while (this.f6965a.f6936b < j) {
                if (this.f6966b.mo3105a(this.f6965a, 8192) == -1) {
                    return false;
                }
            }
            return true;
        }
    }

    /* renamed from: c */
    public C2244c mo3177c() {
        return this.f6965a;
    }

    /* renamed from: c */
    public C2245f mo3180c(long j) {
        mo3174a(j);
        return this.f6965a.mo3180c(j);
    }

    public void close() {
        if (!this.f6967c) {
            this.f6967c = true;
            this.f6966b.close();
            this.f6965a.m10313r();
        }
    }

    /* renamed from: e */
    public boolean mo3181e() {
        if (!this.f6967c) {
            return this.f6965a.mo3181e() && this.f6966b.mo3105a(this.f6965a, 8192) == -1;
        } else {
            throw new IllegalStateException("closed");
        }
    }

    /* renamed from: f */
    public InputStream mo3182f() {
        return new C22551(this);
    }

    /* renamed from: f */
    public byte[] mo3183f(long j) {
        mo3174a(j);
        return this.f6965a.mo3183f(j);
    }

    /* renamed from: g */
    public void mo3185g(long j) {
        if (this.f6967c) {
            throw new IllegalStateException("closed");
        }
        while (j > 0) {
            if (this.f6965a.f6936b == 0 && this.f6966b.mo3105a(this.f6965a, 8192) == -1) {
                throw new EOFException();
            }
            long min = Math.min(j, this.f6965a.m10274b());
            this.f6965a.mo3185g(min);
            j -= min;
        }
    }

    /* renamed from: h */
    public byte mo3186h() {
        mo3174a(1);
        return this.f6965a.mo3186h();
    }

    /* renamed from: i */
    public short mo3189i() {
        mo3174a(2);
        return this.f6965a.mo3189i();
    }

    /* renamed from: j */
    public int mo3190j() {
        mo3174a(4);
        return this.f6965a.mo3190j();
    }

    /* renamed from: k */
    public short mo3193k() {
        mo3174a(2);
        return this.f6965a.mo3193k();
    }

    /* renamed from: l */
    public int mo3194l() {
        mo3174a(4);
        return this.f6965a.mo3194l();
    }

    /* renamed from: m */
    public long mo3195m() {
        mo3174a(1);
        for (int i = 0; m10383b((long) (i + 1)); i++) {
            byte b = this.f6965a.m10273b((long) i);
            if ((b < (byte) 48 || b > (byte) 57) && ((b < (byte) 97 || b > (byte) 102) && (b < (byte) 65 || b > (byte) 70))) {
                if (i == 0) {
                    throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", new Object[]{Byte.valueOf(b)}));
                }
                return this.f6965a.mo3195m();
            }
        }
        return this.f6965a.mo3195m();
    }

    /* renamed from: p */
    public String mo3196p() {
        long a = mo3172a((byte) 10);
        if (a != -1) {
            return this.f6965a.m10289e(a);
        }
        C2244c c2244c = new C2244c();
        this.f6965a.m10265a(c2244c, 0, Math.min(32, this.f6965a.m10274b()));
        throw new EOFException("\\n not found: size=" + this.f6965a.m10274b() + " content=" + c2244c.m10309n().mo3214c() + "…");
    }

    /* renamed from: q */
    public byte[] mo3197q() {
        this.f6965a.mo3173a(this.f6966b);
        return this.f6965a.mo3197q();
    }

    public String toString() {
        return "buffer(" + this.f6966b + ")";
    }
}
