package com.persianswitch.sdk.base.fastkit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class FastAdapter<T, V extends FastViewHolder> extends BaseAdapter {
    private final Context context;
    protected List<T> entities;

    protected abstract void onBindViewHolder(V v, int i);

    protected abstract V onCreateViewHolder(Context context, ViewGroup viewGroup);

    protected FastAdapter(Context context, List<T> entities) {
        this.context = context;
        this.entities = entities;
    }

    public FastAdapter(Context context, Set<T> entitiesSet) {
        this.context = context;
        this.entities = new ArrayList();
        this.entities.addAll(entitiesSet);
    }

    public int getCount() {
        return this.entities.size();
    }

    public T getItem(int i) {
        return this.entities.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        V holder;
        if (view == null) {
            holder = onCreateViewHolder(this.context, viewGroup);
            view = holder.getViewItem();
            view.setTag(holder);
        } else {
            FastViewHolder holder2 = (FastViewHolder) view.getTag();
        }
        onBindViewHolder(holder, i);
        return view;
    }

    protected LayoutInflater inflater() {
        return LayoutInflater.from(getContext());
    }

    public Context getContext() {
        return this.context;
    }

    public List<T> getEntities() {
        return this.entities;
    }
}
