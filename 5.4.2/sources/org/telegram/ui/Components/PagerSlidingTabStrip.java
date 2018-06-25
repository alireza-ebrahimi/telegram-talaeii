package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class PagerSlidingTabStrip extends HorizontalScrollView {
    private int currentPosition = 0;
    private float currentPositionOffset = BitmapDescriptorFactory.HUE_RED;
    private LayoutParams defaultTabLayoutParams;
    public C0188f delegatePageListener;
    private int dividerPadding = AndroidUtilities.dp(12.0f);
    private int indicatorColor = -10066330;
    private int indicatorHeight = AndroidUtilities.dp(8.0f);
    private int lastScrollX = 0;
    private final PageListener pageListener = new PageListener();
    private ViewPager pager;
    private Paint rectPaint;
    private int scrollOffset = AndroidUtilities.dp(52.0f);
    private boolean shouldExpand = false;
    private int tabCount;
    private int tabPadding = AndroidUtilities.dp(24.0f);
    private LinearLayout tabsContainer;
    private int underlineColor = 436207616;
    private int underlineHeight = AndroidUtilities.dp(2.0f);

    public interface IconTabProvider {
        boolean canScrollToTab(int i);

        void customOnDraw(Canvas canvas, int i);

        Drawable getPageIconDrawable(int i);
    }

    /* renamed from: org.telegram.ui.Components.PagerSlidingTabStrip$1 */
    class C44641 implements OnGlobalLayoutListener {
        C44641() {
        }

        public void onGlobalLayout() {
            PagerSlidingTabStrip.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            PagerSlidingTabStrip.this.currentPosition = PagerSlidingTabStrip.this.pager.getCurrentItem();
            PagerSlidingTabStrip.this.scrollToChild(PagerSlidingTabStrip.this.currentPosition, 0);
        }
    }

    /* renamed from: org.telegram.ui.Components.PagerSlidingTabStrip$4 */
    class C44674 implements Runnable {
        C44674() {
        }

        public void run() {
            PagerSlidingTabStrip.this.notifyDataSetChanged();
        }
    }

    private class PageListener implements C0188f {
        private PageListener() {
        }

        public void onPageScrollStateChanged(int i) {
            if (i == 0) {
                PagerSlidingTabStrip.this.scrollToChild(PagerSlidingTabStrip.this.pager.getCurrentItem(), 0);
            }
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageScrollStateChanged(i);
            }
        }

        public void onPageScrolled(int i, float f, int i2) {
            PagerSlidingTabStrip.this.currentPosition = i;
            PagerSlidingTabStrip.this.currentPositionOffset = f;
            PagerSlidingTabStrip.this.scrollToChild(i, (int) (((float) PagerSlidingTabStrip.this.tabsContainer.getChildAt(i).getWidth()) * f));
            PagerSlidingTabStrip.this.invalidate();
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageScrolled(i, f, i2);
            }
        }

        public void onPageSelected(int i) {
            if (PagerSlidingTabStrip.this.delegatePageListener != null) {
                PagerSlidingTabStrip.this.delegatePageListener.onPageSelected(i);
            }
            int i2 = 0;
            while (i2 < PagerSlidingTabStrip.this.tabsContainer.getChildCount()) {
                PagerSlidingTabStrip.this.tabsContainer.getChildAt(i2).setSelected(i2 == i);
                i2++;
            }
        }
    }

    public PagerSlidingTabStrip(Context context) {
        super(context);
        setFillViewport(true);
        setWillNotDraw(false);
        this.tabsContainer = new LinearLayout(context);
        this.tabsContainer.setOrientation(0);
        this.tabsContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.tabsContainer);
        this.rectPaint = new Paint();
        this.rectPaint.setAntiAlias(true);
        this.rectPaint.setStyle(Style.FILL);
        this.defaultTabLayoutParams = new LayoutParams(-2, -1);
    }

    private void addIconTab(final int i, Drawable drawable) {
        boolean z = true;
        View c44652 = new ImageView(getContext()) {
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (PagerSlidingTabStrip.this.pager.getAdapter() instanceof IconTabProvider) {
                    ((IconTabProvider) PagerSlidingTabStrip.this.pager.getAdapter()).customOnDraw(canvas, i);
                }
            }
        };
        c44652.setFocusable(true);
        c44652.setImageDrawable(drawable);
        c44652.setScaleType(ScaleType.CENTER);
        c44652.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!(PagerSlidingTabStrip.this.pager.getAdapter() instanceof IconTabProvider) || ((IconTabProvider) PagerSlidingTabStrip.this.pager.getAdapter()).canScrollToTab(i)) {
                    PagerSlidingTabStrip.this.pager.setCurrentItem(i);
                }
            }
        });
        this.tabsContainer.addView(c44652);
        if (i != this.currentPosition) {
            z = false;
        }
        c44652.setSelected(z);
    }

    private void scrollToChild(int i, int i2) {
        if (this.tabCount != 0) {
            int left = this.tabsContainer.getChildAt(i).getLeft() + i2;
            if (i > 0 || i2 > 0) {
                left -= this.scrollOffset;
            }
            if (left != this.lastScrollX) {
                this.lastScrollX = left;
                scrollTo(left, 0);
            }
        }
    }

    private void updateTabStyles() {
        for (int i = 0; i < this.tabCount; i++) {
            View childAt = this.tabsContainer.getChildAt(i);
            childAt.setLayoutParams(this.defaultTabLayoutParams);
            if (this.shouldExpand) {
                childAt.setPadding(0, 0, 0, 0);
                childAt.setLayoutParams(new LayoutParams(-1, -1, 1.0f));
            } else {
                childAt.setPadding(this.tabPadding, 0, this.tabPadding, 0);
            }
        }
    }

    public int getDividerPadding() {
        return this.dividerPadding;
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public int getIndicatorHeight() {
        return this.indicatorHeight;
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }

    public boolean getShouldExpand() {
        return this.shouldExpand;
    }

    public View getTab(int i) {
        return (i < 0 || i >= this.tabsContainer.getChildCount()) ? null : this.tabsContainer.getChildAt(i);
    }

    public int getTabPaddingLeftRight() {
        return this.tabPadding;
    }

    public int getUnderlineColor() {
        return this.underlineColor;
    }

    public int getUnderlineHeight() {
        return this.underlineHeight;
    }

    public void notifyDataSetChanged() {
        this.tabsContainer.removeAllViews();
        this.tabCount = this.pager.getAdapter().getCount();
        for (int i = 0; i < this.tabCount; i++) {
            if (this.pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) this.pager.getAdapter()).getPageIconDrawable(i));
            }
        }
        updateTabStyles();
        getViewTreeObserver().addOnGlobalLayoutListener(new C44641());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode() && this.tabCount != 0) {
            float f;
            int height = getHeight();
            this.rectPaint.setColor(this.underlineColor);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) (height - this.underlineHeight), (float) this.tabsContainer.getWidth(), (float) height, this.rectPaint);
            View childAt = this.tabsContainer.getChildAt(this.currentPosition);
            float left = (float) childAt.getLeft();
            float right = (float) childAt.getRight();
            if (this.currentPositionOffset <= BitmapDescriptorFactory.HUE_RED || this.currentPosition >= this.tabCount - 1) {
                f = left;
            } else {
                View childAt2 = this.tabsContainer.getChildAt(this.currentPosition + 1);
                float right2 = (float) childAt2.getRight();
                f = (this.currentPositionOffset * ((float) childAt2.getLeft())) + (left * (1.0f - this.currentPositionOffset));
                right = (this.currentPositionOffset * right2) + ((1.0f - this.currentPositionOffset) * right);
            }
            this.rectPaint.setColor(this.indicatorColor);
            canvas.drawRect(f, (float) (height - this.indicatorHeight), right, (float) height, this.rectPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.shouldExpand && MeasureSpec.getMode(i) != 0) {
            this.tabsContainer.measure(getMeasuredWidth() | 1073741824, i2);
        }
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        if (!this.shouldExpand) {
            post(new C44674());
        }
    }

    public void setDividerPadding(int i) {
        this.dividerPadding = i;
        invalidate();
    }

    public void setIndicatorColor(int i) {
        this.indicatorColor = i;
        invalidate();
    }

    public void setIndicatorColorResource(int i) {
        this.indicatorColor = getResources().getColor(i);
        invalidate();
    }

    public void setIndicatorHeight(int i) {
        this.indicatorHeight = i;
        invalidate();
    }

    public void setOnPageChangeListener(C0188f c0188f) {
        this.delegatePageListener = c0188f;
    }

    public void setScrollOffset(int i) {
        this.scrollOffset = i;
        invalidate();
    }

    public void setShouldExpand(boolean z) {
        this.shouldExpand = z;
        this.tabsContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        updateTabStyles();
        requestLayout();
    }

    public void setTabPaddingLeftRight(int i) {
        this.tabPadding = i;
        updateTabStyles();
    }

    public void setUnderlineColor(int i) {
        this.underlineColor = i;
        invalidate();
    }

    public void setUnderlineColorResource(int i) {
        this.underlineColor = getResources().getColor(i);
        invalidate();
    }

    public void setUnderlineHeight(int i) {
        this.underlineHeight = i;
        invalidate();
    }

    public void setViewPager(ViewPager viewPager) {
        this.pager = viewPager;
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        viewPager.setOnPageChangeListener(this.pageListener);
        notifyDataSetChanged();
    }
}
