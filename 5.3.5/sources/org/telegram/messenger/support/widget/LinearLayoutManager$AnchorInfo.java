package org.telegram.messenger.support.widget;

import android.view.View;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.State;

class LinearLayoutManager$AnchorInfo {
    int mCoordinate;
    boolean mLayoutFromEnd;
    int mPosition;
    boolean mValid;
    final /* synthetic */ LinearLayoutManager this$0;

    LinearLayoutManager$AnchorInfo(LinearLayoutManager this$0) {
        this.this$0 = this$0;
        reset();
    }

    void reset() {
        this.mPosition = -1;
        this.mCoordinate = Integer.MIN_VALUE;
        this.mLayoutFromEnd = false;
        this.mValid = false;
    }

    void assignCoordinateFromPadding() {
        int endAfterPadding;
        if (this.mLayoutFromEnd) {
            endAfterPadding = this.this$0.mOrientationHelper.getEndAfterPadding();
        } else {
            endAfterPadding = this.this$0.mOrientationHelper.getStartAfterPadding();
        }
        this.mCoordinate = endAfterPadding;
    }

    public String toString() {
        return "AnchorInfo{mPosition=" + this.mPosition + ", mCoordinate=" + this.mCoordinate + ", mLayoutFromEnd=" + this.mLayoutFromEnd + ", mValid=" + this.mValid + '}';
    }

    boolean isViewValidAsAnchor(View child, State state) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        return !lp.isItemRemoved() && lp.getViewLayoutPosition() >= 0 && lp.getViewLayoutPosition() < state.getItemCount();
    }

    public void assignFromViewAndKeepVisibleRect(View child) {
        int spaceChange = this.this$0.mOrientationHelper.getTotalSpaceChange();
        if (spaceChange >= 0) {
            assignFromView(child);
            return;
        }
        this.mPosition = this.this$0.getPosition(child);
        int startMargin;
        if (this.mLayoutFromEnd) {
            int previousEndMargin = (this.this$0.mOrientationHelper.getEndAfterPadding() - spaceChange) - this.this$0.mOrientationHelper.getDecoratedEnd(child);
            this.mCoordinate = this.this$0.mOrientationHelper.getEndAfterPadding() - previousEndMargin;
            if (previousEndMargin > 0) {
                int estimatedChildStart = this.mCoordinate - this.this$0.mOrientationHelper.getDecoratedMeasurement(child);
                int layoutStart = this.this$0.mOrientationHelper.getStartAfterPadding();
                startMargin = estimatedChildStart - (layoutStart + Math.min(this.this$0.mOrientationHelper.getDecoratedStart(child) - layoutStart, 0));
                if (startMargin < 0) {
                    this.mCoordinate += Math.min(previousEndMargin, -startMargin);
                    return;
                }
                return;
            }
            return;
        }
        int childStart = this.this$0.mOrientationHelper.getDecoratedStart(child);
        startMargin = childStart - this.this$0.mOrientationHelper.getStartAfterPadding();
        this.mCoordinate = childStart;
        if (startMargin > 0) {
            int endMargin = (this.this$0.mOrientationHelper.getEndAfterPadding() - Math.min(0, (this.this$0.mOrientationHelper.getEndAfterPadding() - spaceChange) - this.this$0.mOrientationHelper.getDecoratedEnd(child))) - (childStart + this.this$0.mOrientationHelper.getDecoratedMeasurement(child));
            if (endMargin < 0) {
                this.mCoordinate -= Math.min(startMargin, -endMargin);
            }
        }
    }

    public void assignFromView(View child) {
        if (this.mLayoutFromEnd) {
            this.mCoordinate = this.this$0.mOrientationHelper.getDecoratedEnd(child) + this.this$0.mOrientationHelper.getTotalSpaceChange();
        } else {
            this.mCoordinate = this.this$0.mOrientationHelper.getDecoratedStart(child);
        }
        this.mPosition = this.this$0.getPosition(child);
    }
}
