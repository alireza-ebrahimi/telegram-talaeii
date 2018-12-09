package com.github.vivchar.viewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v7.widget.ao;
import android.support.v7.widget.ao.C0899a;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.github.vivchar.viewpagerindicator.C1659a.C1657a;
import com.github.vivchar.viewpagerindicator.C1659a.C1658b;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerIndicator extends ao {
    /* renamed from: a */
    private static final int f5109a = C1657a.white_circle;
    /* renamed from: b */
    private int f5110b;
    /* renamed from: c */
    private int f5111c;
    /* renamed from: d */
    private int f5112d;
    /* renamed from: e */
    private int f5113e;
    /* renamed from: f */
    private float f5114f;
    /* renamed from: g */
    private int f5115g;
    /* renamed from: h */
    private int f5116h;
    /* renamed from: i */
    private int f5117i;
    /* renamed from: j */
    private final List<ImageView> f5118j;
    /* renamed from: k */
    private C0188f f5119k;

    /* renamed from: com.github.vivchar.viewpagerindicator.ViewPagerIndicator$a */
    private class C1656a implements C0188f {
        /* renamed from: a */
        final /* synthetic */ ViewPagerIndicator f5108a;

        private C1656a(ViewPagerIndicator viewPagerIndicator) {
            this.f5108a = viewPagerIndicator;
        }

        public void onPageScrollStateChanged(int i) {
            if (this.f5108a.f5119k != null) {
                this.f5108a.f5119k.onPageScrollStateChanged(i);
            }
        }

        public void onPageScrolled(int i, float f, int i2) {
            if (this.f5108a.f5119k != null) {
                this.f5108a.f5119k.onPageScrolled(i, f, i2);
            }
        }

        public void onPageSelected(int i) {
            this.f5108a.setSelectedIndex(i);
            if (this.f5108a.f5119k != null) {
                this.f5108a.f5119k.onPageSelected(i);
            }
        }
    }

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f5110b = -1;
        this.f5111c = -1;
        this.f5114f = 1.0f;
        this.f5115g = 10;
        this.f5116h = 10;
        this.f5117i = 10;
        this.f5118j = new ArrayList();
        setOrientation(0);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C1658b.ViewPagerIndicator, 0, 0);
        try {
            this.f5115g = obtainStyledAttributes.getDimensionPixelSize(C1658b.ViewPagerIndicator_itemSize, 10);
            this.f5114f = obtainStyledAttributes.getFloat(C1658b.ViewPagerIndicator_itemScale, 1.0f);
            this.f5111c = obtainStyledAttributes.getColor(C1658b.ViewPagerIndicator_itemSelectedTint, -1);
            this.f5110b = obtainStyledAttributes.getColor(C1658b.ViewPagerIndicator_itemTint, -1);
            this.f5116h = obtainStyledAttributes.getDimensionPixelSize(C1658b.ViewPagerIndicator_delimiterSize, 10);
            this.f5117i = obtainStyledAttributes.getResourceId(C1658b.ViewPagerIndicator_itemIcon, f5109a);
            if (isInEditMode()) {
                m8076a();
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a */
    private FrameLayout m8075a(int i) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        View b = m8078b();
        frameLayout.addView(b);
        this.f5118j.add(b);
        LayoutParams c0899a = new C0899a((int) (((float) this.f5115g) * this.f5114f), (int) (((float) this.f5115g) * this.f5114f));
        if (i > 0) {
            c0899a.setMargins(this.f5116h, 0, 0, 0);
        }
        frameLayout.setLayoutParams(c0899a);
        return frameLayout;
    }

    /* renamed from: a */
    private void m8076a() {
        for (int i = 0; i < 5; i++) {
            View a = m8075a(i);
            addView(a);
            if (i == 1) {
                a = a.getChildAt(0);
                LayoutParams layoutParams = a.getLayoutParams();
                layoutParams.height = (int) (((float) layoutParams.height) * this.f5114f);
                layoutParams.width = (int) (((float) layoutParams.width) * this.f5114f);
                a.setLayoutParams(layoutParams);
            }
        }
    }

    /* renamed from: b */
    private ImageView m8078b() {
        ImageView imageView = new ImageView(getContext());
        LayoutParams layoutParams = new FrameLayout.LayoutParams(this.f5115g, this.f5115g);
        layoutParams.gravity = 17;
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(this.f5117i);
        imageView.setScaleType(ScaleType.FIT_CENTER);
        imageView.setColorFilter(this.f5110b, Mode.SRC_IN);
        return imageView;
    }

    private void setPageCount(int i) {
        int i2 = 0;
        this.f5112d = i;
        this.f5113e = 0;
        removeAllViews();
        this.f5118j.clear();
        while (i2 < i) {
            addView(m8075a(i2));
            i2++;
        }
        setSelectedIndex(this.f5113e);
    }

    private void setSelectedIndex(int i) {
        if (i >= 0 && i <= this.f5112d - 1) {
            ImageView imageView = (ImageView) this.f5118j.get(this.f5113e);
            imageView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
            imageView.setColorFilter(this.f5110b, Mode.SRC_IN);
            imageView = (ImageView) this.f5118j.get(i);
            imageView.animate().scaleX(this.f5114f).scaleY(this.f5114f).setDuration(300).start();
            imageView.setColorFilter(this.f5111c, Mode.SRC_IN);
            this.f5113e = i;
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        setPageCount(viewPager.getAdapter().getCount());
        viewPager.addOnPageChangeListener(new C1656a());
    }
}
