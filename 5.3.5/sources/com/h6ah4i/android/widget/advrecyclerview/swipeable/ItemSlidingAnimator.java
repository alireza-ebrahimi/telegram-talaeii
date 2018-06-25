package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ItemSlidingAnimator {
    public static final int DIR_DOWN = 3;
    public static final int DIR_LEFT = 0;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_UP = 1;
    private static final String TAG = "ItemSlidingAnimator";
    private final List<ViewHolder> mActive;
    private final SwipeableItemWrapperAdapter<ViewHolder> mAdapter;
    private final List<WeakReference<ViewHolderDeferredProcess>> mDeferredProcesses;
    private int mImmediatelySetTranslationThreshold;
    private final Interpolator mSlideToDefaultPositionAnimationInterpolator = new AccelerateDecelerateInterpolator();
    private final Interpolator mSlideToOutsideOfWindowAnimationInterpolator = new AccelerateInterpolator(0.8f);
    private final Interpolator mSlideToSpecifiedPositionAnimationInterpolator = new DecelerateInterpolator();
    private final int[] mTmpLocation = new int[2];
    private final Rect mTmpRect = new Rect();

    private static abstract class ViewHolderDeferredProcess implements Runnable {
        final WeakReference<ViewHolder> mRefHolder;

        protected abstract void onProcess(ViewHolder viewHolder);

        public ViewHolderDeferredProcess(ViewHolder holder) {
            this.mRefHolder = new WeakReference(holder);
        }

        public void run() {
            ViewHolder holder = (ViewHolder) this.mRefHolder.get();
            if (holder != null) {
                onProcess(holder);
            }
        }

        public boolean lostReference(ViewHolder holder) {
            return ((ViewHolder) this.mRefHolder.get()) == null;
        }

        public boolean hasTargetViewHolder(ViewHolder holder) {
            return ((ViewHolder) this.mRefHolder.get()) == holder;
        }
    }

    private static final class DeferredSlideProcess extends ViewHolderDeferredProcess {
        final boolean mHorizontal;
        final float mPosition;

        public DeferredSlideProcess(ViewHolder holder, float position, boolean horizontal) {
            super(holder);
            this.mPosition = position;
            this.mHorizontal = horizontal;
        }

        protected void onProcess(ViewHolder holder) {
            View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
            if (this.mHorizontal) {
                ItemSlidingAnimator.slideInternalCompat(holder, this.mHorizontal, (int) ((((float) containerView.getWidth()) * this.mPosition) + 0.5f), 0);
                return;
            }
            ItemSlidingAnimator.slideInternalCompat(holder, this.mHorizontal, 0, (int) ((((float) containerView.getHeight()) * this.mPosition) + 0.5f));
        }
    }

    private static class SlidingAnimatorListenerObject implements ViewPropertyAnimatorListener, ViewPropertyAnimatorUpdateListener {
        private List<ViewHolder> mActive;
        private SwipeableItemWrapperAdapter<ViewHolder> mAdapter;
        private ViewPropertyAnimatorCompat mAnimator;
        private final long mDuration;
        private final boolean mHorizontal;
        private final Interpolator mInterpolator;
        private float mInvSize;
        private final SwipeFinishInfo mSwipeFinish;
        private final int mToX;
        private final int mToY;
        private ViewHolder mViewHolder;

        SlidingAnimatorListenerObject(SwipeableItemWrapperAdapter<ViewHolder> adapter, List<ViewHolder> activeViewHolders, ViewHolder holder, int toX, int toY, long duration, boolean horizontal, Interpolator interpolator, SwipeFinishInfo swipeFinish) {
            this.mAdapter = adapter;
            this.mActive = activeViewHolders;
            this.mViewHolder = holder;
            this.mToX = toX;
            this.mToY = toY;
            this.mHorizontal = horizontal;
            this.mSwipeFinish = swipeFinish;
            this.mDuration = duration;
            this.mInterpolator = interpolator;
        }

        void start() {
            View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(this.mViewHolder);
            this.mInvSize = 1.0f / Math.max(1.0f, this.mHorizontal ? (float) containerView.getWidth() : (float) containerView.getHeight());
            this.mAnimator = ViewCompat.animate(containerView);
            this.mAnimator.setDuration(this.mDuration);
            this.mAnimator.translationX((float) this.mToX);
            this.mAnimator.translationY((float) this.mToY);
            if (this.mInterpolator != null) {
                this.mAnimator.setInterpolator(this.mInterpolator);
            }
            this.mAnimator.setListener(this);
            this.mAnimator.setUpdateListener(this);
            this.mActive.add(this.mViewHolder);
            this.mAnimator.start();
        }

        public void onAnimationUpdate(View view) {
            this.mAdapter.onUpdateSlideAmount(this.mViewHolder, this.mViewHolder.getLayoutPosition(), (this.mHorizontal ? ViewCompat.getTranslationX(view) : ViewCompat.getTranslationY(view)) * this.mInvSize, true, this.mHorizontal, false);
        }

        public void onAnimationStart(View view) {
        }

        public void onAnimationEnd(View view) {
            this.mAnimator.setListener(null);
            if (VERSION.SDK_INT >= 19) {
                InternalHelperKK.clearViewPropertyAnimatorUpdateListener(view);
            } else {
                this.mAnimator.setUpdateListener(null);
            }
            ViewCompat.setTranslationX(view, (float) this.mToX);
            ViewCompat.setTranslationY(view, (float) this.mToY);
            this.mActive.remove(this.mViewHolder);
            ViewParent itemParentView = this.mViewHolder.itemView.getParent();
            if (itemParentView != null) {
                ViewCompat.postInvalidateOnAnimation((View) itemParentView);
            }
            if (this.mSwipeFinish != null) {
                this.mSwipeFinish.resultAction.slideAnimationEnd();
            }
            this.mActive = null;
            this.mAnimator = null;
            this.mViewHolder = null;
            this.mAdapter = null;
        }

        public void onAnimationCancel(View view) {
        }
    }

    private static class SwipeFinishInfo {
        final int itemPosition;
        SwipeResultAction resultAction;

        public SwipeFinishInfo(int itemPosition, SwipeResultAction resultAction) {
            this.itemPosition = itemPosition;
            this.resultAction = resultAction;
        }

        public void clear() {
            this.resultAction = null;
        }
    }

    public ItemSlidingAnimator(SwipeableItemWrapperAdapter<ViewHolder> adapter) {
        this.mAdapter = adapter;
        this.mActive = new ArrayList();
        this.mDeferredProcesses = new ArrayList();
    }

    public void slideToDefaultPosition(ViewHolder holder, boolean horizontal, boolean shouldAnimate, long duration) {
        cancelDeferredProcess(holder);
        slideToSpecifiedPositionInternal(holder, 0.0f, false, horizontal, shouldAnimate, this.mSlideToDefaultPositionAnimationInterpolator, duration, null);
    }

    public void slideToOutsideOfWindow(ViewHolder holder, int dir, boolean shouldAnimate, long duration) {
        cancelDeferredProcess(holder);
        slideToOutsideOfWindowInternal(holder, dir, shouldAnimate, duration, null);
    }

    public void slideToSpecifiedPosition(ViewHolder holder, float amount, boolean proportionalAmount, boolean horizontal, boolean shouldAnimate, long duration) {
        cancelDeferredProcess(holder);
        slideToSpecifiedPositionInternal(holder, amount, proportionalAmount, horizontal, shouldAnimate, this.mSlideToSpecifiedPositionAnimationInterpolator, duration, null);
    }

    public boolean finishSwipeSlideToDefaultPosition(ViewHolder holder, boolean horizontal, boolean shouldAnimate, long duration, int itemPosition, SwipeResultAction resultAction) {
        cancelDeferredProcess(holder);
        return slideToSpecifiedPositionInternal(holder, 0.0f, false, horizontal, shouldAnimate, this.mSlideToDefaultPositionAnimationInterpolator, duration, new SwipeFinishInfo(itemPosition, resultAction));
    }

    public boolean finishSwipeSlideToOutsideOfWindow(ViewHolder holder, int dir, boolean shouldAnimate, long duration, int itemPosition, SwipeResultAction resultAction) {
        cancelDeferredProcess(holder);
        return slideToOutsideOfWindowInternal(holder, dir, shouldAnimate, duration, new SwipeFinishInfo(itemPosition, resultAction));
    }

    private void cancelDeferredProcess(ViewHolder holder) {
        for (int i = this.mDeferredProcesses.size() - 1; i >= 0; i--) {
            ViewHolderDeferredProcess dp = (ViewHolderDeferredProcess) ((WeakReference) this.mDeferredProcesses.get(i)).get();
            if (dp != null && dp.hasTargetViewHolder(holder)) {
                holder.itemView.removeCallbacks(dp);
                this.mDeferredProcesses.remove(i);
            } else if (dp == null || dp.lostReference(holder)) {
                this.mDeferredProcesses.remove(i);
            }
        }
    }

    private void scheduleViewHolderDeferredSlideProcess(ViewHolder holder, ViewHolderDeferredProcess deferredProcess) {
        this.mDeferredProcesses.add(new WeakReference(deferredProcess));
        holder.itemView.post(deferredProcess);
    }

    private boolean slideToSpecifiedPositionInternal(ViewHolder holder, float amount, boolean proportional, boolean horizontal, boolean shouldAnimate, Interpolator interpolator, long duration, SwipeFinishInfo swipeFinish) {
        View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
        if (shouldAnimate) {
            shouldAnimate = ViewCompat.isAttachedToWindow(containerView) && containerView.getVisibility() == 0;
        }
        if (!shouldAnimate) {
            duration = 0;
        }
        if (amount == 0.0f) {
            return animateSlideInternalCompat(holder, horizontal, 0, 0, duration, interpolator, swipeFinish);
        }
        int width = containerView.getWidth();
        int height = containerView.getHeight();
        if (horizontal && (!proportional || width != 0)) {
            if (proportional) {
                amount *= (float) width;
            }
            return animateSlideInternalCompat(holder, horizontal, (int) (0.5f + amount), 0, duration, interpolator, swipeFinish);
        } else if (!horizontal && (!proportional || height != 0)) {
            if (proportional) {
                amount *= (float) height;
            }
            return animateSlideInternalCompat(holder, horizontal, 0, (int) (0.5f + amount), duration, interpolator, swipeFinish);
        } else if (swipeFinish != null) {
            throw new IllegalStateException("Unexpected state in slideToSpecifiedPositionInternal (swipeFinish == null)");
        } else {
            scheduleViewHolderDeferredSlideProcess(holder, new DeferredSlideProcess(holder, amount, horizontal));
            return false;
        }
    }

    private boolean slideToOutsideOfWindowInternal(ViewHolder holder, int dir, boolean shouldAnimate, long duration, SwipeFinishInfo swipeFinish) {
        if (!(holder instanceof SwipeableItemViewHolder)) {
            return false;
        }
        View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
        ViewGroup parent = (ViewGroup) containerView.getParent();
        if (parent == null) {
            return false;
        }
        int left = containerView.getLeft();
        int right = containerView.getRight();
        int top = containerView.getTop();
        int width = right - left;
        int height = containerView.getBottom() - top;
        parent.getWindowVisibleDisplayFrame(this.mTmpRect);
        int windowWidth = this.mTmpRect.width();
        int windowHeight = this.mTmpRect.height();
        int translateX = 0;
        int translateY = 0;
        if (width != 0 && height != 0) {
            parent.getLocationInWindow(this.mTmpLocation);
            int x = this.mTmpLocation[0];
            int y = this.mTmpLocation[1];
            switch (dir) {
                case 0:
                    translateX = -(x + width);
                    break;
                case 1:
                    translateY = -(y + height);
                    break;
                case 2:
                    translateX = windowWidth - (x - left);
                    break;
                case 3:
                    translateY = windowHeight - (y - top);
                    break;
                default:
                    break;
            }
        }
        switch (dir) {
            case 0:
                translateX = -windowWidth;
                break;
            case 1:
                translateY = -windowHeight;
                break;
            case 2:
                translateX = windowWidth;
                break;
            case 3:
                translateY = windowHeight;
                break;
        }
        shouldAnimate = false;
        if (shouldAnimate) {
            shouldAnimate = ViewCompat.isAttachedToWindow(containerView) && containerView.getVisibility() == 0;
        }
        if (!shouldAnimate) {
            duration = 0;
        }
        boolean horizontal = dir == 0 || dir == 2;
        return animateSlideInternalCompat(holder, horizontal, translateX, translateY, duration, this.mSlideToOutsideOfWindowAnimationInterpolator, swipeFinish);
    }

    private boolean animateSlideInternalCompat(ViewHolder holder, boolean horizontal, int translationX, int translationY, long duration, Interpolator interpolator, SwipeFinishInfo swipeFinish) {
        if (supportsViewPropertyAnimator()) {
            return animateSlideInternal(holder, horizontal, translationX, translationY, duration, interpolator, swipeFinish);
        }
        return slideInternalPreHoneycomb(holder, horizontal, translationX, translationY);
    }

    static void slideInternalCompat(ViewHolder holder, boolean horizontal, int translationX, int translationY) {
        if (supportsViewPropertyAnimator()) {
            slideInternal(holder, horizontal, translationX, translationY);
        } else {
            slideInternalPreHoneycomb(holder, horizontal, translationX, translationY);
        }
    }

    @SuppressLint({"RtlHardcoded"})
    private static boolean slideInternalPreHoneycomb(ViewHolder holder, boolean horizontal, int translationX, int translationY) {
        if (holder instanceof SwipeableItemViewHolder) {
            View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
            LayoutParams lp = containerView.getLayoutParams();
            if (lp instanceof MarginLayoutParams) {
                MarginLayoutParams mlp = (MarginLayoutParams) lp;
                mlp.leftMargin = translationX;
                mlp.rightMargin = -translationX;
                mlp.topMargin = translationY;
                mlp.bottomMargin = -translationY;
                if (lp instanceof FrameLayout.LayoutParams) {
                    ((FrameLayout.LayoutParams) lp).gravity = 51;
                }
                containerView.setLayoutParams(mlp);
            } else {
                Log.w(TAG, "should use MarginLayoutParams supported view for compatibility on Android 2.3");
            }
        }
        return false;
    }

    private static int getTranslationXPreHoneycomb(ViewHolder holder) {
        LayoutParams lp = SwipeableViewHolderUtils.getSwipeableContainerView(holder).getLayoutParams();
        if (lp instanceof MarginLayoutParams) {
            return ((MarginLayoutParams) lp).leftMargin;
        }
        Log.w(TAG, "should use MarginLayoutParams supported view for compatibility on Android 2.3");
        return 0;
    }

    private static int getTranslationYPreHoneycomb(ViewHolder holder) {
        LayoutParams lp = SwipeableViewHolderUtils.getSwipeableContainerView(holder).getLayoutParams();
        if (lp instanceof MarginLayoutParams) {
            return ((MarginLayoutParams) lp).topMargin;
        }
        Log.w(TAG, "should use MarginLayoutParams supported view for compatibility on Android 2.3");
        return 0;
    }

    private static void slideInternal(ViewHolder holder, boolean horizontal, int translationX, int translationY) {
        if (holder instanceof SwipeableItemViewHolder) {
            View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
            ViewCompat.animate(containerView).cancel();
            ViewCompat.setTranslationX(containerView, (float) translationX);
            ViewCompat.setTranslationY(containerView, (float) translationY);
        }
    }

    private boolean animateSlideInternal(ViewHolder holder, boolean horizontal, int translationX, int translationY, long duration, Interpolator interpolator, SwipeFinishInfo swipeFinish) {
        if (!(holder instanceof SwipeableItemViewHolder)) {
            return false;
        }
        View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
        int prevTranslationX = (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        int prevTranslationY = (int) (ViewCompat.getTranslationY(containerView) + 0.5f);
        endAnimation(holder);
        int curTranslationX = (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        int curTranslationY = (int) (ViewCompat.getTranslationY(containerView) + 0.5f);
        int toX = translationX;
        int toY = translationY;
        if (duration == 0 || ((curTranslationX == toX && curTranslationY == toY) || Math.max(Math.abs(toX - prevTranslationX), Math.abs(toY - prevTranslationY)) <= this.mImmediatelySetTranslationThreshold)) {
            ViewCompat.setTranslationX(containerView, (float) toX);
            ViewCompat.setTranslationY(containerView, (float) toY);
            return false;
        }
        ViewCompat.setTranslationX(containerView, (float) prevTranslationX);
        ViewCompat.setTranslationY(containerView, (float) prevTranslationY);
        new SlidingAnimatorListenerObject(this.mAdapter, this.mActive, holder, toX, toY, duration, horizontal, interpolator, swipeFinish).start();
        return true;
    }

    public void endAnimation(ViewHolder holder) {
        if (holder instanceof SwipeableItemViewHolder) {
            cancelDeferredProcess(holder);
            ViewCompat.animate(SwipeableViewHolderUtils.getSwipeableContainerView(holder)).cancel();
            if (this.mActive.remove(holder)) {
                throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [slide]");
            }
        }
    }

    public void endAnimations() {
        for (int i = this.mActive.size() - 1; i >= 0; i--) {
            endAnimation((ViewHolder) this.mActive.get(i));
        }
    }

    public boolean isRunning(ViewHolder holder) {
        return this.mActive.contains(holder);
    }

    public boolean isRunning() {
        return !this.mActive.isEmpty();
    }

    public int getImmediatelySetTranslationThreshold() {
        return this.mImmediatelySetTranslationThreshold;
    }

    public void setImmediatelySetTranslationThreshold(int threshold) {
        this.mImmediatelySetTranslationThreshold = threshold;
    }

    private static boolean supportsViewPropertyAnimator() {
        return VERSION.SDK_INT >= 11;
    }

    public int getSwipeContainerViewTranslationX(ViewHolder holder) {
        if (supportsViewPropertyAnimator()) {
            return (int) (ViewCompat.getTranslationX(SwipeableViewHolderUtils.getSwipeableContainerView(holder)) + 0.5f);
        }
        return getTranslationXPreHoneycomb(holder);
    }

    public int getSwipeContainerViewTranslationY(ViewHolder holder) {
        if (supportsViewPropertyAnimator()) {
            return (int) (ViewCompat.getTranslationY(SwipeableViewHolderUtils.getSwipeableContainerView(holder)) + 0.5f);
        }
        return getTranslationYPreHoneycomb(holder);
    }
}
