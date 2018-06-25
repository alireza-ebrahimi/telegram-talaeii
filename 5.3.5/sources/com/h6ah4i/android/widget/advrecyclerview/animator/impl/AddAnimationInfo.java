package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class AddAnimationInfo extends ItemAnimationInfo {
    public ViewHolder holder;

    public AddAnimationInfo(ViewHolder holder) {
        this.holder = holder;
    }

    public ViewHolder getAvailableViewHolder() {
        return this.holder;
    }

    public void clear(ViewHolder item) {
        if (this.holder == null) {
            this.holder = null;
        }
    }

    public String toString() {
        return "AddAnimationInfo{holder=" + this.holder + '}';
    }
}
