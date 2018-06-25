package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

class SwipeableViewHolderUtils {
    SwipeableViewHolderUtils() {
    }

    public static View getSwipeableContainerView(ViewHolder vh) {
        if (vh instanceof SwipeableItemViewHolder) {
            return getSwipeableContainerView((SwipeableItemViewHolder) vh);
        }
        return null;
    }

    public static View getSwipeableContainerView(SwipeableItemViewHolder vh) {
        if (!(vh instanceof ViewHolder)) {
            return null;
        }
        View swipeableContainerView = vh.getSwipeableContainerView();
        if (swipeableContainerView != ((ViewHolder) vh).itemView) {
            return swipeableContainerView;
        }
        throw new IllegalStateException("Inconsistency detected! getSwipeableContainerView() returns itemView");
    }
}
