package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

@TargetApi(21)
class ay extends Drawable {
    /* renamed from: a */
    private float f2946a;
    /* renamed from: b */
    private final Paint f2947b;
    /* renamed from: c */
    private final RectF f2948c;
    /* renamed from: d */
    private final Rect f2949d;
    /* renamed from: e */
    private float f2950e;
    /* renamed from: f */
    private boolean f2951f = false;
    /* renamed from: g */
    private boolean f2952g = true;
    /* renamed from: h */
    private ColorStateList f2953h;
    /* renamed from: i */
    private PorterDuffColorFilter f2954i;
    /* renamed from: j */
    private ColorStateList f2955j;
    /* renamed from: k */
    private Mode f2956k = Mode.SRC_IN;

    public ay(ColorStateList colorStateList, float f) {
        this.f2946a = f;
        this.f2947b = new Paint(5);
        m5573b(colorStateList);
        this.f2948c = new RectF();
        this.f2949d = new Rect();
    }

    /* renamed from: a */
    private PorterDuffColorFilter m5571a(ColorStateList colorStateList, Mode mode) {
        return (colorStateList == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    /* renamed from: a */
    private void m5572a(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        this.f2948c.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        this.f2949d.set(rect);
        if (this.f2951f) {
            float a = az.m5580a(this.f2950e, this.f2946a, this.f2952g);
            this.f2949d.inset((int) Math.ceil((double) az.m5582b(this.f2950e, this.f2946a, this.f2952g)), (int) Math.ceil((double) a));
            this.f2948c.set(this.f2949d);
        }
    }

    /* renamed from: b */
    private void m5573b(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.f2953h = colorStateList;
        this.f2947b.setColor(this.f2953h.getColorForState(getState(), this.f2953h.getDefaultColor()));
    }

    /* renamed from: a */
    float m5574a() {
        return this.f2950e;
    }

    /* renamed from: a */
    void m5575a(float f) {
        if (f != this.f2946a) {
            this.f2946a = f;
            m5572a(null);
            invalidateSelf();
        }
    }

    /* renamed from: a */
    void m5576a(float f, boolean z, boolean z2) {
        if (f != this.f2950e || this.f2951f != z || this.f2952g != z2) {
            this.f2950e = f;
            this.f2951f = z;
            this.f2952g = z2;
            m5572a(null);
            invalidateSelf();
        }
    }

    /* renamed from: a */
    public void m5577a(ColorStateList colorStateList) {
        m5573b(colorStateList);
        invalidateSelf();
    }

    /* renamed from: b */
    public float m5578b() {
        return this.f2946a;
    }

    /* renamed from: c */
    public ColorStateList m5579c() {
        return this.f2953h;
    }

    public void draw(Canvas canvas) {
        Object obj;
        Paint paint = this.f2947b;
        if (this.f2954i == null || paint.getColorFilter() != null) {
            obj = null;
        } else {
            paint.setColorFilter(this.f2954i);
            obj = 1;
        }
        canvas.drawRoundRect(this.f2948c, this.f2946a, this.f2946a, paint);
        if (obj != null) {
            paint.setColorFilter(null);
        }
    }

    public int getOpacity() {
        return -3;
    }

    public void getOutline(Outline outline) {
        outline.setRoundRect(this.f2949d, this.f2946a);
    }

    public boolean isStateful() {
        return (this.f2955j != null && this.f2955j.isStateful()) || ((this.f2953h != null && this.f2953h.isStateful()) || super.isStateful());
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        m5572a(rect);
    }

    protected boolean onStateChange(int[] iArr) {
        int colorForState = this.f2953h.getColorForState(iArr, this.f2953h.getDefaultColor());
        boolean z = colorForState != this.f2947b.getColor();
        if (z) {
            this.f2947b.setColor(colorForState);
        }
        if (this.f2955j == null || this.f2956k == null) {
            return z;
        }
        this.f2954i = m5571a(this.f2955j, this.f2956k);
        return true;
    }

    public void setAlpha(int i) {
        this.f2947b.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f2947b.setColorFilter(colorFilter);
    }

    public void setTintList(ColorStateList colorStateList) {
        this.f2955j = colorStateList;
        this.f2954i = m5571a(this.f2955j, this.f2956k);
        invalidateSelf();
    }

    public void setTintMode(Mode mode) {
        this.f2956k = mode;
        this.f2954i = m5571a(this.f2955j, this.f2956k);
        invalidateSelf();
    }
}
