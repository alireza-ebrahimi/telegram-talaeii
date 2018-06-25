package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.v7.widget.RecyclerView.Adapter;

public class UnwrapPositionResult {
    public Adapter adapter;
    public int position = -1;
    public Object tag;

    public void clear() {
        this.adapter = null;
        this.tag = null;
        this.position = -1;
    }

    public boolean isValid() {
        return (this.adapter == null || this.position == -1) ? false : true;
    }
}
