package android.support.design.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.C0073a.C0072k;
import android.support.v7.widget.ao;
import android.util.AttributeSet;
import android.view.Gravity;

/* renamed from: android.support.design.internal.a */
public class C0076a extends ao {
    /* renamed from: a */
    protected boolean f142a;
    /* renamed from: b */
    boolean f143b;
    /* renamed from: c */
    private Drawable f144c;
    /* renamed from: d */
    private final Rect f145d;
    /* renamed from: e */
    private final Rect f146e;
    /* renamed from: f */
    private int f147f;

    public C0076a(Context context) {
        this(context, null);
    }

    public C0076a(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public C0076a(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f145d = new Rect();
        this.f146e = new Rect();
        this.f147f = 119;
        this.f142a = true;
        this.f143b = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.ForegroundLinearLayout, i, 0);
        this.f147f = obtainStyledAttributes.getInt(C0072k.ForegroundLinearLayout_android_foregroundGravity, this.f147f);
        Drawable drawable = obtainStyledAttributes.getDrawable(C0072k.ForegroundLinearLayout_android_foreground);
        if (drawable != null) {
            setForeground(drawable);
        }
        this.f142a = obtainStyledAttributes.getBoolean(C0072k.ForegroundLinearLayout_foregroundInsidePadding, true);
        obtainStyledAttributes.recycle();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.f144c != null) {
            Drawable drawable = this.f144c;
            if (this.f143b) {
                this.f143b = false;
                Rect rect = this.f145d;
                Rect rect2 = this.f146e;
                int right = getRight() - getLeft();
                int bottom = getBottom() - getTop();
                if (this.f142a) {
                    rect.set(0, 0, right, bottom);
                } else {
                    rect.set(getPaddingLeft(), getPaddingTop(), right - getPaddingRight(), bottom - getPaddingBottom());
                }
                Gravity.apply(this.f147f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), rect, rect2);
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
    }

    @TargetApi(21)
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.f144c != null) {
            this.f144c.setHotspot(f, f2);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f144c != null && this.f144c.isStateful()) {
            this.f144c.setState(getDrawableState());
        }
    }

    public Drawable getForeground() {
        return this.f144c;
    }

    public int getForegroundGravity() {
        return this.f147f;
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.f144c != null) {
            this.f144c.jumpToCurrentState();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.f143b |= z;
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.f143b = true;
    }

    public void setForeground(Drawable drawable) {
        if (this.f144c != drawable) {
            if (this.f144c != null) {
                this.f144c.setCallback(null);
                unscheduleDrawable(this.f144c);
            }
            this.f144c = drawable;
            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                if (this.f147f == 119) {
                    drawable.getPadding(new Rect());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    public void setForegroundGravity(int i) {
        if (this.f147f != i) {
            int i2 = (8388615 & i) == 0 ? 8388611 | i : i;
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.f147f = i2;
            if (this.f147f == 119 && this.f144c != null) {
                this.f144c.getPadding(new Rect());
            }
            requestLayout();
        }
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f144c;
    }
}
