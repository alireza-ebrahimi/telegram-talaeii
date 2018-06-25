package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class SlidingTabView extends LinearLayout {
    private float animateTabXTo = 0.0f;
    private SlidingTabViewDelegate delegate;
    private DecelerateInterpolator interpolator;
    private Paint paint = new Paint();
    private int selectedTab = 0;
    private long startAnimationTime = 0;
    private float startAnimationX = 0.0f;
    private int tabCount = 0;
    private float tabWidth = 0.0f;
    private float tabX = 0.0f;
    private long totalAnimationDiff = 0;

    public interface SlidingTabViewDelegate {
        void didSelectTab(int i);
    }

    public SlidingTabView(Context context) {
        super(context);
        setOrientation(0);
        setWeightSum(100.0f);
        this.paint.setColor(-1);
        setWillNotDraw(false);
        this.interpolator = new DecelerateInterpolator();
    }

    public void addTextTab(final int position, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setFocusable(true);
        tab.setGravity(17);
        tab.setSingleLine();
        tab.setTextColor(-1);
        tab.setTextSize(1, 14.0f);
        tab.setTypeface(Typeface.DEFAULT_BOLD);
        tab.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, 0));
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SlidingTabView.this.didSelectTab(position);
            }
        });
        addView(tab);
        LayoutParams layoutParams = (LayoutParams) tab.getLayoutParams();
        layoutParams.height = -1;
        layoutParams.width = 0;
        layoutParams.weight = 50.0f;
        tab.setLayoutParams(layoutParams);
        this.tabCount++;
    }

    public void setDelegate(SlidingTabViewDelegate delegate) {
        this.delegate = delegate;
    }

    public int getSeletedTab() {
        return this.selectedTab;
    }

    private void didSelectTab(int tab) {
        if (this.selectedTab != tab) {
            this.selectedTab = tab;
            animateToTab(tab);
            if (this.delegate != null) {
                this.delegate.didSelectTab(tab);
            }
        }
    }

    private void animateToTab(int tab) {
        this.animateTabXTo = ((float) tab) * this.tabWidth;
        this.startAnimationX = this.tabX;
        this.totalAnimationDiff = 0;
        this.startAnimationTime = System.currentTimeMillis();
        invalidate();
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.tabWidth = ((float) (r - l)) / ((float) this.tabCount);
        float f = this.tabWidth * ((float) this.selectedTab);
        this.tabX = f;
        this.animateTabXTo = f;
    }

    protected void onDraw(Canvas canvas) {
        if (this.tabX != this.animateTabXTo) {
            long dt = System.currentTimeMillis() - this.startAnimationTime;
            this.startAnimationTime = System.currentTimeMillis();
            this.totalAnimationDiff += dt;
            if (this.totalAnimationDiff > 200) {
                this.totalAnimationDiff = 200;
                this.tabX = this.animateTabXTo;
            } else {
                this.tabX = this.startAnimationX + (this.interpolator.getInterpolation(((float) this.totalAnimationDiff) / 200.0f) * (this.animateTabXTo - this.startAnimationX));
                invalidate();
            }
        }
        Canvas canvas2 = canvas;
        canvas2.drawRect(this.tabX, (float) (getHeight() - AndroidUtilities.dp(2.0f)), this.tabWidth + this.tabX, (float) getHeight(), this.paint);
    }
}
