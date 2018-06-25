package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
class ChangeBoundsIcs extends TransitionIcs implements ChangeBoundsInterface {
    public ChangeBoundsIcs(TransitionInterface transition) {
        init(transition, new ChangeBoundsPort());
    }

    public void setResizeClip(boolean resizeClip) {
        ((ChangeBoundsPort) this.mTransition).setResizeClip(resizeClip);
    }
}
