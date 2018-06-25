package org.telegram.messenger.support.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class DefaultItemAnimator$4 extends DefaultItemAnimator$VpaListenerAdapter {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ ViewPropertyAnimatorCompat val$animation;
    final /* synthetic */ ViewHolder val$holder;

    DefaultItemAnimator$4(DefaultItemAnimator this$0, ViewHolder viewHolder, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        this.this$0 = this$0;
        this.val$holder = viewHolder;
        this.val$animation = viewPropertyAnimatorCompat;
    }

    public void onAnimationStart(View view) {
        this.this$0.dispatchRemoveStarting(this.val$holder);
    }

    public void onAnimationEnd(View view) {
        this.val$animation.setListener(null);
        ViewCompat.setAlpha(view, 1.0f);
        this.this$0.dispatchRemoveFinished(this.val$holder);
        this.this$0.mRemoveAnimations.remove(this.val$holder);
        this.this$0.dispatchFinishedWhenDone();
    }
}
