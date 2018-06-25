package uk.co.samuelwall.materialtaptargetprompt;

import android.support.annotation.Nullable;
import android.view.MotionEvent;

public interface MaterialTapTargetPrompt$PromptView$PromptTouchedListener {
    void onFocalPressed();

    void onNonFocalPressed();

    @Deprecated
    void onPromptTouched(@Nullable MotionEvent motionEvent, boolean z);
}
