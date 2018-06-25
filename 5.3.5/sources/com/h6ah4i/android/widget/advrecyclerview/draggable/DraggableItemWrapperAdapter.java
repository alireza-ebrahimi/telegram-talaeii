package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.adapter.SimpleWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.util.List;

class DraggableItemWrapperAdapter<VH extends ViewHolder> extends SimpleWrapperAdapter<VH> implements SwipeableItemAdapter<VH> {
    private static final boolean DEBUG_BYPASS_MOVE_OPERATION_MODE = false;
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGI = true;
    private static final boolean LOCAL_LOGV = false;
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVDraggableWrapper";
    private RecyclerViewDragDropManager mDragDropManager;
    private DraggableItemAdapter mDraggableItemAdapter;
    private ItemDraggableRange mDraggableRange;
    private int mDraggingItemCurrentPosition = -1;
    private DraggingItemInfo mDraggingItemInfo;
    private int mDraggingItemInitialPosition = -1;
    private ViewHolder mDraggingItemViewHolder;
    private int mItemMoveMode;

    private interface Constants extends DraggableItemConstants {
    }

    public DraggableItemWrapperAdapter(RecyclerViewDragDropManager manager, Adapter<VH> adapter) {
        super(adapter);
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }
        this.mDragDropManager = manager;
    }

    protected void onRelease() {
        super.onRelease();
        this.mDraggingItemViewHolder = null;
        this.mDraggableItemAdapter = null;
        this.mDragDropManager = null;
    }

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH holder = super.onCreateViewHolder(parent, viewType);
        if (holder instanceof DraggableItemViewHolder) {
            ((DraggableItemViewHolder) holder).setDragStateFlags(-1);
        }
        return holder;
    }

    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        if (isDragging()) {
            long draggingItemId = this.mDraggingItemInfo.id;
            long itemId = holder.getItemId();
            int origPosition = convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode);
            if (itemId == draggingItemId && holder != this.mDraggingItemViewHolder) {
                Log.i(TAG, "a new view holder object for the currently dragging item is assigned");
                this.mDraggingItemViewHolder = holder;
                this.mDragDropManager.onNewDraggingItemViewBound(holder);
            }
            int flags = 1;
            if (itemId == draggingItemId) {
                flags = 1 | 2;
            }
            if (this.mDraggableRange.checkInRange(position)) {
                flags |= 4;
            }
            safeUpdateFlags(holder, flags);
            super.onBindViewHolder(holder, origPosition, payloads);
            return;
        }
        safeUpdateFlags(holder, 0);
        super.onBindViewHolder(holder, position, payloads);
    }

    public long getItemId(int position) {
        if (isDragging()) {
            return super.getItemId(convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode));
        }
        return super.getItemId(position);
    }

    public int getItemViewType(int position) {
        if (isDragging()) {
            return super.getItemViewType(convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode));
        }
        return super.getItemViewType(position);
    }

    protected static int convertToOriginalPosition(int position, int dragInitial, int dragCurrent, int itemMoveMode) {
        if (dragInitial < 0 || dragCurrent < 0) {
            return position;
        }
        if (itemMoveMode == 0) {
            if (dragInitial == dragCurrent || ((position < dragInitial && position < dragCurrent) || (position > dragInitial && position > dragCurrent))) {
                return position;
            }
            if (dragCurrent < dragInitial) {
                if (position != dragCurrent) {
                    return position - 1;
                }
                return dragInitial;
            } else if (position != dragCurrent) {
                return position + 1;
            } else {
                return dragInitial;
            }
        } else if (itemMoveMode != 1) {
            throw new IllegalStateException("unexpected state");
        } else if (position == dragCurrent) {
            return dragInitial;
        } else {
            if (position == dragInitial) {
                return dragCurrent;
            }
            return position;
        }
    }

    protected void onHandleWrappedAdapterChanged() {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterChanged();
        }
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
        }
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
        }
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
        }
    }

    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
        }
    }

    private boolean shouldCancelDragOnDataUpdated() {
        return isDragging();
    }

    private void cancelDrag() {
        if (this.mDragDropManager != null) {
            this.mDragDropManager.cancelDrag();
        }
    }

    void onDragItemStarted(DraggingItemInfo draggingItemInfo, ViewHolder holder, ItemDraggableRange range, int wrappedItemPosition, int itemMoveMode) {
        if (holder.getItemId() == -1) {
            throw new IllegalStateException("dragging target must provides valid ID");
        }
        this.mDraggableItemAdapter = (DraggableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(this, DraggableItemAdapter.class, wrappedItemPosition);
        if (this.mDraggableItemAdapter == null) {
            throw new IllegalStateException("DraggableItemAdapter not found!");
        }
        this.mDraggingItemCurrentPosition = wrappedItemPosition;
        this.mDraggingItemInitialPosition = wrappedItemPosition;
        this.mDraggingItemInfo = draggingItemInfo;
        this.mDraggingItemViewHolder = holder;
        this.mDraggableRange = range;
        this.mItemMoveMode = itemMoveMode;
        notifyDataSetChanged();
    }

    void onDragItemFinished(boolean result) {
        if (result && this.mDraggingItemCurrentPosition != this.mDraggingItemInitialPosition) {
            this.mDraggableItemAdapter.onMoveItem(this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
        }
        this.mDraggingItemInitialPosition = -1;
        this.mDraggingItemCurrentPosition = -1;
        this.mDraggableRange = null;
        this.mDraggingItemInfo = null;
        this.mDraggingItemViewHolder = null;
        this.mDraggableItemAdapter = null;
        notifyDataSetChanged();
    }

    public void onViewRecycled(VH holder, int viewType) {
        if (isDragging()) {
            this.mDragDropManager.onItemViewRecycled(holder);
            this.mDraggingItemViewHolder = this.mDragDropManager.getDraggingItemViewHolder();
        }
        super.onViewRecycled(holder, viewType);
    }

    boolean canStartDrag(ViewHolder holder, int position, int x, int y) {
        DraggableItemAdapter draggableItemAdapter = (DraggableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(this, DraggableItemAdapter.class, position);
        if (draggableItemAdapter == null) {
            return false;
        }
        return draggableItemAdapter.onCheckCanStartDrag(holder, position, x, y);
    }

    boolean canDropItems(int draggingPosition, int dropPosition) {
        return this.mDraggableItemAdapter.onCheckCanDrop(draggingPosition, dropPosition);
    }

    ItemDraggableRange getItemDraggableRange(ViewHolder holder, int position) {
        DraggableItemAdapter draggableItemAdapter = (DraggableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(this, DraggableItemAdapter.class, position);
        if (draggableItemAdapter == null) {
            return null;
        }
        return draggableItemAdapter.onGetItemDraggableRange(holder, position);
    }

    void moveItem(int fromPosition, int toPosition, int layoutType) {
        int origFromPosition = convertToOriginalPosition(fromPosition, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode);
        if (origFromPosition != this.mDraggingItemInitialPosition) {
            throw new IllegalStateException("onMoveItem() - may be a bug or has duplicate IDs  --- mDraggingItemInitialPosition = " + this.mDraggingItemInitialPosition + ", mDraggingItemCurrentPosition = " + this.mDraggingItemCurrentPosition + ", origFromPosition = " + origFromPosition + ", fromPosition = " + fromPosition + ", toPosition = " + toPosition);
        }
        this.mDraggingItemCurrentPosition = toPosition;
        if (this.mItemMoveMode == 0 && CustomRecyclerViewUtils.isLinearLayout(layoutType)) {
            notifyItemMoved(fromPosition, toPosition);
        } else {
            notifyDataSetChanged();
        }
    }

    protected boolean isDragging() {
        return this.mDraggingItemInfo != null;
    }

    int getDraggingItemInitialPosition() {
        return this.mDraggingItemInitialPosition;
    }

    int getDraggingItemCurrentPosition() {
        return this.mDraggingItemCurrentPosition;
    }

    private static void safeUpdateFlags(ViewHolder holder, int flags) {
        if (holder instanceof DraggableItemViewHolder) {
            int curFlags = ((DraggableItemViewHolder) holder).getDragStateFlags();
            if (curFlags == -1 || ((curFlags ^ flags) & Integer.MAX_VALUE) != 0) {
                flags |= Integer.MIN_VALUE;
            }
            ((DraggableItemViewHolder) holder).setDragStateFlags(flags);
        }
    }

    private int getOriginalPosition(int position) {
        if (isDragging()) {
            return convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode);
        }
        return position;
    }

    public int onGetSwipeReactionType(VH holder, int position, int x, int y) {
        Adapter adapter = getWrappedAdapter();
        if (!(adapter instanceof SwipeableItemAdapter)) {
            return 0;
        }
        return ((SwipeableItemAdapter) adapter).onGetSwipeReactionType(holder, getOriginalPosition(position), x, y);
    }

    public void onSetSwipeBackground(VH holder, int position, int type) {
        Adapter adapter = getWrappedAdapter();
        if (adapter instanceof SwipeableItemAdapter) {
            ((SwipeableItemAdapter) adapter).onSetSwipeBackground(holder, getOriginalPosition(position), type);
        }
    }

    public SwipeResultAction onSwipeItem(VH holder, int position, int result) {
        Adapter adapter = getWrappedAdapter();
        if (!(adapter instanceof SwipeableItemAdapter)) {
            return new SwipeResultActionDefault();
        }
        return ((SwipeableItemAdapter) adapter).onSwipeItem(holder, getOriginalPosition(position), result);
    }
}
