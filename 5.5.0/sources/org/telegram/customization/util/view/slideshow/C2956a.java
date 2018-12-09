package org.telegram.customization.util.view.slideshow;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/* renamed from: org.telegram.customization.util.view.slideshow.a */
public class C2956a extends Scroller {
    /* renamed from: a */
    private double f9804a = 1.0d;

    public C2956a(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    /* renamed from: a */
    public void m13640a(double d) {
        this.f9804a = d;
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, (int) (((double) i5) * this.f9804a));
    }
}
