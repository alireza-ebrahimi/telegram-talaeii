package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

class SwapTargetItemOperator extends BaseDraggableItemDecorator {
    private static final ViewPropertyAnimatorListener RESET_TRANSLATION_LISTENER = new C06031();
    private static final String TAG = "SwapTargetItemOperator";
    private float mCurTranslationPhase;
    private final Rect mDraggingItemDecorationOffsets = new Rect();
    private DraggingItemInfo mDraggingItemInfo;
    private float mReqTranslationPhase;
    private boolean mStarted;
    private final Rect mSwapTargetDecorationOffsets = new Rect();
    private ViewHolder mSwapTargetItem;
    private boolean mSwapTargetItemChanged;
    private final Rect mSwapTargetItemMargins = new Rect();
    private Interpolator mSwapTargetTranslationInterpolator;
    private int mTranslationX;
    private int mTranslationY;

    /* renamed from: com.h6ah4i.android.widget.advrecyclerview.draggable.SwapTargetItemOperator$1 */
    static class C06031 implements ViewPropertyAnimatorListener {
        C06031() {
        }

        public void onAnimationStart(View view) {
        }

        public void onAnimationEnd(View view) {
            ViewCompat.animate(view).setListener(null);
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
        }

        public void onAnimationCancel(View view) {
        }
    }

    public SwapTargetItemOperator(RecyclerView recyclerView, ViewHolder draggingItem, DraggingItemInfo draggingItemInfo) {
        super(recyclerView, draggingItem);
        this.mDraggingItemInfo = draggingItemInfo;
        CustomRecyclerViewUtils.getDecorationOffsets(this.mRecyclerView.getLayoutManager(), this.mDraggingItemViewHolder.itemView, this.mDraggingItemDecorationOffsets);
    }

    private static float calculateCurrentTranslationPhase(float cur, float req) {
        float tmp = (0.7f * cur) + (0.3f * req);
        return Math.abs(tmp - req) < 0.01f ? req : tmp;
    }

    public void setSwapTargetTranslationInterpolator(Interpolator interpolator) {
        this.mSwapTargetTranslationInterpolator = interpolator;
    }

    public void setSwapTargetItem(ViewHolder swapTargetItem) {
        if (this.mSwapTargetItem != swapTargetItem) {
            if (this.mSwapTargetItem != null) {
                ViewPropertyAnimatorCompat animator = ViewCompat.animate(this.mSwapTargetItem.itemView);
                animator.cancel();
                animator.setDuration(10).translationX(0.0f).translationY(0.0f).setListener(RESET_TRANSLATION_LISTENER).start();
            }
            this.mSwapTargetItem = swapTargetItem;
            if (this.mSwapTargetItem != null) {
                ViewCompat.animate(this.mSwapTargetItem.itemView).cancel();
            }
            this.mSwapTargetItemChanged = true;
        }
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        ViewHolder draggingItem = this.mDraggingItemViewHolder;
        ViewHolder swapTargetItem = this.mSwapTargetItem;
        if (draggingItem != null && swapTargetItem != null && draggingItem.getItemId() == this.mDraggingItemInfo.id) {
            this.mReqTranslationPhase = calculateTranslationPhase(draggingItem, swapTargetItem);
            if (this.mSwapTargetItemChanged) {
                this.mSwapTargetItemChanged = false;
                this.mCurTranslationPhase = this.mReqTranslationPhase;
            } else {
                this.mCurTranslationPhase = calculateCurrentTranslationPhase(this.mCurTranslationPhase, this.mReqTranslationPhase);
            }
            updateSwapTargetTranslation(draggingItem, swapTargetItem, this.mCurTranslationPhase);
        }
    }

