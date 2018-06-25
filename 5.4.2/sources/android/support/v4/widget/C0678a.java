package android.support.v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.view.C0659t;
import android.support.v4.view.ah;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.ui.ChatActivity;

/* renamed from: android.support.v4.widget.a */
public abstract class C0678a implements OnTouchListener {
    /* renamed from: r */
    private static final int f1495r = ViewConfiguration.getTapTimeout();
    /* renamed from: a */
    final C0676a f1496a = new C0676a();
    /* renamed from: b */
    final View f1497b;
    /* renamed from: c */
    boolean f1498c;
    /* renamed from: d */
    boolean f1499d;
    /* renamed from: e */
    boolean f1500e;
    /* renamed from: f */
    private final Interpolator f1501f = new AccelerateInterpolator();
    /* renamed from: g */
    private Runnable f1502g;
    /* renamed from: h */
    private float[] f1503h = new float[]{BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
    /* renamed from: i */
    private float[] f1504i = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    /* renamed from: j */
    private int f1505j;
    /* renamed from: k */
    private int f1506k;
    /* renamed from: l */
    private float[] f1507l = new float[]{BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
    /* renamed from: m */
    private float[] f1508m = new float[]{BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
    /* renamed from: n */
    private float[] f1509n = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    /* renamed from: o */
    private boolean f1510o;
    /* renamed from: p */
    private boolean f1511p;
    /* renamed from: q */
    private boolean f1512q;

    /* renamed from: android.support.v4.widget.a$a */
    private static class C0676a {
        /* renamed from: a */
        private int f1483a;
        /* renamed from: b */
        private int f1484b;
        /* renamed from: c */
        private float f1485c;
        /* renamed from: d */
        private float f1486d;
        /* renamed from: e */
        private long f1487e = Long.MIN_VALUE;
        /* renamed from: f */
        private long f1488f = 0;
        /* renamed from: g */
        private int f1489g = 0;
        /* renamed from: h */
        private int f1490h = 0;
        /* renamed from: i */
        private long f1491i = -1;
        /* renamed from: j */
        private float f1492j;
        /* renamed from: k */
        private int f1493k;

        C0676a() {
        }

        /* renamed from: a */
        private float m3275a(float f) {
            return ((-4.0f * f) * f) + (4.0f * f);
        }

        /* renamed from: a */
        private float m3276a(long j) {
            if (j < this.f1487e) {
                return BitmapDescriptorFactory.HUE_RED;
            }
            if (this.f1491i < 0 || j < this.f1491i) {
                return C0678a.m3288a(((float) (j - this.f1487e)) / ((float) this.f1483a), (float) BitmapDescriptorFactory.HUE_RED, 1.0f) * 0.5f;
            }
            long j2 = j - this.f1491i;
            return (C0678a.m3288a(((float) j2) / ((float) this.f1493k), (float) BitmapDescriptorFactory.HUE_RED, 1.0f) * this.f1492j) + (1.0f - this.f1492j);
        }

        /* renamed from: a */
        public void m3277a() {
            this.f1487e = AnimationUtils.currentAnimationTimeMillis();
            this.f1491i = -1;
            this.f1488f = this.f1487e;
            this.f1492j = 0.5f;
            this.f1489g = 0;
            this.f1490h = 0;
        }

        /* renamed from: a */
        public void m3278a(float f, float f2) {
            this.f1485c = f;
            this.f1486d = f2;
        }

        /* renamed from: a */
        public void m3279a(int i) {
            this.f1483a = i;
        }

        /* renamed from: b */
        public void m3280b() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.f1493k = C0678a.m3291a((int) (currentAnimationTimeMillis - this.f1487e), 0, this.f1484b);
            this.f1492j = m3276a(currentAnimationTimeMillis);
            this.f1491i = currentAnimationTimeMillis;
        }

        /* renamed from: b */
        public void m3281b(int i) {
            this.f1484b = i;
        }

        /* renamed from: c */
        public boolean m3282c() {
            return this.f1491i > 0 && AnimationUtils.currentAnimationTimeMillis() > this.f1491i + ((long) this.f1493k);
        }

        /* renamed from: d */
        public void m3283d() {
            if (this.f1488f == 0) {
                throw new RuntimeException("Cannot compute scroll delta before calling start()");
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float a = m3275a(m3276a(currentAnimationTimeMillis));
            long j = currentAnimationTimeMillis - this.f1488f;
            this.f1488f = currentAnimationTimeMillis;
            this.f1489g = (int) ((((float) j) * a) * this.f1485c);
            this.f1490h = (int) ((((float) j) * a) * this.f1486d);
        }

        /* renamed from: e */
        public int m3284e() {
            return (int) (this.f1485c / Math.abs(this.f1485c));
        }

        /* renamed from: f */
        public int m3285f() {
            return (int) (this.f1486d / Math.abs(this.f1486d));
        }

        /* renamed from: g */
        public int m3286g() {
            return this.f1489g;
        }

        /* renamed from: h */
        public int m3287h() {
            return this.f1490h;
        }
    }

    /* renamed from: android.support.v4.widget.a$b */
    private class C0677b implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C0678a f1494a;

        C0677b(C0678a c0678a) {
            this.f1494a = c0678a;
        }

        public void run() {
            if (this.f1494a.f1500e) {
                if (this.f1494a.f1498c) {
                    this.f1494a.f1498c = false;
                    this.f1494a.f1496a.m3277a();
                }
                C0676a c0676a = this.f1494a.f1496a;
                if (c0676a.m3282c() || !this.f1494a.m3299a()) {
                    this.f1494a.f1500e = false;
                    return;
                }
                if (this.f1494a.f1499d) {
                    this.f1494a.f1499d = false;
                    this.f1494a.m3302b();
                }
                c0676a.m3283d();
                this.f1494a.mo587a(c0676a.m3286g(), c0676a.m3287h());
                ah.m2787a(this.f1494a.f1497b, (Runnable) this);
            }
        }
    }

    public C0678a(View view) {
        this.f1497b = view;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int i = (int) ((1575.0f * displayMetrics.density) + 0.5f);
        int i2 = (int) ((displayMetrics.density * 315.0f) + 0.5f);
        m3295a((float) i, (float) i);
        m3300b((float) i2, (float) i2);
        m3296a(1);
        m3307e(Float.MAX_VALUE, Float.MAX_VALUE);
        m3305d(0.2f, 0.2f);
        m3303c(1.0f, 1.0f);
        m3301b(f1495r);
        m3304c(ChatActivity.startAllServices);
        m3306d(ChatActivity.startAllServices);
    }

    /* renamed from: a */
    static float m3288a(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    /* renamed from: a */
    private float m3289a(float f, float f2, float f3, float f4) {
        float f5;
        float a = C0678a.m3288a(f * f2, (float) BitmapDescriptorFactory.HUE_RED, f3);
        a = m3294f(f2 - f4, a) - m3294f(f4, a);
        if (a < BitmapDescriptorFactory.HUE_RED) {
            f5 = -this.f1501f.getInterpolation(-a);
        } else if (a <= BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        } else {
            f5 = this.f1501f.getInterpolation(a);
        }
        return C0678a.m3288a(f5, -1.0f, 1.0f);
    }

    /* renamed from: a */
    private float m3290a(int i, float f, float f2, float f3) {
        float a = m3289a(this.f1503h[i], f2, this.f1504i[i], f);
        if (a == BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        float f4 = this.f1507l[i];
        float f5 = this.f1508m[i];
        float f6 = this.f1509n[i];
        f4 *= f3;
        return a > BitmapDescriptorFactory.HUE_RED ? C0678a.m3288a(a * f4, f5, f6) : -C0678a.m3288a((-a) * f4, f5, f6);
    }

    /* renamed from: a */
    static int m3291a(int i, int i2, int i3) {
        return i > i3 ? i3 : i < i2 ? i2 : i;
    }

    /* renamed from: c */
    private void m3292c() {
        if (this.f1502g == null) {
            this.f1502g = new C0677b(this);
        }
        this.f1500e = true;
        this.f1498c = true;
        if (this.f1510o || this.f1506k <= 0) {
            this.f1502g.run();
        } else {
            ah.m2788a(this.f1497b, this.f1502g, (long) this.f1506k);
        }
        this.f1510o = true;
    }

    /* renamed from: d */
    private void m3293d() {
        if (this.f1498c) {
            this.f1500e = false;
        } else {
            this.f1496a.m3280b();
        }
    }

    /* renamed from: f */
    private float m3294f(float f, float f2) {
        if (f2 == BitmapDescriptorFactory.HUE_RED) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        switch (this.f1505j) {
            case 0:
            case 1:
                return f < f2 ? f >= BitmapDescriptorFactory.HUE_RED ? 1.0f - (f / f2) : (this.f1500e && this.f1505j == 1) ? 1.0f : BitmapDescriptorFactory.HUE_RED : BitmapDescriptorFactory.HUE_RED;
            case 2:
                return f < BitmapDescriptorFactory.HUE_RED ? f / (-f2) : BitmapDescriptorFactory.HUE_RED;
            default:
                return BitmapDescriptorFactory.HUE_RED;
        }
    }

    /* renamed from: a */
    public C0678a m3295a(float f, float f2) {
        this.f1509n[0] = f / 1000.0f;
        this.f1509n[1] = f2 / 1000.0f;
        return this;
    }

    /* renamed from: a */
    public C0678a m3296a(int i) {
        this.f1505j = i;
        return this;
    }

    /* renamed from: a */
    public C0678a m3297a(boolean z) {
        if (this.f1511p && !z) {
            m3293d();
        }
        this.f1511p = z;
        return this;
    }

    /* renamed from: a */
    public abstract void mo587a(int i, int i2);

    /* renamed from: a */
    boolean m3299a() {
        C0676a c0676a = this.f1496a;
        int f = c0676a.m3285f();
        int e = c0676a.m3284e();
        return (f != 0 && mo589f(f)) || (e != 0 && mo588e(e));
    }

    /* renamed from: b */
    public C0678a m3300b(float f, float f2) {
        this.f1508m[0] = f / 1000.0f;
        this.f1508m[1] = f2 / 1000.0f;
        return this;
    }

    /* renamed from: b */
    public C0678a m3301b(int i) {
        this.f1506k = i;
        return this;
    }

    /* renamed from: b */
    void m3302b() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
        this.f1497b.onTouchEvent(obtain);
        obtain.recycle();
    }

    /* renamed from: c */
    public C0678a m3303c(float f, float f2) {
        this.f1507l[0] = f / 1000.0f;
        this.f1507l[1] = f2 / 1000.0f;
        return this;
    }

    /* renamed from: c */
    public C0678a m3304c(int i) {
        this.f1496a.m3279a(i);
        return this;
    }

    /* renamed from: d */
    public C0678a m3305d(float f, float f2) {
        this.f1503h[0] = f;
        this.f1503h[1] = f2;
        return this;
    }

    /* renamed from: d */
    public C0678a m3306d(int i) {
        this.f1496a.m3281b(i);
        return this;
    }

    /* renamed from: e */
    public C0678a m3307e(float f, float f2) {
        this.f1504i[0] = f;
        this.f1504i[1] = f2;
        return this;
    }

    /* renamed from: e */
    public abstract boolean mo588e(int i);

    /* renamed from: f */
    public abstract boolean mo589f(int i);

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z = true;
        if (!this.f1511p) {
            return false;
        }
        switch (C0659t.m3205a(motionEvent)) {
            case 0:
                this.f1499d = true;
                this.f1510o = false;
                break;
            case 1:
            case 3:
                m3293d();
                break;
            case 2:
                break;
        }
        this.f1496a.m3278a(m3290a(0, motionEvent.getX(), (float) view.getWidth(), (float) this.f1497b.getWidth()), m3290a(1, motionEvent.getY(), (float) view.getHeight(), (float) this.f1497b.getHeight()));
        if (!this.f1500e && m3299a()) {
            m3292c();
        }
        if (!(this.f1512q && this.f1500e)) {
            z = false;
        }
        return z;
    }
}
