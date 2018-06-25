package com.persianswitch.sdk.base.fastkit;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class FastFilterableAdapter<T extends FilterableModel, V extends FastViewHolder> extends FastAdapter<T, V> implements Filterable {
    private final DefaultFilterImp defaultFilterImp = new DefaultFilterImp();
    private AtomicBoolean mFilterEnabled = new AtomicBoolean(true);
    private final Object mLock = new Object();

    private class DefaultFilterImp extends Filter {
        private List<T> originalEntities;

        private DefaultFilterImp() {
        }

        protected FilterResults performFiltering(CharSequence prefix) {
            prefix = FastFilterableAdapter.this.getFilterOnKey(prefix);
            FilterResults results = new FilterResults();
            if (this.originalEntities == null) {
                synchronized (FastFilterableAdapter.this.mLock) {
                    this.originalEntities = new ArrayList(FastFilterableAdapter.this.entities);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                ArrayList<T> list;
                synchronized (FastFilterableAdapter.this.mLock) {
                    list = new ArrayList(this.originalEntities);
                }
                results.values = list;
                results.count = list.size();
            } else {
                ArrayList<T> values;
                String prefixString = prefix.toString().toLowerCase();
                synchronized (FastFilterableAdapter.this.mLock) {
                    values = new ArrayList(this.originalEntities);
                }
                int count = values.size();
                ArrayList<T> newValues = new ArrayList();
                for (int i = 0; i < count; i++) {
                    FilterableModel value = (FilterableModel) values.get(i);
                    String valueText = value.filterOn().toLowerCase();
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        for (String startsWith : valueText.split(" ")) {
                            if (startsWith.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            FastFilterableAdapter.this.entities = (List) results.values;
            if (results.count > 0) {
                FastFilterableAdapter.this.notifyDataSetChanged();
            } else {
                FastFilterableAdapter.this.notifyDataSetInvalidated();
            }
        }
    }

    protected FastFilterableAdapter(Context context, List<T> entities) {
        super(context, (List) entities);
    }

    public int getCount() {
        if (this.entities == null) {
            return 0;
        }
        return super.getCount();
    }

    public boolean isFilterEnabled() {
        return this.mFilterEnabled.get();
    }

    public void setFilterEnabled(boolean val) {
        this.mFilterEnabled.set(val);
    }

    private CharSequence getFilterOnKey(CharSequence prefix) {
        return isFilterEnabled() ? prefix : "";
    }

    public Filter getFilter() {
        return this.defaultFilterImp;
    }
}
