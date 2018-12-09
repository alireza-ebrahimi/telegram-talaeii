package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.v4.view.p024b.C0604b;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: android.support.v4.widget.r */
class C0718r extends Drawable implements Animatable {
    /* renamed from: a */
    static final Interpolator f1607a = new C0604b();
    /* renamed from: d */
    private static final Interpolator f1608d = new LinearInterpolator();
    /* renamed from: e */
    private static final int[] f1609e = new int[]{Theme.ACTION_BAR_VIDEO_EDIT_COLOR};
    /* renamed from: b */
    float f1610b;
    /* renamed from: c */
    boolean f1611c;
    /* renamed from: f */
    private final ArrayList<Animation> f1612f = new ArrayList();
    /* renamed from: g */
    private final C0717a f1613g;
    /* renamed from: h */
    private float f1614h;
    /* renamed from: i */
    private Resources f1615i;
    /* renamed from: j */
    private View f1616j;
    /* renamed from: k */
    private Animation f1617k;
    /* renamed from: l */
    private double f1618l;
    /* renamed from: m */
    private double f1619m;
    /* renamed from: n */
    private final Callback f1620n = new C07163(this);

    /* renamed from: android.support.v4.widget.r$3 */
    class C07163 implements Callback {
        /* renamed from: a */
        final /* synthetic */ C0718r f1582a;

        C07163(C0718r c0718r) {
            this.f1582a = c0718r;
        }

