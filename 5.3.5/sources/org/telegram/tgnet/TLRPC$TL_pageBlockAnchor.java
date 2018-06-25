package org.telegram.tgnet;

public class TLRPC$TL_pageBlockAnchor extends TLRPC$PageBlock {
    public static int constructor = -837994576;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.name = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.name);
    }
}
