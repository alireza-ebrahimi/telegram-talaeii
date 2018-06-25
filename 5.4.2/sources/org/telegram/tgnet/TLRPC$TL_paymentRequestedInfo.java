package org.telegram.tgnet;

public class TLRPC$TL_paymentRequestedInfo extends TLObject {
    public static int constructor = -1868808300;
    public String email;
    public int flags;
    public String name;
    public String phone;
    public TLRPC$TL_postAddress shipping_address;

    public static TLRPC$TL_paymentRequestedInfo TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_paymentRequestedInfo tLRPC$TL_paymentRequestedInfo = new TLRPC$TL_paymentRequestedInfo();
            tLRPC$TL_paymentRequestedInfo.readParams(abstractSerializedData, z);
            return tLRPC$TL_paymentRequestedInfo;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_paymentRequestedInfo", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.name = abstractSerializedData.readString(z);
        }
        if ((this.flags & 2) != 0) {
            this.phone = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.email = abstractSerializedData.readString(z);
        }
        if ((this.flags & 8) != 0) {
            this.shipping_address = TLRPC$TL_postAddress.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeString(this.name);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.phone);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeString(this.email);
        }
        if ((this.flags & 8) != 0) {
            this.shipping_address.serializeToStream(abstractSerializedData);
        }
    }
}
