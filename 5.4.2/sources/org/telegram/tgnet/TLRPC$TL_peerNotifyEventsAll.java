package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PeerNotifyEvents;

public class TLRPC$TL_peerNotifyEventsAll extends PeerNotifyEvents {
    public static int constructor = 1830677896;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
