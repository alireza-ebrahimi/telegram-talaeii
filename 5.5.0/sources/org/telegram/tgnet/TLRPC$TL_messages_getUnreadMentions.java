package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_getUnreadMentions extends TLObject {
    public static int constructor = 1180140658;
    public int add_offset;
    public int limit;
    public int max_id;
    public int min_id;
    public int offset_id;
    public InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_Messages.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.offset_id);
        abstractSerializedData.writeInt32(this.add_offset);
        abstractSerializedData.writeInt32(this.limit);
        abstractSerializedData.writeInt32(this.max_id);
        abstractSerializedData.writeInt32(this.min_id);
    }
}
