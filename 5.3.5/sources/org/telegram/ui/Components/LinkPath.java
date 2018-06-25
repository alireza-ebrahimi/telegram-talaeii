package org.telegram.ui.Components;

import android.graphics.Path;
import android.graphics.Path.Direction;
import android.text.StaticLayout;

public class LinkPath extends Path {
    private StaticLayout currentLayout;
    private int currentLine;
    private float heightOffset;
    private float lastTop = -1.0f;

    public void setCurrentLayout(StaticLayout layout, int start, float yOffset) {
        this.currentLayout = layout;
        this.currentLine = layout.getLineForOffset(start);
        this.lastTop = -1.0f;
        this.heightOffset = yOffset;
    }

    public void addRect(float left, float top, float right, float bottom, Direction dir) {
        top += this.heightOffset;
        bottom += this.heightOffset;
        if (this.lastTop == -1.0f) {
            this.lastTop = top;
        } else if (this.lastTop != top) {
            this.lastTop = top;
            this.currentLine++;
        }
        float lineRight = this.currentLayout.getLineRight(this.currentLine);
        float lineLeft = this.currentLayout.getLineLeft(this.currentLine);
        if (left < lineRight) {
            if (right > lineRight) {
                right = lineRight;
            }
            if (left < lineLeft) {
                left = lineLeft;
            }
            super.addRect(left, top, right, bottom - (bottom != ((float) this.currentLayout.getHeight()) ? this.currentLayout.getSpacingAdd() : 0.0f), dir);
        }
    }
}
