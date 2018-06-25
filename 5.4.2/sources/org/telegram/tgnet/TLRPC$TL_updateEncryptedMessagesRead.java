package org.telegram.tgnet;

public class TLRPC$TL_updateEncryptedMessagesRead extends TLRPC$Update {
    public static int constructor = 956179895;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt32(z);
        this.max_date = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeInt32(this.max_date);
        abstractSerializedData.writeInt32(this.date);
    }
}
