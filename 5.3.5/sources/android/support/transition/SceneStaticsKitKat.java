package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class SceneStaticsKitKat extends SceneStaticsImpl {
    SceneStaticsKitKat() {
    }

    public SceneImpl getSceneForLayout(ViewGroup sceneRoot, int layoutId, Context context) {
        SceneKitKat scene = new SceneKitKat();
        scene.mScene = Scene.getSceneForLayout(sceneRoot, layoutId, context);
        return scene;
    }
}
