package com.h6ah4i.android.widget.advrecyclerview.expandable;

class ExpandableAdapterHelper {
    private static final long LOWER_31BIT_MASK = 2147483647L;
    private static final long LOWER_32BIT_MASK = 4294967295L;
    public static final long NO_EXPANDABLE_POSITION = -1;

    public static long getPackedPositionForChild(int groupPosition, int childPosition) {
        return (((long) childPosition) << 32) | (((long) groupPosition) & LOWER_32BIT_MASK);
    }

    public static long getPackedPositionForGroup(int groupPosition) {
        return -4294967296L | (((long) groupPosition) & LOWER_32BIT_MASK);
    }

    public static int getPackedPositionChild(long packedPosition) {
        return (int) (packedPosition >>> 32);
    }

    public static int getPackedPositionGroup(long packedPosition) {
        return (int) (LOWER_32BIT_MASK & packedPosition);
    }

    private ExpandableAdapterHelper() {
    }
}
