package com.googlecode.mp4parser.boxes.apple;

public class AppleNameBox extends Utf8AppleDataBox {
    public static final String TYPE = "©nam";

    public AppleNameBox() {
        super(TYPE);
    }
}
