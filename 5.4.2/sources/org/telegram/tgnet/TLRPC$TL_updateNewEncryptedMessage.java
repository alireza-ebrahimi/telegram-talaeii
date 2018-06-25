package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedMessage;

public class TLRPC$TL_updateNewEncryptedMessage extends TLRPC$Update {
    public static int constructor = 314359194;
    public EncryptedMessage message;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.message = EncryptedMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.qts = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.message.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.qts);
    }
}
