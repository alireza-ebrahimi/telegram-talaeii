package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractContainerBox;

public class EditBox extends AbstractContainerBox {
    public static final String TYPE = "edts";

    public EditBox() {
        super(TYPE);
    }
}
