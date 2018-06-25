package org.telegram.customization.util.view.DragDrop;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Model.DialogTab;
import org.telegram.customization.util.view.DragDrop.listeners.OnCustomerListChangedListener;
import org.telegram.customization.util.view.DragDrop.listeners.OnEndDragListener;
import org.telegram.customization.util.view.DragDrop.listeners.OnStartDragListener;
import org.telegram.customization.util.view.DragDrop.utilities.ItemTouchHelperAdapter;
import org.telegram.customization.util.view.DragDrop.utilities.ItemTouchHelperViewHolder;

public class DialogTabsAdapter extends Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {
    private List<DialogTab> dialogTabs;
    private Context mContext;
    private OnEndDragListener mDragEndListener;
    private OnStartDragListener mDragStartListener;
    private OnCustomerListChangedListener mListChangedListener;

    public static class ItemViewHolder extends ViewHolder implements ItemTouchHelperViewHolder {
        public final TextView customerName;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.customerName = (TextView) itemView.findViewById(R.id.text_view_customer_name);
            this.handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        public void onItemSelected() {
            this.itemView.setBackgroundColor(-3355444);
        }

        public void onItemClear() {
            this.itemView.setBackgroundColor(0);
        }
    }

    public DialogTabsAdapter(List<DialogTab> customers, Context context, OnStartDragListener dragLlistener, OnEndDragListener dragEndListener, OnCustomerListChangedListener listChangedListener) {
        this.dialogTabs = customers;
        this.mContext = context;
        this.mDragStartListener = dragLlistener;
        this.mListChangedListener = listChangedListener;
        this.mDragEndListener = dragEndListener;
    }

    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_tab_list, parent, false));
    }

    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.customerName.setText(((DialogTab) this.dialogTabs.get(position)).getShowName());
        holder.handleView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("LEE", "Actttion:" + MotionEventCompat.getActionMasked(event));
                if (MotionEventCompat.getActionMasked(event) == 0) {
                    DialogTabsAdapter.this.mDragStartListener.onStartDrag(holder);
                } else if (MotionEventCompat.getActionMasked(event) == 1) {
                    DialogTabsAdapter.this.mDragEndListener.onEndDrag(holder);
                }
                return false;
            }
        });
    }

    public int getItemCount() {
        return this.dialogTabs.size();
    }

    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(this.dialogTabs, fromPosition, toPosition);
        this.mListChangedListener.onNoteListChanged(this.dialogTabs);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onItemDismiss(int position) {
    }
}
