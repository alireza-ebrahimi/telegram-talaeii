package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.adapter.BridgeAdapterDataObserver.Subscriber;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrappedAdapterUtils;
import java.util.Collections;
import java.util.List;

public class SimpleWrapperAdapter<VH extends ViewHolder> extends Adapter<VH> implements WrapperAdapter<VH>, Subscriber {
    protected static final List<Object> FULL_UPDATE_PAYLOADS = Collections.emptyList();
    private static final boolean LOCAL_LOGD = false;
    private static final String TAG = "ARVSimpleWAdapter";
    private BridgeAdapterDataObserver mBridgeObserver = new BridgeAdapterDataObserver(this, this.mWrappedAdapter, null);
    private Adapter<VH> mWrappedAdapter;

    public SimpleWrapperAdapter(@NonNull Adapter<VH> adapter) {
        this.mWrappedAdapter = adapter;
        this.mWrappedAdapter.registerAdapterDataObserver(this.mBridgeObserver);
        super.setHasStableIds(this.mWrappedAdapter.hasStableIds());
    }

    public boolean isWrappedAdapterAlive() {
        return this.mWrappedAdapter != null;
    }

    public Adapter<VH> getWrappedAdapter() {
        return this.mWrappedAdapter;
    }

    public void getWrappedAdapters(@NonNull List<Adapter> adapters) {
        if (this.mWrappedAdapter != null) {
            adapters.add(this.mWrappedAdapter);
        }
    }

    public void release() {
        onRelease();
        if (!(this.mWrappedAdapter == null || this.mBridgeObserver == null)) {
            this.mWrappedAdapter.unregisterAdapterDataObserver(this.mBridgeObserver);
        }
        this.mWrappedAdapter = null;
        this.mBridgeObserver = null;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    public void onViewAttachedToWindow(VH holder) {
        onViewAttachedToWindow(holder, holder.getItemViewType());
    }

    public void onViewAttachedToWindow(VH holder, int viewType) {
        if (isWrappedAdapterAlive()) {
            WrappedAdapterUtils.invokeOnViewAttachedToWindow(this.mWrappedAdapter, holder, viewType);
        }
    }

    public void onViewDetachedFromWindow(VH holder) {
        onViewDetachedFromWindow(holder, holder.getItemViewType());
    }

    public void onViewDetachedFromWindow(VH holder, int viewType) {
        if (isWrappedAdapterAlive()) {
            WrappedAdapterUtils.invokeOnViewDetachedFromWindow(this.mWrappedAdapter, holder, viewType);
        }
    }

    public void onViewRecycled(VH holder) {
        onViewRecycled(holder, holder.getItemViewType());
    }

    public void onViewRecycled(VH holder, int viewType) {
        if (isWrappedAdapterAlive()) {
            WrappedAdapterUtils.invokeOnViewRecycled(this.mWrappedAdapter, holder, viewType);
        }
    }

    public boolean onFailedToRecycleView(VH holder) {
        return onFailedToRecycleView(holder, holder.getItemViewType());
    }

    public boolean onFailedToRecycleView(VH holder, int viewType) {
        boolean shouldBeRecycled = false;
        if (isWrappedAdapterAlive()) {
            shouldBeRecycled = WrappedAdapterUtils.invokeOnFailedToRecycleView(this.mWrappedAdapter, holder, viewType);
        }
        if (shouldBeRecycled) {
            return true;
        }
        return super.onFailedToRecycleView(holder);
    }

    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.setHasStableIds(hasStableIds);
        }
    }

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.mWrappedAdapter.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position, FULL_UPDATE_PAYLOADS);
    }

    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    public int getItemCount() {
        return isWrappedAdapterAlive() ? this.mWrappedAdapter.getItemCount() : 0;
    }

    public long getItemId(int position) {
        return this.mWrappedAdapter.getItemId(position);
    }

    public int getItemViewType(int position) {
        return this.mWrappedAdapter.getItemViewType(position);
    }

    public void unwrapPosition(@NonNull UnwrapPositionResult dest, int position) {
        dest.adapter = getWrappedAdapter();
        dest.position = position;
    }

    public int wrapPosition(@NonNull AdapterPathSegment pathSegment, int position) {
        return pathSegment.adapter == getWrappedAdapter() ? position : -1;
    }

    @CallSuper
    protected void onRelease() {
    }

    protected void onHandleWrappedAdapterChanged() {
        notifyDataSetChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount, Object payload) {
        notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (itemCount != 1) {
            throw new IllegalStateException("itemCount should be always 1  (actual: " + itemCount + ")");
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public final void onBridgedAdapterChanged(Adapter source, Object tag) {
        onHandleWrappedAdapterChanged();
    }

    public final void onBridgedAdapterItemRangeChanged(Adapter source, Object tag, int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
    }

    public final void onBridgedAdapterItemRangeChanged(Adapter sourceAdapter, Object tag, int positionStart, int itemCount, Object payload) {
        onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount, payload);
    }

    public final void onBridgedAdapterItemRangeInserted(Adapter sourceAdapter, Object tag, int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
    }

    public final void onBridgedAdapterItemRangeRemoved(Adapter sourceAdapter, Object tag, int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
    }

    public final void onBridgedAdapterRangeMoved(Adapter sourceAdapter, Object tag, int fromPosition, int toPosition, int itemCount) {
        onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
    }
}
