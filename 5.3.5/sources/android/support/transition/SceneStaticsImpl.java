package android.support.transition;

import android.content.Context;
import android.view.ViewGroup;

abstract class SceneStaticsImpl {
    public abstract SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context);

    SceneStaticsImpl() {
    }
}
