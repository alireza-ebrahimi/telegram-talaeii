package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeSticker_old2 extends TLRPC$TL_documentAttributeSticker {
    public static int constructor = -1723033470;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.alt = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.alt);
    }
}
