package org.telegram.tgnet;

public class TLRPC$TL_updateEncryptedMessagesRead extends TLRPC$Update {
    public static int constructor = 956179895;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.max_date = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt32(this.max_date);
        stream.writeInt32(this.date);
    }
}
