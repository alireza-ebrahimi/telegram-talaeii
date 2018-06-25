package org.telegram.tgnet;

public class TLRPC$TL_notifyAll extends TLRPC$NotifyPeer {
    public static int constructor = 1959820384;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
