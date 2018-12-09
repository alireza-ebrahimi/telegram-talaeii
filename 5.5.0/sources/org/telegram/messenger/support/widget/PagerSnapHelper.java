package org.telegram.messenger.support.widget;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller.Action;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import org.telegram.messenger.support.widget.RecyclerView.State;

public class PagerSnapHelper extends SnapHelper {
    private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;

    private int distanceToCenter(LayoutManager layoutManager, View view, OrientationHelper orientationHelper) {
        return ((orientationHelper.getDecoratedMeasurement(view) / 2) + orientationHelper.getDecoratedStart(view)) - (layoutManager.getClipToPadding() ? orientationHelper.getStartAfterPadding() + (orientationHelper.getTotalSpace() / 2) : orientationHelper.getEnd() / 2);
    }

    private View findCenterView(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        View view = null;
        int childCount = layoutManager.getChildCount();
        if (childCount != 0) {
            int startAfterPadding = layoutManager.getClipToPadding() ? orientationHelper.getStartAfterPadding() + (orientationHelper.getTotalSpace() / 2) : orientationHelper.getEnd() / 2;
            int i = Integer.MAX_VALUE;
            int i2 = 0;
            while (i2 < childCount) {
                View view2;
                View childAt = layoutManager.getChildAt(i2);
                int abs = Math.abs((orientationHelper.getDecoratedStart(childAt) + (orientationHelper.getDecoratedMeasurement(childAt) / 2)) - startAfterPadding);
                if (abs < i) {
                    view2 = childAt;
                } else {
                    abs = i;
                    view2 = view;
                }
                i2++;
                view = view2;
                i = abs;
            }
        }
        return view;
    }

    private View findStartView(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        View view = null;
        int childCount = layoutManager.getChildCount();
        if (childCount != 0) {
            int i = Integer.MAX_VALUE;
            int i2 = 0;
            while (i2 < childCount) {
                View view2;
                View childAt = layoutManager.getChildAt(i2);
                int decoratedStart = orientationHelper.getDecoratedStart(childAt);
                if (decoratedStart < i) {
                    view2 = childAt;
                } else {
                    decoratedStart = i;
                    view2 = view;
                }
                i2++;
                view = view2;
                i = decoratedStart;
            }
        }
        return view;
    }

    private OrientationHelper getHorizontalHelper(LayoutManager layoutManager) {
        if (this.mHorizontalHelper == null || this.mHorizontalHelper.mLayoutManager != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }

    private OrientationHelper getVerticalHelper(LayoutManager layoutManager) {
        if (this.mVerticalHelper == null || this.mVerticalHelper.mLayoutManager != layoutManager) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    public int[] calculateDistanceToFinalSnap(LayoutManager layoutManager, View view) {
        int[] iArr = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            iArr[0] = distanceToCenter(layoutManager, view, getHorizontalHelper(layoutManager));
        } else {
            iArr[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            iArr[1] = distanceToCenter(layoutManager, view, getVerticalHelper(layoutManager));
        } else {
            iArr[1] = 0;
        }
        return iArr;
    }

    protected LinearSmoothScroller createSnapScroller(LayoutManager layoutManager) {
        return !(layoutManager instanceof ScrollVectorProvider) ? null : new LinearSmoothScroller(this.mRecyclerView.getContext()) {
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / ((float) displayMetrics.densityDpi);
            }

            protected int calculateTimeForScrolling(int i) {
                return Math.min(100, super.calculateTimeForScrolling(i));
            }

            protected void onTargetFound(View view, State state, Action action) {
                int[] calculateDistanceToFinalSnap = PagerSnapHelper.this.calculateDistanceToFinalSnap(PagerSnapHelper.this.mRecyclerView.getLayoutManager(), view);
                int i = calculateDistanceToFinalSnap[0];
                int i2 = calculateDistanceToFinalSnap[1];
                int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(i2)));
                if (calculateTimeForDeceleration > 0) {
                    action.update(i, i2, calculateTimeForDeceleration, this.mDecelerateInterpolator);
                }
            }
        };
    }

    public View findSnapView(LayoutManager layoutManager) {
        return layoutManager.canScrollVertically() ? findCenterView(layoutManager, getVerticalHelper(layoutManager)) : layoutManager.canScrollHorizontally() ? findCenterView(layoutManager, getHorizontalHelper(layoutManager)) : null;
    }

    public int findTargetSnapPosition(LayoutManager layoutManager, int i, int i2) {
        Object obj = null;
        int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return -1;
        }
        View view = null;
        if (layoutManager.canScrollVertically()) {
            view = findStartView(layoutManager, getVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            view = findStartView(layoutManager, getHorizontalHelper(layoutManager));
        }
        if (view == null) {
            return -1;
        }
        int position = layoutManager.getPosition(view);
        if (position == -1) {
            return -1;
        }
        Object obj2;
        if (layoutManager.canScrollHorizontally()) {
            obj2 = i > 0 ? 1 : null;
        } else if (i2 > 0) {
            int i3 = 1;
        } else {
            obj2 = null;
        }
        if (layoutManager instanceof ScrollVectorProvider) {
            PointF computeScrollVectorForPosition = ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(itemCount - 1);
            if (computeScrollVectorForPosition != null && (computeScrollVectorForPosition.x < BitmapDescriptorFactory.HUE_RED || computeScrollVectorForPosition.y < BitmapDescriptorFactory.HUE_RED)) {
                obj = 1;
            }
        }
        return obj != null ? obj2 != null ? position - 1 : position : obj2 != null ? position + 1 : position;
    }
}
