package org.telegram.tgnet;

public class TLRPC$TL_messages_toggleChatAdmins extends TLObject {
    public static int constructor = -326379039;
    public int chat_id;
    public boolean enabled;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeBool(this.enabled);
    }
}
