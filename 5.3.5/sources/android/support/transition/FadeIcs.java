package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class FadeIcs extends TransitionIcs implements VisibilityImpl {
    public FadeIcs(TransitionInterface transition) {
        init(transition, new FadePort());
    }

    public FadeIcs(TransitionInterface transition, int fadingMode) {
        init(transition, new FadePort(fadingMode));
    }

    public boolean isVisible(TransitionValues values) {
        return ((FadePort) this.mTransition).isVisible(values);
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((FadePort) this.mTransition).onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }

    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((FadePort) this.mTransition).onDisappear(sceneRoot, startValues, startVisibility, startValues, startVisibility);
    }
}
