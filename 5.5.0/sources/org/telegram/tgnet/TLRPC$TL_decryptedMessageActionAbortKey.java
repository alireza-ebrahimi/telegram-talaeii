package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageAction;

public class TLRPC$TL_decryptedMessageActionAbortKey extends DecryptedMessageAction {
    public static int constructor = -586814357;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.exchange_id = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.exchange_id);
    }
}
