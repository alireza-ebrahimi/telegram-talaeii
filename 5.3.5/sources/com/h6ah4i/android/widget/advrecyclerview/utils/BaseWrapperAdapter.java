package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.adapter.SimpleWrapperAdapter;

public class BaseWrapperAdapter<VH extends ViewHolder> extends SimpleWrapperAdapter<VH> {
    public BaseWrapperAdapter(Adapter<VH> adapter) {
        super(adapter);
    }
}
