package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.Interpolator;

abstract class BaseDraggableItemDecorator extends ItemDecoration {
    private static final int RETURN_TO_DEFAULT_POS_ANIMATE_THRESHOLD_DP = 2;
    private static final int RETURN_TO_DEFAULT_POS_ANIMATE_THRESHOLD_MSEC = 20;
    protected ViewHolder mDraggingItemViewHolder;
    protected final RecyclerView mRecyclerView;
    private final int mReturnToDefaultPositionAnimateThreshold;
    private int mReturnToDefaultPositionDuration = 200;
    private Interpolator mReturnToDefaultPositionInterpolator;

    public BaseDraggableItemDecorator(RecyclerView recyclerView, ViewHolder draggingItemViewHolder) {
        this.mRecyclerView = recyclerView;
        this.mDraggingItemViewHolder = draggingItemViewHolder;
        this.mReturnToDefaultPositionAnimateThreshold = (int) ((2.0f * recyclerView.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void setReturnToDefaultPositionAnimationDuration(int duration) {
        this.mReturnToDefaultPositionDuration = duration;
    }

    public void setReturnToDefaultPositionAnimationInterpolator(Interpolator interpolator) {
        this.mReturnToDefaultPositionInterpolator = interpolator;
    }

    protected void moveToDefaultPosition(View targetView, float initialScaleX, float initialScaleY, float initialRotation, float initialAlpha, boolean animate) {
        final float initialTranslationZ = ViewCompat.getTranslationZ(targetView);
        int animDuration = (int) (((float) this.mReturnToDefaultPositionDuration) * determineMoveToDefaultPositionAnimationDurationFactor(targetView, initialScaleX, initialScaleY, initialRotation, initialAlpha));
        if (supportsViewPropertyAnimation() && animate && animDuration > 20) {
            ViewPropertyAnimatorCompat animator = ViewCompat.animate(targetView);
            ViewCompat.setScaleX(targetView, initialScaleX);
            ViewCompat.setScaleY(targetView, initialScaleY);
            ViewCompat.setRotation(targetView, initialRotation);
            ViewCompat.setAlpha(targetView, initialAlpha);
            ViewCompat.setTranslationZ(targetView, initialTranslationZ + 1.0f);
            animator.cancel();
            animator.setDuration((long) animDuration);
            animator.setInterpolator(this.mReturnToDefaultPositionInterpolator);
            animator.translationX(0.0f);
            animator.translationY(0.0f);
            animator.translationZ(initialTranslationZ);
            animator.alpha(1.0f);
            animator.rotation(0.0f);
            animator.scaleX(1.0f);
            animator.scaleY(1.0f);
            animator.setListener(new ViewPropertyAnimatorListener() {
                public void onAnimationStart(View view) {
                }

                public void onAnimationEnd(View view) {
                    ViewCompat.animate(view).setListener(null);
                    BaseDraggableItemDecorator.resetDraggingItemViewEffects(view, initialTranslationZ);
                    if (view.getParent() instanceof RecyclerView) {
                        ViewCompat.postInvalidateOnAnimation((RecyclerView) view.getParent());
                    }
                }

                public void onAnimationCancel(View view) {
                }
            });
            animator.start();
            return;
        }
        resetDraggingItemViewEffects(targetView, initialTranslationZ);
    }

    protected float determineMoveToDefaultPositionAnimationDurationFactor(View targetView, float initialScaleX, float initialScaleY, float initialRotation, float initialAlpha) {
        float curTranslationX = ViewCompat.getTranslationX(targetView);
        float curTranslationY = ViewCompat.getTranslationY(targetView);
        int halfItemWidth = targetView.getWidth() / 2;
        int halfItemHeight = targetView.getHeight() / 2;
        float translationXProportion = halfItemWidth > 0 ? Math.abs(curTranslationX / ((float) halfItemWidth)) : 0.0f;
        float translationYProportion = halfItemHeight > 0 ? Math.abs(curTranslationY / ((float) halfItemHeight)) : 0.0f;
        return Math.min(Math.max(Math.max(Math.max(Math.max(Math.max(0.0f, translationXProportion), translationYProportion), Math.abs(Math.max(initialScaleX, initialScaleY) - 1.0f)), Math.abs(0.033333335f * initialRotation)), Math.abs(initialAlpha - 1.0f)), 1.0f);
    }

    protected static void resetDraggingItemViewEffects(View view, float initialTranslationZ) {
        ViewCompat.setTranslationX(view, 0.0f);
        ViewCompat.setTranslationY(view, 0.0f);
        ViewCompat.setTranslationZ(view, initialTranslationZ);
        ViewCompat.setAlpha(view, 1.0f);
        ViewCompat.setRotation(view, 0.0f);
        ViewCompat.setScaleX(view, 1.0f);
        ViewCompat.setScaleY(view, 1.0f);
    }

    protected static void setItemTranslation(RecyclerView rv, ViewHolder holder, float x, float y) {
        ItemAnimator itemAnimator = rv.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimation(holder);
        }
        ViewCompat.setTranslationX(holder.itemView, x);
        ViewCompat.setTranslationY(holder.itemView, y);
    }

    private static boolean supportsViewPropertyAnimation() {
        return VERSION.SDK_INT >= 11;
    }
}
