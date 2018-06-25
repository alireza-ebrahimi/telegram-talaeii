package android.support.v4.view.p023a;

import android.os.Build.VERSION;
import android.view.View;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v4.view.a.o */
public class C0556o {
    /* renamed from: a */
    private static final C0551c f1312a;
    /* renamed from: b */
    private final Object f1313b;

    /* renamed from: android.support.v4.view.a.o$c */
    interface C0551c {
        /* renamed from: a */
        List<CharSequence> mo423a(Object obj);

        /* renamed from: a */
        void mo424a(Object obj, int i);

        /* renamed from: a */
        void mo425a(Object obj, View view, int i);

        /* renamed from: a */
        void mo426a(Object obj, CharSequence charSequence);

        /* renamed from: a */
        void mo427a(Object obj, boolean z);

        /* renamed from: b */
        void mo428b(Object obj, int i);

        /* renamed from: b */
        void mo429b(Object obj, CharSequence charSequence);

        /* renamed from: b */
        void mo430b(Object obj, boolean z);

        /* renamed from: c */
        void mo431c(Object obj, int i);

        /* renamed from: c */
        void mo432c(Object obj, boolean z);

        /* renamed from: d */
        void mo433d(Object obj, int i);

        /* renamed from: d */
        void mo434d(Object obj, boolean z);

        /* renamed from: e */
        void mo435e(Object obj, int i);

        /* renamed from: f */
        void mo436f(Object obj, int i);

        /* renamed from: g */
        void mo437g(Object obj, int i);
    }

    /* renamed from: android.support.v4.view.a.o$e */
    static class C0552e implements C0551c {
        C0552e() {
        }

        /* renamed from: a */
        public List<CharSequence> mo423a(Object obj) {
            return Collections.emptyList();
        }

        /* renamed from: a */
        public void mo424a(Object obj, int i) {
        }

        /* renamed from: a */
        public void mo425a(Object obj, View view, int i) {
        }

        /* renamed from: a */
        public void mo426a(Object obj, CharSequence charSequence) {
        }

        /* renamed from: a */
        public void mo427a(Object obj, boolean z) {
        }

        /* renamed from: b */
        public void mo428b(Object obj, int i) {
        }

        /* renamed from: b */
        public void mo429b(Object obj, CharSequence charSequence) {
        }

        /* renamed from: b */
        public void mo430b(Object obj, boolean z) {
        }

        /* renamed from: c */
        public void mo431c(Object obj, int i) {
        }

        /* renamed from: c */
        public void mo432c(Object obj, boolean z) {
        }

        /* renamed from: d */
        public void mo433d(Object obj, int i) {
        }

        /* renamed from: d */
        public void mo434d(Object obj, boolean z) {
        }

        /* renamed from: e */
        public void mo435e(Object obj, int i) {
        }

        /* renamed from: f */
        public void mo436f(Object obj, int i) {
        }

        /* renamed from: g */
        public void mo437g(Object obj, int i) {
        }
    }

    /* renamed from: android.support.v4.view.a.o$a */
    static class C0553a extends C0552e {
        C0553a() {
        }

        /* renamed from: a */
        public List<CharSequence> mo423a(Object obj) {
            return C0557p.m2489a(obj);
        }

        /* renamed from: a */
        public void mo424a(Object obj, int i) {
            C0557p.m2490a(obj, i);
        }

        /* renamed from: a */
        public void mo426a(Object obj, CharSequence charSequence) {
            C0557p.m2491a(obj, charSequence);
        }

        /* renamed from: a */
        public void mo427a(Object obj, boolean z) {
            C0557p.m2492a(obj, z);
        }

        /* renamed from: b */
        public void mo428b(Object obj, int i) {
            C0557p.m2493b(obj, i);
        }

        /* renamed from: b */
        public void mo429b(Object obj, CharSequence charSequence) {
            C0557p.m2494b(obj, charSequence);
        }

