package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class TransitionManagerStaticsIcs extends TransitionManagerStaticsImpl {
    TransitionManagerStaticsIcs() {
    }

    public void go(SceneImpl scene) {
        TransitionManagerPort.go(((SceneIcs) scene).mScene);
    }

    public void go(SceneImpl scene, TransitionImpl transition) {
        TransitionManagerPort.go(((SceneIcs) scene).mScene, transition == null ? null : ((TransitionIcs) transition).mTransition);
    }

    public void beginDelayedTransition(ViewGroup sceneRoot) {
        TransitionManagerPort.beginDelayedTransition(sceneRoot);
    }

    public void beginDelayedTransition(ViewGroup sceneRoot, TransitionImpl transition) {
        TransitionManagerPort.beginDelayedTransition(sceneRoot, transition == null ? null : ((TransitionIcs) transition).mTransition);
    }
}
