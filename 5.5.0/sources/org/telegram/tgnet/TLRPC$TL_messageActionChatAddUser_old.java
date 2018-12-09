package org.telegram.tgnet;

public class TLRPC$TL_messageActionChatAddUser_old extends TLRPC$TL_messageActionChatAddUser {
    public static int constructor = 1581055051;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
    }
}
