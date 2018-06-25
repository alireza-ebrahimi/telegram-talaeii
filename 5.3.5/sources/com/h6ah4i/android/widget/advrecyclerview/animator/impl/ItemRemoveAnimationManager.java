package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemRemoveAnimationManager extends BaseItemAnimationManager<RemoveAnimationInfo> {
    private static final String TAG = "ARVItemRemoveAnimMgr";

    public abstract boolean addPendingAnimation(ViewHolder viewHolder);

    public ItemRemoveAnimationManager(BaseItemAnimator itemAnimator) {
        super(itemAnimator);
    }

    public long getDuration() {
        return this.mItemAnimator.getRemoveDuration();
    }

    public void setDuration(long duration) {
        this.mItemAnimator.setRemoveDuration(duration);
    }

    public void dispatchStarting(RemoveAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchRemoveStarting(" + item + ")");
        }
        this.mItemAnimator.dispatchRemoveStarting(item);
    }

    public void dispatchFinished(RemoveAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchRemoveFinished(" + item + ")");
        }
        this.mItemAnimator.dispatchRemoveFinished(item);
    }

    protected boolean endNotStartedAnimation(RemoveAnimationInfo info, ViewHolder item) {
        if (info.holder == null || (item != null && info.holder != item)) {
            return false;
        }
        onAnimationEndedBeforeStarted(info, info.holder);
        dispatchFinished(info, info.holder);
        info.clear(info.holder);
        return true;
    }
}
