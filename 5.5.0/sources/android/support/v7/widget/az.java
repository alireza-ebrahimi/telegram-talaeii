package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v7.p026b.C0822a.C0818a;
import android.support.v7.p026b.C0822a.C0819b;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@TargetApi(9)
class az extends Drawable {
    /* renamed from: a */
    static final double f2957a = Math.cos(Math.toRadians(45.0d));
    /* renamed from: c */
    static C0995a f2958c;
    /* renamed from: b */
    final int f2959b;
    /* renamed from: d */
    Paint f2960d;
    /* renamed from: e */
    Paint f2961e;
    /* renamed from: f */
    Paint f2962f;
    /* renamed from: g */
    final RectF f2963g;
    /* renamed from: h */
    float f2964h;
    /* renamed from: i */
    Path f2965i;
    /* renamed from: j */
    float f2966j;
    /* renamed from: k */
    float f2967k;
    /* renamed from: l */
    float f2968l;
    /* renamed from: m */
    float f2969m;
    /* renamed from: n */
    private ColorStateList f2970n;
    /* renamed from: o */
    private boolean f2971o = true;
    /* renamed from: p */
    private final int f2972p;
    /* renamed from: q */
    private final int f2973q;
    /* renamed from: r */
    private boolean f2974r = true;
    /* renamed from: s */
    private boolean f2975s = false;

    /* renamed from: android.support.v7.widget.az$a */
    interface C0995a {
        /* renamed from: a */
        void mo902a(Canvas canvas, RectF rectF, float f, Paint paint);
    }

    az(Resources resources, ColorStateList colorStateList, float f, float f2, float f3) {
        this.f2972p = resources.getColor(C0818a.cardview_shadow_start_color);
        this.f2973q = resources.getColor(C0818a.cardview_shadow_end_color);
        this.f2959b = resources.getDimensionPixelSize(C0819b.cardview_compat_inset_shadow);
        this.f2960d = new Paint(5);
        m5583b(colorStateList);
        this.f2961e = new Paint(5);
        this.f2961e.setStyle(Style.FILL);
        this.f2964h = (float) ((int) (0.5f + f));
        this.f2963g = new RectF();
        this.f2962f = new Paint(this.f2961e);
        this.f2962f.setAntiAlias(false);
        m5589a(f2, f3);
    }

    /* renamed from: a */
    static float m5580a(float f, float f2, boolean z) {
        return z ? (float) (((double) (1.5f * f)) + ((1.0d - f2957a) * ((double) f2))) : 1.5f * f;
    }

