package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
@RequiresApi(14)
class TransitionManagerPort {
    private static final String[] EMPTY_STRINGS = new String[0];
    private static String LOG_TAG = "TransitionManager";
    private static TransitionPort sDefaultTransition = new AutoTransitionPort();
    static ArrayList<ViewGroup> sPendingTransitions = new ArrayList();
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<TransitionPort>>>> sRunningTransitions = new ThreadLocal();
    ArrayMap<String, ArrayMap<ScenePort, TransitionPort>> mNameSceneTransitions = new ArrayMap();
    ArrayMap<ScenePort, ArrayMap<String, TransitionPort>> mSceneNameTransitions = new ArrayMap();
    ArrayMap<ScenePort, ArrayMap<ScenePort, TransitionPort>> mScenePairTransitions = new ArrayMap();
    ArrayMap<ScenePort, TransitionPort> mSceneTransitions = new ArrayMap();

    private static class MultiListener implements OnPreDrawListener, OnAttachStateChangeListener {
        ViewGroup mSceneRoot;
        TransitionPort mTransition;

        MultiListener(TransitionPort transition, ViewGroup sceneRoot) {
            this.mTransition = transition;
            this.mSceneRoot = sceneRoot;
        }

        private void removeListeners() {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
        }

        public void onViewAttachedToWindow(View v) {
        }

