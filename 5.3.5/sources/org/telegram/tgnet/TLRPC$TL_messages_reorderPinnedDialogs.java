package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_reorderPinnedDialogs extends TLObject {
    public static int constructor = -1784678844;
    public int flags;
    public boolean force;
    public ArrayList<TLRPC$InputPeer> order = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.force ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(481674261);
        int count = this.order.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputPeer) this.order.get(a)).serializeToStream(stream);
        }
    }
}
