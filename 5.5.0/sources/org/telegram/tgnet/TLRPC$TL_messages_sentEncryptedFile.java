package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedFile;

public class TLRPC$TL_messages_sentEncryptedFile extends TLRPC$messages_SentEncryptedMessage {
    public static int constructor = -1802240206;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.date = abstractSerializedData.readInt32(z);
        this.file = EncryptedFile.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.date);
        this.file.serializeToStream(abstractSerializedData);
    }
}
