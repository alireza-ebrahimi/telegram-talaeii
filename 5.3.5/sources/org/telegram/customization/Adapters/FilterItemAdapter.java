package org.telegram.customization.Adapters;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Dialogs.FilterDialog;
import org.telegram.customization.Interfaces.FilterChangeListener;
import org.telegram.customization.Model.FilterItem;

public class FilterItemAdapter extends Adapter<FilterItemHolder> {
    FilterDialog dialog;
    FilterChangeListener filterChangeListener;
    ArrayList<FilterItem> filterItems = new ArrayList();

    class FilterItemHolder extends ViewHolder {
        ImageView ivCheck;
        View root;
        TextView tvName;

        public FilterItemHolder(View itemView) {
            super(itemView);
            this.root = itemView.findViewById(R.id.root);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.ivCheck = (ImageView) itemView.findViewById(R.id.iv_select_status);
        }
    }

    public FilterItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter, parent, false));
    }

    public FilterItemAdapter(FilterDialog filterDialog, ArrayList<FilterItem> data, FilterChangeListener listener) {
        this.filterItems = data;
        this.filterChangeListener = listener;
        this.dialog = filterDialog;
    }

    public ArrayList<FilterItem> getData() {
        return this.filterItems;
    }

    public void onBindViewHolder(FilterItemHolder holder, int position) {
        final FilterItem item = (FilterItem) getData().get(position);
        holder.tvName.setText(item.getName());
        if (item.isSelected()) {
            holder.ivCheck.setVisibility(0);
        } else {
            holder.ivCheck.setVisibility(8);
        }
        holder.root.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FilterItemAdapter.this.filterChangeListener.onFilterChange(item);
                FilterItemAdapter.this.dialog.dismiss();
            }
        });
    }

    public int getItemCount() {
        if (getData() == null) {
            return 0;
        }
        return getData().size();
    }
}
