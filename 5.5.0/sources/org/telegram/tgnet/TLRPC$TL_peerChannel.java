package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_peerChannel extends Peer {
    public static int constructor = -1109531342;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.channel_id = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.channel_id);
    }
}
