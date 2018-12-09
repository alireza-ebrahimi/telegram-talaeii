package org.telegram.messenger.audioinfo.m4a;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import org.telegram.messenger.audioinfo.util.PositionInputStream;
import org.telegram.messenger.audioinfo.util.RangeInputStream;

public class MP4Box<I extends PositionInputStream> {
    protected static final String ASCII = "ISO8859_1";
    private MP4Atom child;
    protected final DataInput data;
    private final I input;
    private final MP4Box<?> parent;
    private final String type;

    public MP4Box(I i, MP4Box<?> mP4Box, String str) {
        this.input = i;
        this.parent = mP4Box;
        this.type = str;
        this.data = new DataInputStream(i);
    }

    protected MP4Atom getChild() {
        return this.child;
    }

    public I getInput() {
        return this.input;
    }

    public MP4Box<?> getParent() {
        return this.parent;
    }

    public long getPosition() {
        return this.input.getPosition();
    }

    public String getType() {
        return this.type;
    }

    public MP4Atom nextChild() {
        if (this.child != null) {
            this.child.skip();
        }
        int readInt = this.data.readInt();
        byte[] bArr = new byte[4];
        this.data.readFully(bArr);
        MP4Atom mP4Atom = new MP4Atom(readInt == 1 ? new RangeInputStream(this.input, 16, this.data.readLong() - 16) : new RangeInputStream(this.input, 8, (long) (readInt - 8)), this, new String(bArr, ASCII));
        this.child = mP4Atom;
        return mP4Atom;
    }

    public MP4Atom nextChild(String str) {
        MP4Atom nextChild = nextChild();
        if (nextChild.getType().matches(str)) {
            return nextChild;
        }
        throw new IOException("atom type mismatch, expected " + str + ", got " + nextChild.getType());
    }
}
