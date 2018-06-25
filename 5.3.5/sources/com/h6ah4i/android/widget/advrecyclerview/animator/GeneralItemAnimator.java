package com.h6ah4i.android.widget.advrecyclerview.animator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemAddAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemChangeAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemMoveAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemRemoveAnimationManager;

public abstract class GeneralItemAnimator extends BaseItemAnimator {
    private static final String TAG = "ARVGeneralItemAnimator";
    private ItemAddAnimationManager mAddAnimationsManager;
    private ItemChangeAnimationManager mChangeAnimationsManager;
    private boolean mDebug;
    private ItemMoveAnimationManager mMoveAnimationsManager;
    private ItemRemoveAnimationManager mRemoveAnimationManager;

    protected abstract void onSetup();

    protected GeneralItemAnimator() {
        setup();
    }

    private void setup() {
        onSetup();
        if (this.mRemoveAnimationManager == null || this.mAddAnimationsManager == null || this.mChangeAnimationsManager == null || this.mMoveAnimationsManager == null) {
            throw new IllegalStateException("setup incomplete");
        }
    }

    public void runPendingAnimations() {
        if (hasPendingAnimations()) {
            onSchedulePendingAnimations();
        }
    }

    public boolean animateRemove(ViewHolder holder) {
        if (this.mDebug) {
            Log.d(TAG, "animateRemove(id = " + holder.getItemId() + ", position = " + holder.getLayoutPosition() + ")");
        }
        return this.mRemoveAnimationManager.addPendingAnimation(holder);
    }

    public boolean animateAdd(ViewHolder holder) {
        if (this.mDebug) {
            Log.d(TAG, "animateAdd(id = " + holder.getItemId() + ", position = " + holder.getLayoutPosition() + ")");
        }
        return this.mAddAnimationsManager.addPendingAnimation(holder);
    }

