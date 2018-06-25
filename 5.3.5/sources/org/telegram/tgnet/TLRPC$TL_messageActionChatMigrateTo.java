package org.telegram.tgnet;

public class TLRPC$TL_messageActionChatMigrateTo extends TLRPC$MessageAction {
    public static int constructor = 1371385889;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.channel_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.channel_id);
    }
}
