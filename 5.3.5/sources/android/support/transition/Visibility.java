package android.support.transition;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public abstract class Visibility extends Transition implements VisibilityInterface {
    public Visibility() {
        this(false);
    }

    Visibility(boolean deferred) {
        super(true);
        if (!deferred) {
            if (VERSION.SDK_INT >= 19) {
                this.mImpl = new VisibilityKitKat();
            } else {
                this.mImpl = new VisibilityIcs();
            }
            this.mImpl.init(this);
        }
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureEndValues(transitionValues);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureStartValues(transitionValues);
    }

    public boolean isVisible(TransitionValues values) {
        return ((VisibilityImpl) this.mImpl).isVisible(values);
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((VisibilityImpl) this.mImpl).onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }

    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return ((VisibilityImpl) this.mImpl).onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }
}
