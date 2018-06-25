package org.telegram.tgnet;

public class TLRPC$TL_paymentRequestedInfo extends TLObject {
    public static int constructor = -1868808300;
    public String email;
    public int flags;
    public String name;
    public String phone;
    public TLRPC$TL_postAddress shipping_address;

    public static TLRPC$TL_paymentRequestedInfo TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_paymentRequestedInfo result = new TLRPC$TL_paymentRequestedInfo();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_paymentRequestedInfo", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.name = stream.readString(exception);
        }
        if ((this.flags & 2) != 0) {
            this.phone = stream.readString(exception);
        }
        if ((this.flags & 4) != 0) {
            this.email = stream.readString(exception);
        }
        if ((this.flags & 8) != 0) {
            this.shipping_address = TLRPC$TL_postAddress.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.name);
        }
        if ((this.flags & 2) != 0) {
            stream.writeString(this.phone);
        }
        if ((this.flags & 4) != 0) {
            stream.writeString(this.email);
        }
        if ((this.flags & 8) != 0) {
            this.shipping_address.serializeToStream(stream);
        }
    }
}
