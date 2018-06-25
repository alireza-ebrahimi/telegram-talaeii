package org.telegram.messenger.support.widget;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;

public class LinearSnapHelper extends SnapHelper {
    private static final float INVALID_DISTANCE = 1.0f;
    @Nullable
    private OrientationHelper mHorizontalHelper;
    @Nullable
    private OrientationHelper mVerticalHelper;

    public int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToCenter(layoutManager, targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToCenter(layoutManager, targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    public int findTargetSnapPosition(LayoutManager layoutManager, int velocityX, int velocityY) {
        if (!(layoutManager instanceof RecyclerView$SmoothScroller$ScrollVectorProvider)) {
            return -1;
        }
        int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return -1;
        }
        View currentView = findSnapView(layoutManager);
        if (currentView == null) {
            return -1;
        }
        int currentPosition = layoutManager.getPosition(currentView);
        if (currentPosition == -1) {
            return -1;
        }
        PointF vectorForEnd = ((RecyclerView$SmoothScroller$ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            return -1;
        }
        int hDeltaJump;
        int vDeltaJump;
        int deltaJump;
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager, getHorizontalHelper(layoutManager), velocityX, 0);
            if (vectorForEnd.x < 0.0f) {
                hDeltaJump = -hDeltaJump;
            }
        } else {
            hDeltaJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            vDeltaJump = estimateNextPositionDiffForFling(layoutManager, getVerticalHelper(layoutManager), 0, velocityY);
            if (vectorForEnd.y < 0.0f) {
                vDeltaJump = -vDeltaJump;
            }
        } else {
            vDeltaJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            deltaJump = vDeltaJump;
        } else {
            deltaJump = hDeltaJump;
        }
        if (deltaJump == 0) {
            return -1;
        }
        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            return itemCount - 1;
        }
        return targetPos;
    }

    public View findSnapView(LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return findCenterView(layoutManager, getVerticalHelper(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return findCenterView(layoutManager, getHorizontalHelper(layoutManager));
        }
        return null;
    }

    private int distanceToCenter(@NonNull LayoutManager layoutManager, @NonNull View targetView, OrientationHelper helper) {
        int containerCenter;
        int childCenter = helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView) / 2);
        if (layoutManager.getClipToPadding()) {
            containerCenter = helper.getStartAfterPadding() + (helper.getTotalSpace() / 2);
        } else {
            containerCenter = helper.getEnd() / 2;
        }
        return childCenter - containerCenter;
    }

    private int estimateNextPositionDiffForFling(LayoutManager layoutManager, OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0.0f) {
            return 0;
        }
        int distance = Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];
        if (distance > 0) {
            return (int) Math.floor((double) (((float) distance) / distancePerChild));
        }
        return (int) Math.ceil((double) (((float) distance) / distancePerChild));
    }

    @Nullable
    private View findCenterView(LayoutManager layoutManager, OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }
        int center;
        View closestChild = null;
        if (layoutManager.getClipToPadding()) {
            center = helper.getStartAfterPadding() + (helper.getTotalSpace() / 2);
        } else {
            center = helper.getEnd() / 2;
        }
        int absClosest = Integer.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            int absDistance = Math.abs((helper.getDecoratedStart(child) + (helper.getDecoratedMeasurement(child) / 2)) - center);
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

    private float computeDistancePerChild(LayoutManager layoutManager, OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return 1.0f;
        }
        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            int pos = layoutManager.getPosition(child);
            if (pos != -1) {
                if (pos < minPos) {
                    minPos = pos;
                    minPosView = child;
                }
                if (pos > maxPos) {
                    maxPos = pos;
                    maxPosView = child;
                }
            }
        }
        if (minPosView == null || maxPosView == null) {
            return 1.0f;
        }
        int distance = Math.max(helper.getDecoratedEnd(minPosView), helper.getDecoratedEnd(maxPosView)) - Math.min(helper.getDecoratedStart(minPosView), helper.getDecoratedStart(maxPosView));
        if (distance == 0) {
            return 1.0f;
        }
        return (1.0f * ((float) distance)) / ((float) ((maxPos - minPos) + 1));
    }

    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull LayoutManager layoutManager) {
        if (this.mVerticalHelper == null || this.mVerticalHelper.mLayoutManager != layoutManager) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(@NonNull LayoutManager layoutManager) {
        if (this.mHorizontalHelper == null || this.mHorizontalHelper.mLayoutManager != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }
}
