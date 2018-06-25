package uk.co.samuelwall.materialtaptargetprompt;

import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class MaterialTapTargetPrompt$2 implements OnGlobalLayoutListener {
    final /* synthetic */ MaterialTapTargetPrompt this$0;

    MaterialTapTargetPrompt$2(MaterialTapTargetPrompt this$0) {
        this.this$0 = this$0;
    }

    public void onGlobalLayout() {
        this.this$0.updateFocalCentrePosition();
    }
}
