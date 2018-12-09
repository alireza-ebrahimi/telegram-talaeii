package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_peerChat extends Peer {
    public static int constructor = -1160714821;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
    }
}
