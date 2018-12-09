package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageAction;

public class TLRPC$TL_decryptedMessageActionAcceptKey extends DecryptedMessageAction {
    public static int constructor = 1877046107;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.exchange_id = abstractSerializedData.readInt64(z);
        this.g_b = abstractSerializedData.readByteArray(z);
        this.key_fingerprint = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.exchange_id);
        abstractSerializedData.writeByteArray(this.g_b);
        abstractSerializedData.writeInt64(this.key_fingerprint);
    }
}
