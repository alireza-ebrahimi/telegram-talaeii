package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractContainerBox;

public class TrackReferenceBox extends AbstractContainerBox {
    public static final String TYPE = "tref";

    public TrackReferenceBox() {
        super(TYPE);
    }
}
