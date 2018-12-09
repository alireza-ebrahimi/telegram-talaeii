package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_getDialogs extends TLObject {
    public static int constructor = 421243333;
    public boolean exclude_pinned;
    public int flags;
    public int limit;
    public int offset_date;
    public int offset_id;
    public InputPeer offset_peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_Dialogs.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.exclude_pinned ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.offset_date);
        abstractSerializedData.writeInt32(this.offset_id);
        this.offset_peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.limit);
    }
}
