package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class VisibilityKitKat extends TransitionKitKat implements VisibilityImpl {

    private static class VisibilityWrapper extends Visibility {
        private final VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibility) {
            this.mVisibility = visibility;
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureStartValues(this.mVisibility, transitionValues);
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureEndValues(this.mVisibility, transitionValues);
        }

        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            return this.mVisibility.createAnimator(sceneRoot, TransitionKitKat.convertToSupport(startValues), TransitionKitKat.convertToSupport(endValues));
        }

        public boolean isVisible(TransitionValues values) {
            if (values == null) {
                return false;
            }
            TransitionValues externalValues = new TransitionValues();
            TransitionKitKat.copyValues(values, externalValues);
            return this.mVisibility.isVisible(externalValues);
        }

        public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
            return this.mVisibility.onAppear(sceneRoot, TransitionKitKat.convertToSupport(startValues), startVisibility, TransitionKitKat.convertToSupport(endValues), endVisibility);
        }

        public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
            return this.mVisibility.onDisappear(sceneRoot, TransitionKitKat.convertToSupport(startValues), startVisibility, TransitionKitKat.convertToSupport(endValues), endVisibility);
        }
    }

    VisibilityKitKat() {
    }

    public void init(TransitionInterface external, Object internal) {
        this.mExternalTransition = external;
        if (internal == null) {
            this.mTransition = new VisibilityWrapper((VisibilityInterface) external);
        } else {
            this.mTransition = (Visibility) internal;
        }
    }

    public boolean isVisible(TransitionValues values) {
        return ((Visibility) this.mTransition).isVisible(TransitionKitKat.convertToPlatform(values));
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((Visibility) this.mTransition).onAppear(sceneRoot, TransitionKitKat.convertToPlatform(startValues), startVisibility, TransitionKitKat.convertToPlatform(endValues), endVisibility);
    }

    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((Visibility) this.mTransition).onDisappear(sceneRoot, TransitionKitKat.convertToPlatform(startValues), startVisibility, TransitionKitKat.convertToPlatform(endValues), endVisibility);
    }
}
