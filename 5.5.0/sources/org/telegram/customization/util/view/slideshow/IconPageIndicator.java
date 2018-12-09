package org.telegram.customization.util.view.slideshow;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.ir.talaeii.R;
import utils.C3792d;

public class IconPageIndicator extends HorizontalScrollView implements C2955d {
    /* renamed from: a */
    private final C2957c f9796a;
    /* renamed from: b */
    private ViewPager f9797b;
    /* renamed from: c */
    private C0188f f9798c;
    /* renamed from: d */
    private Runnable f9799d;
    /* renamed from: e */
    private int f9800e;

    public IconPageIndicator(Context context) {
        this(context, null);
    }

    public IconPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setHorizontalScrollBarEnabled(false);
        this.f9796a = new C2957c(context, R.attr.vpiIconPageIndicatorStyle);
        addView(this.f9796a, new LayoutParams(-2, -1, 17));
    }

    /* renamed from: a */
    private void m13635a(int i) {
        final View childAt = this.f9796a.getChildAt(i);
        if (this.f9799d != null) {
            removeCallbacks(this.f9799d);
        }
        this.f9799d = new Runnable(this) {
            /* renamed from: b */
            final /* synthetic */ IconPageIndicator f9795b;

            public void run() {
                this.f9795b.smoothScrollTo(childAt.getLeft() - ((this.f9795b.getWidth() - childAt.getWidth()) / 2), 0);
                this.f9795b.f9799d = null;
            }
        };
        post(this.f9799d);
    }

    /* renamed from: a */
    public void m13636a() {
        this.f9796a.removeAllViews();
        C2645b c2645b = (C2645b) this.f9797b.getAdapter();
        int count = c2645b.getCount();
        for (int i = 0; i < count; i++) {
            View imageView = new ImageView(getContext(), null, R.attr.vpiIconPageIndicatorStyle);
            imageView.setImageResource(c2645b.mo3457a(i));
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            int a = C3792d.m14075a(2.0f, getContext());
            layoutParams.setMargins(a, a, a, a);
            imageView.setLayoutParams(layoutParams);
            this.f9796a.addView(imageView);
        }
        if (this.f9800e > count) {
            this.f9800e = count - 1;
        }
        setCurrentItem(this.f9800e);
        requestLayout();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f9799d != null) {
            post(this.f9799d);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f9799d != null) {
            removeCallbacks(this.f9799d);
        }
    }

    public void onPageScrollStateChanged(int i) {
        if (this.f9798c != null) {
            this.f9798c.onPageScrollStateChanged(i);
        }
    }

    public void onPageScrolled(int i, float f, int i2) {
        if (this.f9798c != null) {
            this.f9798c.onPageScrolled(i, f, i2);
        }
    }

    public void onPageSelected(int i) {
        setCurrentItem(i);
        if (this.f9798c != null) {
            this.f9798c.onPageSelected(i);
        }
    }

    public void setCurrentItem(int i) {
        if (this.f9797b == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.f9800e = i;
        this.f9797b.setCurrentItem(i);
        int childCount = this.f9796a.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.f9796a.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                m13635a(i);
            }
            i2++;
        }
    }

    public void setOnPageChangeListener(C0188f c0188f) {
        this.f9798c = c0188f;
    }

    public void setViewPager(ViewPager viewPager) {
        if (this.f9797b != viewPager) {
            if (this.f9797b != null) {
                this.f9797b.setOnPageChangeListener(null);
            }
            if (viewPager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.f9797b = viewPager;
            viewPager.setOnPageChangeListener(this);
            m13636a();
        }
    }
}
