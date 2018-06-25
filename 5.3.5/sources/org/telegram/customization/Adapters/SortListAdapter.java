package org.telegram.customization.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.DataPool.PostPoolMulti;
import utils.view.FarsiCheckBox;

public class SortListAdapter implements ListAdapter, OnClickListener {
    private final Context context;
    private ArrayList<customSortItem> customItems;
    private LayoutInflater inflater;
    private OnClickListener onClickListener;
    private final int poolId;

    public class customSortItem {
        private boolean checked = false;
        private String sortName;

        public customSortItem(String name, boolean checked) {
            setSortName(name);
            setChecked(checked);
        }

        public String getSortName() {
            return this.sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public boolean isChecked() {
            return this.checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    public SortListAdapter(Context context, ArrayList<String> customItems, OnClickListener onClickListener, int poolId) {
        this.context = context;
        this.poolId = poolId;
        mackCustomItems(customItems);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.onClickListener = onClickListener;
    }

    private void mackCustomItems(ArrayList<String> items) {
        setCustomItems(new ArrayList());
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                getCustomItems().add(new customSortItem((String) items.get(i), PostPoolMulti.getSortOrder(this.poolId) == ((long) i)));
            }
        }
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int position) {
        return false;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    public int getCount() {
        return getCustomItems().size();
    }

    public Object getItem(int position) {
        return getCustomItems().get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = getInflater().inflate(R.layout.sort_dialog, parent, false);
        customSortItem item = (customSortItem) getCustomItems().get(position);
        ((FarsiCheckBox) rowView.findViewById(R.id.text)).setTitle(item.getSortName());
        ((FarsiCheckBox) rowView).setChecked(item.isChecked());
        rowView.setTag(Integer.valueOf(position));
        rowView.setOnClickListener(this);
        return rowView;
    }

    public int getItemViewType(int position) {
        return 1;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return false;
    }

    public void onClick(View v) {
        try {
            int position = ((Integer) v.getTag()).intValue();
            int i = 0;
            while (i < getCustomItems().size()) {
                ((customSortItem) getCustomItems().get(i)).setChecked(i == position);
                i++;
            }
            if (this.onClickListener != null) {
                this.onClickListener.onClick(v);
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<customSortItem> getCustomItems() {
        return this.customItems;
    }

    public void setCustomItems(ArrayList<customSortItem> customItems) {
        this.customItems = customItems;
    }

    public LayoutInflater getInflater() {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        }
        return this.inflater;
    }
}
