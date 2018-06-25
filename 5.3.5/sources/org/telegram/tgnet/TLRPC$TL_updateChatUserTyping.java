package org.telegram.tgnet;

public class TLRPC$TL_updateChatUserTyping extends TLRPC$Update {
    public static int constructor = -1704596961;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
        this.action = TLRPC$SendMessageAction.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.user_id);
        this.action.serializeToStream(stream);
    }
}
