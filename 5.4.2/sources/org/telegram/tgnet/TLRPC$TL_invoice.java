package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_invoice extends TLObject {
    public static int constructor = -1022713000;
    public String currency;
    public boolean email_requested;
    public boolean email_to_provider;
    public int flags;
    public boolean flexible;
    public boolean name_requested;
    public boolean phone_requested;
    public boolean phone_to_provider;
    public ArrayList<TLRPC$TL_labeledPrice> prices = new ArrayList();
    public boolean shipping_address_requested;
    public boolean test;

    public static TLRPC$TL_invoice TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_invoice tLRPC$TL_invoice = new TLRPC$TL_invoice();
            tLRPC$TL_invoice.readParams(abstractSerializedData, z);
            return tLRPC$TL_invoice;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_invoice", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.test = (this.flags & 1) != 0;
        this.name_requested = (this.flags & 2) != 0;
        this.phone_requested = (this.flags & 4) != 0;
        this.email_requested = (this.flags & 8) != 0;
        this.shipping_address_requested = (this.flags & 16) != 0;
        this.flexible = (this.flags & 32) != 0;
        this.phone_to_provider = (this.flags & 64) != 0;
        this.email_to_provider = (this.flags & 128) != 0;
        this.currency = abstractSerializedData.readString(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                TLRPC$TL_labeledPrice TLdeserialize = TLRPC$TL_labeledPrice.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.prices.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.test ? this.flags | 1 : this.flags & -2;
        this.flags = this.name_requested ? this.flags | 2 : this.flags & -3;
        this.flags = this.phone_requested ? this.flags | 4 : this.flags & -5;
        this.flags = this.email_requested ? this.flags | 8 : this.flags & -9;
        this.flags = this.shipping_address_requested ? this.flags | 16 : this.flags & -17;
        this.flags = this.flexible ? this.flags | 32 : this.flags & -33;
        this.flags = this.phone_to_provider ? this.flags | 64 : this.flags & -65;
        this.flags = this.email_to_provider ? this.flags | 128 : this.flags & -129;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeString(this.currency);
        abstractSerializedData.writeInt32(481674261);
        int size = this.prices.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((TLRPC$TL_labeledPrice) this.prices.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
