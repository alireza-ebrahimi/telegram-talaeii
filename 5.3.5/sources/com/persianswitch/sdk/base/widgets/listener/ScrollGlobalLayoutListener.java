package com.persianswitch.sdk.base.widgets.listener;

import android.graphics.Rect;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ScrollView;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScrollGlobalLayoutListener implements OnGlobalLayoutListener {
    private final AtomicBoolean mMustScroll = new AtomicBoolean(false);
    private final ScrollView mScrollView;

    public ScrollGlobalLayoutListener(ScrollView mScrollView) {
        this.mScrollView = mScrollView;
    }

    public static void init(ScrollView scrollView) {
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ScrollGlobalLayoutListener(scrollView));
        }
    }

    public void onGlobalLayout() {
        boolean mustBeScrolled = true;
        Rect r = new Rect();
        this.mScrollView.getWindowVisibleDisplayFrame(r);
        int screenHeight = this.mScrollView.getRootView().getHeight();
        if (((double) (screenHeight - r.bottom)) > ((double) screenHeight) * 0.15d) {
            if (this.mMustScroll.getAndSet(true)) {
                mustBeScrolled = false;
            }
            if (mustBeScrolled) {
                this.mScrollView.smoothScrollTo(0, this.mScrollView.getBottom());
                return;
            }
            return;
        }
        this.mMustScroll.set(false);
    }
}
