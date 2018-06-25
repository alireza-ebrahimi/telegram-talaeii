package android.support.transition;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class Scene {
    private static SceneStaticsImpl sImpl;
    SceneImpl mImpl;

    static {
        if (VERSION.SDK_INT >= 21) {
            sImpl = new SceneStaticsApi21();
        } else if (VERSION.SDK_INT >= 19) {
            sImpl = new SceneStaticsKitKat();
        } else {
            sImpl = new SceneStaticsIcs();
        }
    }

    public Scene(@NonNull ViewGroup sceneRoot) {
        this.mImpl = createSceneImpl();
        this.mImpl.init(sceneRoot);
    }

    public Scene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        this.mImpl = createSceneImpl();
        this.mImpl.init(sceneRoot, layout);
    }

    private Scene(SceneImpl scene) {
        this.mImpl = scene;
    }

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId, @NonNull Context context) {
        SparseArray<Scene> scenes = (SparseArray) sceneRoot.getTag(C0093R.id.transition_scene_layoutid_cache);
        if (scenes == null) {
            scenes = new SparseArray();
            sceneRoot.setTag(C0093R.id.transition_scene_layoutid_cache, scenes);
        }
        Scene scene = (Scene) scenes.get(layoutId);
        if (scene != null) {
            return scene;
        }
        scene = new Scene(sImpl.getSceneForLayout(sceneRoot, layoutId, context));
        scenes.put(layoutId, scene);
        return scene;
    }

    private SceneImpl createSceneImpl() {
        if (VERSION.SDK_INT >= 21) {
            return new SceneApi21();
        }
        if (VERSION.SDK_INT >= 19) {
            return new SceneKitKat();
        }
        return new SceneIcs();
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.mImpl.getSceneRoot();
    }

    public void exit() {
        this.mImpl.exit();
    }

    public void enter() {
        this.mImpl.enter();
    }

    public void setEnterAction(@Nullable Runnable action) {
        this.mImpl.setEnterAction(action);
    }

    public void setExitAction(@Nullable Runnable action) {
        this.mImpl.setExitAction(action);
    }
}
