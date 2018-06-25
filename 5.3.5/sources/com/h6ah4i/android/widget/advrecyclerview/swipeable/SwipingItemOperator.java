package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.Interpolator;

class SwipingItemOperator {
    private static final int MIN_GRABBING_AREA_SIZE = 48;
    private static final int REACTION_CAN_NOT_SWIPE = 0;
    private static final int REACTION_CAN_NOT_SWIPE_WITH_RUBBER_BAND_EFFECT = 1;
    private static final int REACTION_CAN_SWIPE = 2;
    private static final Interpolator RUBBER_BAND_INTERPOLATOR = new RubberBandInterpolator(RUBBER_BAND_LIMIT);
    private static final float RUBBER_BAND_LIMIT = 0.15f;
    private static final String TAG = "SwipingItemOperator";
    private int mDownSwipeReactionType;
    private int mInitialTranslateAmountX;
    private int mInitialTranslateAmountY;
    private float mInvSwipingItemHeight = calcInv(this.mSwipingItemHeight);
    private float mInvSwipingItemWidth = calcInv(this.mSwipingItemWidth);
    private int mLeftSwipeReactionType;
    private float mPrevTranslateAmount;
    private int mRightSwipeReactionType;
    private int mSwipeDistanceX;
    private int mSwipeDistanceY;
    private final boolean mSwipeHorizontal;
    private RecyclerViewSwipeManager mSwipeManager;
    private ViewHolder mSwipingItem;
    private View mSwipingItemContainerView;
    private final int mSwipingItemHeight = this.mSwipingItemContainerView.getHeight();
    private int mSwipingItemWidth = this.mSwipingItemContainerView.getWidth();
    private int mUpSwipeReactionType;

    public SwipingItemOperator(RecyclerViewSwipeManager manager, ViewHolder swipingItem, int swipeReactionType, boolean swipeHorizontal) {
        this.mSwipeManager = manager;
        this.mSwipingItem = swipingItem;
        this.mLeftSwipeReactionType = SwipeReactionUtils.extractLeftReaction(swipeReactionType);
        this.mUpSwipeReactionType = SwipeReactionUtils.extractUpReaction(swipeReactionType);
        this.mRightSwipeReactionType = SwipeReactionUtils.extractRightReaction(swipeReactionType);
        this.mDownSwipeReactionType = SwipeReactionUtils.extractDownReaction(swipeReactionType);
        this.mSwipeHorizontal = swipeHorizontal;
        this.mSwipingItemContainerView = SwipeableViewHolderUtils.getSwipeableContainerView(swipingItem);
    }

    public void start() {
        float density = this.mSwipingItem.itemView.getResources().getDisplayMetrics().density;
        int maxAmountH = Math.max(0, this.mSwipingItemWidth - ((int) (density * 48.0f)));
        int maxAmountV = Math.max(0, this.mSwipingItemHeight - ((int) (density * 48.0f)));
        this.mInitialTranslateAmountX = clip(this.mSwipeManager.getSwipeContainerViewTranslationX(this.mSwipingItem), -maxAmountH, maxAmountH);
        this.mInitialTranslateAmountY = clip(this.mSwipeManager.getSwipeContainerViewTranslationY(this.mSwipingItem), -maxAmountV, maxAmountV);
    }

    public void finish() {
        this.mSwipeManager = null;
        this.mSwipingItem = null;
        this.mSwipeDistanceX = 0;
        this.mSwipeDistanceY = 0;
        this.mSwipingItemWidth = 0;
        this.mInvSwipingItemWidth = 0.0f;
        this.mInvSwipingItemHeight = 0.0f;
        this.mLeftSwipeReactionType = 0;
        this.mUpSwipeReactionType = 0;
        this.mRightSwipeReactionType = 0;
        this.mDownSwipeReactionType = 0;
        this.mPrevTranslateAmount = 0.0f;
        this.mInitialTranslateAmountX = 0;
        this.mInitialTranslateAmountY = 0;
        this.mSwipingItemContainerView = null;
    }

    public void update(int itemPosition, int swipeDistanceX, int swipeDistanceY) {
        if (this.mSwipeDistanceX != swipeDistanceX || this.mSwipeDistanceY != swipeDistanceY) {
            this.mSwipeDistanceX = swipeDistanceX;
            this.mSwipeDistanceY = swipeDistanceY;
            int distance = this.mSwipeHorizontal ? this.mSwipeDistanceX + this.mInitialTranslateAmountX : this.mSwipeDistanceY + this.mInitialTranslateAmountY;
            int itemSize = this.mSwipeHorizontal ? this.mSwipingItemWidth : this.mSwipingItemHeight;
            float invItemSize = this.mSwipeHorizontal ? this.mInvSwipingItemWidth : this.mInvSwipingItemHeight;
            int reactionType = this.mSwipeHorizontal ? distance > 0 ? this.mRightSwipeReactionType : this.mLeftSwipeReactionType : distance > 0 ? this.mDownSwipeReactionType : this.mUpSwipeReactionType;
            float translateAmount = 0.0f;
            switch (reactionType) {
                case 1:
                    translateAmount = Math.signum((float) distance) * RUBBER_BAND_INTERPOLATOR.getInterpolation(((float) Math.min(Math.abs(distance), itemSize)) * invItemSize);
                    break;
                case 2:
                    translateAmount = Math.min(Math.max(((float) distance) * invItemSize, -1.0f), 1.0f);
                    break;
            }
            this.mSwipeManager.applySlideItem(this.mSwipingItem, itemPosition, this.mPrevTranslateAmount, translateAmount, true, this.mSwipeHorizontal, false, true);
            this.mPrevTranslateAmount = translateAmount;
        }
    }

    private static float calcInv(int value) {
        return value != 0 ? 1.0f / ((float) value) : 0.0f;
    }

    private static int clip(int v, int min, int max) {
        return Math.min(Math.max(v, min), max);
    }
}
