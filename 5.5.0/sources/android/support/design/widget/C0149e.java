package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.support.v4.p021e.C0456e;
import android.support.v4.view.C0625f;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.widget.bk;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.design.widget.e */
final class C0149e {
    /* renamed from: a */
    private static final boolean f472a = (VERSION.SDK_INT < 18);
    /* renamed from: b */
    private static final Paint f473b = null;
    /* renamed from: A */
    private boolean f474A;
    /* renamed from: B */
    private Bitmap f475B;
    /* renamed from: C */
    private Paint f476C;
    /* renamed from: D */
    private float f477D;
    /* renamed from: E */
    private float f478E;
    /* renamed from: F */
    private float f479F;
    /* renamed from: G */
    private float f480G;
    /* renamed from: H */
    private int[] f481H;
    /* renamed from: I */
    private boolean f482I;
    /* renamed from: J */
    private final TextPaint f483J;
    /* renamed from: K */
    private Interpolator f484K;
    /* renamed from: L */
    private Interpolator f485L;
    /* renamed from: M */
    private float f486M;
    /* renamed from: N */
    private float f487N;
    /* renamed from: O */
    private float f488O;
    /* renamed from: P */
    private int f489P;
    /* renamed from: Q */
    private float f490Q;
    /* renamed from: R */
    private float f491R;
    /* renamed from: S */
    private float f492S;
    /* renamed from: T */
    private int f493T;
    /* renamed from: c */
    private final View f494c;
    /* renamed from: d */
    private boolean f495d;
    /* renamed from: e */
    private float f496e;
    /* renamed from: f */
    private final Rect f497f;
    /* renamed from: g */
    private final Rect f498g;
    /* renamed from: h */
    private final RectF f499h;
    /* renamed from: i */
    private int f500i = 16;
    /* renamed from: j */
    private int f501j = 16;
    /* renamed from: k */
    private float f502k = 15.0f;
    /* renamed from: l */
    private float f503l = 15.0f;
    /* renamed from: m */
    private ColorStateList f504m;
    /* renamed from: n */
    private ColorStateList f505n;
    /* renamed from: o */
    private float f506o;
    /* renamed from: p */
    private float f507p;
    /* renamed from: q */
    private float f508q;
    /* renamed from: r */
    private float f509r;
    /* renamed from: s */
    private float f510s;
    /* renamed from: t */
    private float f511t;
    /* renamed from: u */
    private Typeface f512u;
    /* renamed from: v */
    private Typeface f513v;
    /* renamed from: w */
    private Typeface f514w;
    /* renamed from: x */
    private CharSequence f515x;
    /* renamed from: y */
    private CharSequence f516y;
    /* renamed from: z */
    private boolean f517z;

    static {
        if (f473b != null) {
            f473b.setAntiAlias(true);
            f473b.setColor(-65281);
        }
    }

    public C0149e(View view) {
        this.f494c = view;
        this.f483J = new TextPaint(129);
        this.f498g = new Rect();
        this.f497f = new Rect();
        this.f499h = new RectF();
    }

    /* renamed from: a */
    private static float m694a(float f, float f2, float f3, Interpolator interpolator) {
        if (interpolator != null) {
            f3 = interpolator.getInterpolation(f3);
        }
        return C0126a.m647a(f, f2, f3);
    }

    /* renamed from: a */
    private static int m695a(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb((int) ((((float) Color.alpha(i)) * f2) + (((float) Color.alpha(i2)) * f)), (int) ((((float) Color.red(i)) * f2) + (((float) Color.red(i2)) * f)), (int) ((((float) Color.green(i)) * f2) + (((float) Color.green(i2)) * f)), (int) ((f2 * ((float) Color.blue(i))) + (((float) Color.blue(i2)) * f)));
    }

    /* renamed from: a */
    private static boolean m696a(float f, float f2) {
        return Math.abs(f - f2) < 0.001f;
    }

    /* renamed from: a */
    private static boolean m697a(Rect rect, int i, int i2, int i3, int i4) {
        return rect.left == i && rect.top == i2 && rect.right == i3 && rect.bottom == i4;
    }

    /* renamed from: b */
    private boolean m698b(CharSequence charSequence) {
        int i = 1;
        if (ah.m2812g(this.f494c) != 1) {
            i = 0;
        }
        return (i != 0 ? C0456e.f1211d : C0456e.f1210c).mo320a(charSequence, 0, charSequence.length());
    }

