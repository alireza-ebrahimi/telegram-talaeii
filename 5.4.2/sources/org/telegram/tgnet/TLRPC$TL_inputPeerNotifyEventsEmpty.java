package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPeerNotifyEvents;

public class TLRPC$TL_inputPeerNotifyEventsEmpty extends InputPeerNotifyEvents {
    public static int constructor = -265263912;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
