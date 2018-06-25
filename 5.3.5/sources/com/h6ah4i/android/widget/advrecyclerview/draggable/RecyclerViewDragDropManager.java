package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.adapter.AdapterPath;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemIdComposer;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class RecyclerViewDragDropManager implements DraggableItemConstants {
    public static final Interpolator DEFAULT_ITEM_SETTLE_BACK_INTO_PLACE_ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
    public static final Interpolator DEFAULT_SWAP_TARGET_TRANSITION_INTERPOLATOR = new BasicSwapTargetTranslationInterpolator();
    public static final int ITEM_MOVE_MODE_DEFAULT = 0;
    public static final int ITEM_MOVE_MODE_SWAP = 1;
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGI = true;
    private static final boolean LOCAL_LOGV = false;
    private static final float SCROLL_AMOUNT_COEFF = 25.0f;
    private static final int SCROLL_DIR_DOWN = 2;
    private static final int SCROLL_DIR_LEFT = 4;
    private static final int SCROLL_DIR_NONE = 0;
    private static final int SCROLL_DIR_RIGHT = 8;
    private static final int SCROLL_DIR_UP = 1;
    private static final float SCROLL_THRESHOLD = 0.3f;
    private static final float SCROLL_TOUCH_SLOP_MULTIPLY = 1.5f;
    private static final String TAG = "ARVDragDropManager";
    private int mActualScrollByXAmount;
    private int mActualScrollByYAmount;
    private boolean mCanDragH;
    private boolean mCanDragV;
    private boolean mCheckCanDrop;
    private final Runnable mCheckItemSwappingRunnable = new C06023();
    private Object mComposedAdapterTag;
    private int mCurrentItemMoveMode = 0;
    private float mDisplayDensity;
    private float mDragEdgeScrollSpeed = 1.0f;
    private int mDragMaxTouchX;
    private int mDragMaxTouchY;
    private int mDragMinTouchX;
    private int mDragMinTouchY;
    private int mDragScrollDistanceX;
    private int mDragScrollDistanceY;
    private int mDragStartTouchX;
    private int mDragStartTouchY;
    private ItemDraggableRange mDraggableRange;
    private DraggingItemDecorator mDraggingItemDecorator;
    private DraggingItemEffectsInfo mDraggingItemEffectsInfo = new DraggingItemEffectsInfo();
    private DraggingItemInfo mDraggingItemInfo;
    ViewHolder mDraggingItemViewHolder;
    private BaseEdgeEffectDecorator mEdgeEffectDecorator;
    private FindSwapTargetContext mFindSwapTargetContext = new FindSwapTargetContext();
    private InternalHandler mHandler;
    private boolean mInScrollByMethod;
    private long mInitialTouchItemId = -1;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private boolean mInitiateOnLongPress;
    private boolean mInitiateOnMove = true;
    private boolean mInitiateOnTouch;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C06001();
    private OnScrollListener mInternalUseOnScrollListener = new C06012();
    private OnItemDragEventListener mItemDragEventListener;
    private int mItemMoveMode = 0;
    private int mItemSettleBackIntoPlaceAnimationDuration = 200;
    private Interpolator mItemSettleBackIntoPlaceAnimationInterpolator = DEFAULT_ITEM_SETTLE_BACK_INTO_PLACE_ANIMATION_INTERPOLATOR;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mLongPressTimeout = ViewConfiguration.getLongPressTimeout();
    private NestedScrollView mNestedScrollView;
    private int mNestedScrollViewScrollX;
    private int mNestedScrollViewScrollY;
    private int mOrigOverScrollMode;
    private RecyclerView mRecyclerView;
    private ItemDraggableRange mRootDraggableRange;
    private int mScrollDirMask = 0;
    private ScrollOnDraggingProcessRunnable mScrollOnDraggingProcess = new ScrollOnDraggingProcessRunnable(this);
    private int mScrollTouchSlop;
    private NinePatchDrawable mShadowDrawable;
    private SwapTargetItemOperator mSwapTargetItemOperator;
    private Interpolator mSwapTargetTranslationInterpolator = DEFAULT_SWAP_TARGET_TRANSITION_INTERPOLATOR;
    private SwapTarget mTempSwapTarget = new SwapTarget();
    private final Rect mTmpRect1 = new Rect();
    private int mTouchSlop;
    private DraggableItemWrapperAdapter mWrapperAdapter;

    /* renamed from: com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager$1 */
    class C06001 implements OnItemTouchListener {
        C06001() {
        }

        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return RecyclerViewDragDropManager.this.onInterceptTouchEvent(rv, e);
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            RecyclerViewDragDropManager.this.onTouchEvent(rv, e);
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            RecyclerViewDragDropManager.this.onRequestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    /* renamed from: com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager$2 */
    class C06012 extends OnScrollListener {
        C06012() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            RecyclerViewDragDropManager.this.onScrollStateChanged(recyclerView, newState);
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            RecyclerViewDragDropManager.this.onScrolled(recyclerView, dx, dy);
        }
    }

    /* renamed from: com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager$3 */
    class C06023 implements Runnable {
        C06023() {
        }

        public void run() {
            if (RecyclerViewDragDropManager.this.mDraggingItemViewHolder != null) {
                RecyclerViewDragDropManager.this.checkItemSwapping(RecyclerViewDragDropManager.this.getRecyclerView());
            }
        }
    }

    static class FindSwapTargetContext {
        public boolean checkCanSwap;
        public ViewHolder draggingItem;
        public DraggingItemInfo draggingItemInfo;
        public int lastTouchX;
        public int lastTouchY;
        public int layoutType;
        public int overlayItemLeft;
        public int overlayItemLeftNotClipped;
        public int overlayItemTop;
        public int overlayItemTopNotClipped;
        public ItemDraggableRange rootAdapterRange;
        public RecyclerView rv;
        public boolean vertical;
        public ItemDraggableRange wrappedAdapterRange;

        FindSwapTargetContext() {
        }

        public void setup(RecyclerView rv, ViewHolder vh, DraggingItemInfo info, int lastTouchX, int lastTouchY, ItemDraggableRange wrappedAdapterPange, ItemDraggableRange rootAdapterRange, boolean checkCanSwap) {
            boolean z = true;
            this.rv = rv;
            this.draggingItemInfo = info;
            this.draggingItem = vh;
            this.lastTouchX = lastTouchX;
            this.lastTouchY = lastTouchY;
            this.wrappedAdapterRange = wrappedAdapterPange;
            this.rootAdapterRange = rootAdapterRange;
            this.checkCanSwap = checkCanSwap;
            this.layoutType = CustomRecyclerViewUtils.getLayoutType(rv);
            if (CustomRecyclerViewUtils.extractOrientation(this.layoutType) != 1) {
                z = false;
            }
            this.vertical = z;
            int i = lastTouchX - info.grabbedPositionX;
            this.overlayItemLeftNotClipped = i;
            this.overlayItemLeft = i;
            i = lastTouchY - info.grabbedPositionY;
            this.overlayItemTopNotClipped = i;
            this.overlayItemTop = i;
            if (this.vertical) {
                this.overlayItemLeft = Math.max(this.overlayItemLeft, rv.getPaddingLeft());
                this.overlayItemLeft = Math.min(this.overlayItemLeft, Math.max(0, (rv.getWidth() - rv.getPaddingRight()) - this.draggingItemInfo.width));
                return;
            }
            this.overlayItemTop = Math.max(this.overlayItemTop, rv.getPaddingTop());
            this.overlayItemTop = Math.min(this.overlayItemTop, Math.max(0, (rv.getHeight() - rv.getPaddingBottom()) - this.draggingItemInfo.height));
        }

        public void clear() {
            this.rv = null;
            this.draggingItemInfo = null;
            this.draggingItem = null;
        }
    }

    private static class InternalHandler extends Handler {
        private static final int MSG_CHECK_ITEM_VIEW_SIZE_UPDATE = 3;
        private static final int MSG_DEFERRED_CANCEL_DRAG = 2;
        private static final int MSG_LONGPRESS = 1;
        private MotionEvent mDownMotionEvent;
        private RecyclerViewDragDropManager mHolder;

        public InternalHandler(RecyclerViewDragDropManager holder) {
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
                    this.mHolder.cancelDrag(true);
                    return;
                case 3:
                    this.mHolder.handleOnCheckItemViewSizeUpdate();
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

        public void removeDeferredCancelDragRequest() {
            removeMessages(2);
        }

        public void requestDeferredCancelDrag() {
            if (!isCancelDragRequested()) {
                sendEmptyMessage(2);
            }
        }

        public boolean isCancelDragRequested() {
            return hasMessages(2);
        }

        public void scheduleDraggingItemViewSizeUpdateCheck() {
            sendEmptyMessage(3);
        }

        public void removeDraggingItemViewSizeUpdateCheckRequest() {
            removeMessages(3);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemMoveMode {
    }

    public interface OnItemDragEventListener {
        void onItemDragFinished(int i, int i2, boolean z);

        void onItemDragMoveDistanceUpdated(int i, int i2);

        void onItemDragPositionChanged(int i, int i2);

        void onItemDragStarted(int i);
    }

    private static class ScrollOnDraggingProcessRunnable implements Runnable {
        private final WeakReference<RecyclerViewDragDropManager> mHolderRef;
        private boolean mStarted;

        public ScrollOnDraggingProcessRunnable(RecyclerViewDragDropManager holder) {
            this.mHolderRef = new WeakReference(holder);
        }

        public void start() {
            if (!this.mStarted) {
                RecyclerViewDragDropManager holder = (RecyclerViewDragDropManager) this.mHolderRef.get();
                if (holder != null) {
                    RecyclerView rv = holder.getRecyclerView();
                    if (rv != null) {
                        ViewCompat.postOnAnimation(rv, this);
                        this.mStarted = true;
                    }
                }
            }
        }

        public void stop() {
            if (this.mStarted) {
                this.mStarted = false;
            }
        }

        public void release() {
            this.mHolderRef.clear();
            this.mStarted = false;
        }

        public void run() {
            RecyclerViewDragDropManager holder = (RecyclerViewDragDropManager) this.mHolderRef.get();
            if (holder != null && this.mStarted) {
                holder.handleScrollOnDragging();
                RecyclerView rv = holder.getRecyclerView();
                if (rv == null || !this.mStarted) {
                    this.mStarted = false;
                } else {
                    ViewCompat.postOnAnimation(rv, this);
                }
            }
        }
    }

    static class SwapTarget {
        public ViewHolder holder;
        public int position;
        public boolean self;

        SwapTarget() {
        }

        public void clear() {
            this.holder = null;
            this.position = -1;
            this.self = false;
        }
    }

    public Adapter createWrappedAdapter(@NonNull Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("The passed adapter does not support stable IDs");
        } else if (this.mWrapperAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        } else {
            this.mWrapperAdapter = new DraggableItemWrapperAdapter(this, adapter);
            return this.mWrapperAdapter;
        }
    }

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    public void attachRecyclerView(@NonNull RecyclerView rv) {
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        } else if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        } else {
            this.mRecyclerView = rv;
            this.mRecyclerView.addOnScrollListener(this.mInternalUseOnScrollListener);
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            this.mDisplayDensity = this.mRecyclerView.getResources().getDisplayMetrics().density;
            this.mTouchSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
            this.mScrollTouchSlop = (int) ((((float) this.mTouchSlop) * SCROLL_TOUCH_SLOP_MULTIPLY) + 0.5f);
            this.mHandler = new InternalHandler(this);
            if (supportsEdgeEffect()) {
                switch (CustomRecyclerViewUtils.getOrientation(this.mRecyclerView)) {
                    case 0:
                        this.mEdgeEffectDecorator = new LeftRightEdgeEffectDecorator(this.mRecyclerView);
                        break;
                    case 1:
                        this.mEdgeEffectDecorator = new TopBottomEdgeEffectDecorator(this.mRecyclerView);
                        break;
                }
                if (this.mEdgeEffectDecorator != null) {
                    this.mEdgeEffectDecorator.start();
                }
            }
        }
    }

    public void release() {
        cancelDrag(true);
        if (this.mHandler != null) {
            this.mHandler.release();
            this.mHandler = null;
        }
        if (this.mEdgeEffectDecorator != null) {
            this.mEdgeEffectDecorator.finish();
            this.mEdgeEffectDecorator = null;
        }
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        if (!(this.mRecyclerView == null || this.mInternalUseOnScrollListener == null)) {
            this.mRecyclerView.removeOnScrollListener(this.mInternalUseOnScrollListener);
        }
        this.mInternalUseOnScrollListener = null;
        if (this.mScrollOnDraggingProcess != null) {
            this.mScrollOnDraggingProcess.release();
            this.mScrollOnDraggingProcess = null;
        }
        this.mWrapperAdapter = null;
        this.mRecyclerView = null;
        this.mSwapTargetTranslationInterpolator = null;
    }

    public boolean isDragging() {
        return (this.mDraggingItemInfo == null || this.mHandler.isCancelDragRequested()) ? false : true;
    }

    public void setDraggingItemShadowDrawable(@Nullable NinePatchDrawable drawable) {
        this.mShadowDrawable = drawable;
    }

    public void setSwapTargetTranslationInterpolator(@Nullable Interpolator interpolator) {
        this.mSwapTargetTranslationInterpolator = interpolator;
    }

    public boolean isInitiateOnLongPressEnabled() {
        return this.mInitiateOnLongPress;
    }

    public void setInitiateOnLongPress(boolean initiateOnLongPress) {
        this.mInitiateOnLongPress = initiateOnLongPress;
    }

    public boolean isInitiateOnMoveEnabled() {
        return this.mInitiateOnMove;
    }

    public void setInitiateOnMove(boolean initiateOnMove) {
        this.mInitiateOnMove = initiateOnMove;
    }

    public boolean isInitiateOnTouchEnabled() {
        return this.mInitiateOnTouch;
    }

    public void setInitiateOnTouch(boolean initiateOnTouch) {
        this.mInitiateOnTouch = initiateOnTouch;
    }

    public void setLongPressTimeout(int longPressTimeout) {
        this.mLongPressTimeout = longPressTimeout;
    }

    public Interpolator setSwapTargetTranslationInterpolator() {
        return this.mSwapTargetTranslationInterpolator;
    }

    @Nullable
    public OnItemDragEventListener getOnItemDragEventListener() {
        return this.mItemDragEventListener;
    }

    public void setOnItemDragEventListener(@Nullable OnItemDragEventListener listener) {
        this.mItemDragEventListener = listener;
    }

    public void setDragEdgeScrollSpeed(float speed) {
        this.mDragEdgeScrollSpeed = Math.min(Math.max(speed, 0.0f), 2.0f);
    }

    public float getDragEdgeScrollSpeed() {
        return this.mDragEdgeScrollSpeed;
    }

    public void setCheckCanDropEnabled(boolean enabled) {
        this.mCheckCanDrop = enabled;
    }

    public boolean isCheckCanDropEnabled() {
        return this.mCheckCanDrop;
    }

    boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = MotionEventCompat.getActionMasked(e);
        switch (action) {
            case 0:
                if (isDragging()) {
                    return false;
                }
                return handleActionDown(rv, e);
            case 1:
            case 3:
                return handleActionUpOrCancel(action, true);
            case 2:
                if (isDragging()) {
                    handleActionMoveWhileDragging(rv, e);
                    return true;
                } else if (handleActionMoveWhileNotDragging(rv, e)) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    void onTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = MotionEventCompat.getActionMasked(e);
        if (isDragging()) {
            switch (action) {
                case 1:
                case 3:
                    handleActionUpOrCancel(action, true);
                    return;
                case 2:
                    handleActionMoveWhileDragging(rv, e);
                    return;
                default:
                    return;
            }
        }
    }

    void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            cancelDrag(true);
        }
    }

    void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (this.mInScrollByMethod) {
            this.mActualScrollByXAmount = dx;
            this.mActualScrollByYAmount = dy;
        } else if (isDragging()) {
            ViewCompat.postOnAnimationDelayed(this.mRecyclerView, this.mCheckItemSwappingRunnable, 500);
        }
    }

    void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == 1) {
            cancelDrag(true);
        }
    }

    private boolean handleActionDown(RecyclerView rv, MotionEvent e) {
        boolean z = true;
        ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(rv, e.getX(), e.getY());
        if (!checkTouchedItemState(rv, holder)) {
            return false;
        }
        int touchX = (int) (e.getX() + 0.5f);
        int touchY = (int) (e.getY() + 0.5f);
        if (!canStartDrag(holder, touchX, touchY)) {
            return false;
        }
        boolean z2;
        boolean handled;
        int orientation = CustomRecyclerViewUtils.getOrientation(this.mRecyclerView);
        int spanCount = CustomRecyclerViewUtils.getSpanCount(this.mRecyclerView);
        this.mLastTouchX = touchX;
        this.mInitialTouchX = touchX;
        this.mLastTouchY = touchY;
        this.mInitialTouchY = touchY;
        this.mInitialTouchItemId = holder.getItemId();
        if (orientation == 0 || (orientation == 1 && spanCount > 1)) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mCanDragH = z2;
        if (orientation != 1 && (orientation != 0 || spanCount <= 1)) {
            z = false;
        }
        this.mCanDragV = z;
        if (this.mInitiateOnTouch) {
            handled = checkConditionAndStartDragging(rv, e, false);
        } else if (this.mInitiateOnLongPress) {
            this.mHandler.startLongPressDetection(e, this.mLongPressTimeout);
            handled = false;
        } else {
            handled = false;
        }
        return handled;
    }

    void handleOnLongPress(MotionEvent e) {
        if (this.mInitiateOnLongPress) {
            checkConditionAndStartDragging(this.mRecyclerView, e, false);
        }
    }

    void handleOnCheckItemViewSizeUpdate() {
        ViewHolder vh = this.mRecyclerView.findViewHolderForItemId(this.mDraggingItemInfo.id);
        if (vh != null) {
            int w = vh.itemView.getWidth();
            int h = vh.itemView.getHeight();
            if (w != this.mDraggingItemInfo.width || h != this.mDraggingItemInfo.height) {
                this.mDraggingItemInfo = DraggingItemInfo.createWithNewView(this.mDraggingItemInfo, vh);
                this.mDraggingItemDecorator.updateDraggingItemView(this.mDraggingItemInfo, vh);
            }
        }
    }

    private void startDragging(RecyclerView rv, MotionEvent e, ViewHolder holder, ItemDraggableRange range, AdapterPath path, int wrappedItemPosition, Object composedAdapterTag) {
        safeEndAnimation(rv, holder);
        this.mHandler.cancelLongPressDetection();
        this.mDraggingItemInfo = new DraggingItemInfo(rv, holder, this.mLastTouchX, this.mLastTouchY);
        this.mDraggingItemViewHolder = holder;
        this.mDraggableRange = range;
        this.mRootDraggableRange = convertToRootAdapterRange(path, this.mDraggableRange);
        NestedScrollView nestedScrollView = findAncestorNestedScrollView(this.mRecyclerView);
        if (nestedScrollView == null || this.mRecyclerView.isNestedScrollingEnabled()) {
            this.mNestedScrollView = null;
        } else {
            this.mNestedScrollView = nestedScrollView;
        }
        this.mOrigOverScrollMode = rv.getOverScrollMode();
        rv.setOverScrollMode(2);
        this.mLastTouchX = (int) (e.getX() + 0.5f);
        this.mLastTouchY = (int) (e.getY() + 0.5f);
        this.mNestedScrollViewScrollX = this.mNestedScrollView != null ? this.mNestedScrollView.getScrollX() : 0;
        this.mNestedScrollViewScrollY = this.mNestedScrollView != null ? this.mNestedScrollView.getScrollY() : 0;
        int i = this.mLastTouchY;
        this.mDragMaxTouchY = i;
        this.mDragMinTouchY = i;
        this.mDragStartTouchY = i;
        i = this.mLastTouchX;
        this.mDragMaxTouchX = i;
        this.mDragMinTouchX = i;
        this.mDragStartTouchX = i;
        this.mScrollDirMask = 0;
        this.mCurrentItemMoveMode = this.mItemMoveMode;
        this.mComposedAdapterTag = composedAdapterTag;
        this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        startScrollOnDraggingProcess();
        this.mWrapperAdapter.onDragItemStarted(this.mDraggingItemInfo, holder, this.mDraggableRange, wrappedItemPosition, this.mCurrentItemMoveMode);
        this.mWrapperAdapter.onBindViewHolder(holder, wrappedItemPosition);
        this.mDraggingItemDecorator = new DraggingItemDecorator(this.mRecyclerView, holder, this.mRootDraggableRange);
        this.mDraggingItemDecorator.setShadowDrawable(this.mShadowDrawable);
        this.mDraggingItemDecorator.setupDraggingItemEffects(this.mDraggingItemEffectsInfo);
        this.mDraggingItemDecorator.start(this.mDraggingItemInfo, this.mLastTouchX, this.mLastTouchY);
        int layoutType = CustomRecyclerViewUtils.getLayoutType(this.mRecyclerView);
        if (supportsViewTranslation() && !this.mCheckCanDrop && CustomRecyclerViewUtils.isLinearLayout(layoutType)) {
            this.mSwapTargetItemOperator = new SwapTargetItemOperator(this.mRecyclerView, holder, this.mDraggingItemInfo);
            this.mSwapTargetItemOperator.setSwapTargetTranslationInterpolator(this.mSwapTargetTranslationInterpolator);
            this.mSwapTargetItemOperator.start();
            this.mSwapTargetItemOperator.update(this.mDraggingItemDecorator.getDraggingItemTranslationX(), this.mDraggingItemDecorator.getDraggingItemTranslationY());
        }
        if (this.mEdgeEffectDecorator != null) {
            this.mEdgeEffectDecorator.reorderToTop();
        }
        if (this.mItemDragEventListener != null) {
            this.mItemDragEventListener.onItemDragStarted(this.mWrapperAdapter.getDraggingItemInitialPosition());
            this.mItemDragEventListener.onItemDragMoveDistanceUpdated(0, 0);
        }
    }

    public int getItemMoveMode() {
        return this.mItemMoveMode;
    }

    public void setItemMoveMode(int mode) {
        this.mItemMoveMode = mode;
    }

    public void cancelDrag() {
        cancelDrag(false);
    }

    void cancelDrag(boolean immediately) {
        handleActionUpOrCancel(3, false);
        if (immediately) {
            finishDragging(false);
        } else if (isDragging()) {
            this.mHandler.requestDeferredCancelDrag();
        }
    }

    private void finishDragging(boolean result) {
        if (isDragging()) {
            if (this.mHandler != null) {
                this.mHandler.removeDeferredCancelDragRequest();
                this.mHandler.removeDraggingItemViewSizeUpdateCheckRequest();
            }
            if (!(this.mRecyclerView == null || this.mDraggingItemViewHolder == null)) {
                this.mRecyclerView.setOverScrollMode(this.mOrigOverScrollMode);
            }
            if (this.mDraggingItemDecorator != null) {
                this.mDraggingItemDecorator.setReturnToDefaultPositionAnimationDuration(this.mItemSettleBackIntoPlaceAnimationDuration);
                this.mDraggingItemDecorator.setReturnToDefaultPositionAnimationInterpolator(this.mItemSettleBackIntoPlaceAnimationInterpolator);
                this.mDraggingItemDecorator.finish(true);
            }
            if (this.mSwapTargetItemOperator != null) {
                this.mSwapTargetItemOperator.setReturnToDefaultPositionAnimationDuration(this.mItemSettleBackIntoPlaceAnimationDuration);
                this.mDraggingItemDecorator.setReturnToDefaultPositionAnimationInterpolator(this.mItemSettleBackIntoPlaceAnimationInterpolator);
                this.mSwapTargetItemOperator.finish(true);
            }
            if (this.mEdgeEffectDecorator != null) {
                this.mEdgeEffectDecorator.releaseBothGlows();
            }
            stopScrollOnDraggingProcess();
            if (!(this.mRecyclerView == null || this.mRecyclerView.getParent() == null)) {
                this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(false);
            }
            if (this.mRecyclerView != null) {
                this.mRecyclerView.invalidate();
            }
            this.mDraggableRange = null;
            this.mRootDraggableRange = null;
            this.mDraggingItemDecorator = null;
            this.mSwapTargetItemOperator = null;
            this.mDraggingItemViewHolder = null;
            this.mDraggingItemInfo = null;
            this.mComposedAdapterTag = null;
            this.mNestedScrollView = null;
            this.mLastTouchX = 0;
            this.mLastTouchY = 0;
            this.mNestedScrollViewScrollX = 0;
            this.mNestedScrollViewScrollY = 0;
            this.mDragStartTouchX = 0;
            this.mDragStartTouchY = 0;
            this.mDragMinTouchX = 0;
            this.mDragMinTouchY = 0;
            this.mDragMaxTouchX = 0;
            this.mDragMaxTouchY = 0;
            this.mDragScrollDistanceX = 0;
            this.mDragScrollDistanceY = 0;
            this.mCanDragH = false;
            this.mCanDragV = false;
            int draggingItemInitialPosition = -1;
            int draggingItemCurrentPosition = -1;
            if (this.mWrapperAdapter != null) {
                draggingItemInitialPosition = this.mWrapperAdapter.getDraggingItemInitialPosition();
                draggingItemCurrentPosition = this.mWrapperAdapter.getDraggingItemCurrentPosition();
                this.mWrapperAdapter.onDragItemFinished(result);
            }
            if (this.mItemDragEventListener != null) {
                this.mItemDragEventListener.onItemDragFinished(draggingItemInitialPosition, draggingItemCurrentPosition, result);
            }
        }
    }

    private boolean handleActionUpOrCancel(int action, boolean invokeFinish) {
        boolean result = true;
        if (action != 1) {
            result = false;
        }
        boolean handled = isDragging();
        if (this.mHandler != null) {
            this.mHandler.cancelLongPressDetection();
        }
        this.mInitialTouchX = 0;
        this.mInitialTouchY = 0;
        this.mLastTouchX = 0;
        this.mLastTouchY = 0;
        this.mDragStartTouchX = 0;
        this.mDragStartTouchY = 0;
        this.mDragMinTouchX = 0;
        this.mDragMinTouchY = 0;
        this.mDragMaxTouchX = 0;
        this.mDragMaxTouchY = 0;
        this.mDragScrollDistanceX = 0;
        this.mDragScrollDistanceY = 0;
        this.mInitialTouchItemId = -1;
        this.mCanDragH = false;
        this.mCanDragV = false;
        if (invokeFinish && isDragging()) {
            finishDragging(result);
        }
        return handled;
    }

    private boolean handleActionMoveWhileNotDragging(RecyclerView rv, MotionEvent e) {
        if (this.mInitiateOnMove) {
            return checkConditionAndStartDragging(rv, e, true);
        }
        return false;
    }

    private boolean checkConditionAndStartDragging(RecyclerView rv, MotionEvent e, boolean checkTouchSlop) {
        if (this.mDraggingItemInfo != null) {
            return false;
        }
        int touchX = (int) (e.getX() + 0.5f);
        int touchY = (int) (e.getY() + 0.5f);
        this.mLastTouchX = touchX;
        this.mLastTouchY = touchY;
        if (this.mInitialTouchItemId == -1) {
            return false;
        }
        if (checkTouchSlop && ((!this.mCanDragH || Math.abs(touchX - this.mInitialTouchX) <= this.mTouchSlop) && (!this.mCanDragV || Math.abs(touchY - this.mInitialTouchY) <= this.mTouchSlop))) {
            return false;
        }
        ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(rv, (float) this.mInitialTouchX, (float) this.mInitialTouchY);
        if (holder == null) {
            return false;
        }
        if (!canStartDrag(holder, touchX, touchY)) {
            return false;
        }
        Adapter rootAdapter = this.mRecyclerView.getAdapter();
        AdapterPath path = new AdapterPath();
        int wrappedItemPosition = WrapperAdapterUtils.unwrapPosition(rootAdapter, this.mWrapperAdapter, null, holder.getAdapterPosition(), path);
        ItemDraggableRange range = this.mWrapperAdapter.getItemDraggableRange(holder, wrappedItemPosition);
        if (range == null) {
            range = new ItemDraggableRange(0, Math.max(0, this.mWrapperAdapter.getItemCount() - 1));
        }
        verifyItemDraggableRange(range, wrappedItemPosition);
        startDragging(rv, e, holder, range, path, wrappedItemPosition, path.lastSegment().tag);
        return true;
    }

    private boolean canStartDrag(ViewHolder holder, int touchX, int touchY) {
        int origRootPosition = holder.getAdapterPosition();
        int wrappedItemPosition = WrapperAdapterUtils.unwrapPosition(this.mRecyclerView.getAdapter(), this.mWrapperAdapter, null, origRootPosition);
        if (wrappedItemPosition == -1) {
            return false;
        }
        View view = holder.itemView;
        if (this.mWrapperAdapter.canStartDrag(holder, wrappedItemPosition, touchX - (view.getLeft() + ((int) (ViewCompat.getTranslationX(view) + 0.5f))), touchY - (view.getTop() + ((int) (ViewCompat.getTranslationY(view) + 0.5f)))) && holder.getAdapterPosition() == origRootPosition) {
            return true;
        }
        return false;
    }

    private void verifyItemDraggableRange(ItemDraggableRange range, int position) {
        int end = Math.max(0, this.mWrapperAdapter.getItemCount() - 1);
        if (range.getStart() > range.getEnd()) {
            throw new IllegalStateException("Invalid wrappedAdapterRange specified --- start > wrappedAdapterRange (wrappedAdapterRange = " + range + ")");
        } else if (range.getStart() < 0) {
            throw new IllegalStateException("Invalid wrappedAdapterRange specified --- start < 0 (wrappedAdapterRange = " + range + ")");
        } else if (range.getEnd() > end) {
            throw new IllegalStateException("Invalid wrappedAdapterRange specified --- end >= count (wrappedAdapterRange = " + range + ")");
        } else if (!range.checkInRange(position)) {
            throw new IllegalStateException("Invalid wrappedAdapterRange specified --- does not contain drag target item (wrappedAdapterRange = " + range + ", position = " + position + ")");
        }
    }

    private void handleActionMoveWhileDragging(RecyclerView rv, MotionEvent e) {
        int scrollX;
        this.mLastTouchX = (int) (e.getX() + 0.5f);
        this.mLastTouchY = (int) (e.getY() + 0.5f);
        if (this.mNestedScrollView != null) {
            scrollX = this.mNestedScrollView.getScrollX();
        } else {
            scrollX = 0;
        }
        this.mNestedScrollViewScrollX = scrollX;
        if (this.mNestedScrollView != null) {
            scrollX = this.mNestedScrollView.getScrollY();
        } else {
            scrollX = 0;
        }
        this.mNestedScrollViewScrollY = scrollX;
        this.mDragMinTouchX = Math.min(this.mDragMinTouchX, this.mLastTouchX);
        this.mDragMinTouchY = Math.min(this.mDragMinTouchY, this.mLastTouchY);
        this.mDragMaxTouchX = Math.max(this.mDragMaxTouchX, this.mLastTouchX);
        this.mDragMaxTouchY = Math.max(this.mDragMaxTouchY, this.mLastTouchY);
        updateDragDirectionMask();
        if (this.mDraggingItemDecorator.update(getLastTouchX(), getLastTouchY(), false)) {
            if (this.mSwapTargetItemOperator != null) {
                this.mSwapTargetItemOperator.update(this.mDraggingItemDecorator.getDraggingItemTranslationX(), this.mDraggingItemDecorator.getDraggingItemTranslationY());
            }
            checkItemSwapping(rv);
            onItemMoveDistanceUpdated();
        }
    }

    private void updateDragDirectionMask() {
        switch (CustomRecyclerViewUtils.getOrientation(this.mRecyclerView)) {
            case 0:
                int lastTouchX = getLastTouchX();
                if (this.mDragStartTouchX - this.mDragMinTouchX > this.mScrollTouchSlop || this.mDragMaxTouchX - lastTouchX > this.mScrollTouchSlop) {
                    this.mScrollDirMask |= 4;
                }
                if (this.mDragMaxTouchX - this.mDragStartTouchX > this.mScrollTouchSlop || lastTouchX - this.mDragMinTouchX > this.mScrollTouchSlop) {
                    this.mScrollDirMask |= 8;
                    return;
                }
                return;
            case 1:
                int lastTouchY = getLastTouchY();
                if (this.mDragStartTouchY - this.mDragMinTouchY > this.mScrollTouchSlop || this.mDragMaxTouchY - lastTouchY > this.mScrollTouchSlop) {
                    this.mScrollDirMask |= 1;
                }
                if (this.mDragMaxTouchY - this.mDragStartTouchY > this.mScrollTouchSlop || lastTouchY - this.mDragMinTouchY > this.mScrollTouchSlop) {
                    this.mScrollDirMask |= 2;
                    return;
                }
                return;
            default:
                return;
        }
    }

    private int getLastTouchX() {
        int touchX = this.mLastTouchX;
        if (this.mNestedScrollView != null) {
            return touchX + (this.mNestedScrollView.getScrollX() - this.mNestedScrollViewScrollX);
        }
        return touchX;
    }

    private int getLastTouchY() {
        int touchY = this.mLastTouchY;
        if (this.mNestedScrollView != null) {
            return touchY + (this.mNestedScrollView.getScrollY() - this.mNestedScrollViewScrollY);
        }
        return touchY;
    }

    void checkItemSwapping(RecyclerView rv) {
        ViewHolder draggingItem = this.mDraggingItemViewHolder;
        FindSwapTargetContext fc = this.mFindSwapTargetContext;
        fc.setup(rv, this.mDraggingItemViewHolder, this.mDraggingItemInfo, getLastTouchX(), getLastTouchY(), this.mDraggableRange, this.mRootDraggableRange, this.mCheckCanDrop);
        int draggingItemInitialPosition = this.mWrapperAdapter.getDraggingItemInitialPosition();
        int draggingItemCurrentPosition = this.mWrapperAdapter.getDraggingItemCurrentPosition();
        boolean canSwap = false;
        SwapTarget swapTarget = findSwapTargetItem(this.mTempSwapTarget, fc, false);
        if (swapTarget.position != -1) {
            if (!this.mCheckCanDrop) {
                canSwap = true;
            }
            if (!canSwap) {
                canSwap = this.mWrapperAdapter.canDropItems(draggingItemInitialPosition, swapTarget.position);
            }
            if (!canSwap) {
                swapTarget = findSwapTargetItem(this.mTempSwapTarget, fc, true);
                if (swapTarget.position != -1) {
                    canSwap = this.mWrapperAdapter.canDropItems(draggingItemInitialPosition, swapTarget.position);
                }
            }
        }
        if (canSwap && swapTarget.holder == null) {
            throw new IllegalStateException("bug check");
        }
        if (canSwap) {
            swapItems(rv, draggingItemCurrentPosition, draggingItem, swapTarget.holder);
        }
        if (this.mSwapTargetItemOperator != null) {
            this.mSwapTargetItemOperator.setSwapTargetItem(canSwap ? swapTarget.holder : null);
        }
        if (canSwap) {
            this.mHandler.scheduleDraggingItemViewSizeUpdateCheck();
        }
        swapTarget.clear();
        fc.clear();
    }

    private void onItemMoveDistanceUpdated() {
        if (this.mItemDragEventListener != null) {
            this.mItemDragEventListener.onItemDragMoveDistanceUpdated(this.mDragScrollDistanceX + this.mDraggingItemDecorator.getDraggingItemMoveOffsetX(), this.mDragScrollDistanceY + this.mDraggingItemDecorator.getDraggingItemMoveOffsetY());
        }
    }

    void handleScrollOnDragging() {
        boolean horizontal;
        RecyclerView rv = this.mRecyclerView;
        switch (CustomRecyclerViewUtils.getOrientation(rv)) {
            case 0:
                horizontal = true;
                break;
            case 1:
                horizontal = false;
                break;
            default:
                return;
        }
        if (this.mNestedScrollView != null) {
            handleScrollOnDraggingInternalWithNestedScrollView(rv, horizontal);
        } else {
            handleScrollOnDraggingInternalWithRecyclerView(rv, horizontal);
        }
    }

    private void handleScrollOnDraggingInternalWithNestedScrollView(RecyclerView rv, boolean horizontal) {
        NestedScrollView nestedScrollView = this.mNestedScrollView;
        int nestedScrollViewScrollOffsetX = nestedScrollView.getScrollX();
        int nestedScrollViewScrollOffsetY = nestedScrollView.getScrollY();
        Rect rect = new Rect();
        int lastTouchX = getLastTouchX();
        rect.right = lastTouchX;
        rect.left = lastTouchX;
        lastTouchX = getLastTouchY();
        rect.bottom = lastTouchX;
        rect.top = lastTouchX;
        offsetDescendantRectToAncestorCoords(this.mRecyclerView, nestedScrollView, rect);
        int nestedScrollViewTouchX = rect.left - nestedScrollViewScrollOffsetX;
        int nestedScrollViewTouchY = rect.top - nestedScrollViewScrollOffsetY;
        float invEdge = 1.0f / ((float) (horizontal ? nestedScrollView.getWidth() : nestedScrollView.getHeight()));
        if (!horizontal) {
            nestedScrollViewTouchX = nestedScrollViewTouchY;
        }
        float centerOffset = (((float) nestedScrollViewTouchX) * invEdge) - 0.5f;
        float acceleration = Math.max(0.0f, SCROLL_THRESHOLD - (0.5f - Math.abs(centerOffset))) * 3.3333333f;
        int mask = this.mScrollDirMask;
        int scrollAmount = ((int) Math.signum(centerOffset)) * ((int) ((((SCROLL_AMOUNT_COEFF * this.mDragEdgeScrollSpeed) * this.mDisplayDensity) * acceleration) + 0.5f));
        if (scrollAmount > 0) {
            if (((horizontal ? 8 : 2) & mask) == 0) {
                scrollAmount = 0;
            }
        } else if (scrollAmount < 0) {
            if (((horizontal ? 4 : 1) & mask) == 0) {
                scrollAmount = 0;
            }
        }
        if (scrollAmount != 0) {
            safeEndAnimationsIfRequired(rv);
            if (horizontal) {
                nestedScrollView.scrollBy(scrollAmount, 0);
            } else {
                nestedScrollView.scrollBy(0, scrollAmount);
            }
        }
        if (this.mDraggingItemDecorator.update(getLastTouchX(), getLastTouchY(), false)) {
            if (this.mSwapTargetItemOperator != null) {
                this.mSwapTargetItemOperator.update(this.mDraggingItemDecorator.getDraggingItemTranslationX(), this.mDraggingItemDecorator.getDraggingItemTranslationY());
            }
            checkItemSwapping(rv);
            onItemMoveDistanceUpdated();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleScrollOnDraggingInternalWithRecyclerView(android.support.v7.widget.RecyclerView r34, boolean r35) {
        /*
        r33 = this;
        if (r35 == 0) goto L_0x0009;
    L_0x0002:
        r12 = r34.getWidth();
    L_0x0006:
        if (r12 != 0) goto L_0x000e;
    L_0x0008:
        return;
    L_0x0009:
        r12 = r34.getHeight();
        goto L_0x0006;
    L_0x000e:
        r30 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = (float) r12;
        r31 = r0;
        r16 = r30 / r31;
        if (r35 == 0) goto L_0x01bb;
    L_0x0017:
        r30 = r33.getLastTouchX();
    L_0x001b:
        r0 = r30;
        r0 = (float) r0;
        r30 = r0;
        r22 = r30 * r16;
        r29 = 1050253722; // 0x3e99999a float:0.3 double:5.188942835E-315;
        r17 = 1079334229; // 0x40555555 float:3.3333333 double:5.33261963E-315;
        r30 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r7 = r22 - r30;
        r2 = java.lang.Math.abs(r7);
        r30 = 0;
        r31 = 1050253722; // 0x3e99999a float:0.3 double:5.188942835E-315;
        r32 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r32 = r32 - r2;
        r31 = r31 - r32;
        r30 = java.lang.Math.max(r30, r31);
        r31 = 1079334229; // 0x40555555 float:3.3333333 double:5.33261963E-315;
        r4 = r30 * r31;
        r0 = r33;
        r0 = r0.mScrollDirMask;
        r19 = r0;
        r0 = r33;
        r8 = r0.mDraggingItemDecorator;
        r30 = java.lang.Math.signum(r7);
        r0 = r30;
        r0 = (int) r0;
        r30 = r0;
        r31 = 1103626240; // 0x41c80000 float:25.0 double:5.45263811E-315;
        r0 = r33;
        r0 = r0.mDragEdgeScrollSpeed;
        r32 = r0;
        r31 = r31 * r32;
        r0 = r33;
        r0 = r0.mDisplayDensity;
        r32 = r0;
        r31 = r31 * r32;
        r31 = r31 * r4;
        r32 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r31 = r31 + r32;
        r0 = r31;
        r0 = (int) r0;
        r31 = r0;
        r28 = r30 * r31;
        r6 = 0;
        r0 = r33;
        r0 = r0.mRootDraggableRange;
        r23 = r0;
        r0 = r33;
        r0 = r0.mRecyclerView;
        r30 = r0;
        r15 = com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils.findFirstCompletelyVisibleItemPosition(r30);
        r0 = r33;
        r0 = r0.mRecyclerView;
        r30 = r0;
        r18 = com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils.findLastCompletelyVisibleItemPosition(r30);
        r24 = 0;
        r25 = 0;
        r26 = 0;
        r27 = 0;
        r30 = -1;
        r0 = r30;
        if (r15 == r0) goto L_0x00b5;
    L_0x009f:
        r30 = r23.getStart();
        r0 = r30;
        if (r15 > r0) goto L_0x00a9;
    L_0x00a7:
        r25 = 1;
    L_0x00a9:
        r30 = r23.getStart();
        r30 = r30 + -1;
        r0 = r30;
        if (r15 > r0) goto L_0x00b5;
    L_0x00b3:
        r24 = 1;
    L_0x00b5:
        r30 = -1;
        r0 = r18;
        r1 = r30;
        if (r0 == r1) goto L_0x00d7;
    L_0x00bd:
        r30 = r23.getEnd();
        r0 = r18;
        r1 = r30;
        if (r0 < r1) goto L_0x00c9;
    L_0x00c7:
        r27 = 1;
    L_0x00c9:
        r30 = r23.getEnd();
        r30 = r30 + 1;
        r0 = r18;
        r1 = r30;
        if (r0 < r1) goto L_0x00d7;
    L_0x00d5:
        r26 = 1;
    L_0x00d7:
        if (r28 <= 0) goto L_0x01c5;
    L_0x00d9:
        if (r35 == 0) goto L_0x01c1;
    L_0x00db:
        r30 = 8;
    L_0x00dd:
        r30 = r30 & r19;
        if (r30 != 0) goto L_0x00e3;
    L_0x00e1:
        r28 = 0;
    L_0x00e3:
        if (r24 != 0) goto L_0x00e7;
    L_0x00e5:
        if (r28 < 0) goto L_0x00eb;
    L_0x00e7:
        if (r26 != 0) goto L_0x01f2;
    L_0x00e9:
        if (r28 <= 0) goto L_0x01f2;
    L_0x00eb:
        r33.safeEndAnimationsIfRequired(r34);
        if (r35 == 0) goto L_0x01d6;
    L_0x00f0:
        r0 = r33;
        r1 = r28;
        r6 = r0.scrollByXAndGetScrolledAmount(r1);
    L_0x00f8:
        if (r28 >= 0) goto L_0x01e4;
    L_0x00fa:
        if (r25 != 0) goto L_0x01e0;
    L_0x00fc:
        r30 = 1;
    L_0x00fe:
        r0 = r30;
        r8.setIsScrolling(r0);
    L_0x0103:
        r30 = 1;
        r0 = r30;
        r8.refresh(r0);
        r0 = r33;
        r0 = r0.mSwapTargetItemOperator;
        r30 = r0;
        if (r30 == 0) goto L_0x0123;
    L_0x0112:
        r0 = r33;
        r0 = r0.mSwapTargetItemOperator;
        r30 = r0;
        r31 = r8.getDraggingItemTranslationX();
        r32 = r8.getDraggingItemTranslationY();
        r30.update(r31, r32);
    L_0x0123:
        r0 = r33;
        r0 = r0.mEdgeEffectDecorator;
        r30 = r0;
        if (r30 == 0) goto L_0x0195;
    L_0x012b:
        r13 = 0;
        r0 = r33;
        r0 = r0.mOrigOverScrollMode;
        r30 = r0;
        r31 = 2;
        r0 = r30;
        r1 = r31;
        if (r0 == r1) goto L_0x0190;
    L_0x013a:
        if (r6 == 0) goto L_0x01fb;
    L_0x013c:
        r5 = 1;
    L_0x013d:
        r14 = 1000593162; // 0x3ba3d70a float:0.005 double:4.94358707E-315;
        if (r35 == 0) goto L_0x01fe;
    L_0x0142:
        r11 = r8.getTranslatedItemPositionLeft();
    L_0x0146:
        if (r35 == 0) goto L_0x0204;
    L_0x0148:
        r9 = r8.getTranslatedItemPositionRight();
    L_0x014c:
        r30 = r11 + r9;
        r10 = r30 / 2;
        if (r15 != 0) goto L_0x020e;
    L_0x0152:
        if (r18 != 0) goto L_0x020e;
    L_0x0154:
        if (r28 >= 0) goto L_0x020a;
    L_0x0156:
        r21 = r11;
    L_0x0158:
        r0 = r21;
        r0 = (float) r0;
        r30 = r0;
        r30 = r30 * r16;
        r31 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r20 = r30 - r31;
        r3 = java.lang.Math.abs(r20);
        r30 = 1053609165; // 0x3ecccccd float:0.4 double:5.205520926E-315;
        r30 = (r3 > r30 ? 1 : (r3 == r30 ? 0 : -1));
        if (r30 <= 0) goto L_0x0190;
    L_0x016e:
        if (r28 == 0) goto L_0x0190;
    L_0x0170:
        if (r5 != 0) goto L_0x0190;
    L_0x0172:
        r30 = 0;
        r30 = (r20 > r30 ? 1 : (r20 == r30 ? 0 : -1));
        if (r30 >= 0) goto L_0x0223;
    L_0x0178:
        if (r35 == 0) goto L_0x021b;
    L_0x017a:
        r30 = r8.isReachedToLeftLimit();
        if (r30 == 0) goto L_0x0190;
    L_0x0180:
        r0 = r33;
        r0 = r0.mDisplayDensity;
        r30 = r0;
        r0 = r30;
        r0 = -r0;
        r30 = r0;
        r31 = 1000593162; // 0x3ba3d70a float:0.005 double:4.94358707E-315;
        r13 = r30 * r31;
    L_0x0190:
        r0 = r33;
        r0.updateEdgeEffect(r13);
    L_0x0195:
        r0 = r33;
        r0 = r0.mRecyclerView;
        r30 = r0;
        r0 = r33;
        r0 = r0.mCheckItemSwappingRunnable;
        r31 = r0;
        android.support.v4.view.ViewCompat.postOnAnimation(r30, r31);
        if (r6 == 0) goto L_0x0008;
    L_0x01a6:
        if (r35 == 0) goto L_0x023f;
    L_0x01a8:
        r0 = r33;
        r0 = r0.mDragScrollDistanceX;
        r30 = r0;
        r30 = r30 + r6;
        r0 = r30;
        r1 = r33;
        r1.mDragScrollDistanceX = r0;
    L_0x01b6:
        r33.onItemMoveDistanceUpdated();
        goto L_0x0008;
    L_0x01bb:
        r30 = r33.getLastTouchY();
        goto L_0x001b;
    L_0x01c1:
        r30 = 2;
        goto L_0x00dd;
    L_0x01c5:
        if (r28 >= 0) goto L_0x00e3;
    L_0x01c7:
        if (r35 == 0) goto L_0x01d3;
    L_0x01c9:
        r30 = 4;
    L_0x01cb:
        r30 = r30 & r19;
        if (r30 != 0) goto L_0x00e3;
    L_0x01cf:
        r28 = 0;
        goto L_0x00e3;
    L_0x01d3:
        r30 = 1;
        goto L_0x01cb;
    L_0x01d6:
        r0 = r33;
        r1 = r28;
        r6 = r0.scrollByYAndGetScrolledAmount(r1);
        goto L_0x00f8;
    L_0x01e0:
        r30 = 0;
        goto L_0x00fe;
    L_0x01e4:
        if (r27 != 0) goto L_0x01ef;
    L_0x01e6:
        r30 = 1;
    L_0x01e8:
        r0 = r30;
        r8.setIsScrolling(r0);
        goto L_0x0103;
    L_0x01ef:
        r30 = 0;
        goto L_0x01e8;
    L_0x01f2:
        r30 = 0;
        r0 = r30;
        r8.setIsScrolling(r0);
        goto L_0x0123;
    L_0x01fb:
        r5 = 0;
        goto L_0x013d;
    L_0x01fe:
        r11 = r8.getTranslatedItemPositionTop();
        goto L_0x0146;
    L_0x0204:
        r9 = r8.getTranslatedItemPositionBottom();
        goto L_0x014c;
    L_0x020a:
        r21 = r9;
        goto L_0x0158;
    L_0x020e:
        r30 = r12 / 2;
        r0 = r30;
        if (r10 >= r0) goto L_0x0218;
    L_0x0214:
        r21 = r11;
    L_0x0216:
        goto L_0x0158;
    L_0x0218:
        r21 = r9;
        goto L_0x0216;
    L_0x021b:
        r30 = r8.isReachedToTopLimit();
        if (r30 == 0) goto L_0x0190;
    L_0x0221:
        goto L_0x0180;
    L_0x0223:
        if (r35 == 0) goto L_0x0238;
    L_0x0225:
        r30 = r8.isReachedToRightLimit();
        if (r30 == 0) goto L_0x0190;
    L_0x022b:
        r0 = r33;
        r0 = r0.mDisplayDensity;
        r30 = r0;
        r31 = 1000593162; // 0x3ba3d70a float:0.005 double:4.94358707E-315;
        r13 = r30 * r31;
        goto L_0x0190;
    L_0x0238:
        r30 = r8.isReachedToBottomLimit();
        if (r30 == 0) goto L_0x0190;
    L_0x023e:
        goto L_0x022b;
    L_0x023f:
        r0 = r33;
        r0 = r0.mDragScrollDistanceY;
        r30 = r0;
        r30 = r30 + r6;
        r0 = r30;
        r1 = r33;
        r1.mDragScrollDistanceY = r0;
        goto L_0x01b6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager.handleScrollOnDraggingInternalWithRecyclerView(android.support.v7.widget.RecyclerView, boolean):void");
    }

    private void updateEdgeEffect(float distance) {
        if (distance == 0.0f) {
            this.mEdgeEffectDecorator.releaseBothGlows();
        } else if (distance < 0.0f) {
            this.mEdgeEffectDecorator.pullFirstEdge(distance);
        } else {
            this.mEdgeEffectDecorator.pullSecondEdge(distance);
        }
    }

    private static NestedScrollView findAncestorNestedScrollView(View v) {
        for (ViewParent target = v.getParent(); target != null; target = target.getParent()) {
            if (target instanceof NestedScrollView) {
                return (NestedScrollView) target;
            }
        }
        return null;
    }

    private static boolean offsetDescendantRectToAncestorCoords(View descendant, View ancestor, Rect rect) {
        View view = descendant;
        Object parent;
        do {
            parent = view.getParent();
            if (!(parent instanceof ViewGroup)) {
                return false;
            }
            ((ViewGroup) parent).offsetDescendantRectToMyCoords(view, rect);
            view = (View) parent;
        } while (parent != ancestor);
        return true;
    }

    private int scrollByYAndGetScrolledAmount(int ry) {
        this.mActualScrollByYAmount = 0;
        this.mInScrollByMethod = true;
        this.mRecyclerView.scrollBy(0, ry);
        this.mInScrollByMethod = false;
        return this.mActualScrollByYAmount;
    }

    private int scrollByXAndGetScrolledAmount(int rx) {
        this.mActualScrollByXAmount = 0;
        this.mInScrollByMethod = true;
        this.mRecyclerView.scrollBy(rx, 0);
        this.mInScrollByMethod = false;
        return this.mActualScrollByXAmount;
    }

    RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    private void startScrollOnDraggingProcess() {
        this.mScrollOnDraggingProcess.start();
    }

    private void stopScrollOnDraggingProcess() {
        if (this.mScrollOnDraggingProcess != null) {
            this.mScrollOnDraggingProcess.stop();
        }
    }

    private void swapItems(RecyclerView rv, int draggingItemAdapterPosition, @Nullable ViewHolder draggingItem, @NonNull ViewHolder swapTargetHolder) {
        Rect swapTargetMargins = CustomRecyclerViewUtils.getLayoutMargins(swapTargetHolder.itemView, this.mTmpRect1);
        int fromPosition = draggingItemAdapterPosition;
        int toPosition = getWrappedAdapterPosition(swapTargetHolder);
        int diffPosition = Math.abs(fromPosition - toPosition);
        boolean performSwapping = false;
        if (fromPosition != -1 && toPosition != -1 && ItemIdComposer.extractWrappedIdPart(this.mWrapperAdapter.getItemId(fromPosition)) == ItemIdComposer.extractWrappedIdPart(this.mDraggingItemInfo.id)) {
            boolean swapNextItemSmoothlyInLinearLayout = CustomRecyclerViewUtils.isLinearLayout(CustomRecyclerViewUtils.getLayoutType(rv)) && !(supportsViewTranslation() && this.mCheckCanDrop);
            if (diffPosition != 0) {
                if (diffPosition == 1 && draggingItem != null && swapNextItemSmoothlyInLinearLayout) {
                    float midPointOfTheItems;
                    float midPointOfTheOverlaidItem;
                    View v1 = draggingItem.itemView;
                    View v2 = swapTargetHolder.itemView;
                    Rect m1 = this.mDraggingItemInfo.margins;
                    Rect m2 = swapTargetMargins;
                    if (this.mCanDragH) {
                        int left = Math.min(v1.getLeft() - m1.left, v2.getLeft() - m2.left);
                        midPointOfTheItems = ((float) left) + (((float) (Math.max(v1.getRight() + m1.right, v2.getRight() + m2.right) - left)) * 0.5f);
                        midPointOfTheOverlaidItem = ((float) (getLastTouchX() - this.mDraggingItemInfo.grabbedPositionX)) + (((float) this.mDraggingItemInfo.width) * 0.5f);
                        if (toPosition < fromPosition) {
                            if (midPointOfTheOverlaidItem < midPointOfTheItems) {
                                performSwapping = true;
                            }
                        } else if (midPointOfTheOverlaidItem > midPointOfTheItems) {
                            performSwapping = true;
                        }
                    }
                    if (!performSwapping && this.mCanDragV) {
                        int top = Math.min(v1.getTop() - m1.top, v2.getTop() - m2.top);
                        midPointOfTheItems = ((float) top) + (((float) (Math.max(v1.getBottom() + m1.bottom, v2.getBottom() + m2.bottom) - top)) * 0.5f);
                        midPointOfTheOverlaidItem = ((float) (getLastTouchY() - this.mDraggingItemInfo.grabbedPositionY)) + (((float) this.mDraggingItemInfo.height) * 0.5f);
                        if (toPosition < fromPosition) {
                            if (midPointOfTheOverlaidItem < midPointOfTheItems) {
                                performSwapping = true;
                            }
                        } else if (midPointOfTheOverlaidItem > midPointOfTheItems) {
                            performSwapping = true;
                        }
                    }
                } else {
                    performSwapping = true;
                }
            }
            if (performSwapping) {
                performSwapItems(rv, draggingItem, swapTargetHolder, swapTargetMargins, fromPosition, toPosition);
            }
        }
    }

    private void performSwapItems(RecyclerView rv, @Nullable ViewHolder draggingItemHolder, @NonNull ViewHolder swapTargetHolder, Rect swapTargetMargins, int fromPosition, int toPosition) {
        if (this.mItemDragEventListener != null) {
            this.mItemDragEventListener.onItemDragPositionChanged(fromPosition, toPosition);
        }
        LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        int layoutType = CustomRecyclerViewUtils.getLayoutType(this.mRecyclerView);
        boolean isVertical = CustomRecyclerViewUtils.extractOrientation(layoutType) == 1;
        int firstVisible = CustomRecyclerViewUtils.findFirstVisibleItemPosition(this.mRecyclerView, false);
        View fromView = draggingItemHolder != null ? draggingItemHolder.itemView : null;
        View toView = swapTargetHolder.itemView;
        View firstView = CustomRecyclerViewUtils.findViewByPosition(layoutManager, firstVisible);
        int rootFromPosition = draggingItemHolder.getLayoutPosition();
        int rootToPosition = swapTargetHolder.getLayoutPosition();
        Integer fromOrigin = getItemViewOrigin(fromView, isVertical);
        Integer toOrigin = getItemViewOrigin(toView, isVertical);
        Integer firstOrigin = getItemViewOrigin(firstView, isVertical);
        this.mWrapperAdapter.moveItem(fromPosition, toPosition, layoutType);
        if (firstVisible == rootFromPosition && firstOrigin != null && toOrigin != null) {
            scrollBySpecifiedOrientation(rv, -(toOrigin.intValue() - firstOrigin.intValue()), isVertical);
            safeEndAnimations(rv);
        } else if (firstVisible == rootToPosition && fromView != null && fromOrigin != null && !fromOrigin.equals(toOrigin)) {
            int amount;
            MarginLayoutParams lp = (MarginLayoutParams) fromView.getLayoutParams();
            if (isVertical) {
                amount = -((layoutManager.getDecoratedMeasuredHeight(fromView) + lp.topMargin) + lp.bottomMargin);
            } else {
                amount = -((layoutManager.getDecoratedMeasuredWidth(fromView) + lp.leftMargin) + lp.rightMargin);
            }
            scrollBySpecifiedOrientation(rv, amount, isVertical);
            safeEndAnimations(rv);
        }
    }

    private static void scrollBySpecifiedOrientation(RecyclerView rv, int amount, boolean vertical) {
        if (vertical) {
            rv.scrollBy(0, amount);
        } else {
            rv.scrollBy(amount, 0);
        }
    }

    private static Integer getItemViewOrigin(View itemView, boolean vertical) {
        if (itemView == null) {
            return null;
        }
        return Integer.valueOf(vertical ? itemView.getTop() : itemView.getLeft());
    }

    private boolean checkTouchedItemState(RecyclerView rv, ViewHolder holder) {
        if (!(holder instanceof DraggableItemViewHolder)) {
            return false;
        }
        int wrappedItemPosition = getWrappedAdapterPosition(holder);
        Adapter adapter = this.mWrapperAdapter;
        if (wrappedItemPosition < 0 || wrappedItemPosition >= adapter.getItemCount()) {
            return false;
        }
        return true;
    }

    private static boolean supportsEdgeEffect() {
        return VERSION.SDK_INT >= 14;
    }

    private static boolean supportsViewTranslation() {
        return VERSION.SDK_INT >= 11;
    }

    private static void safeEndAnimation(RecyclerView rv, ViewHolder holder) {
        ItemAnimator itemAnimator = rv != null ? rv.getItemAnimator() : null;
        if (itemAnimator != null) {
            itemAnimator.endAnimation(holder);
        }
    }

    private static void safeEndAnimations(RecyclerView rv) {
        ItemAnimator itemAnimator = rv != null ? rv.getItemAnimator() : null;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
    }

    private void safeEndAnimationsIfRequired(RecyclerView rv) {
        if (this.mSwapTargetItemOperator != null) {
            safeEndAnimations(rv);
        }
    }

    private SwapTarget findSwapTargetItem(SwapTarget dest, FindSwapTargetContext fc, boolean alternative) {
        ViewHolder swapTargetHolder = null;
        dest.clear();
        if (fc.draggingItem == null || (getWrappedAdapterPosition(fc.draggingItem) != -1 && fc.draggingItem.getItemId() == fc.draggingItemInfo.id)) {
            switch (fc.layoutType) {
                case 0:
                case 1:
                    swapTargetHolder = findSwapTargetItemForLinearLayoutManager(fc, alternative);
                    break;
                case 2:
                case 3:
                    swapTargetHolder = findSwapTargetItemForGridLayoutManager(fc, alternative);
                    break;
                case 4:
                case 5:
                    swapTargetHolder = findSwapTargetItemForStaggeredGridLayoutManager(fc, alternative);
                    break;
            }
        }
        if (swapTargetHolder == fc.draggingItem) {
            swapTargetHolder = null;
            dest.self = true;
        }
        int swapTargetWrappedItemPosition = getWrappedAdapterPosition(swapTargetHolder);
        if (!(swapTargetHolder == null || fc.wrappedAdapterRange == null || fc.wrappedAdapterRange.checkInRange(swapTargetWrappedItemPosition))) {
            swapTargetHolder = null;
        }
        dest.holder = swapTargetHolder;
        if (swapTargetHolder == null) {
            swapTargetWrappedItemPosition = -1;
        }
        dest.position = swapTargetWrappedItemPosition;
        return dest;
    }

    private static ViewHolder findSwapTargetItemForGridLayoutManager(FindSwapTargetContext fc, boolean alternative) {
        if (alternative) {
            return null;
        }
        ViewHolder swapTargetHolder = findSwapTargetItemForGridLayoutManagerInternal1(fc);
        if (swapTargetHolder == null) {
            return findSwapTargetItemForGridLayoutManagerInternal2(fc);
        }
        return swapTargetHolder;
    }

    private static ViewHolder findSwapTargetItemForStaggeredGridLayoutManager(FindSwapTargetContext fc, boolean alternative) {
        if (alternative || fc.draggingItem == null) {
            return null;
        }
        ViewHolder csvh;
        ViewHolder ccvh;
        int sx = fc.overlayItemLeft + 1;
        int cx = (fc.overlayItemLeft + (fc.draggingItemInfo.width / 2)) - 1;
        int ex = (fc.overlayItemLeft + fc.draggingItemInfo.width) - 2;
        int sy = fc.overlayItemTop + 1;
        int cy = (fc.overlayItemTop + (fc.draggingItemInfo.height / 2)) - 1;
        int ey = (fc.overlayItemTop + fc.draggingItemInfo.height) - 2;
        ViewHolder cevh;
        if (fc.vertical) {
            csvh = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) sx, (float) cy);
            cevh = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) ex, (float) cy);
            ccvh = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) cx, (float) cy);
        } else {
            csvh = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) cx, (float) sy);
            cevh = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) cx, (float) cy);
            ccvh = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) cx, (float) ey);
        }
        if (ccvh == fc.draggingItem) {
            return null;
        }
        if (ccvh == csvh || ccvh == cevh) {
            return ccvh;
        }
        return null;
    }

    private static ViewHolder findSwapTargetItemForGridLayoutManagerInternal1(FindSwapTargetContext fc) {
        int gap = (int) (fc.rv.getContext().getResources().getDisplayMetrics().density * 4.0f);
        int cx = fc.overlayItemLeftNotClipped;
        cx += (int) (((float) fc.draggingItemInfo.width) * 0.5f);
        int cy = fc.overlayItemTopNotClipped + ((int) (((float) fc.draggingItemInfo.height) * 0.5f));
        if (fc.vertical) {
            cx = Math.min(Math.max(cx, (fc.rv.getPaddingLeft() + (gap * 2)) + 1), ((fc.rv.getWidth() - fc.rv.getPaddingRight()) - (gap * 2)) - 1);
        } else {
            cy = Math.min(Math.max(cy, (fc.rv.getPaddingTop() + (gap * 2)) + 1), ((fc.rv.getHeight() - fc.rv.getPaddingBottom()) - (gap * 2)) - 1);
        }
        ViewHolder vh1 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) (cx - gap), (float) (cy - gap));
        if (vh1 == null || vh1 == fc.draggingItem) {
            return vh1;
        }
        ViewHolder vh2 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) (cx + gap), (float) (cy - gap));
        if (vh2 == null || vh2 == fc.draggingItem) {
            return vh2;
        }
        ViewHolder vh3 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) (cx - gap), (float) (cy + gap));
        if (vh3 == null || vh3 == fc.draggingItem) {
            return vh3;
        }
        ViewHolder vh4 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) (cx + gap), (float) (cy + gap));
        if (vh4 == null || vh4 == fc.draggingItem) {
            return vh4;
        }
        if (vh1 == vh2 && vh1 == vh3 && vh1 == vh4) {
            return vh1;
        }
        return null;
    }

    private static ViewHolder findSwapTargetItemForGridLayoutManagerInternal2(FindSwapTargetContext fc) {
        int spanCount = CustomRecyclerViewUtils.getSpanCount(fc.rv);
        int height = fc.rv.getHeight();
        int width = fc.rv.getWidth();
        int paddingLeft = fc.vertical ? fc.rv.getPaddingLeft() : 0;
        int paddingTop = !fc.vertical ? fc.rv.getPaddingTop() : 0;
        int columnWidth = ((width - paddingLeft) - (fc.vertical ? fc.rv.getPaddingRight() : 0)) / spanCount;
        int rowHeight = ((height - paddingTop) - (!fc.vertical ? fc.rv.getPaddingBottom() : 0)) / spanCount;
        int cx = fc.overlayItemLeft + (fc.draggingItemInfo.width / 2);
        int cy = fc.overlayItemTop + (fc.draggingItemInfo.height / 2);
        for (int i = spanCount - 1; i >= 0; i--) {
            int cx2;
            int cy2;
            if (fc.vertical) {
                cx2 = ((columnWidth * i) + paddingLeft) + (columnWidth / 2);
            } else {
                cx2 = cx;
            }
            if (fc.vertical) {
                cy2 = cy;
            } else {
                cy2 = ((rowHeight * i) + paddingTop) + (rowHeight / 2);
            }
            ViewHolder vh2 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, (float) cx2, (float) cy2);
            if (vh2 != null) {
                int rangeEndIndex = fc.rootAdapterRange.getEnd();
                int pos = vh2.getAdapterPosition();
                if (pos != -1 && pos == rangeEndIndex) {
                    return vh2;
                }
                return null;
            }
        }
        return null;
    }

    private static ViewHolder findSwapTargetItemForLinearLayoutManager(FindSwapTargetContext fc, boolean alternative) {
        ViewHolder swapTargetHolder = null;
        if (fc.draggingItem == null) {
            return null;
        }
        if (fc.checkCanSwap || alternative) {
            float gap = fc.draggingItem.itemView.getResources().getDisplayMetrics().density * 8.0f;
            float hgap = Math.min(((float) fc.draggingItemInfo.width) * 0.2f, gap);
            float vgap = Math.min(((float) fc.draggingItemInfo.height) * 0.2f, gap);
            float cx = ((float) fc.overlayItemLeft) + (((float) fc.draggingItemInfo.width) * 0.5f);
            float cy = ((float) fc.overlayItemTop) + (((float) fc.draggingItemInfo.height) * 0.5f);
            ViewHolder swapTargetHolder1 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, cx - hgap, cy - vgap);
            if (swapTargetHolder1 == CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(fc.rv, cx + hgap, cy + vgap)) {
                swapTargetHolder = swapTargetHolder1;
            }
        } else {
            int draggingItemPosition = fc.draggingItem.getAdapterPosition();
            int draggingViewOrigin = fc.vertical ? fc.draggingItem.itemView.getTop() : fc.draggingItem.itemView.getLeft();
            int overlayItemOrigin = fc.vertical ? fc.overlayItemTop : fc.overlayItemLeft;
            if (overlayItemOrigin < draggingViewOrigin) {
                if (draggingItemPosition > 0) {
                    swapTargetHolder = fc.rv.findViewHolderForAdapterPosition(draggingItemPosition - 1);
                }
            } else if (overlayItemOrigin > draggingViewOrigin && draggingItemPosition < fc.rv.getAdapter().getItemCount() - 1) {
                swapTargetHolder = fc.rv.findViewHolderForAdapterPosition(draggingItemPosition + 1);
            }
        }
        return swapTargetHolder;
    }

    public void setItemSettleBackIntoPlaceAnimationDuration(int duration) {
        this.mItemSettleBackIntoPlaceAnimationDuration = duration;
    }

    public int getItemSettleBackIntoPlaceAnimationDuration() {
        return this.mItemSettleBackIntoPlaceAnimationDuration;
    }

    public void setItemSettleBackIntoPlaceAnimationInterpolator(@Nullable Interpolator interpolator) {
        this.mItemSettleBackIntoPlaceAnimationInterpolator = interpolator;
    }

    @Nullable
    public Interpolator getItemSettleBackIntoPlaceAnimationInterpolator() {
        return this.mItemSettleBackIntoPlaceAnimationInterpolator;
    }

    public void setDragStartItemAnimationDuration(int duration) {
        this.mDraggingItemEffectsInfo.durationMillis = duration;
    }

    public int getDragStartItemAnimationDuration() {
        return this.mDraggingItemEffectsInfo.durationMillis;
    }

    public void setDragStartItemScaleAnimationInterpolator(Interpolator interpolator) {
        this.mDraggingItemEffectsInfo.scaleInterpolator = interpolator;
    }

    @Nullable
    public Interpolator getDragStartItemScaleAnimationInterpolator() {
        return this.mDraggingItemEffectsInfo.scaleInterpolator;
    }

    public void setDragStartItemRotationAnimationInterpolator(Interpolator interpolator) {
        this.mDraggingItemEffectsInfo.rotationInterpolator = interpolator;
    }

    @Nullable
    public Interpolator getDragStartItemRotationAnimationInterpolator() {
        return this.mDraggingItemEffectsInfo.rotationInterpolator;
    }

    public void setDragStartItemAlphaAnimationInterpolator(Interpolator interpolator) {
        this.mDraggingItemEffectsInfo.alphaInterpolator = interpolator;
    }

    @Nullable
    public Interpolator getDragStartItemAlphaAnimationInterpolator() {
        return this.mDraggingItemEffectsInfo.alphaInterpolator;
    }

    public void setDraggingItemScale(float scale) {
        this.mDraggingItemEffectsInfo.scale = scale;
    }

    public float getDraggingItemScale() {
        return this.mDraggingItemEffectsInfo.scale;
    }

    public void setDraggingItemRotation(float rotation) {
        this.mDraggingItemEffectsInfo.rotation = rotation;
    }

    public float getDraggingItemRotation() {
        return this.mDraggingItemEffectsInfo.rotation;
    }

    public void setDraggingItemAlpha(@FloatRange(from = 0.0d, to = 1.0d) float alpha) {
        this.mDraggingItemEffectsInfo.alpha = alpha;
    }

    public float getDraggingItemAlpha() {
        return this.mDraggingItemEffectsInfo.alpha;
    }

    void onItemViewRecycled(ViewHolder holder) {
        if (holder == this.mDraggingItemViewHolder) {
            onDraggingItemViewRecycled();
        } else if (this.mSwapTargetItemOperator != null) {
            this.mSwapTargetItemOperator.onItemViewRecycled(holder);
        }
    }

    ViewHolder getDraggingItemViewHolder() {
        return this.mDraggingItemViewHolder;
    }

    void onNewDraggingItemViewBound(ViewHolder holder) {
        if (this.mDraggingItemViewHolder != null) {
            onDraggingItemViewRecycled();
        }
        this.mDraggingItemViewHolder = holder;
        this.mDraggingItemDecorator.setDraggingItemViewHolder(holder);
    }

    private void onDraggingItemViewRecycled() {
        Log.i(TAG, "a view holder object which is bound to currently dragging item is recycled");
        this.mDraggingItemViewHolder = null;
        this.mDraggingItemDecorator.invalidateDraggingItem();
    }

    private int getWrappedAdapterPosition(ViewHolder vh) {
        if (vh == null) {
            return -1;
        }
        return WrapperAdapterUtils.unwrapPosition(this.mRecyclerView.getAdapter(), this.mWrapperAdapter, this.mComposedAdapterTag, vh.getAdapterPosition());
    }

    private ItemDraggableRange convertToRootAdapterRange(AdapterPath path, ItemDraggableRange src) {
        Adapter rootAdapter = this.mRecyclerView.getAdapter();
        return new ItemDraggableRange(WrapperAdapterUtils.wrapPosition(path, this.mWrapperAdapter, rootAdapter, src.getStart()), WrapperAdapterUtils.wrapPosition(path, this.mWrapperAdapter, rootAdapter, src.getEnd()));
    }
}
