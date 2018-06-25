package org.telegram.tgnet;

public class TLRPC$TL_inputStickeredMediaDocument extends TLRPC$InputStickeredMedia {
    public static int constructor = 70813275;
    public TLRPC$InputDocument id;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = TLRPC$InputDocument.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
    }
}
