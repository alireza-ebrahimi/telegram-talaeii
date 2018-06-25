package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractContainerBox;

public class SchemeInformationBox extends AbstractContainerBox {
    public static final String TYPE = "schi";

    public SchemeInformationBox() {
        super(TYPE);
    }
}
