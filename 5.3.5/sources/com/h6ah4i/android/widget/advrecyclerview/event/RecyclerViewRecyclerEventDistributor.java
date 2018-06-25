package com.h6ah4i.android.widget.advrecyclerview.event;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.RecyclerListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.lang.ref.WeakReference;

public class RecyclerViewRecyclerEventDistributor extends BaseRecyclerViewEventDistributor<RecyclerListener> {
    private InternalRecyclerListener mInternalRecyclerListener = new InternalRecyclerListener(this);

    private static class InternalRecyclerListener implements RecyclerListener {
        private final WeakReference<RecyclerViewRecyclerEventDistributor> mRefDistributor;

        public InternalRecyclerListener(RecyclerViewRecyclerEventDistributor distributor) {
            this.mRefDistributor = new WeakReference(distributor);
        }

        public void onViewRecycled(ViewHolder holder) {
            RecyclerViewRecyclerEventDistributor distributor = (RecyclerViewRecyclerEventDistributor) this.mRefDistributor.get();
            if (distributor != null) {
                distributor.handleOnViewRecycled(holder);
            }
        }

        public void release() {
            this.mRefDistributor.clear();
        }
    }

    protected void onRecyclerViewAttached(RecyclerView rv) {
        super.onRecyclerViewAttached(rv);
        rv.setRecyclerListener(this.mInternalRecyclerListener);
    }

    protected void onRelease() {
        super.onRelease();
        if (this.mInternalRecyclerListener != null) {
            this.mInternalRecyclerListener.release();
            this.mInternalRecyclerListener = null;
        }
    }

    void handleOnViewRecycled(ViewHolder holder) {
        if (this.mListeners != null) {
            for (RecyclerListener listener : this.mListeners) {
                listener.onViewRecycled(holder);
            }
        }
    }
}
