package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemIdComposer;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemViewTypeComposer;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

public class RecyclerViewExpandableItemManager implements ExpandableItemConstants {
    public static final long NO_EXPANDABLE_POSITION = -1;
    private static final String TAG = "ARVExpandableItemMgr";
    private boolean mDefaultGroupsExpandedState = false;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C06041();
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;
    private RecyclerView mRecyclerView;
    private SavedState mSavedState;
    private int mTouchSlop;
    private long mTouchedItemId = -1;
    private ExpandableRecyclerViewWrapperAdapter mWrapperAdapter;

    /* renamed from: com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager$1 */
    class C06041 implements OnItemTouchListener {
        C06041() {
        }

        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return RecyclerViewExpandableItemManager.this.onInterceptTouchEvent(rv, e);
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public interface OnGroupCollapseListener {
        void onGroupCollapse(int i, boolean z, Object obj);
    }

    public interface OnGroupExpandListener {
        void onGroupExpand(int i, boolean z, Object obj);
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C06051();
        final long[] adapterSavedState;

        /* renamed from: com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager$SavedState$1 */
        static class C06051 implements Creator<SavedState> {
            C06051() {
            }

            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        public SavedState(long[] adapterSavedState) {
            this.adapterSavedState = adapterSavedState;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLongArray(this.adapterSavedState);
        }

        SavedState(Parcel in) {
            this.adapterSavedState = in.createLongArray();
        }
    }

    public RecyclerViewExpandableItemManager(@Nullable Parcelable savedState) {
        if (savedState instanceof SavedState) {
            this.mSavedState = (SavedState) savedState;
        }
    }

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    public void attachRecyclerView(@NonNull RecyclerView rv) {
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        } else if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        } else {
            this.mRecyclerView = rv;
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            this.mTouchSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        }
    }

    public void release() {
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        this.mOnGroupExpandListener = null;
        this.mOnGroupCollapseListener = null;
        this.mRecyclerView = null;
        this.mSavedState = null;
    }

