package com.github.ksoichiro.android.observablescrollview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView implements Scrollable {
    private ObservableScrollViewCallbacks mCallbacks;
    private boolean mDragging;
    private boolean mFirstScroll;
    private boolean mIntercepted;
    private MotionEvent mPrevMoveEvent;
    private int mPrevScrollY;
    private ScrollState mScrollState;
    private int mScrollY;
    private ViewGroup mTouchInterceptionViewGroup;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C04821();
        int prevScrollY;
        int scrollY;

        /* renamed from: com.github.ksoichiro.android.observablescrollview.ObservableScrollView$SavedState$1 */
        static class C04821 implements Creator<SavedState> {
            C04821() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.prevScrollY = in.readInt();
            this.scrollY = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.prevScrollY);
            out.writeInt(this.scrollY);
        }
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        this.mPrevScrollY = ss.prevScrollY;
        this.mScrollY = ss.scrollY;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.prevScrollY = this.mPrevScrollY;
        ss.scrollY = this.mScrollY;
        return ss;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mCallbacks != null) {
            this.mScrollY = t;
            this.mCallbacks.onScrollChanged(t, this.mFirstScroll, this.mDragging);
            if (this.mFirstScroll) {
                this.mFirstScroll = false;
            }
            if (this.mPrevScrollY < t) {
                this.mScrollState = ScrollState.UP;
            } else if (t < this.mPrevScrollY) {
                this.mScrollState = ScrollState.DOWN;
            }
            this.mPrevScrollY = t;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.mCallbacks != null) {
            switch (ev.getActionMasked()) {
                case 0:
                    this.mDragging = true;
                    this.mFirstScroll = true;
                    this.mCallbacks.onDownMotionEvent();
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mCallbacks != null) {
            switch (ev.getActionMasked()) {
                case 1:
                case 3:
                    this.mIntercepted = false;
                    this.mDragging = false;
                    this.mCallbacks.onUpOrCancelMotionEvent(this.mScrollState);
                    break;
                case 2:
                    if (this.mPrevMoveEvent == null) {
                        this.mPrevMoveEvent = ev;
                    }
                    float diffY = ev.getY() - this.mPrevMoveEvent.getY();
                    this.mPrevMoveEvent = MotionEvent.obtainNoHistory(ev);
                    if (((float) getCurrentScrollY()) - diffY <= 0.0f) {
                        if (this.mIntercepted) {
                            return false;
                        }
                        View parent;
                        if (this.mTouchInterceptionViewGroup == null) {
                            parent = (ViewGroup) getParent();
                        } else {
                            parent = this.mTouchInterceptionViewGroup;
                        }
                        float offsetX = 0.0f;
                        float offsetY = 0.0f;
                        View v = this;
                        while (v != null && v != parent) {
                            offsetX += (float) (v.getLeft() - v.getScrollX());
                            offsetY += (float) (v.getTop() - v.getScrollY());
                            v = (View) v.getParent();
                        }
                        final MotionEvent event = MotionEvent.obtainNoHistory(ev);
                        event.offsetLocation(offsetX, offsetY);
                        if (!parent.onInterceptTouchEvent(event)) {
                            return super.onTouchEvent(ev);
                        }
                        this.mIntercepted = true;
                        event.setAction(0);
                        post(new Runnable() {
                            public void run() {
                                parent.dispatchTouchEvent(event);
                            }
                        });
                        return false;
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        this.mCallbacks = listener;
    }

    public void setTouchInterceptionViewGroup(ViewGroup viewGroup) {
        this.mTouchInterceptionViewGroup = viewGroup;
    }

    public void scrollVerticallyTo(int y) {
        scrollTo(0, y);
    }

    public int getCurrentScrollY() {
        return this.mScrollY;
    }
}
