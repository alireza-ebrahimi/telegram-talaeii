package org.telegram.tgnet;

public class TLRPC$TL_notifyChats extends TLRPC$NotifyPeer {
    public static int constructor = -1073230141;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
