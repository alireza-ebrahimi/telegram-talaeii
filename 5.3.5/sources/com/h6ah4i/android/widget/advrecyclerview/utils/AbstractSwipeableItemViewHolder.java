package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemViewHolder;

public abstract class AbstractSwipeableItemViewHolder extends ViewHolder implements SwipeableItemViewHolder {
    private int mAfterSwipeReaction = 0;
    private float mHorizontalSwipeAmount;
    private boolean mIsProportionalAmountModeEnabled = true;
    private float mMaxDownSwipeAmount = 65537.0f;
    private float mMaxLeftSwipeAmount = -65536.0f;
    private float mMaxRightSwipeAmount = 65536.0f;
    private float mMaxUpSwipeAmount = -65537.0f;
    private int mSwipeResult = 0;
    private int mSwipeStateFlags;
    private float mVerticalSwipeAmount;

    public abstract View getSwipeableContainerView();

    public AbstractSwipeableItemViewHolder(View itemView) {
        super(itemView);
    }

    public void setSwipeStateFlags(int flags) {
        this.mSwipeStateFlags = flags;
    }

    public int getSwipeStateFlags() {
        return this.mSwipeStateFlags;
    }

    public void setSwipeResult(int result) {
        this.mSwipeResult = result;
    }

    public int getSwipeResult() {
        return this.mSwipeResult;
    }

    public int getAfterSwipeReaction() {
        return this.mAfterSwipeReaction;
    }

    public void setAfterSwipeReaction(int reaction) {
        this.mAfterSwipeReaction = reaction;
    }

    public void setProportionalSwipeAmountModeEnabled(boolean enabled) {
        this.mIsProportionalAmountModeEnabled = enabled;
    }

    public boolean isProportionalSwipeAmountModeEnabled() {
        return this.mIsProportionalAmountModeEnabled;
    }

    public void setSwipeItemVerticalSlideAmount(float amount) {
        this.mVerticalSwipeAmount = amount;
    }

    public float getSwipeItemVerticalSlideAmount() {
        return this.mVerticalSwipeAmount;
    }

    public void setSwipeItemHorizontalSlideAmount(float amount) {
        this.mHorizontalSwipeAmount = amount;
    }

    public float getSwipeItemHorizontalSlideAmount() {
        return this.mHorizontalSwipeAmount;
    }

    public void setMaxLeftSwipeAmount(float amount) {
        this.mMaxLeftSwipeAmount = amount;
    }

    public float getMaxLeftSwipeAmount() {
        return this.mMaxLeftSwipeAmount;
    }

    public void setMaxUpSwipeAmount(float amount) {
        this.mMaxUpSwipeAmount = amount;
    }

    public float getMaxUpSwipeAmount() {
        return this.mMaxUpSwipeAmount;
    }

    public void setMaxRightSwipeAmount(float amount) {
        this.mMaxRightSwipeAmount = amount;
    }

    public float getMaxRightSwipeAmount() {
        return this.mMaxRightSwipeAmount;
    }

    public void setMaxDownSwipeAmount(float amount) {
        this.mMaxDownSwipeAmount = amount;
    }

    public float getMaxDownSwipeAmount() {
        return this.mMaxDownSwipeAmount;
    }

    public void onSlideAmountUpdated(float horizontalAmount, float verticalAmount, boolean isSwiping) {
    }
}