    private float calculateTranslationPhase(ViewHolder draggingItem, ViewHolder swapTargetItem) {
        View swapItemView = swapTargetItem.itemView;
        int pos1 = draggingItem.getLayoutPosition();
        int pos2 = swapTargetItem.getLayoutPosition();
        CustomRecyclerViewUtils.getDecorationOffsets(this.mRecyclerView.getLayoutManager(), swapItemView, this.mSwapTargetDecorationOffsets);
        CustomRecyclerViewUtils.getLayoutMargins(swapItemView, this.mSwapTargetItemMargins);
        Rect m2 = this.mSwapTargetItemMargins;
        Rect d2 = this.mSwapTargetDecorationOffsets;
        int h2 = (((swapItemView.getHeight() + m2.top) + m2.bottom) + d2.top) + d2.bottom;
        int w2 = (((swapItemView.getWidth() + m2.left) + m2.right) + d2.left) + d2.right;
        float phaseX = w2 != 0 ? ((float) (draggingItem.itemView.getLeft() - this.mTranslationX)) / ((float) w2) : 0.0f;
        float phaseY = h2 != 0 ? ((float) (draggingItem.itemView.getTop() - this.mTranslationY)) / ((float) h2) : 0.0f;
        float translationPhase = 0.0f;
        int orientation = CustomRecyclerViewUtils.getOrientation(this.mRecyclerView);
        if (orientation == 1) {
            if (pos1 > pos2) {
                translationPhase = phaseY;
            } else {
                translationPhase = 1.0f + phaseY;
            }
        } else if (orientation == 0) {
            if (pos1 > pos2) {
                translationPhase = phaseX;
            } else {
                translationPhase = 1.0f + phaseX;
            }
        }
        return Math.min(Math.max(translationPhase, 0.0f), 1.0f);
    }

    private void updateSwapTargetTranslation(ViewHolder draggingItem, ViewHolder swapTargetItem, float translationPhase) {
        View swapItemView = swapTargetItem.itemView;
        int pos1 = draggingItem.getLayoutPosition();
        int pos2 = swapTargetItem.getLayoutPosition();
        Rect m1 = this.mDraggingItemInfo.margins;
        Rect d1 = this.mDraggingItemDecorationOffsets;
        int h1 = (((this.mDraggingItemInfo.height + m1.top) + m1.bottom) + d1.top) + d1.bottom;
        int w1 = (((this.mDraggingItemInfo.width + m1.left) + m1.right) + d1.left) + d1.right;
        if (this.mSwapTargetTranslationInterpolator != null) {
            translationPhase = this.mSwapTargetTranslationInterpolator.getInterpolation(translationPhase);
        }
        switch (CustomRecyclerViewUtils.getOrientation(this.mRecyclerView)) {
            case 0:
                if (pos1 > pos2) {
                    ViewCompat.setTranslationX(swapItemView, ((float) w1) * translationPhase);
                    return;
                } else {
                    ViewCompat.setTranslationX(swapItemView, (translationPhase - 1.0f) * ((float) w1));
                    return;
                }
            case 1:
                if (pos1 > pos2) {
                    ViewCompat.setTranslationY(swapItemView, ((float) h1) * translationPhase);
                    return;
                } else {
                    ViewCompat.setTranslationY(swapItemView, (translationPhase - 1.0f) * ((float) h1));
                    return;
                }
            default:
                return;
        }
    }

    public void start() {
        if (!this.mStarted) {
            this.mRecyclerView.addItemDecoration(this, 0);
            this.mStarted = true;
        }
    }

    public void finish(boolean animate) {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
        }
        ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        this.mRecyclerView.stopScroll();
        if (this.mSwapTargetItem != null) {
            updateSwapTargetTranslation(this.mDraggingItemViewHolder, this.mSwapTargetItem, this.mCurTranslationPhase);
            moveToDefaultPosition(this.mSwapTargetItem.itemView, 1.0f, 1.0f, 0.0f, 1.0f, animate);
            this.mSwapTargetItem = null;
        }
        this.mDraggingItemViewHolder = null;
        this.mTranslationX = 0;
        this.mTranslationY = 0;
        this.mCurTranslationPhase = 0.0f;
        this.mReqTranslationPhase = 0.0f;
        this.mStarted = false;
        this.mDraggingItemInfo = null;
    }

    public void update(int translationX, int translationY) {
        this.mTranslationX = translationX;
        this.mTranslationY = translationY;
    }

    public void onItemViewRecycled(ViewHolder holder) {
        if (holder == this.mSwapTargetItem) {
            setSwapTargetItem(null);
        }
    }
}
