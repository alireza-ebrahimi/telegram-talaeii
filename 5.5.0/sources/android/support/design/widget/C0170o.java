package android.support.design.widget;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.support.v7.p015d.p016a.C0169a;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.design.widget.o */
class C0170o extends C0169a {
    /* renamed from: a */
    static final double f569a = Math.cos(Math.toRadians(45.0d));
    /* renamed from: b */
    final Paint f570b;
    /* renamed from: c */
    final Paint f571c;
    /* renamed from: d */
    final RectF f572d;
    /* renamed from: e */
    float f573e;
    /* renamed from: f */
    Path f574f;
    /* renamed from: g */
    float f575g;
    /* renamed from: h */
    float f576h;
    /* renamed from: i */
    float f577i;
    /* renamed from: j */
    float f578j;
    /* renamed from: k */
    private boolean f579k;
    /* renamed from: l */
    private final int f580l;
    /* renamed from: m */
    private final int f581m;
    /* renamed from: n */
    private final int f582n;
    /* renamed from: o */
    private boolean f583o;
    /* renamed from: p */
    private float f584p;
    /* renamed from: q */
    private boolean f585q;

    /* renamed from: a */
    public static float m812a(float f, float f2, boolean z) {
        return z ? (float) (((double) (1.5f * f)) + ((1.0d - f569a) * ((double) f2))) : 1.5f * f;
    }

