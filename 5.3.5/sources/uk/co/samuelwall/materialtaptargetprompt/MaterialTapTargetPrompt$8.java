package uk.co.samuelwall.materialtaptargetprompt;

import android.animation.Animator;
import android.annotation.TargetApi;

class MaterialTapTargetPrompt$8 extends MaterialTapTargetPrompt$AnimatorListener {
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$8(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    @TargetApi(11)
    public void onAnimationEnd(Animator animation) {
        animation.removeAllListeners();
        this.this$0.mAnimationCurrent = null;
        this.this$0.mRevealedAmount = 1.0f;
        this.this$0.mView.mBackgroundPosition.set(this.this$0.mBaseBackgroundPosition);
        if (this.this$0.mIdleAnimationEnabled) {
            this.this$0.startIdleAnimations();
        }
        this.this$0.onPromptStateChanged(2);
    }

    @TargetApi(11)
    public void onAnimationCancel(Animator animation) {
        animation.removeAllListeners();
        this.this$0.mRevealedAmount = 1.0f;
        this.this$0.mView.mBackgroundPosition.set(this.this$0.mBaseBackgroundPosition);
        this.this$0.mAnimationCurrent = null;
    }
}
