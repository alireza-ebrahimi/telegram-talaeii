package org.telegram.messenger.support.widget.helper;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.p017a.C0211a;
import android.support.v4.p017a.C0212b;
import android.support.v4.p017a.C0214d;
import android.support.v4.p017a.C0216g;
import android.support.v4.view.C0621e;
import android.support.v4.view.C0659t;
import android.support.v4.view.af;
import android.support.v4.view.ah;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.ChildDrawingOrderCallback;
import org.telegram.messenger.support.widget.RecyclerView.ItemAnimator;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnChildAttachStateChangeListener;
import org.telegram.messenger.support.widget.RecyclerView.OnItemTouchListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;

public class ItemTouchHelper extends ItemDecoration implements OnChildAttachStateChangeListener {
    static final int ACTION_MODE_DRAG_MASK = 16711680;
    private static final int ACTION_MODE_IDLE_MASK = 255;
    static final int ACTION_MODE_SWIPE_MASK = 65280;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    static final boolean DEBUG = false;
    static final int DIRECTION_FLAG_COUNT = 8;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    private static final int PIXELS_PER_SECOND = 1000;
    public static final int RIGHT = 8;
    public static final int START = 16;
    static final String TAG = "ItemTouchHelper";
    public static final int UP = 1;
    int mActionState = 0;
    int mActivePointerId = -1;
    Callback mCallback;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    C0621e mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    float mMaxSwipeVelocity;
    private final OnItemTouchListener mOnItemTouchListener = new C36882();
    View mOverdrawChild = null;
    int mOverdrawChildPosition = -1;
    final List<View> mPendingCleanup = new ArrayList();
    List<RecoverAnimation> mRecoverAnimations = new ArrayList();
    RecyclerView mRecyclerView;
    final Runnable mScrollRunnable = new C36871();
    ViewHolder mSelected = null;
    int mSelectedFlags;
    float mSelectedStartX;
    float mSelectedStartY;
    private int mSlop;
    private List<ViewHolder> mSwapTargets;
    float mSwipeEscapeVelocity;
    private final float[] mTmpPosition = new float[2];
    private Rect mTmpRect;
    VelocityTracker mVelocityTracker;

    public interface ViewDropHandler {
        void prepareForDrop(View view, View view2, int i, int i2);
    }

    /* renamed from: org.telegram.messenger.support.widget.helper.ItemTouchHelper$1 */
    class C36871 implements Runnable {
        C36871() {
        }

        public void run() {
            if (ItemTouchHelper.this.mSelected != null && ItemTouchHelper.this.scrollIfNecessary()) {
                if (ItemTouchHelper.this.mSelected != null) {
                    ItemTouchHelper.this.moveIfNecessary(ItemTouchHelper.this.mSelected);
                }
                ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                ah.m2787a(ItemTouchHelper.this.mRecyclerView, (Runnable) this);
            }
        }
    }