        public void onViewDetachedFromWindow(View v) {
            removeListeners();
            TransitionManagerPort.sPendingTransitions.remove(this.mSceneRoot);
            ArrayList<TransitionPort> runningTransitions = (ArrayList) TransitionManagerPort.getRunningTransitions().get(this.mSceneRoot);
            if (runningTransitions != null && runningTransitions.size() > 0) {
                Iterator it = runningTransitions.iterator();
                while (it.hasNext()) {
                    ((TransitionPort) it.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

        public boolean onPreDraw() {
            removeListeners();
            TransitionManagerPort.sPendingTransitions.remove(this.mSceneRoot);
            final ArrayMap<ViewGroup, ArrayList<TransitionPort>> runningTransitions = TransitionManagerPort.getRunningTransitions();
            ArrayList<TransitionPort> currentTransitions = (ArrayList) runningTransitions.get(this.mSceneRoot);
            ArrayList<TransitionPort> previousRunningTransitions = null;
            if (currentTransitions == null) {
                currentTransitions = new ArrayList();
                runningTransitions.put(this.mSceneRoot, currentTransitions);
            } else if (currentTransitions.size() > 0) {
                previousRunningTransitions = new ArrayList(currentTransitions);
            }
            currentTransitions.add(this.mTransition);
            this.mTransition.addListener(new TransitionListenerAdapter() {
                public void onTransitionEnd(TransitionPort transition) {
                    ((ArrayList) runningTransitions.get(MultiListener.this.mSceneRoot)).remove(transition);
                }
            });
            this.mTransition.captureValues(this.mSceneRoot, false);
            if (previousRunningTransitions != null) {
                Iterator it = previousRunningTransitions.iterator();
                while (it.hasNext()) {
                    ((TransitionPort) it.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.playTransition(this.mSceneRoot);
            return true;
        }
    }

    TransitionManagerPort() {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static TransitionPort getDefaultTransition() {
        return sDefaultTransition;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setDefaultTransition(TransitionPort transition) {
        sDefaultTransition = transition;
    }

    private static void changeScene(ScenePort scene, TransitionPort transition) {
        ViewGroup sceneRoot = scene.getSceneRoot();
        TransitionPort transitionClone = null;
        if (transition != null) {
            transitionClone = transition.clone();
            transitionClone.setSceneRoot(sceneRoot);
        }
        ScenePort oldScene = ScenePort.getCurrentScene(sceneRoot);
        if (oldScene != null && oldScene.isCreatedFromLayoutResource()) {
            transitionClone.setCanRemoveViews(true);
        }
        sceneChangeSetup(sceneRoot, transitionClone);
        scene.enter();
        sceneChangeRunTransition(sceneRoot, transitionClone);
    }

    static ArrayMap<ViewGroup, ArrayList<TransitionPort>> getRunningTransitions() {
        WeakReference<ArrayMap<ViewGroup, ArrayList<TransitionPort>>> runningTransitions = (WeakReference) sRunningTransitions.get();
        if (runningTransitions == null || runningTransitions.get() == null) {
            runningTransitions = new WeakReference(new ArrayMap());
            sRunningTransitions.set(runningTransitions);
        }
        return (ArrayMap) runningTransitions.get();
    }

    private static void sceneChangeRunTransition(ViewGroup sceneRoot, TransitionPort transition) {
        if (transition != null && sceneRoot != null) {
            MultiListener listener = new MultiListener(transition, sceneRoot);
            sceneRoot.addOnAttachStateChangeListener(listener);
            sceneRoot.getViewTreeObserver().addOnPreDrawListener(listener);
        }
    }

    private static void sceneChangeSetup(ViewGroup sceneRoot, TransitionPort transition) {
        ArrayList<TransitionPort> runningTransitions = (ArrayList) getRunningTransitions().get(sceneRoot);
        if (runningTransitions != null && runningTransitions.size() > 0) {
            Iterator it = runningTransitions.iterator();
            while (it.hasNext()) {
                ((TransitionPort) it.next()).pause(sceneRoot);
            }
        }
        if (transition != null) {
            transition.captureValues(sceneRoot, true);
        }
        ScenePort previousScene = ScenePort.getCurrentScene(sceneRoot);
        if (previousScene != null) {
            previousScene.exit();
        }
    }

    public static void go(ScenePort scene) {
        changeScene(scene, sDefaultTransition);
    }

    public static void go(ScenePort scene, TransitionPort transition) {
        changeScene(scene, transition);
    }

    public static void beginDelayedTransition(ViewGroup sceneRoot) {
        beginDelayedTransition(sceneRoot, null);
    }

    public static void beginDelayedTransition(ViewGroup sceneRoot, TransitionPort transition) {
        if (!sPendingTransitions.contains(sceneRoot) && ViewCompat.isLaidOut(sceneRoot)) {
            sPendingTransitions.add(sceneRoot);
            if (transition == null) {
                transition = sDefaultTransition;
            }
            TransitionPort transitionClone = transition.clone();
            sceneChangeSetup(sceneRoot, transitionClone);
            ScenePort.setCurrentScene(sceneRoot, null);
            sceneChangeRunTransition(sceneRoot, transitionClone);
        }
    }

    public void setTransition(ScenePort scene, TransitionPort transition) {
        this.mSceneTransitions.put(scene, transition);
    }

    public void setTransition(ScenePort fromScene, ScenePort toScene, TransitionPort transition) {
        ArrayMap<ScenePort, TransitionPort> sceneTransitionMap = (ArrayMap) this.mScenePairTransitions.get(toScene);
        if (sceneTransitionMap == null) {
            sceneTransitionMap = new ArrayMap();
            this.mScenePairTransitions.put(toScene, sceneTransitionMap);
        }
        sceneTransitionMap.put(fromScene, transition);
    }

    private TransitionPort getTransition(ScenePort scene) {
        TransitionPort transition;
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (sceneRoot != null) {
            ScenePort currScene = ScenePort.getCurrentScene(sceneRoot);
            if (currScene != null) {
                ArrayMap<ScenePort, TransitionPort> sceneTransitionMap = (ArrayMap) this.mScenePairTransitions.get(scene);
                if (sceneTransitionMap != null) {
                    transition = (TransitionPort) sceneTransitionMap.get(currScene);
                    if (transition != null) {
                        return transition;
                    }
                }
            }
        }
        transition = (TransitionPort) this.mSceneTransitions.get(scene);
        return transition != null ? transition : sDefaultTransition;
    }

    public TransitionPort getNamedTransition(String fromName, ScenePort toScene) {
        ArrayMap<ScenePort, TransitionPort> m = (ArrayMap) this.mNameSceneTransitions.get(fromName);
        if (m != null) {
            return (TransitionPort) m.get(toScene);
        }
        return null;
    }

    public TransitionPort getNamedTransition(ScenePort fromScene, String toName) {
        ArrayMap<String, TransitionPort> m = (ArrayMap) this.mSceneNameTransitions.get(fromScene);
        if (m != null) {
            return (TransitionPort) m.get(toName);
        }
        return null;
    }

    public String[] getTargetSceneNames(ScenePort fromScene) {
        ArrayMap<String, TransitionPort> m = (ArrayMap) this.mSceneNameTransitions.get(fromScene);
        if (m == null) {
            return EMPTY_STRINGS;
        }
        int count = m.size();
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = (String) m.keyAt(i);
        }
        return result;
    }

    public void setTransition(ScenePort fromScene, String toName, TransitionPort transition) {
        ArrayMap<String, TransitionPort> m = (ArrayMap) this.mSceneNameTransitions.get(fromScene);
        if (m == null) {
            m = new ArrayMap();
            this.mSceneNameTransitions.put(fromScene, m);
        }
        m.put(toName, transition);
    }

    public void setTransition(String fromName, ScenePort toScene, TransitionPort transition) {
        ArrayMap<ScenePort, TransitionPort> m = (ArrayMap) this.mNameSceneTransitions.get(fromName);
        if (m == null) {
            m = new ArrayMap();
            this.mNameSceneTransitions.put(fromName, m);
        }
        m.put(toScene, transition);
    }

    public void transitionTo(ScenePort scene) {
        changeScene(scene, getTransition(scene));
    }
}
