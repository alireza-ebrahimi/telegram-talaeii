package com.persianswitch.p126b;

/* renamed from: com.persianswitch.b.m */
final class C2254m implements C2242d {
    /* renamed from: a */
    public final C2244c f6961a = new C2244c();
    /* renamed from: b */
    public final C2094r f6962b;
    /* renamed from: c */
    boolean f6963c;

    C2254m(C2094r c2094r) {
        if (c2094r == null) {
            throw new NullPointerException("sink == null");
        }
        this.f6962b = c2094r;
    }

    /* renamed from: a */
    public long mo3173a(C2096s c2096s) {
        if (c2096s == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long a = c2096s.mo3105a(this.f6961a, 8192);
            if (a == -1) {
                return j;
            }
            j += a;
            mo3198u();
        }
    }

    /* renamed from: a */
    public C2098t mo3101a() {
        return this.f6962b.mo3101a();
    }

    public void a_(C2244c c2244c, long j) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.a_(c2244c, j);
        mo3198u();
    }

    /* renamed from: b */
    public C2242d mo3175b(C2245f c2245f) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10266a(c2245f);
        return mo3198u();
    }

    /* renamed from: b */
    public C2242d mo3176b(String str) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10267a(str);
        return mo3198u();
    }

    /* renamed from: c */
    public C2244c mo3177c() {
        return this.f6961a;
    }

    /* renamed from: c */
    public C2242d mo3178c(byte[] bArr) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10276b(bArr);
        return mo3198u();
    }

    /* renamed from: c */
    public C2242d mo3179c(byte[] bArr, int i, int i2) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10277b(bArr, i, i2);
        return mo3198u();
    }

    public void close() {
        if (!this.f6963c) {
            Throwable th = null;
            try {
                if (this.f6961a.f6936b > 0) {
                    this.f6962b.a_(this.f6961a, this.f6961a.f6936b);
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                this.f6962b.close();
            } catch (Throwable th3) {
                if (th == null) {
                    th = th3;
                }
            }
            this.f6963c = true;
            if (th != null) {
                C2261u.m10424a(th);
            }
        }
    }

    public void flush() {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        if (this.f6961a.f6936b > 0) {
            this.f6962b.a_(this.f6961a, this.f6961a.f6936b);
        }
        this.f6962b.flush();
    }

    /* renamed from: g */
    public C2242d mo3184g(int i) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10286d(i);
        return mo3198u();
    }

    /* renamed from: h */
    public C2242d mo3187h(int i) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10281c(i);
        return mo3198u();
    }

    /* renamed from: i */
    public C2242d mo3188i(int i) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10275b(i);
        return mo3198u();
    }

    /* renamed from: j */
    public C2242d mo3191j(long j) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10300i(j);
        return mo3198u();
    }

    /* renamed from: k */
    public C2242d mo3192k(long j) {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        this.f6961a.m10298h(j);
        return mo3198u();
    }

    public String toString() {
        return "buffer(" + this.f6962b + ")";
    }

    /* renamed from: u */
    public C2242d mo3198u() {
        if (this.f6963c) {
            throw new IllegalStateException("closed");
        }
        long g = this.f6961a.m10294g();
        if (g > 0) {
            this.f6962b.a_(this.f6961a, g);
        }
        return this;
    }
}
