package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_documentAttributeImageSize extends DocumentAttribute {
    public static int constructor = 1815593308;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.w = stream.readInt32(exception);
        this.h = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.w);
        stream.writeInt32(this.h);
    }
}
