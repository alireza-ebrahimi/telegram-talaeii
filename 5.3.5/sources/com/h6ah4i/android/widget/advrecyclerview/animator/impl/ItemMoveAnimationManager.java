package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemMoveAnimationManager extends BaseItemAnimationManager<MoveAnimationInfo> {
    public static final String TAG = "ARVItemMoveAnimMgr";

    public abstract boolean addPendingAnimation(ViewHolder viewHolder, int i, int i2, int i3, int i4);

    public ItemMoveAnimationManager(BaseItemAnimator itemAnimator) {
        super(itemAnimator);
    }

    public long getDuration() {
        return this.mItemAnimator.getMoveDuration();
    }

    public void setDuration(long duration) {
        this.mItemAnimator.setMoveDuration(duration);
    }

    public void dispatchStarting(MoveAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchMoveStarting(" + item + ")");
        }
        this.mItemAnimator.dispatchMoveStarting(item);
    }

    public void dispatchFinished(MoveAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchMoveFinished(" + item + ")");
        }
        this.mItemAnimator.dispatchMoveFinished(item);
    }

    protected boolean endNotStartedAnimation(MoveAnimationInfo info, ViewHolder item) {
        if (info.holder == null || (item != null && info.holder != item)) {
            return false;
        }
        onAnimationEndedBeforeStarted(info, info.holder);
        dispatchFinished(info, info.holder);
        info.clear(info.holder);
        return true;
    }
}
