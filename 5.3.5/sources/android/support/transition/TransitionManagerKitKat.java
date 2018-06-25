package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionManager;

@TargetApi(19)
@RequiresApi(19)
class TransitionManagerKitKat extends TransitionManagerImpl {
    private final TransitionManager mTransitionManager = new TransitionManager();

    TransitionManagerKitKat() {
    }

    public void setTransition(SceneImpl scene, TransitionImpl transition) {
        this.mTransitionManager.setTransition(((SceneWrapper) scene).mScene, transition == null ? null : ((TransitionKitKat) transition).mTransition);
    }

    public void setTransition(SceneImpl fromScene, SceneImpl toScene, TransitionImpl transition) {
        this.mTransitionManager.setTransition(((SceneWrapper) fromScene).mScene, ((SceneWrapper) toScene).mScene, transition == null ? null : ((TransitionKitKat) transition).mTransition);
    }

    public void transitionTo(SceneImpl scene) {
        this.mTransitionManager.transitionTo(((SceneWrapper) scene).mScene);
    }
}
