package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPaymentCredentials;

public class TLRPC$TL_payments_sendPaymentForm extends TLObject {
    public static int constructor = 730364339;
    public InputPaymentCredentials credentials;
    public int flags;
    public int msg_id;
    public String requested_info_id;
    public String shipping_option_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$payments_PaymentResult.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.msg_id);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeString(this.requested_info_id);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.shipping_option_id);
        }
        this.credentials.serializeToStream(abstractSerializedData);
    }
}