    /* renamed from: a */
    private void m5581a(Canvas canvas) {
        float f = (-this.f2964h) - this.f2968l;
        float f2 = (this.f2964h + ((float) this.f2959b)) + (this.f2969m / 2.0f);
        Object obj = this.f2963g.width() - (2.0f * f2) > BitmapDescriptorFactory.HUE_RED ? 1 : null;
        Object obj2 = this.f2963g.height() - (2.0f * f2) > BitmapDescriptorFactory.HUE_RED ? 1 : null;
        int save = canvas.save();
        canvas.translate(this.f2963g.left + f2, this.f2963g.top + f2);
        canvas.drawPath(this.f2965i, this.f2961e);
        if (obj != null) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f2963g.width() - (2.0f * f2), -this.f2964h, this.f2962f);
        }
        canvas.restoreToCount(save);
        save = canvas.save();
        canvas.translate(this.f2963g.right - f2, this.f2963g.bottom - f2);
        canvas.rotate(180.0f);
        canvas.drawPath(this.f2965i, this.f2961e);
        if (obj != null) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f2963g.width() - (2.0f * f2), this.f2968l + (-this.f2964h), this.f2962f);
        }
        canvas.restoreToCount(save);
        int save2 = canvas.save();
        canvas.translate(this.f2963g.left + f2, this.f2963g.bottom - f2);
        canvas.rotate(270.0f);
        canvas.drawPath(this.f2965i, this.f2961e);
        if (obj2 != null) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f2963g.height() - (2.0f * f2), -this.f2964h, this.f2962f);
        }
        canvas.restoreToCount(save2);
        save2 = canvas.save();
        canvas.translate(this.f2963g.right - f2, this.f2963g.top + f2);
        canvas.rotate(90.0f);
        canvas.drawPath(this.f2965i, this.f2961e);
        if (obj2 != null) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f2963g.height() - (2.0f * f2), -this.f2964h, this.f2962f);
        }
        canvas.restoreToCount(save2);
    }

    /* renamed from: b */
    static float m5582b(float f, float f2, boolean z) {
        return z ? (float) (((double) f) + ((1.0d - f2957a) * ((double) f2))) : f;
    }

    /* renamed from: b */
    private void m5583b(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.f2970n = colorStateList;
        this.f2960d.setColor(this.f2970n.getColorForState(getState(), this.f2970n.getDefaultColor()));
    }

    /* renamed from: b */
    private void m5584b(Rect rect) {
        float f = this.f2967k * 1.5f;
        this.f2963g.set(((float) rect.left) + this.f2967k, ((float) rect.top) + f, ((float) rect.right) - this.f2967k, ((float) rect.bottom) - f);
        m5586g();
    }

    /* renamed from: d */
    private int m5585d(float f) {
        int i = (int) (0.5f + f);
        return i % 2 == 1 ? i - 1 : i;
    }

    /* renamed from: g */
    private void m5586g() {
        RectF rectF = new RectF(-this.f2964h, -this.f2964h, this.f2964h, this.f2964h);
        RectF rectF2 = new RectF(rectF);
        rectF2.inset(-this.f2968l, -this.f2968l);
        if (this.f2965i == null) {
            this.f2965i = new Path();
        } else {
            this.f2965i.reset();
        }
        this.f2965i.setFillType(FillType.EVEN_ODD);
        this.f2965i.moveTo(-this.f2964h, BitmapDescriptorFactory.HUE_RED);
        this.f2965i.rLineTo(-this.f2968l, BitmapDescriptorFactory.HUE_RED);
        this.f2965i.arcTo(rectF2, 180.0f, 90.0f, false);
        this.f2965i.arcTo(rectF, 270.0f, -90.0f, false);
        this.f2965i.close();
        float f = this.f2964h / (this.f2964h + this.f2968l);
        float f2 = this.f2964h + this.f2968l;
        int[] iArr = new int[]{this.f2972p, this.f2972p, this.f2973q};
        float[] fArr = new float[]{BitmapDescriptorFactory.HUE_RED, f, 1.0f};
        this.f2961e.setShader(new RadialGradient(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, f2, iArr, fArr, TileMode.CLAMP));
        float f3 = (-this.f2964h) - this.f2968l;
        int[] iArr2 = new int[]{this.f2972p, this.f2972p, this.f2973q};
        this.f2962f.setShader(new LinearGradient(BitmapDescriptorFactory.HUE_RED, (-this.f2964h) + this.f2968l, BitmapDescriptorFactory.HUE_RED, f3, iArr2, new float[]{BitmapDescriptorFactory.HUE_RED, 0.5f, 1.0f}, TileMode.CLAMP));
        this.f2962f.setAntiAlias(false);
    }

    /* renamed from: a */
    float m5587a() {
        return this.f2964h;
    }

    /* renamed from: a */
    void m5588a(float f) {
        if (f < BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("Invalid radius " + f + ". Must be >= 0");
        }
        float f2 = (float) ((int) (0.5f + f));
        if (this.f2964h != f2) {
            this.f2964h = f2;
            this.f2971o = true;
            invalidateSelf();
        }
    }

    /* renamed from: a */
    void m5589a(float f, float f2) {
        if (f < BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("Invalid shadow size " + f + ". Must be >= 0");
        } else if (f2 < BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("Invalid max shadow size " + f2 + ". Must be >= 0");
        } else {
            float d = (float) m5585d(f);
            float d2 = (float) m5585d(f2);
            if (d > d2) {
                if (!this.f2975s) {
                    this.f2975s = true;
                }
                d = d2;
            }
            if (this.f2969m != d || this.f2967k != d2) {
                this.f2969m = d;
                this.f2967k = d2;
                this.f2968l = (float) ((int) (((d * 1.5f) + ((float) this.f2959b)) + 0.5f));
                this.f2966j = ((float) this.f2959b) + d2;
                this.f2971o = true;
                invalidateSelf();
            }
        }
    }

    /* renamed from: a */
    void m5590a(ColorStateList colorStateList) {
        m5583b(colorStateList);
        invalidateSelf();
    }

    /* renamed from: a */
    void m5591a(Rect rect) {
        getPadding(rect);
    }

    /* renamed from: a */
    public void m5592a(boolean z) {
        this.f2974r = z;
        invalidateSelf();
    }

    /* renamed from: b */
    float m5593b() {
        return this.f2969m;
    }

    /* renamed from: b */
    void m5594b(float f) {
        m5589a(f, this.f2967k);
    }

    /* renamed from: c */
    float m5595c() {
        return this.f2967k;
    }

    /* renamed from: c */
    void m5596c(float f) {
        m5589a(this.f2969m, f);
    }

    /* renamed from: d */
    float m5597d() {
        return (Math.max(this.f2967k, (this.f2964h + ((float) this.f2959b)) + (this.f2967k / 2.0f)) * 2.0f) + ((this.f2967k + ((float) this.f2959b)) * 2.0f);
    }

    public void draw(Canvas canvas) {
        if (this.f2971o) {
            m5584b(getBounds());
            this.f2971o = false;
        }
        canvas.translate(BitmapDescriptorFactory.HUE_RED, this.f2969m / 2.0f);
        m5581a(canvas);
        canvas.translate(BitmapDescriptorFactory.HUE_RED, (-this.f2969m) / 2.0f);
        f2958c.mo902a(canvas, this.f2963g, this.f2964h, this.f2960d);
    }

    /* renamed from: e */
    float m5598e() {
        return (Math.max(this.f2967k, (this.f2964h + ((float) this.f2959b)) + ((this.f2967k * 1.5f) / 2.0f)) * 2.0f) + (((this.f2967k * 1.5f) + ((float) this.f2959b)) * 2.0f);
    }

    /* renamed from: f */
    ColorStateList m5599f() {
        return this.f2970n;
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil((double) m5580a(this.f2967k, this.f2964h, this.f2974r));
        int ceil2 = (int) Math.ceil((double) m5582b(this.f2967k, this.f2964h, this.f2974r));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    public boolean isStateful() {
        return (this.f2970n != null && this.f2970n.isStateful()) || super.isStateful();
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.f2971o = true;
    }

    protected boolean onStateChange(int[] iArr) {
        int colorForState = this.f2970n.getColorForState(iArr, this.f2970n.getDefaultColor());
        if (this.f2960d.getColor() == colorForState) {
            return false;
        }
        this.f2960d.setColor(colorForState);
        this.f2971o = true;
        invalidateSelf();
        return true;
    }

    public void setAlpha(int i) {
        this.f2960d.setAlpha(i);
        this.f2961e.setAlpha(i);
        this.f2962f.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f2960d.setColorFilter(colorFilter);
    }
}
