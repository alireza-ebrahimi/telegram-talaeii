package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class VisibilityIcs extends TransitionIcs implements VisibilityImpl {

    private static class VisibilityWrapper extends VisibilityPort {
        private VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibility) {
            this.mVisibility = visibility;
        }

        public void captureStartValues(TransitionValues transitionValues) {
            this.mVisibility.captureStartValues(transitionValues);
        }

        public void captureEndValues(TransitionValues transitionValues) {
            this.mVisibility.captureEndValues(transitionValues);
        }

        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            return this.mVisibility.createAnimator(sceneRoot, startValues, endValues);
        }

        public boolean isVisible(TransitionValues values) {
            return this.mVisibility.isVisible(values);
        }

        public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
            return this.mVisibility.onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
        }

        public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
            return this.mVisibility.onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
        }
    }

    VisibilityIcs() {
    }

    public void init(TransitionInterface external, Object internal) {
        this.mExternalTransition = external;
        if (internal == null) {
            this.mTransition = new VisibilityWrapper((VisibilityInterface) external);
        } else {
            this.mTransition = (VisibilityPort) internal;
        }
    }

    public boolean isVisible(TransitionValues values) {
        return ((VisibilityPort) this.mTransition).isVisible(values);
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((VisibilityPort) this.mTransition).onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }

    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((VisibilityPort) this.mTransition).onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }
}
