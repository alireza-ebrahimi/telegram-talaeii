package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_channels_readMessageContents extends TLObject {
    public static int constructor = -357180360;
    public TLRPC$InputChannel channel;
    public ArrayList<Integer> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt32(((Integer) this.id.get(a)).intValue());
        }
    }
}
