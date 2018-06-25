package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface WrappedAdapter<VH extends ViewHolder> {
    boolean onFailedToRecycleView(VH vh, int i);

    void onViewAttachedToWindow(VH vh, int i);

    void onViewDetachedFromWindow(VH vh, int i);

    void onViewRecycled(VH vh, int i);
}
