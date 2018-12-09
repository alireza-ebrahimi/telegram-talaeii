package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v7.widget.u */
class C1078u extends C1074q {
    /* renamed from: a */
    private final SeekBar f3195a;
    /* renamed from: b */
    private Drawable f3196b;
    /* renamed from: c */
    private ColorStateList f3197c = null;
    /* renamed from: d */
    private Mode f3198d = null;
    /* renamed from: e */
    private boolean f3199e = false;
    /* renamed from: f */
    private boolean f3200f = false;

    C1078u(SeekBar seekBar) {
        super(seekBar);
        this.f3195a = seekBar;
    }

    /* renamed from: d */
    private void m5899d() {
        if (this.f3196b == null) {
            return;
        }
        if (this.f3199e || this.f3200f) {
            this.f3196b = C0375a.m1784g(this.f3196b.mutate());
            if (this.f3199e) {
                C0375a.m1773a(this.f3196b, this.f3197c);
            }
            if (this.f3200f) {
                C0375a.m1776a(this.f3196b, this.f3198d);
            }
            if (this.f3196b.isStateful()) {
                this.f3196b.setState(this.f3195a.getDrawableState());
            }
        }
    }

    /* renamed from: a */
    void m5900a(Canvas canvas) {
        int i = 1;
        if (this.f3196b != null) {
            int max = this.f3195a.getMax();
            if (max > 1) {
                int intrinsicWidth = this.f3196b.getIntrinsicWidth();
                int intrinsicHeight = this.f3196b.getIntrinsicHeight();
                intrinsicWidth = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                if (intrinsicHeight >= 0) {
                    i = intrinsicHeight / 2;
                }
                this.f3196b.setBounds(-intrinsicWidth, -i, intrinsicWidth, i);
                float width = ((float) ((this.f3195a.getWidth() - this.f3195a.getPaddingLeft()) - this.f3195a.getPaddingRight())) / ((float) max);
                intrinsicHeight = canvas.save();
                canvas.translate((float) this.f3195a.getPaddingLeft(), (float) (this.f3195a.getHeight() / 2));
                for (i = 0; i <= max; i++) {
                    this.f3196b.draw(canvas);
                    canvas.translate(width, BitmapDescriptorFactory.HUE_RED);
                }
                canvas.restoreToCount(intrinsicHeight);
            }
        }
    }

    /* renamed from: a */
    void mo1009a(Drawable drawable) {
        if (this.f3196b != null) {
            this.f3196b.setCallback(null);
        }
        this.f3196b = drawable;
        if (drawable != null) {
            drawable.setCallback(this.f3195a);
            C0375a.m1779b(drawable, ah.m2812g(this.f3195a));
            if (drawable.isStateful()) {
                drawable.setState(this.f3195a.getDrawableState());
            }
            m5899d();
        }
        this.f3195a.invalidate();
    }

    /* renamed from: a */
    void mo1010a(AttributeSet attributeSet, int i) {
        super.mo1010a(attributeSet, i);
        bk a = bk.m5654a(this.f3195a.getContext(), attributeSet, C0747j.AppCompatSeekBar, i, 0);
        Drawable b = a.m5661b(C0747j.AppCompatSeekBar_android_thumb);
        if (b != null) {
            this.f3195a.setThumb(b);
        }
        mo1009a(a.m5657a(C0747j.AppCompatSeekBar_tickMark));
        if (a.m5671g(C0747j.AppCompatSeekBar_tickMarkTintMode)) {
            this.f3198d = ai.m5430a(a.m5656a(C0747j.AppCompatSeekBar_tickMarkTintMode, -1), this.f3198d);
            this.f3200f = true;
        }
        if (a.m5671g(C0747j.AppCompatSeekBar_tickMarkTint)) {
            this.f3197c = a.m5667e(C0747j.AppCompatSeekBar_tickMarkTint);
            this.f3199e = true;
        }
        a.m5658a();
        m5899d();
    }

    @TargetApi(11)
    /* renamed from: b */
    void mo1011b() {
        if (this.f3196b != null) {
            this.f3196b.jumpToCurrentState();
        }
    }

    /* renamed from: c */
    void m5904c() {
        Drawable drawable = this.f3196b;
        if (drawable != null && drawable.isStateful() && drawable.setState(this.f3195a.getDrawableState())) {
            this.f3195a.invalidateDrawable(drawable);
        }
    }
}
