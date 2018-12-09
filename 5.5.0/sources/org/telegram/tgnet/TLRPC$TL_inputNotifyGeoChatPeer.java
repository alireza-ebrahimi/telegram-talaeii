package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputNotifyPeer;

public class TLRPC$TL_inputNotifyGeoChatPeer extends InputNotifyPeer {
    public static int constructor = 1301143240;
    public TLRPC$TL_inputGeoChat peer;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.peer = TLRPC$TL_inputGeoChat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
    }
}
