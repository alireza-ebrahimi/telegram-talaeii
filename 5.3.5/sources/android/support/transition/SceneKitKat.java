package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(19)
@RequiresApi(19)
class SceneKitKat extends SceneWrapper {
    private static Field sEnterAction;
    private static Method sSetCurrentScene;
    private View mLayout;

    SceneKitKat() {
    }

    public void init(ViewGroup sceneRoot) {
        this.mScene = new Scene(sceneRoot);
    }

    public void init(ViewGroup sceneRoot, View layout) {
        if (layout instanceof ViewGroup) {
            this.mScene = new Scene(sceneRoot, (ViewGroup) layout);
            return;
        }
        this.mScene = new Scene(sceneRoot);
        this.mLayout = layout;
    }

    public void enter() {
        if (this.mLayout != null) {
            ViewGroup root = getSceneRoot();
            root.removeAllViews();
            root.addView(this.mLayout);
            invokeEnterAction();
            updateCurrentScene(root);
            return;
        }
        this.mScene.enter();
    }

    private void invokeEnterAction() {
        if (sEnterAction == null) {
            try {
                sEnterAction = Scene.class.getDeclaredField("mEnterAction");
                sEnterAction.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Runnable enterAction = (Runnable) sEnterAction.get(this.mScene);
            if (enterAction != null) {
                enterAction.run();
            }
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private void updateCurrentScene(View view) {
        ReflectiveOperationException e;
        if (sSetCurrentScene == null) {
            try {
                sSetCurrentScene = Scene.class.getDeclaredMethod("setCurrentScene", new Class[]{View.class, Scene.class});
                sSetCurrentScene.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                throw new RuntimeException(e2);
            }
        }
        try {
            sSetCurrentScene.invoke(null, new Object[]{view, this.mScene});
        } catch (IllegalAccessException e3) {
            e = e3;
            throw new RuntimeException(e);
        } catch (InvocationTargetException e4) {
            e = e4;
            throw new RuntimeException(e);
        }
    }
}
