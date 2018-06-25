package org.telegram.customization.speechrecognitionview;

import android.graphics.RectF;

public class RecognitionBar {
    private int height;
    private final int maxHeight;
    private int radius;
    private final RectF rect;
    private final int startX;
    private final int startY;
    /* renamed from: x */
    private int f58x;
    /* renamed from: y */
    private int f59y;

    public RecognitionBar(int x, int y, int height, int maxHeight, int radius) {
        this.f58x = x;
        this.f59y = y;
        this.radius = radius;
        this.startX = x;
        this.startY = y;
        this.height = height;
        this.maxHeight = maxHeight;
        this.rect = new RectF((float) (x - radius), (float) (y - (height / 2)), (float) (x + radius), (float) ((height / 2) + y));
    }

    public void update() {
        this.rect.set((float) (this.f58x - this.radius), (float) (this.f59y - (this.height / 2)), (float) (this.f58x + this.radius), (float) (this.f59y + (this.height / 2)));
    }

    public int getX() {
        return this.f58x;
    }

    public void setX(int x) {
        this.f58x = x;
    }

    public int getY() {
        return this.f59y;
    }

    public void setY(int y) {
        this.f59y = y;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public RectF getRect() {
        return this.rect;
    }

    public int getRadius() {
        return this.radius;
    }
}
