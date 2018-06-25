package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class FadeKitKat extends TransitionKitKat implements VisibilityImpl {
    public FadeKitKat(TransitionInterface transition) {
        init(transition, new Fade());
    }

    public FadeKitKat(TransitionInterface transition, int fadingMode) {
        init(transition, new Fade(fadingMode));
    }

    public boolean isVisible(TransitionValues values) {
        return ((Fade) this.mTransition).isVisible(TransitionKitKat.convertToPlatform(values));
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((Fade) this.mTransition).onAppear(sceneRoot, TransitionKitKat.convertToPlatform(startValues), startVisibility, TransitionKitKat.convertToPlatform(endValues), endVisibility);
    }

    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((Fade) this.mTransition).onDisappear(sceneRoot, TransitionKitKat.convertToPlatform(startValues), startVisibility, TransitionKitKat.convertToPlatform(endValues), endVisibility);
    }
}
