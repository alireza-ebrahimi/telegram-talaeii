package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_inputPeerUser extends InputPeer {
    public static int constructor = 2072935910;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
        this.access_hash = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt64(this.access_hash);
    }
}
