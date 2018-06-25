package org.telegram.tgnet;

public class TLRPC$TL_inputPeerEmpty extends TLRPC$InputPeer {
    public static int constructor = 2134579434;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
