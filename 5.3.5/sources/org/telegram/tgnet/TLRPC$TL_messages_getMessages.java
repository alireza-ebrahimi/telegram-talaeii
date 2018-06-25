package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_getMessages extends TLObject {
    public static int constructor = 1109588596;
    public ArrayList<Integer> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Messages.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt32(((Integer) this.id.get(a)).intValue());
        }
    }
}
