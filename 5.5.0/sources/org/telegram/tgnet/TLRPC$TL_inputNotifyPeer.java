package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputNotifyPeer;
import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_inputNotifyPeer extends InputNotifyPeer {
    public static int constructor = -1195615476;
    public InputPeer peer;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.peer = InputPeer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
    }
}
