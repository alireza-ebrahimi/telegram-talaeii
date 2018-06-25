package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.NotifyPeer;

public class TLRPC$TL_notifyChats extends NotifyPeer {
    public static int constructor = -1073230141;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
