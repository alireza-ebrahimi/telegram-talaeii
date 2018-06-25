package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

@TargetApi(14)
@RequiresApi(14)
class ChangeBoundsPort extends TransitionPort {
    private static final String LOG_TAG = "ChangeBounds";
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    private static final String[] sTransitionProperties = new String[]{PROPNAME_BOUNDS, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    boolean mReparent = false;
    boolean mResizeClip = false;
    int[] tempLocation = new int[2];

    /* renamed from: android.support.transition.ChangeBoundsPort$1 */
    class C00861 extends TransitionListenerAdapter {
        boolean mCanceled = false;

        C00861() {
        }

        public void onTransitionCancel(TransitionPort transition) {
            this.mCanceled = true;
        }

        public void onTransitionEnd(TransitionPort transition) {
            if (!this.mCanceled) {
            }
        }

        public void onTransitionPause(TransitionPort transition) {
        }

        public void onTransitionResume(TransitionPort transition) {
        }
    }

    /* renamed from: android.support.transition.ChangeBoundsPort$2 */
    class C00872 extends TransitionListenerAdapter {
        boolean mCanceled = false;

        C00872() {
        }

        public void onTransitionCancel(TransitionPort transition) {
            this.mCanceled = true;
        }

        public void onTransitionEnd(TransitionPort transition) {
            if (!this.mCanceled) {
            }
        }

        public void onTransitionPause(TransitionPort transition) {
        }

        public void onTransitionResume(TransitionPort transition) {
        }
    }

    /* renamed from: android.support.transition.ChangeBoundsPort$3 */
    class C00883 extends AnimatorListenerAdapter {
        C00883() {
        }

        public void onAnimationEnd(Animator animation) {
        }
    }

    ChangeBoundsPort() {
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean resizeClip) {
        this.mResizeClip = resizeClip;
    }

    public void setReparent(boolean reparent) {
        this.mReparent = reparent;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        values.values.put(PROPNAME_PARENT, values.view.getParent());
        values.view.getLocationInWindow(this.tempLocation);
        values.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.tempLocation[0]));
        values.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.tempLocation[1]));
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        ViewGroup startParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
        ViewGroup endParent = (ViewGroup) endValues.values.get(PROPNAME_PARENT);
        if (startParent == null || endParent == null) {
            return null;
        }
        View view = endValues.view;
        boolean parentsEqual = startParent == endParent || startParent.getId() == endParent.getId();
        Animator anim;
        if (!this.mReparent || parentsEqual) {
            Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
            Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
            int startLeft = startBounds.left;
            int endLeft = endBounds.left;
            int startTop = startBounds.top;
            int endTop = endBounds.top;
            int startRight = startBounds.right;
            int endRight = endBounds.right;
            int startBottom = startBounds.bottom;
            int endBottom = endBounds.bottom;
            int startWidth = startRight - startLeft;
            int startHeight = startBottom - startTop;
            int endWidth = endRight - endLeft;
            int endHeight = endBottom - endTop;
            int numChanges = 0;
            if (!(startWidth == 0 || startHeight == 0 || endWidth == 0 || endHeight == 0)) {
                if (startLeft != endLeft) {
                    numChanges = 0 + 1;
                }
                if (startTop != endTop) {
                    numChanges++;
                }
                if (startRight != endRight) {
                    numChanges++;
                }
                if (startBottom != endBottom) {
                    numChanges++;
                }
            }
            if (numChanges > 0) {
                PropertyValuesHolder[] pvh;
                int pvhIndex;
                int i;
                ViewGroup viewGroup;
                if (this.mResizeClip) {
                    if (startWidth != endWidth) {
                        view.setRight(Math.max(startWidth, endWidth) + endLeft);
                    }
                    if (startHeight != endHeight) {
                        view.setBottom(Math.max(startHeight, endHeight) + endTop);
                    }
                    if (startLeft != endLeft) {
                        view.setTranslationX((float) (startLeft - endLeft));
                    }
                    if (startTop != endTop) {
                        view.setTranslationY((float) (startTop - endTop));
                    }
                    float transXDelta = (float) (endLeft - startLeft);
                    float transYDelta = (float) (endTop - startTop);
                    int widthDelta = endWidth - startWidth;
                    int heightDelta = endHeight - startHeight;
                    numChanges = 0;
                    if (transXDelta != 0.0f) {
                        numChanges = 0 + 1;
                    }
                    if (transYDelta != 0.0f) {
                        numChanges++;
                    }
                    if (!(widthDelta == 0 && heightDelta == 0)) {
                        numChanges++;
                    }
                    pvh = new PropertyValuesHolder[numChanges];
                    if (transXDelta != 0.0f) {
                        pvhIndex = 0 + 1;
                        pvh[0] = PropertyValuesHolder.ofFloat("translationX", new float[]{view.getTranslationX(), 0.0f});
                    } else {
                        pvhIndex = 0;
                    }
                    if (transYDelta != 0.0f) {
                        i = pvhIndex + 1;
                        pvh[pvhIndex] = PropertyValuesHolder.ofFloat("translationY", new float[]{view.getTranslationY(), 0.0f});
                    } else {
                        i = pvhIndex;
                    }
                    if (!(widthDelta == 0 && heightDelta == 0)) {
                        Rect rect = new Rect(0, 0, startWidth, startHeight);
                        rect = new Rect(0, 0, endWidth, endHeight);
                    }
                    anim = ObjectAnimator.ofPropertyValuesHolder(view, pvh);
                    if (view.getParent() instanceof ViewGroup) {
                        viewGroup = (ViewGroup) view.getParent();
                        addListener(new C00872());
                    }
                    anim.addListener(new C00883());
                    return anim;
                }
                pvh = new PropertyValuesHolder[numChanges];
                if (startLeft != endLeft) {
                    view.setLeft(startLeft);
                }
                if (startTop != endTop) {
                    view.setTop(startTop);
                }
                if (startRight != endRight) {
                    view.setRight(startRight);
                }
                if (startBottom != endBottom) {
                    view.setBottom(startBottom);
                }
                if (startLeft != endLeft) {
                    pvhIndex = 0 + 1;
                    pvh[0] = PropertyValuesHolder.ofInt(TtmlNode.LEFT, new int[]{startLeft, endLeft});
                } else {
                    pvhIndex = 0;
                }
                if (startTop != endTop) {
                    i = pvhIndex + 1;
                    pvh[pvhIndex] = PropertyValuesHolder.ofInt("top", new int[]{startTop, endTop});
                    pvhIndex = i;
                }
                if (startRight != endRight) {
                    i = pvhIndex + 1;
                    pvh[pvhIndex] = PropertyValuesHolder.ofInt(TtmlNode.RIGHT, new int[]{startRight, endRight});
                    pvhIndex = i;
                }
                if (startBottom != endBottom) {
                    i = pvhIndex + 1;
                    pvh[pvhIndex] = PropertyValuesHolder.ofInt("bottom", new int[]{startBottom, endBottom});
                } else {
                    i = pvhIndex;
                }
                anim = ObjectAnimator.ofPropertyValuesHolder(view, pvh);
                if (!(view.getParent() instanceof ViewGroup)) {
                    return anim;
                }
                viewGroup = (ViewGroup) view.getParent();
                addListener(new C00861());
                return anim;
            }
        }
        int startX = ((Integer) startValues.values.get(PROPNAME_WINDOW_X)).intValue();
        int startY = ((Integer) startValues.values.get(PROPNAME_WINDOW_Y)).intValue();
        int endX = ((Integer) endValues.values.get(PROPNAME_WINDOW_X)).intValue();
        int endY = ((Integer) endValues.values.get(PROPNAME_WINDOW_Y)).intValue();
        if (!(startX == endX && startY == endY)) {
            sceneRoot.getLocationInWindow(this.tempLocation);
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            view.draw(new Canvas(bitmap));
            final BitmapDrawable drawable = new BitmapDrawable(bitmap);
            view.setVisibility(4);
            ViewOverlay.createFrom(sceneRoot).add(drawable);
            rect = new Rect(startX - this.tempLocation[0], startY - this.tempLocation[1], (startX - this.tempLocation[0]) + view.getWidth(), (startY - this.tempLocation[1]) + view.getHeight());
            Rect endBounds1 = new Rect(endX - this.tempLocation[0], endY - this.tempLocation[1], (endX - this.tempLocation[0]) + view.getWidth(), (endY - this.tempLocation[1]) + view.getHeight());
            anim = ObjectAnimator.ofObject(drawable, "bounds", sRectEvaluator, new Object[]{rect, endBounds1});
            final ViewGroup viewGroup2 = sceneRoot;
            final View view2 = view;
            anim.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    ViewOverlay.createFrom(viewGroup2).remove(drawable);
                    view2.setVisibility(0);
                }
            });
            return anim;
        }
        return null;
    }
}
