package org.telegram.tgnet;

public class TLRPC$TL_payments_paymentResult extends TLRPC$payments_PaymentResult {
    public static int constructor = 1314881805;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.updates = TLRPC$Updates.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.updates.serializeToStream(stream);
    }
}
