package com.googlecode.mp4parser.boxes.apple;

import com.googlecode.mp4parser.AbstractContainerBox;

public class TrackApertureModeDimensionAtom extends AbstractContainerBox {
    public static final String TYPE = "tapt";

    public TrackApertureModeDimensionAtom() {
        super(TYPE);
    }
}
