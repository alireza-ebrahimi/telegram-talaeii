package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class RemoveAnimationInfo extends ItemAnimationInfo {
    public ViewHolder holder;

    public RemoveAnimationInfo(ViewHolder holder) {
        this.holder = holder;
    }

    public ViewHolder getAvailableViewHolder() {
        return this.holder;
    }

    public void clear(ViewHolder item) {
        if (this.holder == item) {
            this.holder = null;
        }
    }

    public String toString() {
        return "RemoveAnimationInfo{holder=" + this.holder + '}';
    }
}