    public boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        if (this.mDebug) {
            Log.d(TAG, "animateMove(id = " + holder.getItemId() + ", position = " + holder.getLayoutPosition() + ", fromX = " + fromX + ", fromY = " + fromY + ", toX = " + toX + ", toY = " + toY + ")");
        }
        return this.mMoveAnimationsManager.addPendingAnimation(holder, fromX, fromY, toX, toY);
    }

    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        if (oldHolder == newHolder) {
            return this.mMoveAnimationsManager.addPendingAnimation(oldHolder, fromX, fromY, toX, toY);
        }
        if (this.mDebug) {
            String oldId = oldHolder != null ? Long.toString(oldHolder.getItemId()) : "-";
            String oldPosition = oldHolder != null ? Long.toString((long) oldHolder.getLayoutPosition()) : "-";
            Log.d(TAG, "animateChange(old.id = " + oldId + ", old.position = " + oldPosition + ", new.id = " + (newHolder != null ? Long.toString(newHolder.getItemId()) : "-") + ", new.position = " + (newHolder != null ? Long.toString((long) newHolder.getLayoutPosition()) : "-") + ", fromX = " + fromX + ", fromY = " + fromY + ", toX = " + toX + ", toY = " + toY + ")");
        }
        return this.mChangeAnimationsManager.addPendingAnimation(oldHolder, newHolder, fromX, fromY, toX, toY);
    }

    protected void cancelAnimations(ViewHolder item) {
        ViewCompat.animate(item.itemView).cancel();
    }

    public void endAnimation(ViewHolder item) {
        cancelAnimations(item);
        this.mMoveAnimationsManager.endPendingAnimations(item);
        this.mChangeAnimationsManager.endPendingAnimations(item);
        this.mRemoveAnimationManager.endPendingAnimations(item);
        this.mAddAnimationsManager.endPendingAnimations(item);
        this.mMoveAnimationsManager.endDeferredReadyAnimations(item);
        this.mChangeAnimationsManager.endDeferredReadyAnimations(item);
        this.mRemoveAnimationManager.endDeferredReadyAnimations(item);
        this.mAddAnimationsManager.endDeferredReadyAnimations(item);
        if (this.mRemoveAnimationManager.removeFromActive(item) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [remove]");
        } else if (this.mAddAnimationsManager.removeFromActive(item) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [add]");
        } else if (this.mChangeAnimationsManager.removeFromActive(item) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [change]");
        } else if (this.mMoveAnimationsManager.removeFromActive(item) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [move]");
        } else {
            dispatchFinishedWhenDone();
        }
    }

    public boolean isRunning() {
        return this.mRemoveAnimationManager.isRunning() || this.mAddAnimationsManager.isRunning() || this.mChangeAnimationsManager.isRunning() || this.mMoveAnimationsManager.isRunning();
    }

    public void endAnimations() {
        this.mMoveAnimationsManager.endAllPendingAnimations();
        this.mRemoveAnimationManager.endAllPendingAnimations();
        this.mAddAnimationsManager.endAllPendingAnimations();
        this.mChangeAnimationsManager.endAllPendingAnimations();
        if (isRunning()) {
            this.mMoveAnimationsManager.endAllDeferredReadyAnimations();
            this.mAddAnimationsManager.endAllDeferredReadyAnimations();
            this.mChangeAnimationsManager.endAllDeferredReadyAnimations();
            this.mRemoveAnimationManager.cancelAllStartedAnimations();
            this.mMoveAnimationsManager.cancelAllStartedAnimations();
            this.mAddAnimationsManager.cancelAllStartedAnimations();
            this.mChangeAnimationsManager.cancelAllStartedAnimations();
            dispatchAnimationsFinished();
        }
    }

    public boolean debugLogEnabled() {
        return this.mDebug;
    }

    public boolean dispatchFinishedWhenDone() {
        if (this.mDebug && !isRunning()) {
            Log.d(TAG, "dispatchFinishedWhenDone()");
        }
        return super.dispatchFinishedWhenDone();
    }

    protected boolean hasPendingAnimations() {
        return this.mRemoveAnimationManager.hasPending() || this.mMoveAnimationsManager.hasPending() || this.mChangeAnimationsManager.hasPending() || this.mAddAnimationsManager.hasPending();
    }

    protected ItemRemoveAnimationManager getRemoveAnimationManager() {
        return this.mRemoveAnimationManager;
    }

    protected void setItemRemoveAnimationManager(ItemRemoveAnimationManager removeAnimationManager) {
        this.mRemoveAnimationManager = removeAnimationManager;
    }

    protected ItemAddAnimationManager getItemAddAnimationsManager() {
        return this.mAddAnimationsManager;
    }

    protected void setItemAddAnimationsManager(ItemAddAnimationManager addAnimationsManager) {
        this.mAddAnimationsManager = addAnimationsManager;
    }

    protected ItemChangeAnimationManager getItemChangeAnimationsManager() {
        return this.mChangeAnimationsManager;
    }

    protected void setItemChangeAnimationsManager(ItemChangeAnimationManager changeAnimationsManager) {
        this.mChangeAnimationsManager = changeAnimationsManager;
    }

    protected ItemMoveAnimationManager getItemMoveAnimationsManager() {
        return this.mMoveAnimationsManager;
    }

    protected void setItemMoveAnimationsManager(ItemMoveAnimationManager moveAnimationsManager) {
        this.mMoveAnimationsManager = moveAnimationsManager;
    }

    public boolean isDebug() {
        return this.mDebug;
    }

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    protected void onSchedulePendingAnimations() {
        schedulePendingAnimationsByDefaultRule();
    }

    protected void schedulePendingAnimationsByDefaultRule() {
        boolean removalsPending = this.mRemoveAnimationManager.hasPending();
        boolean movesPending = this.mMoveAnimationsManager.hasPending();
        boolean changesPending = this.mChangeAnimationsManager.hasPending();
        boolean additionsPending = this.mAddAnimationsManager.hasPending();
        long removeDuration = removalsPending ? getRemoveDuration() : 0;
        long moveDuration = movesPending ? getMoveDuration() : 0;
        long changeDuration = changesPending ? getChangeDuration() : 0;
        if (removalsPending) {
            this.mRemoveAnimationManager.runPendingAnimations(false, 0);
        }
        if (movesPending) {
            this.mMoveAnimationsManager.runPendingAnimations(removalsPending, removeDuration);
        }
        if (changesPending) {
            this.mChangeAnimationsManager.runPendingAnimations(removalsPending, removeDuration);
        }
        if (additionsPending) {
            boolean deferred = removalsPending || movesPending || changesPending;
            this.mAddAnimationsManager.runPendingAnimations(deferred, deferred ? removeDuration + Math.max(moveDuration, changeDuration) : 0);
        }
    }
}
