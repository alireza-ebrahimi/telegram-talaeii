package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class ChangeAnimationInfo extends ItemAnimationInfo {
    public int fromX;
    public int fromY;
    public ViewHolder newHolder;
    public ViewHolder oldHolder;
    public int toX;
    public int toY;

    public ChangeAnimationInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        this.oldHolder = oldHolder;
        this.newHolder = newHolder;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public ViewHolder getAvailableViewHolder() {
        return this.oldHolder != null ? this.oldHolder : this.newHolder;
    }

    public void clear(ViewHolder item) {
        if (this.oldHolder == item) {
            this.oldHolder = null;
        }
        if (this.newHolder == item) {
            this.newHolder = null;
        }
        if (this.oldHolder == null && this.newHolder == null) {
            this.fromX = 0;
            this.fromY = 0;
            this.toX = 0;
            this.toY = 0;
        }
    }

    public String toString() {
        return "ChangeInfo{, oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
    }
}
