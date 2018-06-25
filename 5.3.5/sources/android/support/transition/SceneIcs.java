package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class SceneIcs extends SceneImpl {
    ScenePort mScene;

    SceneIcs() {
    }

    public void init(ViewGroup sceneRoot) {
        this.mScene = new ScenePort(sceneRoot);
    }

    public void init(ViewGroup sceneRoot, View layout) {
        this.mScene = new ScenePort(sceneRoot, layout);
    }

    public void enter() {
        this.mScene.enter();
    }

    public void exit() {
        this.mScene.exit();
    }

    public ViewGroup getSceneRoot() {
        return this.mScene.getSceneRoot();
    }

    public void setEnterAction(Runnable action) {
        this.mScene.setEnterAction(action);
    }

    public void setExitAction(Runnable action) {
        this.mScene.setExitAction(action);
    }
}
