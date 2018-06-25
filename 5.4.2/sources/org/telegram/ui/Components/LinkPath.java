package org.telegram.ui.Components;

import android.graphics.Path;
import android.graphics.Path.Direction;
import android.text.StaticLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class LinkPath extends Path {
    private StaticLayout currentLayout;
    private int currentLine;
    private float heightOffset;
    private float lastTop = -1.0f;

    public void addRect(float f, float f2, float f3, float f4, Direction direction) {
        float f5 = f2 + this.heightOffset;
        float f6 = f4 + this.heightOffset;
        if (this.lastTop == -1.0f) {
            this.lastTop = f5;
        } else if (this.lastTop != f5) {
            this.lastTop = f5;
            this.currentLine++;
        }
        float lineRight = this.currentLayout.getLineRight(this.currentLine);
        float lineLeft = this.currentLayout.getLineLeft(this.currentLine);
        if (f < lineRight) {
            if (f3 <= lineRight) {
                lineRight = f3;
            }
            if (f >= lineLeft) {
                lineLeft = f;
            }
            super.addRect(lineLeft, f5, lineRight, f6 - (f6 != ((float) this.currentLayout.getHeight()) ? this.currentLayout.getSpacingAdd() : BitmapDescriptorFactory.HUE_RED), direction);
        }
    }

    public void setCurrentLayout(StaticLayout staticLayout, int i, float f) {
        this.currentLayout = staticLayout;
        this.currentLine = staticLayout.getLineForOffset(i);
        this.lastTop = -1.0f;
        this.heightOffset = f;
    }
}
