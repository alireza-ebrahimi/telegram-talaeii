package uk.co.samuelwall.materialtaptargetprompt;

import android.view.MotionEvent;

class MaterialTapTargetPrompt$1 implements MaterialTapTargetPrompt$PromptView$PromptTouchedListener {
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$1(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    public void onPromptTouched(MotionEvent event, boolean tappedTarget) {
        if (!this.this$0.mIsDismissingOld) {
            if (tappedTarget) {
                if (this.this$0.mAutoFinish) {
                    this.this$0.finish();
                    this.this$0.mIsDismissingOld = true;
                }
            } else if (this.this$0.mAutoDismiss) {
                this.this$0.dismiss();
                this.this$0.mIsDismissingOld = true;
            }
            this.this$0.onHidePrompt(event, tappedTarget);
        }
    }

    public void onFocalPressed() {
        if (!this.this$0.mDismissing) {
            if (this.this$0.mAutoFinish) {
                this.this$0.finish();
            }
            this.this$0.onPromptStateChanged(3);
        }
    }

    public void onNonFocalPressed() {
        if (!this.this$0.mDismissing) {
            if (this.this$0.mAutoDismiss) {
                this.this$0.dismiss();
            }
            this.this$0.onPromptStateChanged(5);
        }
    }
}
