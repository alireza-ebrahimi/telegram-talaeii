package org.telegram.messenger.support.widget;

import java.util.Arrays;

class StaggeredGridLayoutManager$AnchorInfo {
    boolean mInvalidateOffsets;
    boolean mLayoutFromEnd;
    int mOffset;
    int mPosition;
    int[] mSpanReferenceLines;
    boolean mValid;
    final /* synthetic */ StaggeredGridLayoutManager this$0;

    public StaggeredGridLayoutManager$AnchorInfo(StaggeredGridLayoutManager this$0) {
        this.this$0 = this$0;
        reset();
    }

    void reset() {
        this.mPosition = -1;
        this.mOffset = Integer.MIN_VALUE;
        this.mLayoutFromEnd = false;
        this.mInvalidateOffsets = false;
        this.mValid = false;
        if (this.mSpanReferenceLines != null) {
            Arrays.fill(this.mSpanReferenceLines, -1);
        }
    }

    void saveSpanReferenceLines(StaggeredGridLayoutManager$Span[] spans) {
        int spanCount = spans.length;
        if (this.mSpanReferenceLines == null || this.mSpanReferenceLines.length < spanCount) {
            this.mSpanReferenceLines = new int[this.this$0.mSpans.length];
        }
        for (int i = 0; i < spanCount; i++) {
            this.mSpanReferenceLines[i] = spans[i].getStartLine(Integer.MIN_VALUE);
        }
    }

    void assignCoordinateFromPadding() {
        int endAfterPadding;
        if (this.mLayoutFromEnd) {
            endAfterPadding = this.this$0.mPrimaryOrientation.getEndAfterPadding();
        } else {
            endAfterPadding = this.this$0.mPrimaryOrientation.getStartAfterPadding();
        }
        this.mOffset = endAfterPadding;
    }

    void assignCoordinateFromPadding(int addedDistance) {
        if (this.mLayoutFromEnd) {
            this.mOffset = this.this$0.mPrimaryOrientation.getEndAfterPadding() - addedDistance;
        } else {
            this.mOffset = this.this$0.mPrimaryOrientation.getStartAfterPadding() + addedDistance;
        }
    }
}
