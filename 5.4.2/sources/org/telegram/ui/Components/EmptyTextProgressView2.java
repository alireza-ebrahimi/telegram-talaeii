package org.telegram.ui.Components;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2624m;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import utils.p178a.C3791b;
import utils.view.FarsiButton;

public class EmptyTextProgressView2 extends FrameLayout {
    private Button button;
    private BaseFragment fragment;
    private boolean inLayout;
    private LinearLayout layoutContainer;
    private RadialProgressView progressBar;
    private String searchTerm = TtmlNode.ANONYMOUS_REGION_ID;
    private boolean showAtCenter;
    private TextView textView;

    /* renamed from: org.telegram.ui.Components.EmptyTextProgressView2$1 */
    class C44261 implements OnClickListener {
        C44261() {
        }

        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("EXTRA_TAG", EmptyTextProgressView2.this.getSearchTerm());
            if (EmptyTextProgressView2.this.getFragment() != null) {
                EmptyTextProgressView2.this.getFragment().presentFragment(new C2624m(bundle));
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmptyTextProgressView2$2 */
    class C44272 implements OnTouchListener {
        C44272() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    public EmptyTextProgressView2(Context context) {
        super(context);
        this.progressBar = new RadialProgressView(context);
        this.progressBar.setVisibility(4);
        addView(this.progressBar, LayoutHelper.createFrame(-2, -2.0f));
        this.layoutContainer = new LinearLayout(context);
        this.layoutContainer.setOrientation(1);
        this.layoutContainer.setVisibility(4);
        this.textView = new TextView(context);
        this.textView.setTextSize(1, 20.0f);
        this.textView.setTextColor(Theme.getColor(Theme.key_emptyListPlaceholder));
        this.textView.setGravity(17);
        this.textView.setVisibility(4);
        this.textView.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
        if (!TextUtils.isEmpty(this.searchTerm)) {
            this.textView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        }
        this.layoutContainer.addView(this.textView);
        this.button = new FarsiButton(context);
        this.button.setText(LocaleController.getString("SearchInHotgram", R.string.SearchInHotgram));
        this.button.setGravity(17);
        this.button.setVisibility(4);
        this.button.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
        this.button.setOnClickListener(new C44261());
        if (C3791b.o(ApplicationLoader.applicationContext)) {
            this.layoutContainer.addView(this.button);
        }
        addView(this.layoutContainer, LayoutHelper.createFrame(-2, -2.0f));
        setOnTouchListener(new C44272());
    }

    public BaseFragment getFragment() {
        return this.fragment;
    }

    public String getSearchTerm() {
        return this.searchTerm;
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

    public void setFragment(BaseFragment baseFragment) {
        this.fragment = baseFragment;
    }

    public void setProgressBarColor(int i) {
        this.progressBar.setProgressColor(i);
    }

    public void setSearchTerm(String str) {
        this.searchTerm = str;
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
        this.textView.setText(TtmlNode.ANONYMOUS_REGION_ID);
        this.textView.setVisibility(4);
        this.button.setVisibility(4);
        this.layoutContainer.setVisibility(4);
        this.progressBar.setVisibility(0);
    }

    public void showTextView() {
        if (!TextUtils.isEmpty(this.searchTerm)) {
            this.textView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        }
        this.textView.setVisibility(0);
        this.button.setVisibility(0);
        this.layoutContainer.setVisibility(0);
        this.progressBar.setVisibility(4);
    }
}
