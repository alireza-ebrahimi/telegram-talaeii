package com.persianswitch.sdk.base.fastkit;

import android.view.View;

public abstract class FastViewHolder {
    private final View viewItem;

    public FastViewHolder(View viewItem) {
        this.viewItem = viewItem;
    }

    public View getViewItem() {
        return this.viewItem;
    }

    public final View findViewById(int id) {
        return this.viewItem.findViewById(id);
    }
}
