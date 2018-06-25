package com.squareup.picasso;

import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

class DeferredRequestCreator implements OnPreDrawListener {
    Callback callback;
    final RequestCreator creator;
    final WeakReference<ImageView> target;

    DeferredRequestCreator(RequestCreator creator, ImageView target) {
        this(creator, target, null);
    }

    DeferredRequestCreator(RequestCreator creator, ImageView target, Callback callback) {
        this.creator = creator;
        this.target = new WeakReference(target);
        this.callback = callback;
        target.getViewTreeObserver().addOnPreDrawListener(this);
    }

    public boolean onPreDraw() {
        ImageView target = (ImageView) this.target.get();
        if (target != null) {
            ViewTreeObserver vto = target.getViewTreeObserver();
            if (vto.isAlive()) {
                int width = target.getWidth();
                int height = target.getHeight();
                if (width > 0 && height > 0) {
                    vto.removeOnPreDrawListener(this);
                    this.creator.unfit().resize(width, height).into(target, this.callback);
                }
            }
        }
        return true;
    }

    void cancel() {
        this.callback = null;
        ImageView target = (ImageView) this.target.get();
        if (target != null) {
            ViewTreeObserver vto = target.getViewTreeObserver();
            if (vto.isAlive()) {
                vto.removeOnPreDrawListener(this);
            }
        }
    }
}
