package org.telegram.news;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/* renamed from: org.telegram.news.g */
public class C3775g implements AnimationListener {
    /* renamed from: a */
    private final View f10131a;

    public C3775g(View view) {
        this.f10131a = view;
    }

    public void onAnimationEnd(Animation animation) {
        Log.d("sadegh)anim", TtmlNode.END);
        if (this.f10131a != null) {
            this.f10131a.setVisibility(0);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
