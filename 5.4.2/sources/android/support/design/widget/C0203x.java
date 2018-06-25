package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.design.widget.C0201w.C0200e;
import android.support.design.widget.C0201w.C0200e.C0196b;
import android.support.design.widget.C0201w.C0200e.C0198a;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

/* renamed from: android.support.design.widget.x */
class C0203x extends C0200e {
    /* renamed from: a */
    private static final Handler f684a = new Handler(Looper.getMainLooper());
    /* renamed from: b */
    private long f685b;
    /* renamed from: c */
    private boolean f686c;
    /* renamed from: d */
    private float f687d;
    /* renamed from: e */
    private final int[] f688e = new int[2];
    /* renamed from: f */
    private final float[] f689f = new float[2];
    /* renamed from: g */
    private long f690g = 200;
    /* renamed from: h */
    private Interpolator f691h;
    /* renamed from: i */
    private ArrayList<C0198a> f692i;
    /* renamed from: j */
    private ArrayList<C0196b> f693j;
    /* renamed from: k */
    private final Runnable f694k = new C02021(this);

    /* renamed from: android.support.design.widget.x$1 */
    class C02021 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C0203x f683a;

        C02021(C0203x c0203x) {
            this.f683a = c0203x;
        }

        public void run() {
            this.f683a.m972j();
        }
    }

    C0203x() {
    }

    /* renamed from: k */
    private void m953k() {
        if (this.f693j != null) {
            int size = this.f693j.size();
            for (int i = 0; i < size; i++) {
                ((C0196b) this.f693j.get(i)).mo171a();
            }
        }
    }

    /* renamed from: l */
    private void m954l() {
        if (this.f692i != null) {
            int size = this.f692i.size();
            for (int i = 0; i < size; i++) {
                ((C0198a) this.f692i.get(i)).mo172a();
            }
        }
    }

    /* renamed from: m */
    private void m955m() {
        if (this.f692i != null) {
            int size = this.f692i.size();
            for (int i = 0; i < size; i++) {
                ((C0198a) this.f692i.get(i)).mo174c();
            }
        }
    }

    /* renamed from: n */
    private void m956n() {
        if (this.f692i != null) {
            int size = this.f692i.size();
            for (int i = 0; i < size; i++) {
                ((C0198a) this.f692i.get(i)).mo173b();
            }
        }
    }

    /* renamed from: a */
    public void mo175a() {
        if (!this.f686c) {
            if (this.f691h == null) {
                this.f691h = new AccelerateDecelerateInterpolator();
            }
            this.f686c = true;
            this.f687d = BitmapDescriptorFactory.HUE_RED;
            m971i();
        }
    }

    /* renamed from: a */
    public void mo176a(float f, float f2) {
        this.f689f[0] = f;
        this.f689f[1] = f2;
    }

    /* renamed from: a */
    public void mo177a(int i, int i2) {
        this.f688e[0] = i;
        this.f688e[1] = i2;
    }

    /* renamed from: a */
    public void mo178a(long j) {
        this.f690g = j;
    }

    /* renamed from: a */
    public void mo179a(C0198a c0198a) {
        if (this.f692i == null) {
            this.f692i = new ArrayList();
        }
        this.f692i.add(c0198a);
    }

    /* renamed from: a */
    public void mo180a(C0196b c0196b) {
        if (this.f693j == null) {
            this.f693j = new ArrayList();
        }
        this.f693j.add(c0196b);
    }

    /* renamed from: a */
    public void mo181a(Interpolator interpolator) {
        this.f691h = interpolator;
    }

    /* renamed from: b */
    public boolean mo182b() {
        return this.f686c;
    }

    /* renamed from: c */
    public int mo183c() {
        return C0126a.m648a(this.f688e[0], this.f688e[1], mo186f());
    }

    /* renamed from: d */
    public float mo184d() {
        return C0126a.m647a(this.f689f[0], this.f689f[1], mo186f());
    }

    /* renamed from: e */
    public void mo185e() {
        this.f686c = false;
        f684a.removeCallbacks(this.f694k);
        m955m();
        m956n();
    }

    /* renamed from: f */
    public float mo186f() {
        return this.f687d;
    }

    /* renamed from: g */
    public void mo187g() {
        if (this.f686c) {
            this.f686c = false;
            f684a.removeCallbacks(this.f694k);
            this.f687d = 1.0f;
            m953k();
            m956n();
        }
    }

    /* renamed from: h */
    public long mo188h() {
        return this.f690g;
    }

    /* renamed from: i */
    final void m971i() {
        this.f685b = SystemClock.uptimeMillis();
        m953k();
        m954l();
        f684a.postDelayed(this.f694k, 10);
    }

    /* renamed from: j */
    final void m972j() {
        if (this.f686c) {
            float a = C0168n.m808a(((float) (SystemClock.uptimeMillis() - this.f685b)) / ((float) this.f690g), (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
            if (this.f691h != null) {
                a = this.f691h.getInterpolation(a);
            }
            this.f687d = a;
            m953k();
            if (SystemClock.uptimeMillis() >= this.f685b + this.f690g) {
                this.f686c = false;
                m956n();
            }
        }
        if (this.f686c) {
            f684a.postDelayed(this.f694k, 10);
        }
    }
}
