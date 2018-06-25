package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class FadePort extends VisibilityPort {
    private static boolean DBG = false;
    public static final int IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    private static final String PROPNAME_SCREEN_X = "android:fade:screenX";
    private static final String PROPNAME_SCREEN_Y = "android:fade:screenY";
    private int mFadingMode;

    public FadePort() {
        this(3);
    }

    public FadePort(int fadingMode) {
        this.mFadingMode = fadingMode;
    }

    private Animator createAnimation(View view, float startAlpha, float endAlpha, AnimatorListenerAdapter listener) {
        Animator animator = null;
        if (startAlpha != endAlpha) {
            animator = ObjectAnimator.ofFloat(view, "alpha", new float[]{startAlpha, endAlpha});
            if (DBG) {
                Log.d(LOG_TAG, "Created animator " + animator);
            }
            if (listener != null) {
                animator.addListener(listener);
            }
        } else if (listener != null) {
            listener.onAnimationEnd(null);
        }
        return animator;
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] loc = new int[2];
        transitionValues.view.getLocationOnScreen(loc);
        transitionValues.values.put(PROPNAME_SCREEN_X, Integer.valueOf(loc[0]));
        transitionValues.values.put(PROPNAME_SCREEN_Y, Integer.valueOf(loc[1]));
    }

    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        if ((this.mFadingMode & 1) != 1 || endValues == null) {
            return null;
        }
        final View endView = endValues.view;
        if (DBG) {
            View startView;
            if (startValues != null) {
                startView = startValues.view;
            } else {
                startView = null;
            }
            Log.d(LOG_TAG, "Fade.onAppear: startView, startVis, endView, endVis = " + startView + ", " + startVisibility + ", " + endView + ", " + endVisibility);
        }
        endView.setAlpha(0.0f);
        addListener(new TransitionListenerAdapter() {
            boolean mCanceled = false;
            float mPausedAlpha;

            public void onTransitionCancel(TransitionPort transition) {
                endView.setAlpha(1.0f);
                this.mCanceled = true;
            }

            public void onTransitionEnd(TransitionPort transition) {
                if (!this.mCanceled) {
                    endView.setAlpha(1.0f);
                }
            }

            public void onTransitionPause(TransitionPort transition) {
                this.mPausedAlpha = endView.getAlpha();
                endView.setAlpha(1.0f);
            }

            public void onTransitionResume(TransitionPort transition) {
                endView.setAlpha(this.mPausedAlpha);
            }
        });
        return createAnimation(endView, 0.0f, 1.0f, null);
    }

    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        if ((this.mFadingMode & 2) != 2) {
            return null;
        }
        View view = null;
        View startView = startValues != null ? startValues.view : null;
        View endView = endValues != null ? endValues.view : null;
        if (DBG) {
            Log.d(LOG_TAG, "Fade.onDisappear: startView, startVis, endView, endVis = " + startView + ", " + startVisibility + ", " + endView + ", " + endVisibility);
        }
        View overlayView = null;
        View viewToKeep = null;
        if (endView == null || endView.getParent() == null) {
            if (endView != null) {
                overlayView = endView;
                view = endView;
            } else if (startView != null) {
                if (startView.getParent() == null) {
                    overlayView = startView;
                    view = startView;
                } else if ((startView.getParent() instanceof View) && startView.getParent().getParent() == null) {
                    int id = ((View) startView.getParent()).getId();
                    if (!(id == -1 || sceneRoot.findViewById(id) == null || !this.mCanRemoveViews)) {
                        overlayView = startView;
                        view = startView;
                    }
                }
            }
        } else if (endVisibility == 4) {
            view = endView;
            viewToKeep = view;
        } else if (startView == endView) {
            view = endView;
            viewToKeep = view;
        } else {
            view = startView;
            overlayView = view;
        }
        final int finalVisibility = endVisibility;
        final View finalView;
        final View finalOverlayView;
        final View finalViewToKeep;
        final ViewGroup finalSceneRoot;
        if (overlayView != null) {
            int screenX = ((Integer) startValues.values.get(PROPNAME_SCREEN_X)).intValue();
            int screenY = ((Integer) startValues.values.get(PROPNAME_SCREEN_Y)).intValue();
            int[] loc = new int[2];
            sceneRoot.getLocationOnScreen(loc);
            ViewCompat.offsetLeftAndRight(overlayView, (screenX - loc[0]) - overlayView.getLeft());
            ViewCompat.offsetTopAndBottom(overlayView, (screenY - loc[1]) - overlayView.getTop());
            ViewGroupOverlay.createFrom(sceneRoot).add(overlayView);
            finalView = view;
            finalOverlayView = overlayView;
            finalViewToKeep = viewToKeep;
            finalSceneRoot = sceneRoot;
            return createAnimation(view, 1.0f, 0.0f, new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    finalView.setAlpha(1.0f);
                    if (finalViewToKeep != null) {
                        finalViewToKeep.setVisibility(finalVisibility);
                    }
                    if (finalOverlayView != null) {
                        ViewGroupOverlay.createFrom(finalSceneRoot).remove(finalOverlayView);
                    }
                }
            });
        } else if (viewToKeep == null) {
            return null;
        } else {
            viewToKeep.setVisibility(0);
            finalView = view;
            finalOverlayView = overlayView;
            finalViewToKeep = viewToKeep;
            finalSceneRoot = sceneRoot;
            return createAnimation(view, 1.0f, 0.0f, new AnimatorListenerAdapter() {
                boolean mCanceled = false;
                float mPausedAlpha = -1.0f;

                public void onAnimationCancel(Animator animation) {
                    this.mCanceled = true;
                    if (this.mPausedAlpha >= 0.0f) {
                        finalView.setAlpha(this.mPausedAlpha);
                    }
                }

                public void onAnimationEnd(Animator animation) {
                    if (!this.mCanceled) {
                        finalView.setAlpha(1.0f);
                    }
                    if (!(finalViewToKeep == null || this.mCanceled)) {
                        finalViewToKeep.setVisibility(finalVisibility);
                    }
                    if (finalOverlayView != null) {
                        ViewGroupOverlay.createFrom(finalSceneRoot).add(finalOverlayView);
                    }
                }
            });
        }
    }
}