    /* renamed from: org.telegram.messenger.support.widget.helper.ItemTouchHelper$2 */
    class C36882 implements OnItemTouchListener {
        C36882() {
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            ItemTouchHelper.this.mGestureDetector.m3113a(motionEvent);
            int a = C0659t.m3205a(motionEvent);
            if (a == 0) {
                ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(0);
                ItemTouchHelper.this.mInitialTouchX = motionEvent.getX();
                ItemTouchHelper.this.mInitialTouchY = motionEvent.getY();
                ItemTouchHelper.this.obtainVelocityTracker();
                if (ItemTouchHelper.this.mSelected == null) {
                    RecoverAnimation findAnimation = ItemTouchHelper.this.findAnimation(motionEvent);
                    if (findAnimation != null) {
                        ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                        itemTouchHelper.mInitialTouchX -= findAnimation.mX;
                        itemTouchHelper = ItemTouchHelper.this;
                        itemTouchHelper.mInitialTouchY -= findAnimation.mY;
                        ItemTouchHelper.this.endRecoverAnimation(findAnimation.mViewHolder, true);
                        if (ItemTouchHelper.this.mPendingCleanup.remove(findAnimation.mViewHolder.itemView)) {
                            ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, findAnimation.mViewHolder);
                        }
                        ItemTouchHelper.this.select(findAnimation.mViewHolder, findAnimation.mActionState);
                        ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, 0);
                    }
                }
            } else if (a == 3 || a == 1) {
                ItemTouchHelper.this.mActivePointerId = -1;
                ItemTouchHelper.this.select(null, 0);
            } else if (ItemTouchHelper.this.mActivePointerId != -1) {
                int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                if (findPointerIndex >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(a, motionEvent, findPointerIndex);
                }
            }
            if (ItemTouchHelper.this.mVelocityTracker != null) {
                ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
            }
            return ItemTouchHelper.this.mSelected != null;
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                ItemTouchHelper.this.select(null, 0);
            }
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            int i = 0;
            ItemTouchHelper.this.mGestureDetector.m3113a(motionEvent);
            if (ItemTouchHelper.this.mVelocityTracker != null) {
                ItemTouchHelper.this.mVelocityTracker.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.mActivePointerId != -1) {
                int a = C0659t.m3205a(motionEvent);
                int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                if (findPointerIndex >= 0) {
                    ItemTouchHelper.this.checkSelectForSwipe(a, motionEvent, findPointerIndex);
                }
                ViewHolder viewHolder = ItemTouchHelper.this.mSelected;
                if (viewHolder != null) {
                    switch (a) {
                        case 1:
                            break;
                        case 2:
                            if (findPointerIndex >= 0) {
                                ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, findPointerIndex);
                                ItemTouchHelper.this.moveIfNecessary(viewHolder);
                                ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                                ItemTouchHelper.this.mScrollRunnable.run();
                                ItemTouchHelper.this.mRecyclerView.invalidate();
                                return;
                            }
                            return;
                        case 3:
                            if (ItemTouchHelper.this.mVelocityTracker != null) {
                                ItemTouchHelper.this.mVelocityTracker.clear();
                                break;
                            }
                            break;
                        case 6:
                            a = C0659t.m3206b(motionEvent);
                            if (motionEvent.getPointerId(a) == ItemTouchHelper.this.mActivePointerId) {
                                if (a == 0) {
                                    i = 1;
                                }
                                ItemTouchHelper.this.mActivePointerId = motionEvent.getPointerId(i);
                                ItemTouchHelper.this.updateDxDy(motionEvent, ItemTouchHelper.this.mSelectedFlags, a);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                    ItemTouchHelper.this.select(null, 0);
                    ItemTouchHelper.this.mActivePointerId = -1;
                }
            }
        }
    }

    private class RecoverAnimation implements C0212b {
        final int mActionState;
        final int mAnimationType;
        boolean mEnded = false;
        private float mFraction;
        public boolean mIsPendingCleanup;
        boolean mOverridden = false;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        private final C0216g mValueAnimator;
        final ViewHolder mViewHolder;
        float mX;
        float mY;

        public RecoverAnimation(ViewHolder viewHolder, int i, int i2, float f, float f2, float f3, float f4) {
            this.mActionState = i2;
            this.mAnimationType = i;
            this.mViewHolder = viewHolder;
            this.mStartDx = f;
            this.mStartDy = f2;
            this.mTargetX = f3;
            this.mTargetY = f4;
            this.mValueAnimator = C0211a.m992a();
            this.mValueAnimator.mo193a(new C0214d(ItemTouchHelper.this) {
                public void onAnimationUpdate(C0216g c0216g) {
                    RecoverAnimation.this.setFraction(c0216g.mo196c());
                }
            });
            this.mValueAnimator.mo194a(viewHolder.itemView);
            this.mValueAnimator.mo192a((C0212b) this);
            setFraction(BitmapDescriptorFactory.HUE_RED);
        }

        public void cancel() {
            this.mValueAnimator.mo195b();
        }

        public void onAnimationCancel(C0216g c0216g) {
            setFraction(1.0f);
        }

        public void onAnimationEnd(C0216g c0216g) {
            if (!this.mEnded) {
                this.mViewHolder.setIsRecyclable(true);
            }
            this.mEnded = true;
        }

        public void onAnimationRepeat(C0216g c0216g) {
        }

        public void onAnimationStart(C0216g c0216g) {
        }

        public void setDuration(long j) {
            this.mValueAnimator.mo191a(j);
        }

        public void setFraction(float f) {
            this.mFraction = f;
        }

        public void start() {
            this.mViewHolder.setIsRecyclable(false);
            this.mValueAnimator.mo190a();
        }

        public void update() {
            if (this.mStartDx == this.mTargetX) {
                this.mX = ah.m2822l(this.mViewHolder.itemView);
            } else {
                this.mX = this.mStartDx + (this.mFraction * (this.mTargetX - this.mStartDx));
            }
            if (this.mStartDy == this.mTargetY) {
                this.mY = ah.m2823m(this.mViewHolder.itemView);
            } else {
                this.mY = this.mStartDy + (this.mFraction * (this.mTargetY - this.mStartDy));
            }
        }
    }

    /* renamed from: org.telegram.messenger.support.widget.helper.ItemTouchHelper$5 */
    class C36915 implements ChildDrawingOrderCallback {
        C36915() {
        }

        public int onGetChildDrawingOrder(int i, int i2) {
            if (ItemTouchHelper.this.mOverdrawChild == null) {
                return i2;
            }
            int i3 = ItemTouchHelper.this.mOverdrawChildPosition;
            if (i3 == -1) {
                i3 = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
                ItemTouchHelper.this.mOverdrawChildPosition = i3;
            }
            return i2 == i + -1 ? i3 : i2 >= i3 ? i2 + 1 : i2;
        }
    }

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 500;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator = new C36921();
        private static final Interpolator sDragViewScrollCapInterpolator = new C36932();
        private static final ItemTouchUIUtil sUICallback;
        private int mCachedMaxScrollSpeed = -1;

        /* renamed from: org.telegram.messenger.support.widget.helper.ItemTouchHelper$Callback$1 */
        static class C36921 implements Interpolator {
            C36921() {
            }

            public float getInterpolation(float f) {
                return (((f * f) * f) * f) * f;
            }
        }

        /* renamed from: org.telegram.messenger.support.widget.helper.ItemTouchHelper$Callback$2 */
        static class C36932 implements Interpolator {
            C36932() {
            }

            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * (((f2 * f2) * f2) * f2)) + 1.0f;
            }
        }

        static {
            if (VERSION.SDK_INT >= 21) {
                sUICallback = new Lollipop();
            } else if (VERSION.SDK_INT >= 11) {
                sUICallback = new Honeycomb();
            } else {
                sUICallback = new Gingerbread();
            }
        }

        public static int convertToRelativeDirection(int i, int i2) {
            int i3 = i & ABS_HORIZONTAL_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = (i3 ^ -1) & i;
            return i2 == 0 ? i4 | (i3 << 2) : (i4 | ((i3 << 1) & -789517)) | (((i3 << 1) & ABS_HORIZONTAL_DIR_FLAGS) << 2);
        }

        public static ItemTouchUIUtil getDefaultUIUtil() {
            return sUICallback;
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = AndroidUtilities.dp(20.0f);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public static int makeFlag(int i, int i2) {
            return i2 << (i * 8);
        }

        public static int makeMovementFlags(int i, int i2) {
            return (makeFlag(0, i2 | i) | makeFlag(1, i2)) | makeFlag(2, i);
        }

        public boolean canDropOver(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
            return true;
        }

        public ViewHolder chooseDropTarget(ViewHolder viewHolder, List<ViewHolder> list, int i, int i2) {
            int width = i + viewHolder.itemView.getWidth();
            int height = i2 + viewHolder.itemView.getHeight();
            ViewHolder viewHolder2 = null;
            int i3 = -1;
            int left = i - viewHolder.itemView.getLeft();
            int top = i2 - viewHolder.itemView.getTop();
            int size = list.size();
            int i4 = 0;
            while (i4 < size) {
                int i5;
                ViewHolder viewHolder3;
                int i6;
                ViewHolder viewHolder4;
                int i7;
                ViewHolder viewHolder5 = (ViewHolder) list.get(i4);
                if (left > 0) {
                    int right = viewHolder5.itemView.getRight() - width;
                    if (right < 0 && viewHolder5.itemView.getRight() > viewHolder.itemView.getRight()) {
                        right = Math.abs(right);
                        if (right > i3) {
                            i5 = right;
                            viewHolder3 = viewHolder5;
                            if (left < 0) {
                                i3 = viewHolder5.itemView.getLeft() - i;
                                if (i3 > 0 && viewHolder5.itemView.getLeft() < viewHolder.itemView.getLeft()) {
                                    i3 = Math.abs(i3);
                                    if (i3 > i5) {
                                        viewHolder3 = viewHolder5;
                                        if (top < 0) {
                                            i5 = viewHolder5.itemView.getTop() - i2;
                                            if (i5 > 0 && viewHolder5.itemView.getTop() < viewHolder.itemView.getTop()) {
                                                i5 = Math.abs(i5);
                                                if (i5 > i3) {
                                                    viewHolder3 = viewHolder5;
                                                    if (top > 0) {
                                                        i3 = viewHolder5.itemView.getBottom() - height;
                                                        if (i3 < 0 && viewHolder5.itemView.getBottom() > viewHolder.itemView.getBottom()) {
                                                            i3 = Math.abs(i3);
                                                            if (i3 > i5) {
                                                                i6 = i3;
                                                                viewHolder4 = viewHolder5;
                                                                i7 = i6;
                                                                i4++;
                                                                viewHolder2 = viewHolder4;
                                                                i3 = i7;
                                                            }
                                                        }
                                                    }
                                                    i7 = i5;
                                                    viewHolder4 = viewHolder3;
                                                    i4++;
                                                    viewHolder2 = viewHolder4;
                                                    i3 = i7;
                                                }
                                            }
                                        }
                                        i5 = i3;
                                        if (top > 0) {
                                            i3 = viewHolder5.itemView.getBottom() - height;
                                            i3 = Math.abs(i3);
                                            if (i3 > i5) {
                                                i6 = i3;
                                                viewHolder4 = viewHolder5;
                                                i7 = i6;
                                                i4++;
                                                viewHolder2 = viewHolder4;
                                                i3 = i7;
                                            }
                                        }
                                        i7 = i5;
                                        viewHolder4 = viewHolder3;
                                        i4++;
                                        viewHolder2 = viewHolder4;
                                        i3 = i7;
                                    }
                                }
                            }
                            i3 = i5;
                            if (top < 0) {
                                i5 = viewHolder5.itemView.getTop() - i2;
                                i5 = Math.abs(i5);
                                if (i5 > i3) {
                                    viewHolder3 = viewHolder5;
                                    if (top > 0) {
                                        i3 = viewHolder5.itemView.getBottom() - height;
                                        i3 = Math.abs(i3);
                                        if (i3 > i5) {
                                            i6 = i3;
                                            viewHolder4 = viewHolder5;
                                            i7 = i6;
                                            i4++;
                                            viewHolder2 = viewHolder4;
                                            i3 = i7;
                                        }
                                    }
                                    i7 = i5;
                                    viewHolder4 = viewHolder3;
                                    i4++;
                                    viewHolder2 = viewHolder4;
                                    i3 = i7;
                                }
                            }
                            i5 = i3;
                            if (top > 0) {
                                i3 = viewHolder5.itemView.getBottom() - height;
                                i3 = Math.abs(i3);
                                if (i3 > i5) {
                                    i6 = i3;
                                    viewHolder4 = viewHolder5;
                                    i7 = i6;
                                    i4++;
                                    viewHolder2 = viewHolder4;
                                    i3 = i7;
                                }
                            }
                            i7 = i5;
                            viewHolder4 = viewHolder3;
                            i4++;
                            viewHolder2 = viewHolder4;
                            i3 = i7;
                        }
                    }
                }
                viewHolder3 = viewHolder2;
                i5 = i3;
                if (left < 0) {
                    i3 = viewHolder5.itemView.getLeft() - i;
                    i3 = Math.abs(i3);
                    if (i3 > i5) {
                        viewHolder3 = viewHolder5;
                        if (top < 0) {
                            i5 = viewHolder5.itemView.getTop() - i2;
                            i5 = Math.abs(i5);
                            if (i5 > i3) {
                                viewHolder3 = viewHolder5;
                                if (top > 0) {
                                    i3 = viewHolder5.itemView.getBottom() - height;
                                    i3 = Math.abs(i3);
                                    if (i3 > i5) {
                                        i6 = i3;
                                        viewHolder4 = viewHolder5;
                                        i7 = i6;
                                        i4++;
                                        viewHolder2 = viewHolder4;
                                        i3 = i7;
                                    }
                                }
                                i7 = i5;
                                viewHolder4 = viewHolder3;
                                i4++;
                                viewHolder2 = viewHolder4;
                                i3 = i7;
                            }
                        }
                        i5 = i3;
                        if (top > 0) {
                            i3 = viewHolder5.itemView.getBottom() - height;
                            i3 = Math.abs(i3);
                            if (i3 > i5) {
                                i6 = i3;
                                viewHolder4 = viewHolder5;
                                i7 = i6;
                                i4++;
                                viewHolder2 = viewHolder4;
                                i3 = i7;
                            }
                        }
                        i7 = i5;
                        viewHolder4 = viewHolder3;
                        i4++;
                        viewHolder2 = viewHolder4;
                        i3 = i7;
                    }
                }
                i3 = i5;
                if (top < 0) {
                    i5 = viewHolder5.itemView.getTop() - i2;
                    i5 = Math.abs(i5);
                    if (i5 > i3) {
                        viewHolder3 = viewHolder5;
                        if (top > 0) {
                            i3 = viewHolder5.itemView.getBottom() - height;
                            i3 = Math.abs(i3);
                            if (i3 > i5) {
                                i6 = i3;
                                viewHolder4 = viewHolder5;
                                i7 = i6;
                                i4++;
                                viewHolder2 = viewHolder4;
                                i3 = i7;
                            }
                        }
                        i7 = i5;
                        viewHolder4 = viewHolder3;
                        i4++;
                        viewHolder2 = viewHolder4;
                        i3 = i7;
                    }
                }
                i5 = i3;
                if (top > 0) {
                    i3 = viewHolder5.itemView.getBottom() - height;
                    i3 = Math.abs(i3);
                    if (i3 > i5) {
                        i6 = i3;
                        viewHolder4 = viewHolder5;
                        i7 = i6;
                        i4++;
                        viewHolder2 = viewHolder4;
                        i3 = i7;
                    }
                }
                i7 = i5;
                viewHolder4 = viewHolder3;
                i4++;
                viewHolder2 = viewHolder4;
                i3 = i7;
            }
            return viewHolder2;
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            sUICallback.clearView(viewHolder.itemView);
        }

        public int convertToAbsoluteDirection(int i, int i2) {
            int i3 = i & RELATIVE_DIR_FLAGS;
            if (i3 == 0) {
                return i;
            }
            int i4 = (i3 ^ -1) & i;
            return i2 == 0 ? i4 | (i3 >> 2) : (i4 | ((i3 >> 1) & -3158065)) | (((i3 >> 1) & RELATIVE_DIR_FLAGS) >> 2);
        }

        final int getAbsoluteMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), ah.m2812g(recyclerView));
        }

        public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
            ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            return itemAnimator == null ? i == 8 ? 200 : 250 : i == 8 ? itemAnimator.getMoveDuration() : itemAnimator.getRemoveDuration();
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder);

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }

        public float getSwipeThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f) {
            return f;
        }

        boolean hasDragFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & ItemTouchHelper.ACTION_MODE_DRAG_MASK) != 0;
        }

        boolean hasSwipeFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (getAbsoluteMovementFlags(recyclerView, viewHolder) & ItemTouchHelper.ACTION_MODE_SWIPE_MASK) != 0;
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, int i3, long j) {
            float f = 1.0f;
            int maxDragScroll = (int) (((float) (getMaxDragScroll(recyclerView) * ((int) Math.signum((float) i2)))) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(1.0f, (((float) Math.abs(i2)) * 1.0f) / ((float) i))));
            if (j <= DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS) {
                f = ((float) j) / 500.0f;
            }
            int interpolation = (int) (sDragScrollInterpolator.getInterpolation(f) * ((float) maxDragScroll));
            return interpolation == 0 ? i2 > 0 ? 1 : -1 : interpolation;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            sUICallback.onDraw(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            sUICallback.onDrawOver(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        void onDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            int i2;
            int size = list.size();
            for (i2 = 0; i2 < size; i2++) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) list.get(i2);
                recoverAnimation.update();
                int save = canvas.save();
                onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                canvas.restoreToCount(save);
            }
            if (viewHolder != null) {
                i2 = canvas.save();
                onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(i2);
            }
        }

        void onDrawOver(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, List<RecoverAnimation> list, int i, float f, float f2) {
            int i2;
            int size = list.size();
            for (i2 = 0; i2 < size; i2++) {
                RecoverAnimation recoverAnimation = (RecoverAnimation) list.get(i2);
                int save = canvas.save();
                onChildDrawOver(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
                canvas.restoreToCount(save);
            }
            if (viewHolder != null) {
                i2 = canvas.save();
                onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(i2);
            }
            Object obj = null;
            int i3 = size - 1;
            while (i3 >= 0) {
                Object obj2;
                recoverAnimation = (RecoverAnimation) list.get(i3);
                if (!recoverAnimation.mEnded || recoverAnimation.mIsPendingCleanup) {
                    obj2 = !recoverAnimation.mEnded ? 1 : obj;
                } else {
                    list.remove(i3);
                    obj2 = obj;
                }
                i3--;
                obj = obj2;
            }
            if (obj != null) {
                recyclerView.invalidate();
            }
        }

        public abstract boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2);

        public void onMoved(RecyclerView recyclerView, ViewHolder viewHolder, int i, ViewHolder viewHolder2, int i2, int i3, int i4) {
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, i3, i4);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
        }

        public void onSelectedChanged(ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                sUICallback.onSelected(viewHolder.itemView);
            }
        }

        public abstract void onSwiped(ViewHolder viewHolder, int i);
    }

    private class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        ItemTouchHelperGestureListener() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            View findChildView = ItemTouchHelper.this.findChildView(motionEvent);
            if (findChildView != null) {
                ViewHolder childViewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(findChildView);
                if (childViewHolder != null && ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, childViewHolder) && motionEvent.getPointerId(0) == ItemTouchHelper.this.mActivePointerId) {
                    int findPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    ItemTouchHelper.this.mInitialTouchX = x;
                    ItemTouchHelper.this.mInitialTouchY = y;
                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                    ItemTouchHelper.this.mDy = BitmapDescriptorFactory.HUE_RED;
                    itemTouchHelper.mDx = BitmapDescriptorFactory.HUE_RED;
                    if (ItemTouchHelper.this.mCallback.isLongPressDragEnabled()) {
                        ItemTouchHelper.this.select(childViewHolder, 2);
                    }
                }
            }
        }
    }

    public static abstract class SimpleCallback extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int i, int i2) {
            this.mDefaultSwipeDirs = i2;
            this.mDefaultDragDirs = i;
        }

        public int getDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return this.mDefaultDragDirs;
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            return Callback.makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }

        public int getSwipeDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return this.mDefaultSwipeDirs;
        }

        public void setDefaultDragDirs(int i) {
            this.mDefaultDragDirs = i;
        }

        public void setDefaultSwipeDirs(int i) {
            this.mDefaultSwipeDirs = i;
        }
    }

    public ItemTouchHelper(Callback callback) {
        this.mCallback = callback;
    }

    private void addChildDrawingOrderCallback() {
        if (VERSION.SDK_INT < 21) {
            if (this.mChildDrawingOrderCallback == null) {
                this.mChildDrawingOrderCallback = new C36915();
            }
            this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
        }
    }

    private int checkHorizontalSwipe(ViewHolder viewHolder, int i) {
        int i2 = 8;
        if ((i & 12) != 0) {
            int i3 = this.mDx > BitmapDescriptorFactory.HUE_RED ? 8 : 4;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                float a = af.m2516a(this.mVelocityTracker, this.mActivePointerId);
                float b = af.m2517b(this.mVelocityTracker, this.mActivePointerId);
                if (a <= BitmapDescriptorFactory.HUE_RED) {
                    i2 = 4;
                }
                float abs = Math.abs(a);
                if ((i2 & i) != 0 && i3 == i2 && abs >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && abs > Math.abs(b)) {
                    return i2;
                }
            }
            float width = ((float) this.mRecyclerView.getWidth()) * this.mCallback.getSwipeThreshold(viewHolder);
            if ((i & i3) != 0 && Math.abs(this.mDx) > width) {
                return i3;
            }
        }
        return 0;
    }

    private int checkVerticalSwipe(ViewHolder viewHolder, int i) {
        int i2 = 2;
        if ((i & 3) != 0) {
            int i3 = this.mDy > BitmapDescriptorFactory.HUE_RED ? 2 : 1;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
                float a = af.m2516a(this.mVelocityTracker, this.mActivePointerId);
                float b = af.m2517b(this.mVelocityTracker, this.mActivePointerId);
                if (b <= BitmapDescriptorFactory.HUE_RED) {
                    i2 = 1;
                }
                float abs = Math.abs(b);
                if ((i2 & i) != 0 && i2 == i3 && abs >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && abs > Math.abs(a)) {
                    return i2;
                }
            }
            float height = ((float) this.mRecyclerView.getHeight()) * this.mCallback.getSwipeThreshold(viewHolder);
            if ((i & i3) != 0 && Math.abs(this.mDy) > height) {
                return i3;
            }
        }
        return 0;
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            this.mCallback.clearView(this.mRecyclerView, ((RecoverAnimation) this.mRecoverAnimations.get(0)).mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        releaseVelocityTracker();
    }

    private List<ViewHolder> findSwapTargets(ViewHolder viewHolder) {
        if (this.mSwapTargets == null) {
            this.mSwapTargets = new ArrayList();
            this.mDistances = new ArrayList();
        } else {
            this.mSwapTargets.clear();
            this.mDistances.clear();
        }
        int boundingBoxMargin = this.mCallback.getBoundingBoxMargin();
        int round = Math.round(this.mSelectedStartX + this.mDx) - boundingBoxMargin;
        int round2 = Math.round(this.mSelectedStartY + this.mDy) - boundingBoxMargin;
        int width = (viewHolder.itemView.getWidth() + round) + (boundingBoxMargin * 2);
        int height = (viewHolder.itemView.getHeight() + round2) + (boundingBoxMargin * 2);
        int i = (round + width) / 2;
        int i2 = (round2 + height) / 2;
        LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        int childCount = layoutManager.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = layoutManager.getChildAt(i3);
            if (childAt != viewHolder.itemView && childAt.getBottom() >= round2 && childAt.getTop() <= height && childAt.getRight() >= round && childAt.getLeft() <= width) {
                ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(childAt);
                if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, childViewHolder)) {
                    int abs = Math.abs(i - ((childAt.getLeft() + childAt.getRight()) / 2));
                    boundingBoxMargin = Math.abs(i2 - ((childAt.getBottom() + childAt.getTop()) / 2));
                    int i4 = (abs * abs) + (boundingBoxMargin * boundingBoxMargin);
                    int size = this.mSwapTargets.size();
                    int i5 = 0;
                    abs = 0;
                    while (abs < size && i4 > ((Integer) this.mDistances.get(abs)).intValue()) {
                        i5++;
                        abs++;
                    }
                    this.mSwapTargets.add(i5, childViewHolder);
                    this.mDistances.add(i5, Integer.valueOf(i4));
                }
            }
        }
        return this.mSwapTargets;
    }

    private ViewHolder findSwipedView(MotionEvent motionEvent) {
        LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (this.mActivePointerId == -1) {
            return null;
        }
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        float x = motionEvent.getX(findPointerIndex) - this.mInitialTouchX;
        float y = motionEvent.getY(findPointerIndex) - this.mInitialTouchY;
        x = Math.abs(x);
        y = Math.abs(y);
        if (x < ((float) this.mSlop) && y < ((float) this.mSlop)) {
            return null;
        }
        if (x > y && layoutManager.canScrollHorizontally()) {
            return null;
        }
        if (y > x && layoutManager.canScrollVertically()) {
            return null;
        }
        View findChildView = findChildView(motionEvent);
        return findChildView != null ? this.mRecyclerView.getChildViewHolder(findChildView) : null;
    }

    private void getSelectedDxDy(float[] fArr) {
        if ((this.mSelectedFlags & 12) != 0) {
            fArr[0] = (this.mSelectedStartX + this.mDx) - ((float) this.mSelected.itemView.getLeft());
        } else {
            fArr[0] = ah.m2822l(this.mSelected.itemView);
        }
        if ((this.mSelectedFlags & 3) != 0) {
            fArr[1] = (this.mSelectedStartY + this.mDy) - ((float) this.mSelected.itemView.getTop());
        } else {
            fArr[1] = ah.m2823m(this.mSelected.itemView);
        }
    }

    private static boolean hitTest(View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= ((float) view.getWidth()) + f3 && f2 >= f4 && f2 <= ((float) view.getHeight()) + f4;
    }

    private void initGestureDetector() {
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new C0621e(this.mRecyclerView.getContext(), new ItemTouchHelperGestureListener());
        }
    }

    private void releaseVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setupCallbacks() {
        this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        initGestureDetector();
    }

    private int swipeIfNecessary(ViewHolder viewHolder) {
        if (this.mActionState == 2) {
            return 0;
        }
        int movementFlags = this.mCallback.getMovementFlags(this.mRecyclerView, viewHolder);
        int convertToAbsoluteDirection = (this.mCallback.convertToAbsoluteDirection(movementFlags, ah.m2812g(this.mRecyclerView)) & ACTION_MODE_SWIPE_MASK) >> 8;
        if (convertToAbsoluteDirection == 0) {
            return 0;
        }
        int i = (movementFlags & ACTION_MODE_SWIPE_MASK) >> 8;
        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
            movementFlags = checkHorizontalSwipe(viewHolder, convertToAbsoluteDirection);
            if (movementFlags > 0) {
                return (i & movementFlags) == 0 ? Callback.convertToRelativeDirection(movementFlags, ah.m2812g(this.mRecyclerView)) : movementFlags;
            } else {
                movementFlags = checkVerticalSwipe(viewHolder, convertToAbsoluteDirection);
                return movementFlags > 0 ? movementFlags : 0;
            }
        } else {
            movementFlags = checkVerticalSwipe(viewHolder, convertToAbsoluteDirection);
            if (movementFlags > 0) {
                return movementFlags;
            }
            movementFlags = checkHorizontalSwipe(viewHolder, convertToAbsoluteDirection);
            return movementFlags > 0 ? (i & movementFlags) == 0 ? Callback.convertToRelativeDirection(movementFlags, ah.m2812g(this.mRecyclerView)) : movementFlags : 0;
        }
    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (this.mRecyclerView != recyclerView) {
            if (this.mRecyclerView != null) {
                destroyCallbacks();
            }
            this.mRecyclerView = recyclerView;
            if (this.mRecyclerView != null) {
                recyclerView.getResources();
                this.mSwipeEscapeVelocity = (float) AndroidUtilities.dp(120.0f);
                this.mMaxSwipeVelocity = (float) AndroidUtilities.dp(800.0f);
                setupCallbacks();
            }
        }
    }

    boolean checkSelectForSwipe(int i, MotionEvent motionEvent, int i2) {
        if (this.mSelected != null || i != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled() || this.mRecyclerView.getScrollState() == 1) {
            return false;
        }
        ViewHolder findSwipedView = findSwipedView(motionEvent);
        if (findSwipedView == null) {
            return false;
        }
        int absoluteMovementFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, findSwipedView) & ACTION_MODE_SWIPE_MASK) >> 8;
        if (absoluteMovementFlags == 0) {
            return false;
        }
        float x = motionEvent.getX(i2);
        x -= this.mInitialTouchX;
        float y = motionEvent.getY(i2) - this.mInitialTouchY;
        float abs = Math.abs(x);
        float abs2 = Math.abs(y);
        if (abs < ((float) this.mSlop) && abs2 < ((float) this.mSlop)) {
            return false;
        }
        if (abs > abs2) {
            if (x < BitmapDescriptorFactory.HUE_RED && (absoluteMovementFlags & 4) == 0) {
                return false;
            }
            if (x > BitmapDescriptorFactory.HUE_RED && (absoluteMovementFlags & 8) == 0) {
                return false;
            }
        } else if (y < BitmapDescriptorFactory.HUE_RED && (absoluteMovementFlags & 1) == 0) {
            return false;
        } else {
            if (y > BitmapDescriptorFactory.HUE_RED && (absoluteMovementFlags & 2) == 0) {
                return false;
            }
        }
        this.mDy = BitmapDescriptorFactory.HUE_RED;
        this.mDx = BitmapDescriptorFactory.HUE_RED;
        this.mActivePointerId = motionEvent.getPointerId(0);
        select(findSwipedView, 1);
        return true;
    }

    int endRecoverAnimation(ViewHolder viewHolder, boolean z) {
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.mRecoverAnimations.get(size);
            if (recoverAnimation.mViewHolder == viewHolder) {
                recoverAnimation.mOverridden |= z;
                if (!recoverAnimation.mEnded) {
                    recoverAnimation.cancel();
                }
                this.mRecoverAnimations.remove(size);
                return recoverAnimation.mAnimationType;
            }
        }
        return 0;
    }

    RecoverAnimation findAnimation(MotionEvent motionEvent) {
        if (this.mRecoverAnimations.isEmpty()) {
            return null;
        }
        View findChildView = findChildView(motionEvent);
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.mRecoverAnimations.get(size);
            if (recoverAnimation.mViewHolder.itemView == findChildView) {
                return recoverAnimation;
            }
        }
        return null;
    }

    View findChildView(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.mSelected != null) {
            View view = this.mSelected.itemView;
            if (hitTest(view, x, y, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return view;
            }
        }
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            RecoverAnimation recoverAnimation = (RecoverAnimation) this.mRecoverAnimations.get(size);
            View view2 = recoverAnimation.mViewHolder.itemView;
            if (hitTest(view2, x, y, recoverAnimation.mX, recoverAnimation.mY)) {
                return view2;
            }
        }
        return this.mRecyclerView.findChildViewUnder(x, y);
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        rect.setEmpty();
    }

    boolean hasRunningRecoverAnim() {
        int size = this.mRecoverAnimations.size();
        for (int i = 0; i < size; i++) {
            if (!((RecoverAnimation) this.mRecoverAnimations.get(i)).mEnded) {
                return true;
            }
        }
        return false;
    }

    void moveIfNecessary(ViewHolder viewHolder) {
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            float moveThreshold = this.mCallback.getMoveThreshold(viewHolder);
            int i = (int) (this.mSelectedStartX + this.mDx);
            int i2 = (int) (this.mSelectedStartY + this.mDy);
            if (((float) Math.abs(i2 - viewHolder.itemView.getTop())) >= ((float) viewHolder.itemView.getHeight()) * moveThreshold || ((float) Math.abs(i - viewHolder.itemView.getLeft())) >= moveThreshold * ((float) viewHolder.itemView.getWidth())) {
                List findSwapTargets = findSwapTargets(viewHolder);
                if (findSwapTargets.size() != 0) {
                    ViewHolder chooseDropTarget = this.mCallback.chooseDropTarget(viewHolder, findSwapTargets, i, i2);
                    if (chooseDropTarget == null) {
                        this.mSwapTargets.clear();
                        this.mDistances.clear();
                        return;
                    }
                    int adapterPosition = chooseDropTarget.getAdapterPosition();
                    int adapterPosition2 = viewHolder.getAdapterPosition();
                    if (this.mCallback.onMove(this.mRecyclerView, viewHolder, chooseDropTarget)) {
                        this.mCallback.onMoved(this.mRecyclerView, viewHolder, adapterPosition2, chooseDropTarget, adapterPosition, i, i2);
                    }
                }
            }
        }
    }

    void obtainVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    public void onChildViewAttachedToWindow(View view) {
    }

    public void onChildViewDetachedFromWindow(View view) {
        removeChildDrawingOrderCallbackIfNecessary(view);
        ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(view);
        if (childViewHolder != null) {
            if (this.mSelected == null || childViewHolder != this.mSelected) {
                endRecoverAnimation(childViewHolder, false);
                if (this.mPendingCleanup.remove(childViewHolder.itemView)) {
                    this.mCallback.clearView(this.mRecyclerView, childViewHolder);
                    return;
                }
                return;
            }
            select(null, 0);
        }
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        float f;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        this.mOverdrawChildPosition = -1;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            f = this.mTmpPosition[0];
            f2 = this.mTmpPosition[1];
        } else {
            f = BitmapDescriptorFactory.HUE_RED;
        }
        this.mCallback.onDraw(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f, f2);
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        float f;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            f = this.mTmpPosition[0];
            f2 = this.mTmpPosition[1];
        } else {
            f = BitmapDescriptorFactory.HUE_RED;
        }
        this.mCallback.onDrawOver(canvas, recyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f, f2);
    }

    void postDispatchSwipe(final RecoverAnimation recoverAnimation, final int i) {
        this.mRecyclerView.post(new Runnable() {
            public void run() {
                if (ItemTouchHelper.this.mRecyclerView != null && ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() && !recoverAnimation.mOverridden && recoverAnimation.mViewHolder.getAdapterPosition() != -1) {
                    ItemAnimator itemAnimator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
                    if ((itemAnimator == null || !itemAnimator.isRunning(null)) && !ItemTouchHelper.this.hasRunningRecoverAnim()) {
                        ItemTouchHelper.this.mCallback.onSwiped(recoverAnimation.mViewHolder, i);
                    } else {
                        ItemTouchHelper.this.mRecyclerView.post(this);
                    }
                }
            }
        });
    }

    void removeChildDrawingOrderCallbackIfNecessary(View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback(null);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean scrollIfNecessary() {
        /*
        r14 = this;
        r12 = -9223372036854775808;
        r0 = 0;
        r5 = 0;
        r1 = r14.mSelected;
        if (r1 != 0) goto L_0x000b;
    L_0x0008:
        r14.mDragScrollStartTimeInMs = r12;
    L_0x000a:
        return r0;
    L_0x000b:
        r10 = java.lang.System.currentTimeMillis();
        r2 = r14.mDragScrollStartTimeInMs;
        r1 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r1 != 0) goto L_0x00bb;
    L_0x0015:
        r6 = 0;
    L_0x0017:
        r1 = r14.mRecyclerView;
        r1 = r1.getLayoutManager();
        r2 = r14.mTmpRect;
        if (r2 != 0) goto L_0x0028;
    L_0x0021:
        r2 = new android.graphics.Rect;
        r2.<init>();
        r14.mTmpRect = r2;
    L_0x0028:
        r2 = r14.mSelected;
        r2 = r2.itemView;
        r3 = r14.mTmpRect;
        r1.calculateItemDecorationsForChild(r2, r3);
        r2 = r1.canScrollHorizontally();
        if (r2 == 0) goto L_0x00e6;
    L_0x0037:
        r2 = r14.mSelectedStartX;
        r3 = r14.mDx;
        r2 = r2 + r3;
        r2 = (int) r2;
        r3 = r14.mTmpRect;
        r3 = r3.left;
        r3 = r2 - r3;
        r4 = r14.mRecyclerView;
        r4 = r4.getPaddingLeft();
        r4 = r3 - r4;
        r3 = r14.mDx;
        r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r3 >= 0) goto L_0x00c1;
    L_0x0051:
        if (r4 >= 0) goto L_0x00c1;
    L_0x0053:
        r1 = r1.canScrollVertically();
        if (r1 == 0) goto L_0x010e;
    L_0x0059:
        r1 = r14.mSelectedStartY;
        r2 = r14.mDy;
        r1 = r1 + r2;
        r1 = (int) r1;
        r2 = r14.mTmpRect;
        r2 = r2.top;
        r2 = r1 - r2;
        r3 = r14.mRecyclerView;
        r3 = r3.getPaddingTop();
        r8 = r2 - r3;
        r2 = r14.mDy;
        r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1));
        if (r2 >= 0) goto L_0x00e9;
    L_0x0073:
        if (r8 >= 0) goto L_0x00e9;
    L_0x0075:
        if (r4 == 0) goto L_0x0117;
    L_0x0077:
        r1 = r14.mCallback;
        r2 = r14.mRecyclerView;
        r3 = r14.mSelected;
        r3 = r3.itemView;
        r3 = r3.getWidth();
        r5 = r14.mRecyclerView;
        r5 = r5.getWidth();
        r4 = r1.interpolateOutOfBoundsScroll(r2, r3, r4, r5, r6);
        r9 = r4;
    L_0x008e:
        if (r8 == 0) goto L_0x0115;
    L_0x0090:
        r1 = r14.mCallback;
        r2 = r14.mRecyclerView;
        r3 = r14.mSelected;
        r3 = r3.itemView;
        r3 = r3.getHeight();
        r4 = r14.mRecyclerView;
        r5 = r4.getHeight();
        r4 = r8;
        r1 = r1.interpolateOutOfBoundsScroll(r2, r3, r4, r5, r6);
    L_0x00a7:
        if (r9 != 0) goto L_0x00ab;
    L_0x00a9:
        if (r1 == 0) goto L_0x0111;
    L_0x00ab:
        r2 = r14.mDragScrollStartTimeInMs;
        r0 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r0 != 0) goto L_0x00b3;
    L_0x00b1:
        r14.mDragScrollStartTimeInMs = r10;
    L_0x00b3:
        r0 = r14.mRecyclerView;
        r0.scrollBy(r9, r1);
        r0 = 1;
        goto L_0x000a;
    L_0x00bb:
        r2 = r14.mDragScrollStartTimeInMs;
        r6 = r10 - r2;
        goto L_0x0017;
    L_0x00c1:
        r3 = r14.mDx;
        r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r3 <= 0) goto L_0x00e6;
    L_0x00c7:
        r3 = r14.mSelected;
        r3 = r3.itemView;
        r3 = r3.getWidth();
        r2 = r2 + r3;
        r3 = r14.mTmpRect;
        r3 = r3.right;
        r2 = r2 + r3;
        r3 = r14.mRecyclerView;
        r3 = r3.getWidth();
        r4 = r14.mRecyclerView;
        r4 = r4.getPaddingRight();
        r3 = r3 - r4;
        r4 = r2 - r3;
        if (r4 > 0) goto L_0x0053;
    L_0x00e6:
        r4 = r0;
        goto L_0x0053;
    L_0x00e9:
        r2 = r14.mDy;
        r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1));
        if (r2 <= 0) goto L_0x010e;
    L_0x00ef:
        r2 = r14.mSelected;
        r2 = r2.itemView;
        r2 = r2.getHeight();
        r1 = r1 + r2;
        r2 = r14.mTmpRect;
        r2 = r2.bottom;
        r1 = r1 + r2;
        r2 = r14.mRecyclerView;
        r2 = r2.getHeight();
        r3 = r14.mRecyclerView;
        r3 = r3.getPaddingBottom();
        r2 = r2 - r3;
        r8 = r1 - r2;
        if (r8 > 0) goto L_0x0075;
    L_0x010e:
        r8 = r0;
        goto L_0x0075;
    L_0x0111:
        r14.mDragScrollStartTimeInMs = r12;
        goto L_0x000a;
    L_0x0115:
        r1 = r8;
        goto L_0x00a7;
    L_0x0117:
        r9 = r4;
        goto L_0x008e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.support.widget.helper.ItemTouchHelper.scrollIfNecessary():boolean");
    }

    void select(ViewHolder viewHolder, int i) {
        if (viewHolder != this.mSelected || i != this.mActionState) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            int i2 = this.mActionState;
            endRecoverAnimation(viewHolder, true);
            this.mActionState = i;
            if (i == 2) {
                this.mOverdrawChild = viewHolder.itemView;
                addChildDrawingOrderCallback();
            }
            int i3 = (1 << ((i * 8) + 8)) - 1;
            Object obj = null;
            if (this.mSelected != null) {
                ViewHolder viewHolder2 = this.mSelected;
                if (viewHolder2.itemView.getParent() != null) {
                    float f;
                    float signum;
                    final int swipeIfNecessary = i2 == 2 ? 0 : swipeIfNecessary(viewHolder2);
                    releaseVelocityTracker();
                    switch (swipeIfNecessary) {
                        case 1:
                        case 2:
                            f = BitmapDescriptorFactory.HUE_RED;
                            signum = Math.signum(this.mDy) * ((float) this.mRecyclerView.getHeight());
                            break;
                        case 4:
                        case 8:
                        case 16:
                        case 32:
                            signum = BitmapDescriptorFactory.HUE_RED;
                            f = Math.signum(this.mDx) * ((float) this.mRecyclerView.getWidth());
                            break;
                        default:
                            f = BitmapDescriptorFactory.HUE_RED;
                            signum = BitmapDescriptorFactory.HUE_RED;
                            break;
                    }
                    int i4 = i2 == 2 ? 8 : swipeIfNecessary > 0 ? 2 : 4;
                    getSelectedDxDy(this.mTmpPosition);
                    float f2 = this.mTmpPosition[0];
                    float f3 = this.mTmpPosition[1];
                    final ViewHolder viewHolder3 = viewHolder2;
                    RecoverAnimation c36893 = new RecoverAnimation(viewHolder2, i4, i2, f2, f3, f, signum) {
                        public void onAnimationEnd(C0216g c0216g) {
                            super.onAnimationEnd(c0216g);
                            if (!this.mOverridden) {
                                if (swipeIfNecessary <= 0) {
                                    ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, viewHolder3);
                                } else {
                                    ItemTouchHelper.this.mPendingCleanup.add(viewHolder3.itemView);
                                    this.mIsPendingCleanup = true;
                                    if (swipeIfNecessary > 0) {
                                        ItemTouchHelper.this.postDispatchSwipe(this, swipeIfNecessary);
                                    }
                                }
                                if (ItemTouchHelper.this.mOverdrawChild == viewHolder3.itemView) {
                                    ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(viewHolder3.itemView);
                                }
                            }
                        }
                    };
                    c36893.setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, i4, f - f2, signum - f3));
                    this.mRecoverAnimations.add(c36893);
                    c36893.start();
                    obj = 1;
                } else {
                    removeChildDrawingOrderCallbackIfNecessary(viewHolder2.itemView);
                    this.mCallback.clearView(this.mRecyclerView, viewHolder2);
                }
                this.mSelected = null;
            }
            Object obj2 = obj;
            if (viewHolder != null) {
                this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder) & i3) >> (this.mActionState * 8);
                this.mSelectedStartX = (float) viewHolder.itemView.getLeft();
                this.mSelectedStartY = (float) viewHolder.itemView.getTop();
                this.mSelected = viewHolder;
                if (i == 2) {
                    this.mSelected.itemView.performHapticFeedback(0);
                }
            }
            ViewParent parent = this.mRecyclerView.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(this.mSelected != null);
            }
            if (obj2 == null) {
                this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
            }
            this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
            this.mRecyclerView.invalidate();
        }
    }

    public void startDrag(ViewHolder viewHolder) {
        if (!this.mCallback.hasDragFlag(this.mRecyclerView, viewHolder)) {
            Log.e(TAG, "Start drag has been called but swiping is not enabled");
        } else if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = BitmapDescriptorFactory.HUE_RED;
            this.mDx = BitmapDescriptorFactory.HUE_RED;
            select(viewHolder, 2);
        }
    }

    public void startSwipe(ViewHolder viewHolder) {
        if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, viewHolder)) {
            Log.e(TAG, "Start swipe has been called but dragging is not enabled");
        } else if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = BitmapDescriptorFactory.HUE_RED;
            this.mDx = BitmapDescriptorFactory.HUE_RED;
            select(viewHolder, 1);
        }
    }

    void updateDxDy(MotionEvent motionEvent, int i, int i2) {
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        this.mDx = x - this.mInitialTouchX;
        this.mDy = y - this.mInitialTouchY;
        if ((i & 4) == 0) {
            this.mDx = Math.max(BitmapDescriptorFactory.HUE_RED, this.mDx);
        }
        if ((i & 8) == 0) {
            this.mDx = Math.min(BitmapDescriptorFactory.HUE_RED, this.mDx);
        }
        if ((i & 1) == 0) {
            this.mDy = Math.max(BitmapDescriptorFactory.HUE_RED, this.mDy);
        }
        if ((i & 2) == 0) {
            this.mDy = Math.min(BitmapDescriptorFactory.HUE_RED, this.mDy);
        }
    }
}
