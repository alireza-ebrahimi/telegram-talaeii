package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseItemAnimationManager<T extends ItemAnimationInfo> {
    protected final List<ViewHolder> mActive = new ArrayList();
    protected final List<List<T>> mDeferredReadySets = new ArrayList();
    protected final BaseItemAnimator mItemAnimator;
    protected final List<T> mPending = new ArrayList();

    protected static class BaseAnimatorListener implements ViewPropertyAnimatorListener {
        private ItemAnimationInfo mAnimationInfo;
        private ViewPropertyAnimatorCompat mAnimator;
        private ViewHolder mHolder;
        private BaseItemAnimationManager mManager;

        public BaseAnimatorListener(BaseItemAnimationManager manager, ItemAnimationInfo info, ViewHolder holder, ViewPropertyAnimatorCompat animator) {
            this.mManager = manager;
            this.mAnimationInfo = info;
            this.mHolder = holder;
            this.mAnimator = animator;
        }

        public void onAnimationStart(View view) {
            this.mManager.dispatchStarting(this.mAnimationInfo, this.mHolder);
        }

        public void onAnimationEnd(View view) {
            BaseItemAnimationManager manager = this.mManager;
            ItemAnimationInfo info = this.mAnimationInfo;
            ViewHolder holder = this.mHolder;
            this.mAnimator.setListener(null);
            this.mManager = null;
            this.mAnimationInfo = null;
            this.mHolder = null;
            this.mAnimator = null;
            manager.onAnimationEndedSuccessfully(info, holder);
            manager.dispatchFinished(info, holder);
            info.clear(holder);
            manager.mActive.remove(holder);
            manager.dispatchFinishedWhenDone();
        }

        public void onAnimationCancel(View view) {
            this.mManager.onAnimationCancel(this.mAnimationInfo, this.mHolder);
        }
    }

    public abstract void dispatchFinished(T t, ViewHolder viewHolder);

    public abstract void dispatchStarting(T t, ViewHolder viewHolder);

    protected abstract boolean endNotStartedAnimation(T t, ViewHolder viewHolder);

    public abstract long getDuration();

    protected abstract void onAnimationCancel(T t, ViewHolder viewHolder);

    protected abstract void onAnimationEndedBeforeStarted(T t, ViewHolder viewHolder);

    protected abstract void onAnimationEndedSuccessfully(T t, ViewHolder viewHolder);

    protected abstract void onCreateAnimation(T t);

    public abstract void setDuration(long j);

    public BaseItemAnimationManager(BaseItemAnimator itemAnimator) {
        this.mItemAnimator = itemAnimator;
    }

    protected final boolean debugLogEnabled() {
        return this.mItemAnimator.debugLogEnabled();
    }

    public boolean hasPending() {
        return !this.mPending.isEmpty();
    }

    public boolean isRunning() {
        return (this.mPending.isEmpty() && this.mActive.isEmpty() && this.mDeferredReadySets.isEmpty()) ? false : true;
    }

    public boolean removeFromActive(ViewHolder item) {
        return this.mActive.remove(item);
    }

    public void cancelAllStartedAnimations() {
        List<ViewHolder> active = this.mActive;
        for (int i = active.size() - 1; i >= 0; i--) {
            ViewCompat.animate(((ViewHolder) active.get(i)).itemView).cancel();
        }
    }

    public void runPendingAnimations(boolean deferred, long deferredDelay) {
        final List<T> ready = new ArrayList();
        ready.addAll(this.mPending);
        this.mPending.clear();
        if (deferred) {
            this.mDeferredReadySets.add(ready);
            ViewCompat.postOnAnimationDelayed(((ItemAnimationInfo) ready.get(0)).getAvailableViewHolder().itemView, new Runnable() {
                public void run() {
                    for (ItemAnimationInfo info : ready) {
                        BaseItemAnimationManager.this.createAnimation(info);
                    }
                    ready.clear();
                    BaseItemAnimationManager.this.mDeferredReadySets.remove(ready);
                }
            }, deferredDelay);
            return;
        }
        for (T info : ready) {
            createAnimation(info);
        }
        ready.clear();
    }

    public void endPendingAnimations(ViewHolder item) {
        List<T> pending = this.mPending;
        for (int i = pending.size() - 1; i >= 0; i--) {
            if (endNotStartedAnimation((ItemAnimationInfo) pending.get(i), item) && item != null) {
                pending.remove(i);
            }
        }
        if (item == null) {
            pending.clear();
        }
    }

    public void endAllPendingAnimations() {
        endPendingAnimations(null);
    }

    public void endDeferredReadyAnimations(ViewHolder item) {
        for (int i = this.mDeferredReadySets.size() - 1; i >= 0; i--) {
            List<T> ready = (List) this.mDeferredReadySets.get(i);
            for (int j = ready.size() - 1; j >= 0; j--) {
                if (endNotStartedAnimation((ItemAnimationInfo) ready.get(j), item) && item != null) {
                    ready.remove(j);
                }
            }
            if (item == null) {
                ready.clear();
            }
            if (ready.isEmpty()) {
                this.mDeferredReadySets.remove(ready);
            }
        }
    }

    public void endAllDeferredReadyAnimations() {
        endDeferredReadyAnimations(null);
    }

    void createAnimation(T info) {
        onCreateAnimation(info);
    }

    protected void endAnimation(ViewHolder holder) {
        this.mItemAnimator.endAnimation(holder);
    }

    protected void resetAnimation(ViewHolder holder) {
        AnimatorCompatHelper.clearInterpolator(holder.itemView);
        endAnimation(holder);
    }

    protected void dispatchFinishedWhenDone() {
        this.mItemAnimator.dispatchFinishedWhenDone();
    }

    protected void enqueuePendingAnimationInfo(T info) {
        if (info == null) {
            throw new IllegalStateException("info is null");
        }
        this.mPending.add(info);
    }

    protected void startActiveItemAnimation(T info, ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        animator.setListener(new BaseAnimatorListener(this, info, holder, animator));
        addActiveAnimationTarget(holder);
        animator.start();
    }

    private void addActiveAnimationTarget(ViewHolder item) {
        if (item == null) {
            throw new IllegalStateException("item is null");
        }
        this.mActive.add(item);
    }
}
