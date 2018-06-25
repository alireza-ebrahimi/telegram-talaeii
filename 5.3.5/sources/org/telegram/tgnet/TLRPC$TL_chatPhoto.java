package org.telegram.tgnet;

public class TLRPC$TL_chatPhoto extends TLRPC$ChatPhoto {
    public static int constructor = 1632839530;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.photo_small = TLRPC$FileLocation.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.photo_big = TLRPC$FileLocation.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.photo_small.serializeToStream(stream);
        this.photo_big.serializeToStream(stream);
    }
}
