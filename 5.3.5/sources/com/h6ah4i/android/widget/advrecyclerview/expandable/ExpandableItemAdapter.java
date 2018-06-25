package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.util.List;

public interface ExpandableItemAdapter<GVH extends ViewHolder, CVH extends ViewHolder> {
    int getChildCount(int i);

    @IntRange(from = -134217728, to = 134217727)
    long getChildId(int i, int i2);

    @IntRange(from = -8388608, to = 8388607)
    int getChildItemViewType(int i, int i2);

    int getGroupCount();

    @IntRange(from = -134217728, to = 134217727)
    long getGroupId(int i);

    @IntRange(from = -8388608, to = 8388607)
    int getGroupItemViewType(int i);

    boolean getInitialGroupExpandedState(int i);

    void onBindChildViewHolder(CVH cvh, int i, int i2, @IntRange(from = -8388608, to = 8388607) int i3);

    void onBindChildViewHolder(CVH cvh, int i, int i2, @IntRange(from = -8388608, to = 8388607) int i3, List<Object> list);

    void onBindGroupViewHolder(GVH gvh, int i, @IntRange(from = -8388608, to = 8388607) int i2);

    void onBindGroupViewHolder(GVH gvh, int i, @IntRange(from = -8388608, to = 8388607) int i2, List<Object> list);

    boolean onCheckCanExpandOrCollapseGroup(GVH gvh, int i, int i2, int i3, boolean z);

    CVH onCreateChildViewHolder(ViewGroup viewGroup, @IntRange(from = -8388608, to = 8388607) int i);

    GVH onCreateGroupViewHolder(ViewGroup viewGroup, @IntRange(from = -8388608, to = 8388607) int i);

    boolean onHookGroupCollapse(int i, boolean z);

    boolean onHookGroupCollapse(int i, boolean z, Object obj);

    boolean onHookGroupExpand(int i, boolean z);

    boolean onHookGroupExpand(int i, boolean z, Object obj);
}
