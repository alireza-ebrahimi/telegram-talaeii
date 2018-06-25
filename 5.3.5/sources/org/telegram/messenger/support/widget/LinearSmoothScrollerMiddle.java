package org.telegram.messenger.support.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.ui.ChatActivity;

public class LinearSmoothScrollerMiddle extends SmoothScroller {
    private static final float MILLISECONDS_PER_INCH = 25.0f;
    private static final float TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2f;
    private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
    private final float MILLISECONDS_PER_PX;
    protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator(1.5f);
    protected int mInterimTargetDx = 0;
    protected int mInterimTargetDy = 0;
    protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    protected PointF mTargetVector;

    public LinearSmoothScrollerMiddle(Context context) {
        this.MILLISECONDS_PER_PX = MILLISECONDS_PER_INCH / ((float) context.getResources().getDisplayMetrics().densityDpi);
    }

    protected void onStart() {
    }

    protected void onTargetFound(View targetView, State state, RecyclerView$SmoothScroller$Action action) {
        int dy = calculateDyToMakeVisible(targetView);
        int time = calculateTimeForDeceleration(dy);
        if (time > 0) {
            action.update(0, -dy, Math.max(ChatActivity.scheduleDownloads, time), this.mDecelerateInterpolator);
        }
    }

    protected void onSeekTargetStep(int dx, int dy, State state, RecyclerView$SmoothScroller$Action action) {
        if (getChildCount() == 0) {
            stop();
            return;
        }
        this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, dx);
        this.mInterimTargetDy = clampApplyScroll(this.mInterimTargetDy, dy);
        if (this.mInterimTargetDx == 0 && this.mInterimTargetDy == 0) {
            updateActionForInterimTarget(action);
        }
    }

    protected void onStop() {
        this.mInterimTargetDy = 0;
        this.mInterimTargetDx = 0;
        this.mTargetVector = null;
    }

    protected int calculateTimeForDeceleration(int dx) {
        return (int) Math.ceil(((double) calculateTimeForScrolling(dx)) / 0.3356d);
    }

    protected int calculateTimeForScrolling(int dx) {
        return (int) Math.ceil((double) (((float) Math.abs(dx)) * this.MILLISECONDS_PER_PX));
    }

    protected void updateActionForInterimTarget(RecyclerView$SmoothScroller$Action action) {
        PointF scrollVector = computeScrollVectorForPosition(getTargetPosition());
        if (scrollVector == null || (scrollVector.x == 0.0f && scrollVector.y == 0.0f)) {
            action.jumpTo(getTargetPosition());
            stop();
            return;
        }
        normalize(scrollVector);
        this.mTargetVector = scrollVector;
        this.mInterimTargetDx = (int) (scrollVector.x * 10000.0f);
        this.mInterimTargetDy = (int) (scrollVector.y * 10000.0f);
        action.update((int) (((float) this.mInterimTargetDx) * 1.2f), (int) (((float) this.mInterimTargetDy) * 1.2f), (int) (((float) calculateTimeForScrolling(10000)) * 1.2f), this.mLinearInterpolator);
    }

    private int clampApplyScroll(int tmpDt, int dt) {
        int before = tmpDt;
        tmpDt -= dt;
        if (before * tmpDt <= 0) {
            return 0;
        }
        return tmpDt;
    }

    public int calculateDyToMakeVisible(View view) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null || !layoutManager.canScrollVertically()) {
            return 0;
        }
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        int top = layoutManager.getDecoratedTop(view) - params.topMargin;
        int bottom = layoutManager.getDecoratedBottom(view) + params.bottomMargin;
        int start = layoutManager.getPaddingTop();
        int end = layoutManager.getHeight() - layoutManager.getPaddingBottom();
        if (top > start && bottom < end) {
            return 0;
        }
        int viewSize = bottom - top;
        start = ((end - start) - viewSize) / 2;
        end = start + viewSize;
        int dtStart = start - top;
        if (dtStart > 0) {
            return dtStart;
        }
        int dtEnd = end - bottom;
        if (dtEnd < 0) {
            return dtEnd;
        }
        return 0;
    }

    @Nullable
    public PointF computeScrollVectorForPosition(int targetPosition) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof RecyclerView$SmoothScroller$ScrollVectorProvider) {
            return ((RecyclerView$SmoothScroller$ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(targetPosition);
        }
        return null;
    }
}
