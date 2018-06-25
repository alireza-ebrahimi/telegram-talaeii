package org.telegram.tgnet;

public class TLRPC$TL_updateChatParticipantAdmin extends TLRPC$Update {
    public static int constructor = -1232070311;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.is_admin = abstractSerializedData.readBool(z);
        this.version = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeBool(this.is_admin);
        abstractSerializedData.writeInt32(this.version);
    }
}
