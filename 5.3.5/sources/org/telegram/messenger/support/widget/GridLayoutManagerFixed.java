package org.telegram.messenger.support.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.ArrayList;
import java.util.Arrays;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.Recycler;
import org.telegram.messenger.support.widget.RecyclerView.State;

public class GridLayoutManagerFixed extends GridLayoutManager {
    ArrayList<View> additionalViews = new ArrayList(4);

    public GridLayoutManagerFixed(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridLayoutManagerFixed(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    protected boolean hasSiblingChild(int position) {
        return false;
    }

    protected void recycleViewsFromStart(Recycler recycler, int dt) {
        if (dt >= 0) {
            int childCount = getChildCount();
            int i;
            View child;
            if (this.mShouldReverseLayout) {
                for (i = childCount - 1; i >= 0; i--) {
                    child = getChildAt(i);
                    if (child.getBottom() + ((LayoutParams) child.getLayoutParams()).bottomMargin > dt || child.getTop() + child.getHeight() > dt) {
                        recycleChildren(recycler, childCount - 1, i);
                        return;
                    }
                }
                return;
            }
            for (i = 0; i < childCount; i++) {
                child = getChildAt(i);
                if (this.mOrientationHelper.getDecoratedEnd(child) > dt || this.mOrientationHelper.getTransformedEndWithDecoration(child) > dt) {
                    recycleChildren(recycler, 0, i);
                    return;
                }
            }
        }
    }

    protected int[] calculateItemBorders(int[] cachedBorders, int spanCount, int totalSpace) {
        if (!(cachedBorders != null && cachedBorders.length == spanCount + 1 && cachedBorders[cachedBorders.length - 1] == totalSpace)) {
            cachedBorders = new int[(spanCount + 1)];
        }
        cachedBorders[0] = 0;
        for (int i = 1; i <= spanCount; i++) {
            cachedBorders[i] = (int) Math.ceil((double) ((((float) i) / ((float) spanCount)) * ((float) totalSpace)));
        }
        return cachedBorders;
    }

    public boolean shouldLayoutChildFromOpositeSide(View child) {
        return false;
    }

    protected void measureChild(View view, int otherDirParentSpecMode, boolean alreadyMeasured) {
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        Rect decorInsets = lp.mDecorInsets;
        int horizontalInsets = ((decorInsets.left + decorInsets.right) + lp.leftMargin) + lp.rightMargin;
        measureChildWithDecorationsAndMargin(view, getChildMeasureSpec(this.mCachedBorders[lp.mSpanSize], otherDirParentSpecMode, horizontalInsets, lp.width, false), getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), ((decorInsets.top + decorInsets.bottom) + lp.topMargin) + lp.bottomMargin, lp.height, true), alreadyMeasured);
    }

