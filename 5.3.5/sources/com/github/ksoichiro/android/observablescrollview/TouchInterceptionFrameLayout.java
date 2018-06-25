package com.github.ksoichiro.android.observablescrollview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class TouchInterceptionFrameLayout extends FrameLayout {
    private boolean mBeganFromDownMotionEvent;
    private boolean mChildrenEventsCanceled;
    private boolean mDownMotionEventPended;
    private PointF mInitialPoint;
    private boolean mIntercepting;
    private MotionEvent mPendingDownMotionEvent;
    private TouchInterceptionListener mTouchInterceptionListener;

    public interface TouchInterceptionListener {
        void onDownMotionEvent(MotionEvent motionEvent);

        void onMoveMotionEvent(MotionEvent motionEvent, float f, float f2);

        void onUpOrCancelMotionEvent(MotionEvent motionEvent);

        boolean shouldInterceptTouchEvent(MotionEvent motionEvent, boolean z, float f, float f2);
    }

    public TouchInterceptionFrameLayout(Context context) {
        super(context);
    }

    public TouchInterceptionFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchInterceptionFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public TouchInterceptionFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollInterceptionListener(TouchInterceptionListener listener) {
        this.mTouchInterceptionListener = listener;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mTouchInterceptionListener == null) {
            return false;
        }
        switch (ev.getActionMasked()) {
            case 0:
                this.mInitialPoint = new PointF(ev.getX(), ev.getY());
                this.mPendingDownMotionEvent = MotionEvent.obtainNoHistory(ev);
                this.mDownMotionEventPended = true;
                this.mIntercepting = this.mTouchInterceptionListener.shouldInterceptTouchEvent(ev, false, 0.0f, 0.0f);
                this.mBeganFromDownMotionEvent = this.mIntercepting;
                this.mChildrenEventsCanceled = false;
                return this.mIntercepting;
            case 2:
                if (this.mInitialPoint == null) {
                    this.mInitialPoint = new PointF(ev.getX(), ev.getY());
                }
                this.mIntercepting = this.mTouchInterceptionListener.shouldInterceptTouchEvent(ev, true, ev.getX() - this.mInitialPoint.x, ev.getY() - this.mInitialPoint.y);
                return this.mIntercepting;
            default:
                return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mTouchInterceptionListener != null) {
            MotionEvent event;
            switch (ev.getActionMasked()) {
                case 0:
                    if (this.mIntercepting) {
                        this.mTouchInterceptionListener.onDownMotionEvent(ev);
                        duplicateTouchEventForChildren(ev, new MotionEvent[0]);
                        return true;
                    }
                    break;
                case 1:
                case 3:
                    this.mBeganFromDownMotionEvent = false;
                    if (this.mIntercepting) {
                        this.mTouchInterceptionListener.onUpOrCancelMotionEvent(ev);
                    }
                    if (this.mChildrenEventsCanceled) {
                        return true;
                    }
                    this.mChildrenEventsCanceled = true;
                    if (this.mDownMotionEventPended) {
                        this.mDownMotionEventPended = false;
                        MotionEvent.obtainNoHistory(this.mPendingDownMotionEvent).setLocation(ev.getX(), ev.getY());
                        duplicateTouchEventForChildren(ev, event);
                        return true;
                    }
                    duplicateTouchEventForChildren(ev, new MotionEvent[0]);
                    return true;
                case 2:
                    if (this.mInitialPoint == null) {
                        this.mInitialPoint = new PointF(ev.getX(), ev.getY());
                    }
                    float diffX = ev.getX() - this.mInitialPoint.x;
                    float diffY = ev.getY() - this.mInitialPoint.y;
                    this.mIntercepting = this.mTouchInterceptionListener.shouldInterceptTouchEvent(ev, true, diffX, diffY);
                    if (!this.mIntercepting) {
                        if (this.mDownMotionEventPended) {
                            this.mDownMotionEventPended = false;
                            MotionEvent.obtainNoHistory(this.mPendingDownMotionEvent).setLocation(ev.getX(), ev.getY());
                            duplicateTouchEventForChildren(ev, event);
                        } else {
                            duplicateTouchEventForChildren(ev, new MotionEvent[0]);
                        }
                        this.mBeganFromDownMotionEvent = false;
                        this.mChildrenEventsCanceled = false;
                        break;
                    }
                    if (!this.mBeganFromDownMotionEvent) {
                        this.mBeganFromDownMotionEvent = true;
                        event = MotionEvent.obtainNoHistory(this.mPendingDownMotionEvent);
                        event.setLocation(ev.getX(), ev.getY());
                        this.mTouchInterceptionListener.onDownMotionEvent(event);
                        this.mInitialPoint = new PointF(ev.getX(), ev.getY());
                        diffY = 0.0f;
                        diffX = 0.0f;
                    }
                    if (!this.mChildrenEventsCanceled) {
                        this.mChildrenEventsCanceled = true;
                        duplicateTouchEventForChildren(obtainMotionEvent(ev, 3), new MotionEvent[0]);
                    }
                    this.mTouchInterceptionListener.onMoveMotionEvent(ev, diffX, diffY);
                    this.mDownMotionEventPended = true;
                    return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private MotionEvent obtainMotionEvent(MotionEvent base, int action) {
        MotionEvent ev = MotionEvent.obtainNoHistory(base);
        ev.setAction(action);
        return ev;
    }

    private void duplicateTouchEventForChildren(MotionEvent ev, MotionEvent... pendingEvents) {
        if (ev != null) {
            for (int i = getChildCount() - 1; i >= 0; i--) {
                View childView = getChildAt(i);
                if (childView != null) {
                    Rect childRect = new Rect();
                    childView.getHitRect(childRect);
                    MotionEvent event = MotionEvent.obtainNoHistory(ev);
                    if (childRect.contains((int) event.getX(), (int) event.getY())) {
                        float offsetX = (float) (-childView.getLeft());
                        float offsetY = (float) (-childView.getTop());
                        boolean consumed = false;
                        if (pendingEvents != null) {
                            for (MotionEvent pe : pendingEvents) {
                                if (pe != null) {
                                    MotionEvent peAdjusted = MotionEvent.obtainNoHistory(pe);
                                    peAdjusted.offsetLocation(offsetX, offsetY);
                                    consumed |= childView.dispatchTouchEvent(peAdjusted);
                                }
                            }
                        }
                        event.offsetLocation(offsetX, offsetY);
                        if (consumed | childView.dispatchTouchEvent(event)) {
                            return;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }
}
