package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_getBotCallbackAnswer extends TLObject {
    public static int constructor = -2130010132;
    public byte[] data;
    public int flags;
    public boolean game;
    public int msg_id;
    public InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_messages_botCallbackAnswer.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.game ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.msg_id);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeByteArray(this.data);
        }
    }
}
