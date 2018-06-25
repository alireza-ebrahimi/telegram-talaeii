package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeerNotifyEvents;

public class TLRPC$TL_inputPeerNotifyEventsAll extends InputPeerNotifyEvents {
    public static int constructor = -395694988;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
