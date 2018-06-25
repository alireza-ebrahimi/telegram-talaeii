package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemIdComposer;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

public class RecyclerViewSwipeManager implements SwipeableItemConstants {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MIN_DISTANCE_TOUCH_SLOP_MUL = 5;
    private static final int SLIDE_ITEM_IMMEDIATELY_SET_TRANSLATION_THRESHOLD_DP = 8;
    private static final String TAG = "ARVSwipeManager";
    private long mCheckingTouchSlop = -1;
    private InternalHandler mHandler;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C06061();
    private ItemSlidingAnimator mItemSlideAnimator;
    private OnItemSwipeEventListener mItemSwipeEventListener;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mLongPressTimeout = ViewConfiguration.getLongPressTimeout();
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private long mMoveToOutsideWindowAnimationDuration = 200;
    private long mMoveToSpecifiedPositionAnimationDuration = 200;
    private RecyclerView mRecyclerView;
    private long mReturnToDefaultPositionAnimationDuration = 300;
    private boolean mSwipeHorizontal;
    private int mSwipeThresholdDistance;
    private ViewHolder mSwipingItem;
    private long mSwipingItemId = -1;
    private final Rect mSwipingItemMargins = new Rect();
    private SwipingItemOperator mSwipingItemOperator;
    private int mSwipingItemPosition = -1;
    private int mSwipingItemReactionType;
    private int mTouchSlop;
    private int mTouchedItemOffsetX;
    private int mTouchedItemOffsetY;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private SwipeableItemWrapperAdapter<ViewHolder> mWrapperAdapter;

    /* renamed from: com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager$1 */
    class C06061 implements OnItemTouchListener {
        C06061() {
        }

        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return RecyclerViewSwipeManager.this.onInterceptTouchEvent(rv, e);
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            RecyclerViewSwipeManager.this.onTouchEvent(rv, e);
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            RecyclerViewSwipeManager.this.onRequestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private static class InternalHandler extends Handler {
        private static final int MSG_DEFERRED_CANCEL_SWIPE = 2;
        private static final int MSG_LONGPRESS = 1;
        private MotionEvent mDownMotionEvent;
        private RecyclerViewSwipeManager mHolder;

        public InternalHandler(RecyclerViewSwipeManager holder) {
            this.mHolder = holder;
        }

        public void release() {
            removeCallbacksAndMessages(null);
            this.mHolder = null;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.mHolder.handleOnLongPress(this.mDownMotionEvent);
                    return;
                case 2:
                    this.mHolder.cancelSwipe(true);
                    return;
                default:
                    return;
            }
        }

        public void startLongPressDetection(MotionEvent e, int timeout) {
            cancelLongPressDetection();
            this.mDownMotionEvent = MotionEvent.obtain(e);
            sendEmptyMessageAtTime(1, e.getDownTime() + ((long) timeout));
        }

        public void cancelLongPressDetection() {
            removeMessages(1);
            if (this.mDownMotionEvent != null) {
                this.mDownMotionEvent.recycle();
                this.mDownMotionEvent = null;
            }
        }

        public void removeDeferredCancelSwipeRequest() {
            removeMessages(2);
        }

        public void requestDeferredCancelSwipe() {
            if (!isCancelSwipeRequested()) {
                sendEmptyMessage(2);
            }
        }

        public boolean isCancelSwipeRequested() {
            return hasMessages(2);
        }
    }

    public interface OnItemSwipeEventListener {
        void onItemSwipeFinished(int i, int i2, int i3);

        void onItemSwipeStarted(int i);
    }

