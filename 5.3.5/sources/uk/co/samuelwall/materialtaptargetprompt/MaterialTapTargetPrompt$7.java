package uk.co.samuelwall.materialtaptargetprompt;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;

class MaterialTapTargetPrompt$7 implements AnimatorUpdateListener {
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$7(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    @TargetApi(11)
    public void onAnimationUpdate(ValueAnimator animation) {
        this.this$0.mRevealedAmount = ((Float) animation.getAnimatedValue()).floatValue();
        this.this$0.mView.mBackgroundRadius = this.this$0.mBaseBackgroundRadius * this.this$0.mRevealedAmount;
        this.this$0.mView.mFocalRadius = this.this$0.mBaseFocalRadius * this.this$0.mRevealedAmount;
        this.this$0.mView.mPaintFocal.setAlpha((int) (((float) this.this$0.mBaseFocalColourAlpha) * this.this$0.mRevealedAmount));
        this.this$0.mView.mPaintBackground.setAlpha((int) (((float) this.this$0.mBaseBackgroundColourAlpha) * this.this$0.mRevealedAmount));
        if (this.this$0.mPaintSecondaryText != null) {
            this.this$0.mPaintSecondaryText.setAlpha((int) (((float) this.this$0.mSecondaryTextColourAlpha) * this.this$0.mRevealedAmount));
        }
        if (this.this$0.mPaintPrimaryText != null) {
            this.this$0.mPaintPrimaryText.setAlpha((int) (((float) this.this$0.mPrimaryTextColourAlpha) * this.this$0.mRevealedAmount));
        }
        if (this.this$0.mView.mIconDrawable != null) {
            this.this$0.mView.mIconDrawable.setAlpha(this.this$0.mView.mPaintBackground.getAlpha());
        }
        this.this$0.mView.mBackgroundPosition.set(this.this$0.mView.mFocalCentre.x + ((this.this$0.mBaseBackgroundPosition.x - this.this$0.mView.mFocalCentre.x) * this.this$0.mRevealedAmount), this.this$0.mView.mFocalCentre.y + ((this.this$0.mBaseBackgroundPosition.y - this.this$0.mView.mFocalCentre.y) * this.this$0.mRevealedAmount));
        this.this$0.mView.invalidate();
    }
}
