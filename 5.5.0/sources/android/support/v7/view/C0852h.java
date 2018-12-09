package android.support.v7.view;

import android.support.v4.view.ax;
import android.support.v4.view.bb;
import android.support.v4.view.bc;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: android.support.v7.view.h */
public class C0852h {
    /* renamed from: a */
    final ArrayList<ax> f2047a = new ArrayList();
    /* renamed from: b */
    bb f2048b;
    /* renamed from: c */
    private long f2049c = -1;
    /* renamed from: d */
    private Interpolator f2050d;
    /* renamed from: e */
    private boolean f2051e;
    /* renamed from: f */
    private final bc f2052f = new C08511(this);

    /* renamed from: android.support.v7.view.h$1 */
    class C08511 extends bc {
        /* renamed from: a */
        final /* synthetic */ C0852h f2044a;
        /* renamed from: b */
        private boolean f2045b = false;
        /* renamed from: c */
        private int f2046c = 0;

        C08511(C0852h c0852h) {
            this.f2044a = c0852h;
        }

        /* renamed from: a */
        void m4060a() {
            this.f2046c = 0;
            this.f2045b = false;
            this.f2044a.m4067b();
        }

        public void onAnimationEnd(View view) {
            int i = this.f2046c + 1;
            this.f2046c = i;
            if (i == this.f2044a.f2047a.size()) {
                if (this.f2044a.f2048b != null) {
                    this.f2044a.f2048b.onAnimationEnd(null);
                }
                m4060a();
            }
        }

        public void onAnimationStart(View view) {
            if (!this.f2045b) {
                this.f2045b = true;
                if (this.f2044a.f2048b != null) {
                    this.f2044a.f2048b.onAnimationStart(null);
                }
            }
        }
    }

    /* renamed from: a */
    public C0852h m4061a(long j) {
        if (!this.f2051e) {
            this.f2049c = j;
        }
        return this;
    }

    /* renamed from: a */
    public C0852h m4062a(ax axVar) {
        if (!this.f2051e) {
            this.f2047a.add(axVar);
        }
        return this;
    }

    /* renamed from: a */
    public C0852h m4063a(ax axVar, ax axVar2) {
        this.f2047a.add(axVar);
        axVar2.m3026b(axVar.m3019a());
        this.f2047a.add(axVar2);
        return this;
    }

    /* renamed from: a */
    public C0852h m4064a(bb bbVar) {
        if (!this.f2051e) {
            this.f2048b = bbVar;
        }
        return this;
    }

    /* renamed from: a */
    public C0852h m4065a(Interpolator interpolator) {
        if (!this.f2051e) {
            this.f2050d = interpolator;
        }
        return this;
    }

    /* renamed from: a */
    public void m4066a() {
        if (!this.f2051e) {
            Iterator it = this.f2047a.iterator();
            while (it.hasNext()) {
                ax axVar = (ax) it.next();
                if (this.f2049c >= 0) {
                    axVar.m3021a(this.f2049c);
                }
                if (this.f2050d != null) {
                    axVar.m3024a(this.f2050d);
                }
                if (this.f2048b != null) {
                    axVar.m3022a(this.f2052f);
                }
                axVar.m3029c();
            }
            this.f2051e = true;
        }
    }

    /* renamed from: b */
    void m4067b() {
        this.f2051e = false;
    }

    /* renamed from: c */
    public void m4068c() {
        if (this.f2051e) {
            Iterator it = this.f2047a.iterator();
            while (it.hasNext()) {
                ((ax) it.next()).m3027b();
            }
            this.f2051e = false;
        }
    }
}
