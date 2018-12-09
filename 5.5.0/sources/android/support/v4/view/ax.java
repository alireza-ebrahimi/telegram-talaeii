package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class ax {
    /* renamed from: d */
    static final C0591g f1339d;
    /* renamed from: a */
    Runnable f1340a = null;
    /* renamed from: b */
    Runnable f1341b = null;
    /* renamed from: c */
    int f1342c = -1;
    /* renamed from: e */
    private WeakReference<View> f1343e;

    /* renamed from: android.support.v4.view.ax$g */
    interface C0591g {
        /* renamed from: a */
        long mo529a(ax axVar, View view);

        /* renamed from: a */
        void mo530a(ax axVar, View view, float f);

        /* renamed from: a */
        void mo531a(ax axVar, View view, long j);

        /* renamed from: a */
        void mo532a(ax axVar, View view, bb bbVar);

        /* renamed from: a */
        void mo533a(ax axVar, View view, bd bdVar);

        /* renamed from: a */
        void mo534a(ax axVar, View view, Interpolator interpolator);

        /* renamed from: b */
        void mo535b(ax axVar, View view);

        /* renamed from: b */
        void mo536b(ax axVar, View view, float f);

        /* renamed from: b */
        void mo537b(ax axVar, View view, long j);

        /* renamed from: c */
        void mo538c(ax axVar, View view);

        /* renamed from: c */
        void mo539c(ax axVar, View view, float f);

        /* renamed from: d */
        void mo540d(ax axVar, View view, float f);

        /* renamed from: e */
        void mo541e(ax axVar, View view, float f);
    }

    /* renamed from: android.support.v4.view.ax$a */
    static class C0592a implements C0591g {
        /* renamed from: a */
        WeakHashMap<View, Runnable> f1335a = null;

        /* renamed from: android.support.v4.view.ax$a$a */
        class C0590a implements Runnable {
            /* renamed from: a */
            WeakReference<View> f1332a;
            /* renamed from: b */
            ax f1333b;
            /* renamed from: c */
            final /* synthetic */ C0592a f1334c;

            C0590a(C0592a c0592a, ax axVar, View view) {
                this.f1334c = c0592a;
                this.f1332a = new WeakReference(view);
                this.f1333b = axVar;
            }

            public void run() {
                View view = (View) this.f1332a.get();
                if (view != null) {
                    this.f1334c.m3002d(this.f1333b, view);
                }
            }
        }

        C0592a() {
        }

        /* renamed from: a */
        private void m2989a(View view) {
            if (this.f1335a != null) {
                Runnable runnable = (Runnable) this.f1335a.get(view);
                if (runnable != null) {
                    view.removeCallbacks(runnable);
                }
            }
        }

        /* renamed from: e */
        private void m2990e(ax axVar, View view) {
            Runnable runnable = null;
            if (this.f1335a != null) {
                runnable = (Runnable) this.f1335a.get(view);
            }
            if (runnable == null) {
                runnable = new C0590a(this, axVar, view);
                if (this.f1335a == null) {
                    this.f1335a = new WeakHashMap();
                }
                this.f1335a.put(view, runnable);
            }
            view.removeCallbacks(runnable);
            view.post(runnable);
        }

        /* renamed from: a */
        public long mo529a(ax axVar, View view) {
            return 0;
        }

        /* renamed from: a */
        public void mo530a(ax axVar, View view, float f) {
            m2990e(axVar, view);
        }

        /* renamed from: a */
        public void mo531a(ax axVar, View view, long j) {
        }

        /* renamed from: a */
        public void mo532a(ax axVar, View view, bb bbVar) {
            view.setTag(2113929216, bbVar);
        }

        /* renamed from: a */
        public void mo533a(ax axVar, View view, bd bdVar) {
        }

        /* renamed from: a */
        public void mo534a(ax axVar, View view, Interpolator interpolator) {
        }

        /* renamed from: b */
        public void mo535b(ax axVar, View view) {
            m2990e(axVar, view);
        }

        /* renamed from: b */
        public void mo536b(ax axVar, View view, float f) {
            m2990e(axVar, view);
        }

        /* renamed from: b */
        public void mo537b(ax axVar, View view, long j) {
        }

        /* renamed from: c */
        public void mo538c(ax axVar, View view) {
            m2989a(view);
            m3002d(axVar, view);
        }

        /* renamed from: c */
        public void mo539c(ax axVar, View view, float f) {
            m2990e(axVar, view);
        }

        /* renamed from: d */
        void m3002d(ax axVar, View view) {
            Object tag = view.getTag(2113929216);
            bb bbVar = tag instanceof bb ? (bb) tag : null;
            Runnable runnable = axVar.f1340a;
            Runnable runnable2 = axVar.f1341b;
            axVar.f1340a = null;
            axVar.f1341b = null;
            if (runnable != null) {
                runnable.run();
            }
            if (bbVar != null) {
                bbVar.onAnimationStart(view);
                bbVar.onAnimationEnd(view);
            }
            if (runnable2 != null) {
                runnable2.run();
            }
            if (this.f1335a != null) {
                this.f1335a.remove(view);
            }
        }

        /* renamed from: d */
        public void mo540d(ax axVar, View view, float f) {
            m2990e(axVar, view);
        }

        /* renamed from: e */
        public void mo541e(ax axVar, View view, float f) {
            m2990e(axVar, view);
        }
    }

    /* renamed from: android.support.v4.view.ax$b */
    static class C0594b extends C0592a {
        /* renamed from: b */
        WeakHashMap<View, Integer> f1338b = null;

        /* renamed from: android.support.v4.view.ax$b$a */
        static class C0593a implements bb {
            /* renamed from: a */
            ax f1336a;
            /* renamed from: b */
            boolean f1337b;

            C0593a(ax axVar) {
                this.f1336a = axVar;
            }

            public void onAnimationCancel(View view) {
                Object tag = view.getTag(2113929216);
                bb bbVar = tag instanceof bb ? (bb) tag : null;
                if (bbVar != null) {
                    bbVar.onAnimationCancel(view);
                }
            }

            public void onAnimationEnd(View view) {
                if (this.f1336a.f1342c >= 0) {
                    ah.m2778a(view, this.f1336a.f1342c, null);
                    this.f1336a.f1342c = -1;
                }
                if (VERSION.SDK_INT >= 16 || !this.f1337b) {
                    if (this.f1336a.f1341b != null) {
                        Runnable runnable = this.f1336a.f1341b;
                        this.f1336a.f1341b = null;
                        runnable.run();
                    }
                    Object tag = view.getTag(2113929216);
                    bb bbVar = tag instanceof bb ? (bb) tag : null;
                    if (bbVar != null) {
                        bbVar.onAnimationEnd(view);
                    }
                    this.f1337b = true;
                }
            }

            public void onAnimationStart(View view) {
                this.f1337b = false;
                if (this.f1336a.f1342c >= 0) {
                    ah.m2778a(view, 2, null);
                }
                if (this.f1336a.f1340a != null) {
                    Runnable runnable = this.f1336a.f1340a;
                    this.f1336a.f1340a = null;
                    runnable.run();
                }
                Object tag = view.getTag(2113929216);
                bb bbVar = tag instanceof bb ? (bb) tag : null;
                if (bbVar != null) {
                    bbVar.onAnimationStart(view);
                }
            }
        }

        C0594b() {
        }

        /* renamed from: a */
        public long mo529a(ax axVar, View view) {
            return ay.m3032a(view);
        }

        /* renamed from: a */
        public void mo530a(ax axVar, View view, float f) {
            ay.m3033a(view, f);
        }

        /* renamed from: a */
        public void mo531a(ax axVar, View view, long j) {
            ay.m3034a(view, j);
        }

        /* renamed from: a */
        public void mo532a(ax axVar, View view, bb bbVar) {
            view.setTag(2113929216, bbVar);
            ay.m3035a(view, new C0593a(axVar));
        }

        /* renamed from: a */
        public void mo534a(ax axVar, View view, Interpolator interpolator) {
            ay.m3036a(view, interpolator);
        }

        /* renamed from: b */
        public void mo535b(ax axVar, View view) {
            ay.m3037b(view);
        }

        /* renamed from: b */
        public void mo536b(ax axVar, View view, float f) {
            ay.m3038b(view, f);
        }

        /* renamed from: b */
        public void mo537b(ax axVar, View view, long j) {
            ay.m3039b(view, j);
        }

        /* renamed from: c */
        public void mo538c(ax axVar, View view) {
            ay.m3040c(view);
        }

        /* renamed from: c */
        public void mo539c(ax axVar, View view, float f) {
            ay.m3041c(view, f);
        }

        /* renamed from: d */
        public void mo540d(ax axVar, View view, float f) {
            ay.m3042d(view, f);
        }

        /* renamed from: e */
        public void mo541e(ax axVar, View view, float f) {
            ay.m3043e(view, f);
        }
    }

    /* renamed from: android.support.v4.view.ax$d */
    static class C0595d extends C0594b {
        C0595d() {
        }

        /* renamed from: a */
        public void mo532a(ax axVar, View view, bb bbVar) {
            az.m3044a(view, bbVar);
        }
    }

    /* renamed from: android.support.v4.view.ax$c */
    static class C0596c extends C0595d {
        C0596c() {
        }
    }

    /* renamed from: android.support.v4.view.ax$e */
    static class C0597e extends C0596c {
        C0597e() {
        }

        /* renamed from: a */
        public void mo533a(ax axVar, View view, bd bdVar) {
            ba.m3054a(view, bdVar);
        }
    }

    /* renamed from: android.support.v4.view.ax$f */
    static class C0598f extends C0597e {
        C0598f() {
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            f1339d = new C0598f();
        } else if (i >= 19) {
            f1339d = new C0597e();
        } else if (i >= 18) {
            f1339d = new C0596c();
        } else if (i >= 16) {
            f1339d = new C0595d();
        } else if (i >= 14) {
            f1339d = new C0594b();
        } else {
            f1339d = new C0592a();
        }
    }

    ax(View view) {
        this.f1343e = new WeakReference(view);
    }

    /* renamed from: a */
    public long m3019a() {
        View view = (View) this.f1343e.get();
        return view != null ? f1339d.mo529a(this, view) : 0;
    }

    /* renamed from: a */
    public ax m3020a(float f) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo530a(this, view, f);
        }
        return this;
    }

    /* renamed from: a */
    public ax m3021a(long j) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo531a(this, view, j);
        }
        return this;
    }

    /* renamed from: a */
    public ax m3022a(bb bbVar) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo532a(this, view, bbVar);
        }
        return this;
    }

    /* renamed from: a */
    public ax m3023a(bd bdVar) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo533a(this, view, bdVar);
        }
        return this;
    }

    /* renamed from: a */
    public ax m3024a(Interpolator interpolator) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo534a(this, view, interpolator);
        }
        return this;
    }

    /* renamed from: b */
    public ax m3025b(float f) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo536b(this, view, f);
        }
        return this;
    }

    /* renamed from: b */
    public ax m3026b(long j) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo537b(this, view, j);
        }
        return this;
    }

    /* renamed from: b */
    public void m3027b() {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo535b(this, view);
        }
    }

    /* renamed from: c */
    public ax m3028c(float f) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo539c(this, view, f);
        }
        return this;
    }

    /* renamed from: c */
    public void m3029c() {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo538c(this, view);
        }
    }

    /* renamed from: d */
    public ax m3030d(float f) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo540d(this, view, f);
        }
        return this;
    }

    /* renamed from: e */
    public ax m3031e(float f) {
        View view = (View) this.f1343e.get();
        if (view != null) {
            f1339d.mo541e(this, view, f);
        }
        return this;
    }
}
