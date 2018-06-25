package com.persianswitch.p126b;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.Inflater;

/* renamed from: com.persianswitch.b.k */
public final class C2249k implements C2096s {
    /* renamed from: a */
    private final C2243e f6951a;
    /* renamed from: b */
    private final Inflater f6952b;
    /* renamed from: c */
    private int f6953c;
    /* renamed from: d */
    private boolean f6954d;

    C2249k(C2243e c2243e, Inflater inflater) {
        if (c2243e == null) {
            throw new IllegalArgumentException("source == null");
        } else if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        } else {
            this.f6951a = c2243e;
            this.f6952b = inflater;
        }
    }

    public C2249k(C2096s c2096s, Inflater inflater) {
        this(C2253l.m10358a(c2096s), inflater);
    }

    /* renamed from: c */
    private void m10348c() {
        if (this.f6953c != 0) {
            int remaining = this.f6953c - this.f6952b.getRemaining();
            this.f6953c -= remaining;
            this.f6951a.mo3185g((long) remaining);
        }
    }

    /* renamed from: a */
    public long mo3105a(C2244c c2244c, long j) {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.f6954d) {
            throw new IllegalStateException("closed");
        } else if (j == 0) {
            return 0;
        } else {
            boolean b;
            do {
                b = m10351b();
                try {
                    C2257o e = c2244c.m10288e(1);
                    int inflate = this.f6952b.inflate(e.f6968a, e.f6970c, 8192 - e.f6970c);
                    if (inflate > 0) {
                        e.f6970c += inflate;
                        c2244c.f6936b += (long) inflate;
                        return (long) inflate;
                    } else if (this.f6952b.finished() || this.f6952b.needsDictionary()) {
                        m10348c();
                        if (e.f6969b == e.f6970c) {
                            c2244c.f6935a = e.m10398a();
                            C2258p.m10404a(e);
                        }
                        return -1;
                    }
                } catch (Throwable e2) {
                    throw new IOException(e2);
                }
            } while (!b);
            throw new EOFException("source exhausted prematurely");
        }
    }

    /* renamed from: a */
    public C2098t mo3106a() {
        return this.f6951a.mo3106a();
    }

    /* renamed from: b */
    public boolean m10351b() {
        if (!this.f6952b.needsInput()) {
            return false;
        }
        m10348c();
        if (this.f6952b.getRemaining() != 0) {
            throw new IllegalStateException("?");
        } else if (this.f6951a.mo3181e()) {
            return true;
        } else {
            C2257o c2257o = this.f6951a.mo3177c().f6935a;
            this.f6953c = c2257o.f6970c - c2257o.f6969b;
            this.f6952b.setInput(c2257o.f6968a, c2257o.f6969b, this.f6953c);
            return false;
        }
    }

    public void close() {
        if (!this.f6954d) {
            this.f6952b.end();
            this.f6954d = true;
            this.f6951a.close();
        }
    }
}
