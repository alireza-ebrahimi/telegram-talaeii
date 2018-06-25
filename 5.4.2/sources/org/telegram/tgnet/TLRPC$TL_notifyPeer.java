package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.NotifyPeer;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_notifyPeer extends NotifyPeer {
    public static int constructor = -1613493288;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.peer = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
    }
}
