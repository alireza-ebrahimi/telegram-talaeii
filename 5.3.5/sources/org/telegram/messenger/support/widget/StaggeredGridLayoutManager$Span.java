package org.telegram.messenger.support.widget;

import android.view.View;
import java.util.ArrayList;

class StaggeredGridLayoutManager$Span {
    static final int INVALID_LINE = Integer.MIN_VALUE;
    int mCachedEnd = Integer.MIN_VALUE;
    int mCachedStart = Integer.MIN_VALUE;
    int mDeletedSize = 0;
    final int mIndex;
    ArrayList<View> mViews = new ArrayList();
    final /* synthetic */ StaggeredGridLayoutManager this$0;

    StaggeredGridLayoutManager$Span(StaggeredGridLayoutManager this$0, int index) {
        this.this$0 = this$0;
        this.mIndex = index;
    }

    int getStartLine(int def) {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            return this.mCachedStart;
        }
        if (this.mViews.size() == 0) {
            return def;
        }
        calculateCachedStart();
        return this.mCachedStart;
    }

    void calculateCachedStart() {
        View startView = (View) this.mViews.get(0);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(startView);
        this.mCachedStart = this.this$0.mPrimaryOrientation.getDecoratedStart(startView);
        if (lp.mFullSpan) {
            FullSpanItem fsi = this.this$0.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
            if (fsi != null && fsi.mGapDir == -1) {
                this.mCachedStart -= fsi.getGapForSpan(this.mIndex);
            }
        }
    }

    int getStartLine() {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            return this.mCachedStart;
        }
        calculateCachedStart();
        return this.mCachedStart;
    }

    int getEndLine(int def) {
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            return this.mCachedEnd;
        }
        if (this.mViews.size() == 0) {
            return def;
        }
        calculateCachedEnd();
        return this.mCachedEnd;
    }

    void calculateCachedEnd() {
        View endView = (View) this.mViews.get(this.mViews.size() - 1);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(endView);
        this.mCachedEnd = this.this$0.mPrimaryOrientation.getDecoratedEnd(endView);
        if (lp.mFullSpan) {
            FullSpanItem fsi = this.this$0.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
            if (fsi != null && fsi.mGapDir == 1) {
                this.mCachedEnd += fsi.getGapForSpan(this.mIndex);
            }
        }
    }

    int getEndLine() {
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            return this.mCachedEnd;
        }
        calculateCachedEnd();
        return this.mCachedEnd;
    }

    void prependToSpan(View view) {
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(view);
        lp.mSpan = this;
        this.mViews.add(0, view);
        this.mCachedStart = Integer.MIN_VALUE;
        if (this.mViews.size() == 1) {
            this.mCachedEnd = Integer.MIN_VALUE;
        }
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize += this.this$0.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
    }

    void appendToSpan(View view) {
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(view);
        lp.mSpan = this;
        this.mViews.add(view);
        this.mCachedEnd = Integer.MIN_VALUE;
        if (this.mViews.size() == 1) {
            this.mCachedStart = Integer.MIN_VALUE;
        }
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize += this.this$0.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
    }

    void cacheReferenceLineAndClear(boolean reverseLayout, int offset) {
        int reference;
        if (reverseLayout) {
            reference = getEndLine(Integer.MIN_VALUE);
        } else {
            reference = getStartLine(Integer.MIN_VALUE);
        }
        clear();
        if (reference != Integer.MIN_VALUE) {
            if (reverseLayout && reference < this.this$0.mPrimaryOrientation.getEndAfterPadding()) {
                return;
            }
            if (reverseLayout || reference <= this.this$0.mPrimaryOrientation.getStartAfterPadding()) {
                if (offset != Integer.MIN_VALUE) {
                    reference += offset;
                }
                this.mCachedEnd = reference;
                this.mCachedStart = reference;
            }
        }
    }

    void clear() {
        this.mViews.clear();
        invalidateCache();
        this.mDeletedSize = 0;
    }

    void invalidateCache() {
        this.mCachedStart = Integer.MIN_VALUE;
        this.mCachedEnd = Integer.MIN_VALUE;
    }

    void setLine(int line) {
        this.mCachedStart = line;
        this.mCachedEnd = line;
    }

    void popEnd() {
        int size = this.mViews.size();
        View end = (View) this.mViews.remove(size - 1);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(end);
        lp.mSpan = null;
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize -= this.this$0.mPrimaryOrientation.getDecoratedMeasurement(end);
        }
        if (size == 1) {
            this.mCachedStart = Integer.MIN_VALUE;
        }
        this.mCachedEnd = Integer.MIN_VALUE;
    }

    void popStart() {
        View start = (View) this.mViews.remove(0);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(start);
        lp.mSpan = null;
        if (this.mViews.size() == 0) {
            this.mCachedEnd = Integer.MIN_VALUE;
        }
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize -= this.this$0.mPrimaryOrientation.getDecoratedMeasurement(start);
        }
        this.mCachedStart = Integer.MIN_VALUE;
    }

    public int getDeletedSize() {
        return this.mDeletedSize;
    }

    StaggeredGridLayoutManager$LayoutParams getLayoutParams(View view) {
        return (StaggeredGridLayoutManager$LayoutParams) view.getLayoutParams();
    }

    void onOffset(int dt) {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            this.mCachedStart += dt;
        }
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            this.mCachedEnd += dt;
        }
    }

    public int findFirstVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(this.mViews.size() - 1, -1, false);
        }
        return findOneVisibleChild(0, this.mViews.size(), false);
    }

    public int findFirstPartiallyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
        }
        return findOnePartiallyVisibleChild(0, this.mViews.size(), true);
    }

    public int findFirstCompletelyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(this.mViews.size() - 1, -1, true);
        }
        return findOneVisibleChild(0, this.mViews.size(), true);
    }

    public int findLastVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(0, this.mViews.size(), false);
        }
        return findOneVisibleChild(this.mViews.size() - 1, -1, false);
    }

    public int findLastPartiallyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOnePartiallyVisibleChild(0, this.mViews.size(), true);
        }
        return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
    }

    public int findLastCompletelyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(0, this.mViews.size(), true);
        }
        return findOneVisibleChild(this.mViews.size() - 1, -1, true);
    }

    int findOnePartiallyOrCompletelyVisibleChild(int fromIndex, int toIndex, boolean completelyVisible, boolean acceptCompletelyVisible, boolean acceptEndPointInclusion) {
        int start = this.this$0.mPrimaryOrientation.getStartAfterPadding();
        int end = this.this$0.mPrimaryOrientation.getEndAfterPadding();
        int next = toIndex > fromIndex ? 1 : -1;
        for (int i = fromIndex; i != toIndex; i += next) {
            View child = (View) this.mViews.get(i);
            int childStart = this.this$0.mPrimaryOrientation.getDecoratedStart(child);
            int childEnd = this.this$0.mPrimaryOrientation.getDecoratedEnd(child);
            boolean childStartInclusion = acceptEndPointInclusion ? childStart <= end : childStart < end;
            boolean childEndInclusion = acceptEndPointInclusion ? childEnd >= start : childEnd > start;
            if (childStartInclusion && childEndInclusion) {
                if (completelyVisible && acceptCompletelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return this.this$0.getPosition(child);
                    }
                } else if (acceptCompletelyVisible) {
                    return this.this$0.getPosition(child);
                } else {
                    if (childStart < start || childEnd > end) {
                        return this.this$0.getPosition(child);
                    }
                }
            }
        }
        return -1;
    }

    int findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible) {
        return findOnePartiallyOrCompletelyVisibleChild(fromIndex, toIndex, completelyVisible, true, false);
    }

    int findOnePartiallyVisibleChild(int fromIndex, int toIndex, boolean acceptEndPointInclusion) {
        return findOnePartiallyOrCompletelyVisibleChild(fromIndex, toIndex, false, false, acceptEndPointInclusion);
    }

    public View getFocusableViewAfter(int referenceChildPosition, int layoutDir) {
        View candidate = null;
        int i;
        View view;
        if (layoutDir != -1) {
            for (i = this.mViews.size() - 1; i >= 0; i--) {
                view = (View) this.mViews.get(i);
                if ((this.this$0.mReverseLayout && this.this$0.getPosition(view) >= referenceChildPosition) || ((!this.this$0.mReverseLayout && this.this$0.getPosition(view) <= referenceChildPosition) || !view.hasFocusable())) {
                    break;
                }
                candidate = view;
            }
        } else {
            int limit = this.mViews.size();
            for (i = 0; i < limit; i++) {
                view = (View) this.mViews.get(i);
                if ((this.this$0.mReverseLayout && this.this$0.getPosition(view) <= referenceChildPosition) || ((!this.this$0.mReverseLayout && this.this$0.getPosition(view) >= referenceChildPosition) || !view.hasFocusable())) {
                    break;
                }
                candidate = view;
            }
        }
        return candidate;
    }
}
