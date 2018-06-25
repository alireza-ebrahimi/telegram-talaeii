package android.support.transition;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public class TransitionSet extends Transition {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;

    public TransitionSet() {
        super(true);
        if (VERSION.SDK_INT < 19) {
            this.mImpl = new TransitionSetIcs(this);
        } else {
            this.mImpl = new TransitionSetKitKat(this);
        }
    }

    public int getOrdering() {
        return ((TransitionSetImpl) this.mImpl).getOrdering();
    }

    @NonNull
    public TransitionSet setOrdering(int ordering) {
        ((TransitionSetImpl) this.mImpl).setOrdering(ordering);
        return this;
    }

    @NonNull
    public TransitionSet addTransition(@NonNull Transition transition) {
        ((TransitionSetImpl) this.mImpl).addTransition(transition.mImpl);
        return this;
    }

    @NonNull
    public TransitionSet removeTransition(@NonNull Transition transition) {
        ((TransitionSetImpl) this.mImpl).removeTransition(transition.mImpl);
        return this;
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureEndValues(transitionValues);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureStartValues(transitionValues);
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @NonNull TransitionValues startValues, @NonNull TransitionValues endValues) {
        return this.mImpl.createAnimator(sceneRoot, startValues, endValues);
    }
}
