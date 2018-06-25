package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_getAllChats extends TLObject {
    public static int constructor = -341307408;
    public ArrayList<Integer> except_ids = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Chats.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.except_ids.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt32(((Integer) this.except_ids.get(a)).intValue());
        }
    }
}
