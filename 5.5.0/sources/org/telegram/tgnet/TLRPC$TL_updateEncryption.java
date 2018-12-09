package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedChat;

public class TLRPC$TL_updateEncryption extends TLRPC$Update {
    public static int constructor = -1264392051;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat = EncryptedChat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.date = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.chat.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.date);
    }
}
