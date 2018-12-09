package org.telegram.tgnet;

public class TLRPC$TL_payments_savedInfo extends TLObject {
    public static int constructor = -74456004;
    public int flags;
    public boolean has_saved_credentials;
    public TLRPC$TL_paymentRequestedInfo saved_info;

    public static TLRPC$TL_payments_savedInfo TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_payments_savedInfo tLRPC$TL_payments_savedInfo = new TLRPC$TL_payments_savedInfo();
            tLRPC$TL_payments_savedInfo.readParams(abstractSerializedData, z);
            return tLRPC$TL_payments_savedInfo;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_payments_savedInfo", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.has_saved_credentials = (this.flags & 2) != 0;
        if ((this.flags & 1) != 0) {
            this.saved_info = TLRPC$TL_paymentRequestedInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.has_saved_credentials ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            this.saved_info.serializeToStream(abstractSerializedData);
        }
    }
}