    /* renamed from: a */
    private void m813a(Canvas canvas) {
        int save = canvas.save();
        canvas.rotate(this.f584p, this.f572d.centerX(), this.f572d.centerY());
        float f = (-this.f573e) - this.f577i;
        float f2 = this.f573e;
        Object obj = this.f572d.width() - (2.0f * f2) > BitmapDescriptorFactory.HUE_RED ? 1 : null;
        Object obj2 = this.f572d.height() - (2.0f * f2) > BitmapDescriptorFactory.HUE_RED ? 1 : null;
        float f3 = f2 / ((this.f578j - (this.f578j * 0.5f)) + f2);
        float f4 = f2 / ((this.f578j - (this.f578j * 0.25f)) + f2);
        float f5 = f2 / (f2 + (this.f578j - (this.f578j * 1.0f)));
        int save2 = canvas.save();
        canvas.translate(this.f572d.left + f2, this.f572d.top + f2);
        canvas.scale(f3, f4);
        canvas.drawPath(this.f574f, this.f570b);
        if (obj != null) {
            canvas.scale(1.0f / f3, 1.0f);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f572d.width() - (2.0f * f2), -this.f573e, this.f571c);
        }
        canvas.restoreToCount(save2);
        save2 = canvas.save();
        canvas.translate(this.f572d.right - f2, this.f572d.bottom - f2);
        canvas.scale(f3, f5);
        canvas.rotate(180.0f);
        canvas.drawPath(this.f574f, this.f570b);
        if (obj != null) {
            canvas.scale(1.0f / f3, 1.0f);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f572d.width() - (2.0f * f2), this.f577i + (-this.f573e), this.f571c);
        }
        canvas.restoreToCount(save2);
        int save3 = canvas.save();
        canvas.translate(this.f572d.left + f2, this.f572d.bottom - f2);
        canvas.scale(f3, f5);
        canvas.rotate(270.0f);
        canvas.drawPath(this.f574f, this.f570b);
        if (obj2 != null) {
            canvas.scale(1.0f / f5, 1.0f);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f572d.height() - (2.0f * f2), -this.f573e, this.f571c);
        }
        canvas.restoreToCount(save3);
        save3 = canvas.save();
        canvas.translate(this.f572d.right - f2, this.f572d.top + f2);
        canvas.scale(f3, f4);
        canvas.rotate(90.0f);
        canvas.drawPath(this.f574f, this.f570b);
        if (obj2 != null) {
            canvas.scale(1.0f / f4, 1.0f);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, f, this.f572d.height() - (2.0f * f2), -this.f573e, this.f571c);
        }
        canvas.restoreToCount(save3);
        canvas.restoreToCount(save);
    }

    /* renamed from: a */
    private void m814a(Rect rect) {
        float f = this.f576h * 1.5f;
        this.f572d.set(((float) rect.left) + this.f576h, ((float) rect.top) + f, ((float) rect.right) - this.f576h, ((float) rect.bottom) - f);
        m811b().setBounds((int) this.f572d.left, (int) this.f572d.top, (int) this.f572d.right, (int) this.f572d.bottom);
        m817c();
    }

    /* renamed from: b */
    public static float m815b(float f, float f2, boolean z) {
        return z ? (float) (((double) f) + ((1.0d - f569a) * ((double) f2))) : f;
    }

    /* renamed from: c */
    private static int m816c(float f) {
        int round = Math.round(f);
        return round % 2 == 1 ? round - 1 : round;
    }

    /* renamed from: c */
    private void m817c() {
        float f;
        RectF rectF = new RectF(-this.f573e, -this.f573e, this.f573e, this.f573e);
        RectF rectF2 = new RectF(rectF);
        rectF2.inset(-this.f577i, -this.f577i);
        if (this.f574f == null) {
            this.f574f = new Path();
        } else {
            this.f574f.reset();
        }
        this.f574f.setFillType(FillType.EVEN_ODD);
        this.f574f.moveTo(-this.f573e, BitmapDescriptorFactory.HUE_RED);
        this.f574f.rLineTo(-this.f577i, BitmapDescriptorFactory.HUE_RED);
        this.f574f.arcTo(rectF2, 180.0f, 90.0f, false);
        this.f574f.arcTo(rectF, 270.0f, -90.0f, false);
        this.f574f.close();
        float f2 = -rectF2.top;
        if (f2 > BitmapDescriptorFactory.HUE_RED) {
            f = this.f573e / f2;
            float f3 = f + ((1.0f - f) / 2.0f);
            int[] iArr = new int[]{0, this.f580l, this.f581m, this.f582n};
            float[] fArr = new float[]{BitmapDescriptorFactory.HUE_RED, f, f3, 1.0f};
            this.f570b.setShader(new RadialGradient(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, f2, iArr, fArr, TileMode.CLAMP));
        }
        Paint paint = this.f571c;
        f = rectF.top;
        float f4 = rectF2.top;
        int[] iArr2 = new int[]{this.f580l, this.f581m, this.f582n};
        paint.setShader(new LinearGradient(BitmapDescriptorFactory.HUE_RED, f, BitmapDescriptorFactory.HUE_RED, f4, iArr2, new float[]{BitmapDescriptorFactory.HUE_RED, 0.5f, 1.0f}, TileMode.CLAMP));
        this.f571c.setAntiAlias(false);
    }

    /* renamed from: a */
    public float m818a() {
        return this.f578j;
    }

    /* renamed from: a */
    final void m819a(float f) {
        if (this.f584p != f) {
            this.f584p = f;
            invalidateSelf();
        }
    }

    /* renamed from: a */
    void m820a(float f, float f2) {
        if (f < BitmapDescriptorFactory.HUE_RED || f2 < BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float c = (float) C0170o.m816c(f);
        float c2 = (float) C0170o.m816c(f2);
        if (c > c2) {
            if (!this.f585q) {
                this.f585q = true;
            }
            c = c2;
        }
        if (this.f578j != c || this.f576h != c2) {
            this.f578j = c;
            this.f576h = c2;
            this.f577i = (float) Math.round(c * 1.5f);
            this.f575g = c2;
            this.f579k = true;
            invalidateSelf();
        }
    }

    /* renamed from: b */
    public void m821b(float f) {
        m820a(f, this.f576h);
    }

    public void draw(Canvas canvas) {
        if (this.f579k) {
            m814a(getBounds());
            this.f579k = false;
        }
        m813a(canvas);
        super.draw(canvas);
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil((double) C0170o.m812a(this.f576h, this.f573e, this.f583o));
        int ceil2 = (int) Math.ceil((double) C0170o.m815b(this.f576h, this.f573e, this.f583o));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    protected void onBoundsChange(Rect rect) {
        this.f579k = true;
    }

    public void setAlpha(int i) {
        super.setAlpha(i);
        this.f570b.setAlpha(i);
        this.f571c.setAlpha(i);
    }
}
