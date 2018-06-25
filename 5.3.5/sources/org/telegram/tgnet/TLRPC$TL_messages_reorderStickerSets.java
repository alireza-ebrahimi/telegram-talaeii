package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_reorderStickerSets extends TLObject {
    public static int constructor = 2016638777;
    public int flags;
    public boolean masks;
    public ArrayList<Long> order = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
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
