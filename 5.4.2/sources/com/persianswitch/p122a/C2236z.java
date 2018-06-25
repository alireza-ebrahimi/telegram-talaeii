package com.persianswitch.p122a;

import com.persianswitch.p122a.C2217q.C2216a;
import java.io.Closeable;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: com.persianswitch.a.z */
public final class C2236z implements Closeable {
    /* renamed from: a */
    private final C2231x f6914a;
    /* renamed from: b */
    private final C2226v f6915b;
    /* renamed from: c */
    private final int f6916c;
    /* renamed from: d */
    private final String f6917d;
    /* renamed from: e */
    private final C2214p f6918e;
    /* renamed from: f */
    private final C2217q f6919f;
    /* renamed from: g */
    private final aa f6920g;
    /* renamed from: h */
    private final C2236z f6921h;
    /* renamed from: i */
    private final C2236z f6922i;
    /* renamed from: j */
    private final C2236z f6923j;
    /* renamed from: k */
    private final long f6924k;
    /* renamed from: l */
    private final long f6925l;
    /* renamed from: m */
    private volatile C2195d f6926m;

    /* renamed from: com.persianswitch.a.z$a */
    public static class C2235a {
        /* renamed from: a */
        private C2231x f6902a;
        /* renamed from: b */
        private C2226v f6903b;
        /* renamed from: c */
        private int f6904c;
        /* renamed from: d */
        private String f6905d;
        /* renamed from: e */
        private C2214p f6906e;
        /* renamed from: f */
        private C2216a f6907f;
        /* renamed from: g */
        private aa f6908g;
        /* renamed from: h */
        private C2236z f6909h;
        /* renamed from: i */
        private C2236z f6910i;
        /* renamed from: j */
        private C2236z f6911j;
        /* renamed from: k */
        private long f6912k;
        /* renamed from: l */
        private long f6913l;

        public C2235a() {
            this.f6904c = -1;
            this.f6907f = new C2216a();
        }

        private C2235a(C2236z c2236z) {
            this.f6904c = -1;
            this.f6902a = c2236z.f6914a;
            this.f6903b = c2236z.f6915b;
            this.f6904c = c2236z.f6916c;
            this.f6905d = c2236z.f6917d;
            this.f6906e = c2236z.f6918e;
            this.f6907f = c2236z.f6919f.m10026b();
            this.f6908g = c2236z.f6920g;
            this.f6909h = c2236z.f6921h;
            this.f6910i = c2236z.f6922i;
            this.f6911j = c2236z.f6923j;
            this.f6912k = c2236z.f6924k;
            this.f6913l = c2236z.f6925l;
        }

        /* renamed from: a */
        private void m10175a(String str, C2236z c2236z) {
            if (c2236z.f6920g != null) {
                throw new IllegalArgumentException(str + ".body != null");
            } else if (c2236z.f6921h != null) {
                throw new IllegalArgumentException(str + ".networkResponse != null");
            } else if (c2236z.f6922i != null) {
                throw new IllegalArgumentException(str + ".cacheResponse != null");
            } else if (c2236z.f6923j != null) {
                throw new IllegalArgumentException(str + ".priorResponse != null");
            }
        }

