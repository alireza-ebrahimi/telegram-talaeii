package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.util.List;

class SwipeableItemWrapperAdapter<VH extends ViewHolder> extends BaseWrapperAdapter<VH> {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVSwipeableWrapper";
    private RecyclerViewSwipeManager mSwipeManager;
    private SwipeableItemAdapter mSwipeableItemAdapter;
    private long mSwipingItemId = -1;

    private interface Constants extends SwipeableItemConstants {
    }

    public SwipeableItemWrapperAdapter(RecyclerViewSwipeManager manager, Adapter<VH> adapter) {
        super(adapter);
        this.mSwipeableItemAdapter = (SwipeableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, SwipeableItemAdapter.class);
        if (this.mSwipeableItemAdapter == null) {
            throw new IllegalArgumentException("adapter does not implement SwipeableItemAdapter");
        } else if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        } else {
            this.mSwipeManager = manager;
        }
    }

    protected void onRelease() {
        super.onRelease();
        this.mSwipeableItemAdapter = null;
        this.mSwipeManager = null;
        this.mSwipingItemId = -1;
    }

    public void onViewRecycled(VH holder, int viewType) {
        super.onViewRecycled(holder, viewType);
        if (this.mSwipingItemId != -1 && this.mSwipingItemId == holder.getItemId()) {
            this.mSwipeManager.cancelSwipe();
        }
        if (holder instanceof SwipeableItemViewHolder) {
            if (this.mSwipeManager != null) {
                this.mSwipeManager.cancelPendingAnimations(holder);
            }
            SwipeableItemViewHolder swipeableHolder = (SwipeableItemViewHolder) holder;
            swipeableHolder.setSwipeResult(0);
            swipeableHolder.setAfterSwipeReaction(0);
            swipeableHolder.setSwipeItemHorizontalSlideAmount(0.0f);
            swipeableHolder.setSwipeItemVerticalSlideAmount(0.0f);
            swipeableHolder.setProportionalSwipeAmountModeEnabled(true);
            View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(swipeableHolder);
            if (containerView != null) {
                ViewCompat.animate(containerView).cancel();
                ViewCompat.setTranslationX(containerView, 0.0f);
                ViewCompat.setTranslationY(containerView, 0.0f);
            }
        }
    }

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH holder = super.onCreateViewHolder(parent, viewType);
        if (holder instanceof SwipeableItemViewHolder) {
            ((SwipeableItemViewHolder) holder).setSwipeStateFlags(-1);
        }
        return holder;
    }

    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        float prevSwipeItemSlideAmount = 0.0f;
        SwipeableItemViewHolder swipeableHolder = holder instanceof SwipeableItemViewHolder ? (SwipeableItemViewHolder) holder : null;
        if (swipeableHolder != null) {
            prevSwipeItemSlideAmount = getSwipeItemSlideAmount((SwipeableItemViewHolder) holder, swipeHorizontal());
        }
        if (isSwiping()) {
            int flags = 1;
            if (holder.getItemId() == this.mSwipingItemId) {
                flags = 1 | 2;
            }
            safeUpdateFlags(holder, flags);
            super.onBindViewHolder(holder, position, payloads);
        } else {
            safeUpdateFlags(holder, 0);
            super.onBindViewHolder(holder, position, payloads);
        }
        if (swipeableHolder != null) {
            float swipeItemSlideAmount = getSwipeItemSlideAmount(swipeableHolder, swipeHorizontal());
            boolean proportionalAmount = swipeableHolder.isProportionalSwipeAmountModeEnabled();
            boolean isSwiping = this.mSwipeManager.isSwiping();
            boolean isAnimationRunning = this.mSwipeManager.isAnimationRunning(holder);
            if (prevSwipeItemSlideAmount != swipeItemSlideAmount || (!isSwiping && !isAnimationRunning)) {
                this.mSwipeManager.applySlideItem(holder, position, prevSwipeItemSlideAmount, swipeItemSlideAmount, proportionalAmount, swipeHorizontal(), true, isSwiping);
            }
        }
    }

    protected void onHandleWrappedAdapterChanged() {
        if (isSwiping()) {
            cancelSwipe();
        }
        super.onHandleWrappedAdapterChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        super.onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount, payload);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        if (isSwiping()) {
            int pos = this.mSwipeManager.getSwipingItemPosition();
            if (pos >= positionStart) {
                this.mSwipeManager.syncSwipingItemPosition(pos + itemCount);
            }
        }
        super.onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        if (isSwiping()) {
            int pos = this.mSwipeManager.getSwipingItemPosition();
            if (checkInRange(pos, positionStart, itemCount)) {
                cancelSwipe();
            } else if (positionStart < pos) {
                this.mSwipeManager.syncSwipingItemPosition(pos - itemCount);
            }
        }
        super.onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (isSwiping()) {
            this.mSwipeManager.syncSwipingItemPosition();
        }
        super.onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
    }

    private void cancelSwipe() {
        if (this.mSwipeManager != null) {
            this.mSwipeManager.cancelSwipe();
        }
    }

    int getSwipeReactionType(ViewHolder holder, int position, int x, int y) {
        return this.mSwipeableItemAdapter.onGetSwipeReactionType(holder, position, x, y);
    }

    void onUpdateSlideAmount(ViewHolder holder, int position, float amount, boolean proportionalAmount, boolean horizontal, boolean isSwiping, int type) {
        this.mSwipeableItemAdapter.onSetSwipeBackground(holder, position, type);
        onUpdateSlideAmount(holder, position, amount, proportionalAmount, horizontal, isSwiping);
    }

    void onUpdateSlideAmount(ViewHolder holder, int position, float amount, boolean proportionalAmount, boolean horizontal, boolean isSwiping) {
        float f;
        SwipeableItemViewHolder holder2 = (SwipeableItemViewHolder) holder;
        float adaptedAmount = RecyclerViewSwipeManager.adaptAmount(holder2, horizontal, amount, proportionalAmount, holder2.isProportionalSwipeAmountModeEnabled());
        if (horizontal) {
            f = adaptedAmount;
        } else {
            f = 0.0f;
        }
        if (horizontal) {
            adaptedAmount = 0.0f;
        }
        holder2.onSlideAmountUpdated(f, adaptedAmount, isSwiping);
    }

    void onSwipeItemStarted(RecyclerViewSwipeManager manager, ViewHolder holder, long id) {
        this.mSwipingItemId = id;
        notifyDataSetChanged();
    }

    SwipeResultAction onSwipeItemFinished(ViewHolder holder, int position, int result) {
        this.mSwipingItemId = -1;
        return this.mSwipeableItemAdapter.onSwipeItem(holder, position, result);
    }

    void onSwipeItemFinished2(ViewHolder holder, int position, int result, int afterReaction, SwipeResultAction resultAction) {
        ((SwipeableItemViewHolder) holder).setSwipeResult(result);
        ((SwipeableItemViewHolder) holder).setAfterSwipeReaction(afterReaction);
        if (afterReaction != 3) {
            setSwipeItemSlideAmount((SwipeableItemViewHolder) holder, getSwipeAmountFromAfterReaction(result, afterReaction), swipeHorizontal());
        }
        resultAction.performAction();
        notifyDataSetChanged();
    }

    protected boolean isSwiping() {
        return this.mSwipingItemId != -1;
    }

    private static boolean checkInRange(int pos, int start, int count) {
        return pos >= start && pos < start + count;
    }

    private boolean swipeHorizontal() {
        return this.mSwipeManager.swipeHorizontal();
    }

    private static float getSwipeItemSlideAmount(SwipeableItemViewHolder holder, boolean horizontal) {
        if (horizontal) {
            return holder.getSwipeItemHorizontalSlideAmount();
        }
        return holder.getSwipeItemVerticalSlideAmount();
    }

    private static void setSwipeItemSlideAmount(SwipeableItemViewHolder holder, float amount, boolean horizontal) {
        if (horizontal) {
            holder.setSwipeItemHorizontalSlideAmount(amount);
        } else {
            holder.setSwipeItemVerticalSlideAmount(amount);
        }
    }

    private static float getSwipeAmountFromAfterReaction(int result, int afterReaction) {
        switch (afterReaction) {
            case 1:
            case 2:
                switch (result) {
                    case 2:
                        return -65536.0f;
                    case 3:
                        return -65537.0f;
                    case 4:
                        return 65536.0f;
                    case 5:
                        return 65537.0f;
                    default:
                        return 0.0f;
                }
            default:
                return 0.0f;
        }
    }

    private static void safeUpdateFlags(ViewHolder holder, int flags) {
        if (holder instanceof SwipeableItemViewHolder) {
            int curFlags = ((SwipeableItemViewHolder) holder).getSwipeStateFlags();
            if (curFlags == -1 || ((curFlags ^ flags) & Integer.MAX_VALUE) != 0) {
                flags |= Integer.MIN_VALUE;
            }
            ((SwipeableItemViewHolder) holder).setSwipeStateFlags(flags);
        }
    }
}
