package org.telegram.tgnet;

public class TLRPC$TL_messageActionChannelMigrateFrom extends TLRPC$MessageAction {
    public static int constructor = -1336546578;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.title = stream.readString(exception);
        this.chat_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.title);
        stream.writeInt32(this.chat_id);
    }
}
