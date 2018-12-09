package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Message;

public class TLRPC$TL_messageEmpty extends Message {
    public static int constructor = -2082087340;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
        this.to_id = new TLRPC$TL_peerUser();
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
    }
}
