package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import java.util.ArrayList;
import java.util.List;

public class AdapterPath {
    private List<AdapterPathSegment> mSegments = new ArrayList();

    public AdapterPath append(@NonNull UnwrapPositionResult wrapResult) {
        return append(wrapResult.adapter, wrapResult.tag);
    }

    public AdapterPath append(@NonNull Adapter adapter, @Nullable Object tag) {
        return append(new AdapterPathSegment(adapter, tag));
    }

    public AdapterPath append(@NonNull AdapterPathSegment segment) {
        this.mSegments.add(segment);
        return this;
    }

    public AdapterPath clear() {
        this.mSegments.clear();
        return this;
    }

    public boolean isEmpty() {
        return this.mSegments.isEmpty();
    }

    public List<AdapterPathSegment> segments() {
        return this.mSegments;
    }

    public AdapterPathSegment firstSegment() {
        return !this.mSegments.isEmpty() ? (AdapterPathSegment) this.mSegments.get(0) : null;
    }

    public AdapterPathSegment lastSegment() {
        return !this.mSegments.isEmpty() ? (AdapterPathSegment) this.mSegments.get(this.mSegments.size() - 1) : null;
    }
}
