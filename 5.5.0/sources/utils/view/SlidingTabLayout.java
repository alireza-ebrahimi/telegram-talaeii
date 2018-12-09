package utils.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v4.view.aa;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SlidingTabLayout extends HorizontalScrollView {
    /* renamed from: a */
    private final SlidingTabStrip f10293a;
    /* renamed from: b */
    private int f10294b;
    /* renamed from: c */
    private int f10295c;
    /* renamed from: d */
    private int f10296d;
    /* renamed from: e */
    private boolean f10297e;
    /* renamed from: f */
    private boolean f10298f;
    /* renamed from: g */
    private ViewPager f10299g;
    /* renamed from: h */
    private SparseArray<String> f10300h;
    /* renamed from: i */
    private C0188f f10301i;

    public interface TabColorizer {
        int getIndicatorColor(int i);
    }

    private class InternalViewPagerListener implements C0188f {
        /* renamed from: a */
        final /* synthetic */ SlidingTabLayout f10290a;
        /* renamed from: b */
        private int f10291b;

        private InternalViewPagerListener(SlidingTabLayout slidingTabLayout) {
            this.f10290a = slidingTabLayout;
        }

        public void onPageScrollStateChanged(int i) {
            this.f10291b = i;
            if (this.f10290a.f10301i != null) {
                this.f10290a.f10301i.onPageScrollStateChanged(i);
            }
        }

        public void onPageScrolled(int i, float f, int i2) {
            int childCount = this.f10290a.f10293a.getChildCount();
            if (childCount != 0 && i >= 0 && i < childCount) {
                this.f10290a.f10293a.m14191a(i, f);
                View childAt = this.f10290a.f10293a.getChildAt(i);
                this.f10290a.m14183b(i, childAt != null ? (int) (((float) childAt.getWidth()) * f) : 0);
                if (this.f10290a.f10301i != null) {
                    this.f10290a.f10301i.onPageScrolled(i, f, i2);
                }
            }
        }

        public void onPageSelected(int i) {
            if (this.f10291b == 0) {
                this.f10290a.f10293a.m14191a(i, (float) BitmapDescriptorFactory.HUE_RED);
                this.f10290a.m14183b(i, 0);
            }
            int i2 = 0;
            while (i2 < this.f10290a.f10293a.getChildCount()) {
                this.f10290a.f10293a.getChildAt(i2).setSelected(i == i2);
                i2++;
            }
            if (this.f10290a.f10301i != null) {
                this.f10290a.f10301i.onPageSelected(i);
            }
        }
    }

    private class TabClickListener implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ SlidingTabLayout f10292a;

        private TabClickListener(SlidingTabLayout slidingTabLayout) {
            this.f10292a = slidingTabLayout;
        }

        public void onClick(View view) {
            for (int i = 0; i < this.f10292a.f10293a.getChildCount(); i++) {
                if (view == this.f10292a.f10293a.getChildAt(i) && this.f10292a.m14187a()) {
                    this.f10292a.f10299g.setCurrentItem(i);
                    return;
                }
            }
        }
    }

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f10298f = true;
        this.f10300h = new SparseArray();
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        this.f10294b = (int) (24.0f * getResources().getDisplayMetrics().density);
        this.f10293a = new SlidingTabStrip(context);
        addView(this.f10293a, -1, -2);
    }

    /* renamed from: b */
    private void m14182b() {
        aa adapter = this.f10299g.getAdapter();
        OnClickListener tabClickListener = new TabClickListener();
        for (int i = 0; i < adapter.getCount(); i++) {
            View inflate;
            TextView textView;
            if (this.f10295c != 0) {
                inflate = LayoutInflater.from(getContext()).inflate(this.f10295c, this.f10293a, false);
                textView = (TextView) inflate.findViewById(this.f10296d);
            } else {
                textView = null;
                inflate = null;
            }
            if (inflate == null) {
                inflate = m14185a(getContext());
            }
            TextView textView2 = (textView == null && TextView.class.isInstance(inflate)) ? (TextView) inflate : textView;
            if (this.f10297e) {
                LayoutParams layoutParams = (LayoutParams) inflate.getLayoutParams();
                layoutParams.width = 0;
                layoutParams.weight = 1.0f;
            }
            textView2.setText(adapter.getPageTitle(i));
            inflate.setOnClickListener(tabClickListener);
            String str = (String) this.f10300h.get(i, null);
            if (str != null) {
                inflate.setContentDescription(str);
            }
            this.f10293a.addView(inflate);
            if (i == this.f10299g.getCurrentItem()) {
                inflate.setSelected(true);
            }
        }
    }

    /* renamed from: b */
    private void m14183b(int i, int i2) {
        int childCount = this.f10293a.getChildCount();
        if (childCount != 0 && i >= 0 && i < childCount) {
            View childAt = this.f10293a.getChildAt(i);
            if (childAt != null) {
                childCount = childAt.getLeft() + i2;
                if (i > 0 || i2 > 0) {
                    childCount -= this.f10294b;
                }
                scrollTo(childCount, 0);
            }
        }
    }

    @TargetApi(14)
    /* renamed from: a */
    protected TextView m14185a(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(17);
        textView.setTextSize(2, 12.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LayoutParams(-2, -2));
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(16843534, typedValue, true);
        textView.setBackgroundResource(typedValue.resourceId);
        textView.setAllCaps(true);
        int i = (int) (16.0f * getResources().getDisplayMetrics().density);
        textView.setPadding(i, i, i, i);
        return textView;
    }

    /* renamed from: a */
    public void m14186a(int i, int i2) {
        this.f10295c = i;
        this.f10296d = i2;
    }

    /* renamed from: a */
    public boolean m14187a() {
        return this.f10298f;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f10299g != null) {
            m14183b(this.f10299g.getCurrentItem(), 0);
        }
    }

    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        this.f10293a.m14192a(tabColorizer);
    }

    public void setDistributeEvenly(boolean z) {
        this.f10297e = z;
    }

    public void setOnClickEnabled(boolean z) {
        this.f10298f = z;
    }

    public void setOnPageChangeListener(C0188f c0188f) {
        this.f10301i = c0188f;
    }

    public void setSelectedIndicatorColors(int... iArr) {
        this.f10293a.m14193a(iArr);
    }

    public void setViewPager(ViewPager viewPager) {
        this.f10293a.removeAllViews();
        this.f10299g = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            m14182b();
        }
    }
}
