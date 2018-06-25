package org.telegram.tgnet;

public class TLRPC$TL_payments_sendPaymentForm extends TLObject {
    public static int constructor = 730364339;
    public TLRPC$InputPaymentCredentials credentials;
    public int flags;
    public int msg_id;
    public String requested_info_id;
    public String shipping_option_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$payments_PaymentResult.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt32(this.msg_id);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.requested_info_id);
        }
        if ((this.flags & 2) != 0) {
            stream.writeString(this.shipping_option_id);
        }
        this.credentials.serializeToStream(stream);
    }
}
