package org.telegram.tgnet;

public class TLRPC$TL_peerNotifyEventsEmpty extends TLRPC$PeerNotifyEvents {
    public static int constructor = -1378534221;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
