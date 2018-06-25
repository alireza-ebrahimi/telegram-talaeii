package uk.co.samuelwall.materialtaptargetprompt;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.os.Build.VERSION;

class MaterialTapTargetPrompt$10 implements AnimatorUpdateListener {
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$10(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    @TargetApi(11)
    public void onAnimationUpdate(ValueAnimator animation) {
        float fraction;
        this.this$0.mView.mFocalRippleSize = ((Float) animation.getAnimatedValue()).floatValue();
        if (VERSION.SDK_INT >= 12) {
            fraction = animation.getAnimatedFraction();
        } else {
            fraction = (this.this$0.mFocalRadius10Percent * 6.0f) / ((this.this$0.mView.mFocalRippleSize - this.this$0.mBaseFocalRadius) - this.this$0.mFocalRadius10Percent);
        }
        this.this$0.mView.mFocalRippleAlpha = (int) (((float) this.this$0.mBaseFocalRippleAlpha) * (1.0f - fraction));
    }
}
