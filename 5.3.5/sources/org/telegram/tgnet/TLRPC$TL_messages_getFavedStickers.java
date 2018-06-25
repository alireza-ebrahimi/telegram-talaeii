package org.telegram.tgnet;

public class TLRPC$TL_messages_getFavedStickers extends TLObject {
    public static int constructor = 567151374;
    public int hash;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_FavedStickers.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.hash);
    }
}