        /* renamed from: d */
        private void m10179d(C2236z c2236z) {
            if (c2236z.f6920g != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
        }

        /* renamed from: a */
        public C2235a m10188a(int i) {
            this.f6904c = i;
            return this;
        }

        /* renamed from: a */
        public C2235a m10189a(long j) {
            this.f6912k = j;
            return this;
        }

        /* renamed from: a */
        public C2235a m10190a(aa aaVar) {
            this.f6908g = aaVar;
            return this;
        }

        /* renamed from: a */
        public C2235a m10191a(C2214p c2214p) {
            this.f6906e = c2214p;
            return this;
        }

        /* renamed from: a */
        public C2235a m10192a(C2217q c2217q) {
            this.f6907f = c2217q.m10026b();
            return this;
        }

        /* renamed from: a */
        public C2235a m10193a(C2226v c2226v) {
            this.f6903b = c2226v;
            return this;
        }

        /* renamed from: a */
        public C2235a m10194a(C2231x c2231x) {
            this.f6902a = c2231x;
            return this;
        }

        /* renamed from: a */
        public C2235a m10195a(C2236z c2236z) {
            if (c2236z != null) {
                m10175a("networkResponse", c2236z);
            }
            this.f6909h = c2236z;
            return this;
        }

        /* renamed from: a */
        public C2235a m10196a(String str) {
            this.f6905d = str;
            return this;
        }

        /* renamed from: a */
        public C2235a m10197a(String str, String str2) {
            this.f6907f.m10017a(str, str2);
            return this;
        }

        /* renamed from: a */
        public C2236z m10198a() {
            if (this.f6902a == null) {
                throw new IllegalStateException("request == null");
            } else if (this.f6903b == null) {
                throw new IllegalStateException("protocol == null");
            } else if (this.f6904c >= 0) {
                return new C2236z();
            } else {
                throw new IllegalStateException("code < 0: " + this.f6904c);
            }
        }

        /* renamed from: b */
        public C2235a m10199b(long j) {
            this.f6913l = j;
            return this;
        }

        /* renamed from: b */
        public C2235a m10200b(C2236z c2236z) {
            if (c2236z != null) {
                m10175a("cacheResponse", c2236z);
            }
            this.f6910i = c2236z;
            return this;
        }

        /* renamed from: c */
        public C2235a m10201c(C2236z c2236z) {
            if (c2236z != null) {
                m10179d(c2236z);
            }
            this.f6911j = c2236z;
            return this;
        }
    }

    private C2236z(C2235a c2235a) {
        this.f6914a = c2235a.f6902a;
        this.f6915b = c2235a.f6903b;
        this.f6916c = c2235a.f6904c;
        this.f6917d = c2235a.f6905d;
        this.f6918e = c2235a.f6906e;
        this.f6919f = c2235a.f6907f.m10018a();
        this.f6920g = c2235a.f6908g;
        this.f6921h = c2235a.f6909h;
        this.f6922i = c2235a.f6910i;
        this.f6923j = c2235a.f6911j;
        this.f6924k = c2235a.f6912k;
        this.f6925l = c2235a.f6913l;
    }

    /* renamed from: a */
    public C2231x m10214a() {
        return this.f6914a;
    }

    /* renamed from: a */
    public String m10215a(String str) {
        return m10216a(str, null);
    }

    /* renamed from: a */
    public String m10216a(String str, String str2) {
        String a = this.f6919f.m10025a(str);
        return a != null ? a : str2;
    }

    /* renamed from: b */
    public int m10217b() {
        return this.f6916c;
    }

    /* renamed from: c */
    public boolean m10218c() {
        return this.f6916c >= Callback.DEFAULT_DRAG_ANIMATION_DURATION && this.f6916c < 300;
    }

    public void close() {
        this.f6920g.close();
    }

    /* renamed from: d */
    public C2214p m10219d() {
        return this.f6918e;
    }

    /* renamed from: e */
    public C2217q m10220e() {
        return this.f6919f;
    }

    /* renamed from: f */
    public aa m10221f() {
        return this.f6920g;
    }

    /* renamed from: g */
    public C2235a m10222g() {
        return new C2235a();
    }

    /* renamed from: h */
    public C2195d m10223h() {
        C2195d c2195d = this.f6926m;
        if (c2195d != null) {
            return c2195d;
        }
        c2195d = C2195d.m9936a(this.f6919f);
        this.f6926m = c2195d;
        return c2195d;
    }

    /* renamed from: i */
    public long m10224i() {
        return this.f6924k;
    }

    /* renamed from: j */
    public long m10225j() {
        return this.f6925l;
    }

    public String toString() {
        return "Response{protocol=" + this.f6915b + ", code=" + this.f6916c + ", message=" + this.f6917d + ", url=" + this.f6914a.m10157a() + '}';
    }
}
