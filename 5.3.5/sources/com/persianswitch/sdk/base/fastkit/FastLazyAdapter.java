package com.persianswitch.sdk.base.fastkit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.persianswitch.sdk.base.log.SDKLog;
import java.util.List;
import java.util.Set;

public abstract class FastLazyAdapter<T, V extends FastViewHolder> extends FastAdapter<T, V> {
    private static final String TAG = "FastLazyAdapter";
    private View emptyView;
    private volatile boolean inProgress;
    private View pendingView;

    protected abstract View getEmptyView(ViewGroup viewGroup);

    protected abstract View getPendingView(ViewGroup viewGroup);

    public FastLazyAdapter(Context context, List<T> entities) {
        super(context, (List) entities);
    }

    public FastLazyAdapter(Context context, Set<T> entitiesSet) {
        super(context, (Set) entitiesSet);
    }

    public int getCount() {
        if (this.inProgress) {
            return super.getCount() + 1;
        }
        if (this.inProgress || super.getCount() != 0) {
            return super.getCount();
        }
        return 1;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        SDKLog.m35d(TAG, "inProgress:%b, position :%d", Boolean.valueOf(this.inProgress), Integer.valueOf(position));
        if (this.inProgress && position == super.getCount()) {
            if (this.pendingView == null) {
                this.pendingView = getPendingView(viewGroup);
            }
            SDKLog.m35d(TAG, "show pending view", new Object[0]);
            return this.pendingView;
        } else if (this.inProgress || super.getCount() != 0) {
            return super.getView(position, view, viewGroup);
        } else {
            if (this.emptyView == null) {
                this.emptyView = getEmptyView(viewGroup);
            }
            SDKLog.m35d(TAG, "show empty view", new Object[0]);
            return this.emptyView;
        }
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
        notifyDataSetChanged();
    }
}
