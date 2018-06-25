package org.telegram.tgnet;

public class TLRPC$TL_documentAttributeSticker_old extends TLRPC$TL_documentAttributeSticker {
    public static int constructor = -83208409;

    public void readParams(AbstractSerializedData stream, boolean exception) {
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
