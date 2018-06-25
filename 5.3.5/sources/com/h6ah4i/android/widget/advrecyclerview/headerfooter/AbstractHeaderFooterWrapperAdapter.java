package com.h6ah4i.android.widget.advrecyclerview.headerfooter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.adapter.AdapterPathSegment;
import com.h6ah4i.android.widget.advrecyclerview.composedadapter.ComposedAdapter;
import com.h6ah4i.android.widget.advrecyclerview.composedadapter.ComposedChildAdapterTag;
import java.util.List;

public abstract class AbstractHeaderFooterWrapperAdapter<HeaderVH extends ViewHolder, FooterVH extends ViewHolder> extends ComposedAdapter {
    public static final int SEGMENT_TYPE_FOOTER = 2;
    public static final int SEGMENT_TYPE_HEADER = 0;
    public static final int SEGMENT_TYPE_NORMAL = 1;
    private Adapter mFooterAdapter;
    private ComposedChildAdapterTag mFooterAdapterTag;
    private Adapter mHeaderAdapter;
    private ComposedChildAdapterTag mHeaderAdapterTag;
    private Adapter mWrappedAdapter;
    private ComposedChildAdapterTag mWrappedAdapterTag;

    public static class BaseFooterAdapter extends Adapter<ViewHolder> {
        protected AbstractHeaderFooterWrapperAdapter mHolder;

        public BaseFooterAdapter(AbstractHeaderFooterWrapperAdapter holder) {
            this.mHolder = holder;
        }

        public int getItemCount() {
            return this.mHolder.getFooterItemCount();
        }

        public long getItemId(int position) {
            return this.mHolder.getFooterItemId(position);
        }

        public int getItemViewType(int position) {
            return this.mHolder.getFooterItemViewType(position);
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return this.mHolder.onCreateFooterItemViewHolder(parent, viewType);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            throw new IllegalStateException();
        }

        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
            this.mHolder.onBindFooterItemViewHolder(holder, position, payloads);
        }
    }

    public static class BaseHeaderAdapter extends Adapter<ViewHolder> {
        protected AbstractHeaderFooterWrapperAdapter mHolder;

        public BaseHeaderAdapter(AbstractHeaderFooterWrapperAdapter holder) {
            this.mHolder = holder;
        }

        public int getItemCount() {
            return this.mHolder.getHeaderItemCount();
        }

        public long getItemId(int position) {
            return this.mHolder.getHeaderItemId(position);
        }

        public int getItemViewType(int position) {
            return this.mHolder.getHeaderItemViewType(position);
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return this.mHolder.onCreateHeaderItemViewHolder(parent, viewType);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            throw new IllegalStateException();
        }

        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
            this.mHolder.onBindHeaderItemViewHolder(holder, position, payloads);
        }
    }

    public abstract int getFooterItemCount();

    public abstract int getHeaderItemCount();

    public abstract void onBindFooterItemViewHolder(FooterVH footerVH, int i);

    public abstract void onBindHeaderItemViewHolder(HeaderVH headerVH, int i);

    public abstract FooterVH onCreateFooterItemViewHolder(ViewGroup viewGroup, int i);

    public abstract HeaderVH onCreateHeaderItemViewHolder(ViewGroup viewGroup, int i);

    public AbstractHeaderFooterWrapperAdapter setAdapter(@NonNull Adapter<? extends ViewHolder> adapter) {
        if (this.mWrappedAdapter != null) {
            throw new IllegalStateException("setAdapter() can call only once");
        }
        this.mWrappedAdapter = adapter;
        this.mHeaderAdapter = onCreateHeaderAdapter();
        this.mFooterAdapter = onCreateFooterAdapter();
        boolean hasStableIds = adapter.hasStableIds();
        this.mHeaderAdapter.setHasStableIds(hasStableIds);
        this.mFooterAdapter.setHasStableIds(hasStableIds);
        setHasStableIds(hasStableIds);
        this.mHeaderAdapterTag = addAdapter(this.mHeaderAdapter);
        this.mWrappedAdapterTag = addAdapter(this.mWrappedAdapter);
        this.mFooterAdapterTag = addAdapter(this.mFooterAdapter);
        return this;
    }

    protected void onRelease() {
        super.onRelease();
        this.mHeaderAdapterTag = null;
        this.mWrappedAdapterTag = null;
        this.mFooterAdapterTag = null;
        this.mHeaderAdapter = null;
        this.mWrappedAdapter = null;
        this.mFooterAdapter = null;
    }

    @NonNull
    protected Adapter onCreateHeaderAdapter() {
        return new BaseHeaderAdapter(this);
    }

    @NonNull
    protected Adapter onCreateFooterAdapter() {
        return new BaseFooterAdapter(this);
    }

    public Adapter getHeaderAdapter() {
        return this.mHeaderAdapter;
    }

    public Adapter getFooterAdapter() {
        return this.mFooterAdapter;
    }

    public Adapter getWrappedAdapter() {
        return this.mWrappedAdapter;
    }

    public AdapterPathSegment getWrappedAdapterSegment() {
        return new AdapterPathSegment(this.mWrappedAdapter, this.mWrappedAdapterTag);
    }

    public AdapterPathSegment getHeaderSegment() {
        return new AdapterPathSegment(this.mHeaderAdapter, this.mHeaderAdapterTag);
    }

    public AdapterPathSegment getFooterSegment() {
        return new AdapterPathSegment(this.mFooterAdapter, this.mFooterAdapterTag);
    }

    public void onBindHeaderItemViewHolder(HeaderVH holder, int localPosition, List<Object> list) {
        onBindHeaderItemViewHolder(holder, localPosition);
    }

    public void onBindFooterItemViewHolder(FooterVH holder, int localPosition, List<Object> list) {
        onBindFooterItemViewHolder(holder, localPosition);
    }

    @IntRange(from = -36028797018963968L, to = 36028797018963967L)
    public long getHeaderItemId(int localPosition) {
        if (hasStableIds()) {
            return -1;
        }
        return (long) localPosition;
    }

    @IntRange(from = -36028797018963968L, to = 36028797018963967L)
    public long getFooterItemId(int localPosition) {
        if (hasStableIds()) {
            return -1;
        }
        return (long) localPosition;
    }

    @IntRange(from = -8388608, to = 8388607)
    public int getHeaderItemViewType(int localPosition) {
        return 0;
    }

    @IntRange(from = -8388608, to = 8388607)
    public int getFooterItemViewType(int localPosition) {
        return 0;
    }
}
