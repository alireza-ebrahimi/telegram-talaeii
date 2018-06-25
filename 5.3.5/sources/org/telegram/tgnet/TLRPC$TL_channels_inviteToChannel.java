package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_channels_inviteToChannel extends TLObject {
    public static int constructor = 429865580;
    public TLRPC$InputChannel channel;
    public ArrayList<TLRPC$InputUser> users = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.users.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputUser) this.users.get(a)).serializeToStream(stream);
        }
    }
}
