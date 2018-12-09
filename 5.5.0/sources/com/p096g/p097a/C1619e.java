package com.p096g.p097a;

import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

/* renamed from: com.g.a.e */
class C1619e implements OnPreDrawListener {
    /* renamed from: a */
    final C1641p f4951a;
    /* renamed from: b */
    final WeakReference<ImageView> f4952b;
    /* renamed from: c */
    C1618d f4953c;

    C1619e(C1641p c1641p, ImageView imageView, C1618d c1618d) {
        this.f4951a = c1641p;
        this.f4952b = new WeakReference(imageView);
        this.f4953c = c1618d;
        imageView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    /* renamed from: a */
    void m7987a() {
        this.f4953c = null;
        ImageView imageView = (ImageView) this.f4952b.get();
        if (imageView != null) {
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this);
            }
        }
    }

    public boolean onPreDraw() {
        ImageView imageView = (ImageView) this.f4952b.get();
        if (imageView != null) {
            ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                if (width > 0 && height > 0) {
                    viewTreeObserver.removeOnPreDrawListener(this);
                    this.f4951a.m8037a().m8038a(width, height).m8039a(imageView, this.f4953c);
                }
            }
        }
        return true;
    }
}