    public Adapter createWrappedAdapter(@NonNull Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("The passed adapter does not support stable IDs");
        } else if (this.mWrapperAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        } else {
            long[] adapterSavedState;
            if (this.mSavedState != null) {
                adapterSavedState = this.mSavedState.adapterSavedState;
            } else {
                adapterSavedState = null;
            }
            this.mSavedState = null;
            this.mWrapperAdapter = new ExpandableRecyclerViewWrapperAdapter(this, adapter, adapterSavedState);
            this.mWrapperAdapter.setOnGroupExpandListener(this.mOnGroupExpandListener);
            this.mOnGroupExpandListener = null;
            this.mWrapperAdapter.setOnGroupCollapseListener(this.mOnGroupCollapseListener);
            this.mOnGroupCollapseListener = null;
            return this.mWrapperAdapter;
        }
    }

    public Parcelable getSavedState() {
        long[] adapterSavedState = null;
        if (this.mWrapperAdapter != null) {
            adapterSavedState = this.mWrapperAdapter.getExpandedItemsSavedStateArray();
        }
        return new SavedState(adapterSavedState);
    }

    boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.mWrapperAdapter != null) {
            switch (MotionEventCompat.getActionMasked(e)) {
                case 0:
                    handleActionDown(rv, e);
                    break;
                case 1:
                case 3:
                    if (handleActionUpOrCancel(rv, e)) {
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    private void handleActionDown(RecyclerView rv, MotionEvent e) {
        ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY());
        this.mInitialTouchX = (int) (e.getX() + 0.5f);
        this.mInitialTouchY = (int) (e.getY() + 0.5f);
        if (holder instanceof ExpandableItemViewHolder) {
            this.mTouchedItemId = holder.getItemId();
        } else {
            this.mTouchedItemId = -1;
        }
    }

    private boolean handleActionUpOrCancel(RecyclerView rv, MotionEvent e) {
        long touchedItemId = this.mTouchedItemId;
        int initialTouchX = this.mInitialTouchX;
        int initialTouchY = this.mInitialTouchY;
        this.mTouchedItemId = -1;
        this.mInitialTouchX = 0;
        this.mInitialTouchY = 0;
        if (touchedItemId == -1 || MotionEventCompat.getActionMasked(e) != 1) {
            return false;
        }
        if (this.mRecyclerView.isComputingLayout()) {
            return false;
        }
        int touchX = (int) (e.getX() + 0.5f);
        int touchY = (int) (e.getY() + 0.5f);
        int diffY = touchY - initialTouchY;
        if (Math.abs(touchX - initialTouchX) >= this.mTouchSlop || Math.abs(diffY) >= this.mTouchSlop) {
            return false;
        }
        ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY());
        if (holder == null || holder.getItemId() != touchedItemId) {
            return false;
        }
        int wrappedItemPosition = WrapperAdapterUtils.unwrapPosition(this.mRecyclerView.getAdapter(), this.mWrapperAdapter, CustomRecyclerViewUtils.getSynchronizedPosition(holder));
        if (wrappedItemPosition == -1) {
            return false;
        }
        View view = holder.itemView;
        return this.mWrapperAdapter.onTapItem(holder, wrappedItemPosition, touchX - (view.getLeft() + ((int) (ViewCompat.getTranslationX(view) + 0.5f))), touchY - (view.getTop() + ((int) (ViewCompat.getTranslationY(view) + 0.5f))));
    }

    public void expandAll() {
        if (this.mWrapperAdapter != null) {
            this.mWrapperAdapter.expandAll();
        }
    }

    public void collapseAll() {
        if (this.mWrapperAdapter != null) {
            this.mWrapperAdapter.collapseAll();
        }
    }

    public boolean expandGroup(int groupPosition) {
        return expandGroup(groupPosition, null);
    }

    public boolean expandGroup(int groupPosition, Object payload) {
        return this.mWrapperAdapter != null && this.mWrapperAdapter.expandGroup(groupPosition, false, payload);
    }

    public boolean collapseGroup(int groupPosition) {
        return collapseGroup(groupPosition, null);
    }

    public boolean collapseGroup(int groupPosition, Object payload) {
        return this.mWrapperAdapter != null && this.mWrapperAdapter.collapseGroup(groupPosition, false, payload);
    }

    public long getExpandablePosition(int flatPosition) {
        if (this.mWrapperAdapter == null) {
            return -1;
        }
        return this.mWrapperAdapter.getExpandablePosition(flatPosition);
    }

    public int getFlatPosition(long packedPosition) {
        if (this.mWrapperAdapter == null) {
            return -1;
        }
        return this.mWrapperAdapter.getFlatPosition(packedPosition);
    }

    public static int getPackedPositionChild(long packedPosition) {
        return ExpandableAdapterHelper.getPackedPositionChild(packedPosition);
    }

    public static long getPackedPositionForChild(int groupPosition, int childPosition) {
        return ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPosition);
    }

    public static long getPackedPositionForGroup(int groupPosition) {
        return ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
    }

    public static int getPackedPositionGroup(long packedPosition) {
        return ExpandableAdapterHelper.getPackedPositionGroup(packedPosition);
    }

    public boolean isGroupExpanded(int groupPosition) {
        return this.mWrapperAdapter != null && this.mWrapperAdapter.isGroupExpanded(groupPosition);
    }

    public static long getCombinedChildId(long groupId, long childId) {
        return ItemIdComposer.composeExpandableChildId(groupId, childId);
    }

    public static long getCombinedGroupId(long groupId) {
        return ItemIdComposer.composeExpandableGroupId(groupId);
    }

    public static boolean isGroupViewType(int rawViewType) {
        return ItemViewTypeComposer.isExpandableGroup(rawViewType);
    }

    public static int getGroupViewType(int rawViewType) {
        return ItemViewTypeComposer.extractWrappedViewTypePart(rawViewType);
    }

    public static int getChildViewType(int rawViewType) {
        return ItemViewTypeComposer.extractWrappedViewTypePart(rawViewType);
    }

    public static boolean isGroupItemId(long rawId) {
        return ItemIdComposer.isExpandableGroup(rawId);
    }

    public static long getGroupItemId(long rawId) {
        return ItemIdComposer.extractExpandableGroupIdPart(rawId);
    }

    public static long getChildItemId(long rawId) {
        return ItemIdComposer.extractExpandableChildIdPart(rawId);
    }

    public void setOnGroupExpandListener(@Nullable OnGroupExpandListener listener) {
        if (this.mWrapperAdapter != null) {
            this.mWrapperAdapter.setOnGroupExpandListener(listener);
        } else {
            this.mOnGroupExpandListener = listener;
        }
    }

    public void setOnGroupCollapseListener(@Nullable OnGroupCollapseListener listener) {
        if (this.mWrapperAdapter != null) {
            this.mWrapperAdapter.setOnGroupCollapseListener(listener);
        } else {
            this.mOnGroupCollapseListener = listener;
        }
    }

    public void restoreState(@Nullable Parcelable savedState) {
        restoreState(savedState, false, false);
    }

    public void restoreState(@Nullable Parcelable savedState, boolean callHooks, boolean callListeners) {
        if (savedState != null) {
            if (!(savedState instanceof SavedState)) {
                throw new IllegalArgumentException("Illegal saved state object passed");
            } else if (this.mWrapperAdapter == null || this.mRecyclerView == null) {
                throw new IllegalStateException("RecyclerView has not been attached");
            } else {
                this.mWrapperAdapter.restoreState(((SavedState) savedState).adapterSavedState, callHooks, callListeners);
            }
        }
    }

    public void notifyGroupItemChanged(int groupPosition) {
        this.mWrapperAdapter.notifyGroupItemChanged(groupPosition, null);
    }

    public void notifyGroupItemChanged(int groupPosition, Object payload) {
        this.mWrapperAdapter.notifyGroupItemChanged(groupPosition, payload);
    }

    public void notifyGroupAndChildrenItemsChanged(int groupPosition) {
        this.mWrapperAdapter.notifyGroupAndChildrenItemsChanged(groupPosition, null);
    }

    public void notifyGroupAndChildrenItemsChanged(int groupPosition, Object payload) {
        this.mWrapperAdapter.notifyGroupAndChildrenItemsChanged(groupPosition, payload);
    }

    public void notifyChildrenOfGroupItemChanged(int groupPosition) {
        this.mWrapperAdapter.notifyChildrenOfGroupItemChanged(groupPosition, null);
    }

    public void notifyChildrenOfGroupItemChanged(int groupPosition, Object payload) {
        this.mWrapperAdapter.notifyChildrenOfGroupItemChanged(groupPosition, payload);
    }

    public void notifyChildItemChanged(int groupPosition, int childPosition) {
        this.mWrapperAdapter.notifyChildItemChanged(groupPosition, childPosition, null);
    }

    public void notifyChildItemChanged(int groupPosition, int childPosition, Object payload) {
        this.mWrapperAdapter.notifyChildItemChanged(groupPosition, childPosition, payload);
    }

    public void notifyChildItemRangeChanged(int groupPosition, int childPositionStart, int itemCount) {
        this.mWrapperAdapter.notifyChildItemRangeChanged(groupPosition, childPositionStart, itemCount, null);
    }

    public void notifyChildItemRangeChanged(int groupPosition, int childPositionStart, int itemCount, Object payload) {
        this.mWrapperAdapter.notifyChildItemRangeChanged(groupPosition, childPositionStart, itemCount, payload);
    }

    public void notifyGroupItemInserted(int groupPosition) {
        notifyGroupItemInserted(groupPosition, this.mDefaultGroupsExpandedState);
    }

    public void notifyGroupItemInserted(int groupPosition, boolean expanded) {
        this.mWrapperAdapter.notifyGroupItemInserted(groupPosition, expanded);
    }

    public void notifyGroupItemRangeInserted(int groupPositionStart, int itemCount) {
        notifyGroupItemRangeInserted(groupPositionStart, itemCount, this.mDefaultGroupsExpandedState);
    }

    public void notifyGroupItemRangeInserted(int groupPositionStart, int itemCount, boolean expanded) {
        this.mWrapperAdapter.notifyGroupItemRangeInserted(groupPositionStart, itemCount, expanded);
    }

    public void notifyChildItemInserted(int groupPosition, int childPosition) {
        this.mWrapperAdapter.notifyChildItemInserted(groupPosition, childPosition);
    }

    public void notifyChildItemRangeInserted(int groupPosition, int childPositionStart, int itemCount) {
        this.mWrapperAdapter.notifyChildItemRangeInserted(groupPosition, childPositionStart, itemCount);
    }

    public void notifyGroupItemRemoved(int groupPosition) {
        this.mWrapperAdapter.notifyGroupItemRemoved(groupPosition);
    }

    public void notifyGroupItemRangeRemoved(int groupPositionStart, int itemCount) {
        this.mWrapperAdapter.notifyGroupItemRangeRemoved(groupPositionStart, itemCount);
    }

    public void notifyChildItemRemoved(int groupPosition, int childPosition) {
        this.mWrapperAdapter.notifyChildItemRemoved(groupPosition, childPosition);
    }

    public void notifyChildItemRangeRemoved(int groupPosition, int childPositionStart, int itemCount) {
        this.mWrapperAdapter.notifyChildItemRangeRemoved(groupPosition, childPositionStart, itemCount);
    }

    public void notifyGroupItemMoved(int fromGroupPosition, int toGroupPosition) {
        this.mWrapperAdapter.notifyGroupItemMoved(fromGroupPosition, toGroupPosition);
    }

    public void notifyChildItemMoved(int groupPosition, int fromChildPosition, int toChildPosition) {
        this.mWrapperAdapter.notifyChildItemMoved(groupPosition, fromChildPosition, toChildPosition);
    }

    public void notifyChildItemMoved(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        this.mWrapperAdapter.notifyChildItemMoved(fromGroupPosition, fromChildPosition, toGroupPosition, toChildPosition);
    }

    public int getGroupCount() {
        return this.mWrapperAdapter.getGroupCount();
    }

    public int getChildCount(int groupPosition) {
        return this.mWrapperAdapter.getChildCount(groupPosition);
    }

    public void scrollToGroup(int groupPosition, int childItemHeight) {
        scrollToGroup(groupPosition, childItemHeight, 0, 0);
    }

    public void scrollToGroup(int groupPosition, int childItemHeight, int topMargin, int bottomMargin) {
        scrollToGroupWithTotalChildrenHeight(groupPosition, getChildCount(groupPosition) * childItemHeight, topMargin, bottomMargin);
    }

    public void scrollToGroupWithTotalChildrenHeight(int groupPosition, int totalChildrenHeight, int topMargin, int bottomMargin) {
        int flatPosition = getFlatPosition(getPackedPositionForGroup(groupPosition));
        ViewHolder vh = this.mRecyclerView.findViewHolderForLayoutPosition(flatPosition);
        if (vh != null) {
            if (!isGroupExpanded(groupPosition)) {
                totalChildrenHeight = 0;
            }
            int groupItemTop = vh.itemView.getTop();
            int topRoom = groupItemTop;
            int bottomRoom = this.mRecyclerView.getHeight() - vh.itemView.getBottom();
            if (topRoom <= topMargin) {
                int parentTopPadding = this.mRecyclerView.getPaddingTop();
                ((LinearLayoutManager) this.mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(flatPosition, (topMargin - parentTopPadding) - ((LayoutParams) vh.itemView.getLayoutParams()).topMargin);
            } else if (bottomRoom < totalChildrenHeight + bottomMargin) {
                this.mRecyclerView.smoothScrollBy(0, Math.min(topRoom - topMargin, Math.max(0, (totalChildrenHeight + bottomMargin) - bottomRoom)));
            }
        }
    }

    public int getExpandedGroupsCount() {
        return this.mWrapperAdapter.getExpandedGroupsCount();
    }

    public int getCollapsedGroupsCount() {
        return this.mWrapperAdapter.getCollapsedGroupsCount();
    }

    public boolean isAllGroupsExpanded() {
        return this.mWrapperAdapter.isAllGroupsExpanded();
    }

    public boolean isAllGroupsCollapsed() {
        return this.mWrapperAdapter.isAllGroupsCollapsed();
    }

    public void setDefaultGroupsExpandedState(boolean expanded) {
        this.mDefaultGroupsExpandedState = expanded;
    }

    public boolean getDefaultGroupsExpandedState() {
        return this.mDefaultGroupsExpandedState;
    }
}
