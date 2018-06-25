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

    public static TLRPC$TL_invoice TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_invoice result = new TLRPC$TL_invoice();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_invoice", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.test = z;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.name_requested = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.phone_requested = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.email_requested = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.shipping_address_requested = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.flexible = z;
        if ((this.flags & 64) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.phone_to_provider = z;
        if ((this.flags & 128) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.email_to_provider = z;
        this.currency = stream.readString(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_labeledPrice object = TLRPC$TL_labeledPrice.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.prices.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.test ? this.flags | 1 : this.flags & -2;
        this.flags = this.name_requested ? this.flags | 2 : this.flags & -3;
        this.flags = this.phone_requested ? this.flags | 4 : this.flags & -5;
        this.flags = this.email_requested ? this.flags | 8 : this.flags & -9;
        this.flags = this.shipping_address_requested ? this.flags | 16 : this.flags & -17;
        this.flags = this.flexible ? this.flags | 32 : this.flags & -33;
        this.flags = this.phone_to_provider ? this.flags | 64 : this.flags & -65;
        this.flags = this.email_to_provider ? this.flags | 128 : this.flags & -129;
        stream.writeInt32(this.flags);
        stream.writeString(this.currency);
        stream.writeInt32(481674261);
        int count = this.prices.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_labeledPrice) this.prices.get(a)).serializeToStream(stream);
        }
    }
}
