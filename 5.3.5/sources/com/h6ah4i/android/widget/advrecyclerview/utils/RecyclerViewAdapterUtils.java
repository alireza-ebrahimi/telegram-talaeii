package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewParent;

public class RecyclerViewAdapterUtils {
    private RecyclerViewAdapterUtils() {
    }

    @Nullable
    public static RecyclerView getParentRecyclerView(@Nullable View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof RecyclerView) {
            return (RecyclerView) parent;
        }
        return parent instanceof View ? getParentRecyclerView((View) parent) : null;
    }

    @Nullable
    public static View getParentViewHolderItemView(@Nullable View view) {
        RecyclerView rv = getParentRecyclerView(view);
        if (rv == null) {
            return null;
        }
        return rv.findContainingItemView(view);
    }

    @Nullable
    public static ViewHolder getViewHolder(@Nullable View view) {
        RecyclerView rv = getParentRecyclerView(view);
        if (rv == null) {
            return null;
        }
        return rv.findContainingViewHolder(view);
    }
}
