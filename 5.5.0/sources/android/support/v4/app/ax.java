package android.support.v4.app;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;

class ax implements OnAttachStateChangeListener, OnPreDrawListener {
    /* renamed from: a */
    private final View f995a;
    /* renamed from: b */
    private ViewTreeObserver f996b;
    /* renamed from: c */
    private final Runnable f997c;

    private ax(View view, Runnable runnable) {
        this.f995a = view;
        this.f996b = view.getViewTreeObserver();
        this.f997c = runnable;
    }

    /* renamed from: a */
    public static ax m1389a(View view, Runnable runnable) {
        ax axVar = new ax(view, runnable);
        view.getViewTreeObserver().addOnPreDrawListener(axVar);
        view.addOnAttachStateChangeListener(axVar);
        return axVar;
    }

    /* renamed from: a */
    public void m1390a() {
        if (this.f996b.isAlive()) {
            this.f996b.removeOnPreDrawListener(this);
        } else {
            this.f995a.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        this.f995a.removeOnAttachStateChangeListener(this);
    }

    public boolean onPreDraw() {
        m1390a();
        this.f997c.run();
        return true;
    }

    public void onViewAttachedToWindow(View view) {
        this.f996b = view.getViewTreeObserver();
    }

    public void onViewDetachedFromWindow(View view) {
        m1390a();
    }
}