    /* renamed from: c */
    private void m699c(float f) {
        m701d(f);
        this.f510s = C0149e.m694a(this.f508q, this.f509r, f, this.f484K);
        this.f511t = C0149e.m694a(this.f506o, this.f507p, f, this.f484K);
        m702e(C0149e.m694a(this.f502k, this.f503l, f, this.f485L));
        if (this.f505n != this.f504m) {
            this.f483J.setColor(C0149e.m695a(m705j(), m706k(), f));
        } else {
            this.f483J.setColor(m706k());
        }
        this.f483J.setShadowLayer(C0149e.m694a(this.f490Q, this.f486M, f, null), C0149e.m694a(this.f491R, this.f487N, f, null), C0149e.m694a(this.f492S, this.f488O, f, null), C0149e.m695a(this.f493T, this.f489P, f));
        ah.m2799c(this.f494c);
    }

    /* renamed from: d */
    private Typeface m700d(int i) {
        TypedArray obtainStyledAttributes = this.f494c.getContext().obtainStyledAttributes(i, new int[]{16843692});
        try {
            String string = obtainStyledAttributes.getString(0);
            if (string != null) {
                Typeface create = Typeface.create(string, 0);
                return create;
            }
            obtainStyledAttributes.recycle();
            return null;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: d */
    private void m701d(float f) {
        this.f499h.left = C0149e.m694a((float) this.f497f.left, (float) this.f498g.left, f, this.f484K);
        this.f499h.top = C0149e.m694a(this.f506o, this.f507p, f, this.f484K);
        this.f499h.right = C0149e.m694a((float) this.f497f.right, (float) this.f498g.right, f, this.f484K);
        this.f499h.bottom = C0149e.m694a((float) this.f497f.bottom, (float) this.f498g.bottom, f, this.f484K);
    }

    /* renamed from: e */
    private void m702e(float f) {
        m703f(f);
        boolean z = f472a && this.f479F != 1.0f;
        this.f474A = z;
        if (this.f474A) {
            m708m();
        }
        ah.m2799c(this.f494c);
    }

    /* renamed from: f */
    private void m703f(float f) {
        boolean z = true;
        if (this.f515x != null) {
            float f2;
            boolean z2;
            float width = (float) this.f498g.width();
            float width2 = (float) this.f497f.width();
            if (C0149e.m696a(f, this.f503l)) {
                f2 = this.f503l;
                this.f479F = 1.0f;
                if (this.f514w != this.f512u) {
                    this.f514w = this.f512u;
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                f2 = this.f502k;
                if (this.f514w != this.f513v) {
                    this.f514w = this.f513v;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (C0149e.m696a(f, this.f502k)) {
                    this.f479F = 1.0f;
                } else {
                    this.f479F = f / this.f502k;
                }
                float f3 = this.f503l / this.f502k;
                width = width2 * f3 > width ? Math.min(width / f3, width2) : width2;
            }
            if (width > BitmapDescriptorFactory.HUE_RED) {
                z2 = this.f480G != f2 || this.f482I || z2;
                this.f480G = f2;
                this.f482I = false;
            }
            if (this.f516y == null || r0) {
                this.f483J.setTextSize(this.f480G);
                this.f483J.setTypeface(this.f514w);
                TextPaint textPaint = this.f483J;
                if (this.f479F == 1.0f) {
                    z = false;
                }
                textPaint.setLinearText(z);
                CharSequence ellipsize = TextUtils.ellipsize(this.f515x, this.f483J, width, TruncateAt.END);
                if (!TextUtils.equals(ellipsize, this.f516y)) {
                    this.f516y = ellipsize;
                    this.f517z = m698b(this.f516y);
                }
            }
        }
    }

    /* renamed from: i */
    private void m704i() {
        m699c(this.f496e);
    }

    /* renamed from: j */
    private int m705j() {
        return this.f481H != null ? this.f504m.getColorForState(this.f481H, 0) : this.f504m.getDefaultColor();
    }

    /* renamed from: k */
    private int m706k() {
        return this.f481H != null ? this.f505n.getColorForState(this.f481H, 0) : this.f505n.getDefaultColor();
    }

    /* renamed from: l */
    private void m707l() {
        int i = 1;
        float f = BitmapDescriptorFactory.HUE_RED;
        float f2 = this.f480G;
        m703f(this.f503l);
        float measureText = this.f516y != null ? this.f483J.measureText(this.f516y, 0, this.f516y.length()) : BitmapDescriptorFactory.HUE_RED;
        int a = C0625f.m3120a(this.f501j, this.f517z ? 1 : 0);
        switch (a & 112) {
            case 48:
                this.f507p = ((float) this.f498g.top) - this.f483J.ascent();
                break;
            case 80:
                this.f507p = (float) this.f498g.bottom;
                break;
            default:
                this.f507p = (((this.f483J.descent() - this.f483J.ascent()) / 2.0f) - this.f483J.descent()) + ((float) this.f498g.centerY());
                break;
        }
        switch (a & 8388615) {
            case 1:
                this.f509r = ((float) this.f498g.centerX()) - (measureText / 2.0f);
                break;
            case 5:
                this.f509r = ((float) this.f498g.right) - measureText;
                break;
            default:
                this.f509r = (float) this.f498g.left;
                break;
        }
        m703f(this.f502k);
        if (this.f516y != null) {
            f = this.f483J.measureText(this.f516y, 0, this.f516y.length());
        }
        int i2 = this.f500i;
        if (!this.f517z) {
            i = 0;
        }
        i2 = C0625f.m3120a(i2, i);
        switch (i2 & 112) {
            case 48:
                this.f506o = ((float) this.f497f.top) - this.f483J.ascent();
                break;
            case 80:
                this.f506o = (float) this.f497f.bottom;
                break;
            default:
                this.f506o = (((this.f483J.descent() - this.f483J.ascent()) / 2.0f) - this.f483J.descent()) + ((float) this.f497f.centerY());
                break;
        }
        switch (i2 & 8388615) {
            case 1:
                this.f508q = ((float) this.f497f.centerX()) - (f / 2.0f);
                break;
            case 5:
                this.f508q = ((float) this.f497f.right) - f;
                break;
            default:
                this.f508q = (float) this.f497f.left;
                break;
        }
        m709n();
        m702e(f2);
    }

    /* renamed from: m */
    private void m708m() {
        if (this.f475B == null && !this.f497f.isEmpty() && !TextUtils.isEmpty(this.f516y)) {
            m699c((float) BitmapDescriptorFactory.HUE_RED);
            this.f477D = this.f483J.ascent();
            this.f478E = this.f483J.descent();
            int round = Math.round(this.f483J.measureText(this.f516y, 0, this.f516y.length()));
            int round2 = Math.round(this.f478E - this.f477D);
            if (round > 0 && round2 > 0) {
                this.f475B = Bitmap.createBitmap(round, round2, Config.ARGB_8888);
                new Canvas(this.f475B).drawText(this.f516y, 0, this.f516y.length(), BitmapDescriptorFactory.HUE_RED, ((float) round2) - this.f483J.descent(), this.f483J);
                if (this.f476C == null) {
                    this.f476C = new Paint(3);
                }
            }
        }
    }

    /* renamed from: n */
    private void m709n() {
        if (this.f475B != null) {
            this.f475B.recycle();
            this.f475B = null;
        }
    }

    /* renamed from: a */
    void m710a() {
        boolean z = this.f498g.width() > 0 && this.f498g.height() > 0 && this.f497f.width() > 0 && this.f497f.height() > 0;
        this.f495d = z;
    }

    /* renamed from: a */
    void m711a(float f) {
        if (this.f502k != f) {
            this.f502k = f;
            m730f();
        }
    }

    /* renamed from: a */
    void m712a(int i) {
        if (this.f500i != i) {
            this.f500i = i;
            m730f();
        }
    }

    /* renamed from: a */
    void m713a(int i, int i2, int i3, int i4) {
        if (!C0149e.m697a(this.f497f, i, i2, i3, i4)) {
            this.f497f.set(i, i2, i3, i4);
            this.f482I = true;
            m710a();
        }
    }

    /* renamed from: a */
    void m714a(ColorStateList colorStateList) {
        if (this.f505n != colorStateList) {
            this.f505n = colorStateList;
            m730f();
        }
    }

    /* renamed from: a */
    public void m715a(Canvas canvas) {
        int save = canvas.save();
        if (this.f516y != null && this.f495d) {
            float f;
            float f2 = this.f510s;
            float f3 = this.f511t;
            int i = (!this.f474A || this.f475B == null) ? 0 : 1;
            float f4;
            if (i != 0) {
                f = this.f477D * this.f479F;
                f4 = this.f478E * this.f479F;
            } else {
                f = this.f483J.ascent() * this.f479F;
                f4 = this.f483J.descent() * this.f479F;
            }
            if (i != 0) {
                f3 += f;
            }
            if (this.f479F != 1.0f) {
                canvas.scale(this.f479F, this.f479F, f2, f3);
            }
            if (i != 0) {
                canvas.drawBitmap(this.f475B, f2, f3, this.f476C);
            } else {
                canvas.drawText(this.f516y, 0, this.f516y.length(), f2, f3, this.f483J);
            }
        }
        canvas.restoreToCount(save);
    }

    /* renamed from: a */
    void m716a(Typeface typeface) {
        this.f513v = typeface;
        this.f512u = typeface;
        m730f();
    }

    /* renamed from: a */
    void m717a(Interpolator interpolator) {
        this.f485L = interpolator;
        m730f();
    }

    /* renamed from: a */
    void m718a(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.f515x)) {
            this.f515x = charSequence;
            this.f516y = null;
            m709n();
            m730f();
        }
    }

    /* renamed from: a */
    final boolean m719a(int[] iArr) {
        this.f481H = iArr;
        if (!m727c()) {
            return false;
        }
        m730f();
        return true;
    }

    /* renamed from: b */
    Typeface m720b() {
        return this.f512u != null ? this.f512u : Typeface.DEFAULT;
    }

    /* renamed from: b */
    void m721b(float f) {
        float a = C0168n.m808a(f, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
        if (a != this.f496e) {
            this.f496e = a;
            m704i();
        }
    }

    /* renamed from: b */
    void m722b(int i) {
        if (this.f501j != i) {
            this.f501j = i;
            m730f();
        }
    }

    /* renamed from: b */
    void m723b(int i, int i2, int i3, int i4) {
        if (!C0149e.m697a(this.f498g, i, i2, i3, i4)) {
            this.f498g.set(i, i2, i3, i4);
            this.f482I = true;
            m710a();
        }
    }

    /* renamed from: b */
    void m724b(ColorStateList colorStateList) {
        if (this.f504m != colorStateList) {
            this.f504m = colorStateList;
            m730f();
        }
    }

    /* renamed from: b */
    void m725b(Interpolator interpolator) {
        this.f484K = interpolator;
        m730f();
    }

    /* renamed from: c */
    void m726c(int i) {
        bk a = bk.m5652a(this.f494c.getContext(), i, C0747j.TextAppearance);
        if (a.m5671g(C0747j.TextAppearance_android_textColor)) {
            this.f505n = a.m5667e(C0747j.TextAppearance_android_textColor);
        }
        if (a.m5671g(C0747j.TextAppearance_android_textSize)) {
            this.f503l = (float) a.m5666e(C0747j.TextAppearance_android_textSize, (int) this.f503l);
        }
        this.f489P = a.m5656a(C0747j.TextAppearance_android_shadowColor, 0);
        this.f487N = a.m5655a(C0747j.TextAppearance_android_shadowDx, (float) BitmapDescriptorFactory.HUE_RED);
        this.f488O = a.m5655a(C0747j.TextAppearance_android_shadowDy, (float) BitmapDescriptorFactory.HUE_RED);
        this.f486M = a.m5655a(C0747j.TextAppearance_android_shadowRadius, (float) BitmapDescriptorFactory.HUE_RED);
        a.m5658a();
        if (VERSION.SDK_INT >= 16) {
            this.f512u = m700d(i);
        }
        m730f();
    }

    /* renamed from: c */
    final boolean m727c() {
        return (this.f505n != null && this.f505n.isStateful()) || (this.f504m != null && this.f504m.isStateful());
    }

    /* renamed from: d */
    float m728d() {
        return this.f496e;
    }

    /* renamed from: e */
    float m729e() {
        return this.f503l;
    }

    /* renamed from: f */
    public void m730f() {
        if (this.f494c.getHeight() > 0 && this.f494c.getWidth() > 0) {
            m707l();
            m704i();
        }
    }

    /* renamed from: g */
    CharSequence m731g() {
        return this.f515x;
    }

    /* renamed from: h */
    ColorStateList m732h() {
        return this.f505n;
    }
}
