package com.h6ah4i.android.widget.advrecyclerview.expandable;

import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;

public class ChildPositionItemDraggableRange extends ItemDraggableRange {
    public ChildPositionItemDraggableRange(int start, int end) {
        super(start, end);
    }

    protected String getClassName() {
        return "ChildPositionItemDraggableRange";
    }
}
