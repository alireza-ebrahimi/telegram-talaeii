package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PeerNotifyEvents;

public class TLRPC$TL_peerNotifyEventsEmpty extends PeerNotifyEvents {
    public static int constructor = -1378534221;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
