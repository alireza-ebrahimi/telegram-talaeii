package org.telegram.tgnet;

public class TLRPC$TL_messages_getAllDrafts extends TLObject {
    public static int constructor = 1782549861;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
