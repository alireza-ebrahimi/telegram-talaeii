package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaInvoice extends MessageMedia {
    public static int constructor = -2074799289;
    public TLRPC$TL_webDocument photo;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.shipping_address_requested = (this.flags & 2) != 0;
        if ((this.flags & 8) == 0) {
            z2 = false;
        }
        this.test = z2;
        this.title = abstractSerializedData.readString(z);
        this.description = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            this.photo = TLRPC$TL_webDocument.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 4) != 0) {
            this.receipt_msg_id = abstractSerializedData.readInt32(z);
        }
        this.currency = abstractSerializedData.readString(z);
        this.total_amount = abstractSerializedData.readInt64(z);
        this.start_param = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.shipping_address_requested ? this.flags | 2 : this.flags & -3;
        this.flags = this.test ? this.flags | 8 : this.flags & -9;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.description);
        if ((this.flags & 1) != 0) {
            this.photo.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeInt32(this.receipt_msg_id);
        }
        abstractSerializedData.writeString(this.currency);
        abstractSerializedData.writeInt64(this.total_amount);
        abstractSerializedData.writeString(this.start_param);
    }
}
