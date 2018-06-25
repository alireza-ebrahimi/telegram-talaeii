package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemIdComposer;
import com.h6ah4i.android.widget.advrecyclerview.adapter.SimpleWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.OnGroupCollapseListener;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.OnGroupExpandListener;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.util.List;

class ExpandableRecyclerViewWrapperAdapter extends SimpleWrapperAdapter<ViewHolder> implements DraggableItemAdapter<ViewHolder>, SwipeableItemAdapter<ViewHolder> {
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVExpandableWrapper";
    private static final int VIEW_TYPE_FLAG_IS_GROUP = Integer.MIN_VALUE;
    private int mDraggingItemChildRangeEnd = -1;
    private int mDraggingItemChildRangeStart = -1;
    private int mDraggingItemGroupRangeEnd = -1;
    private int mDraggingItemGroupRangeStart = -1;
    private ExpandableItemAdapter mExpandableItemAdapter;
    private RecyclerViewExpandableItemManager mExpandableListManager;
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;
    private ExpandablePositionTranslator mPositionTranslator;

    private interface Constants extends ExpandableItemConstants {
    }

    public ExpandableRecyclerViewWrapperAdapter(RecyclerViewExpandableItemManager manager, Adapter<ViewHolder> adapter, long[] expandedItemsSavedState) {
        super(adapter);
        this.mExpandableItemAdapter = getExpandableItemAdapter(adapter);
        if (this.mExpandableItemAdapter == null) {
            throw new IllegalArgumentException("adapter does not implement ExpandableItemAdapter");
        } else if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        } else {
            this.mExpandableListManager = manager;
            this.mPositionTranslator = new ExpandablePositionTranslator();
            this.mPositionTranslator.build(this.mExpandableItemAdapter, 0, this.mExpandableListManager.getDefaultGroupsExpandedState());
            if (expandedItemsSavedState != null) {
                this.mPositionTranslator.restoreExpandedGroupItems(expandedItemsSavedState, null, null, null);
            }
        }
    }

    protected void onRelease() {
        super.onRelease();
        this.mExpandableItemAdapter = null;
        this.mExpandableListManager = null;
        this.mOnGroupExpandListener = null;
        this.mOnGroupCollapseListener = null;
    }

    public int getItemCount() {
        return this.mPositionTranslator.getItemCount();
    }

    public long getItemId(int position) {
        if (this.mExpandableItemAdapter == null) {
            return -1;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            return ItemIdComposer.composeExpandableGroupId(this.mExpandableItemAdapter.getGroupId(groupPosition));
        }
        return ItemIdComposer.composeExpandableChildId(this.mExpandableItemAdapter.getGroupId(groupPosition), this.mExpandableItemAdapter.getChildId(groupPosition, childPosition));
    }

    public int getItemViewType(int position) {
        if (this.mExpandableItemAdapter == null) {
            return 0;
        }
        int type;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            type = this.mExpandableItemAdapter.getGroupItemViewType(groupPosition);
        } else {
            type = this.mExpandableItemAdapter.getChildItemViewType(groupPosition, childPosition);
        }
        if ((type & Integer.MIN_VALUE) == 0) {
            return childPosition == -1 ? type | Integer.MIN_VALUE : type;
        } else {
            throw new IllegalStateException("Illegal view type (type = " + Integer.toHexString(type) + ")");
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.mExpandableItemAdapter == null) {
            return null;
        }
        ViewHolder holder;
        int maskedViewType = viewType & Integer.MAX_VALUE;
        if ((Integer.MIN_VALUE & viewType) != 0) {
            holder = this.mExpandableItemAdapter.onCreateGroupViewHolder(parent, maskedViewType);
        } else {
            holder = this.mExpandableItemAdapter.onCreateChildViewHolder(parent, maskedViewType);
        }
        if (!(holder instanceof ExpandableItemViewHolder)) {
            return holder;
        }
        ((ExpandableItemViewHolder) holder).setExpandStateFlags(-1);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (this.mExpandableItemAdapter != null) {
            int flags;
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
            int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            int viewType = holder.getItemViewType() & Integer.MAX_VALUE;
            if (childPosition == -1) {
                flags = 0 | 1;
            } else {
                flags = 0 | 2;
            }
            if (this.mPositionTranslator.isGroupExpanded(groupPosition)) {
                flags |= 4;
            }
            safeUpdateExpandStateFlags(holder, flags);
            correctItemDragStateFlags(holder, groupPosition, childPosition);
            if (childPosition == -1) {
                this.mExpandableItemAdapter.onBindGroupViewHolder(holder, groupPosition, viewType, payloads);
            } else {
                this.mExpandableItemAdapter.onBindChildViewHolder(holder, groupPosition, childPosition, viewType, payloads);
            }
        }
    }

    private void rebuildPositionTranslator() {
        if (this.mPositionTranslator != null) {
            long[] savedState = this.mPositionTranslator.getSavedStateArray();
            this.mPositionTranslator.build(this.mExpandableItemAdapter, 0, this.mExpandableListManager.getDefaultGroupsExpandedState());
            this.mPositionTranslator.restoreExpandedGroupItems(savedState, null, null, null);
        }
    }

    public void onViewRecycled(ViewHolder holder, int viewType) {
        if (holder instanceof ExpandableItemViewHolder) {
            ((ExpandableItemViewHolder) holder).setExpandStateFlags(-1);
        }
        super.onViewRecycled(holder, viewType);
    }

    protected void onHandleWrappedAdapterChanged() {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        super.onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        if (itemCount == 1) {
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(positionStart);
            int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            if (childPosition == -1) {
                this.mPositionTranslator.removeGroupItem(groupPosition);
            } else {
                this.mPositionTranslator.removeChildItem(groupPosition, childPosition);
            }
        } else {
            rebuildPositionTranslator();
        }
        super.onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
    }

    public boolean onCheckCanStartDrag(ViewHolder holder, int position, int x, int y) {
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter)) {
            return false;
        }
        boolean canStart;
        ExpandableDraggableItemAdapter adapter = this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            canStart = adapter.onCheckGroupCanStartDrag(holder, groupPosition, x, y);
        } else {
            canStart = adapter.onCheckChildCanStartDrag(holder, groupPosition, childPosition, x, y);
        }
        this.mDraggingItemGroupRangeStart = -1;
        this.mDraggingItemGroupRangeEnd = -1;
        this.mDraggingItemChildRangeStart = -1;
        this.mDraggingItemChildRangeEnd = -1;
        return canStart;
    }

    public ItemDraggableRange onGetItemDraggableRange(ViewHolder holder, int position) {
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter)) {
            return null;
        }
        if (this.mExpandableItemAdapter.getGroupCount() < 1) {
            return null;
        }
        ExpandableDraggableItemAdapter adapter = this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        ItemDraggableRange range;
        int end;
        if (childPosition == -1) {
            range = adapter.onGetGroupItemDraggableRange(holder, groupPosition);
            if (range == null) {
                return new ItemDraggableRange(0, Math.max(0, (this.mPositionTranslator.getItemCount() - this.mPositionTranslator.getVisibleChildCount(Math.max(0, this.mExpandableItemAdapter.getGroupCount() - 1))) - 1));
            } else if (isGroupPositionRange(range)) {
                long startPackedGroupPosition = ExpandableAdapterHelper.getPackedPositionForGroup(range.getStart());
                long endPackedGroupPosition = ExpandableAdapterHelper.getPackedPositionForGroup(range.getEnd());
                int start = this.mPositionTranslator.getFlatPosition(startPackedGroupPosition);
                end = this.mPositionTranslator.getFlatPosition(endPackedGroupPosition);
                if (range.getEnd() > groupPosition) {
                    end += this.mPositionTranslator.getVisibleChildCount(range.getEnd());
                }
                this.mDraggingItemGroupRangeStart = range.getStart();
                this.mDraggingItemGroupRangeEnd = range.getEnd();
                return new ItemDraggableRange(start, end);
            } else {
                throw new IllegalStateException("Invalid range specified: " + range);
            }
        }
        range = adapter.onGetChildItemDraggableRange(holder, groupPosition, childPosition);
        if (range == null) {
            return new ItemDraggableRange(1, Math.max(1, this.mPositionTranslator.getItemCount() - 1));
        }
        if (isGroupPositionRange(range)) {
            startPackedGroupPosition = ExpandableAdapterHelper.getPackedPositionForGroup(range.getStart());
            end = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(range.getEnd())) + this.mPositionTranslator.getVisibleChildCount(range.getEnd());
            start = Math.min(this.mPositionTranslator.getFlatPosition(startPackedGroupPosition) + 1, end);
            this.mDraggingItemGroupRangeStart = range.getStart();
            this.mDraggingItemGroupRangeEnd = range.getEnd();
            return new ItemDraggableRange(start, end);
        } else if (isChildPositionRange(range)) {
            int maxChildrenPos = Math.max(this.mPositionTranslator.getVisibleChildCount(groupPosition) - 1, 0);
            int childStart = Math.min(range.getStart(), maxChildrenPos);
            int childEnd = Math.min(range.getEnd(), maxChildrenPos);
            long startPackedChildPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childStart);
            long endPackedChildPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childEnd);
            start = this.mPositionTranslator.getFlatPosition(startPackedChildPosition);
            end = this.mPositionTranslator.getFlatPosition(endPackedChildPosition);
            this.mDraggingItemChildRangeStart = childStart;
            this.mDraggingItemChildRangeEnd = childEnd;
            return new ItemDraggableRange(start, end);
        } else {
            throw new IllegalStateException("Invalid range specified: " + range);
        }
    }

    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter)) {
            return true;
        }
        if (this.mExpandableItemAdapter.getGroupCount() < 1) {
            return false;
        }
        ExpandableDraggableItemAdapter adapter = this.mExpandableItemAdapter;
        int draggingFlatPosition = draggingPosition;
        long draggingExpandablePosition = this.mPositionTranslator.getExpandablePosition(draggingFlatPosition);
        int draggingGroupPosition = ExpandableAdapterHelper.getPackedPositionGroup(draggingExpandablePosition);
        int draggingChildPosition = ExpandableAdapterHelper.getPackedPositionChild(draggingExpandablePosition);
        int dropFlatPosition = dropPosition;
        long dropExpandablePosition = this.mPositionTranslator.getExpandablePosition(dropFlatPosition);
        int dropGroupPosition = ExpandableAdapterHelper.getPackedPositionGroup(dropExpandablePosition);
        int dropChildPosition = ExpandableAdapterHelper.getPackedPositionChild(dropExpandablePosition);
        boolean draggingIsGroup = draggingChildPosition == -1;
        boolean dropIsGroup = dropChildPosition == -1;
        boolean canDrop;
        boolean isDropGroupExpanded;
        if (draggingIsGroup) {
            if (draggingGroupPosition == dropGroupPosition) {
                canDrop = dropIsGroup;
            } else if (draggingFlatPosition < dropFlatPosition) {
                isDropGroupExpanded = this.mPositionTranslator.isGroupExpanded(dropGroupPosition);
                int dropGroupVisibleChildren = this.mPositionTranslator.getVisibleChildCount(dropGroupPosition);
                if (dropIsGroup) {
                    canDrop = !isDropGroupExpanded;
                } else {
                    canDrop = dropChildPosition == dropGroupVisibleChildren + -1;
                }
            } else {
                canDrop = dropIsGroup;
            }
            if (canDrop) {
                return adapter.onCheckGroupCanDrop(draggingGroupPosition, dropGroupPosition);
            }
            return false;
        }
        isDropGroupExpanded = this.mPositionTranslator.isGroupExpanded(dropGroupPosition);
        int modDropGroupPosition = dropGroupPosition;
        int modDropChildPosition = dropChildPosition;
        if (draggingFlatPosition < dropFlatPosition) {
            canDrop = true;
            if (dropIsGroup) {
                modDropChildPosition = isDropGroupExpanded ? 0 : this.mPositionTranslator.getChildCount(modDropGroupPosition);
            }
        } else if (!dropIsGroup) {
            canDrop = true;
        } else if (modDropGroupPosition > 0) {
            modDropGroupPosition--;
            modDropChildPosition = this.mPositionTranslator.getChildCount(modDropGroupPosition);
            canDrop = true;
        } else {
            canDrop = false;
        }
        if (canDrop) {
            return adapter.onCheckChildCanDrop(draggingGroupPosition, draggingChildPosition, modDropGroupPosition, modDropChildPosition);
        }
        return false;
    }

    private static boolean isGroupPositionRange(ItemDraggableRange range) {
        if (range.getClass().equals(GroupPositionItemDraggableRange.class) || range.getClass().equals(ItemDraggableRange.class)) {
            return true;
        }
        return false;
    }

    private static boolean isChildPositionRange(ItemDraggableRange range) {
        return range.getClass().equals(ChildPositionItemDraggableRange.class);
    }

    public void onMoveItem(int fromPosition, int toPosition) {
        if (this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter) {
            this.mDraggingItemGroupRangeStart = -1;
            this.mDraggingItemGroupRangeEnd = -1;
            this.mDraggingItemChildRangeStart = -1;
            this.mDraggingItemChildRangeEnd = -1;
            if (fromPosition != toPosition) {
                ExpandableDraggableItemAdapter adapter = this.mExpandableItemAdapter;
                long expandableFromPosition = this.mPositionTranslator.getExpandablePosition(fromPosition);
                int fromGroupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandableFromPosition);
                int fromChildPosition = ExpandableAdapterHelper.getPackedPositionChild(expandableFromPosition);
                long expandableToPosition = this.mPositionTranslator.getExpandablePosition(toPosition);
                int toGroupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandableToPosition);
                int toChildPosition = ExpandableAdapterHelper.getPackedPositionChild(expandableToPosition);
                boolean fromIsGroup = fromChildPosition == -1;
                boolean toIsGroup = toChildPosition == -1;
                int actualToFlatPosition = fromPosition;
                if (fromIsGroup && toIsGroup) {
                    adapter.onMoveGroupItem(fromGroupPosition, toGroupPosition);
                    this.mPositionTranslator.moveGroupItem(fromGroupPosition, toGroupPosition);
                    actualToFlatPosition = toPosition;
                } else if (!fromIsGroup && !toIsGroup) {
                    if (fromGroupPosition == toGroupPosition) {
                        modToChildPosition = toChildPosition;
                    } else if (fromPosition < toPosition) {
                        modToChildPosition = toChildPosition + 1;
                    } else {
                        modToChildPosition = toChildPosition;
                    }
                    actualToFlatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(fromGroupPosition, modToChildPosition));
                    adapter.onMoveChildItem(fromGroupPosition, fromChildPosition, toGroupPosition, modToChildPosition);
                    this.mPositionTranslator.moveChildItem(fromGroupPosition, fromChildPosition, toGroupPosition, modToChildPosition);
                } else if (!fromIsGroup) {
                    int modToGroupPosition;
                    if (toPosition < fromPosition) {
                        if (toGroupPosition == 0) {
                            modToGroupPosition = toGroupPosition;
                            modToChildPosition = 0;
                        } else {
                            modToGroupPosition = toGroupPosition - 1;
                            modToChildPosition = this.mPositionTranslator.getChildCount(modToGroupPosition);
                        }
                    } else if (this.mPositionTranslator.isGroupExpanded(toGroupPosition)) {
                        modToGroupPosition = toGroupPosition;
                        modToChildPosition = 0;
                    } else {
                        modToGroupPosition = toGroupPosition;
                        modToChildPosition = this.mPositionTranslator.getChildCount(modToGroupPosition);
                    }
                    if (fromGroupPosition == modToGroupPosition) {
                        modToChildPosition = Math.min(modToChildPosition, Math.max(0, this.mPositionTranslator.getChildCount(modToGroupPosition) - 1));
                    }
                    if (!(fromGroupPosition == modToGroupPosition && fromChildPosition == modToChildPosition)) {
                        if (this.mPositionTranslator.isGroupExpanded(toGroupPosition)) {
                            actualToFlatPosition = toPosition;
                        } else {
                            actualToFlatPosition = -1;
                        }
                        adapter.onMoveChildItem(fromGroupPosition, fromChildPosition, modToGroupPosition, modToChildPosition);
                        this.mPositionTranslator.moveChildItem(fromGroupPosition, fromChildPosition, modToGroupPosition, modToChildPosition);
                    }
                } else if (fromGroupPosition != toGroupPosition) {
                    actualToFlatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(toGroupPosition));
                    adapter.onMoveGroupItem(fromGroupPosition, toGroupPosition);
                    this.mPositionTranslator.moveGroupItem(fromGroupPosition, toGroupPosition);
                }
                if (actualToFlatPosition == fromPosition) {
                    return;
                }
                if (actualToFlatPosition != -1) {
                    notifyItemMoved(fromPosition, actualToFlatPosition);
                } else {
                    notifyItemRemoved(fromPosition);
                }
            }
        }
    }

    public int onGetSwipeReactionType(ViewHolder holder, int position, int x, int y) {
        if (!(this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter)) {
            return 0;
        }
        BaseExpandableSwipeableItemAdapter adapter = this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            return adapter.onGetGroupItemSwipeReactionType(holder, groupPosition, x, y);
        }
        return adapter.onGetChildItemSwipeReactionType(holder, groupPosition, childPosition, x, y);
    }

    public void onSetSwipeBackground(ViewHolder holder, int position, int type) {
        if (this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter) {
            BaseExpandableSwipeableItemAdapter adapter = this.mExpandableItemAdapter;
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
            int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            if (childPosition == -1) {
                adapter.onSetGroupItemSwipeBackground(holder, groupPosition, type);
            } else {
                adapter.onSetChildItemSwipeBackground(holder, groupPosition, childPosition, type);
            }
        }
    }

    public SwipeResultAction onSwipeItem(ViewHolder holder, int position, int result) {
        if (!(this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter) || position == -1) {
            return null;
        }
        BaseExpandableSwipeableItemAdapter<?, ?> adapter = this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        return ExpandableSwipeableItemInternalUtils.invokeOnSwipeItem(adapter, holder, ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition), ExpandableAdapterHelper.getPackedPositionChild(expandablePosition), result);
    }

    boolean onTapItem(ViewHolder holder, int position, int x, int y) {
        if (this.mExpandableItemAdapter == null) {
            return false;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        if (ExpandableAdapterHelper.getPackedPositionChild(expandablePosition) != -1) {
            return false;
        }
        boolean expand = !this.mPositionTranslator.isGroupExpanded(groupPosition);
        if (!this.mExpandableItemAdapter.onCheckCanExpandOrCollapseGroup(holder, groupPosition, x, y, expand)) {
            return false;
        }
        if (expand) {
            expandGroup(groupPosition, true, null);
        } else {
            collapseGroup(groupPosition, true, null);
        }
        return true;
    }

    void expandAll() {
        if (!this.mPositionTranslator.isEmpty() && !this.mPositionTranslator.isAllExpanded()) {
            this.mPositionTranslator.build(this.mExpandableItemAdapter, 1, this.mExpandableListManager.getDefaultGroupsExpandedState());
            notifyDataSetChanged();
        }
    }

    void collapseAll() {
        if (!this.mPositionTranslator.isEmpty() && !this.mPositionTranslator.isAllCollapsed()) {
            this.mPositionTranslator.build(this.mExpandableItemAdapter, 2, this.mExpandableListManager.getDefaultGroupsExpandedState());
            notifyDataSetChanged();
        }
    }

    boolean collapseGroup(int groupPosition, boolean fromUser, Object payload) {
        if (!this.mPositionTranslator.isGroupExpanded(groupPosition) || !this.mExpandableItemAdapter.onHookGroupCollapse(groupPosition, fromUser, payload)) {
            return false;
        }
        if (this.mPositionTranslator.collapseGroup(groupPosition)) {
            int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition));
            notifyItemRangeRemoved(flatPosition + 1, this.mPositionTranslator.getChildCount(groupPosition));
        }
        notifyItemChanged(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition)), payload);
        if (this.mOnGroupCollapseListener != null) {
            this.mOnGroupCollapseListener.onGroupCollapse(groupPosition, fromUser, payload);
        }
        return true;
    }

    boolean expandGroup(int groupPosition, boolean fromUser, Object payload) {
        if (this.mPositionTranslator.isGroupExpanded(groupPosition) || !this.mExpandableItemAdapter.onHookGroupExpand(groupPosition, fromUser, payload)) {
            return false;
        }
        if (this.mPositionTranslator.expandGroup(groupPosition)) {
            int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition));
            notifyItemRangeInserted(flatPosition + 1, this.mPositionTranslator.getChildCount(groupPosition));
        }
        notifyItemChanged(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition)), payload);
        if (this.mOnGroupExpandListener != null) {
            this.mOnGroupExpandListener.onGroupExpand(groupPosition, fromUser, payload);
        }
        return true;
    }

    boolean isGroupExpanded(int groupPosition) {
        return this.mPositionTranslator.isGroupExpanded(groupPosition);
    }

    long getExpandablePosition(int flatPosition) {
        return this.mPositionTranslator.getExpandablePosition(flatPosition);
    }

    int getFlatPosition(long packedPosition) {
        return this.mPositionTranslator.getFlatPosition(packedPosition);
    }

    long[] getExpandedItemsSavedStateArray() {
        if (this.mPositionTranslator != null) {
            return this.mPositionTranslator.getSavedStateArray();
        }
        return null;
    }

    void setOnGroupExpandListener(OnGroupExpandListener listener) {
        this.mOnGroupExpandListener = listener;
    }

    void setOnGroupCollapseListener(OnGroupCollapseListener listener) {
        this.mOnGroupCollapseListener = listener;
    }

    void restoreState(long[] adapterSavedState, boolean callHook, boolean callListeners) {
        OnGroupCollapseListener onGroupCollapseListener = null;
        ExpandablePositionTranslator expandablePositionTranslator = this.mPositionTranslator;
        ExpandableItemAdapter expandableItemAdapter = callHook ? this.mExpandableItemAdapter : null;
        OnGroupExpandListener onGroupExpandListener = callListeners ? this.mOnGroupExpandListener : null;
        if (callListeners) {
            onGroupCollapseListener = this.mOnGroupCollapseListener;
        }
        expandablePositionTranslator.restoreExpandedGroupItems(adapterSavedState, expandableItemAdapter, onGroupExpandListener, onGroupCollapseListener);
    }

    void notifyGroupItemChanged(int groupPosition, Object payload) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition));
        if (flatPosition != -1) {
            notifyItemChanged(flatPosition, payload);
        }
    }

    void notifyGroupAndChildrenItemsChanged(int groupPosition, Object payload) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition));
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(groupPosition);
        if (flatPosition != -1) {
            notifyItemRangeChanged(flatPosition, visibleChildCount + 1, payload);
        }
    }

    void notifyChildrenOfGroupItemChanged(int groupPosition, Object payload) {
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(groupPosition);
        if (visibleChildCount > 0) {
            int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, 0));
            if (flatPosition != -1) {
                notifyItemRangeChanged(flatPosition, visibleChildCount, payload);
            }
        }
    }

    void notifyChildItemChanged(int groupPosition, int childPosition, Object payload) {
        notifyChildItemRangeChanged(groupPosition, childPosition, 1, payload);
    }

    void notifyChildItemRangeChanged(int groupPosition, int childPositionStart, int itemCount, Object payload) {
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(groupPosition);
        if (visibleChildCount > 0 && childPositionStart < visibleChildCount) {
            int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, 0));
            if (flatPosition != -1) {
                notifyItemRangeChanged(flatPosition + childPositionStart, Math.min(itemCount, visibleChildCount - childPositionStart), payload);
            }
        }
    }

    void notifyChildItemInserted(int groupPosition, int childPosition) {
        this.mPositionTranslator.insertChildItem(groupPosition, childPosition);
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPosition));
        if (flatPosition != -1) {
            notifyItemInserted(flatPosition);
        }
    }

    void notifyChildItemRangeInserted(int groupPosition, int childPositionStart, int itemCount) {
        this.mPositionTranslator.insertChildItems(groupPosition, childPositionStart, itemCount);
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPositionStart));
        if (flatPosition != -1) {
            notifyItemRangeInserted(flatPosition, itemCount);
        }
    }

    void notifyChildItemRemoved(int groupPosition, int childPosition) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPosition));
        this.mPositionTranslator.removeChildItem(groupPosition, childPosition);
        if (flatPosition != -1) {
            notifyItemRemoved(flatPosition);
        }
    }

    void notifyChildItemRangeRemoved(int groupPosition, int childPositionStart, int itemCount) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPositionStart));
        this.mPositionTranslator.removeChildItems(groupPosition, childPositionStart, itemCount);
        if (flatPosition != -1) {
            notifyItemRangeRemoved(flatPosition, itemCount);
        }
    }

    void notifyGroupItemInserted(int groupPosition, boolean expanded) {
        if (this.mPositionTranslator.insertGroupItem(groupPosition, expanded) > 0) {
            notifyItemInserted(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition)));
            raiseOnGroupExpandedSequentially(groupPosition, 1, false, null);
        }
    }

    void notifyGroupItemRangeInserted(int groupPositionStart, int count, boolean expanded) {
        int insertedCount = this.mPositionTranslator.insertGroupItems(groupPositionStart, count, expanded);
        if (insertedCount > 0) {
            notifyItemRangeInserted(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPositionStart)), insertedCount);
            raiseOnGroupExpandedSequentially(groupPositionStart, count, false, null);
        }
    }

    void notifyGroupItemMoved(int fromGroupPosition, int toGroupPosition) {
        long packedFrom = RecyclerViewExpandableItemManager.getPackedPositionForGroup(fromGroupPosition);
        long packedTo = RecyclerViewExpandableItemManager.getPackedPositionForGroup(toGroupPosition);
        int flatFrom = getFlatPosition(packedFrom);
        int flatTo = getFlatPosition(packedTo);
        boolean fromExpanded = isGroupExpanded(fromGroupPosition);
        boolean toExpanded = isGroupExpanded(toGroupPosition);
        this.mPositionTranslator.moveGroupItem(fromGroupPosition, toGroupPosition);
        if (fromExpanded || toExpanded) {
            notifyDataSetChanged();
        } else {
            notifyItemMoved(flatFrom, flatTo);
        }
    }

    void notifyChildItemMoved(int groupPosition, int fromChildPosition, int toChildPosition) {
        notifyChildItemMoved(groupPosition, fromChildPosition, groupPosition, toChildPosition);
    }

    void notifyChildItemMoved(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        long packedFrom = RecyclerViewExpandableItemManager.getPackedPositionForChild(fromGroupPosition, fromChildPosition);
        long packedTo = RecyclerViewExpandableItemManager.getPackedPositionForChild(toGroupPosition, toChildPosition);
        int flatFrom = getFlatPosition(packedFrom);
        int flatTo = getFlatPosition(packedTo);
        this.mPositionTranslator.moveChildItem(fromGroupPosition, fromChildPosition, toGroupPosition, toChildPosition);
        if (flatFrom != -1 && flatTo != -1) {
            notifyItemMoved(flatFrom, flatTo);
        } else if (flatFrom != -1) {
            notifyItemRemoved(flatFrom);
        } else if (flatTo != -1) {
            notifyItemInserted(flatTo);
        }
    }

    private void raiseOnGroupExpandedSequentially(int groupPositionStart, int count, boolean fromUser, Object payload) {
        if (this.mOnGroupExpandListener != null) {
            for (int i = 0; i < count; i++) {
                this.mOnGroupExpandListener.onGroupExpand(groupPositionStart + i, fromUser, payload);
            }
        }
    }

    void notifyGroupItemRemoved(int groupPosition) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition));
        int removedCount = this.mPositionTranslator.removeGroupItem(groupPosition);
        if (removedCount > 0) {
            notifyItemRangeRemoved(flatPosition, removedCount);
        }
    }

    void notifyGroupItemRangeRemoved(int groupPositionStart, int count) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(groupPositionStart));
        int removedCount = this.mPositionTranslator.removeGroupItems(groupPositionStart, count);
        if (removedCount > 0) {
            notifyItemRangeRemoved(flatPosition, removedCount);
        }
    }

    int getGroupCount() {
        return this.mExpandableItemAdapter.getGroupCount();
    }

    int getChildCount(int groupPosition) {
        return this.mExpandableItemAdapter.getChildCount(groupPosition);
    }

    int getExpandedGroupsCount() {
        return this.mPositionTranslator.getExpandedGroupsCount();
    }

    int getCollapsedGroupsCount() {
        return this.mPositionTranslator.getCollapsedGroupsCount();
    }

    boolean isAllGroupsExpanded() {
        return this.mPositionTranslator.isAllExpanded();
    }

    boolean isAllGroupsCollapsed() {
        return this.mPositionTranslator.isAllCollapsed();
    }

    private static ExpandableItemAdapter getExpandableItemAdapter(Adapter adapter) {
        return (ExpandableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, ExpandableItemAdapter.class);
    }

    private static void safeUpdateExpandStateFlags(ViewHolder holder, int flags) {
        if (holder instanceof ExpandableItemViewHolder) {
            ExpandableItemViewHolder holder2 = (ExpandableItemViewHolder) holder;
            int curFlags = holder2.getExpandStateFlags();
            if (!(curFlags == -1 || ((curFlags ^ flags) & 4) == 0)) {
                flags |= 8;
            }
            if (curFlags == -1 || ((curFlags ^ flags) & Integer.MAX_VALUE) != 0) {
                flags |= Integer.MIN_VALUE;
            }
            holder2.setExpandStateFlags(flags);
        }
    }

    private void correctItemDragStateFlags(ViewHolder holder, int groupPosition, int childPosition) {
        if (holder instanceof DraggableItemViewHolder) {
            DraggableItemViewHolder holder2 = (DraggableItemViewHolder) holder;
            boolean groupRangeSpecified;
            if (this.mDraggingItemGroupRangeStart == -1 || this.mDraggingItemGroupRangeEnd == -1) {
                groupRangeSpecified = false;
            } else {
                groupRangeSpecified = true;
            }
            boolean childRangeSpecified;
            if (this.mDraggingItemChildRangeStart == -1 || this.mDraggingItemChildRangeEnd == -1) {
                childRangeSpecified = false;
            } else {
                childRangeSpecified = true;
            }
            boolean isInGroupRange;
            if (groupPosition < this.mDraggingItemGroupRangeStart || groupPosition > this.mDraggingItemGroupRangeEnd) {
                isInGroupRange = false;
            } else {
                isInGroupRange = true;
            }
            boolean isInChildRange;
            if (groupPosition == -1 || childPosition < this.mDraggingItemChildRangeStart || childPosition > this.mDraggingItemChildRangeEnd) {
                isInChildRange = false;
            } else {
                isInChildRange = true;
            }
            int flags = holder2.getDragStateFlags();
            boolean needCorrection = false;
            if ((flags & 1) != 0 && (flags & 4) == 0 && ((!groupRangeSpecified || isInGroupRange) && (!childRangeSpecified || (childRangeSpecified && isInChildRange)))) {
                needCorrection = true;
            }
            if (needCorrection) {
                holder2.setDragStateFlags((flags | 4) | Integer.MIN_VALUE);
            }
        }
    }
}
