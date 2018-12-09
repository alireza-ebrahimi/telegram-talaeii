package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedChat;

public class TLRPC$TL_encryptedChatEmpty extends EncryptedChat {
    public static int constructor = -1417756512;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
    }
}
