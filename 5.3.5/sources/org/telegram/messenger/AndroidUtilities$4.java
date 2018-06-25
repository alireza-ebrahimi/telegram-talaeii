package org.telegram.messenger;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class AndroidUtilities$4 extends AnimatorListenerAdapter {
    final /* synthetic */ int val$num;
    final /* synthetic */ View val$view;
    final /* synthetic */ float val$x;

    AndroidUtilities$4(View view, int i, float f) {
        this.val$view = view;
        this.val$num = i;
        this.val$x = f;
    }

    public void onAnimationEnd(Animator animation) {
        AndroidUtilities.shakeView(this.val$view, this.val$num == 5 ? 0.0f : -this.val$x, this.val$num + 1);
    }
}
