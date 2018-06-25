package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_payments_validatedRequestedInfo extends TLObject {
    public static int constructor = -784000893;
    public int flags;
    public String id;
    public ArrayList<TLRPC$TL_shippingOption> shipping_options = new ArrayList();

    public static TLRPC$TL_payments_validatedRequestedInfo TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_payments_validatedRequestedInfo result = new TLRPC$TL_payments_validatedRequestedInfo();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_payments_validatedRequestedInfo", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.id = stream.readString(exception);
        }
        if ((this.flags & 2) != 0) {
            if (stream.readInt32(exception) == 481674261) {
                int count = stream.readInt32(exception);
                int a = 0;
                while (a < count) {
                    TLRPC$TL_shippingOption object = TLRPC$TL_shippingOption.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object != null) {
                        this.shipping_options.add(object);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.id);
        }
        if ((this.flags & 2) != 0) {
            stream.writeInt32(481674261);
            int count = this.shipping_options.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$TL_shippingOption) this.shipping_options.get(a)).serializeToStream(stream);
            }
        }
    }
}
