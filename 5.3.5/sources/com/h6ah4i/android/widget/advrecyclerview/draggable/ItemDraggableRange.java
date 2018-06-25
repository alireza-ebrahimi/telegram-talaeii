package com.h6ah4i.android.widget.advrecyclerview.draggable;

public class ItemDraggableRange {
    private final int mEnd;
    private final int mStart;

    public ItemDraggableRange(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("end position (= " + end + ") is smaller than start position (=" + start + ")");
        }
        this.mStart = start;
        this.mEnd = end;
    }

    public int getStart() {
        return this.mStart;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public boolean checkInRange(int position) {
        return position >= this.mStart && position <= this.mEnd;
    }

    protected String getClassName() {
        return "ItemDraggableRange";
    }

    public String toString() {
        return getClassName() + "{mStart=" + this.mStart + ", mEnd=" + this.mEnd + '}';
    }
}
