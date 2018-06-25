package com.googlecode.mp4parser.h264.model;

public class AspectRatio {
    public static final AspectRatio Extended_SAR = new AspectRatio(255);
    private int value;

    private AspectRatio(int value) {
        this.value = value;
    }

    public static AspectRatio fromValue(int value) {
        if (value == Extended_SAR.value) {
            return Extended_SAR;
        }
        return new AspectRatio(value);
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AspectRatio{");
        sb.append("value=").append(this.value);
        sb.append('}');
        return sb.toString();
    }
}
