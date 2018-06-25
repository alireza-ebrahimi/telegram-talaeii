package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
final class ScenePort {
    private Context mContext;
    Runnable mEnterAction;
    Runnable mExitAction;
    private View mLayout;
    private int mLayoutId = -1;
    private ViewGroup mSceneRoot;

    public ScenePort(ViewGroup sceneRoot) {
        this.mSceneRoot = sceneRoot;
    }

    private ScenePort(ViewGroup sceneRoot, int layoutId, Context context) {
        this.mContext = context;
        this.mSceneRoot = sceneRoot;
        this.mLayoutId = layoutId;
    }

    public ScenePort(ViewGroup sceneRoot, View layout) {
        this.mSceneRoot = sceneRoot;
        this.mLayout = layout;
    }

    public static ScenePort getSceneForLayout(ViewGroup sceneRoot, int layoutId, Context context) {
        return new ScenePort(sceneRoot, layoutId, context);
    }

    static void setCurrentScene(View view, ScenePort scene) {
        view.setTag(C0093R.id.transition_current_scene, scene);
    }

    static ScenePort getCurrentScene(View view) {
        return (ScenePort) view.getTag(C0093R.id.transition_current_scene);
    }

    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    public void exit() {
        if (getCurrentScene(this.mSceneRoot) == this && this.mExitAction != null) {
            this.mExitAction.run();
        }
    }

    public void enter() {
        if (this.mLayoutId > 0 || this.mLayout != null) {
            getSceneRoot().removeAllViews();
            if (this.mLayoutId > 0) {
                LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            } else {
                this.mSceneRoot.addView(this.mLayout);
            }
        }
        if (this.mEnterAction != null) {
            this.mEnterAction.run();
        }
        setCurrentScene(this.mSceneRoot, this);
    }

    public void setEnterAction(Runnable action) {
        this.mEnterAction = action;
    }

    public void setExitAction(Runnable action) {
        this.mExitAction = action;
    }

    boolean isCreatedFromLayoutResource() {
        return this.mLayoutId > 0;
    }
}
