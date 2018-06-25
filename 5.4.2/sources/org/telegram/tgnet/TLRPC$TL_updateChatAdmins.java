package org.telegram.tgnet;

public class TLRPC$TL_updateChatAdmins extends TLRPC$Update {
    public static int constructor = 1855224129;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt32(z);
        this.enabled = abstractSerializedData.readBool(z);
        this.version = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeBool(this.enabled);
        abstractSerializedData.writeInt32(this.version);
    }
}
