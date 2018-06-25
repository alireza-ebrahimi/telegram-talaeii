package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_updateStickerSetsOrder extends TLRPC$Update {
    public static int constructor = 196268545;
    public ArrayList<Long> order = new ArrayList();

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.masks = z;
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            for (int a = 0; a < count; a++) {
                this.order.add(Long.valueOf(stream.readInt64(exception)));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.masks ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(481674261);
        int count = this.order.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.order.get(a)).longValue());
        }
    }
}
