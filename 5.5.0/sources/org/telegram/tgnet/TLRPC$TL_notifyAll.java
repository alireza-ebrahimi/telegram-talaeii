package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.NotifyPeer;

public class TLRPC$TL_notifyAll extends NotifyPeer {
    public static int constructor = 1959820384;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
