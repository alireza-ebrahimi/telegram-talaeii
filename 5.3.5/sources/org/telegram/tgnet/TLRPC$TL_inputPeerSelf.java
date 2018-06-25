package org.telegram.tgnet;

public class TLRPC$TL_inputPeerSelf extends TLRPC$InputPeer {
    public static int constructor = 2107670217;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
