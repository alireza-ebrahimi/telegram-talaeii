package com.h6ah4i.android.widget.advrecyclerview.composedadapter;

import java.util.Arrays;

class SegmentedPositionTranslator {
    private static final int NO_CACHED_ITEM_COUNT = -1;
    private static final int NO_CACHED_SEGMENT = 0;
    private AdaptersSet mAdaptersSet;
    private int mCachedTotalItemCount = -1;
    private int mLastOffsetCachedSegment = 0;
    private int[] mSegmentItemCountCache = new int[128];
    private int[] mSegmentOffsetCache = new int[128];

    public SegmentedPositionTranslator(AdaptersSet adaptersSet) {
        this.mAdaptersSet = adaptersSet;
        Arrays.fill(this.mSegmentItemCountCache, -1);
    }

    public int getTotalItemCount() {
        if (this.mCachedTotalItemCount == -1) {
            this.mCachedTotalItemCount = countTotalItems();
        }
        return this.mCachedTotalItemCount;
    }

    public int getFlatPosition(int segment, int offset) {
        return getSegmentOffset(segment) + offset;
    }

    public long getSegmentedPosition(int flatPosition) {
        if (flatPosition == -1) {
            return -1;
        }
        int loopStartIndex;
        int segment;
        int localOffset;
        int binSearchResult = Arrays.binarySearch(this.mSegmentOffsetCache, 0, this.mLastOffsetCachedSegment, flatPosition);
        if (binSearchResult >= 0) {
            loopStartIndex = binSearchResult;
            segment = loopStartIndex;
            localOffset = 0;
        } else {
            loopStartIndex = Math.max(0, (binSearchResult ^ -1) - 1);
            segment = -1;
            localOffset = -1;
        }
        int nSegments = this.mAdaptersSet.getSegmentCount();
        int segmentOffset = this.mSegmentOffsetCache[loopStartIndex];
        for (int i = loopStartIndex; i < nSegments; i++) {
            int count = getSegmentItemCount(i);
            if (segmentOffset + count > flatPosition) {
                localOffset = flatPosition - segmentOffset;
                segment = i;
                break;
            }
            segmentOffset += count;
        }
        if (segment >= 0) {
            return AdaptersSet.composeSegmentedPosition(segment, localOffset);
        }
        return AdaptersSet.NO_SEGMENTED_POSITION;
    }

    private int countTotalItems() {
        int segmentCount = this.mAdaptersSet.getSegmentCount();
        if (segmentCount == 0) {
            return 0;
        }
        int lastSegment = segmentCount - 1;
        return getSegmentOffset(lastSegment) + getSegmentItemCount(lastSegment);
    }

    public int getSegmentOffset(int segment) {
        if (segment <= this.mLastOffsetCachedSegment) {
            return this.mSegmentOffsetCache[segment];
        }
        int nSegments = this.mAdaptersSet.getSegmentCount();
        int loopStartIndex = this.mLastOffsetCachedSegment;
        int offset = this.mSegmentOffsetCache[loopStartIndex];
        for (int i = loopStartIndex; i < segment; i++) {
            offset += getSegmentItemCount(i);
        }
        return offset;
    }

    public int getSegmentItemCount(int segment) {
        if (this.mSegmentItemCountCache[segment] != -1) {
            return this.mSegmentItemCountCache[segment];
        }
        int count = this.mAdaptersSet.getAdapter(segment).getItemCount();
        this.mSegmentItemCountCache[segment] = count;
        if (segment != this.mLastOffsetCachedSegment) {
            return count;
        }
        this.mSegmentOffsetCache[segment + 1] = this.mSegmentOffsetCache[segment] + count;
        this.mLastOffsetCachedSegment = segment + 1;
        return count;
    }

    public void invalidateSegment(int segment) {
        this.mCachedTotalItemCount = -1;
        this.mLastOffsetCachedSegment = Math.min(this.mLastOffsetCachedSegment, segment);
        this.mSegmentItemCountCache[segment] = -1;
    }

    public void invalidateAll() {
        this.mCachedTotalItemCount = -1;
        this.mLastOffsetCachedSegment = 0;
        Arrays.fill(this.mSegmentItemCountCache, -1);
    }

    public void release() {
        this.mAdaptersSet = null;
        this.mSegmentItemCountCache = null;
        this.mSegmentOffsetCache = null;
    }
}
