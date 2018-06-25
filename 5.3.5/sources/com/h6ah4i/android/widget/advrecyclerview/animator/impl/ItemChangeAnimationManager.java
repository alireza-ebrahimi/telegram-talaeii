package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemChangeAnimationManager extends BaseItemAnimationManager<ChangeAnimationInfo> {
    private static final String TAG = "ARVItemChangeAnimMgr";

    public abstract boolean addPendingAnimation(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4);

    protected abstract void onCreateChangeAnimationForNewItem(ChangeAnimationInfo changeAnimationInfo);

    protected abstract void onCreateChangeAnimationForOldItem(ChangeAnimationInfo changeAnimationInfo);

    public ItemChangeAnimationManager(BaseItemAnimator itemAnimator) {
        super(itemAnimator);
    }

    public void dispatchStarting(ChangeAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchChangeStarting(" + item + ")");
        }
        this.mItemAnimator.dispatchChangeStarting(item, item == info.oldHolder);
    }

    public void dispatchFinished(ChangeAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchChangeFinished(" + item + ")");
        }
        this.mItemAnimator.dispatchChangeFinished(item, item == info.oldHolder);
    }

    public long getDuration() {
        return this.mItemAnimator.getChangeDuration();
    }

    public void setDuration(long duration) {
        this.mItemAnimator.setChangeDuration(duration);
    }

    protected void onCreateAnimation(ChangeAnimationInfo info) {
        if (!(info.oldHolder == null || info.oldHolder.itemView == null)) {
            onCreateChangeAnimationForOldItem(info);
        }
        if (info.newHolder != null && info.newHolder.itemView != null) {
            onCreateChangeAnimationForNewItem(info);
        }
    }

    protected boolean endNotStartedAnimation(ChangeAnimationInfo info, ViewHolder item) {
        if (info.oldHolder != null && (item == null || info.oldHolder == item)) {
            onAnimationEndedBeforeStarted(info, info.oldHolder);
            dispatchFinished(info, info.oldHolder);
            info.clear(info.oldHolder);
        }
        if (info.newHolder != null && (item == null || info.newHolder == item)) {
            onAnimationEndedBeforeStarted(info, info.newHolder);
            dispatchFinished(info, info.newHolder);
            info.clear(info.newHolder);
        }
        return info.oldHolder == null && info.newHolder == null;
    }
}
