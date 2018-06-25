package org.telegram.tgnet;

public class TLRPC$TL_pageBlockCover extends TLRPC$PageBlock {
    public static int constructor = 972174080;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.cover = TLRPC$PageBlock.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.cover.serializeToStream(stream);
    }
}
