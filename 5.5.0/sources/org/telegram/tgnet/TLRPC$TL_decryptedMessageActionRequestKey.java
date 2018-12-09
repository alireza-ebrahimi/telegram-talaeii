package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageAction;

public class TLRPC$TL_decryptedMessageActionRequestKey extends DecryptedMessageAction {
    public static int constructor = -204906213;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.exchange_id = abstractSerializedData.readInt64(z);
        this.g_a = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.exchange_id);
        abstractSerializedData.writeByteArray(this.g_a);
    }
}
