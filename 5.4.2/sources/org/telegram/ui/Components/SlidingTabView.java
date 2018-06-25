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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

public class SlidingTabView extends LinearLayout {
    private float animateTabXTo = BitmapDescriptorFactory.HUE_RED;
    private SlidingTabViewDelegate delegate;
    private DecelerateInterpolator interpolator;
    private Paint paint = new Paint();
    private int selectedTab = 0;
    private long startAnimationTime = 0;
    private float startAnimationX = BitmapDescriptorFactory.HUE_RED;
    private int tabCount = 0;
    private float tabWidth = BitmapDescriptorFactory.HUE_RED;
    private float tabX = BitmapDescriptorFactory.HUE_RED;
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

    private void animateToTab(int i) {
        this.animateTabXTo = ((float) i) * this.tabWidth;
        this.startAnimationX = this.tabX;
        this.totalAnimationDiff = 0;
        this.startAnimationTime = System.currentTimeMillis();
        invalidate();
    }

    private void didSelectTab(int i) {
        if (this.selectedTab != i) {
            this.selectedTab = i;
            animateToTab(i);
            if (this.delegate != null) {
                this.delegate.didSelectTab(i);
            }
        }
    }

    public void addTextTab(final int i, String str) {
        View textView = new TextView(getContext());
        textView.setText(str);
        textView.setFocusable(true);
        textView.setGravity(17);
        textView.setSingleLine();
        textView.setTextColor(-1);
        textView.setTextSize(1, 14.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, 0));
        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SlidingTabView.this.didSelectTab(i);
            }
        });
        addView(textView);
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        layoutParams.height = -1;
        layoutParams.width = 0;
        layoutParams.weight = 50.0f;
        textView.setLayoutParams(layoutParams);
        this.tabCount++;
    }

    public int getSeletedTab() {
        return this.selectedTab;
    }

    protected void onDraw(Canvas canvas) {
        if (this.tabX != this.animateTabXTo) {
            long currentTimeMillis = System.currentTimeMillis() - this.startAnimationTime;
            this.startAnimationTime = System.currentTimeMillis();
            this.totalAnimationDiff = currentTimeMillis + this.totalAnimationDiff;
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

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.tabWidth = ((float) (i3 - i)) / ((float) this.tabCount);
        float f = this.tabWidth * ((float) this.selectedTab);
        this.tabX = f;
        this.animateTabXTo = f;
    }

    public void setDelegate(SlidingTabViewDelegate slidingTabViewDelegate) {
        this.delegate = slidingTabViewDelegate;
    }
}
