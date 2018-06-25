package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionManager;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class TransitionManagerStaticsKitKat extends TransitionManagerStaticsImpl {
    TransitionManagerStaticsKitKat() {
    }

    public void go(SceneImpl scene) {
        TransitionManager.go(((SceneWrapper) scene).mScene);
    }

    public void go(SceneImpl scene, TransitionImpl transition) {
        TransitionManager.go(((SceneWrapper) scene).mScene, transition == null ? null : ((TransitionKitKat) transition).mTransition);
    }

    public void beginDelayedTransition(ViewGroup sceneRoot) {
        TransitionManager.beginDelayedTransition(sceneRoot);
    }

    public void beginDelayedTransition(ViewGroup sceneRoot, TransitionImpl transition) {
        TransitionManager.beginDelayedTransition(sceneRoot, transition == null ? null : ((TransitionKitKat) transition).mTransition);
    }
}
