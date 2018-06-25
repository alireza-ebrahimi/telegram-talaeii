package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(21)
@RequiresApi(21)
class SceneStaticsApi21 extends SceneStaticsImpl {
    SceneStaticsApi21() {
    }

    public SceneImpl getSceneForLayout(ViewGroup sceneRoot, int layoutId, Context context) {
        SceneApi21 scene = new SceneApi21();
        scene.mScene = Scene.getSceneForLayout(sceneRoot, layoutId, context);
        return scene;
    }
}
