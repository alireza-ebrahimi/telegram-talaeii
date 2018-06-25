package org.telegram.messenger.audioinfo.m4a;

import java.io.IOException;
import java.io.InputStream;
import org.telegram.messenger.audioinfo.util.PositionInputStream;

public final class MP4Input extends MP4Box<PositionInputStream> {
    public MP4Input(InputStream delegate) {
        super(new PositionInputStream(delegate), null, "");
    }

    public MP4Atom nextChildUpTo(String expectedTypeExpression) throws IOException {
        MP4Atom atom;
        do {
            atom = nextChild();
        } while (!atom.getType().matches(expectedTypeExpression));
        return atom;
    }

    public String toString() {
        return "mp4[pos=" + getPosition() + "]";
    }
}