        public void invalidateDrawable(Drawable drawable) {
            this.f1582a.invalidateSelf();
        }

        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            this.f1582a.scheduleSelf(runnable, j);
        }

        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            this.f1582a.unscheduleSelf(runnable);
        }
    }

    /* renamed from: android.support.v4.widget.r$a */
    private static class C0717a {
        /* renamed from: a */
        private final RectF f1583a = new RectF();
        /* renamed from: b */
        private final Paint f1584b = new Paint();
        /* renamed from: c */
        private final Paint f1585c = new Paint();
        /* renamed from: d */
        private final Callback f1586d;
        /* renamed from: e */
        private float f1587e = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: f */
        private float f1588f = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: g */
        private float f1589g = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: h */
        private float f1590h = 5.0f;
        /* renamed from: i */
        private float f1591i = 2.5f;
        /* renamed from: j */
        private int[] f1592j;
        /* renamed from: k */
        private int f1593k;
        /* renamed from: l */
        private float f1594l;
        /* renamed from: m */
        private float f1595m;
        /* renamed from: n */
        private float f1596n;
        /* renamed from: o */
        private boolean f1597o;
        /* renamed from: p */
        private Path f1598p;
        /* renamed from: q */
        private float f1599q;
        /* renamed from: r */
        private double f1600r;
        /* renamed from: s */
        private int f1601s;
        /* renamed from: t */
        private int f1602t;
        /* renamed from: u */
        private int f1603u;
        /* renamed from: v */
        private final Paint f1604v = new Paint(1);
        /* renamed from: w */
        private int f1605w;
        /* renamed from: x */
        private int f1606x;

        C0717a(Callback callback) {
            this.f1586d = callback;
            this.f1584b.setStrokeCap(Cap.SQUARE);
            this.f1584b.setAntiAlias(true);
            this.f1584b.setStyle(Style.STROKE);
            this.f1585c.setStyle(Style.FILL);
            this.f1585c.setAntiAlias(true);
        }

        /* renamed from: a */
        private void m3476a(Canvas canvas, float f, float f2, Rect rect) {
            if (this.f1597o) {
                if (this.f1598p == null) {
                    this.f1598p = new Path();
                    this.f1598p.setFillType(FillType.EVEN_ODD);
                } else {
                    this.f1598p.reset();
                }
                float f3 = ((float) (((int) this.f1591i) / 2)) * this.f1599q;
                float cos = (float) ((this.f1600r * Math.cos(0.0d)) + ((double) rect.exactCenterX()));
                float sin = (float) ((this.f1600r * Math.sin(0.0d)) + ((double) rect.exactCenterY()));
                this.f1598p.moveTo(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                this.f1598p.lineTo(((float) this.f1601s) * this.f1599q, BitmapDescriptorFactory.HUE_RED);
                this.f1598p.lineTo((((float) this.f1601s) * this.f1599q) / 2.0f, ((float) this.f1602t) * this.f1599q);
                this.f1598p.offset(cos - f3, sin);
                this.f1598p.close();
                this.f1585c.setColor(this.f1606x);
                canvas.rotate((f + f2) - 5.0f, rect.exactCenterX(), rect.exactCenterY());
                canvas.drawPath(this.f1598p, this.f1585c);
            }
        }

        /* renamed from: n */
        private int m3477n() {
            return (this.f1593k + 1) % this.f1592j.length;
        }

        /* renamed from: o */
        private void m3478o() {
            this.f1586d.invalidateDrawable(null);
        }

        /* renamed from: a */
        public int m3479a() {
            return this.f1592j[m3477n()];
        }

        /* renamed from: a */
        public void m3480a(double d) {
            this.f1600r = d;
        }

        /* renamed from: a */
        public void m3481a(float f) {
            this.f1590h = f;
            this.f1584b.setStrokeWidth(f);
            m3478o();
        }

        /* renamed from: a */
        public void m3482a(float f, float f2) {
            this.f1601s = (int) f;
            this.f1602t = (int) f2;
        }

        /* renamed from: a */
        public void m3483a(int i) {
            this.f1605w = i;
        }

        /* renamed from: a */
        public void m3484a(int i, int i2) {
            float min = (float) Math.min(i, i2);
            min = (this.f1600r <= 0.0d || min < BitmapDescriptorFactory.HUE_RED) ? (float) Math.ceil((double) (this.f1590h / 2.0f)) : (float) (((double) (min / 2.0f)) - this.f1600r);
            this.f1591i = min;
        }

        /* renamed from: a */
        public void m3485a(Canvas canvas, Rect rect) {
            RectF rectF = this.f1583a;
            rectF.set(rect);
            rectF.inset(this.f1591i, this.f1591i);
            float f = (this.f1587e + this.f1589g) * 360.0f;
            float f2 = ((this.f1588f + this.f1589g) * 360.0f) - f;
            this.f1584b.setColor(this.f1606x);
            canvas.drawArc(rectF, f, f2, false, this.f1584b);
            m3476a(canvas, f, f2, rect);
            if (this.f1603u < 255) {
                this.f1604v.setColor(this.f1605w);
                this.f1604v.setAlpha(255 - this.f1603u);
                canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(), (float) (rect.width() / 2), this.f1604v);
            }
        }

        /* renamed from: a */
        public void m3486a(ColorFilter colorFilter) {
            this.f1584b.setColorFilter(colorFilter);
            m3478o();
        }

        /* renamed from: a */
        public void m3487a(boolean z) {
            if (this.f1597o != z) {
                this.f1597o = z;
                m3478o();
            }
        }

        /* renamed from: a */
        public void m3488a(int[] iArr) {
            this.f1592j = iArr;
            m3494c(0);
        }

        /* renamed from: b */
        public void m3489b() {
            m3494c(m3477n());
        }

        /* renamed from: b */
        public void m3490b(float f) {
            this.f1587e = f;
            m3478o();
        }

        /* renamed from: b */
        public void m3491b(int i) {
            this.f1606x = i;
        }

        /* renamed from: c */
        public int m3492c() {
            return this.f1603u;
        }

        /* renamed from: c */
        public void m3493c(float f) {
            this.f1588f = f;
            m3478o();
        }

        /* renamed from: c */
        public void m3494c(int i) {
            this.f1593k = i;
            this.f1606x = this.f1592j[this.f1593k];
        }

        /* renamed from: d */
        public float m3495d() {
            return this.f1590h;
        }

        /* renamed from: d */
        public void m3496d(float f) {
            this.f1589g = f;
            m3478o();
        }

        /* renamed from: d */
        public void m3497d(int i) {
            this.f1603u = i;
        }

        /* renamed from: e */
        public float m3498e() {
            return this.f1587e;
        }

        /* renamed from: e */
        public void m3499e(float f) {
            if (f != this.f1599q) {
                this.f1599q = f;
                m3478o();
            }
        }

        /* renamed from: f */
        public float m3500f() {
            return this.f1594l;
        }

        /* renamed from: g */
        public float m3501g() {
            return this.f1595m;
        }

        /* renamed from: h */
        public int m3502h() {
            return this.f1592j[this.f1593k];
        }

        /* renamed from: i */
        public float m3503i() {
            return this.f1588f;
        }

        /* renamed from: j */
        public double m3504j() {
            return this.f1600r;
        }

        /* renamed from: k */
        public float m3505k() {
            return this.f1596n;
        }

        /* renamed from: l */
        public void m3506l() {
            this.f1594l = this.f1587e;
            this.f1595m = this.f1588f;
            this.f1596n = this.f1589g;
        }

        /* renamed from: m */
        public void m3507m() {
            this.f1594l = BitmapDescriptorFactory.HUE_RED;
            this.f1595m = BitmapDescriptorFactory.HUE_RED;
            this.f1596n = BitmapDescriptorFactory.HUE_RED;
            m3490b((float) BitmapDescriptorFactory.HUE_RED);
            m3493c((float) BitmapDescriptorFactory.HUE_RED);
            m3496d((float) BitmapDescriptorFactory.HUE_RED);
        }
    }

    C0718r(Context context, View view) {
        this.f1616j = view;
        this.f1615i = context.getResources();
        this.f1613g = new C0717a(this.f1620n);
        this.f1613g.m3488a(f1609e);
        m3515a(1);
        m3509a();
    }

    /* renamed from: a */
    private int m3508a(float f, int i, int i2) {
        int intValue = Integer.valueOf(i).intValue();
        int i3 = (intValue >> 24) & 255;
        int i4 = (intValue >> 16) & 255;
        int i5 = (intValue >> 8) & 255;
        intValue &= 255;
        int intValue2 = Integer.valueOf(i2).intValue();
        return (intValue + ((int) (((float) ((intValue2 & 255) - intValue)) * f))) | ((((i3 + ((int) (((float) (((intValue2 >> 24) & 255) - i3)) * f))) << 24) | ((i4 + ((int) (((float) (((intValue2 >> 16) & 255) - i4)) * f))) << 16)) | ((((int) (((float) (((intValue2 >> 8) & 255) - i5)) * f)) + i5) << 8));
    }

    /* renamed from: a */
    private void m3509a() {
        final C0717a c0717a = this.f1613g;
        Animation c07141 = new Animation(this) {
            /* renamed from: b */
            final /* synthetic */ C0718r f1579b;

            public void applyTransformation(float f, Transformation transformation) {
                if (this.f1579b.f1611c) {
                    this.f1579b.m3519b(f, c0717a);
                    return;
                }
                float a = this.f1579b.m3511a(c0717a);
                float g = c0717a.m3501g();
                float f2 = c0717a.m3500f();
                float k = c0717a.m3505k();
                this.f1579b.m3514a(f, c0717a);
                if (f <= 0.5f) {
                    float f3 = 0.8f - a;
                    c0717a.m3490b(f2 + (C0718r.f1607a.getInterpolation(f / 0.5f) * f3));
                }
                if (f > 0.5f) {
                    c0717a.m3493c(((0.8f - a) * C0718r.f1607a.getInterpolation((f - 0.5f) / 0.5f)) + g);
                }
                c0717a.m3496d((0.25f * f) + k);
                this.f1579b.m3521c((216.0f * f) + (1080.0f * (this.f1579b.f1610b / 5.0f)));
            }
        };
        c07141.setRepeatCount(-1);
        c07141.setRepeatMode(1);
        c07141.setInterpolator(f1608d);
        c07141.setAnimationListener(new AnimationListener(this) {
            /* renamed from: b */
            final /* synthetic */ C0718r f1581b;

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
                c0717a.m3506l();
                c0717a.m3489b();
                c0717a.m3490b(c0717a.m3503i());
                if (this.f1581b.f1611c) {
                    this.f1581b.f1611c = false;
                    animation.setDuration(1332);
                    c0717a.m3487a(false);
                    return;
                }
                this.f1581b.f1610b = (this.f1581b.f1610b + 1.0f) % 5.0f;
            }

            public void onAnimationStart(Animation animation) {
                this.f1581b.f1610b = BitmapDescriptorFactory.HUE_RED;
            }
        });
        this.f1617k = c07141;
    }

    /* renamed from: a */
    private void m3510a(double d, double d2, double d3, double d4, float f, float f2) {
        C0717a c0717a = this.f1613g;
        float f3 = this.f1615i.getDisplayMetrics().density;
        this.f1618l = ((double) f3) * d;
        this.f1619m = ((double) f3) * d2;
        c0717a.m3481a(((float) d4) * f3);
        c0717a.m3480a(((double) f3) * d3);
        c0717a.m3494c(0);
        c0717a.m3482a(f * f3, f3 * f2);
        c0717a.m3484a((int) this.f1618l, (int) this.f1619m);
    }

    /* renamed from: a */
    float m3511a(C0717a c0717a) {
        return (float) Math.toRadians(((double) c0717a.m3495d()) / (6.283185307179586d * c0717a.m3504j()));
    }

    /* renamed from: a */
    public void m3512a(float f) {
        this.f1613g.m3499e(f);
    }

    /* renamed from: a */
    public void m3513a(float f, float f2) {
        this.f1613g.m3490b(f);
        this.f1613g.m3493c(f2);
    }

    /* renamed from: a */
    void m3514a(float f, C0717a c0717a) {
        if (f > 0.75f) {
            c0717a.m3491b(m3508a((f - 0.75f) / 0.25f, c0717a.m3502h(), c0717a.m3479a()));
        }
    }

    /* renamed from: a */
    public void m3515a(int i) {
        if (i == 0) {
            m3510a(56.0d, 56.0d, 12.5d, 3.0d, 12.0f, 6.0f);
        } else {
            m3510a(40.0d, 40.0d, 8.75d, 2.5d, 10.0f, 5.0f);
        }
    }

    /* renamed from: a */
    public void m3516a(boolean z) {
        this.f1613g.m3487a(z);
    }

    /* renamed from: a */
    public void m3517a(int... iArr) {
        this.f1613g.m3488a(iArr);
        this.f1613g.m3494c(0);
    }

    /* renamed from: b */
    public void m3518b(float f) {
        this.f1613g.m3496d(f);
    }

    /* renamed from: b */
    void m3519b(float f, C0717a c0717a) {
        m3514a(f, c0717a);
        float floor = (float) (Math.floor((double) (c0717a.m3505k() / 0.8f)) + 1.0d);
        float a = m3511a(c0717a);
        c0717a.m3490b((((c0717a.m3501g() - a) - c0717a.m3500f()) * f) + c0717a.m3500f());
        c0717a.m3493c(c0717a.m3501g());
        c0717a.m3496d(((floor - c0717a.m3505k()) * f) + c0717a.m3505k());
    }

    /* renamed from: b */
    public void m3520b(int i) {
        this.f1613g.m3483a(i);
    }

    /* renamed from: c */
    void m3521c(float f) {
        this.f1614h = f;
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int save = canvas.save();
        canvas.rotate(this.f1614h, bounds.exactCenterX(), bounds.exactCenterY());
        this.f1613g.m3485a(canvas, bounds);
        canvas.restoreToCount(save);
    }

    public int getAlpha() {
        return this.f1613g.m3492c();
    }

    public int getIntrinsicHeight() {
        return (int) this.f1619m;
    }

    public int getIntrinsicWidth() {
        return (int) this.f1618l;
    }

    public int getOpacity() {
        return -3;
    }

    public boolean isRunning() {
        ArrayList arrayList = this.f1612f;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Animation animation = (Animation) arrayList.get(i);
            if (animation.hasStarted() && !animation.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    public void setAlpha(int i) {
        this.f1613g.m3497d(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f1613g.m3486a(colorFilter);
    }

    public void start() {
        this.f1617k.reset();
        this.f1613g.m3506l();
        if (this.f1613g.m3503i() != this.f1613g.m3498e()) {
            this.f1611c = true;
            this.f1617k.setDuration(666);
            this.f1616j.startAnimation(this.f1617k);
            return;
        }
        this.f1613g.m3494c(0);
        this.f1613g.m3507m();
        this.f1617k.setDuration(1332);
        this.f1616j.startAnimation(this.f1617k);
    }

    public void stop() {
        this.f1616j.clearAnimation();
        m3521c(BitmapDescriptorFactory.HUE_RED);
        this.f1613g.m3487a(false);
        this.f1613g.m3494c(0);
        this.f1613g.m3507m();
    }
}
