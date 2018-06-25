package org.telegram.tgnet;

public class TLRPC$TL_payments_validateRequestedInfo extends TLObject {
    public static int constructor = 1997180532;
    public int flags;
    public TLRPC$TL_paymentRequestedInfo info;
    public int msg_id;
    public boolean save;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_payments_validatedRequestedInfo.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.save ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.msg_id);
        this.info.serializeToStream(stream);
    }
}
