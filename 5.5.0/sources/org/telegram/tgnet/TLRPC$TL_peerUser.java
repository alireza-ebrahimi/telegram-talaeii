package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_peerUser extends Peer {
    public static int constructor = -1649296275;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
    }
}
