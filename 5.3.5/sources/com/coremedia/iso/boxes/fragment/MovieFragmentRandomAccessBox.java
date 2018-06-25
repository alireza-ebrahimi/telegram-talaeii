package com.coremedia.iso.boxes.fragment;

import com.googlecode.mp4parser.AbstractContainerBox;

public class MovieFragmentRandomAccessBox extends AbstractContainerBox {
    public static final String TYPE = "mfra";

    public MovieFragmentRandomAccessBox() {
        super(TYPE);
    }
}
