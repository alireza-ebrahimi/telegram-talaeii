package org.telegram.tgnet;

public class TLRPC$TL_payments_getPaymentForm extends TLObject {
    public static int constructor = -1712285883;
    public int msg_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_payments_paymentForm.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.msg_id);
    }
}
