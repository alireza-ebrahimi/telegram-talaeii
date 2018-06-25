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
import org.telegram.customization.Activities.TagSearchActivity;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import utils.view.Constants;
import utils.view.FarsiButton;

public class EmptyTextProgressView2 extends FrameLayout {
    private Button button;
    private BaseFragment fragment;
    private boolean inLayout;
    private LinearLayout layoutContainer;
    private RadialProgressView progressBar;
    private String searchTerm = "";
    private boolean showAtCenter;
    private TextView textView;

    /* renamed from: org.telegram.ui.Components.EmptyTextProgressView2$1 */
    class C25881 implements OnClickListener {
        C25881() {
        }

        public void onClick(View view) {
            Bundle args = new Bundle();
            args.putString(Constants.EXTRA_TAG, EmptyTextProgressView2.this.getSearchTerm());
            if (EmptyTextProgressView2.this.getFragment() != null) {
                EmptyTextProgressView2.this.getFragment().presentFragment(new TagSearchActivity(args));
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmptyTextProgressView2$2 */
    class C25892 implements OnTouchListener {
        C25892() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    public BaseFragment getFragment() {
        return this.fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
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
        this.button.setOnClickListener(new C25881());
        this.layoutContainer.addView(this.button);
        addView(this.layoutContainer, LayoutHelper.createFrame(-2, -2.0f));
        setOnTouchListener(new C25892());
    }

    public void showProgress() {
        this.textView.setText("");
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

    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setTextColor(int color) {
        this.textView.setTextColor(color);
    }

    public void setProgressBarColor(int color) {
        this.progressBar.setProgressColor(color);
    }

    public void setTextSize(int size) {
        this.textView.setTextSize(1, (float) size);
    }

    public void setShowAtCenter(boolean value) {
        this.showAtCenter = value;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.inLayout = true;
        int width = r - l;
        int height = b - t;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int y;
                int x = (width - child.getMeasuredWidth()) / 2;
                if (this.showAtCenter) {
                    y = ((height / 2) - child.getMeasuredHeight()) / 2;
                } else {
                    y = (height - child.getMeasuredHeight()) / 2;
                }
                child.layout(x, y, child.getMeasuredWidth() + x, child.getMeasuredHeight() + y);
            }
        }
        this.inLayout = false;
    }

    public void requestLayout() {
        if (!this.inLayout) {
            super.requestLayout();
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }
}
