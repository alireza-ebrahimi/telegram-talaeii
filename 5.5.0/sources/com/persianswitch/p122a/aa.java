package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p126b.C2243e;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* renamed from: com.persianswitch.a.aa */
public abstract class aa implements Closeable {
    /* renamed from: g */
    private Charset m9695g() {
        C2222t a = mo3143a();
        return a != null ? a.m10085a(C2187l.f6636c) : C2187l.f6636c;
    }

    /* renamed from: a */
    public abstract C2222t mo3143a();

    /* renamed from: b */
    public abstract long mo3144b();

    /* renamed from: c */
    public final InputStream m9698c() {
        return mo3145d().mo3182f();
    }

    public void close() {
        C2187l.m9898a(mo3145d());
    }

    /* renamed from: d */
    public abstract C2243e mo3145d();

    /* renamed from: e */
    public final byte[] m9700e() {
        long b = mo3144b();
        if (b > 2147483647L) {
            throw new IOException("Cannot buffer entire body for content length: " + b);
        }
        Closeable d = mo3145d();
        try {
            byte[] q = d.mo3197q();
            if (b == -1 || b == ((long) q.length)) {
                return q;
            }
            throw new IOException("Content-Length and stream length disagree");
        } finally {
            C2187l.m9898a(d);
        }
    }

    /* renamed from: f */
    public final String m9701f() {
        return new String(m9700e(), m9695g().name());
    }
}
