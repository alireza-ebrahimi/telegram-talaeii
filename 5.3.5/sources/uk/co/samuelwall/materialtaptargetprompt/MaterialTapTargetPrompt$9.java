package uk.co.samuelwall.materialtaptargetprompt;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;

class MaterialTapTargetPrompt$9 implements AnimatorUpdateListener {
    boolean direction = true;
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$9(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    @TargetApi(11)
    public void onAnimationUpdate(ValueAnimator animation) {
        float newFocalFraction = ((Float) animation.getAnimatedValue()).floatValue();
        boolean newDirection = this.direction;
        if (newFocalFraction < this.this$0.mFocalRippleProgress && this.direction) {
            newDirection = false;
        } else if (newFocalFraction > this.this$0.mFocalRippleProgress && !this.direction) {
            newDirection = true;
        }
        if (!(newDirection == this.direction || newDirection)) {
            this.this$0.mAnimationFocalRipple.start();
        }
        this.direction = newDirection;
        this.this$0.mFocalRippleProgress = newFocalFraction;
        this.this$0.mView.mFocalRadius = this.this$0.mBaseFocalRadius + this.this$0.mFocalRippleProgress;
        this.this$0.mView.invalidate();
    }
}
