package com.persianswitch.sdk.base.widgets.listener;

import android.graphics.Rect;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ScrollView;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScrollGlobalLayoutListener implements OnGlobalLayoutListener {
    /* renamed from: a */
    private final AtomicBoolean f7367a = new AtomicBoolean(false);
    /* renamed from: b */
    private final ScrollView f7368b;

    public ScrollGlobalLayoutListener(ScrollView scrollView) {
        this.f7368b = scrollView;
    }

    /* renamed from: a */
    public static void m11028a(ScrollView scrollView) {
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ScrollGlobalLayoutListener(scrollView));
        }
    }

    public void onGlobalLayout() {
        boolean z = true;
        Rect rect = new Rect();
        this.f7368b.getWindowVisibleDisplayFrame(rect);
        int height = this.f7368b.getRootView().getHeight();
        if (((double) (height - rect.bottom)) > ((double) height) * 0.15d) {
            if (this.f7367a.getAndSet(true)) {
                z = false;
            }
            if (z) {
                this.f7368b.smoothScrollTo(0, this.f7368b.getBottom());
                return;
            }
            return;
        }
        this.f7367a.set(false);
    }
}
