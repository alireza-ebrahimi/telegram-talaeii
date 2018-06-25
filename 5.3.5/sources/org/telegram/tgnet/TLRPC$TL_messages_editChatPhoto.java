package org.telegram.tgnet;

public class TLRPC$TL_messages_editChatPhoto extends TLObject {
    public static int constructor = -900957736;
    public int chat_id;
    public TLRPC$InputChatPhoto photo;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        this.photo.serializeToStream(stream);
    }
}
