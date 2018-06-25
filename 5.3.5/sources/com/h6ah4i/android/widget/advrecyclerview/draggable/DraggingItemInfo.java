package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

public class DraggingItemInfo {
    public final int grabbedPositionX;
    public final int grabbedPositionY;
    public final int height;
    public final long id;
    public final int initialItemLeft;
    public final int initialItemTop;
    public final Rect margins;
    public final int spanSize;
    public final int width;

    public DraggingItemInfo(RecyclerView rv, ViewHolder vh, int touchX, int touchY) {
        this.width = vh.itemView.getWidth();
        this.height = vh.itemView.getHeight();
        this.id = vh.getItemId();
        this.initialItemLeft = vh.itemView.getLeft();
        this.initialItemTop = vh.itemView.getTop();
        this.grabbedPositionX = touchX - this.initialItemLeft;
        this.grabbedPositionY = touchY - this.initialItemTop;
        this.margins = new Rect();
        CustomRecyclerViewUtils.getLayoutMargins(vh.itemView, this.margins);
        this.spanSize = CustomRecyclerViewUtils.getSpanSize(vh);
    }

    private DraggingItemInfo(DraggingItemInfo info, ViewHolder vh) {
        this.id = info.id;
        this.width = vh.itemView.getWidth();
        this.height = vh.itemView.getHeight();
        this.margins = new Rect(info.margins);
        this.spanSize = CustomRecyclerViewUtils.getSpanSize(vh);
        this.initialItemLeft = info.initialItemLeft;
        this.initialItemTop = info.initialItemTop;
        float cx = ((float) this.width) * 0.5f;
        float cy = ((float) this.height) * 0.5f;
        float gpx = cx + (((float) info.grabbedPositionX) - (((float) info.width) * 0.5f));
        float gpy = cy + (((float) info.grabbedPositionY) - (((float) info.height) * 0.5f));
        if (gpx < 0.0f || gpx >= ((float) this.width)) {
            gpx = cx;
        }
        this.grabbedPositionX = (int) gpx;
        if (gpy < 0.0f || gpy >= ((float) this.height)) {
            gpy = cy;
        }
        this.grabbedPositionY = (int) gpy;
    }

    public static DraggingItemInfo createWithNewView(DraggingItemInfo info, ViewHolder vh) {
        return new DraggingItemInfo(info, vh);
    }
}
