package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.NotifyPeer;

public class TLRPC$TL_notifyUsers extends NotifyPeer {
    public static int constructor = -1261946036;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
