package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;

public abstract class AbstractDraggableItemViewHolder extends ViewHolder implements DraggableItemViewHolder {
    private int mDragStateFlags;

    public AbstractDraggableItemViewHolder(View itemView) {
        super(itemView);
    }

    public void setDragStateFlags(int flags) {
        this.mDragStateFlags = flags;
    }

    public int getDragStateFlags() {
        return this.mDragStateFlags;
    }
}