        /* renamed from: b */
        public void mo430b(Object obj, boolean z) {
            C0557p.m2495b(obj, z);
        }

        /* renamed from: c */
        public void mo431c(Object obj, int i) {
            C0557p.m2496c(obj, i);
        }

        /* renamed from: c */
        public void mo432c(Object obj, boolean z) {
            C0557p.m2497c(obj, z);
        }

        /* renamed from: d */
        public void mo433d(Object obj, int i) {
            C0557p.m2498d(obj, i);
        }

        /* renamed from: d */
        public void mo434d(Object obj, boolean z) {
            C0557p.m2499d(obj, z);
        }

        /* renamed from: e */
        public void mo435e(Object obj, int i) {
            C0557p.m2500e(obj, i);
        }
    }

    /* renamed from: android.support.v4.view.a.o$b */
    static class C0554b extends C0553a {
        C0554b() {
        }

        /* renamed from: f */
        public void mo436f(Object obj, int i) {
            C0558q.m2501a(obj, i);
        }

        /* renamed from: g */
        public void mo437g(Object obj, int i) {
            C0558q.m2502b(obj, i);
        }
    }

    /* renamed from: android.support.v4.view.a.o$d */
    static class C0555d extends C0554b {
        C0555d() {
        }

        /* renamed from: a */
        public void mo425a(Object obj, View view, int i) {
            C0559r.m2503a(obj, view, i);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            f1312a = new C0555d();
        } else if (VERSION.SDK_INT >= 15) {
            f1312a = new C0554b();
        } else if (VERSION.SDK_INT >= 14) {
            f1312a = new C0553a();
        } else {
            f1312a = new C0552e();
        }
    }

    @Deprecated
    public C0556o(Object obj) {
        this.f1313b = obj;
    }

    /* renamed from: a */
    public List<CharSequence> m2474a() {
        return f1312a.mo423a(this.f1313b);
    }

    /* renamed from: a */
    public void m2475a(int i) {
        f1312a.mo428b(this.f1313b, i);
    }

    /* renamed from: a */
    public void m2476a(View view, int i) {
        f1312a.mo425a(this.f1313b, view, i);
    }

    /* renamed from: a */
    public void m2477a(CharSequence charSequence) {
        f1312a.mo426a(this.f1313b, charSequence);
    }

    /* renamed from: a */
    public void m2478a(boolean z) {
        f1312a.mo427a(this.f1313b, z);
    }

    /* renamed from: b */
    public void m2479b(int i) {
        f1312a.mo424a(this.f1313b, i);
    }

    /* renamed from: b */
    public void m2480b(CharSequence charSequence) {
        f1312a.mo429b(this.f1313b, charSequence);
    }

    /* renamed from: b */
    public void m2481b(boolean z) {
        f1312a.mo430b(this.f1313b, z);
    }

    /* renamed from: c */
    public void m2482c(int i) {
        f1312a.mo435e(this.f1313b, i);
    }

    /* renamed from: c */
    public void m2483c(boolean z) {
        f1312a.mo432c(this.f1313b, z);
    }

    /* renamed from: d */
    public void m2484d(int i) {
        f1312a.mo431c(this.f1313b, i);
    }

    /* renamed from: d */
    public void m2485d(boolean z) {
        f1312a.mo434d(this.f1313b, z);
    }

    /* renamed from: e */
    public void m2486e(int i) {
        f1312a.mo433d(this.f1313b, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        C0556o c0556o = (C0556o) obj;
        return this.f1313b == null ? c0556o.f1313b == null : this.f1313b.equals(c0556o.f1313b);
    }

    /* renamed from: f */
    public void m2487f(int i) {
        f1312a.mo436f(this.f1313b, i);
    }

    /* renamed from: g */
    public void m2488g(int i) {
        f1312a.mo437g(this.f1313b, i);
    }

    public int hashCode() {
        return this.f1313b == null ? 0 : this.f1313b.hashCode();
    }
}
