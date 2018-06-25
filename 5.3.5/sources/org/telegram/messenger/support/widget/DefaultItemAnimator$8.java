package org.telegram.messenger.support.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

class DefaultItemAnimator$8 extends DefaultItemAnimator$VpaListenerAdapter {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ DefaultItemAnimator$ChangeInfo val$changeInfo;
    final /* synthetic */ View val$newView;
    final /* synthetic */ ViewPropertyAnimatorCompat val$newViewAnimation;

    DefaultItemAnimator$8(DefaultItemAnimator this$0, DefaultItemAnimator$ChangeInfo defaultItemAnimator$ChangeInfo, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, View view) {
        this.this$0 = this$0;
        this.val$changeInfo = defaultItemAnimator$ChangeInfo;
        this.val$newViewAnimation = viewPropertyAnimatorCompat;
        this.val$newView = view;
    }

    public void onAnimationStart(View view) {
        this.this$0.dispatchChangeStarting(this.val$changeInfo.newHolder, false);
    }

    public void onAnimationEnd(View view) {
        this.val$newViewAnimation.setListener(null);
        ViewCompat.setAlpha(this.val$newView, 1.0f);
        ViewCompat.setTranslationX(this.val$newView, 0.0f);
        ViewCompat.setTranslationY(this.val$newView, 0.0f);
        this.this$0.dispatchChangeFinished(this.val$changeInfo.newHolder, false);
        this.this$0.mChangeAnimations.remove(this.val$changeInfo.newHolder);
        this.this$0.dispatchFinishedWhenDone();
    }
}
