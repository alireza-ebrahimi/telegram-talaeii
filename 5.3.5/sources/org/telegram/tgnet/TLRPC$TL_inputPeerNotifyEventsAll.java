package org.telegram.tgnet;

public class TLRPC$TL_inputPeerNotifyEventsAll extends TLRPC$InputPeerNotifyEvents {
    public static int constructor = -395694988;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
