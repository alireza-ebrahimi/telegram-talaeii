package org.telegram.tgnet;

public class TLRPC$TL_contacts_topPeersNotModified extends TLRPC$contacts_TopPeers {
    public static int constructor = -567906571;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
