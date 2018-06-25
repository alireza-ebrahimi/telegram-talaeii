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

    public static TLRPC$TL_payments_paymentReceipt TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_payments_paymentReceipt result = new TLRPC$TL_payments_paymentReceipt();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_payments_paymentReceipt", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.bot_id = stream.readInt32(exception);
        this.invoice = TLRPC$TL_invoice.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.provider_id = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.info = TLRPC$TL_paymentRequestedInfo.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 2) != 0) {
            this.shipping = TLRPC$TL_shippingOption.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        this.currency = stream.readString(exception);
        this.total_amount = stream.readInt64(exception);
        this.credentials_title = stream.readString(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                User object = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.users.add(object);
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
        stream.writeInt32(this.flags);
        stream.writeInt32(this.date);
        stream.writeInt32(this.bot_id);
        this.invoice.serializeToStream(stream);
        stream.writeInt32(this.provider_id);
        if ((this.flags & 1) != 0) {
            this.info.serializeToStream(stream);
        }
        if ((this.flags & 2) != 0) {
            this.shipping.serializeToStream(stream);
        }
        stream.writeString(this.currency);
        stream.writeInt64(this.total_amount);
        stream.writeString(this.credentials_title);
        stream.writeInt32(481674261);
        int count = this.users.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
