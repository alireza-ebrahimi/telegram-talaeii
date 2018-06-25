package com.googlecode.mp4parser.boxes.apple;

import com.googlecode.mp4parser.AbstractContainerBox;

public class GenericMediaHeaderAtom extends AbstractContainerBox {
    public static final String TYPE = "gmhd";

    public GenericMediaHeaderAtom() {
        super(TYPE);
    }
}
