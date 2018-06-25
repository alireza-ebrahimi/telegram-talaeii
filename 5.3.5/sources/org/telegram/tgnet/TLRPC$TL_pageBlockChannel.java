package org.telegram.tgnet;

public class TLRPC$TL_pageBlockChannel extends TLRPC$PageBlock {
    public static int constructor = -283684427;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.channel = TLRPC$Chat.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
    }
}
