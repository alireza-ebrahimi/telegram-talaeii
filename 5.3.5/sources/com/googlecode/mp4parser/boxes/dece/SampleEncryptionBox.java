package com.googlecode.mp4parser.boxes.dece;

import com.googlecode.mp4parser.boxes.AbstractSampleEncryptionBox;

public class SampleEncryptionBox extends AbstractSampleEncryptionBox {
    public static final String TYPE = "senc";

    public SampleEncryptionBox() {
        super(TYPE);
    }
}
