package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;

public class AdapterPathSegment {
    public final Adapter adapter;
    public final Object tag;

    public AdapterPathSegment(@NonNull Adapter adapter, @Nullable Object tag) {
        this.adapter = adapter;
        this.tag = tag;
    }
}
