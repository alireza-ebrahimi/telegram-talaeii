package org.telegram.messenger.support.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class DefaultItemAnimator$5 extends DefaultItemAnimator$VpaListenerAdapter {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ ViewPropertyAnimatorCompat val$animation;
    final /* synthetic */ ViewHolder val$holder;

    DefaultItemAnimator$5(DefaultItemAnimator this$0, ViewHolder viewHolder, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        this.this$0 = this$0;
        this.val$holder = viewHolder;
        this.val$animation = viewPropertyAnimatorCompat;
    }

    public void onAnimationStart(View view) {
        this.this$0.dispatchAddStarting(this.val$holder);
    }

    public void onAnimationCancel(View view) {
        ViewCompat.setAlpha(view, 1.0f);
    }

    public void onAnimationEnd(View view) {
        this.val$animation.setListener(null);
        this.this$0.dispatchAddFinished(this.val$holder);
        this.this$0.mAddAnimations.remove(this.val$holder);
        this.this$0.dispatchFinishedWhenDone();
    }
}
