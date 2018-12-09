package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedFile;
import org.telegram.tgnet.TLRPC.EncryptedMessage;

public class TLRPC$TL_encryptedMessage extends EncryptedMessage {
    public static int constructor = -317144808;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.random_id = abstractSerializedData.readInt64(z);
        this.chat_id = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        this.bytes = abstractSerializedData.readByteArray(z);
        this.file = EncryptedFile.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.random_id);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeByteArray(this.bytes);
        this.file.serializeToStream(abstractSerializedData);
    }
}
