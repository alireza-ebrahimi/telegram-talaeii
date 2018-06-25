package com.h6ah4i.android.widget.advrecyclerview.composedadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.adapter.AdapterPathSegment;
import com.h6ah4i.android.widget.advrecyclerview.adapter.BridgeAdapterDataObserver.Subscriber;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemIdComposer;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemViewTypeComposer;
import com.h6ah4i.android.widget.advrecyclerview.adapter.UnwrapPositionResult;
import com.h6ah4i.android.widget.advrecyclerview.adapter.WrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrappedAdapterUtils;
import java.util.List;

public class ComposedAdapter extends Adapter<ViewHolder> implements WrapperAdapter<ViewHolder>, Subscriber {
    public static long NO_SEGMENTED_POSITION = AdaptersSet.NO_SEGMENTED_POSITION;
    private AdaptersSet mAdaptersSet = new AdaptersSet(this);
    private SegmentedPositionTranslator mSegmentedPositionTranslator = new SegmentedPositionTranslator(this.mAdaptersSet);
    private SegmentedViewTypeTranslator mViewTypeTranslator = new SegmentedViewTypeTranslator();

    public ComposedAdapter() {
        setHasStableIds(true);
    }

    public void getWrappedAdapters(@NonNull List<Adapter> adapters) {
        if (this.mAdaptersSet != null) {
            adapters.addAll(this.mAdaptersSet.getUniqueAdaptersList());
        }
    }

    public void release() {
        onRelease();
    }

    @CallSuper
    protected void onRelease() {
        if (this.mAdaptersSet != null) {
            this.mAdaptersSet.release();
            this.mAdaptersSet = null;
        }
        if (this.mSegmentedPositionTranslator != null) {
            this.mSegmentedPositionTranslator.release();
            this.mSegmentedPositionTranslator = null;
        }
        this.mViewTypeTranslator = null;
    }

    public int getChildAdapterCount() {
        return this.mAdaptersSet.getSegmentCount();
    }

    public ComposedChildAdapterTag addAdapter(@NonNull Adapter adapter) {
        return addAdapter(adapter, getChildAdapterCount());
    }

    public ComposedChildAdapterTag addAdapter(@NonNull Adapter adapter, int position) {
        if (hasObservers() && hasStableIds() && !adapter.hasStableIds()) {
            throw new IllegalStateException("Wrapped child adapter must has stable IDs");
        }
        ComposedChildAdapterTag tag = this.mAdaptersSet.addAdapter(adapter, position);
        this.mSegmentedPositionTranslator.invalidateSegment(this.mAdaptersSet.getAdapterSegment(tag));
        notifyDataSetChanged();
        return tag;
    }

    public boolean removeAdapter(@NonNull ComposedChildAdapterTag tag) {
        int segment = this.mAdaptersSet.getAdapterSegment(tag);
        if (segment < 0) {
            return false;
        }
        this.mAdaptersSet.removeAdapter(tag);
        this.mSegmentedPositionTranslator.invalidateSegment(segment);
        notifyDataSetChanged();
        return true;
    }

    public int getSegment(@NonNull ComposedChildAdapterTag tag) {
        return this.mAdaptersSet.getAdapterSegment(tag);
    }

    public long getSegmentedPosition(int flatPosition) {
        return this.mSegmentedPositionTranslator.getSegmentedPosition(flatPosition);
    }

    public static int extractSegmentPart(long segmentedPosition) {
        return AdaptersSet.extractSegment(segmentedPosition);
    }

    public static int extractSegmentOffsetPart(long segmentedPosition) {
        return AdaptersSet.extractSegmentOffset(segmentedPosition);
    }

    public void setHasStableIds(boolean hasStableIds) {
        if (hasStableIds && !hasStableIds()) {
            int numSegments = this.mAdaptersSet.getSegmentCount();
            int i = 0;
            while (i < numSegments) {
                if (this.mAdaptersSet.getAdapter(i).hasStableIds()) {
                    i++;
                } else {
                    throw new IllegalStateException("All child adapters must support stable IDs");
                }
            }
        }
        super.setHasStableIds(hasStableIds);
    }

    public long getItemId(int position) {
        long segmentedPosition = getSegmentedPosition(position);
        int segment = AdaptersSet.extractSegment(segmentedPosition);
        int offset = AdaptersSet.extractSegmentOffset(segmentedPosition);
        Adapter adapter = this.mAdaptersSet.getAdapter(segment);
        int rawViewType = adapter.getItemViewType(offset);
        return ItemIdComposer.composeSegment(ItemViewTypeComposer.extractSegmentPart(this.mViewTypeTranslator.wrapItemViewType(segment, rawViewType)), adapter.getItemId(offset));
    }

