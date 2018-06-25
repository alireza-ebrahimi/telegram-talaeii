package uk.co.samuelwall.materialtaptargetprompt;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;

class MaterialTapTargetPrompt$3 implements AnimatorUpdateListener {
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$3(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    @TargetApi(11)
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = ((Float) animation.getAnimatedValue()).floatValue();
        this.this$0.mRevealedAmount = ((1.0f - value) / 4.0f) + 1.0f;
        this.this$0.mView.mBackgroundRadius = this.this$0.mBaseBackgroundRadius * this.this$0.mRevealedAmount;
        this.this$0.mView.mFocalRadius = this.this$0.mBaseFocalRadius * this.this$0.mRevealedAmount;
        this.this$0.mView.mPaintFocal.setAlpha((int) (((float) this.this$0.mBaseFocalColourAlpha) * value));
        this.this$0.mView.mPaintBackground.setAlpha((int) (((float) this.this$0.mBaseBackgroundColourAlpha) * value));
        if (this.this$0.mPaintSecondaryText != null) {
            this.this$0.mPaintSecondaryText.setAlpha((int) (((float) this.this$0.mSecondaryTextColourAlpha) * value));
        }
        if (this.this$0.mPaintPrimaryText != null) {
            this.this$0.mPaintPrimaryText.setAlpha((int) (((float) this.this$0.mPrimaryTextColourAlpha) * value));
        }
        if (this.this$0.mView.mIconDrawable != null) {
            this.this$0.mView.mIconDrawable.setAlpha(this.this$0.mView.mPaintBackground.getAlpha());
        }
        this.this$0.mView.invalidate();
    }
}
