package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(14)
@RequiresApi(14)
class TransitionManagerIcs extends TransitionManagerImpl {
    private final TransitionManagerPort mTransitionManager = new TransitionManagerPort();

    TransitionManagerIcs() {
    }

    public void setTransition(SceneImpl scene, TransitionImpl transition) {
        this.mTransitionManager.setTransition(((SceneIcs) scene).mScene, transition == null ? null : ((TransitionIcs) transition).mTransition);
    }

    public void setTransition(SceneImpl fromScene, SceneImpl toScene, TransitionImpl transition) {
        this.mTransitionManager.setTransition(((SceneIcs) fromScene).mScene, ((SceneIcs) toScene).mScene, transition == null ? null : ((TransitionIcs) transition).mTransition);
    }

    public void transitionTo(SceneImpl scene) {
        this.mTransitionManager.transitionTo(((SceneIcs) scene).mScene);
    }
}
