package org.telegram.tgnet;

public class TLRPC$TL_messages_addChatUser extends TLObject {
    public static int constructor = -106911223;
    public int chat_id;
    public int fwd_limit;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        this.user_id.serializeToStream(stream);
        stream.writeInt32(this.fwd_limit);
    }
}
