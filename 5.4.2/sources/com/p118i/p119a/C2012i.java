package com.p118i.p119a;

import com.p118i.p119a.p120a.C1996a;

/* renamed from: com.i.a.i */
public class C2012i {
    /* renamed from: a */
    private C2008d f5929a;

    public C2012i() {
        this(true);
    }

    public C2012i(boolean z) {
        this.f5929a = new C2008d();
        this.f5929a.m9075a();
        C2012i.m9081a(z);
    }

    /* renamed from: a */
    private void m9080a(String str) {
        if (m9084a()) {
            throw new IllegalStateException(str);
        }
    }

    /* renamed from: a */
    private static void m9081a(boolean z) {
        C1996a.m9023a(z);
    }

    /* renamed from: a */
    public int m9082a(int i) {
        m9080a("cancel(...) called on a released ThinDownloadManager.");
        return this.f5929a.m9073a(i);
    }

    /* renamed from: a */
    public int m9083a(C2002c c2002c) {
        m9080a("add(...) called on a released ThinDownloadManager.");
        if (c2002c != null) {
            return this.f5929a.m9074a(c2002c);
        }
        throw new IllegalArgumentException("DownloadRequest cannot be null");
    }

    /* renamed from: a */
    public boolean m9084a() {
        return this.f5929a == null;
    }
}
