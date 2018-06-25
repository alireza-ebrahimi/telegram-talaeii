package uk.co.samuelwall.materialtaptargetprompt;

import android.animation.Animator;
import android.annotation.TargetApi;

class MaterialTapTargetPrompt$6 extends MaterialTapTargetPrompt$AnimatorListener {
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$6(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    @TargetApi(11)
    public void onAnimationEnd(Animator animation) {
        this.this$0.cleanUpPrompt(6);
    }

    @TargetApi(11)
    public void onAnimationCancel(Animator animation) {
        this.this$0.cleanUpPrompt(6);
    }
}
