package org.telegram.messenger.support.widget;

import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class DefaultItemAnimator$MoveInfo {
    public int fromX;
    public int fromY;
    public ViewHolder holder;
    public int toX;
    public int toY;

    DefaultItemAnimator$MoveInfo(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        this.holder = holder;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }
}