    void layoutChunk(Recycler recycler, State state, LinearLayoutManager$LayoutState layoutState, LinearLayoutManager$LayoutChunkResult result) {
        View view;
        int otherDirSpecMode = this.mOrientationHelper.getModeInOther();
        boolean layingOutInPrimaryDirection = layoutState.mItemDirection == 1;
        boolean working = true;
        result.mConsumed = 0;
        int startPosition = layoutState.mCurrentPosition;
        if (layoutState.mLayoutDirection != -1) {
            if (hasSiblingChild(layoutState.mCurrentPosition) && findViewByPosition(layoutState.mCurrentPosition + 1) == null) {
                if (hasSiblingChild(layoutState.mCurrentPosition + 1)) {
                    layoutState.mCurrentPosition += 3;
                } else {
                    layoutState.mCurrentPosition += 2;
                }
                int backupPosition = layoutState.mCurrentPosition;
                for (int a = layoutState.mCurrentPosition; a > startPosition; a--) {
                    view = layoutState.next(recycler);
                    this.additionalViews.add(view);
                    if (a != backupPosition) {
                        calculateItemDecorationsForChild(view, this.mDecorInsets);
                        measureChild(view, otherDirSpecMode, false);
                        int size = this.mOrientationHelper.getDecoratedMeasurement(view);
                        layoutState.mOffset -= size;
                        layoutState.mAvailable += size;
                    }
                }
                layoutState.mCurrentPosition = backupPosition;
            }
        }
        while (working) {
            int count = 0;
            int consumedSpanCount = 0;
            int remainingSpan = this.mSpanCount;
            working = !this.additionalViews.isEmpty();
            int firstPositionStart = layoutState.mCurrentPosition;
            while (count < this.mSpanCount && layoutState.hasMore(state) && remainingSpan > 0) {
                int pos = layoutState.mCurrentPosition;
                int spanSize = getSpanSize(recycler, state, pos);
                remainingSpan -= spanSize;
                if (remainingSpan < 0) {
                    break;
                }
                if (this.additionalViews.isEmpty()) {
                    view = layoutState.next(recycler);
                } else {
                    view = (View) this.additionalViews.get(0);
                    this.additionalViews.remove(0);
                    layoutState.mCurrentPosition--;
                }
                if (view == null) {
                    break;
                }
                consumedSpanCount += spanSize;
                this.mSet[count] = view;
                count++;
                if (layoutState.mLayoutDirection == -1 && remainingSpan <= 0 && hasSiblingChild(pos)) {
                    working = true;
                }
            }
            if (count == 0) {
                result.mFinished = true;
                return;
            }
            int i;
            int maxSize = 0;
            float maxSizeInOther = 0.0f;
            assignSpans(recycler, state, count, consumedSpanCount, layingOutInPrimaryDirection);
            for (i = 0; i < count; i++) {
                view = this.mSet[i];
                if (layoutState.mScrapList == null) {
                    if (layingOutInPrimaryDirection) {
                        addView(view);
                    } else {
                        addView(view, 0);
                    }
                } else if (layingOutInPrimaryDirection) {
                    addDisappearingView(view);
                } else {
                    addDisappearingView(view, 0);
                }
                calculateItemDecorationsForChild(view, this.mDecorInsets);
                measureChild(view, otherDirSpecMode, false);
                size = this.mOrientationHelper.getDecoratedMeasurement(view);
                if (size > maxSize) {
                    maxSize = size;
                }
                float otherSize = (1.0f * ((float) this.mOrientationHelper.getDecoratedMeasurementInOther(view))) / ((float) ((GridLayoutManager.LayoutParams) view.getLayoutParams()).mSpanSize);
                if (otherSize > maxSizeInOther) {
                    maxSizeInOther = otherSize;
                }
            }
            for (i = 0; i < count; i++) {
                view = this.mSet[i];
                if (this.mOrientationHelper.getDecoratedMeasurement(view) != maxSize) {
                    GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                    Rect decorInsets = lp.mDecorInsets;
                    measureChildWithDecorationsAndMargin(view, getChildMeasureSpec(this.mCachedBorders[lp.mSpanSize], 1073741824, ((decorInsets.left + decorInsets.right) + lp.leftMargin) + lp.rightMargin, lp.width, false), MeasureSpec.makeMeasureSpec(maxSize - (((decorInsets.top + decorInsets.bottom) + lp.topMargin) + lp.bottomMargin), 1073741824), true);
                }
            }
            boolean fromOpositeSide = shouldLayoutChildFromOpositeSide(this.mSet[0]);
            int bottom;
            int top;
            int left;
            GridLayoutManager.LayoutParams params;
            int right;
            if (!(fromOpositeSide && layoutState.mLayoutDirection == -1) && (fromOpositeSide || layoutState.mLayoutDirection != 1)) {
                if (layoutState.mLayoutDirection == -1) {
                    bottom = layoutState.mOffset - result.mConsumed;
                    top = bottom - maxSize;
                    left = getWidth();
                } else {
                    top = layoutState.mOffset + result.mConsumed;
                    bottom = top + maxSize;
                    left = 0;
                }
                for (i = 0; i < count; i++) {
                    view = this.mSet[i];
                    params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                    right = this.mOrientationHelper.getDecoratedMeasurementInOther(view);
                    if (layoutState.mLayoutDirection == -1) {
                        left -= right;
                    }
                    layoutDecoratedWithMargins(view, left, top, left + right, bottom);
                    if (layoutState.mLayoutDirection != -1) {
                        left += right;
                    }
                    if (params.isItemRemoved() || params.isItemChanged()) {
                        result.mIgnoreConsumed = true;
                    }
                    result.mFocusable |= view.hasFocusable();
                }
            } else {
                if (layoutState.mLayoutDirection == -1) {
                    bottom = layoutState.mOffset - result.mConsumed;
                    top = bottom - maxSize;
                    left = 0;
                } else {
                    top = layoutState.mOffset + result.mConsumed;
                    bottom = top + maxSize;
                    left = getWidth();
                }
                for (i = count - 1; i >= 0; i--) {
                    view = this.mSet[i];
                    params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                    right = this.mOrientationHelper.getDecoratedMeasurementInOther(view);
                    if (layoutState.mLayoutDirection == 1) {
                        left -= right;
                    }
                    layoutDecoratedWithMargins(view, left, top, left + right, bottom);
                    if (layoutState.mLayoutDirection == -1) {
                        left += right;
                    }
                    if (params.isItemRemoved() || params.isItemChanged()) {
                        result.mIgnoreConsumed = true;
                    }
                    result.mFocusable |= view.hasFocusable();
                }
            }
            result.mConsumed += maxSize;
            Arrays.fill(this.mSet, null);
        }
    }
}
