package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.annotation.IntRange;

public class ItemViewTypeComposer {
    public static final int BIT_MASK_EXPANDABLE_FLAG = Integer.MIN_VALUE;
    public static final int BIT_MASK_SEGMENT = 2130706432;
    public static final int BIT_MASK_WRAPPED_VIEW_TYPE = 16777215;
    public static final int BIT_OFFSET_EXPANDABLE_FLAG = 31;
    public static final int BIT_OFFSET_SEGMENT = 24;
    public static final int BIT_OFFSET_WRAPPED_VIEW_TYPE = 0;
    public static final int BIT_WIDTH_EXPANDABLE_FLAG = 1;
    public static final int BIT_WIDTH_SEGMENT = 7;
    public static final int BIT_WIDTH_WRAPPED_VIEW_TYPE = 24;
    public static final int MAX_SEGMENT = 127;
    public static final int MAX_WRAPPED_VIEW_TYPE = 8388607;
    public static final int MIN_SEGMENT = 0;
    public static final int MIN_WRAPPED_VIEW_TYPE = -8388608;

    private ItemViewTypeComposer() {
    }

    @IntRange(from = 0, to = 127)
    public static int extractSegmentPart(int composedViewType) {
        return (2130706432 & composedViewType) >>> 24;
    }

    @IntRange(from = -8388608, to = 8388607)
    public static int extractWrappedViewTypePart(int composedViewType) {
        return (composedViewType << 8) >> 8;
    }

    public static boolean isExpandableGroup(int composedViewType) {
        return (Integer.MIN_VALUE & composedViewType) != 0;
    }

    public static int composeSegment(@IntRange(from = 0, to = 127) int segment, int wrappedViewType) {
        if (segment >= 0 && segment <= 127) {
            return (segment << 24) | (-2130706433 & wrappedViewType);
        }
        throw new IllegalArgumentException("Segment value is out of range. (segment = " + segment + ")");
    }
}
