package utils.app;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

public class AnimationUtil {
    public static final long defaultDuration = 500;

    public static int getCenterX(View v) {
        return (v.getLeft() + v.getRight()) / 2;
    }

    public static int getCenterY(View v) {
        return (v.getTop() + v.getBottom()) / 2;
    }

    public static int getRelativeX(View v, float r) {
        return (int) (((float) v.getLeft()) + (((float) (v.getRight() - v.getLeft())) * r));
    }

    public static int getRelativeY(View v, float r) {
        return (int) (((float) v.getTop()) + (((float) (v.getBottom() - v.getTop())) * r));
    }

    public static synchronized void runRippleAnimFromSelf(View target, View position, float x, float y, boolean isReverse, AnimatorListener listener) {
        synchronized (AnimationUtil.class) {
            runRippleAnim(target, getRelativeX(position, x), getRelativeY(position, y), isReverse, listener);
        }
    }

    public static synchronized void runRippleAnim(View target, View position, boolean isReverse, AnimatorListener listener) {
        synchronized (AnimationUtil.class) {
            runRippleAnim(target, getCenterX(position), getCenterY(position), isReverse, listener);
        }
    }

    public static synchronized void runRippleAnim(View target, int cx, int cy, boolean isReverse, AnimatorListener listener) {
        synchronized (AnimationUtil.class) {
            if (VERSION.SDK_INT >= 21) {
                final View view = target;
                final boolean z = isReverse;
                final int i = cx;
                final int i2 = cy;
                final AnimatorListener animatorListener = listener;
                target.post(new Runnable() {
                    @TargetApi(21)
                    public void run() {
                        Animator animator;
                        int finalRadius = Math.max(view.getWidth(), view.getHeight());
                        if (z) {
                            animator = ViewAnimationUtils.createCircularReveal(view, i, i2, (float) finalRadius, 0.0f);
                        } else {
                            animator = ViewAnimationUtils.createCircularReveal(view, i, i2, 0.0f, (float) finalRadius);
                        }
                        animator.setDuration(500);
                        if (animatorListener != null) {
                            animator.addListener(animatorListener);
                        }
                        animator.start();
                    }
                });
            } else {
                AnimationSet anim = new AnimationSet(true);
                if (isReverse) {
                    anim.addAnimation(new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f, 0, (float) cx, 1, 0.0f));
                } else {
                    anim.addAnimation(new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, 0, (float) cx, 1, 0.0f));
                }
                anim.setDuration(500);
                final AnimatorListener animatorListener2 = listener;
                anim.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        if (animatorListener2 != null) {
                            animatorListener2.onAnimationStart(null);
                        }
                    }

                    public void onAnimationEnd(Animation animation) {
                        if (animatorListener2 != null) {
                            animatorListener2.onAnimationEnd(null);
                        }
                    }

                    public void onAnimationRepeat(Animation animation) {
                        if (animatorListener2 != null) {
                            animatorListener2.onAnimationRepeat(null);
                        }
                    }
                });
                target.startAnimation(anim);
            }
        }
    }

    public static void expand(final View v) {
        v.measure(-1, -1);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 1;
        v.setVisibility(0);
        Animation a = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1.0f ? -1 : (int) (((float) targetHeight) * interpolatedTime);
                v.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((long) ((int) (((float) targetHeight) / v.getContext().getResources().getDisplayMetrics().density)));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    v.setVisibility(8);
                    return;
                }
                v.getLayoutParams().height = initialHeight - ((int) (((float) initialHeight) * interpolatedTime));
                v.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((long) ((int) (((float) initialHeight) / v.getContext().getResources().getDisplayMetrics().density)));
        v.startAnimation(a);
    }
}
