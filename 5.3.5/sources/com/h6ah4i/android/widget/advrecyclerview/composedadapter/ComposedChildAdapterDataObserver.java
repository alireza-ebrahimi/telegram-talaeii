package com.h6ah4i.android.widget.advrecyclerview.composedadapter;

import android.support.v7.widget.RecyclerView.Adapter;
import com.h6ah4i.android.widget.advrecyclerview.adapter.BridgeAdapterDataObserver;
import com.h6ah4i.android.widget.advrecyclerview.adapter.BridgeAdapterDataObserver.Subscriber;
import java.util.ArrayList;
import java.util.List;

class ComposedChildAdapterDataObserver extends BridgeAdapterDataObserver {
    public ComposedChildAdapterDataObserver(Subscriber subscriber, Adapter sourceAdapter) {
        super(subscriber, sourceAdapter, new ArrayList());
    }

    private List<ComposedChildAdapterTag> getChildAdapterTags() {
        return (List) getTag();
    }

    public void registerChildAdapterTag(ComposedChildAdapterTag tag) {
        getChildAdapterTags().add(tag);
    }

    public void unregisterChildAdapterTag(ComposedChildAdapterTag tag) {
        getChildAdapterTags().remove(tag);
    }

    public boolean hasChildAdapters() {
        return !getChildAdapterTags().isEmpty();
    }

    public void release() {
        getChildAdapterTags().clear();
    }
}
