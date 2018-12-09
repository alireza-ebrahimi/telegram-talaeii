package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageActionPaymentSent extends MessageAction {
    public static int constructor = 1080663248;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.currency = abstractSerializedData.readString(z);
        this.total_amount = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.currency);
        abstractSerializedData.writeInt64(this.total_amount);
    }
}
