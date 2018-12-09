package org.telegram.tgnet;

public class TLRPC$TL_payments_paymentResult extends TLRPC$payments_PaymentResult {
    public static int constructor = 1314881805;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.updates = TLRPC$Updates.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.updates.serializeToStream(abstractSerializedData);
    }
}
