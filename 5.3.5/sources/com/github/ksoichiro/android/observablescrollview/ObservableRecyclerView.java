package com.github.ksoichiro.android.observablescrollview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class ObservableRecyclerView extends RecyclerView implements Scrollable {
    private ObservableScrollViewCallbacks mCallbacks;
    private SparseIntArray mChildrenHeights;
    private boolean mDragging;
    private boolean mFirstScroll;
    private boolean mIntercepted;
    private int mPrevFirstVisibleChildHeight = -1;
    private int mPrevFirstVisiblePosition;
    private MotionEvent mPrevMoveEvent;
    private int mPrevScrollY;
    private int mPrevScrolledChildrenHeight;
    private ScrollState mScrollState;
    private int mScrollY;
    private ViewGroup mTouchInterceptionViewGroup;

    static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C04802();
        public static final SavedState EMPTY_STATE = new C04791();
        SparseIntArray childrenHeights;
        int prevFirstVisibleChildHeight;
        int prevFirstVisiblePosition;
        int prevScrollY;
        int prevScrolledChildrenHeight;
        int scrollY;
        Parcelable superState;

        /* renamed from: com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView$SavedState$1 */
        static class C04791 extends SavedState {
            C04791() {
                super();
            }
        }

        /* renamed from: com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView$SavedState$2 */
        static class C04802 implements Creator<SavedState> {
            C04802() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        private SavedState() {
            this.prevFirstVisibleChildHeight = -1;
            this.superState = null;
        }

        SavedState(Parcelable superState) {
            this.prevFirstVisibleChildHeight = -1;
            if (superState == EMPTY_STATE) {
                superState = null;
            }
            this.superState = superState;
        }

        private SavedState(Parcel in) {
            this.prevFirstVisibleChildHeight = -1;
            Parcelable superState = in.readParcelable(RecyclerView.class.getClassLoader());
            if (superState == null) {
                superState = EMPTY_STATE;
            }
            this.superState = superState;
            this.prevFirstVisiblePosition = in.readInt();
            this.prevFirstVisibleChildHeight = in.readInt();
            this.prevScrolledChildrenHeight = in.readInt();
            this.prevScrollY = in.readInt();
            this.scrollY = in.readInt();
            this.childrenHeights = new SparseIntArray();
            int numOfChildren = in.readInt();
            if (numOfChildren > 0) {
                for (int i = 0; i < numOfChildren; i++) {
                    this.childrenHeights.put(in.readInt(), in.readInt());
                }
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeParcelable(this.superState, flags);
            out.writeInt(this.prevFirstVisiblePosition);
            out.writeInt(this.prevFirstVisibleChildHeight);
            out.writeInt(this.prevScrolledChildrenHeight);
            out.writeInt(this.prevScrollY);
            out.writeInt(this.scrollY);
            int numOfChildren = this.childrenHeights == null ? 0 : this.childrenHeights.size();
            out.writeInt(numOfChildren);
            if (numOfChildren > 0) {
                for (int i = 0; i < numOfChildren; i++) {
                    out.writeInt(this.childrenHeights.keyAt(i));
                    out.writeInt(this.childrenHeights.valueAt(i));
                }
            }
        }

        public Parcelable getSuperState() {
            return this.superState;
        }
    }

    public ObservableRecyclerView(Context context) {
        super(context);
        init();
    }

    public ObservableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ObservableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        this.mPrevFirstVisiblePosition = ss.prevFirstVisiblePosition;
        this.mPrevFirstVisibleChildHeight = ss.prevFirstVisibleChildHeight;
        this.mPrevScrolledChildrenHeight = ss.prevScrolledChildrenHeight;
        this.mPrevScrollY = ss.prevScrollY;
        this.mScrollY = ss.scrollY;
        this.mChildrenHeights = ss.childrenHeights;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.prevFirstVisiblePosition = this.mPrevFirstVisiblePosition;
        ss.prevFirstVisibleChildHeight = this.mPrevFirstVisibleChildHeight;
        ss.prevScrolledChildrenHeight = this.mPrevScrolledChildrenHeight;
        ss.prevScrollY = this.mPrevScrollY;
        ss.scrollY = this.mScrollY;
        ss.childrenHeights = this.mChildrenHeights;
        return ss;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mCallbacks != null && getChildCount() > 0) {
            int firstVisiblePosition = getChildPosition(getChildAt(0));
            int lastVisiblePosition = getChildPosition(getChildAt(getChildCount() - 1));
            int i = firstVisiblePosition;
            int j = 0;
            while (i <= lastVisiblePosition) {
                if (this.mChildrenHeights.indexOfKey(i) < 0 || getChildAt(j).getHeight() != this.mChildrenHeights.get(i)) {
                    this.mChildrenHeights.put(i, getChildAt(j).getHeight());
                }
                i++;
                j++;
            }
            View firstVisibleChild = getChildAt(0);
            if (firstVisibleChild != null) {
                int skippedChildrenHeight;
                if (this.mPrevFirstVisiblePosition < firstVisiblePosition) {
                    skippedChildrenHeight = 0;
                    if (firstVisiblePosition - this.mPrevFirstVisiblePosition != 1) {
                        for (i = firstVisiblePosition - 1; i > this.mPrevFirstVisiblePosition; i--) {
                            if (this.mChildrenHeights.indexOfKey(i) > 0) {
                                skippedChildrenHeight += this.mChildrenHeights.get(i);
                            } else {
                                skippedChildrenHeight += firstVisibleChild.getHeight();
                            }
                        }
                    }
                    this.mPrevScrolledChildrenHeight += this.mPrevFirstVisibleChildHeight + skippedChildrenHeight;
                    this.mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                } else if (firstVisiblePosition < this.mPrevFirstVisiblePosition) {
                    skippedChildrenHeight = 0;
                    if (this.mPrevFirstVisiblePosition - firstVisiblePosition != 1) {
                        for (i = this.mPrevFirstVisiblePosition - 1; i > firstVisiblePosition; i--) {
                            if (this.mChildrenHeights.indexOfKey(i) > 0) {
                                skippedChildrenHeight += this.mChildrenHeights.get(i);
                            } else {
                                skippedChildrenHeight += firstVisibleChild.getHeight();
                            }
                        }
                    }
                    this.mPrevScrolledChildrenHeight -= firstVisibleChild.getHeight() + skippedChildrenHeight;
                    this.mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                } else if (firstVisiblePosition == 0) {
                    this.mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                }
                if (this.mPrevFirstVisibleChildHeight < 0) {
                    this.mPrevFirstVisibleChildHeight = 0;
                }
                this.mScrollY = this.mPrevScrolledChildrenHeight - firstVisibleChild.getTop();
                this.mPrevFirstVisiblePosition = firstVisiblePosition;
                this.mCallbacks.onScrollChanged(this.mScrollY, this.mFirstScroll, this.mDragging);
                if (this.mFirstScroll) {
                    this.mFirstScroll = false;
                }
                if (this.mPrevScrollY < this.mScrollY) {
                    this.mScrollState = ScrollState.UP;
                } else if (this.mScrollY < this.mPrevScrollY) {
                    this.mScrollState = ScrollState.DOWN;
                } else {
                    this.mScrollState = ScrollState.STOP;
                }
                this.mPrevScrollY = this.mScrollY;
            }
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
        View firstVisibleChild = getChildAt(0);
        if (firstVisibleChild != null) {
            scrollVerticallyToPosition(y / firstVisibleChild.getHeight());
        }
    }

    public void scrollVerticallyToPosition(int position) {
        LayoutManager lm = getLayoutManager();
        if (lm == null || !(lm instanceof LinearLayoutManager)) {
            scrollToPosition(position);
        } else {
            ((LinearLayoutManager) lm).scrollToPositionWithOffset(position, 0);
        }
    }

    public int getCurrentScrollY() {
        return this.mScrollY;
    }

    private void init() {
        this.mChildrenHeights = new SparseIntArray();
    }
}
