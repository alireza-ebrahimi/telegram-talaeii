package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputNotifyPeer;

public class TLRPC$TL_inputNotifyUsers extends InputNotifyPeer {
    public static int constructor = 423314455;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
