package com.googlecode.mp4parser.boxes.apple;

import com.coremedia.iso.boxes.GenreBox;

public class AppleGenreIDBox extends AppleVariableSignedIntegerBox {
    public AppleGenreIDBox() {
        super(GenreBox.TYPE);
    }
}
