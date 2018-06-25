package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.C0392a;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.design.widget.d */
class C0148d extends Drawable {
    /* renamed from: a */
    final Paint f460a;
    /* renamed from: b */
    final Rect f461b;
    /* renamed from: c */
    final RectF f462c;
    /* renamed from: d */
    float f463d;
    /* renamed from: e */
    private int f464e;
    /* renamed from: f */
    private int f465f;
    /* renamed from: g */
    private int f466g;
    /* renamed from: h */
    private int f467h;
    /* renamed from: i */
    private ColorStateList f468i;
    /* renamed from: j */
    private int f469j;
    /* renamed from: k */
    private boolean f470k;
    /* renamed from: l */
    private float f471l;

    /* renamed from: a */
    private Shader m691a() {
        Rect rect = this.f461b;
        copyBounds(rect);
        float height = this.f463d / ((float) rect.height());
        int[] iArr = new int[]{C0392a.m1825a(this.f464e, this.f469j), C0392a.m1825a(this.f465f, this.f469j), C0392a.m1825a(C0392a.m1834c(this.f465f, 0), this.f469j), C0392a.m1825a(C0392a.m1834c(this.f467h, 0), this.f469j), C0392a.m1825a(this.f467h, this.f469j), C0392a.m1825a(this.f466g, this.f469j)};
        float[] fArr = new float[]{BitmapDescriptorFactory.HUE_RED, height, 0.5f, 0.5f, 1.0f - height, 1.0f};
        return new LinearGradient(BitmapDescriptorFactory.HUE_RED, (float) rect.top, BitmapDescriptorFactory.HUE_RED, (float) rect.bottom, iArr, fArr, TileMode.CLAMP);
    }

    /* renamed from: a */
    final void m692a(float f) {
        if (f != this.f471l) {
            this.f471l = f;
            invalidateSelf();
        }
    }

    /* renamed from: a */
    void m693a(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.f469j = colorStateList.getColorForState(getState(), this.f469j);
        }
        this.f468i = colorStateList;
        this.f470k = true;
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        if (this.f470k) {
            this.f460a.setShader(m691a());
            this.f470k = false;
        }
        float strokeWidth = this.f460a.getStrokeWidth() / 2.0f;
        RectF rectF = this.f462c;
        copyBounds(this.f461b);
        rectF.set(this.f461b);
        rectF.left += strokeWidth;
        rectF.top += strokeWidth;
        rectF.right -= strokeWidth;
        rectF.bottom -= strokeWidth;
        canvas.save();
        canvas.rotate(this.f471l, rectF.centerX(), rectF.centerY());
        canvas.drawOval(rectF, this.f460a);
        canvas.restore();
    }

    public int getOpacity() {
        return this.f463d > BitmapDescriptorFactory.HUE_RED ? -3 : -2;
    }

    public boolean getPadding(Rect rect) {
        int round = Math.round(this.f463d);
        rect.set(round, round, round, round);
        return true;
    }

    public boolean isStateful() {
        return (this.f468i != null && this.f468i.isStateful()) || super.isStateful();
    }

    protected void onBoundsChange(Rect rect) {
        this.f470k = true;
    }

    protected boolean onStateChange(int[] iArr) {
        if (this.f468i != null) {
            int colorForState = this.f468i.getColorForState(iArr, this.f469j);
            if (colorForState != this.f469j) {
                this.f470k = true;
                this.f469j = colorForState;
            }
        }
        if (this.f470k) {
            invalidateSelf();
        }
        return this.f470k;
    }

    public void setAlpha(int i) {
        this.f460a.setAlpha(i);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f460a.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
