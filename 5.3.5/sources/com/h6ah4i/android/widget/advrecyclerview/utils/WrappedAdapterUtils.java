package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.adapter.WrappedAdapter;
import com.h6ah4i.android.widget.advrecyclerview.adapter.WrapperAdapter;

public class WrappedAdapterUtils {
    private WrappedAdapterUtils() {
    }

    public static void invokeOnViewRecycled(@NonNull Adapter adapter, @NonNull ViewHolder holder, int viewType) {
        if (adapter instanceof WrapperAdapter) {
            ((WrapperAdapter) adapter).onViewRecycled(holder, viewType);
        } else {
            adapter.onViewRecycled(holder);
        }
    }

    public static boolean invokeOnFailedToRecycleView(@NonNull Adapter adapter, @NonNull ViewHolder holder, int viewType) {
        if (adapter instanceof WrappedAdapter) {
            return ((WrappedAdapter) adapter).onFailedToRecycleView(holder, viewType);
        }
        return adapter.onFailedToRecycleView(holder);
    }

    public static void invokeOnViewAttachedToWindow(@NonNull Adapter adapter, @NonNull ViewHolder holder, int viewType) {
        if (adapter instanceof WrappedAdapter) {
            ((WrappedAdapter) adapter).onViewAttachedToWindow(holder, viewType);
        } else {
            adapter.onViewAttachedToWindow(holder);
        }
    }

    public static void invokeOnViewDetachedFromWindow(Adapter adapter, ViewHolder holder, int viewType) {
        if (adapter instanceof WrappedAdapter) {
            ((WrappedAdapter) adapter).onViewDetachedFromWindow(holder, viewType);
        } else {
            adapter.onViewDetachedFromWindow(holder);
        }
    }
}