    public int getItemViewType(int position) {
        long segmentedPosition = getSegmentedPosition(position);
        int segment = AdaptersSet.extractSegment(segmentedPosition);
        return this.mViewTypeTranslator.wrapItemViewType(segment, this.mAdaptersSet.getAdapter(segment).getItemViewType(AdaptersSet.extractSegmentOffset(segmentedPosition)));
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        long packedViewType = this.mViewTypeTranslator.unwrapViewType(viewType);
        int segment = SegmentedViewTypeTranslator.extractWrapperSegment(packedViewType);
        return this.mAdaptersSet.getAdapter(segment).onCreateViewHolder(parent, SegmentedViewTypeTranslator.extractWrappedViewType(packedViewType));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        long segmentedPosition = getSegmentedPosition(position);
        int segment = AdaptersSet.extractSegment(segmentedPosition);
        this.mAdaptersSet.getAdapter(segment).onBindViewHolder(holder, AdaptersSet.extractSegmentOffset(segmentedPosition));
    }

    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        long segmentedPosition = getSegmentedPosition(position);
        int segment = AdaptersSet.extractSegment(segmentedPosition);
        this.mAdaptersSet.getAdapter(segment).onBindViewHolder(holder, AdaptersSet.extractSegmentOffset(segmentedPosition), payloads);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        List<Adapter> adapters = this.mAdaptersSet.getUniqueAdaptersList();
        for (int i = 0; i < adapters.size(); i++) {
            ((Adapter) adapters.get(i)).onAttachedToRecyclerView(recyclerView);
        }
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        List<Adapter> adapters = this.mAdaptersSet.getUniqueAdaptersList();
        for (int i = 0; i < adapters.size(); i++) {
            ((Adapter) adapters.get(i)).onDetachedFromRecyclerView(recyclerView);
        }
    }

    public void onViewAttachedToWindow(ViewHolder holder) {
        onViewAttachedToWindow(holder, holder.getItemViewType());
    }

    public void onViewAttachedToWindow(ViewHolder holder, int viewType) {
        long packedViewType = this.mViewTypeTranslator.unwrapViewType(viewType);
        int segment = SegmentedViewTypeTranslator.extractWrapperSegment(packedViewType);
        WrappedAdapterUtils.invokeOnViewAttachedToWindow(this.mAdaptersSet.getAdapter(segment), holder, SegmentedViewTypeTranslator.extractWrappedViewType(packedViewType));
    }

    public void onViewDetachedFromWindow(ViewHolder holder) {
        onViewDetachedFromWindow(holder, holder.getItemViewType());
    }

    public void onViewDetachedFromWindow(ViewHolder holder, int viewType) {
        long packedViewType = this.mViewTypeTranslator.unwrapViewType(viewType);
        int segment = SegmentedViewTypeTranslator.extractWrapperSegment(packedViewType);
        WrappedAdapterUtils.invokeOnViewDetachedFromWindow(this.mAdaptersSet.getAdapter(segment), holder, SegmentedViewTypeTranslator.extractWrappedViewType(packedViewType));
    }

    public void onViewRecycled(ViewHolder holder) {
        onViewRecycled(holder, holder.getItemViewType());
    }

    public void onViewRecycled(ViewHolder holder, int viewType) {
        long packedViewType = this.mViewTypeTranslator.unwrapViewType(viewType);
        int segment = SegmentedViewTypeTranslator.extractWrapperSegment(packedViewType);
        WrappedAdapterUtils.invokeOnViewRecycled(this.mAdaptersSet.getAdapter(segment), holder, SegmentedViewTypeTranslator.extractWrappedViewType(packedViewType));
    }

    public boolean onFailedToRecycleView(ViewHolder holder) {
        return onFailedToRecycleView(holder, holder.getItemViewType());
    }

    public boolean onFailedToRecycleView(ViewHolder holder, int viewType) {
        long packedViewType = this.mViewTypeTranslator.unwrapViewType(viewType);
        int segment = SegmentedViewTypeTranslator.extractWrapperSegment(packedViewType);
        return WrappedAdapterUtils.invokeOnFailedToRecycleView(this.mAdaptersSet.getAdapter(segment), holder, SegmentedViewTypeTranslator.extractWrappedViewType(packedViewType));
    }

    public int getItemCount() {
        return this.mSegmentedPositionTranslator.getTotalItemCount();
    }

    public void unwrapPosition(@NonNull UnwrapPositionResult dest, int position) {
        long segmentedPosition = this.mSegmentedPositionTranslator.getSegmentedPosition(position);
        if (segmentedPosition != AdaptersSet.NO_SEGMENTED_POSITION) {
            int segment = AdaptersSet.extractSegment(segmentedPosition);
            int offset = AdaptersSet.extractSegmentOffset(segmentedPosition);
            dest.adapter = this.mAdaptersSet.getAdapter(segment);
            dest.position = offset;
            dest.tag = this.mAdaptersSet.getTag(segment);
        }
    }

    public int wrapPosition(@NonNull AdapterPathSegment pathSegment, int position) {
        if (pathSegment.tag == null) {
            return -1;
        }
        return this.mSegmentedPositionTranslator.getFlatPosition(this.mAdaptersSet.getAdapterSegment(pathSegment.tag), position);
    }

    public void onBridgedAdapterChanged(Adapter source, Object tag) {
        onHandleWrappedAdapterChanged(source, (List) tag);
    }

    public void onBridgedAdapterItemRangeChanged(Adapter source, Object tag, int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeChanged(source, (List) tag, positionStart, itemCount);
    }

    public void onBridgedAdapterItemRangeChanged(Adapter source, Object tag, int positionStart, int itemCount, Object payload) {
        onHandleWrappedAdapterItemRangeChanged(source, (List) tag, positionStart, itemCount, payload);
    }

    public void onBridgedAdapterItemRangeInserted(Adapter source, Object tag, int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeInserted(source, (List) tag, positionStart, itemCount);
    }

    public void onBridgedAdapterItemRangeRemoved(Adapter source, Object tag, int positionStart, int itemCount) {
        onHandleWrappedAdapterItemRangeRemoved(source, (List) tag, positionStart, itemCount);
    }

    public void onBridgedAdapterRangeMoved(Adapter source, Object tag, int fromPosition, int toPosition, int itemCount) {
        onHandleWrappedAdapterRangeMoved(source, (List) tag, fromPosition, toPosition, itemCount);
    }

    protected void onHandleWrappedAdapterChanged(Adapter sourceAdapter, List<ComposedChildAdapterTag> list) {
        this.mSegmentedPositionTranslator.invalidateAll();
        notifyDataSetChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(Adapter sourceAdapter, List<ComposedChildAdapterTag> sourceTags, int localPositionStart, int itemCount) {
        int nTags = sourceTags.size();
        for (int i = 0; i < nTags; i++) {
            notifyItemRangeChanged(this.mSegmentedPositionTranslator.getFlatPosition(this.mAdaptersSet.getAdapterSegment((ComposedChildAdapterTag) sourceTags.get(i)), localPositionStart), itemCount);
        }
    }

    protected void onHandleWrappedAdapterItemRangeChanged(Adapter sourceAdapter, List<ComposedChildAdapterTag> sourceTags, int localPositionStart, int itemCount, Object payload) {
        int nTags = sourceTags.size();
        for (int i = 0; i < nTags; i++) {
            notifyItemRangeChanged(this.mSegmentedPositionTranslator.getFlatPosition(this.mAdaptersSet.getAdapterSegment((ComposedChildAdapterTag) sourceTags.get(i)), localPositionStart), itemCount, payload);
        }
    }

    protected void onHandleWrappedAdapterItemRangeInserted(Adapter sourceAdapter, List<ComposedChildAdapterTag> sourceTags, int localPositionStart, int itemCount) {
        if (itemCount > 0) {
            int nTags = sourceTags.size();
            if (nTags == 1) {
                int adapterSegment = this.mAdaptersSet.getAdapterSegment((ComposedChildAdapterTag) sourceTags.get(0));
                this.mSegmentedPositionTranslator.invalidateSegment(adapterSegment);
                notifyItemRangeInserted(this.mSegmentedPositionTranslator.getFlatPosition(adapterSegment, localPositionStart), itemCount);
                return;
            }
            for (int i = 0; i < nTags; i++) {
                this.mSegmentedPositionTranslator.invalidateSegment(this.mAdaptersSet.getAdapterSegment((ComposedChildAdapterTag) sourceTags.get(i)));
            }
            notifyDataSetChanged();
        }
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(Adapter sourceAdapter, List<ComposedChildAdapterTag> sourceTags, int localPositionStart, int itemCount) {
        if (itemCount > 0) {
            int nTags = sourceTags.size();
            if (nTags == 1) {
                int adapterSegment = this.mAdaptersSet.getAdapterSegment((ComposedChildAdapterTag) sourceTags.get(0));
                this.mSegmentedPositionTranslator.invalidateSegment(adapterSegment);
                notifyItemRangeRemoved(this.mSegmentedPositionTranslator.getFlatPosition(adapterSegment, localPositionStart), itemCount);
                return;
            }
            for (int i = 0; i < nTags; i++) {
                this.mSegmentedPositionTranslator.invalidateSegment(this.mAdaptersSet.getAdapterSegment((ComposedChildAdapterTag) sourceTags.get(i)));
            }
            notifyDataSetChanged();
        }
    }

    protected void onHandleWrappedAdapterRangeMoved(Adapter sourceAdapter, List<ComposedChildAdapterTag> sourceTags, int localFromPosition, int localToPosition, int itemCount) {
        if (itemCount != 1) {
            throw new IllegalStateException("itemCount should be always 1  (actual: " + itemCount + ")");
        } else if (sourceTags.size() == 1) {
            int adapterSegment = this.mAdaptersSet.getAdapterSegment((ComposedChildAdapterTag) sourceTags.get(0));
            notifyItemMoved(this.mSegmentedPositionTranslator.getFlatPosition(adapterSegment, localFromPosition), this.mSegmentedPositionTranslator.getFlatPosition(adapterSegment, localToPosition));
        } else {
            notifyDataSetChanged();
        }
    }
}
