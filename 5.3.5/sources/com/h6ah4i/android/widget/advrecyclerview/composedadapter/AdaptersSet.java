package com.h6ah4i.android.widget.advrecyclerview.composedadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import com.h6ah4i.android.widget.advrecyclerview.adapter.BridgeAdapterDataObserver.Subscriber;
import java.util.ArrayList;
import java.util.List;

class AdaptersSet {
    public static long NO_SEGMENTED_POSITION = -1;
    private List<ComposedChildAdapterTag> mAdapterTags = new ArrayList();
    private List<Adapter> mAdapters = new ArrayList();
    private List<ComposedChildAdapterDataObserver> mObservers = new ArrayList();
    private Subscriber mSubscriber;
    private List<Adapter> mUniqueAdapters = new ArrayList();

    public AdaptersSet(Subscriber bridgeSubscriber) {
        this.mSubscriber = bridgeSubscriber;
    }

    public ComposedChildAdapterTag addAdapter(@NonNull Adapter adapter, int position) {
        ComposedChildAdapterDataObserver observer;
        ComposedChildAdapterTag tag = new ComposedChildAdapterTag();
        this.mAdapterTags.add(position, tag);
        this.mAdapters.add(position, adapter);
        int uniqueAdapterIndex = this.mUniqueAdapters.indexOf(adapter);
        if (uniqueAdapterIndex >= 0) {
            observer = (ComposedChildAdapterDataObserver) this.mObservers.get(uniqueAdapterIndex);
        } else {
            observer = new ComposedChildAdapterDataObserver(this.mSubscriber, adapter);
            this.mObservers.add(observer);
            this.mUniqueAdapters.add(adapter);
            adapter.registerAdapterDataObserver(observer);
        }
        observer.registerChildAdapterTag(tag);
        return tag;
    }

    public Adapter removeAdapter(@NonNull ComposedChildAdapterTag tag) {
        int segment = getAdapterSegment(tag);
        if (segment < 0) {
            return null;
        }
        Adapter adapter = (Adapter) this.mAdapters.remove(segment);
        this.mAdapterTags.remove(segment);
        int uniqueAdapterIndex = this.mUniqueAdapters.indexOf(adapter);
        if (uniqueAdapterIndex < 0) {
            throw new IllegalStateException("Something wrong. Inconsistency detected.");
        }
        ComposedChildAdapterDataObserver observer = (ComposedChildAdapterDataObserver) this.mObservers.get(uniqueAdapterIndex);
        observer.unregisterChildAdapterTag(tag);
        if (observer.hasChildAdapters()) {
            return adapter;
        }
        adapter.unregisterAdapterDataObserver(observer);
        return adapter;
    }

    public int getAdapterSegment(ComposedChildAdapterTag tag) {
        return this.mAdapterTags.indexOf(tag);
    }

    public int getSegmentCount() {
        return this.mAdapters.size();
    }

    public Adapter getAdapter(int segment) {
        return (Adapter) this.mAdapters.get(segment);
    }

    public ComposedChildAdapterTag getTag(int segment) {
        return (ComposedChildAdapterTag) this.mAdapterTags.get(segment);
    }

    public static int extractSegment(long segmentedPosition) {
        return (int) (segmentedPosition >>> 32);
    }

    public static int extractSegmentOffset(long segmentedPosition) {
        return (int) (4294967295L & segmentedPosition);
    }

    public static long composeSegmentedPosition(int segment, int offset) {
        return (((long) segment) << 32) | (((long) offset) & 4294967295L);
    }

    public void release() {
        this.mAdapterTags.clear();
        this.mAdapters.clear();
        int numUniqueAdapters = this.mUniqueAdapters.size();
        for (int i = 0; i < numUniqueAdapters; i++) {
            ComposedChildAdapterDataObserver observer = (ComposedChildAdapterDataObserver) this.mObservers.get(i);
            ((Adapter) this.mUniqueAdapters.get(i)).unregisterAdapterDataObserver(observer);
            observer.release();
        }
        this.mUniqueAdapters.clear();
        this.mObservers.clear();
    }

    public List<Adapter> getUniqueAdaptersList() {
        return this.mUniqueAdapters;
    }
}
