package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class MoveAnimationInfo extends ItemAnimationInfo {
    public final int fromX;
    public final int fromY;
    public ViewHolder holder;
    public final int toX;
    public final int toY;

    public MoveAnimationInfo(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        this.holder = holder;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
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
        return "MoveAnimationInfo{holder=" + this.holder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
    }
}
