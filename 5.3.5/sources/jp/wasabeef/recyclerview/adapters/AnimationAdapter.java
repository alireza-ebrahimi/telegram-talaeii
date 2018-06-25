package jp.wasabeef.recyclerview.adapters;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import jp.wasabeef.recyclerview.internal.ViewHelper;
import org.telegram.customization.Activities.ScheduleDownloadActivity;

public abstract class AnimationAdapter extends Adapter<ViewHolder> {
    private boolean isFirstOnly = true;
    private Adapter<ViewHolder> mAdapter;
    private int mDuration = ScheduleDownloadActivity.CHECK_CELL2;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = -1;

    protected abstract Animator[] getAnimators(View view);

    public AnimationAdapter(Adapter<ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.mAdapter.onCreateViewHolder(parent, viewType);
    }

    public void registerAdapterDataObserver(AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        this.mAdapter.registerAdapterDataObserver(observer);
    }

    public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        this.mAdapter.unregisterAdapterDataObserver(observer);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        this.mAdapter.onBindViewHolder(holder, position);
        int adapterPosition = holder.getAdapterPosition();
        if (!this.isFirstOnly || adapterPosition > this.mLastPosition) {
            for (Animator anim : getAnimators(holder.itemView)) {
                anim.setDuration((long) this.mDuration).start();
                anim.setInterpolator(this.mInterpolator);
            }
            this.mLastPosition = adapterPosition;
            return;
        }
        ViewHelper.clear(holder.itemView);
    }

    public void onViewRecycled(ViewHolder holder) {
        this.mAdapter.onViewRecycled(holder);
        super.onViewRecycled(holder);
    }

    public int getItemCount() {
        return this.mAdapter.getItemCount();
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void setStartPosition(int start) {
        this.mLastPosition = start;
    }

    public void setFirstOnly(boolean firstOnly) {
        this.isFirstOnly = firstOnly;
    }

    public int getItemViewType(int position) {
        return this.mAdapter.getItemViewType(position);
    }

    public Adapter<ViewHolder> getWrappedAdapter() {
        return this.mAdapter;
    }

    public long getItemId(int position) {
        return this.mAdapter.getItemId(position);
    }
}
