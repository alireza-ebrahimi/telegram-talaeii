package com.h6ah4i.android.widget.advrecyclerview.animator;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SimpleItemAnimator;

public abstract class BaseItemAnimator extends SimpleItemAnimator {
    private ItemAnimatorListener mListener;

    public interface ItemAnimatorListener {
        void onAddFinished(ViewHolder viewHolder);

        void onChangeFinished(ViewHolder viewHolder);

        void onMoveFinished(ViewHolder viewHolder);

        void onRemoveFinished(ViewHolder viewHolder);
    }

    public void setListener(ItemAnimatorListener listener) {
        this.mListener = listener;
    }

    public final void onAddStarting(ViewHolder item) {
        onAddStartingImpl(item);
    }

    public final void onAddFinished(ViewHolder item) {
        onAddFinishedImpl(item);
        if (this.mListener != null) {
            this.mListener.onAddFinished(item);
        }
    }

    public final void onChangeStarting(ViewHolder item, boolean oldItem) {
        onChangeStartingItem(item, oldItem);
    }

    public final void onChangeFinished(ViewHolder item, boolean oldItem) {
        onChangeFinishedImpl(item, oldItem);
        if (this.mListener != null) {
            this.mListener.onChangeFinished(item);
        }
    }

    public final void onMoveStarting(ViewHolder item) {
        onMoveStartingImpl(item);
    }

    public final void onMoveFinished(ViewHolder item) {
        onMoveFinishedImpl(item);
        if (this.mListener != null) {
            this.mListener.onMoveFinished(item);
        }
    }

    public final void onRemoveStarting(ViewHolder item) {
        onRemoveStartingImpl(item);
    }

    public final void onRemoveFinished(ViewHolder item) {
        onRemoveFinishedImpl(item);
        if (this.mListener != null) {
            this.mListener.onRemoveFinished(item);
        }
    }

    protected void onAddStartingImpl(ViewHolder item) {
    }

    protected void onAddFinishedImpl(ViewHolder item) {
    }

    protected void onChangeStartingItem(ViewHolder item, boolean oldItem) {
    }

    protected void onChangeFinishedImpl(ViewHolder item, boolean oldItem) {
    }

    protected void onMoveStartingImpl(ViewHolder item) {
    }

    protected void onMoveFinishedImpl(ViewHolder item) {
    }

    protected void onRemoveStartingImpl(ViewHolder item) {
    }

    protected void onRemoveFinishedImpl(ViewHolder item) {
    }

    public boolean dispatchFinishedWhenDone() {
        if (isRunning()) {
            return false;
        }
        dispatchAnimationsFinished();
        return true;
    }

    public boolean debugLogEnabled() {
        return false;
    }
}
