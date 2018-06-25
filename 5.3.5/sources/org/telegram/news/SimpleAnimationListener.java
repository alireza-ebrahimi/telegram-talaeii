package org.telegram.news;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class SimpleAnimationListener implements AnimationListener {
    private final View view;

    public SimpleAnimationListener(View view) {
        this.view = view;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        Log.d("sadegh)anim", TtmlNode.END);
        if (this.view != null) {
            this.view.setVisibility(0);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
