package com.coremedia.iso.boxes.apple;

import com.googlecode.mp4parser.AbstractContainerBox;

public class AppleReferenceMovieDescriptorBox extends AbstractContainerBox {
    public static final String TYPE = "rmda";

    public AppleReferenceMovieDescriptorBox() {
        super(TYPE);
    }
}
