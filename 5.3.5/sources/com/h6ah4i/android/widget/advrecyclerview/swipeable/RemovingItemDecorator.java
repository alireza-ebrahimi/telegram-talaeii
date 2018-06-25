package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import java.lang.ref.WeakReference;

class RemovingItemDecorator extends ItemDecoration {
    private static final long ADDITIONAL_REMOVE_DURATION = 50;
    private static final int NOTIFY_REMOVAL_EFFECT_END = 1;
    private static final int NOTIFY_REMOVAL_EFFECT_PHASE_1 = 0;
    private static final String TAG = "RemovingItemDecorator";
    private final boolean mHorizontal;
    private final long mMoveAnimationDuration;
    private Interpolator mMoveAnimationInterpolator;
    private int mPendingNotificationMask = 0;
    private RecyclerView mRecyclerView;
    private final long mRemoveAnimationDuration;
    private long mStartTime;
    private Drawable mSwipeBackgroundDrawable;
    private ViewHolder mSwipingItem;
    private final Rect mSwipingItemBounds = new Rect();
    private final long mSwipingItemId;
    private int mTranslationX;
    private int mTranslationY;

    private static class DelayedNotificationRunner implements Runnable {
        private final int mCode;
        private WeakReference<RemovingItemDecorator> mRefDecorator;

        public DelayedNotificationRunner(RemovingItemDecorator decorator, int code) {
            this.mRefDecorator = new WeakReference(decorator);
            this.mCode = code;
        }

        public void run() {
            RemovingItemDecorator decorator = (RemovingItemDecorator) this.mRefDecorator.get();
            this.mRefDecorator.clear();
            this.mRefDecorator = null;
            if (decorator != null) {
                decorator.onDelayedNotification(this.mCode);
            }
        }
    }

    public RemovingItemDecorator(RecyclerView rv, ViewHolder swipingItem, int result, long removeAnimationDuration, long moveAnimationDuration) {
        boolean z = false;
        this.mRecyclerView = rv;
        this.mSwipingItem = swipingItem;
        this.mSwipingItemId = swipingItem.getItemId();
        if (result == 2 || result == 4) {
            z = true;
        }
        this.mHorizontal = z;
        this.mRemoveAnimationDuration = ADDITIONAL_REMOVE_DURATION + removeAnimationDuration;
        this.mMoveAnimationDuration = moveAnimationDuration;
        this.mTranslationX = (int) (ViewCompat.getTranslationX(swipingItem.itemView) + 0.5f);
        this.mTranslationY = (int) (ViewCompat.getTranslationY(swipingItem.itemView) + 0.5f);
        CustomRecyclerViewUtils.getViewBounds(this.mSwipingItem.itemView, this.mSwipingItemBounds);
    }

    public void setMoveAnimationInterpolator(Interpolator interpolator) {
        this.mMoveAnimationInterpolator = interpolator;
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        long elapsedTime = getElapsedTime(this.mStartTime);
        fillSwipingItemBackground(c, this.mSwipeBackgroundDrawable, determineBackgroundScaleSwipeCompletedSuccessfully(elapsedTime));
        if (this.mSwipingItemId == this.mSwipingItem.getItemId()) {
            this.mTranslationX = (int) (ViewCompat.getTranslationX(this.mSwipingItem.itemView) + 0.5f);
            this.mTranslationY = (int) (ViewCompat.getTranslationY(this.mSwipingItem.itemView) + 0.5f);
        }
        if (requiresContinuousAnimationAfterSwipeCompletedSuccessfully(elapsedTime)) {
            postInvalidateOnAnimation();
        }
    }

    private boolean requiresContinuousAnimationAfterSwipeCompletedSuccessfully(long elapsedTime) {
        return elapsedTime >= this.mRemoveAnimationDuration && elapsedTime < this.mRemoveAnimationDuration + this.mMoveAnimationDuration;
    }

