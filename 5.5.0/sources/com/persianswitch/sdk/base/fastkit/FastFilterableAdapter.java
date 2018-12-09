package com.persianswitch.sdk.base.fastkit;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class FastFilterableAdapter<T extends FilterableModel, V extends FastViewHolder> extends FastAdapter<T, V> implements Filterable {
    /* renamed from: b */
    private final Object f7052b = new Object();
    /* renamed from: c */
    private final DefaultFilterImp f7053c = new DefaultFilterImp();
    /* renamed from: d */
    private AtomicBoolean f7054d = new AtomicBoolean(true);

    private class DefaultFilterImp extends Filter {
        /* renamed from: a */
        final /* synthetic */ FastFilterableAdapter f7050a;
        /* renamed from: b */
        private List<T> f7051b;

        private DefaultFilterImp(FastFilterableAdapter fastFilterableAdapter) {
            this.f7050a = fastFilterableAdapter;
        }

        protected FilterResults performFiltering(CharSequence charSequence) {
            CharSequence a = this.f7050a.m10626a(charSequence);
            FilterResults filterResults = new FilterResults();
            if (this.f7051b == null) {
                synchronized (this.f7050a.f7052b) {
                    this.f7051b = new ArrayList(this.f7050a.a);
                }
            }
            if (a == null || a.length() == 0) {
                ArrayList arrayList;
                synchronized (this.f7050a.f7052b) {
                    arrayList = new ArrayList(this.f7051b);
                }
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
            } else {
                ArrayList arrayList2;
                String toLowerCase = a.toString().toLowerCase();
                synchronized (this.f7050a.f7052b) {
                    arrayList2 = new ArrayList(this.f7051b);
                }
                int size = arrayList2.size();
                ArrayList arrayList3 = new ArrayList();
                for (int i = 0; i < size; i++) {
                    FilterableModel filterableModel = (FilterableModel) arrayList2.get(i);
                    String toLowerCase2 = filterableModel.mo3299a().toLowerCase();
                    if (toLowerCase2.startsWith(toLowerCase)) {
                        arrayList3.add(filterableModel);
                    } else {
                        for (String startsWith : toLowerCase2.split(" ")) {
                            if (startsWith.startsWith(toLowerCase)) {
                                arrayList3.add(filterableModel);
                                break;
                            }
                        }
                    }
                }
                filterResults.values = arrayList3;
                filterResults.count = arrayList3.size();
            }
            return filterResults;
        }

        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            this.f7050a.a = (List) filterResults.values;
            if (filterResults.count > 0) {
                this.f7050a.notifyDataSetChanged();
            } else {
                this.f7050a.notifyDataSetInvalidated();
            }
        }
    }

    protected FastFilterableAdapter(Context context, List<T> list) {
        super(context, list);
    }

    /* renamed from: a */
    private CharSequence m10626a(CharSequence charSequence) {
        return m10629c() ? charSequence : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: a */
    public void m10628a(boolean z) {
        this.f7054d.set(z);
    }

    /* renamed from: c */
    public boolean m10629c() {
        return this.f7054d.get();
    }

    public int getCount() {
        return this.a == null ? 0 : super.getCount();
    }

    public Filter getFilter() {
        return this.f7053c;
    }
}
