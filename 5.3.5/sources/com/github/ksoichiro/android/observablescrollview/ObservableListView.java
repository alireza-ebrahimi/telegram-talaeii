package com.github.ksoichiro.android.observablescrollview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ObservableListView extends ListView implements Scrollable {
    private ObservableScrollViewCallbacks mCallbacks;
    private SparseIntArray mChildrenHeights;
    private boolean mDragging;
    private boolean mFirstScroll;
    private boolean mIntercepted;
    private OnScrollListener mOriginalScrollListener;
    private int mPrevFirstVisibleChildHeight = -1;
    private int mPrevFirstVisiblePosition;
    private MotionEvent mPrevMoveEvent;
    private int mPrevScrollY;
    private int mPrevScrolledChildrenHeight;
    private OnScrollListener mScrollListener = new C04751();
    private ScrollState mScrollState;
    private int mScrollY;
    private ViewGroup mTouchInterceptionViewGroup;

    /* renamed from: com.github.ksoichiro.android.observablescrollview.ObservableListView$1 */
    class C04751 implements OnScrollListener {
        C04751() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (ObservableListView.this.mOriginalScrollListener != null) {
                ObservableListView.this.mOriginalScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (ObservableListView.this.mOriginalScrollListener != null) {
                ObservableListView.this.mOriginalScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            ObservableListView.this.onScrollChanged();
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C04771();
        SparseIntArray childrenHeights;
        int prevFirstVisibleChildHeight;
        int prevFirstVisiblePosition;
        int prevScrollY;
        int prevScrolledChildrenHeight;
        int scrollY;

        /* renamed from: com.github.ksoichiro.android.observablescrollview.ObservableListView$SavedState$1 */
        static class C04771 implements Creator<SavedState> {
            C04771() {
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
            this.prevFirstVisibleChildHeight = -1;
        }

        private SavedState(Parcel in) {
            super(in);
            this.prevFirstVisibleChildHeight = -1;
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

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
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
    }

    public ObservableListView(Context context) {
        super(context);
        init();
    }

    public ObservableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ObservableListView(Context context, AttributeSet attrs, int defStyle) {
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

    public void setOnScrollListener(OnScrollListener l) {
        this.mOriginalScrollListener = l;
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
            setSelection(y / firstVisibleChild.getHeight());
        }
    }

    public int getCurrentScrollY() {
        return this.mScrollY;
    }

    private void init() {
        this.mChildrenHeights = new SparseIntArray();
        super.setOnScrollListener(this.mScrollListener);
    }

    private void onScrollChanged() {
        if (this.mCallbacks != null && getChildCount() > 0) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int i = getFirstVisiblePosition();
            int j = 0;
            while (i <= getLastVisiblePosition()) {
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
}
