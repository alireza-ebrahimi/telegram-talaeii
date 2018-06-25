package com.h6ah4i.android.widget.advrecyclerview.touchguard;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class RecyclerViewTouchActionGuardManager {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "ARVTouchActionGuardMgr";
    private boolean mEnabled;
    private boolean mGuarding;
    private int mInitialTouchY;
    private boolean mInterceptScrollingWhileAnimationRunning;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C06071();
    private int mLastTouchY;
    private RecyclerView mRecyclerView;
    private int mTouchSlop;

    /* renamed from: com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager$1 */
    class C06071 implements OnItemTouchListener {
        C06071() {
        }

        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return RecyclerViewTouchActionGuardManager.this.onInterceptTouchEvent(rv, e);
        }

        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            RecyclerViewTouchActionGuardManager.this.onTouchEvent(rv, e);
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
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
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            this.mTouchSlop = ViewConfiguration.get(rv.getContext()).getScaledTouchSlop();
        }
    }

    public void release() {
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        this.mRecyclerView = null;
    }

    boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (!this.mEnabled) {
            return false;
        }
        switch (MotionEventCompat.getActionMasked(e)) {
            case 0:
                handleActionDown(e);
                return false;
            case 1:
            case 3:
                handleActionUpOrCancel();
                return false;
            case 2:
                if (handleActionMove(rv, e)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.mEnabled) {
            switch (MotionEventCompat.getActionMasked(e)) {
                case 1:
                case 3:
                    handleActionUpOrCancel();
                    return;
                default:
                    return;
            }
        }
    }

    private boolean handleActionMove(RecyclerView rv, MotionEvent e) {
        if (!this.mGuarding) {
            this.mLastTouchY = (int) (e.getY() + 0.5f);
            int distance = this.mLastTouchY - this.mInitialTouchY;
            if (this.mInterceptScrollingWhileAnimationRunning && Math.abs(distance) > this.mTouchSlop && rv.isAnimating()) {
                this.mGuarding = true;
            }
        }
        return this.mGuarding;
    }

    private void handleActionUpOrCancel() {
        this.mGuarding = false;
        this.mInitialTouchY = 0;
        this.mLastTouchY = 0;
    }

    private void handleActionDown(MotionEvent e) {
        int y = (int) (e.getY() + 0.5f);
        this.mLastTouchY = y;
        this.mInitialTouchY = y;
        this.mGuarding = false;
    }

    public void setEnabled(boolean enabled) {
        if (this.mEnabled != enabled) {
            this.mEnabled = enabled;
            if (!this.mEnabled) {
                handleActionUpOrCancel();
            }
        }
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setInterceptVerticalScrollingWhileAnimationRunning(boolean enabled) {
        this.mInterceptScrollingWhileAnimationRunning = enabled;
    }

    public boolean isInterceptScrollingWhileAnimationRunning() {
        return this.mInterceptScrollingWhileAnimationRunning;
    }
}
