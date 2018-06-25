package org.telegram.tgnet;

public class TLRPC$TL_payments_savedInfo extends TLObject {
    public static int constructor = -74456004;
    public int flags;
    public boolean has_saved_credentials;
    public TLRPC$TL_paymentRequestedInfo saved_info;

    public static TLRPC$TL_payments_savedInfo TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_payments_savedInfo result = new TLRPC$TL_payments_savedInfo();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_payments_savedInfo", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.has_saved_credentials = (this.flags & 2) != 0;
        if ((this.flags & 1) != 0) {
            this.saved_info = TLRPC$TL_paymentRequestedInfo.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.has_saved_credentials ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            this.saved_info.serializeToStream(stream);
        }
    }
}
