package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputNotifyPeer;

public class TLRPC$TL_inputNotifyChats extends InputNotifyPeer {
    public static int constructor = 1251338318;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
