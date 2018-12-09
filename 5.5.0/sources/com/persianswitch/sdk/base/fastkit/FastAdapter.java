package com.persianswitch.sdk.base.fastkit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public abstract class FastAdapter<T, V extends FastViewHolder> extends BaseAdapter {
    /* renamed from: a */
    protected List<T> f7048a;
    /* renamed from: b */
    private final Context f7049b;

    protected FastAdapter(Context context, List<T> list) {
        this.f7049b = context;
        this.f7048a = list;
    }

    /* renamed from: a */
    public Context m10621a() {
        return this.f7049b;
    }

    /* renamed from: a */
    protected abstract V mo3306a(Context context, ViewGroup viewGroup);

    /* renamed from: a */
    protected abstract void mo3307a(V v, int i);

    /* renamed from: b */
    public List<T> m10624b() {
        return this.f7048a;
    }

    public int getCount() {
        return this.f7048a.size();
    }

    public T getItem(int i) {
        return this.f7048a.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        FastViewHolder a;
        if (view == null) {
            a = mo3306a(this.f7049b, viewGroup);
            view = a.m10630a();
            view.setTag(a);
        } else {
            a = (FastViewHolder) view.getTag();
        }
        mo3307a(a, i);
        return view;
    }
}
