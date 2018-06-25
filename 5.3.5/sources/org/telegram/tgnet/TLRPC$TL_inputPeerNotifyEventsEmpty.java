package org.telegram.tgnet;

public class TLRPC$TL_inputPeerNotifyEventsEmpty extends TLRPC$InputPeerNotifyEvents {
    public static int constructor = -265263912;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
