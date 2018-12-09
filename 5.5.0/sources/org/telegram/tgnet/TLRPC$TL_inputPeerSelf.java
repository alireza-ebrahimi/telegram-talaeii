package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_inputPeerSelf extends InputPeer {
    public static int constructor = 2107670217;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
