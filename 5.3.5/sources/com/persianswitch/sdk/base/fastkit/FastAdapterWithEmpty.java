package com.persianswitch.sdk.base.fastkit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class FastAdapterWithEmpty<T, V extends FastViewHolder> extends FastAdapter<T, V> {
    protected abstract View getEmptyView(ViewGroup viewGroup);

    protected FastAdapterWithEmpty(Context context, List<T> entities) {
        super(context, (List) entities);
    }

    public int getCount() {
        if (getEntities().size() == 0) {
            return 1;
        }
        return super.getCount();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getEntities().size() == 0 && i == 0) {
            return getEmptyView(viewGroup);
        }
        return super.getView(i, view, viewGroup);
    }
}
