package org.telegram.customization.util.view.slideshow;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.aa;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import org.ir.talaeii.R;
import org.telegram.messenger.C3336R;

public class SlideshowView extends FrameLayout {
    /* renamed from: a */
    View f9801a;
    /* renamed from: b */
    AutoScrollViewPager f9802b;
    /* renamed from: c */
    IconPageIndicator f9803c;

    /* renamed from: org.telegram.customization.util.view.slideshow.SlideshowView$a */
    public static abstract class C2646a extends aa implements C2645b {
    }

    public SlideshowView(Context context) {
        super(context);
        m13637b();
        m13638a();
    }

    public SlideshowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m13637b();
        m13639a(context, attributeSet);
        m13638a();
    }

    public SlideshowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m13637b();
        m13639a(context, attributeSet);
        m13638a();
    }

    @TargetApi(21)
    public SlideshowView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        m13637b();
        m13639a(context, attributeSet);
        m13638a();
    }

    /* renamed from: b */
    private void m13637b() {
        this.f9801a = inflate(getContext(), R.layout.slideshow, null);
        this.f9802b = (AutoScrollViewPager) this.f9801a.findViewById(R.id.autoViewPager);
        this.f9803c = (IconPageIndicator) this.f9801a.findViewById(R.id.indicator);
        addView(this.f9801a);
    }

    /* renamed from: a */
    void m13638a() {
    }

    /* renamed from: a */
    void m13639a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C3336R.styleable.SlideshowView);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            switch (index) {
                case 0:
                    this.f9802b.setInterval((long) obtainStyledAttributes.getInteger(index, 2000));
                    break;
                case 1:
                    this.f9802b.setDirection(obtainStyledAttributes.getBoolean(index, true) ? 0 : 1);
                    break;
                case 2:
                    if (!obtainStyledAttributes.getBoolean(index, true)) {
                        break;
                    }
                    this.f9802b.m13631a();
                    break;
                case 3:
                    this.f9802b.setAutoScrollDurationFactor((double) obtainStyledAttributes.getFloat(index, 5.0f));
                    break;
                default:
                    break;
            }
        }
        obtainStyledAttributes.recycle();
    }

    public void setAdapter(C2646a c2646a) {
        this.f9802b.setAdapter(c2646a);
        this.f9803c.setViewPager(this.f9802b);
    }
}
