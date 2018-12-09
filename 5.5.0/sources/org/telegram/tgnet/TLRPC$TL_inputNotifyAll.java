package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputNotifyPeer;

public class TLRPC$TL_inputNotifyAll extends InputNotifyPeer {
    public static int constructor = -1540769658;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
