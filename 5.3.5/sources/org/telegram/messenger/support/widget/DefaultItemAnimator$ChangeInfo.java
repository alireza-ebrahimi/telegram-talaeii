package org.telegram.messenger.support.widget;

import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

class DefaultItemAnimator$ChangeInfo {
    public int fromX;
    public int fromY;
    public ViewHolder newHolder;
    public ViewHolder oldHolder;
    public int toX;
    public int toY;

    private DefaultItemAnimator$ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder) {
        this.oldHolder = oldHolder;
        this.newHolder = newHolder;
    }

    DefaultItemAnimator$ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        this(oldHolder, newHolder);
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public String toString() {
        return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
    }
}
