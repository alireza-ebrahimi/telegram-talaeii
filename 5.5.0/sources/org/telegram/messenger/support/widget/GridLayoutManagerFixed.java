package org.telegram.messenger.support.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import org.telegram.messenger.support.widget.GridLayoutManager.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Recycler;
import org.telegram.messenger.support.widget.RecyclerView.State;

public class GridLayoutManagerFixed extends GridLayoutManager {
    ArrayList<View> additionalViews = new ArrayList(4);

    public GridLayoutManagerFixed(Context context, int i) {
        super(context, i);
    }

    public GridLayoutManagerFixed(Context context, int i, int i2, boolean z) {
        super(context, i, i2, z);
    }

    protected int[] calculateItemBorders(int[] iArr, int i, int i2) {
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        iArr[0] = 0;
        for (int i3 = 1; i3 <= i; i3++) {
            iArr[i3] = (int) Math.ceil((double) ((((float) i3) / ((float) i)) * ((float) i2)));
        }
        return iArr;
    }

    protected boolean hasSiblingChild(int i) {
        return false;
    }

    void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int modeInOther = this.mOrientationHelper.getModeInOther();
        boolean z = layoutState.mItemDirection == 1;
        Object obj = 1;
        layoutChunkResult.mConsumed = 0;
        int i3 = layoutState.mCurrentPosition;
        if (layoutState.mLayoutDirection != -1) {
            if (hasSiblingChild(layoutState.mCurrentPosition) && findViewByPosition(layoutState.mCurrentPosition + 1) == null) {
                if (hasSiblingChild(layoutState.mCurrentPosition + 1)) {
                    layoutState.mCurrentPosition += 3;
                } else {
                    layoutState.mCurrentPosition += 2;
                }
                i = layoutState.mCurrentPosition;
                for (i2 = layoutState.mCurrentPosition; i2 > i3; i2--) {
                    View next = layoutState.next(recycler);
                    this.additionalViews.add(next);
                    if (i2 != i) {
                        calculateItemDecorationsForChild(next, this.mDecorInsets);
                        measureChild(next, modeInOther, false);
                        int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(next);
                        layoutState.mOffset -= decoratedMeasurement;
                        layoutState.mAvailable = decoratedMeasurement + layoutState.mAvailable;
                    }
                }
                layoutState.mCurrentPosition = i;
            }
        }
        while (obj != null) {
            decoratedMeasurement = 0;
            int i4 = 0;
            i3 = this.mSpanCount;
            Object obj2 = !this.additionalViews.isEmpty() ? 1 : null;
            i = layoutState.mCurrentPosition;
            obj = obj2;
            i2 = i3;
            while (decoratedMeasurement < this.mSpanCount && layoutState.hasMore(state) && i2 > 0) {
                i = layoutState.mCurrentPosition;
                int spanSize = getSpanSize(recycler, state, i);
                i3 = i2 - spanSize;
                if (i3 < 0) {
                    break;
                }
                View next2;
                if (this.additionalViews.isEmpty()) {
                    next2 = layoutState.next(recycler);
                } else {
                    next2 = (View) this.additionalViews.get(0);
                    this.additionalViews.remove(0);
                    layoutState.mCurrentPosition--;
                }
                if (next2 == null) {
                    break;
                }
                i4 += spanSize;
                this.mSet[decoratedMeasurement] = next2;
                decoratedMeasurement++;
                obj2 = (layoutState.mLayoutDirection == -1 && i3 <= 0 && hasSiblingChild(i)) ? 1 : obj;
                obj = obj2;
                i2 = i3;
            }
            if (decoratedMeasurement == 0) {
                layoutChunkResult.mFinished = true;
                return;
            }
            assignSpans(recycler, state, decoratedMeasurement, i4, z);
            i4 = 0;
            float f = BitmapDescriptorFactory.HUE_RED;
            i3 = 0;
            while (i4 < decoratedMeasurement) {
                View view = this.mSet[i4];
                if (layoutState.mScrapList == null) {
                    if (z) {
                        addView(view);
                    } else {
                        addView(view, 0);
                    }
                } else if (z) {
                    addDisappearingView(view);
                } else {
                    addDisappearingView(view, 0);
                }
                calculateItemDecorationsForChild(view, this.mDecorInsets);
                measureChild(view, modeInOther, false);
                i2 = this.mOrientationHelper.getDecoratedMeasurement(view);
                if (i2 > i3) {
                    i3 = i2;
                }
                float decoratedMeasurementInOther = (((float) this.mOrientationHelper.getDecoratedMeasurementInOther(view)) * 1.0f) / ((float) ((LayoutParams) view.getLayoutParams()).mSpanSize);
                if (decoratedMeasurementInOther <= f) {
                    decoratedMeasurementInOther = f;
                }
                i4++;
                f = decoratedMeasurementInOther;
            }
            for (i = 0; i < decoratedMeasurement; i++) {
                View view2 = this.mSet[i];
                if (this.mOrientationHelper.getDecoratedMeasurement(view2) != i3) {
                    LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
                    Rect rect = layoutParams.mDecorInsets;
                    measureChildWithDecorationsAndMargin(view2, LayoutManager.getChildMeasureSpec(this.mCachedBorders[layoutParams.mSpanSize], 1073741824, ((rect.right + rect.left) + layoutParams.leftMargin) + layoutParams.rightMargin, layoutParams.width, false), MeasureSpec.makeMeasureSpec(i3 - (((rect.top + rect.bottom) + layoutParams.topMargin) + layoutParams.bottomMargin), 1073741824), true);
                }
            }
            boolean shouldLayoutChildFromOpositeSide = shouldLayoutChildFromOpositeSide(this.mSet[0]);
            int i5;
            int i6;
            int i7;
            View view3;
            if (!(shouldLayoutChildFromOpositeSide && layoutState.mLayoutDirection == -1) && (shouldLayoutChildFromOpositeSide || layoutState.mLayoutDirection != 1)) {
                if (layoutState.mLayoutDirection == -1) {
                    i5 = layoutState.mOffset - layoutChunkResult.mConsumed;
                    i6 = i5 - i3;
                    i2 = getWidth();
                } else {
                    i6 = layoutState.mOffset + layoutChunkResult.mConsumed;
                    i5 = i6 + i3;
                    i2 = 0;
                }
                i7 = i2;
                for (i = 0; i < decoratedMeasurement; i++) {
                    view3 = this.mSet[i];
                    layoutParams = (LayoutParams) view3.getLayoutParams();
                    i4 = this.mOrientationHelper.getDecoratedMeasurementInOther(view3);
                    if (layoutState.mLayoutDirection == -1) {
                        i7 -= i4;
                    }
                    layoutDecoratedWithMargins(view3, i7, i6, i7 + i4, i5);
                    if (layoutState.mLayoutDirection != -1) {
                        i7 += i4;
                    }
                    if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                        layoutChunkResult.mIgnoreConsumed = true;
                    }
                    layoutChunkResult.mFocusable |= view3.hasFocusable();
                }
            } else {
                if (layoutState.mLayoutDirection == -1) {
                    i5 = layoutState.mOffset - layoutChunkResult.mConsumed;
                    i6 = i5 - i3;
                    i2 = 0;
                } else {
                    i6 = layoutState.mOffset + layoutChunkResult.mConsumed;
                    i5 = i6 + i3;
                    i2 = getWidth();
                }
                i7 = i2;
                for (i = decoratedMeasurement - 1; i >= 0; i--) {
                    view3 = this.mSet[i];
                    layoutParams = (LayoutParams) view3.getLayoutParams();
                    decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurementInOther(view3);
                    if (layoutState.mLayoutDirection == 1) {
                        i7 -= decoratedMeasurement;
                    }
                    layoutDecoratedWithMargins(view3, i7, i6, i7 + decoratedMeasurement, i5);
                    if (layoutState.mLayoutDirection == -1) {
                        i7 += decoratedMeasurement;
                    }
                    if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                        layoutChunkResult.mIgnoreConsumed = true;
                    }
                    layoutChunkResult.mFocusable |= view3.hasFocusable();
                }
            }
            layoutChunkResult.mConsumed += i3;
            Arrays.fill(this.mSet, null);
        }
    }

    protected void measureChild(View view, int i, boolean z) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int i2 = ((rect.top + rect.bottom) + layoutParams.topMargin) + layoutParams.bottomMargin;
        measureChildWithDecorationsAndMargin(view, LayoutManager.getChildMeasureSpec(this.mCachedBorders[layoutParams.mSpanSize], i, ((rect.right + rect.left) + layoutParams.leftMargin) + layoutParams.rightMargin, layoutParams.width, false), LayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i2, layoutParams.height, true), z);
    }

    protected void recycleViewsFromStart(Recycler recycler, int i) {
        if (i >= 0) {
            int childCount = getChildCount();
            View childAt;
            if (this.mShouldReverseLayout) {
                for (int i2 = childCount - 1; i2 >= 0; i2--) {
                    childAt = getChildAt(i2);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                    if (layoutParams.bottomMargin + childAt.getBottom() > i || childAt.getTop() + childAt.getHeight() > i) {
                        recycleChildren(recycler, childCount - 1, i2);
                        return;
                    }
                }
                return;
            }
            for (int i3 = 0; i3 < childCount; i3++) {
                childAt = getChildAt(i3);
                if (this.mOrientationHelper.getDecoratedEnd(childAt) > i || this.mOrientationHelper.getTransformedEndWithDecoration(childAt) > i) {
                    recycleChildren(recycler, 0, i3);
                    return;
                }
            }
        }
    }

    public boolean shouldLayoutChildFromOpositeSide(View view) {
        return false;
    }
}
