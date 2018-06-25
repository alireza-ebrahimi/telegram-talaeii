package org.telegram.messenger.support.widget;

import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller.Action;
import org.telegram.messenger.support.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
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

    private int clampApplyScroll(int i, int i2) {
        int i3 = i - i2;
        return i * i3 <= 0 ? 0 : i3;
    }

    public int calculateDyToMakeVisible(View view) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null || !layoutManager.canScrollVertically()) {
            return 0;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int decoratedTop = layoutManager.getDecoratedTop(view) - layoutParams.topMargin;
        int decoratedBottom = layoutManager.getDecoratedBottom(view) + layoutParams.bottomMargin;
        int paddingTop = layoutManager.getPaddingTop();
        int height = layoutManager.getHeight() - layoutManager.getPaddingBottom();
        if (decoratedTop > paddingTop && decoratedBottom < height) {
            return 0;
        }
        paddingTop = height - paddingTop;
        height = decoratedBottom - decoratedTop;
        paddingTop = (paddingTop - height) / 2;
        height += paddingTop;
        paddingTop -= decoratedTop;
        if (paddingTop > 0) {
            return paddingTop;
        }
        paddingTop = height - decoratedBottom;
        return paddingTop >= 0 ? 0 : paddingTop;
    }

    protected int calculateTimeForDeceleration(int i) {
        return (int) Math.ceil(((double) calculateTimeForScrolling(i)) / 0.3356d);
    }

    protected int calculateTimeForScrolling(int i) {
        return (int) Math.ceil((double) (((float) Math.abs(i)) * this.MILLISECONDS_PER_PX));
    }

    public PointF computeScrollVectorForPosition(int i) {
        LayoutManager layoutManager = getLayoutManager();
        return layoutManager instanceof ScrollVectorProvider ? ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(i) : null;
    }

    protected void onSeekTargetStep(int i, int i2, State state, Action action) {
        if (getChildCount() == 0) {
            stop();
            return;
        }
        this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, i);
        this.mInterimTargetDy = clampApplyScroll(this.mInterimTargetDy, i2);
        if (this.mInterimTargetDx == 0 && this.mInterimTargetDy == 0) {
            updateActionForInterimTarget(action);
        }
    }

    protected void onStart() {
    }

    protected void onStop() {
        this.mInterimTargetDy = 0;
        this.mInterimTargetDx = 0;
        this.mTargetVector = null;
    }

    protected void onTargetFound(View view, State state, Action action) {
        int calculateDyToMakeVisible = calculateDyToMakeVisible(view);
        int calculateTimeForDeceleration = calculateTimeForDeceleration(calculateDyToMakeVisible);
        if (calculateTimeForDeceleration > 0) {
            action.update(0, -calculateDyToMakeVisible, Math.max(ChatActivity.scheduleDownloads, calculateTimeForDeceleration), this.mDecelerateInterpolator);
        }
    }

    protected void updateActionForInterimTarget(Action action) {
        PointF computeScrollVectorForPosition = computeScrollVectorForPosition(getTargetPosition());
        if (computeScrollVectorForPosition == null || (computeScrollVectorForPosition.x == BitmapDescriptorFactory.HUE_RED && computeScrollVectorForPosition.y == BitmapDescriptorFactory.HUE_RED)) {
            action.jumpTo(getTargetPosition());
            stop();
            return;
        }
        normalize(computeScrollVectorForPosition);
        this.mTargetVector = computeScrollVectorForPosition;
        this.mInterimTargetDx = (int) (computeScrollVectorForPosition.x * 10000.0f);
        this.mInterimTargetDy = (int) (computeScrollVectorForPosition.y * 10000.0f);
        action.update((int) (((float) this.mInterimTargetDx) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) this.mInterimTargetDy) * TARGET_SEEK_EXTRA_SCROLL_RATIO), (int) (((float) calculateTimeForScrolling(10000)) * TARGET_SEEK_EXTRA_SCROLL_RATIO), this.mLinearInterpolator);
    }
}
