package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemAdapter;
import java.util.List;

public abstract class AbstractExpandableItemAdapter<GVH extends ViewHolder, CVH extends ViewHolder> extends Adapter<ViewHolder> implements ExpandableItemAdapter<GVH, CVH> {
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public final long getItemId(int position) {
        return -1;
    }

    public final int getItemViewType(int position) {
        return 0;
    }

    public final int getItemCount() {
        return 0;
    }

    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    public final void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void onBindGroupViewHolder(GVH holder, int groupPosition, int viewType, List<Object> list) {
        onBindGroupViewHolder(holder, groupPosition, viewType);
    }

    public void onBindChildViewHolder(CVH holder, int groupPosition, int childPosition, int viewType, List<Object> list) {
        onBindChildViewHolder(holder, groupPosition, childPosition, viewType);
    }

    public boolean onHookGroupExpand(int groupPosition, boolean fromUser) {
        return true;
    }

    public boolean onHookGroupExpand(int groupPosition, boolean fromUser, Object payload) {
        return onHookGroupExpand(groupPosition, fromUser);
    }

    public boolean onHookGroupCollapse(int groupPosition, boolean fromUser) {
        return true;
    }

    public boolean onHookGroupCollapse(int groupPosition, boolean fromUser, Object payload) {
        return onHookGroupCollapse(groupPosition, fromUser);
    }

    public boolean getInitialGroupExpandedState(int groupPosition) {
        return false;
    }
}
