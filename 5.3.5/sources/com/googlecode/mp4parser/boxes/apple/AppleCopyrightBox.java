package com.googlecode.mp4parser.boxes.apple;

import com.coremedia.iso.boxes.CopyrightBox;

public class AppleCopyrightBox extends Utf8AppleDataBox {
    public AppleCopyrightBox() {
        super(CopyrightBox.TYPE);
    }
}
