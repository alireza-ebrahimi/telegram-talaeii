package org.telegram.tgnet;

public class TLRPC$TL_inputNotifyAll extends TLRPC$InputNotifyPeer {
    public static int constructor = -1540769658;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
