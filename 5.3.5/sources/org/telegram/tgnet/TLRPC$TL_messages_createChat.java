package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_createChat extends TLObject {
    public static int constructor = 164303470;
    public String title;
    public ArrayList<TLRPC$InputUser> users = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.users.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputUser) this.users.get(a)).serializeToStream(stream);
        }
        stream.writeString(this.title);
    }
}
