package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_payments_paymentReceipt extends TLObject {
    public static int constructor = 1342771681;
    public int bot_id;
    public String credentials_title;
    public String currency;
    public int date;
    public int flags;
    public TLRPC$TL_paymentRequestedInfo info;
    public TLRPC$TL_invoice invoice;
    public int provider_id;
    public TLRPC$TL_shippingOption shipping;
    public long total_amount;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_payments_paymentReceipt TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_payments_paymentReceipt tLRPC$TL_payments_paymentReceipt = new TLRPC$TL_payments_paymentReceipt();
            tLRPC$TL_payments_paymentReceipt.readParams(abstractSerializedData, z);
            return tLRPC$TL_payments_paymentReceipt;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_payments_paymentReceipt", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        this.bot_id = abstractSerializedData.readInt32(z);
        this.invoice = TLRPC$TL_invoice.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.provider_id = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.info = TLRPC$TL_paymentRequestedInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 2) != 0) {
            this.shipping = TLRPC$TL_shippingOption.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        this.currency = abstractSerializedData.readString(z);
        this.total_amount = abstractSerializedData.readInt64(z);
        this.credentials_title = abstractSerializedData.readString(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                User TLdeserialize = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.users.add(TLdeserialize);
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
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeInt32(this.bot_id);
        this.invoice.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.provider_id);
        if ((this.flags & 1) != 0) {
            this.info.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 2) != 0) {
            this.shipping.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeString(this.currency);
        abstractSerializedData.writeInt64(this.total_amount);
        abstractSerializedData.writeString(this.credentials_title);
        abstractSerializedData.writeInt32(481674261);
        int size = this.users.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((User) this.users.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
