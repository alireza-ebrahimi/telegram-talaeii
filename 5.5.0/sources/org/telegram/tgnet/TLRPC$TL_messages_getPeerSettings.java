package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_getPeerSettings extends TLObject {
    public static int constructor = 913498268;
    public InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_peerSettings.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
    }
}
