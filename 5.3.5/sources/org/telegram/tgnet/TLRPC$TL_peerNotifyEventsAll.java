package org.telegram.tgnet;

public class TLRPC$TL_peerNotifyEventsAll extends TLRPC$PeerNotifyEvents {
    public static int constructor = 1830677896;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
