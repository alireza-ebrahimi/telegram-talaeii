package org.telegram.messenger.support.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

class DefaultItemAnimator$7 extends DefaultItemAnimator$VpaListenerAdapter {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ DefaultItemAnimator$ChangeInfo val$changeInfo;
    final /* synthetic */ ViewPropertyAnimatorCompat val$oldViewAnim;

    DefaultItemAnimator$7(DefaultItemAnimator this$0, DefaultItemAnimator$ChangeInfo defaultItemAnimator$ChangeInfo, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        this.this$0 = this$0;
        this.val$changeInfo = defaultItemAnimator$ChangeInfo;
        this.val$oldViewAnim = viewPropertyAnimatorCompat;
    }

    public void onAnimationStart(View view) {
        this.this$0.dispatchChangeStarting(this.val$changeInfo.oldHolder, true);
    }

    public void onAnimationEnd(View view) {
        this.val$oldViewAnim.setListener(null);
        ViewCompat.setAlpha(view, 1.0f);
        ViewCompat.setTranslationX(view, 0.0f);
        ViewCompat.setTranslationY(view, 0.0f);
        this.this$0.dispatchChangeFinished(this.val$changeInfo.oldHolder, true);
        this.this$0.mChangeAnimations.remove(this.val$changeInfo.oldHolder);
        this.this$0.dispatchFinishedWhenDone();
    }
}
