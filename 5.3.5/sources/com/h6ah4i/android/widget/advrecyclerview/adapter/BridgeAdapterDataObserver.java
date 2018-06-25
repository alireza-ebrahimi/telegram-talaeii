package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import java.lang.ref.WeakReference;

public class BridgeAdapterDataObserver extends AdapterDataObserver {
    private final WeakReference<Adapter> mRefSourceHolder;
    private final WeakReference<Subscriber> mRefSubscriber;
    private final Object mTag;

    public interface Subscriber {
        void onBridgedAdapterChanged(Adapter adapter, Object obj);

        void onBridgedAdapterItemRangeChanged(Adapter adapter, Object obj, int i, int i2);

        void onBridgedAdapterItemRangeChanged(Adapter adapter, Object obj, int i, int i2, Object obj2);

        void onBridgedAdapterItemRangeInserted(Adapter adapter, Object obj, int i, int i2);

        void onBridgedAdapterItemRangeRemoved(Adapter adapter, Object obj, int i, int i2);

        void onBridgedAdapterRangeMoved(Adapter adapter, Object obj, int i, int i2, int i3);
    }

    public BridgeAdapterDataObserver(@NonNull Subscriber subscriber, @NonNull Adapter sourceAdapter, @Nullable Object tag) {
        this.mRefSubscriber = new WeakReference(subscriber);
        this.mRefSourceHolder = new WeakReference(sourceAdapter);
        this.mTag = tag;
    }

    public Object getTag() {
        return this.mTag;
    }

    public void onChanged() {
        Subscriber subscriber = (Subscriber) this.mRefSubscriber.get();
        Adapter source = (Adapter) this.mRefSourceHolder.get();
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterChanged(source, this.mTag);
        }
    }

    public void onItemRangeChanged(int positionStart, int itemCount) {
        Subscriber subscriber = (Subscriber) this.mRefSubscriber.get();
        Adapter source = (Adapter) this.mRefSourceHolder.get();
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeChanged(source, this.mTag, positionStart, itemCount);
        }
    }

    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        Subscriber subscriber = (Subscriber) this.mRefSubscriber.get();
        Adapter source = (Adapter) this.mRefSourceHolder.get();
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeChanged(source, this.mTag, positionStart, itemCount, payload);
        }
    }

    public void onItemRangeInserted(int positionStart, int itemCount) {
        Subscriber subscriber = (Subscriber) this.mRefSubscriber.get();
        Adapter source = (Adapter) this.mRefSourceHolder.get();
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeInserted(source, this.mTag, positionStart, itemCount);
        }
    }

    public void onItemRangeRemoved(int positionStart, int itemCount) {
        Subscriber subscriber = (Subscriber) this.mRefSubscriber.get();
        Adapter source = (Adapter) this.mRefSourceHolder.get();
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterItemRangeRemoved(source, this.mTag, positionStart, itemCount);
        }
    }

    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        Subscriber subscriber = (Subscriber) this.mRefSubscriber.get();
        Adapter source = (Adapter) this.mRefSourceHolder.get();
        if (subscriber != null && source != null) {
            subscriber.onBridgedAdapterRangeMoved(source, this.mTag, fromPosition, toPosition, itemCount);
        }
    }
}
