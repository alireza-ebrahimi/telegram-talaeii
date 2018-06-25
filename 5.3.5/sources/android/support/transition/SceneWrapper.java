package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
abstract class SceneWrapper extends SceneImpl {
    Scene mScene;

    SceneWrapper() {
    }

    public ViewGroup getSceneRoot() {
        return this.mScene.getSceneRoot();
    }

    public void exit() {
        this.mScene.exit();
    }

    public void setEnterAction(Runnable action) {
        this.mScene.setEnterAction(action);
    }

    public void setExitAction(Runnable action) {
        this.mScene.setExitAction(action);
    }
}
