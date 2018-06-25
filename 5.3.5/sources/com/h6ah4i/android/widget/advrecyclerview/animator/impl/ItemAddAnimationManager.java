package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemAddAnimationManager extends BaseItemAnimationManager<AddAnimationInfo> {
    private static final String TAG = "ARVItemAddAnimMgr";

    public abstract boolean addPendingAnimation(ViewHolder viewHolder);

    public ItemAddAnimationManager(BaseItemAnimator itemAnimator) {
        super(itemAnimator);
    }

    public long getDuration() {
        return this.mItemAnimator.getAddDuration();
    }

    public void setDuration(long duration) {
        this.mItemAnimator.setAddDuration(duration);
    }

    public void dispatchStarting(AddAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchAddStarting(" + item + ")");
        }
        this.mItemAnimator.dispatchAddStarting(item);
    }

    public void dispatchFinished(AddAnimationInfo info, ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchAddFinished(" + item + ")");
        }
        this.mItemAnimator.dispatchAddFinished(item);
    }

    protected boolean endNotStartedAnimation(AddAnimationInfo info, ViewHolder item) {
        if (info.holder == null || (item != null && info.holder != item)) {
            return false;
        }
        onAnimationEndedBeforeStarted(info, info.holder);
        dispatchFinished(info, info.holder);
        info.clear(info.holder);
        return true;
    }
}
