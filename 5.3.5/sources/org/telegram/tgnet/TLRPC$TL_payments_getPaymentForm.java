package org.telegram.tgnet;

public class TLRPC$TL_payments_getPaymentForm extends TLObject {
    public static int constructor = -1712285883;
    public int msg_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_payments_paymentForm.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.msg_id);
    }
}
