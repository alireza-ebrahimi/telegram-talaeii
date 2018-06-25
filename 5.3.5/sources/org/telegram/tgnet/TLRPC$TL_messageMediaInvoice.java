package org.telegram.tgnet;

public class TLRPC$TL_messageMediaInvoice extends TLRPC$MessageMedia {
    public static int constructor = -2074799289;
    public TLRPC$TL_webDocument photo;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z = true;
        this.flags = stream.readInt32(exception);
        this.shipping_address_requested = (this.flags & 2) != 0;
        if ((this.flags & 8) == 0) {
            z = false;
        }
        this.test = z;
        this.title = stream.readString(exception);
        this.description = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            this.photo = TLRPC$TL_webDocument.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 4) != 0) {
            this.receipt_msg_id = stream.readInt32(exception);
        }
        this.currency = stream.readString(exception);
        this.total_amount = stream.readInt64(exception);
        this.start_param = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.shipping_address_requested ? this.flags | 2 : this.flags & -3;
        this.flags = this.test ? this.flags | 8 : this.flags & -9;
        stream.writeInt32(this.flags);
        stream.writeString(this.title);
        stream.writeString(this.description);
        if ((this.flags & 1) != 0) {
            this.photo.serializeToStream(stream);
        }
        if ((this.flags & 4) != 0) {
            stream.writeInt32(this.receipt_msg_id);
        }
        stream.writeString(this.currency);
        stream.writeInt64(this.total_amount);
        stream.writeString(this.start_param);
    }
}
