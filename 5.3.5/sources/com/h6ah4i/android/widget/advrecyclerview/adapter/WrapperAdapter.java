package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.List;

public interface WrapperAdapter<VH extends ViewHolder> extends WrappedAdapter<VH> {
    void getWrappedAdapters(@NonNull List<Adapter> list);

    void release();

    void unwrapPosition(@NonNull UnwrapPositionResult unwrapPositionResult, int i);

    int wrapPosition(@NonNull AdapterPathSegment adapterPathSegment, int i);
}
