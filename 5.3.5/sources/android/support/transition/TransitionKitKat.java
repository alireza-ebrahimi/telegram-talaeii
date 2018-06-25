package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(19)
@RequiresApi(19)
class TransitionKitKat extends TransitionImpl {
    private CompatListener mCompatListener;
    TransitionInterface mExternalTransition;
    Transition mTransition;

    private class CompatListener implements TransitionListener {
        private final ArrayList<TransitionInterfaceListener> mListeners = new ArrayList();

        CompatListener() {
        }

        void addListener(TransitionInterfaceListener listener) {
            this.mListeners.add(listener);
        }

        void removeListener(TransitionInterfaceListener listener) {
            this.mListeners.remove(listener);
        }

        boolean isEmpty() {
            return this.mListeners.isEmpty();
        }

        public void onTransitionStart(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionStart(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionEnd(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionEnd(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionCancel(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionCancel(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionPause(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionPause(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionResume(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionResume(TransitionKitKat.this.mExternalTransition);
            }
        }
    }

    private static class TransitionWrapper extends Transition {
        private TransitionInterface mTransition;

        public TransitionWrapper(TransitionInterface transition) {
            this.mTransition = transition;
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureStartValues(this.mTransition, transitionValues);
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureEndValues(this.mTransition, transitionValues);
        }

        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            return this.mTransition.createAnimator(sceneRoot, TransitionKitKat.convertToSupport(startValues), TransitionKitKat.convertToSupport(endValues));
        }
    }

    TransitionKitKat() {
    }

    static void copyValues(TransitionValues source, TransitionValues dest) {
        if (source != null) {
            dest.view = source.view;
            if (source.values.size() > 0) {
                dest.values.putAll(source.values);
            }
        }
    }

    static void copyValues(TransitionValues source, TransitionValues dest) {
        if (source != null) {
            dest.view = source.view;
            if (source.values.size() > 0) {
                dest.values.putAll(source.values);
            }
        }
    }

    static void wrapCaptureStartValues(TransitionInterface transition, TransitionValues transitionValues) {
        TransitionValues externalValues = new TransitionValues();
        copyValues(transitionValues, externalValues);
        transition.captureStartValues(externalValues);
        copyValues(externalValues, transitionValues);
    }

    static void wrapCaptureEndValues(TransitionInterface transition, TransitionValues transitionValues) {
        TransitionValues externalValues = new TransitionValues();
        copyValues(transitionValues, externalValues);
        transition.captureEndValues(externalValues);
        copyValues(externalValues, transitionValues);
    }

    static TransitionValues convertToSupport(TransitionValues values) {
        if (values == null) {
            return null;
        }
        TransitionValues supportValues = new TransitionValues();
        copyValues(values, supportValues);
        return supportValues;
    }

    static TransitionValues convertToPlatform(TransitionValues values) {
        if (values == null) {
            return null;
        }
        TransitionValues platformValues = new TransitionValues();
        copyValues(values, platformValues);
        return platformValues;
    }

    public void init(TransitionInterface external, Object internal) {
        this.mExternalTransition = external;
        if (internal == null) {
            this.mTransition = new TransitionWrapper(external);
        } else {
            this.mTransition = (Transition) internal;
        }
    }

    public TransitionImpl addListener(TransitionInterfaceListener listener) {
        if (this.mCompatListener == null) {
            this.mCompatListener = new CompatListener();
            this.mTransition.addListener(this.mCompatListener);
        }
        this.mCompatListener.addListener(listener);
        return this;
    }

    public TransitionImpl removeListener(TransitionInterfaceListener listener) {
        if (this.mCompatListener != null) {
            this.mCompatListener.removeListener(listener);
            if (this.mCompatListener.isEmpty()) {
                this.mTransition.removeListener(this.mCompatListener);
                this.mCompatListener = null;
            }
        }
        return this;
    }

    public TransitionImpl addTarget(View target) {
        this.mTransition.addTarget(target);
        return this;
    }

    public TransitionImpl addTarget(int targetId) {
        this.mTransition.addTarget(targetId);
        return this;
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues internalValues = new TransitionValues();
        copyValues(transitionValues, internalValues);
        this.mTransition.captureEndValues(internalValues);
        copyValues(internalValues, transitionValues);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues internalValues = new TransitionValues();
        copyValues(transitionValues, internalValues);
        this.mTransition.captureStartValues(internalValues);
        copyValues(internalValues, transitionValues);
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        TransitionValues internalStartValues;
        TransitionValues internalEndValues;
        if (startValues != null) {
            internalStartValues = new TransitionValues();
            copyValues(startValues, internalStartValues);
        } else {
            internalStartValues = null;
        }
        if (endValues != null) {
            internalEndValues = new TransitionValues();
            copyValues(endValues, internalEndValues);
        } else {
            internalEndValues = null;
        }
        return this.mTransition.createAnimator(sceneRoot, internalStartValues, internalEndValues);
    }

    public TransitionImpl excludeChildren(View target, boolean exclude) {
        this.mTransition.excludeChildren(target, exclude);
        return this;
    }

    public TransitionImpl excludeChildren(int targetId, boolean exclude) {
        this.mTransition.excludeChildren(targetId, exclude);
        return this;
    }

    public TransitionImpl excludeChildren(Class type, boolean exclude) {
        this.mTransition.excludeChildren(type, exclude);
        return this;
    }

    public TransitionImpl excludeTarget(View target, boolean exclude) {
        this.mTransition.excludeTarget(target, exclude);
        return this;
    }

    public TransitionImpl excludeTarget(int targetId, boolean exclude) {
        this.mTransition.excludeTarget(targetId, exclude);
        return this;
    }

    public TransitionImpl excludeTarget(Class type, boolean exclude) {
        this.mTransition.excludeTarget(type, exclude);
        return this;
    }

    public long getDuration() {
        return this.mTransition.getDuration();
    }

    public TransitionImpl setDuration(long duration) {
        this.mTransition.setDuration(duration);
        return this;
    }

    public TimeInterpolator getInterpolator() {
        return this.mTransition.getInterpolator();
    }

    public TransitionImpl setInterpolator(TimeInterpolator interpolator) {
        this.mTransition.setInterpolator(interpolator);
        return this;
    }

    public String getName() {
        return this.mTransition.getName();
    }

    public long getStartDelay() {
        return this.mTransition.getStartDelay();
    }

    public TransitionImpl setStartDelay(long startDelay) {
        this.mTransition.setStartDelay(startDelay);
        return this;
    }

    public List<Integer> getTargetIds() {
        return this.mTransition.getTargetIds();
    }

    public List<View> getTargets() {
        return this.mTransition.getTargets();
    }

    public String[] getTransitionProperties() {
        return this.mTransition.getTransitionProperties();
    }

    public TransitionValues getTransitionValues(View view, boolean start) {
        TransitionValues values = new TransitionValues();
        copyValues(this.mTransition.getTransitionValues(view, start), values);
        return values;
    }

    public TransitionImpl removeTarget(View target) {
        this.mTransition.removeTarget(target);
        return this;
    }

    public TransitionImpl removeTarget(int targetId) {
        if (targetId > 0) {
            getTargetIds().remove(Integer.valueOf(targetId));
        }
        return this;
    }

    public String toString() {
        return this.mTransition.toString();
    }
}
