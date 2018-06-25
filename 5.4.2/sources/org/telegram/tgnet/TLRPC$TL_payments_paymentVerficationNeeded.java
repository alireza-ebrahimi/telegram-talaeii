package org.telegram.tgnet;

public class TLRPC$TL_payments_paymentVerficationNeeded extends TLRPC$payments_PaymentResult {
    public static int constructor = 1800845601;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
    }
}
