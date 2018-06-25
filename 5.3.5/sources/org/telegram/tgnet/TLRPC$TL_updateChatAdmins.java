package org.telegram.tgnet;

public class TLRPC$TL_updateChatAdmins extends TLRPC$Update {
    public static int constructor = 1855224129;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.enabled = stream.readBool(exception);
        this.version = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeBool(this.enabled);
        stream.writeInt32(this.version);
    }
}
