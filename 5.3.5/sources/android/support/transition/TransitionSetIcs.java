package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
class TransitionSetIcs extends TransitionIcs implements TransitionSetImpl {
    private TransitionSetPort mTransitionSet = new TransitionSetPort();

    public TransitionSetIcs(TransitionInterface transition) {
        init(transition, this.mTransitionSet);
    }

    public int getOrdering() {
        return this.mTransitionSet.getOrdering();
    }

    public TransitionSetIcs setOrdering(int ordering) {
        this.mTransitionSet.setOrdering(ordering);
        return this;
    }

    public TransitionSetIcs addTransition(TransitionImpl transition) {
        this.mTransitionSet.addTransition(((TransitionIcs) transition).mTransition);
        return this;
    }

    public TransitionSetIcs removeTransition(TransitionImpl transition) {
        this.mTransitionSet.removeTransition(((TransitionIcs) transition).mTransition);
        return this;
    }
}
