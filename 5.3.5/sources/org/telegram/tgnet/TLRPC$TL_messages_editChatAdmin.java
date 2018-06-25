package org.telegram.tgnet;

public class TLRPC$TL_messages_editChatAdmin extends TLObject {
    public static int constructor = -1444503762;
    public int chat_id;
    public boolean is_admin;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        this.user_id.serializeToStream(stream);
        stream.writeBool(this.is_admin);
    }
}
