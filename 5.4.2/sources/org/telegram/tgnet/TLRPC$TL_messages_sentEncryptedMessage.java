package org.telegram.tgnet;

public class TLRPC$TL_messages_sentEncryptedMessage extends TLRPC$messages_SentEncryptedMessage {
    public static int constructor = 1443858741;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.date = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.date);
    }
}
