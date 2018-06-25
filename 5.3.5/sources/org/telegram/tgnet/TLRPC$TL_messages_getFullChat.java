package org.telegram.tgnet;

public class TLRPC$TL_messages_getFullChat extends TLObject {
    public static int constructor = 998448230;
    public int chat_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_chatFull.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
    }
}
