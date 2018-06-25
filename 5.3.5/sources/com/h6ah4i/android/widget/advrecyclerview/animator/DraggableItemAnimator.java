package com.h6ah4i.android.widget.advrecyclerview.animator;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class DraggableItemAnimator extends RefactoredDefaultItemAnimator {
    protected void onSetup() {
        super.onSetup();
        super.setSupportsChangeAnimations(false);
    }

    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        if (oldHolder != newHolder || fromX != toX || fromY != toY) {
            return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
        }
        dispatchChangeFinished(oldHolder, true);
        return false;
    }
}
