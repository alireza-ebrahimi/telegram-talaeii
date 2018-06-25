package com.h6ah4i.android.widget.advrecyclerview.adapter;

import android.support.annotation.IntRange;

public class ItemIdComposer {
    public static final long BIT_MASK_CHILD_ID = 268435455;
    public static final long BIT_MASK_GROUP_ID = 72057593769492480L;
    public static final long BIT_MASK_RESERVED_SIGN_FLAG = Long.MIN_VALUE;
    public static final long BIT_MASK_SEGMENT = 9151314442816847872L;
    public static final int BIT_OFFSET_CHILD_ID = 0;
    public static final int BIT_OFFSET_GROUP_ID = 28;
    public static final int BIT_OFFSET_RESERVED_SIGN_FLAG = 63;
    public static final int BIT_OFFSET_SEGMENT = 56;
    public static final int BIT_WIDTH_CHILD_ID = 28;
    public static final int BIT_WIDTH_GROUP_ID = 28;
    public static final int BIT_WIDTH_RESERVED_SIGN_FLAG = 1;
    public static final int BIT_WIDTH_SEGMENT = 7;
    public static final long MAX_CHILD_ID = 134217727;
    public static final long MAX_GROUP_ID = 134217727;
    public static final int MAX_SEGMENT = 127;
    public static final long MAX_WRAPPED_ID = 36028797018963967L;
    public static final long MIN_CHILD_ID = -134217728;
    public static final long MIN_GROUP_ID = -134217728;
    public static final int MIN_SEGMENT = 0;
    public static final long MIN_WRAPPED_ID = -36028797018963968L;

    private ItemIdComposer() {
    }

    public static long composeExpandableChildId(@IntRange(from = -134217728, to = 134217727) long groupId, @IntRange(from = -134217728, to = 134217727) long childId) {
        if (groupId < -134217728 || groupId > 134217727) {
            throw new IllegalArgumentException("Group ID value is out of range. (groupId = " + groupId + ")");
        } else if (childId >= -134217728 && childId <= 134217727) {
            return ((groupId << 28) & BIT_MASK_GROUP_ID) | ((childId << null) & BIT_MASK_CHILD_ID);
        } else {
            throw new IllegalArgumentException("Child ID value is out of range. (childId = " + childId + ")");
        }
    }

    public static long composeExpandableGroupId(@IntRange(from = -134217728, to = 134217727) long groupId) {
        if (groupId >= -134217728 && groupId <= 134217727) {
            return ((groupId << 28) & BIT_MASK_GROUP_ID) | BIT_MASK_CHILD_ID;
        }
        throw new IllegalArgumentException("Group ID value is out of range. (groupId = " + groupId + ")");
    }

    public static boolean isExpandableGroup(long composedId) {
        return composedId != -1 && (composedId & BIT_MASK_CHILD_ID) == BIT_MASK_CHILD_ID;
    }

    @IntRange(from = 0, to = 127)
    public static int extractSegmentPart(long composedId) {
        return (int) ((BIT_MASK_SEGMENT & composedId) >>> 56);
    }

    @IntRange(from = -134217728, to = 134217727)
    public static long extractExpandableGroupIdPart(long composedId) {
        if (composedId == -1 || !isExpandableGroup(composedId)) {
            return -1;
        }
        return (composedId << 8) >> 36;
    }

    @IntRange(from = -134217728, to = 134217727)
    public static long extractExpandableChildIdPart(long composedId) {
        if (composedId == -1 || isExpandableGroup(composedId)) {
            return -1;
        }
        return (composedId << 36) >> 36;
    }

    public static long extractWrappedIdPart(long composedId) {
        if (composedId == -1) {
            return -1;
        }
        return (composedId << 8) >> 8;
    }

    public static long composeSegment(@IntRange(from = 0, to = 127) int segment, long wrappedId) {
        if (segment >= 0 && segment <= 127) {
            return (((long) segment) << 56) | (-9151314442816847873L & wrappedId);
        }
        throw new IllegalArgumentException("Segment value is out of range. (segment = " + segment + ")");
    }
}
