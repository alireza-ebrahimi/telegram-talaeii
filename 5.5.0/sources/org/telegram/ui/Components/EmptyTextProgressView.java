package org.telegram.ui.Components;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;

public class EmptyTextProgressView extends FrameLayout {
    private boolean inLayout;
    private RadialProgressView progressBar;
    private boolean showAtCenter;
    private TextView textView;

    /* renamed from: org.telegram.ui.Components.EmptyTextProgressView$1 */
    class C44251 implements OnTouchListener {
        C44251() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    public EmptyTextProgressView(Context context) {
        super(context);
        this.progressBar = new RadialProgressView(context);
        this.progressBar.setVisibility(4);
        addView(this.progressBar, LayoutHelper.createFrame(-2, -2.0f));
        this.textView = new TextView(context);
        this.textView.setTextSize(1, 20.0f);
        this.textView.setTextColor(Theme.getColor(Theme.key_emptyListPlaceholder));
        this.textView.setGravity(17);
        this.textView.setVisibility(4);
        this.textView.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
        this.textView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        addView(this.textView, LayoutHelper.createFrame(-2, -2.0f));
        setOnTouchListener(new C44251());
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.inLayout = true;
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int childCount = getChildCount();
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = (i5 - childAt.getMeasuredWidth()) / 2;
                int measuredHeight = this.showAtCenter ? ((i6 / 2) - childAt.getMeasuredHeight()) / 2 : (i6 - childAt.getMeasuredHeight()) / 2;
                childAt.layout(measuredWidth, measuredHeight, childAt.getMeasuredWidth() + measuredWidth, childAt.getMeasuredHeight() + measuredHeight);
            }
        }
        this.inLayout = false;
    }

    public void requestLayout() {
        if (!this.inLayout) {
            super.requestLayout();
        }
    }

    public void setProgressBarColor(int i) {
        this.progressBar.setProgressColor(i);
    }

    public void setShowAtCenter(boolean z) {
        this.showAtCenter = z;
    }

    public void setText(String str) {
        this.textView.setText(str);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }

    public void setTextSize(int i) {
        this.textView.setTextSize(1, (float) i);
    }

    public void showProgress() {
        this.textView.setVisibility(4);
        this.progressBar.setVisibility(0);
    }

    public void showTextView() {
        this.textView.setVisibility(0);
        this.progressBar.setVisibility(4);
    }
}
