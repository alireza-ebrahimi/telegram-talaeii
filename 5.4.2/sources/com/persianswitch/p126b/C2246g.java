package com.persianswitch.p126b;

import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* renamed from: com.persianswitch.b.g */
public final class C2246g implements C2094r {
    /* renamed from: a */
    private final C2242d f6942a;
    /* renamed from: b */
    private final Deflater f6943b;
    /* renamed from: c */
    private boolean f6944c;

    C2246g(C2242d c2242d, Deflater deflater) {
        if (c2242d == null) {
            throw new IllegalArgumentException("source == null");
        } else if (deflater == null) {
            throw new IllegalArgumentException("inflater == null");
        } else {
            this.f6942a = c2242d;
            this.f6943b = deflater;
        }
    }

    public C2246g(C2094r c2094r, Deflater deflater) {
        this(C2253l.m10357a(c2094r), deflater);
    }

    @IgnoreJRERequirement
    /* renamed from: a */
    private void m10332a(boolean z) {
        C2244c c = this.f6942a.mo3177c();
        while (true) {
            C2257o e = c.m10288e(1);
            int deflate = z ? this.f6943b.deflate(e.f6968a, e.f6970c, 8192 - e.f6970c, 2) : this.f6943b.deflate(e.f6968a, e.f6970c, 8192 - e.f6970c);
            if (deflate > 0) {
                e.f6970c += deflate;
                c.f6936b += (long) deflate;
                this.f6942a.mo3198u();
            } else if (this.f6943b.needsInput()) {
                break;
            }
        }
        if (e.f6969b == e.f6970c) {
            c.f6935a = e.m10398a();
            C2258p.m10404a(e);
        }
    }

    /* renamed from: a */
    public C2098t mo3101a() {
        return this.f6942a.mo3101a();
    }

    public void a_(C2244c c2244c, long j) {
        C2261u.m10423a(c2244c.f6936b, 0, j);
        while (j > 0) {
            C2257o c2257o = c2244c.f6935a;
            int min = (int) Math.min(j, (long) (c2257o.f6970c - c2257o.f6969b));
            this.f6943b.setInput(c2257o.f6968a, c2257o.f6969b, min);
            m10332a(false);
            c2244c.f6936b -= (long) min;
            c2257o.f6969b += min;
            if (c2257o.f6969b == c2257o.f6970c) {
                c2244c.f6935a = c2257o.m10398a();
                C2258p.m10404a(c2257o);
            }
            j -= (long) min;
        }
    }

    /* renamed from: b */
    void m10334b() {
        this.f6943b.finish();
        m10332a(false);
    }

    public void close() {
        if (!this.f6944c) {
            Throwable th;
            Throwable th2 = null;
            try {
                m10334b();
            } catch (Throwable th3) {
                th2 = th3;
            }
            try {
                this.f6943b.end();
                th = th2;
            } catch (Throwable th4) {
                th = th4;
                if (th2 != null) {
                    th = th2;
                }
            }
            try {
                this.f6942a.close();
            } catch (Throwable th22) {
                if (th == null) {
                    th = th22;
                }
            }
            this.f6944c = true;
            if (th != null) {
                C2261u.m10424a(th);
            }
        }
    }

    public void flush() {
        m10332a(true);
        this.f6942a.flush();
    }

    public String toString() {
        return "DeflaterSink(" + this.f6942a + ")";
    }
}
