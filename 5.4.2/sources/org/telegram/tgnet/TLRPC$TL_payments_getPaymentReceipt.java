package org.telegram.tgnet;

public class TLRPC$TL_payments_getPaymentReceipt extends TLObject {
    public static int constructor = -1601001088;
    public int msg_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_payments_paymentReceipt.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.msg_id);
    }
}
