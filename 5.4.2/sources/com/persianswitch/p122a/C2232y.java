package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p126b.C2242d;
import java.nio.charset.Charset;

/* renamed from: com.persianswitch.a.y */
public abstract class C2232y {
    /* renamed from: a */
    public static C2232y m10165a(C2222t c2222t, String str) {
        Charset charset = C2187l.f6636c;
        if (c2222t != null) {
            charset = c2222t.m10084a();
            if (charset == null) {
                charset = C2187l.f6636c;
                c2222t = C2222t.m10083a(c2222t + "; charset=utf-8");
            }
        }
        return C2232y.m10166a(c2222t, str.getBytes(charset));
    }

    /* renamed from: a */
    public static C2232y m10166a(C2222t c2222t, byte[] bArr) {
        return C2232y.m10167a(c2222t, bArr, 0, bArr.length);
    }

    /* renamed from: a */
    public static C2232y m10167a(final C2222t c2222t, final byte[] bArr, final int i, final int i2) {
        if (bArr == null) {
            throw new NullPointerException("content == null");
        }
        C2187l.m9897a((long) bArr.length, (long) i, (long) i2);
        return new C2232y() {
            /* renamed from: a */
            public C2222t mo3169a() {
                return c2222t;
            }

            /* renamed from: a */
            public void mo3170a(C2242d c2242d) {
                c2242d.mo3179c(bArr, i, i2);
            }

            /* renamed from: b */
            public long mo3171b() {
                return (long) i2;
            }
        };
    }

    /* renamed from: a */
    public abstract C2222t mo3169a();

    /* renamed from: a */
    public abstract void mo3170a(C2242d c2242d);

    /* renamed from: b */
    public long mo3171b() {
        return -1;
    }
}
