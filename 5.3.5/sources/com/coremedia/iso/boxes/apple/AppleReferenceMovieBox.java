package com.coremedia.iso.boxes.apple;

import com.googlecode.mp4parser.AbstractContainerBox;

public class AppleReferenceMovieBox extends AbstractContainerBox {
    public static final String TYPE = "rmra";

    public AppleReferenceMovieBox() {
        super(TYPE);
    }
}
