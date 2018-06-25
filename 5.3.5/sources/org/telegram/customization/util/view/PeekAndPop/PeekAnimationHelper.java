package org.telegram.customization.util.view.PeekAndPop;

import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

public class PeekAnimationHelper {
    private Context context;
    private View peekLayout;
    private View peekView;

    public PeekAnimationHelper(Context context, View peekLayout, View peekView) {
        this.context = context;
        this.peekLayout = peekLayout;
        this.peekView = peekView;
    }

    public void animatePeek(int duration) {
        this.peekView.setAlpha(1.0f);
        ObjectAnimator animatorLayoutAlpha = ObjectAnimator.ofFloat(this.peekLayout, "alpha", new float[]{1.0f});
        animatorLayoutAlpha.setInterpolator(new OvershootInterpolator(1.2f));
        animatorLayoutAlpha.setDuration((long) duration);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(this.peekView, "scaleX", new float[]{1.0f});
        animatorScaleX.setDuration((long) duration);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(this.peekView, "scaleY", new float[]{1.0f});
        animatorScaleY.setDuration((long) duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator(1.2f));
        animatorSet.play(animatorScaleX).with(animatorScaleY);
        animatorSet.start();
        animatorLayoutAlpha.start();
    }

    public void animatePop(AnimatorListener animatorListener, int duration) {
        ObjectAnimator animatorLayoutAlpha = ObjectAnimator.ofFloat(this.peekLayout, "alpha", new float[]{0.0f});
        animatorLayoutAlpha.setDuration((long) duration);
        animatorLayoutAlpha.addListener(animatorListener);
        animatorLayoutAlpha.setInterpolator(new DecelerateInterpolator(1.5f));
        animatorLayoutAlpha.start();
        animateReturn(duration);
    }

    public void animateReturn(int duration) {
        ObjectAnimator animatorTranslate;
        if (this.context.getResources().getConfiguration().orientation == 1) {
            animatorTranslate = ObjectAnimator.ofFloat(this.peekView, "translationY", new float[]{0.0f});
        } else {
            animatorTranslate = ObjectAnimator.ofFloat(this.peekView, "translationX", new float[]{0.0f});
        }
        ObjectAnimator animatorShrinkY = ObjectAnimator.ofFloat(this.peekView, "scaleY", new float[]{0.75f});
        ObjectAnimator animatorShrinkX = ObjectAnimator.ofFloat(this.peekView, "scaleX", new float[]{0.75f});
        animatorShrinkX.setInterpolator(new DecelerateInterpolator());
        animatorShrinkY.setInterpolator(new DecelerateInterpolator());
        animatorTranslate.setInterpolator(new DecelerateInterpolator());
        animatorShrinkX.setDuration((long) duration);
        animatorShrinkY.setDuration((long) duration);
        animatorTranslate.setDuration((long) duration);
        animatorShrinkX.start();
        animatorShrinkY.start();
        animatorTranslate.start();
    }

    public void animateExpand(int duration, long popTime) {
        long timeDifference = System.currentTimeMillis() - popTime;
        ObjectAnimator animatorExpandY = ObjectAnimator.ofFloat(this.peekView, "scaleY", new float[]{1.025f});
        ObjectAnimator animatorExpandX = ObjectAnimator.ofFloat(this.peekView, "scaleX", new float[]{1.025f});
        animatorExpandX.setInterpolator(new DecelerateInterpolator());
        animatorExpandY.setInterpolator(new DecelerateInterpolator());
        animatorExpandX.setDuration(Math.max(0, ((long) duration) - timeDifference));
        animatorExpandY.setDuration(Math.max(0, ((long) duration) - timeDifference));
        animatorExpandX.start();
        animatorExpandY.start();
    }

    public void animateFling(float velocityX, float velocityY, int duration, long popTime, float flingVelocityMax) {
        long timeDifference = System.currentTimeMillis() - popTime;
        float translationAmount;
        if (this.context.getResources().getConfiguration().orientation == 1) {
            translationAmount = Math.max(velocityY / 8.0f, flingVelocityMax);
            ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(this.peekView, "translationY", new float[]{translationAmount});
            animatorTranslateY.setInterpolator(new DecelerateInterpolator());
            animatorTranslateY.setDuration(Math.max(0, ((long) duration) - timeDifference));
            animatorTranslateY.start();
        } else if (this.context.getResources().getConfiguration().orientation == 2) {
            translationAmount = Math.max(velocityX / 8.0f, flingVelocityMax);
            ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(this.peekView, "translationX", new float[]{translationAmount});
            animatorTranslateX.setInterpolator(new DecelerateInterpolator());
            animatorTranslateX.setDuration(Math.max(0, ((long) duration) - timeDifference));
            animatorTranslateX.start();
        }
    }
}
