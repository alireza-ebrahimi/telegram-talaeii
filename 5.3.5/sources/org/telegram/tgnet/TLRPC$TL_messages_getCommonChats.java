package org.telegram.tgnet;

public class TLRPC$TL_messages_getCommonChats extends TLObject {
    public static int constructor = 218777796;
    public int limit;
    public int max_id;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Chats.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.user_id.serializeToStream(stream);
        stream.writeInt32(this.max_id);
        stream.writeInt32(this.limit);
    }
}
