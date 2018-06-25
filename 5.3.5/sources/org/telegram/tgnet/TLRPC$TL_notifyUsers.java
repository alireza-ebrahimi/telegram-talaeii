package org.telegram.tgnet;

public class TLRPC$TL_notifyUsers extends TLRPC$NotifyPeer {
    public static int constructor = -1261946036;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
