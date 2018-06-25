package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.annotation.Nullable;
import android.view.View;

public interface SwipeableItemViewHolder {
    int getAfterSwipeReaction();

    float getMaxDownSwipeAmount();

    float getMaxLeftSwipeAmount();

    float getMaxRightSwipeAmount();

    float getMaxUpSwipeAmount();

    float getSwipeItemHorizontalSlideAmount();

    float getSwipeItemVerticalSlideAmount();

    int getSwipeResult();

    int getSwipeStateFlags();

    @Nullable
    View getSwipeableContainerView();

    boolean isProportionalSwipeAmountModeEnabled();

    void onSlideAmountUpdated(float f, float f2, boolean z);

    void setAfterSwipeReaction(int i);

    void setMaxDownSwipeAmount(float f);

    void setMaxLeftSwipeAmount(float f);

    void setMaxRightSwipeAmount(float f);

    void setMaxUpSwipeAmount(float f);

    void setProportionalSwipeAmountModeEnabled(boolean z);

    void setSwipeItemHorizontalSlideAmount(float f);

    void setSwipeItemVerticalSlideAmount(float f);

    void setSwipeResult(int i);

    void setSwipeStateFlags(int i);
}
