package org.telegram.tgnet;

public class TLRPC$TL_payments_getPaymentReceipt extends TLObject {
    public static int constructor = -1601001088;
    public int msg_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_payments_paymentReceipt.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.msg_id);
    }
}