    public Adapter createWrappedAdapter(@NonNull Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("The passed adapter does not support stable IDs");
        } else if (this.mWrapperAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        } else {
            this.mWrapperAdapter = new SwipeableItemWrapperAdapter(this, adapter);
            return this.mWrapperAdapter;
        }
    }

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    public void attachRecyclerView(@NonNull RecyclerView rv) {
        boolean z = true;
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        } else if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        } else {
            int layoutOrientation = CustomRecyclerViewUtils.getOrientation(rv);
            if (layoutOrientation == -1) {
                throw new IllegalStateException("failed to determine layout orientation");
            }
            this.mRecyclerView = rv;
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            ViewConfiguration vc = ViewConfiguration.get(rv.getContext());
            this.mTouchSlop = vc.getScaledTouchSlop();
            this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
            this.mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
            this.mSwipeThresholdDistance = this.mTouchSlop * 5;
            this.mItemSlideAnimator = new ItemSlidingAnimator(this.mWrapperAdapter);
            this.mItemSlideAnimator.setImmediatelySetTranslationThreshold((int) ((rv.getResources().getDisplayMetrics().density * 8.0f) + 0.5f));
            if (layoutOrientation != 1) {
                z = false;
            }
            this.mSwipeHorizontal = z;
            this.mHandler = new InternalHandler(this);
        }
    }

    public void release() {
        cancelSwipe(true);
        if (this.mHandler != null) {
            this.mHandler.release();
            this.mHandler = null;
        }
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mItemSlideAnimator != null) {
            this.mItemSlideAnimator.endAnimations();
            this.mItemSlideAnimator = null;
        }
        this.mWrapperAdapter = null;
        this.mRecyclerView = null;
    }

    public boolean isSwiping() {
        return (this.mSwipingItem == null || this.mHandler.isCancelSwipeRequested()) ? false : true;
    }

    public void setLongPressTimeout(int longPressTimeout) {
        this.mLongPressTimeout = longPressTimeout;
    }

    public void setSwipeThresholdDistance(int distanceInPixels) {
        this.mSwipeThresholdDistance = Math.max(distanceInPixels, this.mTouchSlop);
    }

    public int getSwipeThresholdDistance() {
        return this.mSwipeThresholdDistance;
    }

    boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        switch (MotionEventCompat.getActionMasked(e)) {
            case 0:
                if (!isSwiping()) {
                    handleActionDown(rv, e);
                    break;
                }
                break;
            case 1:
            case 3:
                if (handleActionUpOrCancel(e, true)) {
                    return true;
                }
                break;
            case 2:
                if (isSwiping()) {
                    handleActionMoveWhileSwiping(e);
                    return true;
                } else if (handleActionMoveWhileNotSwiping(rv, e)) {
                    return true;
                }
                break;
        }
        return false;
    }

    void onTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = MotionEventCompat.getActionMasked(e);
        if (isSwiping()) {
            switch (action) {
                case 1:
                case 3:
                    handleActionUpOrCancel(e, true);
                    return;
                case 2:
                    handleActionMoveWhileSwiping(e);
                    return;
                default:
                    return;
            }
        }
    }

    void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            cancelSwipe(true);
        }
    }

    private boolean handleActionDown(RecyclerView rv, MotionEvent e) {
        ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY());
        if (!(holder instanceof SwipeableItemViewHolder)) {
            return false;
        }
        int wrappedItemPosition = getWrappedItemPosition(holder);
        if (wrappedItemPosition < 0 || wrappedItemPosition >= this.mWrapperAdapter.getItemCount()) {
            return false;
        }
        if (ItemIdComposer.extractWrappedIdPart(holder.getItemId()) != ItemIdComposer.extractWrappedIdPart(this.mWrapperAdapter.getItemId(wrappedItemPosition))) {
            return false;
        }
        int touchX = (int) (e.getX() + 0.5f);
        int touchY = (int) (e.getY() + 0.5f);
        View view = holder.itemView;
        int reactionType = this.mWrapperAdapter.getSwipeReactionType(holder, wrappedItemPosition, touchX - (view.getLeft() + ((int) (ViewCompat.getTranslationX(view) + 0.5f))), touchY - (view.getTop() + ((int) (ViewCompat.getTranslationY(view) + 0.5f))));
        if (reactionType == 0) {
            return false;
        }
        this.mInitialTouchX = touchX;
        this.mInitialTouchY = touchY;
        this.mCheckingTouchSlop = holder.getItemId();
        this.mSwipingItemReactionType = reactionType;
        if ((16777216 & reactionType) != 0) {
            this.mHandler.startLongPressDetection(e, this.mLongPressTimeout);
        }
        return true;
    }

    private boolean handleActionUpOrCancel(MotionEvent e, boolean invokeFinish) {
        int action = 3;
        if (e != null) {
            action = MotionEventCompat.getActionMasked(e);
            this.mLastTouchX = (int) (e.getX() + 0.5f);
            this.mLastTouchY = (int) (e.getY() + 0.5f);
        }
        if (isSwiping()) {
            if (invokeFinish) {
                handleActionUpOrCancelWhileSwiping(action);
            }
            return true;
        }
        handleActionUpOrCancelWhileNotSwiping();
        return false;
    }

    private void handleActionUpOrCancelWhileNotSwiping() {
        if (this.mHandler != null) {
            this.mHandler.cancelLongPressDetection();
        }
        this.mCheckingTouchSlop = -1;
        this.mSwipingItemReactionType = 0;
    }

    private void handleActionUpOrCancelWhileSwiping(int action) {
        int result = 1;
        if (action == 1) {
            float swipeThresholdVelocity = (float) this.mMinFlingVelocity;
            boolean horizontal = this.mSwipeHorizontal;
            SwipeableItemViewHolder holder = this.mSwipingItem;
            View containerView = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
            int containerSize = horizontal ? containerView.getWidth() : containerView.getHeight();
            float distance = horizontal ? (float) (this.mLastTouchX - this.mInitialTouchX) : (float) (this.mLastTouchY - this.mInitialTouchY);
            float absDistance = Math.abs(distance);
            boolean canSwipeNegativeDir = horizontal ? SwipeReactionUtils.canSwipeLeft(this.mSwipingItemReactionType) : SwipeReactionUtils.canSwipeUp(this.mSwipingItemReactionType);
            boolean canSwipePositiveDir = horizontal ? SwipeReactionUtils.canSwipeRight(this.mSwipingItemReactionType) : SwipeReactionUtils.canSwipeDown(this.mSwipingItemReactionType);
            boolean proportional = holder.isProportionalSwipeAmountModeEnabled();
            float negativeDirLimit = horizontal ? holder.getMaxLeftSwipeAmount() : holder.getMaxUpSwipeAmount();
            float positiveDirLimit = horizontal ? holder.getMaxRightSwipeAmount() : holder.getMaxDownSwipeAmount();
            negativeDirLimit = adaptAmount(holder, horizontal, negativeDirLimit, proportional, false);
            positiveDirLimit = adaptAmount(holder, horizontal, positiveDirLimit, proportional, false);
            if (isSpecialSwipeAmountValue(negativeDirLimit)) {
                negativeDirLimit = (float) (-containerSize);
            }
            if (isSpecialSwipeAmountValue(positiveDirLimit)) {
                positiveDirLimit = (float) containerSize;
            }
            this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaxFlingVelocity);
            float velocity = horizontal ? this.mVelocityTracker.getXVelocity() : this.mVelocityTracker.getYVelocity();
            float absVelocity = Math.abs(velocity);
            boolean swiped = false;
            boolean positiveDir = false;
            if (absDistance > ((float) this.mSwipeThresholdDistance)) {
                if (absVelocity >= swipeThresholdVelocity) {
                    if (velocity * distance >= 0.0f) {
                        swiped = true;
                        positiveDir = velocity > 0.0f;
                    }
                } else if (distance < 0.0f && distance <= 0.8f * negativeDirLimit) {
                    swiped = true;
                    positiveDir = false;
                } else if (distance > 0.0f && distance >= 0.8f * positiveDirLimit) {
                    swiped = true;
                    positiveDir = true;
                }
            }
            if (swiped) {
                if (!positiveDir && canSwipeNegativeDir) {
                    result = horizontal ? 2 : 3;
                } else if (positiveDir && canSwipePositiveDir) {
                    result = horizontal ? 4 : 5;
                }
            }
        }
        finishSwiping(result);
    }

    private boolean handleActionMoveWhileNotSwiping(RecyclerView rv, MotionEvent e) {
        if (this.mCheckingTouchSlop == -1) {
            return false;
        }
        int scrollAxisDelta;
        int swipeAxisDelta;
        int dx = ((int) (e.getX() + 0.5f)) - this.mInitialTouchX;
        int dy = ((int) (e.getY() + 0.5f)) - this.mInitialTouchY;
        if (this.mSwipeHorizontal) {
            scrollAxisDelta = dy;
            swipeAxisDelta = dx;
        } else {
            scrollAxisDelta = dx;
            swipeAxisDelta = dy;
        }
        if (Math.abs(scrollAxisDelta) > this.mTouchSlop) {
            this.mCheckingTouchSlop = -1;
            return false;
        } else if (Math.abs(swipeAxisDelta) <= this.mTouchSlop) {
            return false;
        } else {
            boolean dirMasked;
            if (this.mSwipeHorizontal) {
                if (swipeAxisDelta >= 0) {
                    dirMasked = (this.mSwipingItemReactionType & 32768) != 0;
                } else if ((this.mSwipingItemReactionType & 8) != 0) {
                    dirMasked = true;
                } else {
                    dirMasked = false;
                }
            } else if (swipeAxisDelta < 0) {
                dirMasked = (this.mSwipingItemReactionType & 512) != 0;
            } else {
                dirMasked = (this.mSwipingItemReactionType & 2097152) != 0;
            }
            if (dirMasked) {
                this.mCheckingTouchSlop = -1;
                return false;
            }
            ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY());
            if (holder != null && holder.getItemId() == this.mCheckingTouchSlop) {
                return checkConditionAndStartSwiping(e, holder);
            }
            this.mCheckingTouchSlop = -1;
            return false;
        }
    }

    private void handleActionMoveWhileSwiping(MotionEvent e) {
        this.mLastTouchX = (int) (e.getX() + 0.5f);
        this.mLastTouchY = (int) (e.getY() + 0.5f);
        this.mVelocityTracker.addMovement(e);
        int swipeDistanceX = this.mLastTouchX - this.mTouchedItemOffsetX;
        int swipeDistanceY = this.mLastTouchY - this.mTouchedItemOffsetY;
        this.mSwipingItemOperator.update(getSwipingItemPosition(), swipeDistanceX, swipeDistanceY);
    }

    private boolean checkConditionAndStartSwiping(MotionEvent e, ViewHolder holder) {
        int wrappedItemPosition = getWrappedItemPosition(holder);
        if (wrappedItemPosition == -1) {
            return false;
        }
        startSwiping(e, holder, wrappedItemPosition);
        return true;
    }

    private void startSwiping(MotionEvent e, ViewHolder holder, int itemPosition) {
        this.mHandler.cancelLongPressDetection();
        this.mSwipingItem = holder;
        this.mSwipingItemPosition = itemPosition;
        this.mSwipingItemId = this.mWrapperAdapter.getItemId(itemPosition);
        this.mLastTouchX = (int) (e.getX() + 0.5f);
        this.mLastTouchY = (int) (e.getY() + 0.5f);
        this.mTouchedItemOffsetX = this.mLastTouchX;
        this.mTouchedItemOffsetY = this.mLastTouchY;
        this.mCheckingTouchSlop = -1;
        CustomRecyclerViewUtils.getLayoutMargins(holder.itemView, this.mSwipingItemMargins);
        this.mSwipingItemOperator = new SwipingItemOperator(this, this.mSwipingItem, this.mSwipingItemReactionType, this.mSwipeHorizontal);
        this.mSwipingItemOperator.start();
        this.mVelocityTracker.clear();
        this.mVelocityTracker.addMovement(e);
        this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        if (this.mItemSwipeEventListener != null) {
            this.mItemSwipeEventListener.onItemSwipeStarted(itemPosition);
        }
        this.mWrapperAdapter.onSwipeItemStarted(this, holder, this.mSwipingItemId);
    }

    private void finishSwiping(int result) {
        ViewHolder swipingItem = this.mSwipingItem;
        if (swipingItem != null) {
            this.mHandler.removeDeferredCancelSwipeRequest();
            this.mHandler.cancelLongPressDetection();
            if (!(this.mRecyclerView == null || this.mRecyclerView.getParent() == null)) {
                this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(false);
            }
            int itemPosition = getSwipingItemPosition();
            this.mVelocityTracker.clear();
            this.mSwipingItem = null;
            this.mSwipingItemPosition = -1;
            this.mSwipingItemId = -1;
            this.mLastTouchX = 0;
            this.mLastTouchY = 0;
            this.mInitialTouchX = 0;
            this.mTouchedItemOffsetX = 0;
            this.mTouchedItemOffsetY = 0;
            this.mCheckingTouchSlop = -1;
            this.mSwipingItemReactionType = 0;
            if (this.mSwipingItemOperator != null) {
                this.mSwipingItemOperator.finish();
                this.mSwipingItemOperator = null;
            }
            int slideDir = resultCodeToSlideDirection(result);
            SwipeResultAction resultAction = null;
            if (this.mWrapperAdapter != null) {
                resultAction = this.mWrapperAdapter.onSwipeItemFinished(swipingItem, itemPosition, result);
            }
            if (resultAction == null) {
                resultAction = new SwipeResultActionDefault();
            }
            int afterReaction = resultAction.getResultActionType();
            verifyAfterReaction(result, afterReaction);
            boolean slideAnimated = false;
            switch (afterReaction) {
                case 0:
                    slideAnimated = this.mItemSlideAnimator.finishSwipeSlideToDefaultPosition(swipingItem, this.mSwipeHorizontal, true, this.mReturnToDefaultPositionAnimationDuration, itemPosition, resultAction);
                    break;
                case 1:
                    ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
                    long removeAnimationDuration = itemAnimator != null ? itemAnimator.getRemoveDuration() : 0;
                    if (supportsViewPropertyAnimator()) {
                        RemovingItemDecorator decorator = new RemovingItemDecorator(this.mRecyclerView, swipingItem, result, removeAnimationDuration, itemAnimator != null ? itemAnimator.getMoveDuration() : 0);
                        decorator.setMoveAnimationInterpolator(SwipeDismissItemAnimator.MOVE_INTERPOLATOR);
                        decorator.start();
                    }
                    slideAnimated = this.mItemSlideAnimator.finishSwipeSlideToOutsideOfWindow(swipingItem, slideDir, true, removeAnimationDuration, itemPosition, resultAction);
                    break;
                case 2:
                    slideAnimated = this.mItemSlideAnimator.finishSwipeSlideToOutsideOfWindow(swipingItem, slideDir, true, this.mMoveToOutsideWindowAnimationDuration, itemPosition, resultAction);
                    break;
                case 3:
                    break;
                default:
                    throw new IllegalStateException("Unknown after reaction type: " + afterReaction);
            }
            if (this.mWrapperAdapter != null) {
                this.mWrapperAdapter.onSwipeItemFinished2(swipingItem, itemPosition, result, afterReaction, resultAction);
            }
            if (this.mItemSwipeEventListener != null) {
                this.mItemSwipeEventListener.onItemSwipeFinished(itemPosition, result, afterReaction);
            }
            if (!slideAnimated) {
                resultAction.slideAnimationEnd();
            }
        }
    }

    private static void verifyAfterReaction(int result, int afterReaction) {
        if (afterReaction == 2 || afterReaction == 1) {
            switch (result) {
                case 2:
                case 3:
                case 4:
                case 5:
                    return;
                default:
                    throw new IllegalStateException("Unexpected after reaction has been requested: result = " + result + ", afterReaction = " + afterReaction);
            }
        }
    }

    private static int resultCodeToSlideDirection(int result) {
        switch (result) {
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            default:
                return 0;
        }
    }

    static int getItemPosition(@Nullable Adapter adapter, long itemId, int itemPositionGuess) {
        if (adapter == null) {
            return -1;
        }
        int itemCount = adapter.getItemCount();
        if (itemPositionGuess >= 0 && itemPositionGuess < itemCount && adapter.getItemId(itemPositionGuess) == itemId) {
            return itemPositionGuess;
        }
        for (int i = 0; i < itemCount; i++) {
            if (adapter.getItemId(i) == itemId) {
                return i;
            }
        }
        return -1;
    }

    public void cancelSwipe() {
        cancelSwipe(false);
    }

    public boolean performFakeSwipe(ViewHolder holder, int result) {
        if (!(holder instanceof SwipeableItemViewHolder)) {
            return false;
        }
        if (isSwiping()) {
            return false;
        }
        switch (result) {
            case 1:
                break;
            case 2:
            case 4:
                if (!this.mSwipeHorizontal) {
                    return false;
                }
                break;
            case 3:
            case 5:
                if (this.mSwipeHorizontal) {
                    return false;
                }
                break;
            default:
                return false;
        }
        int wrappedItemPosition = getWrappedItemPosition(holder);
        if (wrappedItemPosition == -1) {
            return false;
        }
        MotionEvent fakeMotionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0);
        startSwiping(fakeMotionEvent, holder, wrappedItemPosition);
        fakeMotionEvent.recycle();
        int direction = 0;
        if (result == 2 || result == 3) {
            direction = -1;
        } else if (result == 4 || result == 5) {
            direction = 1;
        }
        applySlideItem(holder, wrappedItemPosition, 0.0f, (float) direction, false, this.mSwipeHorizontal, false, true);
        finishSwiping(result);
        return true;
    }

    void cancelSwipe(boolean immediately) {
        handleActionUpOrCancel(null, false);
        if (immediately) {
            finishSwiping(1);
        } else if (isSwiping()) {
            this.mHandler.requestDeferredCancelSwipe();
        }
    }

    boolean isAnimationRunning(ViewHolder item) {
        return this.mItemSlideAnimator != null && this.mItemSlideAnimator.isRunning(item);
    }

    private void slideItem(ViewHolder holder, float amount, boolean proportionalAmount, boolean horizontal, boolean shouldAnimate) {
        if (amount == -65536.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 0, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
        } else if (amount == -65537.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 1, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
        } else if (amount == 65536.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 2, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
        } else if (amount == 65537.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 3, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
        } else if (amount == 0.0f) {
            this.mItemSlideAnimator.slideToDefaultPosition(holder, horizontal, shouldAnimate, this.mReturnToDefaultPositionAnimationDuration);
        } else {
            this.mItemSlideAnimator.slideToSpecifiedPosition(holder, amount, proportionalAmount, horizontal, shouldAnimate, this.mMoveToSpecifiedPositionAnimationDuration);
        }
    }

    private int getWrappedItemPosition(ViewHolder holder) {
        return WrapperAdapterUtils.unwrapPosition(this.mRecyclerView.getAdapter(), this.mWrapperAdapter, CustomRecyclerViewUtils.getSynchronizedPosition(holder));
    }

    public long getReturnToDefaultPositionAnimationDuration() {
        return this.mReturnToDefaultPositionAnimationDuration;
    }

    public void setReturnToDefaultPositionAnimationDuration(long duration) {
        this.mReturnToDefaultPositionAnimationDuration = duration;
    }

    public long getMoveToSpecifiedPositionAnimationDuration() {
        return this.mMoveToSpecifiedPositionAnimationDuration;
    }

    public void setMoveToSpecifiedPositionAnimationDuration(long duration) {
        this.mMoveToSpecifiedPositionAnimationDuration = duration;
    }

    public long getMoveToOutsideWindowAnimationDuration() {
        return this.mMoveToOutsideWindowAnimationDuration;
    }

    public void setMoveToOutsideWindowAnimationDuration(long duration) {
        this.mMoveToOutsideWindowAnimationDuration = duration;
    }

    @Nullable
    public OnItemSwipeEventListener getOnItemSwipeEventListener() {
        return this.mItemSwipeEventListener;
    }

    public void setOnItemSwipeEventListener(@Nullable OnItemSwipeEventListener listener) {
        this.mItemSwipeEventListener = listener;
    }

    boolean swipeHorizontal() {
        return this.mSwipeHorizontal;
    }

    void applySlideItem(ViewHolder holder, int itemPosition, float prevAmount, float amount, boolean proportionalAmount, boolean horizontal, boolean shouldAnimate, boolean isSwiping) {
        SwipeableItemViewHolder holder2 = (SwipeableItemViewHolder) holder;
        if (SwipeableViewHolderUtils.getSwipeableContainerView(holder2) != null) {
            int reqBackgroundType;
            if (amount != 0.0f) {
                reqBackgroundType = determineBackgroundType(amount, horizontal);
            } else if (prevAmount == 0.0f) {
                reqBackgroundType = 0;
            } else {
                reqBackgroundType = determineBackgroundType(prevAmount, horizontal);
            }
            float adjustedAmount = amount;
            if (amount != 0.0f) {
                boolean isLimitProportional = holder2.isProportionalSwipeAmountModeEnabled();
                adjustedAmount = Math.min(Math.max(adjustedAmount, adaptAmount(holder2, horizontal, horizontal ? holder2.getMaxLeftSwipeAmount() : holder2.getMaxUpSwipeAmount(), isLimitProportional, proportionalAmount)), adaptAmount(holder2, horizontal, horizontal ? holder2.getMaxRightSwipeAmount() : holder2.getMaxDownSwipeAmount(), isLimitProportional, proportionalAmount));
            }
            slideItem(holder, adjustedAmount, proportionalAmount, horizontal, shouldAnimate);
            this.mWrapperAdapter.onUpdateSlideAmount(holder, itemPosition, amount, proportionalAmount, horizontal, isSwiping, reqBackgroundType);
        }
    }

    private static int determineBackgroundType(float amount, boolean horizontal) {
        return horizontal ? amount < 0.0f ? 1 : 3 : amount < 0.0f ? 2 : 4;
    }

    void cancelPendingAnimations(ViewHolder holder) {
        if (this.mItemSlideAnimator != null) {
            this.mItemSlideAnimator.endAnimation(holder);
        }
    }

    int getSwipeContainerViewTranslationX(ViewHolder holder) {
        return this.mItemSlideAnimator.getSwipeContainerViewTranslationX(holder);
    }

    int getSwipeContainerViewTranslationY(ViewHolder holder) {
        return this.mItemSlideAnimator.getSwipeContainerViewTranslationY(holder);
    }

    void handleOnLongPress(MotionEvent e) {
        ViewHolder holder = this.mRecyclerView.findViewHolderForItemId(this.mCheckingTouchSlop);
        if (holder != null) {
            checkConditionAndStartSwiping(e, holder);
        }
    }

    int getSwipingItemPosition() {
        return this.mSwipingItemPosition;
    }

    int syncSwipingItemPosition() {
        return syncSwipingItemPosition(this.mSwipingItemPosition);
    }

    int syncSwipingItemPosition(int positionGuess) {
        this.mSwipingItemPosition = getItemPosition(this.mWrapperAdapter, this.mSwipingItemId, positionGuess);
        return this.mSwipingItemPosition;
    }

    static float adaptAmount(SwipeableItemViewHolder holder, boolean horizontal, float srcAmount, boolean isSrcProportional, boolean isDestProportional) {
        float destAmount = srcAmount;
        if ((isSrcProportional ^ isDestProportional) == 0 || srcAmount == 0.0f || isSpecialSwipeAmountValue(srcAmount)) {
            return destAmount;
        }
        View v = SwipeableViewHolderUtils.getSwipeableContainerView(holder);
        float d = horizontal ? (float) v.getWidth() : (float) v.getHeight();
        if (isDestProportional) {
            d = d != 0.0f ? 1.0f / d : 0.0f;
        }
        return destAmount * d;
    }

    private static boolean isSpecialSwipeAmountValue(float amount) {
        return amount == -65536.0f || amount == 65536.0f || amount == -65537.0f || amount == 65537.0f;
    }

    private static boolean supportsViewPropertyAnimator() {
        return VERSION.SDK_INT >= 11;
    }
}
