package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_messages_getGameHighScores extends TLObject {
    public static int constructor = -400399203;
    public int id;
    public InputPeer peer;
    public InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_messages_highScores.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.id);
        this.user_id.serializeToStream(abstractSerializedData);
    }
}
