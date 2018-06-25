package org.telegram.tgnet;

public class TLRPC$TL_payments_paymentVerficationNeeded extends TLRPC$payments_PaymentResult {
    public static int constructor = 1800845601;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
    }
}
