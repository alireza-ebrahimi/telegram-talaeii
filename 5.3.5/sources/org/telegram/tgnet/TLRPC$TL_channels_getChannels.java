package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_channels_getChannels extends TLObject {
    public static int constructor = 176122811;
    public ArrayList<TLRPC$InputChannel> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Chats.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputChannel) this.id.get(a)).serializeToStream(stream);
        }
    }
}
