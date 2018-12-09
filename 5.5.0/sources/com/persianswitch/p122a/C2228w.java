package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2077h;
import com.persianswitch.p122a.p123a.C2127j;
import com.persianswitch.p122a.p123a.p127b.C2134c;
import com.persianswitch.p122a.p123a.p127b.C2155l;
import com.persianswitch.p122a.p123a.p127b.C2158o;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.persianswitch.a.w */
final class C2228w implements C2196e {
    /* renamed from: a */
    C2231x f6883a;
    /* renamed from: b */
    private final C2225u f6884b;
    /* renamed from: c */
    private final C2158o f6885c;
    /* renamed from: d */
    private boolean f6886d;

    /* renamed from: com.persianswitch.a.w$a */
    final class C2227a extends C2077h {
        /* renamed from: a */
        final /* synthetic */ C2228w f6881a;
        /* renamed from: c */
        private final C2197f f6882c;

        /* renamed from: a */
        String m10130a() {
            return this.f6881a.f6883a.m10157a().m10075f();
        }

        /* renamed from: b */
        protected void mo3089b() {
            IOException e;
            Object obj = 1;
            Object obj2 = null;
            try {
                C2236z a = this.f6881a.m10137d();
                if (this.f6881a.f6885c.m9749a()) {
                    try {
                        this.f6882c.m9949a(this.f6881a, new IOException("Canceled"));
                    } catch (IOException e2) {
                        e = e2;
                        if (obj == null) {
                            try {
                                C2127j.m9615c().mo3132a(4, "Callback failure for " + this.f6881a.m10134c(), (Throwable) e);
                            } catch (Throwable th) {
                                this.f6881a.f6884b.m10124s().m10005a(this);
                            }
                        } else {
                            this.f6882c.m9949a(this.f6881a, e);
                        }
                        this.f6881a.f6884b.m10124s().m10005a(this);
                    }
                }
                this.f6882c.m9948a(this.f6881a, a);
                this.f6881a.f6884b.m10124s().m10005a(this);
            } catch (IOException e3) {
                e = e3;
                obj = obj2;
                if (obj == null) {
                    this.f6882c.m9949a(this.f6881a, e);
                } else {
                    C2127j.m9615c().mo3132a(4, "Callback failure for " + this.f6881a.m10134c(), (Throwable) e);
                }
                this.f6881a.f6884b.m10124s().m10005a(this);
            }
        }
    }

    protected C2228w(C2225u c2225u, C2231x c2231x) {
        this.f6884b = c2225u;
        this.f6883a = c2231x;
        this.f6885c = new C2158o(c2225u);
    }

    /* renamed from: c */
    private String m10134c() {
        return (this.f6885c.m9749a() ? "canceled call" : "call") + " to " + m10139b();
    }

    /* renamed from: d */
    private C2236z m10137d() {
        List arrayList = new ArrayList();
        arrayList.addAll(this.f6884b.m10127v());
        arrayList.add(this.f6885c);
        if (!this.f6885c.m9750b()) {
            arrayList.addAll(this.f6884b.m10128w());
        }
        arrayList.add(new C2134c(this.f6885c.m9750b()));
        return new C2155l(arrayList, null, null, null, 0, this.f6883a).mo3147a(this.f6883a);
    }

    /* renamed from: a */
    public C2236z mo3168a() {
        synchronized (this) {
            if (this.f6886d) {
                throw new IllegalStateException("Already Executed");
            }
            this.f6886d = true;
        }
        try {
            this.f6884b.m10124s().m10006a(this);
            C2236z d = m10137d();
            if (d != null) {
                return d;
            }
            throw new IOException("Canceled");
        } finally {
            this.f6884b.m10124s().m10008b(this);
        }
    }

    /* renamed from: b */
    C2221r m10139b() {
        return this.f6883a.m10157a().m10070c("/...");
    }
}
