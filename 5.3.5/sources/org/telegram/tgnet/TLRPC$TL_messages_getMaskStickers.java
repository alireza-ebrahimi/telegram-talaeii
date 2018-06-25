package org.telegram.tgnet;

public class TLRPC$TL_messages_getMaskStickers extends TLObject {
    public static int constructor = 1706608543;
    public int hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_AllStickers.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.hash);
    }
}
