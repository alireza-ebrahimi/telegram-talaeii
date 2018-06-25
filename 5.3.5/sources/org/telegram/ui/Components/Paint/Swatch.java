package org.telegram.ui.Components.Paint;

public class Swatch {
    public float brushWeight;
    public int color;
    public float colorLocation;

    public Swatch(int color, float colorLocation, float brushWeight) {
        this.color = color;
        this.colorLocation = colorLocation;
        this.brushWeight = brushWeight;
    }
}
