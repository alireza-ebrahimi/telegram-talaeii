package com.h6ah4i.android.widget.advrecyclerview.composedadapter;

import android.support.v4.internal.view.SupportMenu;
import android.util.SparseIntArray;
import com.h6ah4i.android.widget.advrecyclerview.adapter.ItemViewTypeComposer;

class SegmentedViewTypeTranslator {
    private SparseIntArray mUnwrapSegmentMap = new SparseIntArray();
    private SparseIntArray mWrapSegmentMap = new SparseIntArray();

    public int wrapItemViewType(int segment, int viewType) {
        int flattenSegments;
        int packedSegments = (segment << 16) | ItemViewTypeComposer.extractSegmentPart(viewType);
        int index = this.mWrapSegmentMap.indexOfKey(packedSegments);
        if (index >= 0) {
            flattenSegments = this.mWrapSegmentMap.valueAt(index);
        } else {
            flattenSegments = this.mWrapSegmentMap.size() + 1;
            if (flattenSegments > 127) {
                throw new IllegalStateException("Failed to allocate a new wrapped view type.");
            }
            this.mWrapSegmentMap.put(packedSegments, flattenSegments);
            this.mUnwrapSegmentMap.put(flattenSegments, packedSegments);
        }
        return ItemViewTypeComposer.composeSegment(flattenSegments, viewType);
    }

    public long unwrapViewType(int viewType) {
        int index = this.mUnwrapSegmentMap.indexOfKey(ItemViewTypeComposer.extractSegmentPart(viewType));
        if (index >= 0) {
            return (((long) this.mUnwrapSegmentMap.valueAt(index)) << 32) | (((long) viewType) & 4294967295L);
        }
        throw new IllegalStateException("Corresponding wrapped view type is not found!");
    }

    public static int extractWrappedViewType(long packedViewType) {
        return ItemViewTypeComposer.composeSegment(((int) (packedViewType >>> 32)) & SupportMenu.USER_MASK, (int) (4294967295L & packedViewType));
    }

    public static int extractWrapperSegment(long packedViewType) {
        return (int) ((packedViewType >>> 48) & 65535);
    }
}
