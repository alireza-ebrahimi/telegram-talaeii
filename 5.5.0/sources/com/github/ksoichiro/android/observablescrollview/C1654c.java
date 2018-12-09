package com.github.ksoichiro.android.observablescrollview;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/* renamed from: com.github.ksoichiro.android.observablescrollview.c */
public final class C1654c {
    /* renamed from: a */
    public static void m8073a(final View view, final Runnable runnable) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (VERSION.SDK_INT < 16) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                runnable.run();
            }
        });
    }
}
