package org.telegram.customization.util.view.DragDrop.utilities;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;

public class SimpleItemTouchHelperCallback extends Callback {
    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.mAdapter = adapter;
    }

    public boolean isLongPressDragEnabled() {
        return true;
    }

    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        Log.d("alireza", "alireza + 3");
        return makeMovementFlags(3, 48);
    }

    public boolean onMove(RecyclerView recyclerView, ViewHolder source, ViewHolder target) {
        this.mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    public void onSwiped(ViewHolder viewHolder, int i) {
        this.mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        if (actionState != 0) {
            ((ItemTouchHelperViewHolder) viewHolder).onItemSelected();
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ((ItemTouchHelperViewHolder) viewHolder).onItemClear();
    }
}
