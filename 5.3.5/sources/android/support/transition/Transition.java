package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.os.Build.VERSION;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class Transition implements TransitionInterface {
    TransitionImpl mImpl;

    public interface TransitionListener extends TransitionInterfaceListener<Transition> {
        void onTransitionCancel(@NonNull Transition transition);

        void onTransitionEnd(@NonNull Transition transition);

        void onTransitionPause(@NonNull Transition transition);

        void onTransitionResume(@NonNull Transition transition);

        void onTransitionStart(@NonNull Transition transition);
    }

    public abstract void captureEndValues(@NonNull TransitionValues transitionValues);

    public abstract void captureStartValues(@NonNull TransitionValues transitionValues);

    public Transition() {
        this(false);
    }

    Transition(boolean deferred) {
        if (!deferred) {
            if (VERSION.SDK_INT >= 23) {
                this.mImpl = new TransitionApi23();
            } else if (VERSION.SDK_INT >= 19) {
                this.mImpl = new TransitionKitKat();
            } else {
                this.mImpl = new TransitionIcs();
            }
            this.mImpl.init(this);
        }
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener listener) {
        this.mImpl.addListener(listener);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull View target) {
        this.mImpl.addTarget(target);
        return this;
    }

    @NonNull
    public Transition addTarget(@IdRes int targetId) {
        this.mImpl.addTarget(targetId);
        return this;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return null;
    }

    @NonNull
    public Transition excludeChildren(@NonNull View target, boolean exclude) {
        this.mImpl.excludeChildren(target, exclude);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@IdRes int targetId, boolean exclude) {
        this.mImpl.excludeChildren(targetId, exclude);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class type, boolean exclude) {
        this.mImpl.excludeChildren(type, exclude);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull View target, boolean exclude) {
        this.mImpl.excludeTarget(target, exclude);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int targetId, boolean exclude) {
        this.mImpl.excludeTarget(targetId, exclude);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class type, boolean exclude) {
        this.mImpl.excludeTarget(type, exclude);
        return this;
    }

    public long getDuration() {
        return this.mImpl.getDuration();
    }

    @NonNull
    public Transition setDuration(long duration) {
        this.mImpl.setDuration(duration);
        return this;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.mImpl.getInterpolator();
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator interpolator) {
        this.mImpl.setInterpolator(interpolator);
        return this;
    }

    @NonNull
    public String getName() {
        return this.mImpl.getName();
    }

    public long getStartDelay() {
        return this.mImpl.getStartDelay();
    }

    @NonNull
    public Transition setStartDelay(long startDelay) {
        this.mImpl.setStartDelay(startDelay);
        return this;
    }

    @NonNull
    public List<Integer> getTargetIds() {
        return this.mImpl.getTargetIds();
    }

    @NonNull
    public List<View> getTargets() {
        return this.mImpl.getTargets();
    }

    @Nullable
    public String[] getTransitionProperties() {
        return this.mImpl.getTransitionProperties();
    }

    @NonNull
    public TransitionValues getTransitionValues(@NonNull View view, boolean start) {
        return this.mImpl.getTransitionValues(view, start);
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener listener) {
        this.mImpl.removeListener(listener);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View target) {
        this.mImpl.removeTarget(target);
        return this;
    }

    @NonNull
    public Transition removeTarget(@IdRes int targetId) {
        this.mImpl.removeTarget(targetId);
        return this;
    }

    public String toString() {
        return this.mImpl.toString();
    }
}