    private float determineBackgroundScaleSwipeCompletedSuccessfully(long elapsedTime) {
        if (elapsedTime < this.mRemoveAnimationDuration) {
            return 1.0f;
        }
        if (elapsedTime >= this.mRemoveAnimationDuration + this.mMoveAnimationDuration || this.mMoveAnimationDuration == 0) {
            return 0.0f;
        }
        float heightScale = 1.0f - (((float) (elapsedTime - this.mRemoveAnimationDuration)) / ((float) this.mMoveAnimationDuration));
        if (this.mMoveAnimationInterpolator != null) {
            return this.mMoveAnimationInterpolator.getInterpolation(heightScale);
        }
        return heightScale;
    }

    private void fillSwipingItemBackground(Canvas c, Drawable drawable, float scale) {
        float hScale;
        Rect bounds = this.mSwipingItemBounds;
        int translationX = this.mTranslationX;
        int translationY = this.mTranslationY;
        if (this.mHorizontal) {
            hScale = 1.0f;
        } else {
            hScale = scale;
        }
        int width = (int) ((((float) bounds.width()) * hScale) + 0.5f);
        int height = (int) ((((float) bounds.height()) * (this.mHorizontal ? scale : 1.0f)) + 0.5f);
        if (height != 0 && width != 0 && drawable != null) {
            int savedCount = c.save();
            c.clipRect(bounds.left + translationX, bounds.top + translationY, (bounds.left + translationX) + width, (bounds.top + translationY) + height);
            c.translate((float) ((bounds.left + translationX) - ((bounds.width() - width) / 2)), (float) ((bounds.top + translationY) - ((bounds.height() - height) / 2)));
            drawable.setBounds(0, 0, bounds.width(), bounds.height());
            drawable.draw(c);
            c.restoreToCount(savedCount);
        }
    }

    private void postInvalidateOnAnimation() {
        ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
    }

    public void start() {
        ViewCompat.animate(SwipeableViewHolderUtils.getSwipeableContainerView(this.mSwipingItem)).cancel();
        this.mRecyclerView.addItemDecoration(this);
        this.mStartTime = System.currentTimeMillis();
        this.mTranslationY = (int) (ViewCompat.getTranslationY(this.mSwipingItem.itemView) + 0.5f);
        this.mSwipeBackgroundDrawable = this.mSwipingItem.itemView.getBackground();
        postInvalidateOnAnimation();
        notifyDelayed(0, this.mRemoveAnimationDuration);
    }

    private void notifyDelayed(int code, long delay) {
        int mask = 1 << code;
        if ((this.mPendingNotificationMask & mask) == 0) {
            this.mPendingNotificationMask |= mask;
            ViewCompat.postOnAnimationDelayed(this.mRecyclerView, new DelayedNotificationRunner(this, code), delay);
        }
    }

    void onDelayedNotification(int code) {
        int mask = 1 << code;
        long elapsedTime = getElapsedTime(this.mStartTime);
        this.mPendingNotificationMask &= mask ^ -1;
        switch (code) {
            case 0:
                if (elapsedTime < this.mRemoveAnimationDuration) {
                    notifyDelayed(0, this.mRemoveAnimationDuration - elapsedTime);
                    return;
                }
                postInvalidateOnAnimation();
                notifyDelayed(1, this.mMoveAnimationDuration);
                return;
            case 1:
                finish();
                return;
            default:
                return;
        }
    }

    private void finish() {
        this.mRecyclerView.removeItemDecoration(this);
        postInvalidateOnAnimation();
        this.mRecyclerView = null;
        this.mSwipingItem = null;
        this.mTranslationY = 0;
        this.mMoveAnimationInterpolator = null;
    }

    protected static long getElapsedTime(long initialTime) {
        long curTime = System.currentTimeMillis();
        return curTime >= initialTime ? curTime - initialTime : Long.MAX_VALUE;
    }
}
