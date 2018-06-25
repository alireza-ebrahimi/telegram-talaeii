package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_readFeaturedStickers extends TLObject {
    public static int constructor = 1527873830;
    public ArrayList<Long> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.id.get(a)).longValue());
        }
    }
}
