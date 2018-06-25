package org.telegram.messenger.support.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class DefaultItemAnimator$6 extends DefaultItemAnimator$VpaListenerAdapter {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ ViewPropertyAnimatorCompat val$animation;
    final /* synthetic */ int val$deltaX;
    final /* synthetic */ int val$deltaY;
    final /* synthetic */ ViewHolder val$holder;

    DefaultItemAnimator$6(DefaultItemAnimator this$0, ViewHolder viewHolder, int i, int i2, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        this.this$0 = this$0;
        this.val$holder = viewHolder;
        this.val$deltaX = i;
        this.val$deltaY = i2;
        this.val$animation = viewPropertyAnimatorCompat;
    }

    public void onAnimationStart(View view) {
        this.this$0.dispatchMoveStarting(this.val$holder);
    }

    public void onAnimationCancel(View view) {
        if (this.val$deltaX != 0) {
            ViewCompat.setTranslationX(view, 0.0f);
        }
        if (this.val$deltaY != 0) {
            ViewCompat.setTranslationY(view, 0.0f);
        }
    }

    public void onAnimationEnd(View view) {
        this.val$animation.setListener(null);
        this.this$0.dispatchMoveFinished(this.val$holder);
        this.this$0.mMoveAnimations.remove(this.val$holder);
        this.this$0.dispatchFinishedWhenDone();
    }
}
