package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;

public class TLRPC$TL_messages_clearRecentStickers extends TLObject {
    public static int constructor = -1986437075;
    public boolean attached;
    public int flags;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.attached ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
    }
}
